-- 2024-04-16
-- jspUser / mysql /jspdb

-- mysql -ujspUser -pmysql

create table board(
bno int not null auto_increment,
title varchar(100) not null,
writer varchar(50) not null,
content text,
regdate datetime default now(),
moddate datetime default now(), 
primary key(bno));

-- 2024-04-17
create table member(
id varchar(100),
pwd varchar(200) not null,
email varchar(200) not null,
age int default 0,
phone varchar(50),
regdate datetime default now(),
lastlogin datetime default now(),
primary key(id));

-- 2024-04-18
create table comment(
cno int auto_increment,
bno int not null,
writer varchar(100) default "unknown",
content varchar(1000) not null,
regdate datetime default now(),
primary key(cno));

-- 2020-04-22
alter table board add imageFile varchar(100);