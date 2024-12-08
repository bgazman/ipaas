import React from 'react';
import {Home, BarChart, Users, Settings, Workflow} from 'lucide-react';
import { Link } from 'react-router-dom';

const navItems = [
    { icon: Home, label: 'Home', path: '/' },
    { icon: Workflow, label: 'Workflow', path: '/workflow' },
    // { icon: Users, label: 'Users', path: '/users' },
    // { icon: Settings, label: 'Settings', path: '/settings' }
];
interface SidebarProps {
    isOpen: boolean;
}
// Add hover/active states for better UX
const Sidebar: React.FC<SidebarProps> = ({ isOpen }) => {
    return (
        <aside className={`transition-all duration-300 ${isOpen ? 'w-64' : 'w-20'} bg-gray-800 h-full fixed inset-y-0 left-0 z-50 flex flex-col`}>
            {navItems.map((item, index) => (
                <Link key={index} to={item.path} className="flex items-center p-2 hover:bg-gray-700 transition duration-200">
                    <item.icon className="text-white" />
                    {isOpen && <span className="ml-2 text-white">{item.label}</span>}
                </Link>
            ))}
        </aside>
    );
}

export default Sidebar;