import axios from "axios";

class WorkflowDefinitionsService {
    constructor() {
        this.baseUrl = "http://localhost:8081/api/workflow/definition";
    }

    // Method to fetch all workflow definitions
    async getWorkflowDefinitions() {
        try {
            const response = await axios.get(this.baseUrl);
            if (response.status === 200) {
                return response.data.data; // Assuming the relevant data is in the `data` field
            }
            throw new Error(response.data.message || "Failed to fetch workflow definitions.");
        } catch (error) {
            console.error("Error fetching workflow definitions:", error.message);
            throw error;
        }
    }

    // Method to save a new workflow definition
    async createWorkflowDefinition(workflowDefinition) {
        try {
            const payload = {
                data: workflowDefinition,
                metadata: {
                    requestId: `${Date.now()}`,
                    timestamp: new Date().toISOString(),
                },
            };

            const response = await axios.post(this.baseUrl, payload, {
                headers: {
                    "Content-Type": "application/json",
                },
            });

            if (response.status === 201 || response.status === 200) {
                return response.data; // Assuming server response contains created/updated entity details
            }
            throw new Error(response.data.message || "Failed to save workflow definition.");
        } catch (error) {
            console.error("Error saving workflow definition:", error.message);
            throw error;
        }
    }
}

export default new WorkflowDefinitionsService();
