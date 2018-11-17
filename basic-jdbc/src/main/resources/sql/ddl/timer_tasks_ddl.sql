-- This DDL assumes that task_timer table has been created
-- and current role has create privileges

CREATE SCHEMA IF NOT EXISTS timer;
CREATE TYPE TASK_TYPES AS ENUM ('google','app');

CREATE TABLE IF NOT EXISTS timer.tasks(
	id 			BIGSERIAL 	PRIMARY KEY
	,account_id	BIGINT		REFERENCES timer.accounts(id) ON DELETE CASCADE
	,task_id 	TEXT 		
	,task_name 	TEXT		NOT NULL 
	,list_id 	TEXT 		
	,list_name 	TEXT		
	,task_type 	TASK_TYPES	NOT NULL
	,task_time	TIME		DEFAULT '00:15:00'
	,task_notes	TEXT
);

CREATE TABLE IF NOT EXISTS timer.accounts(
	id			BIGSERIAL	PRIMARY KEY
	,email		TEXT		UNIQUE
);

-- Create service accounts
DROP ROLE pomo_timer_task_app;
CREATE ROLE pomo_timer_task_app LOGIN;
-- Grant privileges to schema
GRANT USAGE ON SCHEMA timer TO pomo_timer_task_app;
GRANT USAGE ON ALL SEQUENCES IN SCHEMA timer TO pomo_timer_task_app;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA timer TO pomo_timer_task_app;