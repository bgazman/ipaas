import { useState, useEffect, useContext } from 'react';
import { api } from '../api/WorkflowDefinitionService.tsx';
import { WorkflowDefinition } from '../pages/WorkflowDefinition/types/workflow-types.ts';
import { TreeContext } from '../context/TreeContext';

export const useTree = () => {
    const context = useContext(TreeContext);
    if (!context) {
        throw new Error('useTree must be used within a TreeProvider');
    }
    return context;
};

export const useWorkflows = () => {
    const [workflows, setWorkflows] = useState<WorkflowDefinition[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    // const fetchWorkflows = async () => {
    //     try {
    //         const response = await workflowsDefinitions.getAll();
    //         setWorkflows(response.data);
    //     } catch (err) {
    //         setError(err instanceof Error ? err.message : 'Failed to fetch workflows');
    //     } finally {
    //         setLoading(false);
    //     }
    // };

    // useEffect(() => {
    //     fetchWorkflows();
    // }, []);

};