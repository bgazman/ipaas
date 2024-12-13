export const convertToReactFlow = (workflowString) => {
    try {
        const workflowObj = typeof workflowString === 'string' ? JSON.parse(workflowString) : workflowString;

        const nodes = workflowObj.nodes.map(node => ({
            id: node.id,
            type: node.id === '1' ? 'input' : node.id === '3' ? 'output' : 'default',
            data: { label: node.label },
            position: node.position,
            style: {
                background: node.id === '1' ? '#d4f1db' : node.id === '3' ? '#ffe6e6' : '#e6f3ff',
                borderColor: node.id === '1' ? '#2d993f' : node.id === '3' ? '#cc0000' : '#1a73e8'
            }
        }));

        const edges = workflowObj.edges.map(edge => ({
            id: edge.id,
            source: edge.source,
            target: edge.target,
            type: 'smoothstep',
            animated: true,
            style: { stroke: '#555' }
        }));

        return { nodes, edges };
    } catch (error) {
        console.error('Error converting workflow:', error);
        return { nodes: [], edges: [] };
    }
};