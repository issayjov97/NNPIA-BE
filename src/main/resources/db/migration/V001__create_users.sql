create table if not exists users(
    id  SERIAL PRIMARY KEY,
    username varchar(50) not null unique,
    password varchar(100) not null,
    firstname varchar(100) not null,
    lastname varchar(100) not null,
    email varchar(100) not null unique,
    enabled boolean not null default true,
    rating number not null default true,
    skill_hours_id integer not  null ,
    constraint fk_skill_hours foreign key(skill_hours_id) references skill_hours(id)
    );