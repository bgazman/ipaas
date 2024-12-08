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
import {LayoutDirection} from "../types/workflow-types.ts";

import {calculatePositions} from "../utils/WorkflowPositionUtils"




interface WorkflowLayoutProps {
    definition: Workflow;
   }



const WorkflowLayout: React.FC<{
    definition: Workflow;
    direction?: LayoutDirection;
}> = ({ definition, direction = 'horizontal' }) => {
    const positions = calculatePositions(definition.nodes, definition.edges, direction);

    const nodesWithPositions = definition.nodes.map(node => ({
        id: node.id,
        position: positions[node.id],
        data: { label: node.id },
        type: 'default',
        sourcePosition: direction === 'horizontal' ? Position.Right : Position.Bottom,
        targetPosition: direction === 'horizontal' ? Position.Left : Position.Top
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