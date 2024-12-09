import React, { useState } from 'react';
import {ChevronLeft, ChevronRight, ChevronDown, FolderIcon, FileIcon, Plus} from 'lucide-react';
import {useWorkflowLayout} from "../context/WorkflowLayoutContext.tsx";

const TreeItem = ({ item, depth = 0 }) => {
    const [isOpen, setIsOpen] = useState(false);

    return (
        <div className="select-none">
            <div
                className="flex items-center gap-2 px-2 py-1.5 hover:bg-gray-100 cursor-pointer"
                style={{ paddingLeft: `${depth * 12 + 8}px` }}
                onClick={() => item.children && setIsOpen(!isOpen)}
            >
                {item.children ? (
                    <>
                        <ChevronDown className={`w-4 h-4 transition-transform ${isOpen ? 'rotate-0' : '-rotate-90'}`} />
                        <FolderIcon className="w-4 h-4" />
                    </>
                ) : (
                    <>
                        <span className="w-4" />
                        <FileIcon className="w-4 h-4" />
                    </>
                )}
                <span className="text-sm">{item.name}</span>
            </div>

            {isOpen && item.children?.map((child) => (
                <TreeItem key={child.id} item={child} depth={depth + 1} />
            ))}
        </div>
    );
};

const WorkflowSidebar = ({ onAddWorkflow }) => {
    const [isCollapsed, setIsCollapsed] = useState(true);
    const { handleAddWorkflow } = useWorkflowLayout();
    const dummyData = [
        {
            id: '1',
            name: "Production Workflows",
            children: [
                { id: '1-1', name: "Order Processing" },
                { id: '1-2', name: "Inventory Management" },
                {
                    id: '1-3',
                    name: "Shipping",
                    children: [
                        { id: '1-3-1', name: "Domestic" },
                        { id: '1-3-2', name: "International" }
                    ]
                }
            ]
        },
        {
            id: '2',
            name: "HR Workflows",
            children: [
                { id: '2-1', name: "Onboarding" },
                { id: '2-2', name: "Leave Requests" }
            ]
        }
    ];

    return (
        <div className={`
      h-full bg-white border-r
      transition-all duration-300 ease-in-out
      ${isCollapsed ? 'w-8' : 'w-64'}
      relative
    `}>
            <div className="flex justify-between items-center">
                <button
                    onClick={() => setIsCollapsed(!isCollapsed)}
                    className="sticky top-3 p-1 rounded-r bg-gray-100 hover:bg-gray-200"
                >
                    {isCollapsed ?
                        <ChevronRight className="w-4 h-4"/> :
                        <ChevronLeft className="w-4 h-4"/>}
                </button>

                {!isCollapsed && (
                    <button
                        onClick={onAddWorkflow}
                        className="absolute top-3 right-3 p-1 rounded bg-blue-500 hover:bg-blue-600 text-white"
                    >
                        <Plus className="w-4 h-4" />
                    </button>
                )}
            </div>

            {!isCollapsed && (
                <div className="mt-12">
                    {dummyData.map((item) => (
                        <TreeItem key={item.id} item={item} />
                    ))}
                </div>
            )}
        </div>
    );
};

export default WorkflowSidebar;