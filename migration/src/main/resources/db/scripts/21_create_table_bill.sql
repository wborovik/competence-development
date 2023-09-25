create table bill
(
    id            bigint  not null,
    created       timestamp(6) not null,
    changed       timestamp(6),
    deleted       boolean not null,
    work_order_id bigint,
    price         numeric(9, 2),
    primary key (id)
);
alter table if exists bill add constraint bill_order_constraint foreign key (work_order_id) references work_order;

create table payment
(
    id            bigint  not null,
    created       timestamp(6) not null,
    changed       timestamp(6),
    deleted       boolean not null,
    work_order_id bigint,
    primary key (id)
);
alter table if exists bill add constraint payment_order_constraint foreign key (work_order_id) references work_order;

create table evaluation
(
    id         bigint  not null,
    created    timestamp(6) not null,
    changed    timestamp(6),
    deleted    boolean not null,
    evaluation double precision check (evaluation >= 0.00 and evaluation <= 5.00),
    count      bigint,
    primary key (id)
);

alter table executor
    add column evaluation_id bigint;

alter table if exists executor add constraint executor_evaluation_constraint foreign key (evaluation_id) references evaluation;

insert into evaluation (id, created, deleted, evaluation, count)
values (1, now(), false, 4.333, 3);

insert into evaluation (id, created, deleted, evaluation, count)
values (2, now(), false, 4.16667, 6);

insert into evaluation (id, created, deleted, evaluation, count)
values (3, now(), false, 4, 5);

insert into evaluation (id, created, deleted, evaluation, count)
values (4, now(), false, 5, 1);

insert into evaluation (id, created, deleted, evaluation, count)
values (5, now(), false, 4.1, 10);

insert into evaluation (id, created, deleted, evaluation, count)
values (6, now(), false, 4.14286, 7);

insert into evaluation (id, created, deleted, evaluation, count)
values (7, now(), false, 4.25, 8);

insert into evaluation (id, created, deleted, evaluation, count)
values (8, now(), false, 3.66667, 3);

insert into evaluation (id, created, deleted, evaluation, count)
values (9, now(), false, 3.66667, 9);

insert into evaluation (id, created, deleted, evaluation, count)
values (10, now(), false, 3.8, 5);

update executor set evaluation_id = 1 where id = 1;
update executor set evaluation_id = 2 where id = 2;
update executor set evaluation_id = 3 where id = 3;
update executor set evaluation_id = 4 where id = 4;
update executor set evaluation_id = 5 where id = 5;
update executor set evaluation_id = 6 where id = 6;
update executor set evaluation_id = 7 where id = 7;
update executor set evaluation_id = 8 where id = 8;
update executor set evaluation_id = 9 where id = 9;
update executor set evaluation_id = 10 where id = 10;


