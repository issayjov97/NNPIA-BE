create table if not exists skill_hours(
id serial primary key,
available int not null default 0,
earned  int not null default 0,
used  int not null default 0
)