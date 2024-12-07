interface FlowNode {
    id: string;
    type: string;
    position: { x: number; y: number };
    data: {
        label: string;
        condition?: string;
        metadata?: Record<string, any>;
    };
}

interface FlowEdge {
    id: string;
    source: string;
    target: string;
    type?: string;
    label?: string;
    animated?: boolean;
}

const flowData = {
    nodes: [
        {
            id: "start",
            type: "input",
            position: { x: 250, y: 0 },
            data: { label: "Start Process" }
        },
        {
            id: "condition1",
            type: "conditional",
            position: { x: 250, y: 100 },
            data: {
                label: "Check Status",
                condition: "status === 'active'"
            }
        },
        {
            id: "parallel1",
            type: "parallel",
            position: { x: 100, y: 200 },
            data: { label: "Parallel Tasks" }
        },
        {
            id: "task1",
            type: "default",
            position: { x: 0, y: 300 },
            data: { label: "Send Email" }
        },
        {
            id: "task2",
            type: "default",
            position: { x: 200, y: 300 },
            data: { label: "Update Database" }
        },
        {
            id: "merge1",
            type: "parallel",
            position: { x: 100, y: 400 },
            data: { label: "Merge Parallel Tasks" }
        },
        {
            id: "fallback",
            type: "default",
            position: { x: 400, y: 200 },
            data: { label: "Handle Inactive Status" }
        },
        {
            id: "end",
            type: "output",
            position: { x: 250, y: 500 },
            data: { label: "End Process" }
        }
    ],
    edges: [
        {
            id: "e1",
            source: "start",
            target: "condition1",
            type: "smoothstep"
        },
        {
            id: "e2",
            source: "condition1",
            target: "parallel1",
            type: "smoothstep",
            label: "True"
        },
        {
            id: "e3",
            source: "condition1",
            target: "fallback",
            type: "smoothstep",
            label: "False"
        },
        {
            id: "e4",
            source: "parallel1",
            target: "task1",
            type: "smoothstep"
        },
        {
            id: "e5",
            source: "parallel1",
            target: "task2",
            type: "smoothstep"
        },
        {
            id: "e6",
            source: "task1",
            target: "merge1",
            type: "smoothstep"
        },
        {
            id: "e7",
            source: "task2",
            target: "merge1",
            type: "smoothstep"
        },
        {
            id: "e8",
            source: "merge1",
            target: "end",
            type: "smoothstep"
        },
        {
            id: "e9",
            source: "fallback",
            target: "end",
            type: "smoothstep"
        }
    ]
} as const;

export default flowData;