import { ChevronRight, ChevronDown, ExternalLink } from 'lucide-react';
import { useState } from 'react';

export default function WorkflowCard({ workflow, onClick }) {
    const [isExpanded, setIsExpanded] = useState(false);

    const handleExpand = (e) => {
        e.stopPropagation();
        setIsExpanded(!isExpanded);
    };

    const handleClick = (e) => {
        e.stopPropagation();
        onClick(workflow);
    };

    return (
        <div className="border border-gray-300 rounded-lg p-2 mb-2 w-full">
            <div className="flex items-center justify-between gap-2 mb-1">
                <div className="flex items-center gap-2 min-w-0 flex-1">
                    <button
                        onClick={handleExpand}
                        className="p-0.5 hover:bg-gray-100 rounded"
                        aria-label={isExpanded ? "Collapse details" : "Expand details"}
                    >
                        {isExpanded ? (
                            <ChevronDown className="w-4 h-4 text-gray-600" />
                        ) : (
                            <ChevronRight className="w-4 h-4 text-gray-600" />
                        )}
                    </button>
                    <h3 className="text-sm font-semibold truncate">{workflow.name}</h3>
                </div>
                <div className="flex items-center gap-2">
          <span
              className={`${
                  workflow.active ? 'bg-green-500' : 'bg-gray-500'
              } text-white px-1.5 py-0.5 rounded text-xs whitespace-nowrap`}
          >
            {workflow.active ? 'Active' : 'Inactive'}
          </span>
                    <button
                        onClick={handleClick}
                        className="p-0.5 hover:bg-gray-100 rounded"
                        aria-label="Open workflow"
                    >
                        <ExternalLink className="w-4 h-4 text-gray-600" />
                    </button>
                </div>
            </div>

            <div className="text-xs text-gray-600 truncate">
                Domain: {workflow.domain}
            </div>

            {isExpanded && (
                <div className="mt-2 text-xs text-gray-600 border-t pt-2">
                    <div className="mb-1">Created: {workflow.createdAt}</div>
                    <div className="mb-1">Last Modified: {workflow.lastModified}</div>
                    <div className="mb-1">Owner: {workflow.owner}</div>
                    <div>Description: {workflow.description}</div>
                </div>
            )}
        </div>
    );
}

