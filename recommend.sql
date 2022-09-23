use librarydb;

create table if not exists recommend(
	id int NOT NULL auto_increment,
	bid int NOT NULL,
    month int not null,
   PRIMARY KEY (id)
) default CHARSET=utf8;

select * from recommend;