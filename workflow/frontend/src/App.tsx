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
                        <Route path="/workflow" element={<Workflow definition={workflowDefinition}/>} />
                    </Routes>
                </DashboardLayout>
        </Router>
    );
};

const workflowDefinition = {
    nodes: [
        { "id": "A"},
        { "id": "B" },
        { "id": "C" },
        { "id": "D" },
        { "id": "E" },
        { "id": "F" }
    ],
    edges: [
        { "id": "e1", "source": "A", "target": "B" },
        { "id": "e2", "source": "B", "target": "C" },
        { "id": "e3", "source": "B", "target": "D" },
        { "id": "e4", "source": "B", "target": "E" },
        { "id": "e5", "source": "C", "target": "F" },
        { "id": "e6", "source": "D", "target": "F" }
    ]
};
export default App;
