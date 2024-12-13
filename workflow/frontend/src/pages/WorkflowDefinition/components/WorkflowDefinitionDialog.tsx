// src/WorkflowDefinitionDialog.tsx
import React, { useState } from 'react';
import WorkflowDefinitionForm from './WorkflowDefinitionForm';

const WorkflowDefinitionDialog: React.FC = () => {
    const [isOpen, setIsOpen] = useState(false);

    const openDialog = () => {
        setIsOpen(true);
    };

    const closeDialog = () => {
        setIsOpen(false);
    };

    return (
        <div className="relative">
            <button
                onClick={openDialog}
                className="py-2 px-4 bg-blue-600 text-white font-semibold rounded-md hover:bg-blue-700 transition duration-200"
            >
                Open Workflow Definition Form
            </button>

            {isOpen && (
                <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
                    <div className="bg-white rounded-lg shadow-lg p-6 max-w-sm w-full mx-4">
                        <div className="flex justify-between items-center mb-4">
                            <h2 className="text-xl font-semibold">Workflow Definition</h2>
                            <button
                                onClick={closeDialog}
                                className="text-gray-500 hover:text-gray-700"
                                aria-label="Close"
                            >
                                &times;
                            </button>
                        </div>
                        <WorkflowDefinitionForm />
                        <div className="mt-4 flex justify-end">
                            <button
                                onClick={closeDialog}
                                className="py-2 px-4 bg-red-600 text-white font-semibold rounded-md hover:bg-red-700 transition duration-200"
                            >
                                Close
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default WorkflowDefinitionDialog;
