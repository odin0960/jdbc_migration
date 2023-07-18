CREATE TABLE IF NOT EXISTS worker (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR (1000) NOT NULL,
	birthday DATE,
	level ENUM('Trainee', 'Junior', 'Middle', 'Senior') DEFAULT 'Junior',
	salary NUMERIC(8,2),
	CONSTRAINT length_worker_name CHECK (CHAR_LENGTH(name) >= 2),
	CONSTRAINT check_birthday_worker CHECK birthday>'1900-12-31',
	CONSTRAINT check_salary_worker CHECK (salary>=100.00 AND salary<=100000.00)
);

CREATE TABLE IF NOT EXISTS client (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR (1000) NOT NULL,
	CONSTRAINT length_client_name CHECK (CHAR_LENGTH(name) >= 2)
);

CREATE TABLE IF NOT EXISTS project (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	client_id BIGINT,
	start_date DATE DEFAULT CURRENT_DATE(),
	finish_date DATE,
	CONSTRAINT client_id_fk 
	FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE IF NOT EXISTS project_worker (
	project_id BIGINT,
	worker_id BIGINT,
	PRIMARY KEY (project_id, worker_id),
	CONSTRAINT project_id_fk 
	FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE RESTRICT,
	CONSTRAINT worker_id_fk 
	FOREIGN KEY (worker_id) REFERENCES worker(id) ON DELETE RESTRICT
);