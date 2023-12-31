
    create sequence MyEntity_SEQ start with 1 increment by 50;

    create sequence T_BOOK_SEQ start with 1 increment by 50;

    create table MyEntity (
        id bigint not null,
        field varchar(255),
        primary key (id)
    );

    create table T_BOOK (
        nb_of_pages integer,
        price numeric(38,2),
        publication_date date,
        id bigint not null,
        isbn varchar(50),
        title varchar(200),
        description varchar(1000),
        image_url varchar(255),
        primary key (id)
    );
