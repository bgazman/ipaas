import React, { useState } from 'react';
import { ChevronRight, ChevronLeft } from 'lucide-react';
import Tree from "../../../components/Tree/Tree.tsx";
import {WorkflowDefinitionProvider} from "../context/WorkflowDefinitionContext.tsx";


// const data = [
//     { id: '1', label: 'Root' },
//     { id: '2', parentId: '1', label: 'Child 1' },
//     { id: '3', parentId: '1', label: 'Child 2' }
// ];


const WorkflowSidebar: React.FC = () => {
    const [isCollapsed, setIsCollapsed] = useState(true);
    const [nodes, setNodes] = useState([
        { id: '1', label: 'Root', type: 'folder' }
    ]);

    const handleAdd = (parentId: string | null, label: string, type: 'folder' | 'file') => {
        const newNode = {
            id: crypto.randomUUID(),
            parentId,
            label,
            type
        };
        setNodes([...nodes, newNode]);
    };

    const handleOpen = (nodeId: string) => {
        // Handle file open
        console.log('Opening file:', nodeId);
    };

    const handleDelete = (nodeId: string) => {
        setNodes(nodes.filter(n => n.id !== nodeId));
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
                    <WorkflowDefinitionProvider >
                        <Tree nodes={nodes} onAdd={handleAdd} onDelete={handleDelete} />
                    </WorkflowDefinitionProvider>
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
