import { calculatePositions } from "./calculatePositions";

export const initializeCanvas = (workflowDefinition, layoutType = "vertical") => {
    if (!workflowDefinition || !workflowDefinition.nodes || !workflowDefinition.edges) {
        console.error("Invalid workflow definition");
        return { nodes: [], edges: [] };
    }

    // Extract nodes and edges from the workflow definition
    const { nodes, edges } = workflowDefinition;

    // Use calculatePositions to arrange nodes and edges
    const { nodes: arrangedNodes, edges: arrangedEdges } = calculatePositions(
        nodes.map((node) => ({
            ...node,
            position: { x: 0, y: 0 }, // Reset positions for recalculation
        })),
        edges
    );

    return {
        nodes: arrangedNodes,
        edges: arrangedEdges,
    };
};
