import React from 'react';
import ReactFlow, { MiniMap, Controls } from 'react-flow-renderer';
import 'reactflow/dist/style.css';
import {convertToReactFlow} from "../utils/ReactflowUtils.jsx";

const WorkflowFlowView = ({ workflow }) => {
    console.log("IN WORKFLOW VIEW " ,workflow);
    console.log('Workflow before parsing:', workflow);
    if (!workflow) return null;

    const parsedWorkflow = typeof workflow === 'string' ? JSON.parse(workflow) : workflow;
    const { nodes, edges } = convertToReactFlow(parsedWorkflow);
    // const { nodes, edges } = convertToReactFlow(workflow);



    return (
        <div className="absolute inset-10 p-10">
            <div className="w-full h-full shadow-lg">
                <ReactFlow
                    nodes={workflow.nodes}
                    edges={workflow.edges}
                    fitView
                    // noWheelClassName="scrollable"
                >
                    <Controls/>
                    <MiniMap/>
                </ReactFlow>
            </div>
        </div>

    );
};
export default WorkflowFlowView;


