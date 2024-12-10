export interface TreeNode {
    id: string;
    name: string;
    type: 'file' | 'folder';
    label: string;
    parentId: string | null;
    children?: TreeNode[];
    metadata?: Record<string, any>;
}

