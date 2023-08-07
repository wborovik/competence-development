create table admin (id bigint not null,
changed_date timestamp(6),
created_date timestamp(6),
is_deleted boolean not null,
age integer,
name varchar(255),
surname varchar(255),
primary key (id));

create table customer (id bigint not null,
changed_date timestamp(6),
created_date timestamp(6),
is_deleted boolean not null,
age integer, name varchar(255),
surname varchar(255),
primary key (id));

create table executor (id bigint not null,
changed_date timestamp(6),
created_date timestamp(6),
is_deleted boolean not null,
age integer,
name varchar(255),
surname varchar(255),
primary key (id));

create table orders (id bigint not null,
changed_date timestamp(6),
created_date timestamp(6),
is_deleted boolean not null,
customer_id bigint,
executor_id bigint,
primary key (id));

create sequence global_sequence start with 1 increment by 1;

alter table if exists orders add constraint FK624gtjin3po807j3vix093tlf foreign key (customer_id) references customer;

alter table if exists orders add constraint FKnniiumcfln8h9ux9hjt8fpjtx foreign key (executor_id) references executor;