import React, {FC, useState} from 'react';
import {FolderPlus, Plus} from 'lucide-react';
interface DialogProps {
    onSubmit: (data: { name: string; domain: string; parentId: string | null }) => void;
    open: boolean;
    onOpenChange: (open: boolean) => void;
    parentNode?: { id: string; domain: string } | null;
    title: string;
}

const WorkflowDefinitionDialog: FC<DialogProps> = ({ onSubmit, open, onOpenChange, parentNode, title }) => {
    const [name, setName] = useState('');
    const [domain, setDomain] = useState('');
    const isAddingToDomain = !!parentNode;

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        onSubmit({
            name,
            domain: isAddingToDomain ? parentNode.domain : domain,
            parentId: parentNode?.id || null
        });
        setName('');
        setDomain('');
        onOpenChange(false);
    };

    if (!open) return null;

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
            <div className="bg-white p-4 rounded-lg w-96">
                <div className="flex justify-between mb-4">
                    <h2 className="text-lg font-bold">{title}</h2>
                    <button onClick={() => onOpenChange(false)} className="text-gray-500 hover:text-gray-700">×</button>
                </div>
                <form onSubmit={handleSubmit} className="space-y-4">
                    {!isAddingToDomain && (
                        <input
                            type="text"
                            placeholder="Domain Name"
                            value={domain}
                            onChange={(e) => setDomain(e.target.value)}
                            className="w-full p-2 border rounded"
                            autoFocus
                        />
                    )}
                    <input
                        type="text"
                        placeholder="Workflow Name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        className="w-full p-2 border rounded"
                        autoFocus={isAddingToDomain}
                    />
                    <div className="flex justify-end gap-2">
                        <button
                            type="button"
                            onClick={() => onOpenChange(false)}
                            className="px-4 py-2 text-gray-600 hover:bg-gray-100 rounded"
                        >
                            Cancel
                        </button>
                        <button
                            type="submit"
                            disabled={!name || (!isAddingToDomain && !domain)}
                            className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 flex items-center gap-2 disabled:opacity-50"
                        >
                            <Plus size={16} />
                            Create
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};
export default WorkflowDefinitionDialog;