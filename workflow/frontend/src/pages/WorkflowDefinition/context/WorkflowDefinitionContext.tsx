// pages/Workflow/context/WorkflowDefinitionContext.tsx
import {createContext, useState, useEffect, useContext} from 'react';
import axios from 'axios';

interface WorkflowDefinition {
  id: string;
  domain: string;
  name: string;
  type: string;
  version: string;
  definition: string;
  active: boolean;
  createdAt: string;
  updatedAt: string;
}

interface ApiResponse {
  data: WorkflowDefinition[];
  status: string;
  message: string;
}

interface WorkflowDefinitionContextType {
  workflows: WorkflowDefinition[];
  loading: boolean;
  error: string | null;
  fetchWorkflows: () => Promise<void>;
}

export const WorkflowDefinitionContext = createContext<WorkflowDefinitionContextType | null>(null);

export const WorkflowDefinitionProvider = ({ children }: { children: React.ReactNode }) => {
  const [workflows, setWorkflows] = useState<WorkflowDefinition[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const fetchWorkflows = async () => {
    try {
      setLoading(true);
      const response = await axios.get<ApiResponse>('/api/workflows');
      setWorkflows(response.data.data);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to fetch workflows');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchWorkflows();
  }, []);

  return (
      <WorkflowDefinitionContext.Provider value={{ workflows, loading, error, fetchWorkflows }}>
        {children}
      </WorkflowDefinitionContext.Provider>
  );
};

export const useWorkflowDefinition = () => {
  const context = useContext(WorkflowDefinitionContext);
  if (!context) {
    throw new Error('useWorkflowDefinition must be used within a WorkflowDefinitionProvider');
  }
  return context;
};