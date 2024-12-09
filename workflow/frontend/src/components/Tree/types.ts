export type TreeNode = {
    id: string;
    name: string;
    type: 'file' | 'folder';
    children?: TreeNode[];
};

export type TreeContextType = {
    data: TreeNode[];
    setData: React.Dispatch<React.SetStateAction<TreeNode[]>>;
    selectedNode: string | null;
    setSelectedNode: (id: string | null) => void;
    createNode: (parentId: string | null, type: 'file' | 'folder') => void;
    deleteNode: (id: string) => void;
    renameNode: (id: string, newName: string) => void;
};

export interface BaseTreeItemProps {
    id: string;
    name: string;
    type: 'file' | 'folder';
    children?: TreeNode[];
    level: number;
    isExpanded?: boolean;
    onToggle?: (id: string) => void;
    onSelect?: (id: string) => void;
    renderActions?: (node: TreeNode) => React.ReactNode;
    className?: string;
}