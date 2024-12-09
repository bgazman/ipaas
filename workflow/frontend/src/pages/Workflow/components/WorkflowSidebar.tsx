import React, { useState } from 'react';
import { ChevronRight, ChevronLeft } from 'lucide-react';
import Tree from "../../../components/Tree/Tree.tsx";
import {TreeProvider} from "../../../context/TreeContext.tsx";




const WorkflowSidebar: React.FC = () => {
    const [isCollapsed, setIsCollapsed] = useState(true);

    const initialData = {
        id: "root",
        label: "Workflows",
        type: "folder",
        children: [
            {
                id: "1",
                label: "Workflow 1",
                type: "folder",
                children: [
                    { id: "1-1", label: "Task 1", type: "file" },
                    { id: "1-2", label: "Task 2", type: "file" },
                ],
            },
            {
                id: "2",
                label: "Workflow 2",
                type: "folder",
                children: [
                    { id: "2-1", label: "Task 1", type: "file" }
                ],
            },
        ],
    };

    return (
        <div className="flex h-full">
            <div
                className={`
                    transition-all duration-300 ease-in-out
                    ${isCollapsed ? 'w-0' : 'w-64'}
                    bg-white border-r
                    overflow-hidden
                `}
            >
                <div className="h-full w-64"> {/* Container with fixed width */}
                    <TreeProvider initialData={initialData}>
                        <Tree />
                    </TreeProvider>
                </div>
            </div>
            <div
                className={`
                    transition-all duration-300
                    ${isCollapsed ? 'border-l rounded-l-md' : ''}
                `}
            >
                <button
                    onClick={() => setIsCollapsed(!isCollapsed)}
                    className="h-8 w-8 flex items-center justify-center hover:bg-gray-100 rounded-r"
                >
                    {isCollapsed ? <ChevronRight size={20} /> : <ChevronLeft size={20} />}
                </button>
            </div>
        </div>
    );
};

export default WorkflowSidebar;
