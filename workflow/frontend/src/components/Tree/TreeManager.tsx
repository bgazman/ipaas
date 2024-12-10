// TreeManager.tsx
import { TreeNode } from './types';

export class TreeManager {
    private nodes: Map<string, TreeNode>;
    private rootNodes: TreeNode[];

    constructor() {
        this.nodes = new Map();
        this.rootNodes = [];
    }

    setData(nodes: TreeNode[]) {
        this.nodes.clear();
        this.rootNodes = [];
        nodes.forEach(node => {
            const newNode = { ...node, children: [] };
            this.nodes.set(node.id, newNode);
        });
        nodes.forEach(node => {
            if (!node.parentId) {
                this.rootNodes.push(this.nodes.get(node.id)!);
            } else {
                const parent = this.nodes.get(node.parentId);
                if (parent && !parent.children!.some(child => child.id === node.id)) {
                    parent.children!.push(this.nodes.get(node.id)!);
                }
            }
        });
    }

    getRootNodes(): TreeNode[] {
        return this.rootNodes;
    }
}