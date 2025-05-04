-- REMOVE the "CREATE DATABASE" part

-- Table: public.shrink_urls
CREATE TABLE IF NOT EXISTS public.shrink_urls (
    id bigint NOT NULL,
    original_url varchar(255) NOT NULL,
    shrink_code varchar(255) NOT NULL,
    category_info text,
    safety_info text,
    CONSTRAINT shrink_urls_pkey PRIMARY KEY (id),
    CONSTRAINT shrink_urls_shrink_code_key UNIQUE (shrink_code)
);

-- Sequence: public.entry_seq
CREATE SEQUENCE IF NOT EXISTS public.entry_seq
    INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;
