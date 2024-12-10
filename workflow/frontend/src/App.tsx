import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import DashboardLayout from './layouts/DashboardLayout';
import Home from './pages/Home';
import Workflow from './pages/Workflow/Workflow.tsx';
const App: React.FC = () => {
    return (
        <Router>
                <DashboardLayout>
                    <Routes>
                        <Route path="/" element={<Home />} />
                        <Route path="/workflow" element={<Workflow />} />
                    </Routes>
                </DashboardLayout>
        </Router>
    );
};

// const workflowDefinitionInterface = {
//     nodes: [
//         { id: "Start", type: "trigger", status: "ready" },
//         { id: "Input_Validation", type: "validator", status: "pending" },
//         { id: "Data_Enrichment", type: "processor", status: "idle" },
//         { id: "Business_Rules", type: "validator", status: "idle" },
//         { id: "Format_Check", type: "validator", status: "idle" },
//         { id: "Data_Transform", type: "processor", status: "idle" },
//         { id: "API_Call", type: "action", status: "idle" },
//         { id: "Cache_Check", type: "condition", status: "idle" },
//         { id: "Database_Write", type: "action", status: "idle" },
//         { id: "Error_Handler", type: "error", status: "idle" },
//         { id: "Retry_Logic", type: "processor", status: "idle" },
//         { id: "Email_Notify", type: "notification", status: "idle" },
//         { id: "Slack_Notify", type: "notification", status: "idle" },
//         { id: "Audit_Log", type: "logger", status: "idle" },
//         { id: "End", type: "terminal", status: "idle" }
//     ],
//     edges: [
//         { id: "e1", source: "Start", target: "Input_Validation", type: "default" },
//         { id: "e2", source: "Input_Validation", target: "Data_Enrichment", type: "success" },
//         { id: "e3", source: "Input_Validation", target: "Error_Handler", type: "error" },
//         { id: "e4", source: "Data_Enrichment", target: "Business_Rules", type: "default" },
//         { id: "e5", source: "Business_Rules", target: "Format_Check", type: "success" },
//         { id: "e6", source: "Business_Rules", target: "Error_Handler", type: "error" },
//         { id: "e7", source: "Format_Check", target: "Data_Transform", type: "success" },
//         { id: "e8", source: "Format_Check", target: "Retry_Logic", type: "warning" },
//         { id: "e9", source: "Data_Transform", target: "API_Call", type: "default" },
//         { id: "e10", source: "API_Call", target: "Cache_Check", type: "success" },
//         { id: "e11", source: "API_Call", target: "Error_Handler", type: "error" },
//         { id: "e12", source: "Cache_Check", target: "Database_Write", type: "false" },
//         { id: "e13", source: "Cache_Check", target: "Email_Notify", type: "true" },
//         { id: "e14", source: "Database_Write", target: "Audit_Log", type: "default" },
//         { id: "e15", source: "Retry_Logic", target: "Data_Transform", type: "retry" },
//         { id: "e16", source: "Retry_Logic", target: "Error_Handler", type: "max_retries" },
//         { id: "e17", source: "Error_Handler", target: "Slack_Notify", type: "default" },
//         { id: "e18", source: "Email_Notify", target: "End", type: "default" },
//         { id: "e19", source: "Slack_Notify", target: "End", type: "default" },
//         { id: "e20", source: "Audit_Log", target: "End", type: "default" }
//     ]
// };
export default App;
