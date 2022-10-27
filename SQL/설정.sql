create database url_shorterdb;

use url_shorterdb;


CREATE TABLE IF NOT EXISTS url(
	id int NOT NULL auto_increment primary key,
	addr VARCHAR(50),
	shorter VARCHAR(20),
	custom_check boolean,
    timestampt VARCHAR(20),
    last_used VARCHAR(20)
)default CHARSET=utf8;