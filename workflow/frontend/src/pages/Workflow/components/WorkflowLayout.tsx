import React from 'react';
import ReactFlow, {
    Node,
    Edge,
    NodeTypes,
    Background,
    Controls,
    useNodesState,
    useEdgesState,
    MarkerType, Position
} from 'reactflow';
import 'reactflow/dist/style.css';
import Workflow from "../Workflow";

import {calculatePositions} from "../utils/WorkflowPositionUtils"


export type WorkflowLayoutType = 'vertical' | 'horizontal';




interface WorkflowLayoutProps {
    definition: Workflow;
   }



const WorkflowLayout: React.FC<{
    definition: Workflow;
    layoutType?: WorkflowLayoutType;
}> = ({ definition, layoutType = 'horizontal' }) => {
    const positions = calculatePositions(definition.nodes, definition.edges, layoutType);

    const nodesWithPositions = definition.nodes.map(node => ({
        id: node.id,
        position: positions[node.id],
        data: { label: node.id },
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
            fitView
        />
    );
};
export default WorkflowLayout;