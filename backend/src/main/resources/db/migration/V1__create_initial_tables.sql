CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(150) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);


CREATE TABLE projects (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(150) NOT NULL,
                          description TEXT,
                          owner_id BIGINT NOT NULL,

                          CONSTRAINT fk_project_owner
                              FOREIGN KEY (owner_id)
                                  REFERENCES users (id)
                                  ON DELETE RESTRICT
);

CREATE TABLE project_members (
                                 id BIGSERIAL PRIMARY KEY,
                                 user_id BIGINT NOT NULL,
                                 project_id BIGINT NOT NULL,
                                 role VARCHAR(20) NOT NULL,

                                 CONSTRAINT fk_pm_user
                                     FOREIGN KEY (user_id)
                                         REFERENCES users (id)
                                         ON DELETE CASCADE,

                                 CONSTRAINT fk_pm_project
                                     FOREIGN KEY (project_id)
                                         REFERENCES projects (id)
                                         ON DELETE CASCADE,

                                 CONSTRAINT uk_project_member UNIQUE (user_id, project_id),

                                 CONSTRAINT chk_role
                                     CHECK (role IN ('ADMIN', 'MEMBER'))
);

CREATE TABLE tasks (
                       id BIGSERIAL PRIMARY KEY,
                       title VARCHAR(200) NOT NULL,
                       description TEXT,

                       status VARCHAR(20) NOT NULL,
                       priority VARCHAR(20) NOT NULL,

                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       deadline TIMESTAMP,

                       project_id BIGINT NOT NULL,
                       assignee_id BIGINT,

                       CONSTRAINT fk_task_project
                           FOREIGN KEY (project_id)
                               REFERENCES projects (id)
                               ON DELETE CASCADE,

                       CONSTRAINT fk_task_assignee
                           FOREIGN KEY (assignee_id)
                               REFERENCES users (id)
                               ON DELETE SET NULL,

                       CONSTRAINT chk_status
                           CHECK (status IN ('TODO', 'IN_PROGRESS', 'DONE')),

                       CONSTRAINT chk_priority
                           CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH', 'CRITICAL'))
);

