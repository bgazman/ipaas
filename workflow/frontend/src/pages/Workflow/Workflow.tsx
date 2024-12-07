import React from 'react';
import WorkflowLayout from './components/WorkflowLayout.tsx';

interface WorkflowProps {
    definition: {
        steps: Array<{
            id: string;
            type: string;
            label: string;
            next?: string | string[];
        }>;
    };
}

const Workflow: React.FC<WorkflowProps> = ({ definition }) => {
    return (
        <div className="h-screen w-full">
            <WorkflowLayout definition={definition} />
        </div>
    );
};
export default Workflow;