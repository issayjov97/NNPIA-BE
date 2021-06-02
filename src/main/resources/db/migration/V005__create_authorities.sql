create table if not exists  authorities (
                                                            id serial primary key,
                                                            user_id integer not null,
                                                            authority varchar(50) not null,
    constraint fk_authorities_users foreign key(user_id) references users(id)
    );
create unique index ix_auth_username on authorities (user_id,authority);
