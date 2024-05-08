create table users(
    id bigint auto_increment primary key,
    username varchar(255) unique not null ,
    telegramChatId bigint not null
);

create index username_idx on users (username);