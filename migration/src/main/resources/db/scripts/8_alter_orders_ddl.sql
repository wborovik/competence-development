alter table work_order add column "order_check" jsonb;

update work_order set "order_check" = '{"price" : 200, "code" : "f2abc35"}' where id = 1;
update work_order set "order_check" = '{"price" : 400, "code" : "f3abc35"}' where id = 2;
update work_order set "order_check" = '{"price" : 220, "code" : "f4abc35"}' where id = 3;
update work_order set "order_check" = '{"price" : 150, "code" : "f5abc35"}' where id = 4;
update work_order set "order_check" = '{"price" : 280, "code" : "f6abc35"}' where id = 5;
update work_order set "order_check" = '{"price" : 440, "code" : "f7abc35"}' where id = 6;
update work_order set "order_check" = '{"price" : 550, "code" : "f8abc35"}' where id = 7;
update work_order set "order_check" = '{"price" : 210, "code" : "f9abc35"}' where id = 8;
update work_order set "order_check" = '{"price" : 300, "code" : "f1abc36"}' where id = 9;
update work_order set "order_check" = '{"price" : 260, "code" : "f2abc36"}' where id = 10;