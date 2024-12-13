import { Node, Viewport, Rect } from '@xyflow/react';

export const isNodeOutsideView = (
    node: Node,
    viewport: Viewport,
    bounds: Rect
): boolean => {
    // Convert node position to viewport coordinates
    const nodeX = (node.position.x * viewport.zoom) + viewport.x;
    const nodeY = (node.position.y * viewport.zoom) + viewport.y;

    // Add padding for better visibility check
    const padding = 50;

    return (
        nodeX < bounds.x - padding ||
        nodeX > bounds.x + bounds.width + padding ||
        nodeY < bounds.y - padding ||
        nodeY > bounds.y + bounds.height + padding
    );
};