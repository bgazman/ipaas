import axios, { AxiosResponse } from 'axios';

export interface WorkflowDefinition {
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

export interface ApiResponse<T> {
    data: T[];
    status: 'SUCCESS' | 'ERROR';
    message: string;
}

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8081/api',
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    }
});

export const api = {
    async getWorkflowDefinitions(): Promise<ApiResponse<WorkflowDefinition>> {
        const response: AxiosResponse<ApiResponse<WorkflowDefinition>> = await axiosInstance.get('/workflow/definition');
        // console.log("Got response: " + JSON.stringify(response));
        return response.data;
    },
    async deleteWorkflowDefinition(id: string): Promise<ApiResponse<WorkflowDefinition>> {
        const response: AxiosResponse<ApiResponse<WorkflowDefinition>> =
            await axiosInstance.delete(`/workflow/definition/${id}`);
        return response.data;
    },

    async addWorkflowDefinition(data: { name: string; parentId: string,domain:string,type,string }): Promise<ApiResponse<WorkflowDefinition>> {
        const response: AxiosResponse<ApiResponse<WorkflowDefinition>> =
            await axiosInstance.post('/workflow/definition', data);
        return response.data;
    }
};