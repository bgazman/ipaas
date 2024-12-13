import { useCallback } from 'react';
import { Node, Edge } from '../types';
import { STYLES } from '../constants';
import { calculatePositions } from '../utils/calculatePositions';

export const useFlowNodes = (
    nodes: Node[],
    setNodes: (nodes: Node[]) => void,
    nodeId: number,
) => {
    const createNode = useCallback((id: string) => ({
        id,
        data: { label: id },
        position: { x: 0, y: 0 },
        style: STYLES.DEFAULT_NODE
    }), []);

    const updateNodesPositions = useCallback((nodes: Node[], edges: Edge[]) => {
        const positions = calculatePositions(nodes, edges);
        return nodes.map(node => ({
            ...node,
            position: positions[node.id] || node.position
        }));
    }, []);

    return { createNode, updateNodesPositions };
};