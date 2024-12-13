// src/WorkflowDefinitionForm.tsx
import React, { useState } from 'react';

interface WorkflowDefinition {
    id: string;
    domain: string;
    name: string;
    type: string;
    version: string;
    definition: string;
    active: boolean;
    createdAt: string;
    updatedAt: string;
}

const WorkflowDefinitionForm: React.FC = () => {
    const [formData, setFormData] = useState<WorkflowDefinition>({
        id: "01bbeed2-f511-4be9-945c-a7ba880e3640",
        domain: "ddddddd",
        name: "siiss",
        type: "fff",
        version: "1.0.0",
        definition: "asd",
        active: false,
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString(),
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value, type, checked } = e.target;
        setFormData({
            ...formData,
            [name]: type === 'checkbox' ? checked : value,
        });
    };

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        console.log('Form submitted:', formData);
        // Add your submission logic here (e.g., API call)
    };

    return (
        <form onSubmit={handleSubmit} className="max-w-md mx-auto p-4 bg-white rounded-lg shadow-md">
            <h2 className="text-lg font-semibold mb-4">Workflow Definition Form</h2>

            {Object.keys(formData).map((key) => (
                <div key={key} className="mb-4">
                    <label className="block text-sm font-medium text-gray-700" htmlFor={key}>
                        {key.charAt(0).toUpperCase() + key.slice(1)}
                    </label>
                    {typeof formData[key as keyof WorkflowDefinition] === 'boolean' ? (
                        <input
                            type="checkbox"
                            name={key}
                            id={key}
                            checked={formData[key as keyof WorkflowDefinition]}
                            onChange={handleChange}
                            className="mt-1 h-4 w-4 text-blue-600 border-gray-300 rounded focus:ring-blue-500"
                        />
                    ) : (
                        <input
                            type={key === 'definition' ? 'textarea' : 'text'}
                            name={key}
                            id={key}
                            value={formData[key as keyof WorkflowDefinition]}
                            onChange={handleChange}
                            className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:border-blue-500 focus:ring focus:ring-blue-500"
                        />
                    )}
                </div>
            ))}

            <button
                type="submit"
                className="w-full py-2 px-4 bg-blue-600 text-white font-semibold rounded-md hover:bg-blue-700 transition duration-200"
            >
                Submit
            </button>
        </form>
    );
};

export default WorkflowDefinitionForm;
