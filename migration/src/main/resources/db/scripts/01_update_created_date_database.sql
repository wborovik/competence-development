alter table "admin" alter column created_date set not null;
alter table "customer" alter column created_date set not null;
alter table "executor" alter column created_date set not null;
alter table "orders" alter column created_date set not null;