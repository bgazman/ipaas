import {TreeProvider} from "../../context/TreeContext.tsx";
import {useTree} from "../../hooks/useTree.tsx";
import React from 'react';
import { Plus, Folder } from 'lucide-react';
import { TreeItem } from './TreeItem';

const TreeContent = () => {
    const { data, createNode } = useTree();

    return (
        <div className="w-full">
            {/* Optional: Top actions */}
            <div className="flex items-center gap-2 p-2">
                <button
                    onClick={() => createNode(null, 'file')}
                    className="flex items-center gap-1 px-2 py-1 text-sm bg-blue-50 hover:bg-blue-100 rounded-md"
                >
                    <Plus className="w-4 h-4" />
                    New File
                </button>
                <button
                    onClick={() => createNode(null, 'folder')}
                    className="flex items-center gap-1 px-2 py-1 text-sm bg-blue-50 hover:bg-blue-100 rounded-md"
                >
                    <Folder className="w-4 h-4" />
                    New Folder
                </button>
            </div>

            {/* Tree Items */}
            <div className="w-full">
                {data && data.length > 0 ? (
                    data.map((item) => (
                        <TreeItem
                            key={item.id}
                            id={item.id}
                            name={item.name}
                            type={item.type}
                            children={item.children}
                            level={0}
                        />
                    ))
                ) : (
                    <div className="p-4 text-gray-500 text-center">No items</div>
                )}
            </div>
        </div>
    );
};

const Tree = () => {
    return <TreeContent />;
};

export default Tree;