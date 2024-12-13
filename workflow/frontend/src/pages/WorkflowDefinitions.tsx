import React from 'react';
import WorkflowDefinitionList from "../components/WorkflowDefinitionList.jsx";
import WorkflowDefinitionService from "../api/WorkflowDefinitionService";
import { useState, useEffect } from 'react';
import WorkflowDefinitionDialog from "../components/WorkflowDefinitionDialog";
import {WorkflowDefinitionSidebar} from "../components/WorkflowDefinitionSidebar";
import WorkflowViewer from "../components/WorkflowViewer.tsx";
const WorkflowDefinitions = () => {
    const [workflows, setWorkflows] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [showDialog, setShowDialog] = useState(false); // you're using showDialog but defined isDialogOpen
    const [selectedWorkflow, setSelectedWorkflow] = useState(null);

    const handleDefinitionSelected = (workflow) => {
        setSelectedWorkflow(workflow);
        console.log("SELECTED DEFINITION: ",workflow)
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
        <div className="max-w-7xl mx-auto flex min-h-[calc(100vh-4rem)]">
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
            <div className="flex-grow h-full">
                {selectedWorkflow ? (
                    <div className="flex flex-col h-full">
                        <div className="flex-1 relative overflow-hidden">
                            {/*<WorkflowView workflow={selectedWorkflow.definition}/>*/}
                            <WorkflowViewer workflow={selectedWorkflow.definition}/>
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