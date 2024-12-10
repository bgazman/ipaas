import React, { useState } from 'react';
import { Plus } from 'lucide-react';

const WorkflowDialog = ({ onSubmit, open, onOpenChange }) => {
    const [workflowName, setWorkflowName] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit({
            id: Date.now(),
            name: workflowName,
            children: []
        });
        setWorkflowName('');
        onOpenChange(false);
    };

    if (!open) return null;

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
            <div className="bg-white p-4 rounded-lg w-96">
                <h2 className="text-lg font-bold mb-4">Add New Workflow</h2>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <input
                        type="text"
                        placeholder="Workflow Name"
                        value={workflowName}
                        onChange={(e) => setWorkflowName(e.target.value)}
                        className="w-full p-2 border rounded"
                    />
                    <button
                        type="submit"
                        className="w-full bg-blue-500 text-white p-2 rounded hover:bg-blue-600"
                    >
                        <Plus className="inline mr-2 w-4 h-4" />
                        Create Workflow
                    </button>
                </form>
            </div>
        </div>
    );
};

export default WorkflowDialog;