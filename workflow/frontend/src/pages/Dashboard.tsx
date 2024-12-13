import React from 'react';

const Dashboard = () => {
    return (
        <div className="p-6">
            <h2 className="text-2xl font-bold mb-6">Dashboard</h2>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                <div className="bg-white rounded-lg shadow p-6">
                    <h3 className="font-semibold mb-2">Active Projects</h3>
                    <p className="text-3xl font-bold text-blue-600">12</p>
                </div>
                <div className="bg-white rounded-lg shadow p-6">
                    <h3 className="font-semibold mb-2">Tasks Due Today</h3>
                    <p className="text-3xl font-bold text-red-600">5</p>
                </div>
                <div className="bg-white rounded-lg shadow p-6">
                    <h3 className="font-semibold mb-2">Completed Tasks</h3>
                    <p className="text-3xl font-bold text-green-600">27</p>
                </div>
            </div>
        </div>
    );
};

export default Dashboard;