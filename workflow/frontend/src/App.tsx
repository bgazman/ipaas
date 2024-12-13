import React from 'react';
import { Link,BrowserRouter, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Dashboard from './pages/Dashboard';
import WorkflowDefinitions from './pages/WorkflowDefinitions';
import WorkflowInstances from './pages/WorkflowInstances';

function App() {
    return (
        <BrowserRouter>
            <div className="min-h-screen bg-gray-100">
                <Navbar />
                <main  className="pt-16 h-full">
                    <Routes>
                        <Route path="/" element={<Dashboard />} />
                        <Route path="/definitions" element={<WorkflowDefinitions />} />
                        <Route path="/instances" element={<WorkflowInstances />} />
                    </Routes>
                </main>
            </div>
        </BrowserRouter>
    );
}

export default App;