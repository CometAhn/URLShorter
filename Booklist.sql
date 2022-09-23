create database librarydb;
use librarydb;

CREATE TABLE IF NOT EXISTS booklist(
	bid int NOT NULL auto_increment,
	title VARCHAR(20),
	writer VARCHAR(20),
	description TEXT,
   	category VARCHAR(20),
	publisher VARCHAR(20),
	stock LONG,
	book_cover  VARCHAR(20),
	date VARCHAR(20),
	PRIMARY KEY (bid)
)default CHARSET=utf8;

desc BookList;