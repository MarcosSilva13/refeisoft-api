-- Database: refeisoft_db

CREATE DATABASE IF NOT EXISTS refeisoft_db
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'pt_BR.UTF-8'
    LC_CTYPE = 'pt_BR.UTF-8'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

CREATE TABLE tb_user (
	user_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL,
	role VARCHAR(255) NOT NULL
);

-- a password é 123
INSERT INTO tb_user (name, email, password, role) VALUES ('Admin', 'admin@gmail.com', '$2a$10$3vBgspUhmx28Atyeo9ARXe3avQQkovApBauaQNIqm6fFuvawC2JYu', 'ADMIN');

CREATE TABLE tb_student (
	student_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	registration_number VARCHAR(255) NOT NULL UNIQUE,
	email VARCHAR(255) NOT NULL UNIQUE,
	status VARCHAR(255) NOT NULL,
	blocked_until DATE
);

CREATE TABLE tb_credit (
	student_id BIGINT NOT NULL,
	meal_type VARCHAR(255) NOT NULL,
	credit_quantity INT NOT NULL,
	last_update TIMESTAMP NOT NULL,
	PRIMARY KEY (student_id, meal_type),
	FOREIGN KEY (student_id) REFERENCES tb_student(student_id) ON DELETE CASCADE
);

CREATE TABLE tb_transaction (
	transaction_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	student_id BIGINT NOT NULL,
	meal_type VARCHAR(255) NOT NULL,
	transaction_type VARCHAR(255) NOT NULL,
	quantity INT NOT NULL,
	transaction_date TIMESTAMP NOT NULL,
	FOREIGN KEY (student_id) REFERENCES tb_student(student_id) ON DELETE CASCADE
);