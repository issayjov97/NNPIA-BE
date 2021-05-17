create table if not exists  talent_exchange.wishlist(
    user_id integer not null references talent_exchange.users(id),
    post_id integer  not null references talent_exchange.posts(id),
    primary key (user_id, post_id)
    )