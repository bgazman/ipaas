
import '@xyflow/react/dist/style.css';
import { DEFAULT_VIEWPORT } from './constants';
import { isNodeOutsideView } from './utils/viewport';



// src/components/Flow/Flow.tsx
import {useState, useCallback, useRef} from 'react';
import {
    ReactFlow,
    useNodesState,
    useEdgesState,
    addEdge,
    Panel,
    ReactFlowProvider, Background, ReactFlowInstance
} from '@xyflow/react';
import { INITIAL_NODES, FIT_VIEW_OPTIONS } from './constants';
import { useFlowNodes } from './hooks/useFlowNodes';

export function Flow() {
    const [nodes, setNodes, onNodesChange] = useNodesState(INITIAL_NODES);
    const [edges, setEdges, onEdgesChange] = useEdgesState([]);
    const [nodeId, setNodeId] = useState(1);
    const reactFlowInstance = useRef<ReactFlowInstance | null>(null);
    const flowWrapper = useRef<HTMLDivElement>(null);

    const { createNode, updateNodesPositions } = useFlowNodes(nodes, setNodes, nodeId);

    const checkViewportAndZoom = useCallback((updatedNodes: Node[]) => {
        console.log("CHECKING ZOOM ");
        if (!reactFlowInstance.current || !flowWrapper.current) return;
        console.log("CHECKING ZOOM 2" );

        const viewport = reactFlowInstance.current.getViewport();
        const bounds = flowWrapper.current.getBoundingClientRect();

        // Check if any node is outside view
        const anyNodeOutside = updatedNodes.some(node =>
            isNodeOutsideView(node, viewport, bounds)
        );

        if (anyNodeOutside) {
            setTimeout(() => {
                reactFlowInstance.current?.fitView({
                    padding: 0.2,
                    duration: 200
                });
            }, 0);
        }
    }, []);
    const onNodeClick = useCallback((event, clickedNode) => {
        const newNode = createNode(`node_${nodeId}`);
        const newEdge = {
            id: `e${clickedNode.id}-${newNode.id}`,
            source: clickedNode.id,
            target: newNode.id,
            // type:"step"
        };

        const updatedNodes = [...nodes, newNode];
        const updatedEdges = [...edges, newEdge];

        const arrangedNodes = updateNodesPositions(updatedNodes, updatedEdges);
        setNodes(arrangedNodes);
        setEdges(updatedEdges);
        setNodeId(prev => prev + 1);

        checkViewportAndZoom(arrangedNodes);
    }, [nodes, edges, nodeId, createNode, updateNodesPositions, checkViewportAndZoom]);

    const onEdgeClick = useCallback((event, edge) => {
        // Get current siblings under the source node
        const currentSiblings = edges
            .filter(e => e.source === edge.source)
            .map(e => e.target);

        // Find the index where the clicked edge's target appears
        const targetIndex = currentSiblings.indexOf(edge.target);

        // Create new node with position hint
        const newNode = {
            ...createNode(`node_${nodeId}`),
            // Add a hint about desired position
            position: {
                x: (nodes.find(n => n.id === edge.target)?.position?.x || 0),
                y: 0  // Y will be calculated by layout algorithm
            }
        };

        const newEdges = [
            ...edges.filter(e => e.id !== edge.id),
            {
                id: `e${edge.source}-${newNode.id}`,
                source: edge.source,
                target: newNode.id,
                // type: 'step',
            },
            {
                id: `e${newNode.id}-${edge.target}`,
                source: newNode.id,
                target: edge.target,
                // type: 'step',
            }
        ];

        // Sort other edges to maintain desired order
        const updatedEdges = newEdges.map(e => {
            if (e.source === edge.source) {
                // Add metadata about desired ordering
                return {
                    ...e,
                    // Add any other properties that might help with ordering
                    data: {
                        ...e.data,
                        originalOrder: targetIndex
                    }
                };
            }
            return e;
        });

        const updatedNodes = [...nodes, newNode];
        const arrangedNodes = updateNodesPositions(updatedNodes, updatedEdges);

        setNodes(arrangedNodes);
        setEdges(updatedEdges);
        setNodeId(prev => prev + 1);

        checkViewportAndZoom(arrangedNodes);
    }, [nodes, edges, nodeId, createNode, updateNodesPositions, checkViewportAndZoom]);
    const onInit = useCallback((instance: ReactFlowInstance) => {
        reactFlowInstance.current = instance;
    }, []);

    return (

        <ReactFlowProvider>
            <div ref={flowWrapper} style={{width: '100vw', height: '100vh'}}>
                <ReactFlow
                    nodes={nodes}
                    edges={edges}
                    onNodesChange={onNodesChange}
                    onEdgesChange={onEdgesChange}
                    onNodeClick={onNodeClick}
                    onEdgeClick={onEdgeClick}
                    onInit={onInit}
                    fitView
                    fitViewOptions={FIT_VIEW_OPTIONS}
                    defaultViewport={DEFAULT_VIEWPORT}
                >
                    {/*<Controls />*/}
                    {/*<MiniMap />*/}
                    <Background variant="dots" gap={12} size={1}/>
                </ReactFlow>
            </div>
        </ReactFlowProvider>
);
}