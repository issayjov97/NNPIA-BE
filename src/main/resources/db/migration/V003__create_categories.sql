create table if not exists talent_exchange.categories(
    id serial primary key,
    name varchar(100) not null unique,
    image varchar(100)
    );