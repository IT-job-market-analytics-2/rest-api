create table subscriptions(
    id bigint auto_increment primary key,
    userId bigint,
    query varchar(255),
    foreign key (userId) references users(id)
);

create index query_idx on subscriptions (query);