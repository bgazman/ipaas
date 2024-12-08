import { Position as HandlePosition } from 'reactflow';

export type LayoutDirection = 'vertical' | 'horizontal';

export type Position = {
    x: number;
    y: number;

  }
  
  export type Node = {
    id: string;
    position?: Position;
    sourcePosition?: HandlePosition;  // Add this
    targetPosition?: HandlePosition;  // Add this
  }
  
  export type Edge = {
    id: string;
    source: string;
    target: string;
  }
  
  export type Workflow = {
    nodes: Node[];
    edges: Edge[];
  }