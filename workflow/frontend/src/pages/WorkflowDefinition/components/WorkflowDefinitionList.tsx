import React, { useState } from 'react';
import WorkflowDefinitionCard from './WorkflowDefinitionCard';
import WorkflowDefinitionDialog from './WorkflowDefinitionDialog';

const WorkflowDefinitionsList = () => {
    const [workflows, setWorkflows] = useState([
        { id: '1', name: 'Workflow A', domain: 'Domain A', type: 'Type A' },
        { id: '2', name: 'Workflow B', domain: 'Domain B', type: 'Type B' },
    ]);

    const [isDialogOpen, setDialogOpen] = useState(false);
    const [isAddMode, setAddMode] = useState(false);
    const [isDeleteMode, setDeleteMode] = useState(false);
    const [selectedWorkflow, setSelectedWorkflow] = useState(null);


    // Open dialog
    const openDialog = (workflow = null, deleteMode = false) => {
        setSelectedWorkflow(workflow);
        setAddMode(!workflow && !deleteMode); // Add mode when no workflow is passed and not delete mode
        setDeleteMode(deleteMode);
        setDialogOpen(true);
    };

    // Close dialog
    const closeDialog = () => {
        setSelectedWorkflow(null);
        setDialogOpen(false);
        setAddMode(false);
        setDeleteMode(false);
    };

    // Handle add workflow
    const handleAdd = (newWorkflow) => {
        setWorkflows((prev) => [
            ...prev,
            { ...newWorkflow, id: Date.now().toString() }, // Add unique ID
        ]);
        closeDialog();
    };

    // Handle delete workflow
    const handleDelete = (workflow) => {
        openDialog(workflow, true); // Open dialog in delete mode
    };

    const confirmDelete = (id) => {
        setWorkflows((prev) => prev.filter((workflow) => workflow.id !== id));
        closeDialog();
    };

    return (
        <div className="p-4 bg-white shadow rounded-lg max-w-md mx-auto">
            {/* Header */}
            <div className="flex justify-between items-center mb-4">
                <h2 className="text-xl font-bold">Workflows</h2>
                <button
                    onClick={() => openDialog()} // Open dialog in Add Mode
                    className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition"
                >
                    Add
                </button>
            </div>

            {/* Workflow List */}
            <div className="space-y-2">
                {workflows.map((workflow) => (
                    <WorkflowDefinitionCard
                        key={workflow.id}
                        workflow={workflow}
                        onOpen={(workflow) => openDialog(workflow)} // Open dialog for view/edit
                        onDelete={(workflow) => handleDelete(workflow)} // Open dialog in delete mode
                    />
                ))}
            </div>

            {/* Workflow Definition Dialog */}
            <WorkflowDefinitionDialog
                open={isDialogOpen}
                workflow={selectedWorkflow}
                isAddMode={isAddMode}
                disableFields={isDeleteMode} // Disable fields in delete mode
                onClose={closeDialog}
                onDelete={(id) => confirmDelete(id)}
                onAdd={(newWorkflow) => handleAdd(newWorkflow)} // Handle add functionality
            />
        </div>


)
    ;
};

export default WorkflowDefinitionsList;
