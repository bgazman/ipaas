export const STYLES = {
    START_NODE: {
        background: '#4CAF50',
        color: 'white',
        border: 'none',
        width: 100,
        borderRadius: '8px',
    },
    DEFAULT_NODE: {
        background: '#fff',
        border: '1px solid #ddd',
        borderRadius: '8px',
        width: 100,
    }
} as const;

export const SPACING = {
    VERTICAL: 100,
    HORIZONTAL: 300,
} as const;

export const INITIAL_NODES = [{
    id: 'start',
    type: 'input',
    data: { label: 'Start' },
    position: { x: 250, y: 50 },
    style: STYLES.START_NODE
}];

export const FIT_VIEW_OPTIONS = {
    padding: 0.5,  // Increased padding to 50% around the graph
    minZoom: 0.2,  // Lowered minimum zoom to allow more zooming out
    maxZoom: 1.5,
} as const;

export const DEFAULT_VIEWPORT = {
    x: 0,
    y: 0,
    zoom: 0.7  // Decreased default zoom level
} as const;