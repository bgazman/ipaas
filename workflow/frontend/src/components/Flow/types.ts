import React from "react";

export interface Node {
    id: string;
    type?: string;
    data: { label: string };
    position: { x: number; y: number };
    style?: React.CSSProperties;
}

export interface Edge {
    id: string;
    source: string;
    target: string;
}

export interface Position {
    x: number;
    y: number;
}
