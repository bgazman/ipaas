import React from 'react';
import { Link,BrowserRouter, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Dashboard from './pages/Dashboard';
import Projects from './pages/WorkflowDefinitions';
import Settings from './pages/WorkflowInstances';

function App() {
    return (
        <BrowserRouter>
            <div className="min-h-screen bg-gray-100">
                <Navbar />
                <main className="pt-16 max-w-7xl mx-auto">
                    <Routes>
                        <Route path="/" element={<Dashboard />} />
                        <Route path="/definitions" element={<Projects />} />
                        <Route path="/instances" element={<Settings />} />
                    </Routes>
                </main>
            </div>
        </BrowserRouter>
    );
}

export default App;