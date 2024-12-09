import React, { createContext, useContext, useState } from 'react';


type TreeNode = {
    id: string;
    name: string;
    type: 'file' | 'folder';
    children?: TreeNode[];
};

type TreeContextType = {
    data: TreeNode[];
    setData: React.Dispatch<React.SetStateAction<TreeNode[]>>;
    selectedNode: string | null;
    setSelectedNode: (id: string | null) => void;
    createNode: (parentId: string | null, type: 'file' | 'folder', onSuccess?: (parentId: string) => void) => void;
    deleteNode: (id: string) => void;
    renameNode: (id: string, newName: string) => void;
};

export const TreeContext = createContext<TreeContextType | undefined>(undefined);

type TreeProviderProps = {
    children: React.ReactNode;
    initialData?: any;
};

export const TreeProvider: React.FC<TreeProviderProps> = ({ children, initialData }) => {
    const transformData = (inputData: any): TreeNode[] => {
        if (!inputData) return [];

        const transformNode = (node: any): TreeNode => ({
            id: node.id,
            name: node.label,
            type: node.type,
            children: node.children?.map(transformNode)
        });

        if (inputData.id === 'root') {
            return inputData.children?.map(transformNode) || [];
        }
        return Array.isArray(inputData) ? inputData.map(transformNode) : [transformNode(inputData)];
    };

    const [data, setData] = useState<TreeNode[]>(transformData(initialData));
    const [selectedNode, setSelectedNode] = useState<string | null>(null);

    const createNode = (parentId: string | null, type: 'file' | 'folder', onSuccess?: (parentId: string) => void) => {
        const newNode: TreeNode = {
            id: Math.random().toString(36).substr(2, 9),
            name: type === 'folder' ? 'New Folder' : 'New File',
            type,
            children: type === 'folder' ? [] : undefined,
        };

        setData(prevData => {
            if (!parentId) return [...prevData, newNode];

            const updateChildren = (nodes: TreeNode[]): TreeNode[] => {
                return nodes.map(node => {
                    if (node.id === parentId) {
                        return {
                            ...node,
                            children: [...(node.children || []), newNode],
                        };
                    }
                    if (node.children) {
                        return {
                            ...node,
                            children: updateChildren(node.children),
                        };
                    }
                    return node;
                });
            };

            const result = updateChildren(prevData);
            // Call onSuccess after the state has been updated
            if (onSuccess) {
                onSuccess(parentId);
            }
            return result;
        });
    };

    const deleteNode = (id: string) => {
        setData(prevData => {
            const deleteFromNodes = (nodes: TreeNode[]): TreeNode[] => {
                return nodes.filter(node => {
                    if (node.id === id) return false;
                    if (node.children) {
                        node.children = deleteFromNodes(node.children);
                    }
                    return true;
                });
            };

            return deleteFromNodes(prevData);
        });

        if (selectedNode === id) {
            setSelectedNode(null);
        }
    };

    const renameNode = (id: string, newName: string) => {
        setData(prevData => {
            const renameInNodes = (nodes: TreeNode[]): TreeNode[] => {
                return nodes.map(node => {
                    if (node.id === id) {
                        return { ...node, name: newName };
                    }
                    if (node.children) {
                        return {
                            ...node,
                            children: renameInNodes(node.children),
                        };
                    }
                    return node;
                });
            };

            return renameInNodes(prevData);
        });
    };

    return (
        <TreeContext.Provider
            value={{
                data,
                setData,
                selectedNode,
                setSelectedNode,
                createNode,
                deleteNode,
                renameNode,
            }}
        >
            {children}
        </TreeContext.Provider>
    );
};