DROP SCHEMA IF EXISTS "account" CASCADE;

CREATE SCHEMA "account";


CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS "account".accounts CASCADE;
CREATE TABLE "account".accounts
(
    id uuid NOT NULL,
    customer_id uuid NOT NULL,
    account_number character varying COLLATE pg_catalog."default",
    initial_amount numeric(10,2) NOT NULL,
    actual_amount numeric(10,2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT accounts_pkey PRIMARY KEY (id)
);

CREATE TABLE "account".customers
(
    customer_id uuid NOT NULL,
    identification character varying COLLATE pg_catalog."default" NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    last_name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT customers_pkey PRIMARY KEY (customer_id)
);