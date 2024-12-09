export interface Node {
    id: string;
    type: 'input' | 'output' | 'default';
    position: {
      x: number;
      y: number;
    };
    data: {
      label: string;
      description?: string;
    };
    style?: {
      background?: string;
      border?: string;
      width?: number;
      padding?: number;
    };
  }
  
  export interface Edge {
    id: string;
    source: string;
    target: string;
    type?: 'default' | 'straight' | 'step' | 'smoothstep' | 'bezier';
    animated?: boolean;
    style?: {
      stroke?: string;
      strokeWidth?: number;
    };
    markerEnd?: string;
  }
  
  export interface WorkflowData {
    nodes: Node[];
    edges: Edge[];
  }
  
  const workflowSchema: WorkflowData = {
    nodes: [
      {
        id: '1',
        type: 'input',
        position: { x: 100, y: 100 },
        data: { label: 'New Request' }
      },
      {
        id: '2',
        type: 'default',
        position: { x: 100, y: 200 },
        data: { label: 'Validate' }
      },
      {
        id: '3',
        type: 'default',
        position: { x: 100, y: 300 },
        data: { label: 'Process' }
      },
      {
        id: '4',
        type: 'output',
        position: { x: 100, y: 400 },
        data: { label: 'Complete' }
      }
    ],
    edges: [
      {
        id: 'e1-2',
        source: '1',
        target: '2',
        type: 'smoothstep'
      },
      {
        id: 'e2-3',
        source: '2',
        target: '3',
        type: 'smoothstep'
      },
      {
        id: 'e3-4',
        source: '3',
        target: '4',
        type: 'smoothstep'
      }
    ]
  };
  
  export default workflowSchema;