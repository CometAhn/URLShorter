use librarydb;
create table if not exists loan( 
	id int NOT NULL auto_increment,
    lid varchar(10) NOT NULL,
	bid int NOT NULL,
    start_date  varchar(20) NOT NULL,
    return_date varchar(20),
    end_date  varchar(20) NOT NULL,
    status boolean not null,
   reviewed boolean not null default 0,
   PRIMARY KEY (id)
) default CHARSET=utf8;

select * from Loan;