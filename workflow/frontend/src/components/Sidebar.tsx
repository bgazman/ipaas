import React from 'react';
import { Home, BarChart, Users, Settings, Workflow, LucideIcon } from 'lucide-react';
import { Link } from 'react-router-dom';

interface NavItem {
    path: string;
    icon: LucideIcon;  // Changed from React.FC
    label: string;
}

interface SidebarProps {
    isOpen: boolean;
}  // Removed navItems from props since it's defined internally

const navItems: NavItem[] = [
    { icon: Home, label: 'Home', path: '/' },
    { icon: Workflow, label: 'Workflow', path: '/workflow' },
];

const Sidebar: React.FC<SidebarProps> = ({ isOpen }) => {
    return (
        <aside className={`transition-all duration-300 ${isOpen ? 'w-64' : 'w-1'}`}>
            {navItems.map(item => (
                <Link
                    key={item.path}
                    to={item.path}
                    className="flex items-center p-2 hover:bg-gray-100"
                >
                    <item.icon size={20} />
                    {isOpen && <span className="ml-2">{item.label}</span>}
                </Link>
            ))}
        </aside>
    );
}

export default Sidebar;