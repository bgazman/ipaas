import React from 'react';
import WorkflowDefinitionCard from './WorkflowDefinitionCard'; // Adjust import as necessary

const WorkflowDefinitionList = ({ workflows, loading, error, onDefinitionSelected }) => {
    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error}</div>;

    return (
        <div className="mx-auto p-0">
            {/* Your list items go here */}
            {
                workflows.map(workflow => (
                    <WorkflowDefinitionCard
                        key={workflow.id}
                        workflow={workflow}
                        onClick={onDefinitionSelected} // Pass the click handler
                    />
                ))}
        </div>
    );
};

export default WorkflowDefinitionList;
