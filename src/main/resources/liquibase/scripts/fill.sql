--liquibase formatted sql

--changeset rsbryanskiy:fill

INSERT INTO cities (id, city_name) VALUES (1, 'Moscow');
INSERT INTO cities (id, city_name) VALUES (2, 'Saratov');
INSERT INTO cities (id, city_name) VALUES (3, 'Perm');