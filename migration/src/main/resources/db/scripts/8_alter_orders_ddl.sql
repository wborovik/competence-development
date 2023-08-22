alter table orders add column "check" jsonb;

update orders set "check" = '{"price" : 200, "code" : "f2abc35"}' where id = 1;
update orders set "check" = '{"price" : 400, "code" : "f3abc35"}' where id = 2;
update orders set "check" = '{"price" : 220, "code" : "f4abc35"}' where id = 3;
update orders set "check" = '{"price" : 150, "code" : "f5abc35"}' where id = 4;
update orders set "check" = '{"price" : 280, "code" : "f6abc35"}' where id = 5;
update orders set "check" = '{"price" : 440, "code" : "f7abc35"}' where id = 6;
update orders set "check" = '{"price" : 550, "code" : "f8abc35"}' where id = 7;
update orders set "check" = '{"price" : 210, "code" : "f9abc35"}' where id = 8;
update orders set "check" = '{"price" : 300, "code" : "f1abc36"}' where id = 9;
update orders set "check" = '{"price" : 260, "code" : "f2abc36"}' where id = 10;