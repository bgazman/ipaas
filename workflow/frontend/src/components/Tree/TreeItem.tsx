import React, {useCallback, useState} from 'react';
import { ChevronRight, ChevronDown, Folder, File, Edit2, Trash2, Plus } from 'lucide-react';
import { useTree } from '../../hooks/useTree';



interface TreeItemProps {
    id: string;
    name: string;
    type: 'file' | 'folder';
    children?: any[];
    level: number;
}

export const TreeItem: React.FC<TreeItemProps> = ({ id, name, type, children, level }) => {
    const [isExpanded, setIsExpanded] = useState(true);
    const [isEditing, setIsEditing] = useState(false);
    const [editName, setEditName] = useState(name);

    const { selectedNode, setSelectedNode, createNode, deleteNode, renameNode } = useTree();

    const handleToggle = (e: React.MouseEvent) => {
        e.stopPropagation();
        if (type === 'folder') {
            setIsExpanded(!isExpanded);
        }
    };

    const handleSelect = (e: React.MouseEvent) => {
        e.stopPropagation();
        setSelectedNode(id);
    };

    const handleRename = () => {
        if (editName.trim() && editName !== name) {
            renameNode(id, editName);
        }
        setIsEditing(false);
    };

    const handleCreateNode = useCallback((e: React.MouseEvent, nodeType: 'file' | 'folder') => {
        e.stopPropagation();
        setIsExpanded(true);
        createNode(id, nodeType);
    }, [id, createNode]);

    return (
        <div className="select-none">
            <div
                className={`group flex items-center h-8 py-1 px-2 hover:bg-gray-100 rounded-md cursor-pointer ${
                    selectedNode === id ? 'bg-blue-100' : ''
                }`}
                style={{ paddingLeft: `${level * 12 + 8}px` }}
                onClick={handleSelect}
            >
                <div className="flex items-center min-w-0 flex-1">
                    {type === 'folder' && (
                        <span onClick={handleToggle} className="mr-1 flex-shrink-0">
              {isExpanded ? (
                  <ChevronDown className="w-4 h-4" />
              ) : (
                  <ChevronRight className="w-4 h-4" />
              )}
            </span>
                    )}
                    {type === 'folder' ? (
                        <Folder className="w-4 h-4 mr-2 flex-shrink-0 text-blue-500" />
                    ) : (
                        <File className="w-4 h-4 mr-2 flex-shrink-0 text-gray-500" />
                    )}

                    {isEditing ? (
                        <input
                            type="text"
                            value={editName}
                            onChange={(e) => setEditName(e.target.value)}
                            onBlur={handleRename}
                            onKeyDown={(e) => {
                                if (e.key === 'Enter') handleRename();
                                if (e.key === 'Escape') setIsEditing(false);
                            }}
                            className="flex-1 bg-white border rounded px-1 py-0 text-sm min-w-0"
                            autoFocus
                            onClick={(e) => e.stopPropagation()}
                        />
                    ) : (
                        <span className="truncate">{name}</span>
                    )}
                </div>

                <div className="hidden group-hover:flex items-center gap-1 flex-shrink-0 ml-2">
                    <button
                        onClick={(e) => {
                            e.stopPropagation();
                            setIsEditing(true);
                        }}
                        className="p-1 hover:bg-gray-200 rounded-md"
                    >
                        <Edit2 className="w-3 h-3" />
                    </button>
                    <button
                        onClick={(e) => {
                            e.stopPropagation();
                            deleteNode(id);
                        }}
                        className="p-1 hover:bg-gray-200 rounded-md"
                    >
                        <Trash2 className="w-3 h-3" />
                    </button>
                    {type === 'folder' && (
                        <>
                            <button
                                onClick={(e) => handleCreateNode(e, 'file')}
                                className="p-1 hover:bg-gray-200 rounded-md"
                            >
                                <Plus className="w-3 h-3" />
                            </button>
                            <button
                                onClick={(e) => handleCreateNode(e, 'folder')}
                                className="p-1 hover:bg-gray-200 rounded-md"
                            >
                                <Folder className="w-3 h-3" />
                            </button>
                        </>
                    )}
                </div>
            </div>

            {type === 'folder' && isExpanded && children && (
                <div>
                    {children.map((child) => (
                        <TreeItem
                            key={child.id}
                            id={child.id}
                            name={child.name}
                            type={child.type}
                            children={child.children}
                            level={level + 1}
                        />
                    ))}
                </div>
            )}
        </div>
    );
};