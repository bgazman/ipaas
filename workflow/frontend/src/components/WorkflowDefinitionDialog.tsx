import React from 'react';
import WorkflowDefinitionForm from "./WorkflowDefinitionForm";
import axios from "axios";
import  handleSave  from '../pages/WorkflowDefinitions';

const WorkflowDefinitionDialog = ({ onClose, onSave, open }) => {
    if (!open) return null;
    return (
        <div style={{
            position: 'fixed',
            top: 0,
            left: 0,
            right: 0,
            bottom: 0,
            backgroundColor: 'rgba(0,0,0,0.5)',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            zIndex: 1000
        }}>
            <div style={{
                backgroundColor: 'white',
                padding: '20px',
                borderRadius: '8px',
                width: '500px'
            }}>
                <h2>Create Workflow Definition</h2>
                <WorkflowDefinitionForm
                    onSave={onSave}
                    onClose={onClose}
                />
            </div>
        </div>
    );
};

export default WorkflowDefinitionDialog;