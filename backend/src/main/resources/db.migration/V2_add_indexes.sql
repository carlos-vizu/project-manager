--id based indexes
CREATE INDEX idx_task_project ON tasks(project_id);
CREATE INDEX idx_task_status ON tasks(status);
CREATE INDEX idx_task_priority ON tasks(priority);
CREATE INDEX idx_task_assignee ON tasks(assignee_id);

--indexes for task creation and deadline
CREATE INDEX idx_task_created_at ON tasks(created_at);
CREATE INDEX idx_task_deadline ON tasks(deadline);

--string based indexes
CREATE INDEX idx_task_title ON tasks(title);
CREATE INDEX idx_task_description ON tasks(description);