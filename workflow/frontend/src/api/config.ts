
import axios from 'axios';




// const axiosInstance = axios.create({
//     baseURL: import.meta.env.VITE_API_URL || '/api',
//     headers: {
//         'Content-Type': 'application/json',
//         'Authorization': `Bearer ${import.meta.env.VITE_API_TOKEN}`,
//         'Accept': 'application/json'
//     }
// });

// // Add request interceptor for dynamic headers
// axiosInstance.interceptors.request.use(
//     (config) => {
//         const token = localStorage.getItem('token');
//         if (token) {
//             config.headers.Authorization = `Bearer ${token}`;
//         }
//         return config;
//     },
//     (error) => {
//         return Promise.reject(error);
//     }
// );

// export const api = {
//     async getWorkflowDefinitions(): Promise<ApiResponse> {
//         const response = await axiosInstance.get<ApiResponse>('/workflow-definitions');
//         return response.data;
//     },
//
//     async createWorkflowDefinition(workflow: Omit<WorkflowDefinition, 'id' | 'createdAt' | 'updatedAt'>): Promise<ApiResponse> {
//         const response = await axiosInstance.post<ApiResponse>('/workflow-definitions', workflow);
//         return response.data;
//     },
//
//     async deleteWorkflowDefinition(id: string): Promise<ApiResponse> {
//         const response = await axiosInstance.delete<ApiResponse>(`/workflow-definitions/${id}`);
//         return response.data;
//     }
// };