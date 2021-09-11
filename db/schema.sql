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

ALTER TABLE candidate ADD COLUMN cityId integer;
update candidate SET cityId = 1 where id = 1;
update candidate SET cityId = 2 where id = 2;
update candidate SET cityId = 3 where id = 3;
update candidate SET cityId = 4 where id = 4;
update candidate SET cityId = 5 where id = 5;

create table cities (
                        id SERIAL PRIMARY KEY,
                        name text UNIQUE
);

insert into cities(name) values ('Москва');
insert into cities(name) values ('Санкт-Петербург');
insert into cities(name) values ('Казань');
insert into cities(name) values ('Ростов-на-Дону');
insert into cities(name) values ('Рязань');
insert into cities(name) values ('Нижний Новгород');
insert into cities(name) values ('Екатеринбург');
insert into cities(name) values ('Волгоград');
insert into cities(name) values ('Тверь');
insert into cities(name) values ('Уфа');

ALTER TABLE candidate ADD COLUMN date timestamp;
update candidate SET date = '2021-09-10 20:00:00' where id = 1;
update candidate SET date = '2021-09-10 10:00:00' where id = 2;
update candidate SET date = '2021-09-09 20:00:00' where id = 3;
update candidate SET date = '2021-09-09 20:00:00' where id = 4;
update candidate SET date = '2021-09-08 20:00:00' where id = 5;

ALTER TABLE candidate ADD COLUMN date timestamp;
update candidate SET date = '2021-09-10 20:00:00' where id = 1;
update candidate SET date = '2021-09-10 10:00:00' where id = 2;
update candidate SET date = '2021-09-09 20:00:00' where id = 3;
update candidate SET date = '2021-09-09 20:00:00' where id = 4;

insert into candidate(name, cityId, date) values ('Richard', 6, current_timestamp);
insert into post(name, date) values ('Java Architect', current_timestamp);