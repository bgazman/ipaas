import React, { useState } from 'react';
import {ChevronLeft, ChevronRight, ChevronDown, FolderIcon, FileIcon, Plus} from 'lucide-react';
import {workflow_definition_list} from '../data/SampleData.ts'
import WorkflowDialog from "./WorkflowDialog.tsx";

interface ItemType {
    id: string;
    name: string;
    children?: ItemType[];
}

interface TreeItemProps {
    item: ItemType;
    depth: number;
    key?: React.Key;
}

const TreeItem: React.FC<TreeItemProps> = ({ item, depth = 0 }) => {
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

const WorkflowSidebar = () => {
    const [isCollapsed, setIsCollapsed] = useState(true);
    const [workflows, setWorkflows] = useState(workflow_definition_list);
    const [dialogOpen, setDialogOpen] = useState(false);

    const handleAddWorkflow = (newWorkflow) => {
        setWorkflows([...workflows, newWorkflow]);
    };

    return (
        <div className="relative">
            <div className={`h-full bg-white border-r transition-all duration-300 ease-in-out
                ${isCollapsed ? 'w-8' : 'w-64'}`}>
                <button
                    onClick={() => setIsCollapsed(!isCollapsed)}
                    className="absolute left-0 top-3 p-1 rounded-r bg-gray-100 hover:bg-gray-200"
                >
                    {isCollapsed ? <ChevronRight className="w-4 h-4"/> : <ChevronLeft className="w-4 h-4"/>}
                </button>

                {!isCollapsed && (
                    <>
                        <button
                            onClick={() => setDialogOpen(true)}
                            className="absolute top-3 right-3 p-1 rounded bg-blue-500 hover:bg-blue-600 text-white"
                        >
                            <Plus className="w-4 h-4"/>
                        </button>

                        <div className="mt-12">
                            {workflows.map((item) => (
                                <TreeItem key={item.id} item={item}/>
                            ))}
                        </div>
                    </>
                )}
            </div>

            {dialogOpen && (
                <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
                    <div className="bg-white p-4 rounded-lg w-96">
                        <h2 className="text-lg font-bold mb-4">Add New Workflow</h2>
                        <form onSubmit={(e) => {
                            e.preventDefault();
                            const name = e.target.workflow.value;
                            handleAddWorkflow({
                                id: Date.now(),
                                name,
                                children: []
                            });
                            setDialogOpen(false);
                        }}>
                            <input
                                name="workflow"
                                type="text"
                                placeholder="Workflow Name"
                                className="w-full p-2 border rounded mb-4"
                            />
                            <div className="flex justify-end gap-2">
                                <button
                                    type="button"
                                    onClick={() => setDialogOpen(false)}
                                    className="px-4 py-2 border rounded"
                                >
                                    Cancel
                                </button>
                                <button
                                    type="submit"
                                    className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                                >
                                    Create
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
};

export default WorkflowSidebar;