import { Node, Edge, Position } from '../types';
import { SPACING } from '../constants';


export const calculateGridPositions = (nodes, options = {}) => {
    console.log("GRID POSITIONS");

    const {
        startX = 250,           // Center position
        startY = 50,           // Starting Y
        gridWidth = 200,       // Distance between columns
        gridHeight = 100       // Distance between rows
    } = options;

    // Define which column and row each node should be in
    const gridPositions = {
        "start": { col: 1, row: 0 },           // Center column, first row
        "validate": { col: 1, row: 1 },        // Center column
        "stock_check": { col: 0, row: 2 },     // Left column
        "payment_auth": { col: 2, row: 2 },    // Right column
        "restock": { col: 0, row: 3 },         // Left column
        "process_payment": { col: 2, row: 3 },  // Right column
        "wait_stock": { col: 0, row: 4 },      // Left column
        "prepare_shipment": { col: 1, row: 5 }, // Back to center
        "end": { col: 1, row: 6 }              // Center column
    };

    return nodes.map(node => ({
        ...node,
        position: {
            x: startX + (gridPositions[node.id].col - 1) * gridWidth,
            y: startY + gridPositions[node.id].row * gridHeight
        }
    }));
};

export const calculateComplexFlowPositions = (nodes, edges, options = {}) => {
    const {
        startX = 250,
        startY = 50,
        verticalGap = 100,
        horizontalGap = 200  // Gap between parallel nodes
    } = options;

    // Find parallel paths by analyzing edges
    const getNextNodes = (nodeId) =>
        edges.filter(e => e.source === nodeId).map(e => e.target);

    const getPrevNodes = (nodeId) =>
        edges.filter(e => e.target === nodeId).map(e => e.source);

    // Calculate levels (y-position) based on dependencies
    const levels = {};
    const calculateLevels = (nodeId, level = 0) => {
        if (levels[nodeId] === undefined || level > levels[nodeId]) {
            levels[nodeId] = level;
            getNextNodes(nodeId).forEach(nextId =>
                calculateLevels(nextId, level + 1)
            );
        }
    };

    // Start from input nodes
    const startNodes = nodes.filter(n => n.type === 'input').map(n => n.id);
    startNodes.forEach(id => calculateLevels(id));

    // Calculate parallel positions (x-position) for each level
    const positions = {};
    Object.entries(levels).forEach(([nodeId, level]) => {
        const parallelNodes = Object.entries(levels)
            .filter(([, l]) => l === level)
            .map(([id]) => id);

        const position = parallelNodes.indexOf(nodeId);
        const totalParallel = parallelNodes.length;
        const xOffset = position - (totalParallel - 1) / 2;

        positions[nodeId] = {
            x: startX + (xOffset * horizontalGap),
            y: startY + (level * verticalGap)
        };
    });

    // Return nodes with calculated positions
    return nodes.map(node => ({
        ...node,
        position: positions[node.id]
    }));
};

export const calculatePositions = (nodes: Node[], edges: Edge[]): Record<string, Position> => {
    const incomingEdges = new Map();
    const parentPositions = new Map();
    const childrenMap = new Map();
    const MIN_NODE_SPACING = SPACING.HORIZONTAL * 1.2;

    // Initialize positions map with default values
    const positions: Record<string, Position> = {};
    nodes.forEach(node => {
        positions[node.id] = {
            x: node.position?.x || 0,
            y: node.position?.y || 0
        };
    });

    // Track original positions for ordering
    const originalPositions = new Map(
        nodes.map(node => [node.id, node.position?.x || 0])
    );

    edges.forEach(edge => {
        if (!incomingEdges.has(edge.target)) {
            incomingEdges.set(edge.target, []);
        }
        incomingEdges.get(edge.target).push(edge.source);
        parentPositions.set(edge.target, edge.source);

        if (!childrenMap.has(edge.source)) {
            childrenMap.set(edge.source, []);
        }
        childrenMap.get(edge.source).push(edge.target);
    });

    const levels: Record<number, string[]> = {};
    const seen = new Set();

    const startNode = nodes.find(node => node.id === 'start');
    const centerX = startNode?.position?.x || 0;
    const centerY = startNode?.position?.y || 0;

    const assignLevel = (nodeId: string, level: number) => {
        if (!seen.has(nodeId)) {
            seen.add(nodeId);
            levels[level] = levels[level] || [];
            levels[level].push(nodeId);

            edges
                .filter(edge => edge.source === nodeId)
                .forEach(edge => assignLevel(edge.target, level + 1));
        }
    };

    nodes
        .filter(node => !incomingEdges.has(node.id))
        .forEach(node => assignLevel(node.id, 0));

    const getX = (nodeId: string): number => {
        return positions[nodeId]?.x || 0;
    };

    const adjustOverlappingPositions = (levelNodes: string[], levelY: number) => {
        if (levelNodes.length <= 1) return;

        const sortedNodes = [...levelNodes].sort((a, b) => getX(a) - getX(b));

        for (let i = 1; i < sortedNodes.length; i++) {
            const prevNode = sortedNodes[i - 1];
            const currentNode = sortedNodes[i];
            const gap = getX(currentNode) - getX(prevNode);

            if (gap < MIN_NODE_SPACING) {
                const shift = MIN_NODE_SPACING - gap;
                for (let j = i; j < sortedNodes.length; j++) {
                    positions[sortedNodes[j]].x += shift;
                }
            }
        }

        const leftmost = Math.min(...sortedNodes.map(id => getX(id)));
        const rightmost = Math.max(...sortedNodes.map(id => getX(id)));
        const center = (leftmost + rightmost) / 2;
        const offset = centerX - center;

        sortedNodes.forEach(nodeId => {
            positions[nodeId].x += offset;
        });
    };

    // Position nodes level by level
    Object.entries(levels).forEach(([level, nodesInLevel]) => {
        const levelY = centerY + (parseInt(level) * SPACING.VERTICAL);

        // Group nodes by their parent
        const nodesByParent = new Map();
        nodesInLevel.forEach(nodeId => {
            const parentId = parentPositions.get(nodeId);
            if (!nodesByParent.has(parentId)) {
                nodesByParent.set(parentId, []);
            }
            nodesByParent.get(parentId).push(nodeId);
        });

        // Position each group of siblings
        nodesByParent.forEach((siblings, parentId) => {
            // Special handling for single child - place directly below parent
            if (siblings.length === 1) {
                const parentX = parentId ? getX(parentId) : centerX;
                positions[siblings[0]] = {
                    x: parentX,
                    y: levelY
                };
                return;
            }

            // For multiple siblings, sort and space them out
            siblings.sort((a, b) => {
                const aPos = originalPositions.get(a) || 0;
                const bPos = originalPositions.get(b) || 0;
                return aPos - bPos;
            });

            const parentX = parentId ? getX(parentId) : centerX;
            const totalWidth = MIN_NODE_SPACING * (siblings.length - 1);
            const startX = parentX - (totalWidth / 2);

            siblings.forEach((nodeId, index) => {
                positions[nodeId] = {
                    x: startX + (index * MIN_NODE_SPACING),
                    y: levelY
                };
            });
        });

        // After positioning all nodes in this level, check and fix any overlaps
        adjustOverlappingPositions(nodesInLevel, levelY);
    });

    return positions;
};

