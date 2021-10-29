drop table if exists users;
drop table if exists persons;
drop table if exists events;
drop table if exists auth_tokens;

create table users
(
    username varchar(255) not null primary key,
    passwd varchar(255) not null, -- salt/hash?
    email varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    gender char(1) not null,
    person_id varchar(255) not null
);

create table persons
(
    person_id varchar(255) not null primary key,
    username varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    gender char(1) not null,
    father_id varchar(255),
    mother_id varchar(255),
    spouse_id varchar(255),
    foreign key(username) references users(username) on delete cascade,
    foreign key(father_id) references persons(person_id) on delete set null on update cascade,
    foreign key(mother_id) references persons(person_id) on delete set null on update cascade,
    foreign key(spouse_id) references persons(person_id) on delete set null on update cascade
);

create table events
(
    event_id varchar(255) not null primary key,
    username varchar(255) not null,
    person_id varchar(255) not null,
    latitude float not null,
    longitude float not null,
    country varchar(255) not null,
    city varchar(255) not null,
    event_type varchar(255) not null,
    year integer not null,
    foreign key(username) references users(username) on delete cascade,
    foreign key(person_id) references persons(person_id) on delete cascade
);

create table auth_tokens
(
    auth_token varchar(255) not null,
    username varchar(255) not null,
    foreign key(username) references users(username) on delete cascade
);