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

interface WorkflowStep {
    id: string;
    type: 'start' | 'end' | 'task' | 'condition' | 'parallel';
    label: string;
    next?: string | string[];
    position?: { x: number; y: number };
}

interface WorkflowDefinition {
    steps: WorkflowStep[];
}

interface WorkflowLayoutProps extends React.HTMLProps<HTMLDivElement> {
    definition: {
        steps: Array<{
            id: string;
            type: 'start' | 'end' | 'task' | 'condition' | 'parallel';
            label: string;
            next?: string | string[];
        }>;
    };
}
const WorkflowNode = ({ data }: { data: { label: string; type: string } }) => (
    <div className={`px-4 py-2 rounded-lg border-2 bg-white shadow-md
    ${data.type === 'start' ? 'border-green-500' :
        data.type === 'end' ? 'border-red-500' :
            data.type === 'condition' ? 'border-yellow-500' :
                'border-blue-500'}`}>
        <div className="font-medium">{data.label}</div>
    </div>
);

const nodeTypes: NodeTypes = {
    workflow: WorkflowNode
};

const createNodesAndEdges = (definition: WorkflowDefinition) => {
    const stepsWithPositions = definition.steps.map((step, index) => ({
        ...step,
        position: { x: 250, y: index * 100 }
    }));

    const nodes: Node[] = stepsWithPositions.map(step => ({
        id: step.id,
        type: 'workflow',
        position: step.position!,
        data: { label: step.label, type: step.type }
    }));

    const edges: Edge[] = stepsWithPositions.reduce((acc: Edge[], step) => {
        if (step.next) {
            const nextSteps = Array.isArray(step.next) ? step.next : [step.next];
            nextSteps.forEach(nextId => {
                acc.push({
                    id: `${step.id}-${nextId}`,
                    source: step.id,
                    target: nextId,
                    type: 'smoothstep'
                });
            });
        }
        return acc;
    }, []);

    return { nodes, edges };
};

const WorkflowLayout: React.FC<WorkflowLayoutProps> = ({ definition }) => {
    const { nodes: initialNodes, edges: initialEdges } = createNodesAndEdges(definition);
    const [nodes, setNodes, onNodesChange] = useNodesState(initialNodes);
    const [edges, setEdges, onEdgesChange] = useEdgesState(initialEdges);

    return (
        <div className="w-full h-full">
            <ReactFlow
                nodes={nodes}
                edges={edges}
                onNodesChange={onNodesChange}
                onEdgesChange={onEdgesChange}
                nodeTypes={nodeTypes}
                defaultEdgeOptions={{
                    type: 'smoothstep',
                    style: { stroke: '#64748b' },
                    markerEnd: { type: MarkerType.ArrowClosed }
                }}
                fitView
            >
                <Background />
                <Controls />
            </ReactFlow>
        </div>
    );
};
export default WorkflowLayout;