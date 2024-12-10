import {useEffect, useState} from "react";
import {TreeManager} from "./TreeManager.tsx";
import {TreeNode} from "./types.ts";
import {FolderOpen, Plus, Trash,File} from "lucide-react";
interface TreeNode {
    id: string;
    parentId?: string | null;
    children?: TreeNode[];
    label: string;
}



interface DialogProps {
    isOpen: boolean;
    onClose: () => void;
    onConfirm: (value: string) => void;
    title: string;
}

interface DialogProps {
    isOpen: boolean;
    onClose: () => void;
    onConfirm: (value: string, type: 'folder' | 'file') => void;
    title: string;
}

const Dialog: React.FC<DialogProps> = ({ isOpen, onClose, onConfirm, title }) => {
    const [value, setValue] = useState('');
    const [type, setType] = useState<'folder' | 'file'>('folder');

    const handleConfirm = (e: React.FormEvent) => {
        e.preventDefault();
        if (!value.trim()) return;
        onConfirm(value.trim(), type);
        setValue('');
        setType('folder');
        onClose();
    };

    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
            <form onSubmit={handleConfirm} className="bg-white rounded-lg p-6 w-96">
                <h2 className="text-lg font-semibold mb-4">{title}</h2>
                <input
                    type="text"
                    value={value}
                    onChange={(e) => setValue(e.target.value)}
                    className="w-full p-2 border rounded mb-4"
                    placeholder="Enter name"
                    autoFocus
                />
                <div className="flex gap-4 mb-4">
                    <label className="flex items-center gap-2">
                        <input
                            type="radio"
                            checked={type === 'folder'}
                            onChange={() => setType('folder')}
                        />
                        Folder
                    </label>
                    <label className="flex items-center gap-2">
                        <input
                            type="radio"
                            checked={type === 'file'}
                            onChange={() => setType('file')}
                        />
                        File
                    </label>
                </div>
                <div className="flex justify-end gap-2">
                    <button type="button" onClick={onClose} className="px-4 py-2 border rounded hover:bg-gray-100">
                        Cancel
                    </button>
                    <button type="submit" className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600">
                        Confirm
                    </button>
                </div>
            </form>
        </div>
    );
};

interface TreeProps {
    nodes: TreeNode[];
    onAdd?: (parentId: string | null, label: string, type: 'folder' | 'file') => void;
    onDelete?: (nodeId: string) => void;
    onOpen?: (nodeId: string) => void;
}

export const Tree: React.FC<TreeProps> = ({ nodes, onAdd, onDelete, onOpen }) => {
    const [manager] = useState(() => new TreeManager());
    const [expanded, setExpanded] = useState<Set<string>>(new Set());
    const [dialog, setDialog] = useState<{ isOpen: boolean; parentId: string | null }>({
        isOpen: false,
        parentId: null
    });

    useEffect(() => {
        manager.setData(nodes);
    }, [nodes]);

    useEffect(() => {
        nodes.forEach(node => {
            if (node.parentId) {
                setExpanded(prev => new Set([...prev, node.parentId!]));
            }
        });
    }, [nodes]);

    const toggleNode = (nodeId: string) => {
        setExpanded(prev => {
            const next = new Set(prev);
            if (next.has(nodeId)) {
                next.delete(nodeId);
            } else {
                next.add(nodeId);
            }
            return next;
        });
    };

    const handleAdd = (value: string, type: 'folder' | 'file') => {
        onAdd?.(dialog.parentId, value, type);
    };

    const renderNode = (node: TreeNode, parentPath: string = '', isRoot = false) => {
        const nodePath = `${parentPath}-${node.id}`;
        const isExpanded = expanded.has(node.id);
        const hasChildren = node.children && node.children.length > 0;

        return (
            <div key={nodePath} className="select-none">
                <div className="flex items-center gap-2 px-2 py-1 hover:bg-gray-100 group">
                    {node.type === 'folder' && (
                        <span
                            onClick={() => toggleNode(node.id)}
                            className="w-4 text-gray-500 cursor-pointer"
                        >
              {hasChildren && (isExpanded ? '▼' : '►')}
            </span>
                    )}
                    {!isRoot && (
                        <>
                            {node.type === 'file' ?
                                <File size={14} className="text-gray-500" /> :
                                <FolderOpen size={14} className="text-gray-500" />
                            }
                            <span
                                className="flex-1 cursor-pointer"
                                onClick={() => node.type === 'file' && onOpen?.(node.id)}
                            >
                {node.label}
              </span>
                        </>
                    )}
                    <div className="opacity-0 group-hover:opacity-100 flex items-center gap-1">
                        {(isRoot || node.type === 'folder') && (
                            <button
                                onClick={() => setDialog({ isOpen: true, parentId: node.id })}
                                className="p-1 hover:bg-gray-200 rounded"
                            >
                                <Plus size={14} />
                            </button>
                        )}
                        {!isRoot && (
                            <button
                                onClick={() => onDelete?.(node.id)}
                                className="p-1 hover:bg-gray-200 rounded text-red-500"
                            >
                                <Trash size={14} />
                            </button>
                        )}
                    </div>
                </div>
                {hasChildren && isExpanded && (
                    <div className="ml-4 border-l border-gray-200">
                        {node.children.map(child => renderNode(child, nodePath))}
                    </div>
                )}
            </div>
        );
    };

    return (
        <div className="p-2">
            {manager.getRootNodes().map(node => renderNode(node, '', true))}
            <Dialog
                isOpen={dialog.isOpen}
                onClose={() => setDialog({ isOpen: false, parentId: null })}
                onConfirm={handleAdd}
                title="Add New Item"
            />
        </div>
    );
};
export default Tree;