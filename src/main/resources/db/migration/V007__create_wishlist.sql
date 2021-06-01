create table if not exists  wishlist(
    user_id integer not null references users(id),
    post_id integer  not null references posts(id),
    primary key (user_id, post_id)
    )