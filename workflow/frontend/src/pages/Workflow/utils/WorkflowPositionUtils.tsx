import {Edge , Node , Position, LayoutDirection} from "../types/workflow-types.ts";

export const calculatePositions = (
    nodes: Node[],
    edges: Edge[],
    direction: LayoutDirection
) => {
    return direction === 'vertical'
        ? calculateVerticalPositions(nodes, edges)
        : calculateHorizontalPositions(nodes, edges);
};

export const calculateVerticalPositions = (nodes: Node[], edges: Edge[]) => {
    const verticalSpacing = 100;
    const horizontalSpacing = 200;

    const incomingEdges = new Map();
    edges.forEach(edge => {
        if (!incomingEdges.has(edge.target)) {
            incomingEdges.set(edge.target, []);
        }
        incomingEdges.get(edge.target).push(edge.source);
    });

    const levels: { [key: number]: string[] } = {};
    const seen = new Set();

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

    const positions: { [key: string]: Position } = {};
    Object.entries(levels).forEach(([level, nodesInLevel]) => {
        const levelY = parseInt(level) * verticalSpacing;
        const startX = (nodesInLevel.length > 1)
            ? -(horizontalSpacing * (nodesInLevel.length - 1)) / 2
            : 0;

        nodesInLevel.forEach((nodeId, index) => {
            positions[nodeId] = {
                x: startX + (index * horizontalSpacing),
                y: levelY
            };
        });
    });

    return positions;
};
export const calculateHorizontalPositions = (nodes: Node[], edges: Edge[]) => {
    const horizontalSpacing = 200;
    const verticalSpacing = 150;

    const incomingEdges = new Map();
    edges.forEach(edge => {
        if (!incomingEdges.has(edge.target)) {
            incomingEdges.set(edge.target, []);
        }
        incomingEdges.get(edge.target).push(edge.source);
    });

    const levels: { [key: number]: string[] } = {};
    const seen = new Set();

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

    const positions: { [key: string]: Position } = {};
    Object.entries(levels).forEach(([level, nodesInLevel]) => {
        // Assign X based on level (depth)
        const levelX = parseInt(level) * horizontalSpacing + 50; // Added offset

        // Calculate vertical distribution
        const totalHeight = (nodesInLevel.length - 1) * verticalSpacing;
        const startY = -totalHeight / 2;

        nodesInLevel.forEach((nodeId, index) => {
            positions[nodeId] = {
                x: levelX,
                y: startY + (index * verticalSpacing)
            };
        });
    });

    return positions;
};