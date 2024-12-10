import React from 'react';
import ReactFlow, {Position, Node, Edge} from 'reactflow';
import {calculatePositions} from "../utils/WorkflowLayoutUtils.tsx";
import {useWorkflowLayout} from "../context/WorkflowLayoutContext.tsx";
import {WorkflowDefinition} from "../types/workflow-types.ts";

interface WorkflowLayoutProps {
    layoutType: 'horizontal' | 'vertical';
    workflowDefinitionInterface: WorkflowDefinition;
}

interface WorkflowLayoutProps {
    layoutType?: string
}

const WorkflowLayout: React.FC<WorkflowLayoutProps> = ({layoutType = 'horizontal'}) => {
    const context = useWorkflowLayout();
    const {definition, handleNodeClick, handleEdgeClick} = context;
    if (!definition || definition.nodes.length === 0) {
        return <ReactFlow nodes={[]} edges={[]} fitView/>;
    }

    const positions = calculatePositions(definition.nodes, definition.edges, layoutType);

    const nodesWithPositions = definition.nodes.map(node => ({
        id: node.id,
        position: positions[node.id],
        data: {label: node.id},
        type: 'default',
        sourcePosition: layoutType === 'horizontal' ? Position.Right : Position.Bottom,
        targetPosition: layoutType === 'horizontal' ? Position.Left : Position.Top
    }));

    return (
        <ReactFlow
            nodes={nodesWithPositions}
            edges={definition.edges.map(edge => ({
                id: edge.id,
                source: edge.source,
                target: edge.target,
                type: 'step',
            }))}
            onNodeClick={handleNodeClick}
            onEdgeClick={handleEdgeClick}
            nodesDraggable={false}
            fitView
        />
    );
};

export default WorkflowLayout;