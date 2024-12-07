// types.ts
import { Node, Edge } from 'reactflow';

// Define the shape of your node data
export interface WorkflowNodeData {
    label: string;
    icon?: string; // Optional icon property
    condition?: string; // Optional condition for conditional nodes
}

// Extend the Node type with your custom data
export type WorkflowNode = Node<WorkflowNodeData>;

// Extend the Edge type if necessary (you can add custom properties)
export type WorkflowEdge = Edge;