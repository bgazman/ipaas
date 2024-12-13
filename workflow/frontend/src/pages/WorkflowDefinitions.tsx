import React from 'react';
import WorkflowDefinitionList from "../components/WorkflowDefinitionList.jsx";
import WorkflowDefinitionService from "../api/WorkflowDefinitionService";
import { useState, useEffect } from 'react';
import WorkflowDefinitionDialog from "../components/WorkflowDefinitionDialog";
import {WorkflowDefinitionSidebar} from "../components/WorkflowDefinitionSidebar";
import WorkflowFlowView from "../components/WorkflowView";
import WorkflowView from "../components/WorkflowView";
const WorkflowDefinitions = () => {
    const [workflows, setWorkflows] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [showDialog, setShowDialog] = useState(false); // you're using showDialog but defined isDialogOpen
    const [selectedWorkflow, setSelectedWorkflow] = useState(null);

    const handleDefinitionSelected = (workflow) => {
        setSelectedWorkflow(workflow);
    };


    const handleCreate = async (data) => {
        try {
            console.log("Handling Create")
            await WorkflowDefinitionService.createWorkflowDefinition(data);
            await fetchWorkflowDefinitions();
            setShowDialog(false);
        } catch (error) {
            console.error("Failed to create workflow:", error);
        }
    };
    const fetchWorkflowDefinitions = async () => {
        setLoading(true);
        try {
            const definitions = await WorkflowDefinitionService.getWorkflowDefinitions();
            setWorkflows(definitions);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchWorkflowDefinitions();
    }, []);
    return (
        <div className="flex h-[calc(100vh-4rem)] pt-16"> {/* Adjusted height and added padding-top */}
            <WorkflowDefinitionSidebar onCreateClick={() => setShowDialog(true)}>
                <WorkflowDefinitionList
                    workflows={workflows}
                    loading={loading}
                    error={error}
                    onDefinitionSelected={handleDefinitionSelected}
                />
            </WorkflowDefinitionSidebar>
            <WorkflowDefinitionDialog
                open={showDialog}
                onClose={() => setShowDialog(false)}
                onSave={handleCreate}
            />
            {/* Container for the main content */}
            <div className="flex-1 flex flex-col overflow-hidden">
                {selectedWorkflow ? (
                    <div className="flex flex-col h-full">
                        <div className="flex-1 relative overflow-hidden">
                            <WorkflowView workflow={selectedWorkflow.definition}/>
                        </div>
                    </div>
                ) : (
                    <div className="flex items-center justify-center h-full">
                        <p>Select a workflow to view details</p>
                    </div>
                )}
            </div>
        </div>
    );
};

export default WorkflowDefinitions;