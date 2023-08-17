update orders set title = 'Изменить картинку в фотошопе' where id = 1;
update orders set title = 'Записать короткий промо-ролик' where id = 2;
update orders set title = 'Написать статью о короновирусе' where id = 3;
update orders set title = 'Создать картинку для обложки группы ВК' where id = 4;
update orders set title = 'Рекламное видео' where id = 5;
update orders set title = 'Редактировать статью' where id = 6;
update orders set title = 'Записать поздравительное аудио' where id = 7;
update orders set title = 'Записать видео обучающее игре на гитаре' where id = 8;
update orders set title = 'Написать статью о выращивании огурцов на Крайнем Севере' where id = 9;
update orders set title = 'Записать реп аудио бит' where id = 10;

insert into cls_order_category (global_id, created, deleted, description, designation)
values (1, now(), false,'Написание и редактирование текста', 'Text');

insert into cls_order_category(global_id, created, deleted, description, designation)
values (2, now(), false, 'Работа с изображениями', 'Image');

insert into cls_order_category(global_id, created, deleted, description, designation)
values (3, now(), false, 'Запись и редактирование аудио', 'Audio');

insert into cls_order_category(global_id, created, deleted, description, designation)
values (4, now(), false, 'Запись и редактирование видео', 'Video');

update orders set category_global_id = 2 where id = 1;
update orders set category_global_id = 3 where id = 2;
update orders set category_global_id = 1 where id = 3;
update orders set category_global_id = 2 where id = 4;
update orders set category_global_id = 4 where id = 5;
update orders set category_global_id = 1 where id = 6;
update orders set category_global_id = 3 where id = 7;
update orders set category_global_id = 4 where id = 8;
update orders set category_global_id = 1 where id = 9;
update orders set category_global_id = 3 where id = 10;
