use LibraryDB;

create table Login ( 
    lid varchar(10) not null,
    password varchar(10) not null,
    name varchar(10) not null,
    gender varchar(4),
    birth  varchar(10),
    email  varchar(30),
    phone varchar(20),
    address varchar(90),
    regist_day varchar(50), 
    grade boolean not null default 0,
    used boolean not null default 1,
    overdue varchar(20), 
    review_count int not null default 0,
    loan_count int not null default 0,
    primary key(lid) 
) default CHARSET=utf8;

select * from Login;
# �궘�젣
#drop table Login;
