import React, { useState } from 'react';
import { ChevronLeft, ChevronRight } from 'lucide-react'; // Make sure to import your icons
import { WorkflowLayoutProvider, useWorkflowLayout } from './context/WorkflowLayoutContext';
import WorkflowLayout from './components/WorkflowLayout';
import WorkflowSidebar from "./components/WorkflowSidebar.tsx";

const WorkflowContainer = () => {
    const [isCollapsed, setIsCollapsed] = useState(true);
    const { handleAddWorkflow, definition } = useWorkflowLayout();

    return (
        <div className="flex h-screen">
            <WorkflowSidebar onAddWorkflow={handleAddWorkflow} />
            <div className="flex-1">
                <WorkflowLayout layoutType={'horizontal'}/>
            </div>
        </div>
    );
};

const Workflow = () => {
    return (
        <WorkflowLayoutProvider>
            <WorkflowContainer />
        </WorkflowLayoutProvider>
    );
};

export default Workflow;