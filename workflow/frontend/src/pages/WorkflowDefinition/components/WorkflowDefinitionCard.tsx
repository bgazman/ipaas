const WorkflowDefinitionCard = ({ workflow, onOpen, onDelete }) => {
    return (
        <div
            onClick={() => onOpen(workflow)} // Open the dialog for viewing or editing
            className="bg-gray-100 shadow-sm rounded-lg p-4 flex justify-between items-center cursor-pointer hover:bg-gray-200 transition"
        >
            {/* Workflow Name */}
            <div className="text-gray-800 truncate">{workflow.name}</div>

            {/* Action Buttons */}
            <div
                className="flex space-x-2"
                onClick={(e) => e.stopPropagation()} // Prevent card click event when clicking delete button
            >
                <button
                    onClick={() => onDelete(workflow)} // Pass workflow to onDelete handler
                    className="p-2 rounded-md bg-red-300 hover:bg-red-400 transition"
                    title="Delete"
                >
                    🗑️
                </button>
            </div>
        </div>
    );
};

export default WorkflowDefinitionCard;
