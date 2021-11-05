--liquibase formatted sql

--changeset rrmasgutov:init

create table account1
(
    id bigserial constraint account_pk primary key,
    amount int constraint positive_amount CHECK (amount >= 0),
    version int
);
