--liquibase formatted sql

--changeset i.ilyin:index

create INDEX id_idx ON account1 (id);
