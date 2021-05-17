create table if not exists  talent_exchange.authorities (
                                                            id serial primary key,
                                                            user_id integer not null,
                                                            authority varchar(50) not null,
    constraint fk_authorities_users foreign key(user_id) references talent_exchange.users(id)
    );
create unique index ix_auth_username on talent_exchange.authorities (user_id,authority);
