use LibraryDB;

create table Cart ( 
	id int NOT NULL auto_increment,
	lid varchar(10) not null,
	bid int NOT NULL,
   PRIMARY KEY (id)
) default CHARSET=utf8;

select * from Cart;
# 삭제
#drop table Login;