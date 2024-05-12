create table if not exists users(
    id bigint auto_increment primary key,
    password varchar(255) not null,
    username varchar(255) unique not null ,
    telegramChatId bigint
);

create index username_idx on users (username);