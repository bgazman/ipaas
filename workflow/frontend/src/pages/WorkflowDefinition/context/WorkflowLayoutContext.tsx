import React, { createContext, useContext, useState,useCallback  } from 'react';
import {Node, Edge, Position} from 'reactflow';
import { WorkflowDefinition } from "../types/workflow-types.ts";
interface WorkflowLayoutContextType {
    definition: WorkflowDefinition;
    handleNodeClick: (event: any, node: Node) => void;
    handleEdgeClick: (event: any, edge: Edge) => void;
    setDefinition?: (definition: WorkflowDefinition) => void;
    handleAddWorkflow: () => void;  // Add this line
    layoutType: 'horizontal' | 'vertical';

}

const WorkflowLayoutContext = createContext<WorkflowLayoutContextType>({
    handleAddWorkflow(): void {
    },
    definition: { nodes: [], edges: [] },
    handleNodeClick: () => {},
    handleEdgeClick: () => {},
    setDefinition: () => {},
    layoutType: 'horizontal'


});

const emptyWorkflowDefinition: WorkflowDefinition = {
    nodes: [
        { id: '1', type: 'default', data: { label: 'Start' }, position: { x: 100, y: 100 } },

    ],
    edges: [

    ],
};
export const WorkflowLayoutProvider: React.FC<{
    children: React.ReactNode;
    layoutType: 'horizontal' | 'vertical'
}> = ({ children, layoutType }) => {
    const [definition, setDefinition] = useState<WorkflowDefinition>({
        nodes: [],
        edges: []
    });

    const handleNodeClick = useCallback((event: React.MouseEvent, node: Node) => {
        const newNodeId = `node-${Date.now()}`;
        const newNode: Node = {
            id: newNodeId,
            type: 'default',
            data: { label: `New Node ${newNodeId}` },
            position: layoutType === 'horizontal'
                ? { x: node.position.x + 200, y: node.position.y }
                : { x: node.position.x, y: node.position.y + 150 },
            sourcePosition: layoutType === 'horizontal' ? Position.Right : Position.Bottom,
            targetPosition: layoutType === 'horizontal' ? Position.Left : Position.Top
        };

        const newEdge: Edge = {
            id: `edge-${Date.now()}`,
            source: node.id,
            target: newNodeId,
            type: 'step'
        };

        setDefinition(prev => ({
            nodes: [...prev.nodes, newNode],
            edges: [...prev.edges, newEdge]
        }));
    }, [layoutType]);

    const handleEdgeClick = (event: React.MouseEvent, edge: Edge) => {
        // Edge click handler implementation
    };

    const handleAddWorkflow = () => {
        setDefinition(emptyWorkflowDefinition);
    };

    return (
        <WorkflowLayoutContext.Provider value={{
            layoutType,
            definition,
            handleNodeClick,
            handleEdgeClick,
            setDefinition,
            handleAddWorkflow
        }}>
            {children}
        </WorkflowLayoutContext.Provider>
    );
};

export const useWorkflowLayout = () => {
    const context = useContext(WorkflowLayoutContext);
    if (context === undefined) {
        throw new Error('useWorkflowLayout must be used within a WorkflowProvider');
    }
    return context;
};