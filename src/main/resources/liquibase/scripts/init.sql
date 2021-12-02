--liquibase formatted sql

--changeset rsbryanskiy:init
CREATE TABLE "customers"
(
    "id"               BIGSERIAL PRIMARY KEY,
    "state"            int,
    "telegram_name"    varchar,
    "telegram_chat_id" bigint
);

CREATE TABLE "meetings"
(
    "id"           BIGSERIAL PRIMARY KEY,
    "name"         varchar,
    "city_id"      bigint,
    "address"      varchar,
    "meeting_date" timestamp
);

CREATE TABLE "cities"
(
    "id"        bigint PRIMARY KEY,
    "city_name" varchar
);

CREATE TABLE "customer_meetings"
(
    "customer_id" bigint,
    "meeting_id"  bigint,
    "role"   int,
    PRIMARY KEY ("customer_id", "meeting_id")
);

ALTER TABLE "customer_meetings"
    ADD FOREIGN KEY ("customer_id") REFERENCES "customers" ("id");

ALTER TABLE "customer_meetings"
    ADD FOREIGN KEY ("meeting_id") REFERENCES "meetings" ("id");

ALTER TABLE "meetings"
    ADD FOREIGN KEY ("city_id") REFERENCES "cities" ("id");

CREATE SEQUENCE "hibernate_sequence";