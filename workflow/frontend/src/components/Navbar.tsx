import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { Menu, X } from 'lucide-react';

const Navbar = () => {
    const [isOpen, setIsOpen] = useState(false);

    return (
        <>
            {isOpen && (
                <div className="fixed inset-0 bg-black bg-opacity-50 z-40" onClick={() => setIsOpen(false)} />
            )}

            <div className={`fixed top-0 left-0 h-full w-64 bg-gray-800 transform transition-transform z-50 ${isOpen ? 'translate-x-0' : '-translate-x-full'}`}>
                <div className="p-4">
                    <button onClick={() => setIsOpen(false)} className="text-gray-400 hover:text-white">
                        <X className="h-6 w-6" />
                    </button>
                    <div className="mt-4 space-y-2">
                        <Link to="/" className="text-gray-300 hover:text-white block px-3 py-2" onClick={() => setIsOpen(false)}>Dashboard</Link>
                        <Link to="/definitions" className="text-gray-300 hover:text-white block px-3 py-2" onClick={() => setIsOpen(false)}>Definitions</Link>
                        <Link to="/instances" className="text-gray-300 hover:text-white block px-3 py-2" onClick={() => setIsOpen(false)}>Instances</Link>
                    </div>
                </div>
            </div>

            <nav className="fixed top-0 w-full bg-gray-800 z-40">
                <div className="max-w-7xl mx-auto px-4">
                    <div className="flex items-center h-16">
                        <button onClick={() => setIsOpen(!isOpen)} className="text-gray-400 hover:text-white">
                            <Menu className="h-6 w-6" />
                        </button>
                        <Link to="/" className="text-white text-xl font-bold ml-4">Workflow Dashboard</Link>
                    </div>
                </div>
            </nav>
        </>
    );
};

export default Navbar;