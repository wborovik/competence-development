insert into "admin" (id, is_deleted, name, surname, age, created_date)
values (1, false, 'Сергей', 'Иванов', 32, now());

insert into "customer" (id, is_deleted, name, surname, age, created_date)
values (1, false, 'Алексей', 'Потапов', 28, now());

insert into "executor" (id, is_deleted, name, surname, age, created_date)
values (1, false, 'Александр', 'Русских', 44, now());

insert into "orders" (id, is_deleted, customer_id, executor_id, created_date)
values (1, false, 1, 1, now());

