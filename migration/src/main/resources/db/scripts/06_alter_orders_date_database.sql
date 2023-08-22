alter table work_order
    add column title varchar(255);
alter table work_order add column category_global_id bigint;

create table cls_order_category
(global_id bigint not null,
    changed timestamp(6),
    created timestamp(6),
    deleted boolean not null,
    description varchar(255),
    designation varchar(255),
    primary key (global_id)
);

alter table if exists work_order add constraint FKnniiumcfln8h9ux9hjt8fpjtr foreign key (category_global_id) references cls_order_category;