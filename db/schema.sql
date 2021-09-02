CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE candidate (
    id SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE user_pool (
    email TEXT PRIMARY KEY,
    password TEXT
);

insert into user_pool values ('s595659@mail.ru', 'password');

create table users(
                      id SERIAL PRIMARY KEY,
                      email text UNIQUE,
                      password text
);
insert into users(email, password) values ('s595659@mail.ru', 'password');