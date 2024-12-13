export const WorkflowDefinitionSidebar = ({ children, onCreateClick }) => {

    return (
        <aside className="w-64 flex-shrink-0 min-h-[calc(100vh-4rem)] border-r border-gray-200">

            <div className="h-full py-4">
                <div style={{
                    padding: '20px',
                    borderBottom: '1px solid #eee',
                    display: 'flex',
                    justifyContent: 'space-between',
                    alignItems: 'center'
                }}>
                    <h2 style={{margin: 0}}>Workflows</h2>
                    <button
                        onClick={() => onCreateClick()}
                        style={{
                            padding: '8px 16px',
                            backgroundColor: '#0066cc',
                            color: 'white',
                            border: 'none',
                            borderRadius: '4px',
                            cursor: 'pointer'
                        }}
                    >
                        Create
                    </button>
                </div>
                <div style={{padding: '20px'}}>
                    {children}
                </div>
            </div>
        </aside>
    );
};