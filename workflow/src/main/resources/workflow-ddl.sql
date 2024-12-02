CREATE SCHEMA IF NOT EXISTS workflow;



CREATE TABLE IF NOT EXISTS workflow.workflows (
    id UUID PRIMARY KEY,       -- Unique identifier for tracking the workflow, generated in the app
    name VARCHAR(255) NOT NULL,         -- Name or type of the workflow
    correlation_id VARCHAR(255),        -- ID to correlate the process with other systems
    status VARCHAR(50) NOT NULL,        -- Status of the workflow (e.g., pending, in_progress, complete, failed)
    type VARCHAR(255),
    max_retries INT NOT NULL,                       -- max retries defined by workflow at step creation
    retry_count INT NOT NULL,                       -- The retry counter
    sla_deadline TIMESTAMP,             -- SLA 
    created_at TIMESTAMP DEFAULT NOW(), -- Timestamp when the workflow was created
    updated_at TIMESTAMP DEFAULT NOW(), -- Timestamp when the workflow was last updated
    completed_at TIMESTAMP,             -- Timestamp when the workflow was completed (null until completed)
    created_by VARCHAR(255),            -- User who created the workflow
    updated_by VARCHAR(255),             -- User who last updated the workflow
    priority INT,                      -- For workflow prioritization
    parent_workflow_id UUID REFERENCES workflow.workflows(id) -- For sub-workflows    
);

CREATE TABLE IF NOT EXISTS workflow.workflow_steps (
    id UUID PRIMARY KEY,                       -- Unique identifier for each step, generated in the app
    workflow_id UUID REFERENCES workflow.workflows(id) ON DELETE CASCADE,  -- Foreign key linking to the workflow
    step_name VARCHAR(255) NOT NULL,                -- Name of the step (e.g., data_validation, data_processing)
    step_order INT NOT NULL,                        -- The order of the step in the workflow
    status VARCHAR(50) NOT NULL,                    -- Status of the step (e.g., pending, in_progress, complete, failed)
    type VARCHAR(255),    
    retry_count INT NOT NULL,                       -- The retry counter
    max_retries INT NOT NULL,                       -- max retries defined by workflow at step creation
    sla_deadline TIMESTAMP,                         -- SLA 
    started_at TIMESTAMP,                           -- Timestamp when the step started (null if not started)
    completed_at TIMESTAMP,                         -- Timestamp when the step was completed (null until completed)
    created_at TIMESTAMP DEFAULT NOW(),             -- Timestamp when the step was created
    updated_at TIMESTAMP DEFAULT NOW(),             -- Timestamp when the step was last updated
    created_by VARCHAR(255),                        -- User who created the step
    updated_by VARCHAR(255),                         -- User who last updated the step
    error_message TEXT,                -- For storing failure reasons
    parameters TEXT                   -- For step-specific configuration    
);

CREATE TABLE IF NOT EXISTS workflow.step_status_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),  -- Unique identifier for each status change
    step_id UUID REFERENCES workflow.workflow_steps(id) ON DELETE CASCADE,  -- Foreign key linking to workflow step
    old_status VARCHAR(50),                         -- Previous status of the step
    new_status VARCHAR(50),                         -- New status of the step
    reason TEXT,                                    --reason for change 
    changed_at TIMESTAMP DEFAULT NOW(),             -- Timestamp when the status was changed
    changed_by VARCHAR(255)                         -- User who changed the status
);

CREATE TABLE IF NOT EXISTS workflow.workflow_payloads (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),  -- Unique identifier for each payload
    workflow_id UUID REFERENCES workflow.workflows(id) ON DELETE CASCADE,  -- Foreign key linking to workflows
    payload TEXT,                                  --  payload data
    created_at TIMESTAMP DEFAULT NOW(),             -- Timestamp when the payload was created
    updated_at TIMESTAMP DEFAULT NOW(),             -- Timestamp when the payload was last updated
    created_by VARCHAR(255),                        -- User who created the payload
    updated_by VARCHAR(255),                         -- User who last updated the payload
    version INT DEFAULT 1  -- New version column

);

CREATE TABLE IF NOT EXISTS workflow.workflow_definitions (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,  -- e.g., "submit-order", "process-payment"
    version VARCHAR(50) NOT NULL,       -- e.g., "1.0.0", "2.1.0"
    definition JSONB NOT NULL,          -- Stores the workflow structure
    active BOOLEAN DEFAULT true,        -- Whether this version is active
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    UNIQUE (name, version)
);


-- Existing indexes remain the same

-- Additional indexes for user-related columns

CREATE INDEX IF NOT EXISTS idx_workflow_created_by ON workflow.workflows(created_by);
CREATE INDEX IF NOT EXISTS idx_workflow_updated_by ON workflow.workflows(updated_by);
CREATE INDEX IF NOT EXISTS idx_workflow_step_created_by ON workflow.workflow_steps(created_by);
CREATE INDEX IF NOT EXISTS idx_workflow_step_updated_by ON workflow.workflow_steps(updated_by);
CREATE INDEX IF NOT EXISTS idx_step_status_history_changed_by ON workflow.step_status_history(changed_by);
CREATE INDEX IF NOT EXISTS idx_workflow_payload_created_by ON workflow.workflow_payloads(created_by);
CREATE INDEX IF NOT EXISTS idx_workflow_payload_updated_by ON workflow.workflow_payloads(updated_by);
CREATE INDEX IF NOT EXISTS idx_step_status_history_step_id_changed_at ON workflow.step_status_history(step_id, changed_at);
CREATE INDEX IF NOT EXISTS idx_workflows_status ON workflow.workflows(status);
CREATE INDEX IF NOT EXISTS idx_workflows_created_at ON workflow.workflows(created_at);
CREATE INDEX IF NOT EXISTS idx_workflow_steps_status ON workflow.workflow_steps(status);
CREATE INDEX IF NOT EXISTS idx_step_status_history_changed_at ON workflow.step_status_history(changed_at);
