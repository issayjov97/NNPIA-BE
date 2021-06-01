create table if not exists skill_hours(
id serial primary key,
available integer not null default 0,
earned  integer not null default 0,
used  integer not null default 0
)