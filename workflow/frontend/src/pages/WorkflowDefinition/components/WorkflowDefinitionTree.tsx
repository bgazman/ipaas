import React, { useState, FC } from 'react';
import { File, FolderOpen, Plus, Trash, FolderPlus } from 'lucide-react';
import WorkflowDefinitionDialog from "./WorkflowDefinitionDialog.tsx";
interface TreeNode {
    id: string;
    label: string;
    type: 'file' | 'folder';
    parentId: string | null;
    domain?: string;
    version?: string;
    active?: boolean;
    children?: TreeNode[];
}
interface TreeProps {
    nodes: TreeNode[];
    onAdd?: (parentId: string ,name :string, label: string, type: string) => void;
    onDelete?: (id: string) => void;
    onOpen?: (id: string) => void;
}





export const WorkflowDefinitionTree: FC<TreeProps> = ({ nodes, onAdd, onDelete, onOpen }) => {
    const [expanded, setExpanded] = useState(new Set<string>());
    const [dialogOpen, setDialogOpen] = useState(false);
    const [selectedNode, setSelectedNode] = useState<TreeNode | null>(null);


    const organizeByDomain = (nodes) => {
        const domains = new Map();

        // Create domain folders
        nodes.forEach(node => {
            if (!node.domain) return;
            if (!domains.has(node.domain)) {
                domains.set(node.domain, {
                    id: node.domain,
                    label: node.domain,
                    type: 'folder',
                    parentId: null,
                    children: []
                });
            }
        });

        // Add files to domains
        nodes.forEach(node => {
            if (!node.domain) return;
            const domainNode = domains.get(node.domain);
            if (domainNode) {
                domainNode.children.push({
                    ...node,
                    parentId: domainNode.id
                });
            }
        });

        return Array.from(domains.values());
    };

    const handleAdd = (data: { name: string; domain: string; parentId: string ,type: string }) => {
        // Convert dialog data to match expected onAdd signature
        onAdd?.(data.domain, data.name, data.parentId , data.type);
        setDialogOpen(false);
        setSelectedNode(null);
    };

    const renderNode = (node: TreeNode) => {
        const hasChildren = node.children?.length > 0;
        const isExpanded = expanded.has(node.id);

        return (
            <div key={node.id} className="select-none">
                <div className="flex items-center gap-2 px-2 py-1 hover:bg-gray-100 group">
                    {node.type === 'folder' && (
                        <span
                            onClick={() => {
                                const newExpanded = new Set(expanded);
                                if (isExpanded) {
                                    newExpanded.delete(node.id);
                                } else {
                                    newExpanded.add(node.id);
                                }
                                setExpanded(newExpanded);
                            }}
                            className="w-4 text-gray-500 cursor-pointer"
                        >
              {hasChildren ? (isExpanded ? '▼' : '►') : ''}
            </span>
                    )}

                    {node.type === 'file' ? (
                        <File size={14} className="text-gray-500"/>
                    ) : (
                        <FolderOpen size={14} className="text-gray-500"/>
                    )}

                    <span className="flex-1 cursor-pointer">
            {node.label}
                        {node.version && <span className="text-xs text-gray-500 ml-2">v{node.version}</span>}
          </span>

                    <div className="opacity-0 group-hover:opacity-100 flex items-center gap-1">
                        {node.type === 'folder' && (
                            <button
                                onClick={() => {
                                    setSelectedNode(node);
                                    setDialogOpen(true);
                                }}
                                className="p-1 hover:bg-gray-200 rounded"
                            >
                                <Plus size={14}/>
                            </button>
                        )}
                        {node.type === 'file' && (
                            <>
                                <button
                                    onClick={() => onOpen?.(node.id)}
                                    className="p-1 hover:bg-gray-200 rounded text-blue-500"
                                >
                                    <File size={14}/>
                                </button>
                                <button
                                    onClick={() => onDelete?.(node.id)}
                                    className="p-1 hover:bg-gray-200 rounded text-red-500"
                                >
                                    <Trash size={14}/>
                                </button>
                            </>
                        )}
                    </div>
                </div>

                {node.type === 'folder' && hasChildren && isExpanded && (
                    <div className="ml-4 border-l border-gray-200">
                        {node.children.map(renderNode)}
                    </div>
                )}
            </div>
        );
    };

    const organizedNodes = organizeByDomain(nodes);

    return (
        <div className="p-2">
            <div className="flex justify-end mb-2">
                <button
                    onClick={() => {
                        setSelectedNode(null);
                        setDialogOpen(true);
                    }}
                    className="p-1 hover:bg-gray-200 rounded"
                >
                    <FolderPlus size={14}/>
                </button>
            </div>
            {organizedNodes.map(renderNode)}
            <WorkflowDefinitionDialog
                open={dialogOpen}
                onOpenChange={setDialogOpen}
                onSubmit={handleAdd}
                parentNode={selectedNode ? {id: selectedNode.id, domain: selectedNode.label} : null}
                title={`Add New ${selectedNode ? 'Workflow' : 'Domain'}`}
            />
        </div>
    );
};
export default WorkflowDefinitionTree;