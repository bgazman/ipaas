import { Menu, Bell, User } from 'lucide-react';
interface NavbarProps {
    onMenuClick: () => void;
}

const Navbar: React.FC<NavbarProps> = ({ onMenuClick }) => {
    return (
        <nav className="h-16 bg-white border-b flex items-center justify-between px-4">
            {/* Left side */}
            <div className="flex items-center gap-4">
                <button onClick={onMenuClick} className="p-2">
                    <Menu className="h-6 w-6" />
                </button>
                <h1 className="text-xl font-semibold">Dashboard</h1>
            </div>

            {/* Right side */}
            <div className="flex items-center gap-4">
                <button className="p-2">
                    <Bell className="h-6 w-6" />
                </button>
                <button className="p-2">
                    <User className="h-6 w-6" />
                </button>
            </div>
        </nav>
    );
};
export default Navbar;