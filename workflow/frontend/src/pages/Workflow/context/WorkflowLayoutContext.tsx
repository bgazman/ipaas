import React, { createContext, useContext, useState } from 'react';
import { Node, Edge } from 'reactflow';
import { WorkflowDefinition } from "../types/workflow-types.ts";
interface WorkflowLayoutContextType {
    definition: WorkflowDefinition;
    handleNodeClick: (event: any, node: Node) => void;
    handleEdgeClick: (event: any, edge: Edge) => void;
    setDefinition?: (definition: WorkflowDefinition) => void;
    handleAddWorkflow: () => void;  // Add this line
}

const WorkflowLayoutContext = createContext<WorkflowLayoutContextType>({
    handleAddWorkflow(): void {
    },
    definition: { nodes: [], edges: [] },
    handleNodeClick: () => {},
    handleEdgeClick: () => {},
    setDefinition: () => {}
});

const emptyWorkflowDefinition: WorkflowDefinition = {
    nodes: [
        { id: '1', type: 'default', data: { label: 'Start' }, position: { x: 100, y: 100 } },

    ],
    edges: [

    ],
};
export const WorkflowLayoutProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const [definition, setDefinition] = useState<WorkflowDefinition>({
        nodes: [],
        edges: []
    });

    const handleNodeClick = (event: React.MouseEvent, node: Node) => {
        console.log(node.id);
    };

    const handleEdgeClick = (event: React.MouseEvent, edge: Edge) => {
        // Edge click handler implementation
    };

    const handleAddWorkflow = () => {
        setDefinition(emptyWorkflowDefinition);
    };

    return (
        <WorkflowLayoutContext.Provider value={{
            definition,
            handleNodeClick,
            handleEdgeClick,
            setDefinition,
            handleAddWorkflow  // Add this new function to the context
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