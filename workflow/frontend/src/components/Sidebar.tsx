import React from 'react';
import {Home, BarChart, Users, Settings, Workflow} from 'lucide-react';
import { Link } from 'react-router-dom';

const navItems = [
    { icon: Home, label: 'Home', path: '/' },
    { icon: Workflow, label: 'Workflow', path: '/workflow' },
    // { icon: Users, label: 'Users', path: '/users' },
    // { icon: Settings, label: 'Settings', path: '/settings' }
];

// Add hover/active states for better UX
const Sidebar = ({ isOpen }) => {
    return (
        <aside className={`transition-all duration-300 ${isOpen ? 'w-64' : 'w-20'}`}>
            {navItems.map(item => (
                <Link to={item.path} className="flex items-center p-2">
                    <item.icon />
                    {isOpen && <span className="ml-2">{item.label}</span>}
                </Link>
            ))}
        </aside>
    );
}
export default Sidebar;