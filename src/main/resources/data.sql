CREATE TABLE TB_USER (
        username varchar(255) not null,
        password varchar(255) not null,
        primary key (username)
    );
INSERT INTO TB_USER (username, password) VALUES ('admin','admin');
commit;