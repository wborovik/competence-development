create table cls_work_speed
(
    global_id bigint       not null,
    created   timestamp(6) not null,
    changed   timestamp(6),
    deleted   boolean      not null,
    description varchar(255),
    speed     varchar(255),
    primary key (global_id)
);

insert into cls_work_speed(global_id, created, deleted, description, speed)
values (1, now(), false, 'Очень медленная скорость выполнения заказов', 'very_slow');

insert into cls_work_speed(global_id, created, deleted, description, speed)
values (2, now(), false, 'Медленная скорость выполнения заказов', 'slow');

insert into cls_work_speed(global_id, created, deleted, description, speed)
values (3, now(), false, 'Средняя скорость выполнения заказов', 'average');

insert into cls_work_speed(global_id, created, deleted, description, speed)
values (4, now(), false, 'Быстрая скорость выполнения заказов', 'fast');

insert into cls_work_speed(global_id, created, deleted, description, speed)
values (5, now(), false, 'Очень быстрая скорость выполнения заказов', 'very_fast');

alter table executor add column work_speed_global_id bigint;

alter table if exists executor add constraint executor_speed foreign key (work_speed_global_id) references cls_work_speed;

update executor set work_speed_global_id = 2 where id = 1;
update executor set work_speed_global_id = 4 where id = 2;
update executor set work_speed_global_id = 1 where id = 3;
update executor set work_speed_global_id = 3 where id = 4;
update executor set work_speed_global_id = 4 where id = 5;
update executor set work_speed_global_id = 2 where id = 6;
update executor set work_speed_global_id = 3 where id = 7;
update executor set work_speed_global_id = 5 where id = 8;
update executor set work_speed_global_id = 3 where id = 9;
update executor set work_speed_global_id = 5 where id = 10;