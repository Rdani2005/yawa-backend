DROP SCHEMA IF EXISTS customer CASCADE;

CREATE SCHEMA customer;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE customer.customers
(
    id uuid NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    last_name character varying COLLATE pg_catalog."default" NOT NULL,
    identification character varying COLLATE pg_catalog."default" NOT NULL,
    birth_day TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT customers_pkey PRIMARY KEY (id)
);

DROP MATERIALIZED VIEW IF EXISTS customer.account_customer_m_view;

CREATE MATERIALIZED VIEW customer.account_customer_m_view
TABLESPACE pg_default
AS
SELECT id,
       name,
       last_name,
       identification,
       birth_day,
       created_at
FROM customer.customers
    WITH DATA;

refresh materialized VIEW customer.account_customer_m_view;

DROP function IF EXISTS customer.refresh_account_customer_m_view;

CREATE OR replace function customer.refresh_account_customer_m_view()
returns trigger
AS '
BEGIN
    refresh materialized VIEW customer.account_customer_m_view;
    return null;
END;
'  LANGUAGE plpgsql;

DROP trigger IF EXISTS refresh_account_customer_m_view ON customer.customers;

CREATE trigger refresh_account_customer_m_view
    after INSERT OR UPDATE OR DELETE OR truncate
                    ON customer.customers FOR each statement
                        EXECUTE PROCEDURE customer.refresh_account_customer_m_view();