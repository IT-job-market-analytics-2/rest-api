create table if not exists vacancy_analytics(
    id               bigint auto_increment primary key,
    date             date not null ,
    query            varchar(255),
    vacancy_count    bigint,
    average_salary   decimal
);

CREATE UNIQUE INDEX date_query_idx ON vacancy_analytics (date, query);