// workflow-types.ts
export type Position = {
    x: number;
    y: number;
  }
  
  export type Node = {
    id: string;
    position?: Position;
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