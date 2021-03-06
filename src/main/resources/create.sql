CREATE SCHEMA IF NOT EXISTS beer;

CREATE TABLE IF NOT EXISTS beer (
	id	INT(20) PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	ingredients	VARCHAR(100) NOT NULL,
	alcohol_content VARCHAR(10) NOT NULL,
	price DECIMAL(10,2) NOT NULL,
	category VARCHAR(20) NOT NULL
);