import React, { useCallback, useRef, useState } from 'react';

import '@xyflow/react/dist/style.css';
import {
    ReactFlow,
    ReactFlowProvider,
    useNodesState,
    useEdgesState,
    Background, ReactFlowInstance,
} from "@xyflow/react";
import { useFlowNodes } from "./Flow/hooks/useFlowNodes"; // Assume you have a custom hook for managing nodes
import { isNodeOutsideView } from "./Flow/utils/viewport";
import {convertToReactflow} from "./Flow/utils/convertToReactflow"; // Assume you have a utility function for checking node visibility
import {calculateComplexFlowPositions,calculatePositions,calculateGridPositions} from "./Flow/utils/calculatePositions"
const FIT_VIEW_OPTIONS = {
    padding: 0.2,
};

const DEFAULT_VIEWPORT = { x: 0, y: 0, zoom: 1.5 };

// const calculateVerticalFlowPositions = (nodes, options = {}) => {
//     const {
//         startX = 250,
//         startY = 50,
//         verticalGap = 100
//     } = options;
//
//     return nodes.map((node, index) => ({
//         ...node,
//         position: {
//             x: startX,
//             y: startY + (index * verticalGap)
//         }
//     }));
// };

const WorkflowViewer = ({ workflow }) => {
    const flowWrapper = useRef(null);

    const nodesWithPositions = calculateGridPositions(workflow.nodes, workflow.edges);

    const onInit = useCallback((reactFlowInstance) => {
        reactFlowInstance.fitView(FIT_VIEW_OPTIONS);
    }, []);

    return (
        <ReactFlowProvider>
            <div ref={flowWrapper} style={{ width: "100vw", height: "100vh" }}>
                <ReactFlow
                    nodes={nodesWithPositions}
                    edges={workflow.edges}
                    onInit={onInit}
                    fitView
                    fitViewOptions={FIT_VIEW_OPTIONS}
                    defaultViewport={DEFAULT_VIEWPORT}
                >
                    <Background variant="dots" gap={12} size={1} />
                </ReactFlow>
            </div>
        </ReactFlowProvider>
    );
};


export default WorkflowViewer;
