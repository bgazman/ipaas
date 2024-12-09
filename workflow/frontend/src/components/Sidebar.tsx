import React from 'react';
import { Home,  Workflow, LucideIcon } from 'lucide-react';
import { Link } from 'react-router-dom';

interface NavItem {
    path: string;
    icon: LucideIcon;  // Changed from React.FC
    label: string;
}

interface SidebarProps {
    isOpen: boolean;
    onClose: () => void;
}  // Removed navItems from props since it's defined internally

const navItems: NavItem[] = [
    { icon: Home, label: 'Home', path: '/' },
    { icon: Workflow, label: 'Workflow', path: '/workflow' },
];

const Sidebar: React.FC<SidebarProps> = ({ isOpen, onClose }) => {
    return (
        <aside className={`
            fixed top-16 h-[calc(100vh-4rem)] bg-white shadow-lg z-20
            transition-all duration-300
            ${isOpen ? 'translate-x-0' : '-translate-x-full'}
            w-64
        `}>
            {navItems.map(item => (
                <Link
                    key={item.path}
                    to={item.path}
                    className="flex items-center p-2 hover:bg-gray-100"
                    onClick={onClose} // Close sidebar when clicking on a link
                >
                    <item.icon size={20} />
                    <span className="ml-2">{item.label}</span>
                </Link>
            ))}
        </aside>
    );
}

export default Sidebar;