import React, { useState, useEffect } from 'react';
import { ChevronLeft, ChevronRight } from 'lucide-react';
import {api} from "../../../api/WorkflowDefinitionApi";
import {ApiResponse, WorkflowDefinition} from "../../../api/WorkflowDefinitionApi";
import {WorkflowDefinitionProvider} from "../context/WorkflowDefinitionTreeContext.tsx";


const useWorkflowDefinitions = () => {
    const [nodes, setNodes] = useState<WorkflowDefinition[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const fetchWorkflows = async () => {
        try {
            const response: ApiResponse = await api.getWorkflowDefinitions();
            if (response.status === 'SUCCESS') {
                const treeNodes = response.data.map(workflowDefinition => ({
                    id: workflowDefinition.id,
                    label: workflowDefinition.name,
                    type: workflowDefinition.type,
                    parentId: workflowDefinition.parentId,
                    domain: workflowDefinition.domain,
                    version: workflowDefinition.version,
                    active: workflowDefinition.active
                }));
                console.log('Mapped tree nodes:', treeNodes);


                setNodes(treeNodes);
            } else {
                setError(response.message);
            }
        } catch (error) {
            setError(error instanceof Error ? error.message : 'Failed to fetch workflows');
        } finally {
            setLoading(false);
        }
    };


    interface WorkflowDefinitionRequest {
        name: string;
        parentId: string;
        type: string;
        domain: string;
        version: string;
        definition: string;
    }

    interface ApiRequest<T> {
        data: T;
        metadata: {
            requestId: string;
            timestamp: string;
        };
    }

    const addWorkflowDefinition = async ( domain:string, name: string,) => {
        try {
            const workflowData: WorkflowDefinitionRequest = {
                name: name,
                parentId: 'dummy',
                type:'basic',
                domain:domain,// Assuming this is constant or derived from somewhere
                version: '1.0.0',   // Assuming this is constant or derived from somewhere
                definition: ''      // Empty definition for new workflow
            };

            const requestData: ApiRequest<WorkflowDefinitionRequest> = {
                data: workflowData,
                metadata: {
                    requestId: Date.now().toString(), // Generate a unique requestId
                    timestamp: new Date().toISOString()
                }
            };

            const response: FullApiResponse<WorkflowDefinition> = await api.addWorkflowDefinition(requestData);

            if (response.status !== 'SUCCESS') {
                throw new Error(response.message || 'Failed to add workflow definition');
            }

            const newWorkflow = response.data;

            setNodes(prev => [...prev, {
                id: newWorkflow.id,
                label: newWorkflow.label,
                name: newWorkflow.name,
                type: newWorkflow.type,
                domain: newWorkflow.domain,
                parentId: newWorkflow.parentId
            }]);

        } catch (err) {
            setError(err instanceof Error ? err.message : 'Failed to add workflow definition');
        }
    };



    const deleteWorkflowDefinition = async (nodeId: string) => {
        try {
            await api.deleteWorkflowDefinition(nodeId);
            setNodes(prev => prev.filter(node => node.id !== nodeId));
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Failed to delete workflow');
        }
    };



    useEffect(() => {
        fetchWorkflows();
    }, []);

    return { nodes, loading, error, deleteWorkflowDefinition,addWorkflowDefinition };
};
const WorkflowDefinitionSidebar = () => {
    const [isCollapsed, setIsCollapsed] = useState(true);
    const { nodes, loading, error, addWorkflowDefinition, deleteWorkflowDefinition } = useWorkflowDefinitions();
    console.log('Nodes passed to Tree component:', nodes);

    const handleAdd = ( domain: string,name: string,) => {
        addWorkflowDefinition(domain,name); // Changed 'default' to 'file'
    };

    return (
        <div className="flex h-full">
            <div className={`transition-all duration-300 ease-in-out ${isCollapsed ? 'w-0' : 'w-64'} bg-white border-r overflow-hidden`}>
                <div className="h-full w-64">
                    <WorkflowDefinitionProvider>

                    </WorkflowDefinitionProvider>
                </div>
            </div>
            <div className={`transition-all duration-300 ${isCollapsed ? 'border-l rounded-l-md' : ''}`}>
                <button
                    onClick={() => setIsCollapsed(!isCollapsed)}
                    className="h-8 w-8 flex items-center justify-center hover:bg-gray-100 rounded-r"
                >
                    {isCollapsed ? <ChevronRight size={20} /> : <ChevronLeft size={20} />}
                </button>
            </div>
        </div>
    );
};

export default WorkflowDefinitionSidebar;