
import {createContext} from "react";

interface TreeContextType<T> {
    items: T[];
    selectedItem?: T;
    onSelect: (item: T) => void;
}

export const TreeContext = createContext<TreeContextType<any> | null>(null);