create table if not exists talent_exchange.transactions(
    id serial not null primary key,
    author integer not null,
    requested_at timestamp not null,
    approved_by_author_at timestamp ,
    approved_by_requester_at timestamp ,
    delivered_by_author_at timestamp ,
    delivered_by_requester_at timestamp,
    agreed_skill_hours integer ,
    status varchar(50) not null,
    post_id integer not null,
    constraint fk_user foreign key(author) references talent_exchange.users(id),
    constraint fk_post foreign key(post_id) references talent_exchange.posts(id)
    )