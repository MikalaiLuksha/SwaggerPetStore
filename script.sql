create table users
(
);

alter table users
    owner to postgres;

create table category
(
    id   integer,
    name varchar
);

alter table category
    owner to postgres;

create table pet
(
    id     integer,
    name   varchar,
    status varchar
);

alter table pet
    owner to postgres;

create table tag
(
    id   integer,
    name varchar
);

alter table tag
    owner to postgres;

create table data_category
(
    idpet      integer,
    idcategory integer
);

alter table data_category
    owner to postgres;

create table data_tags
(
    idpet integer,
    idtag integer[]
);

alter table data_tags
    owner to postgres;


