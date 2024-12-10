import { api } from './config';
import { ApiResponse } from './types';

export const workflowsDefinitions = {
    getAll: async () => {
        const response = await api.get<ApiResponse>('/api/workflow-definitions');
        return response.data;
    }
};