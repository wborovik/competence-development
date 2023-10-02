drop table executor_category;

alter table executor add column work_category_global_id bigint;

alter table if exists executor add constraint executor_category foreign key (work_category_global_id) references cls_order_category;

insert into cls_order_category(global_id, created, deleted, description, designation)
values (6,now(), false, 'Разработка на языке Java', 'java_development');

update executor set work_category_global_id = 2 where id = 1;
update executor set work_category_global_id = 1 where id = 2;
update executor set work_category_global_id = 1 where id = 3;
update executor set work_category_global_id = 2 where id = 4;
update executor set work_category_global_id = 3 where id = 5;
update executor set work_category_global_id = 3 where id = 6;
update executor set work_category_global_id = 4 where id = 7;
update executor set work_category_global_id = 4 where id = 8;
update executor set work_category_global_id = 5 where id = 9;
update executor set work_category_global_id = 6 where id = 10;

update work_order set executor_id = 5 where id = 2;
update work_order set executor_id = 9 where id = 3;