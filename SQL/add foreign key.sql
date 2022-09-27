use librarydb;

-- cart fk
alter table cart add foreign key(lid) references login( lid )on delete cascade;
alter table cart add foreign key(bid) references booklist( bid )on delete cascade;

-- loan fk
alter table loan add foreign key(lid) references login( lid )on delete cascade;
alter table loan add foreign key(bid) references booklist( bid )on delete cascade;

-- recommend fk
alter table recommend add foreign key(bid) references booklist( bid )on delete cascade;

-- review fk
alter table review add foreign key(lid) references login( lid )on delete cascade;
alter table review add foreign key(bid) references booklist( bid )on delete cascade;