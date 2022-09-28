use LibraryDB;

create table Cart ( 
	id int NOT NULL auto_increment,
	login_lid varchar(10) not null,
	library_bid int NOT NULL,
   PRIMARY KEY (id)
) default CHARSET=utf8;

select * from Cart;
# 삭제
#drop table Login;