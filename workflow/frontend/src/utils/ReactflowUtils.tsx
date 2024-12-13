import {Node,Edge,XYPosition } from "@xyflow/react";



interface XYPosition {
    x: number;
    y: number;
}

interface Node {
    id: string;
    // other node properties
}

interface Edge {
    source: string;
    target: string;
    // other edge properties
}

// Main layout calculation function
const calculatePositions = (
    nodes: Node[],
    edges: Edge[],
    layoutType: string
): { [key: string]: XYPosition } => {
    return layoutType === 'vertical'
        ? calculateVerticalPositions(nodes, edges)
        : calculateHorizontalPositions(nodes, edges);
};

// Common utilities
const createIncomingEdgesMap = (edges: Edge[]): Map<string, string[]> => {
    const incomingEdges = new Map();
    edges.forEach(edge => {
        if (!incomingEdges.has(edge.target)) {
            incomingEdges.set(edge.target, []);
        }
        incomingEdges.get(edge.target).push(edge.source);
    });
    return incomingEdges;
};

const createLevelsMap = (
    nodes: Node[],
    edges: Edge[],
    incomingEdges: Map<string, string[]>
): { [key: number]: string[] } => {
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

    return levels;
};

// Vertical layout calculation
const calculateVerticalPositions = (nodes: Node[], edges: Edge[]): { [key: string]: XYPosition } => {
    const VERTICAL_SPACING = 100;
    const HORIZONTAL_SPACING = 200;

    const incomingEdges = createIncomingEdgesMap(edges);
    const levels = createLevelsMap(nodes, edges, incomingEdges);
    const positions: { [key: string]: XYPosition } = {};

    Object.entries(levels).forEach(([level, nodesInLevel]) => {
        const levelY = parseInt(level) * VERTICAL_SPACING;
        const startX = (nodesInLevel.length > 1)
            ? -(HORIZONTAL_SPACING * (nodesInLevel.length - 1)) / 2
            : 0;

        nodesInLevel.forEach((nodeId, index) => {
            positions[nodeId] = {
                x: startX + (index * HORIZONTAL_SPACING),
                y: levelY
            };
        });
    });

    return positions;
};

// Horizontal layout calculation
const calculateHorizontalPositions = (nodes: Node[], edges: Edge[]): { [key: string]: XYPosition } => {
    const HORIZONTAL_SPACING = 200;
    const VERTICAL_SPACING = 150;
    const INITIAL_OFFSET = 50;

    const incomingEdges = createIncomingEdgesMap(edges);
    const levels = createLevelsMap(nodes, edges, incomingEdges);
    const positions: { [key: string]: XYPosition } = {};

    Object.entries(levels).forEach(([level, nodesInLevel]) => {
        const levelX = parseInt(level) * HORIZONTAL_SPACING + INITIAL_OFFSET;
        const totalHeight = (nodesInLevel.length - 1) * VERTICAL_SPACING;
        const startY = -totalHeight / 2;

        nodesInLevel.forEach((nodeId, index) => {
            positions[nodeId] = {
                x: levelX,
                y: startY + (index * VERTICAL_SPACING)
            };
        });
    });

    return positions;
};

export {
    calculatePositions,
    calculateVerticalPositions,
    calculateHorizontalPositions
};