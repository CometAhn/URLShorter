create database url_shorterdb;

use url_shorterdb;


CREATE TABLE IF NOT EXISTS url(
	id int NOT NULL auto_increment primary key,
	addr VARCHAR(50),
	shorter VARCHAR(20),
    timestampt VARCHAR(20)
)default CHARSET=utf8;