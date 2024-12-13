import React, { ReactNode, useState } from 'react';
import Navbar from '../Navbar.tsx';
import Sidebar from './Sidebar';


interface DashboardLayoutProps {
    children: ReactNode;
}

const DashboardLayout = ({ children }) => {
    const [isOpen, setIsOpen] = useState(false); // Closed by default
    const handleMenuToggle = () => setIsOpen(!isOpen);
    const handleSidebarClose = () => setIsOpen(false);
    return (
        <div className="h-screen flex flex-col">
            <nav className="h-16 flex-shrink-0">
                <Navbar onMenuClick={handleMenuToggle} />
            </nav>            {/* <button onClick={() => setIsOpen(!isOpen)}></button> */}
            <div className="flex flex-1">
                <Sidebar isOpen={isOpen} onClose={handleSidebarClose} />
                <main className="flex-1">{children}</main>
            </div>
        </div>
    );
};
export default DashboardLayout;