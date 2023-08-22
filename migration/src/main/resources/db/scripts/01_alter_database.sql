insert into "admin" (id, deleted, name, surname, age, created)
values (1, false, 'Сергей', 'Иванов', 32, now());

insert into "customer" (id, deleted, name, surname, age, created)
values (1, false, 'Алексей', 'Потапов', 28, now());

insert into "executor" (id, deleted, name, surname, age, created)
values (1, false, 'Александр', 'Русских', 44, now());

insert into "work_order" (id, deleted, customer_id, executor_id, created)
values (1, false, 1, 1, now());

