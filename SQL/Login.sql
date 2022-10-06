use LibraryDB;

create table Login ( 
    lid varchar(10) not null,
    password varchar(40) not null,
    token varchar(15) not null default 0,
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
    emailkey varchar(10),
    checked boolean not null default 0,
    temppw boolean default 0,
    primary key(lid) 
) default CHARSET=utf8;

select * from Login;
# 삭제
#drop table Login;
