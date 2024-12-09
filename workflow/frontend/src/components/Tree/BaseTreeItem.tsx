import React from 'react';
import { ChevronRight, ChevronDown, Folder, File } from 'lucide-react';
import { BaseTreeItemProps, TreeNode } from './types';

export const BaseTreeItem: React.FC<BaseTreeItemProps> = ({
                                                              id,
                                                              name,
                                                              type,
                                                              children,
                                                              level,
                                                              isExpanded = false,
                                                              onToggle,
                                                              onSelect,
                                                              renderActions,
                                                              className = '',
                                                          }) => {
    const handleToggle = (e: React.MouseEvent) => {
        e.stopPropagation();
        if (type === 'folder' && onToggle) {
            onToggle(id);
        }
    };

    const handleSelect = (e: React.MouseEvent) => {
        e.stopPropagation();
        if (onSelect) {
            onSelect(id);
        }
    };

    return (
        <div className="select-none">
            <div
                className={`group flex items-center h-8 py-1 px-2 hover:bg-gray-100 rounded-md cursor-pointer ${className}`}
                style={{ paddingLeft: `${level * 12 + 8}px` }}
                onClick={handleSelect}
            >
                <div className="flex items-center min-w-0 flex-1">
                    {type === 'folder' && (
                        <span onClick={handleToggle} className="mr-1 flex-shrink-0">
              {isExpanded ? (
                  <ChevronDown className="w-4 h-4" />
              ) : (
                  <ChevronRight className="w-4 h-4" />
              )}
            </span>
                    )}
                    {type === 'folder' ? (
                        <Folder className="w-4 h-4 mr-2 flex-shrink-0 text-blue-500" />
                    ) : (
                        <File className="w-4 h-4 mr-2 flex-shrink-0 text-gray-500" />
                    )}

                    <span className="truncate">{name}</span>
                </div>

                {renderActions &&
                    <div className="hidden group-hover:flex items-center gap-1 flex-shrink-0 ml-2">
                        {renderActions({ id, name, type, children })}
                    </div>
                }
            </div>

            {type === 'folder' && isExpanded && children && (
                <div>
                    {children.map((child) => (
                        <BaseTreeItem
                            key={child.id}
                            {...child}
                            level={level + 1}
                            isExpanded={false}
                            onToggle={onToggle}
                            onSelect={onSelect}
                            renderActions={renderActions}
                            className={className}
                        />
                    ))}
                </div>
            )}
        </div>
    );
};