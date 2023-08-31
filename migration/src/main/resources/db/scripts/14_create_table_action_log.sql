create table action_log
(
    id bigint not null,
    method_name varchar,
    request varchar,
    response varchar,
    exception_message varchar,
    actual_date timestamp(6) not null,
    primary key (id)
);

create sequence action_log_sequence start with 1 increment by 1;