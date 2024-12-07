import React, { ReactNode, useState } from 'react';
import Navbar from '../components/Navbar';
import Sidebar from '../components/Sidebar';


interface DashboardLayoutProps {
    children: ReactNode;
}

const DashboardLayout = ({ children }) => {
    const [isOpen, setIsOpen] = useState(false); // Closed by default

    return (
        <div className="h-screen flex flex-col">
            <nav className="h-16 flex-shrink-0">
                <Navbar onMenuClick={() => setIsOpen(!isOpen)} />
            </nav>            {/* <button onClick={() => setIsOpen(!isOpen)}></button> */}
            <div className="flex flex-1">
                <Sidebar isOpen={isOpen} />
                <main className="flex-1">{children}</main>
            </div>
        </div>
    );
};
export default DashboardLayout;