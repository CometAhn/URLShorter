CREATE TABLE IF NOT EXISTS review(
   id int NOT NULL auto_increment,
   login_lid VARCHAR(10) NOT NULL,
   library_bid int not null,
   loan_id int not null,
   title VARCHAR(20),
   contents VARCHAR(20),
   date VARCHAR(20),
   score VARCHAR(20),
   PRIMARY KEY (id)
)default CHARSET=utf8;

Select * from review;