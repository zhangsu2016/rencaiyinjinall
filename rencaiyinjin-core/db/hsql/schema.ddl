
    drop table DemoModel if exists;

    create table DemoModel (
        id bigint generated by default as identity (start with 1),
        age integer not null,
        name varchar(255),
        primary key (id)
    );
