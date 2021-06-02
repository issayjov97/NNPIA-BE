create table if not exists posts(
    id serial primary key,
    title varchar(100) not null,
    type varchar(50) not null,
    category_id integer not null,
    description varchar(350) not null,
    details text,
    user_id integer not null,
    constraint fk_category foreign key(category_id) references categories(id),
    constraint fk_user foreign key(user_id) references users(id)
    )