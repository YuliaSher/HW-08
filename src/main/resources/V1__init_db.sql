CREATE type technical_level as enum ('Trainee', 'Junior', 'Middle', 'Senior');

CREATE SEQUENCE IF NOT EXISTS seq_worker_id
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS seq_client_id
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS seq_project_id
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS worker
(
    ID       BIGINT DEFAULT nextval('seq_worker_id'),
    NAME     VARCHAR(1000)   NOT NULL check (length(NAME) >= 2),
    BIRTHDAY DATE CHECK (EXTRACT(YEAR FROM birthday) > 1900),
    LEVEL    technical_level NOT NULL,
    SALARY   DECIMAL(10, 2) check (SALARY between 100 and 100000),
    CONSTRAINT pk_worker_id PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS client
(
    ID   BIGINT DEFAULT nextval('seq_client_id'),
    NAME VARCHAR(1000) NOT NULL check (length(NAME) >= 2),
    CONSTRAINT pk_client_id PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS project
(
    ID          BIGINT DEFAULT nextval('seq_project_id'),
    NAME        VARCHAR(1000) NOT NULL,
    CLIENT_ID   BIGINT,
    START_DATE  DATE,
    FINISH_DATE DATE,
    CONSTRAINT fk_client_id FOREIGN KEY (CLIENT_ID) references client (ID),
    CONSTRAINT pk_project_id PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS project_worker
(
    PROJECT_ID BIGINT,
    WORKER_ID  BIGINT,
    CONSTRAINT pk_project_worker_id PRIMARY KEY (PROJECT_ID, WORKER_ID),
    CONSTRAINT fk_project_id FOREIGN KEY (PROJECT_ID) references project (ID),
    CONSTRAINT fk_worker_id FOREIGN KEY (WORKER_ID) references worker (ID)
);

CREATE INDEX project_client_idx ON project (client_id);
CREATE INDEX project_worker_project_idx ON project_worker (project_id);
CREATE INDEX project_worker_worker_idx ON project_worker (worker_id);