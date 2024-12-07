import React from 'react';
import ReactFlow, {
    Node,
    Edge,
    NodeTypes,
    Background,
    Controls,
    useNodesState,
    useEdgesState,
    MarkerType
} from 'reactflow';
import 'reactflow/dist/style.css';
import Workflow from "../Workflow";
import {WorkflowEdge,WorkflowNode} from "../types/types.ts";
import {Position} from "../types/workflow-types.ts";
import { Edge as wfEdge } from "../types/workflow-types";
import { Node as wfNode } from "../types/workflow-types";




interface WorkflowLayoutProps {
    definition: Workflow;
   }


const calculatePositions = (nodes: wfNode[], edges: wfEdge[]) => {
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
   const WorkflowLayout: React.FC<WorkflowLayoutProps> = ({ definition }) => {
       const positions = calculatePositions(definition.nodes, definition.edges);

       // Apply calculated positions to nodes
       const nodesWithPositions = definition.nodes.map(node => ({
           id: node.id,
           position: positions[node.id],
           data: { label: node.id },
           type: 'default'
       }));


       return (
           <ReactFlow
               nodes={nodesWithPositions}
               edges={definition.edges.map(edge => ({
                   id: edge.id,
                   source: edge.source,
                   target: edge.target,
                   type: 'smoothstep'
               }))}
               fitView
           />
       );
   };

export default WorkflowLayout;