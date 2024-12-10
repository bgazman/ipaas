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

export interface ApiResponse {
  data: WorkflowDefinition[];
  status: string;
  message: string;
}