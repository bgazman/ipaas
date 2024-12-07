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
    steps: [
      { id: 'start', type: 'start', label: 'Start', next: 'task1' },
      { id: 'task1', type: 'task', label: 'Process Data', next: 'condition1' },
      { id: 'condition1', type: 'condition', label: 'Check Status', next: ['task2', 'task3'] },
      { id: 'task2', type: 'task', label: 'Success Path', next: 'end' },
      { id: 'task3', type: 'task', label: 'Failure Path', next: 'end' },
      { id: 'end', type: 'end', label: 'End' }
    ]
  };
export default App;
