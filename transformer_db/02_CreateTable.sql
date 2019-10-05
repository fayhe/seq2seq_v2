--Set the default tablespace to use before any create table statements.
SET default_tablespace = transformer;


CREATE SEQUENCE task_id_seq START 1;
CREATE SEQUENCE client_id_seq START 1;
CREATE SEQUENCE doc_type_id_seq START 1;
CREATE SEQUENCE client_task_doc_type_id_seq START 1;
CREATE SEQUENCE model_type_id_seq START 1;
CREATE SEQUENCE training_data_id_seq START 1;
CREATE SEQUENCE model_id_seq START 1;
CREATE SEQUENCE training_process_id_seq START 1;

-- Table: public.task
CREATE TABLE public.task
(
    task_id bigint NOT NULL default nextval('task_id_seq'),
    task_name character varying(100) NOT NULL,
    description character varying(1000),
    created_date timestamp with time zone default now() NOT NULL,
    updated_date timestamp with time zone default now() NOT NULL,
    is_deleted char default 'N' NOT NULL,
    CONSTRAINT task_pkey PRIMARY KEY (task_name)
)
WITH (
    OIDS = FALSE
);
ALTER TABLE public.task
    OWNER to postgres;



-- Table: public.client
CREATE TABLE public.client
(
    client_id bigint NOT NULL default nextval('client_id_seq'),
    client_name character varying(100) NOT NULL,
    description character varying(1000),
    created_date timestamp with time zone default now() NOT NULL,
    updated_date timestamp with time zone default now() NOT NULL,
    is_deleted char default 'N' NOT NULL,
    status character varying(100) NOT NULL,
    CONSTRAINT client_pkey PRIMARY KEY (client_name)
)
WITH (
    OIDS = FALSE
);
ALTER TABLE public.client
    OWNER to postgres;

-- Table: public.doc_type
CREATE TABLE public.doc_type
(
    doc_type_id bigint NOT NULL default nextval('doc_type_id_seq'),
    doc_type_name character varying(100) NOT NULL,
    client_name character varying(100) NOT NULL,
    description character varying(1000),
    created_date timestamp with time zone default now() NOT NULL,
    updated_date timestamp with time zone default now() NOT NULL,
    is_deleted char default 'N' NOT NULL,
    CONSTRAINT doc_type_client_fkey FOREIGN KEY (client_name)
        REFERENCES public.client (client_name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT doc_type_pkey PRIMARY KEY (doc_type_name)
)
WITH (
    OIDS = FALSE
);
ALTER TABLE public.doc_type
    OWNER to postgres;


-- Table: public.client_task_doc_type
CREATE TABLE public.client_task_doc_type
(
    client_task_doc_type_id bigint NOT NULL default nextval('client_task_doc_type_id_seq'),
    client_name character varying(100) NOT NULL,
	task_name character varying(100) NOT NULL,
    doc_type_name character varying(100) NOT NULL,
    created_date timestamp with time zone default now() NOT NULL,
    updated_date timestamp with time zone default now() NOT NULL,
    is_deleted char default 'N' NOT NULL,
    CONSTRAINT client_task_doc_type_client_fkey FOREIGN KEY (client_name)
        REFERENCES public.client (client_name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
	CONSTRAINT client_task_doc_type_task_fkey FOREIGN KEY (task_name)
        REFERENCES public.task (task_name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION, 
	CONSTRAINT client_task_doc_type_doc_type_fkey FOREIGN KEY (doc_type_name)
        REFERENCES public.doc_type (doc_type_name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,     
    CONSTRAINT client_task_doc_type_pkey PRIMARY KEY (client_name, task_name, doc_type_name)
)
WITH (
    OIDS = FALSE
);
ALTER TABLE public.client_task_doc_type
    OWNER to postgres;        

-- Table: public.model_type
CREATE TABLE public.model_type
(
    model_type_id bigint NOT NULL default nextval('model_type_id_seq'),
    model_type_name character varying(100) NOT NULL,
    task_name character varying(100) NOT NULL,
    description character varying(1000),
    created_date timestamp with time zone default now() NOT NULL,
    updated_date timestamp with time zone default now() NOT NULL,
    is_deleted char default 'N' NOT NULL,
    detail character varying(1000),
    CONSTRAINT model_type_task_fkey FOREIGN KEY (task_name)
        REFERENCES public.task (task_name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT model_type_pkey PRIMARY KEY (model_type_name)
)
WITH (
    OIDS = FALSE
);
ALTER TABLE public.model_type
    OWNER to postgres;   
    
-- Table: public.training_data
CREATE TABLE public.training_data
(
    training_data_id bigint NOT NULL default nextval('training_data_id_seq'),
    client_name character varying(100) NOT NULL,
	task_name character varying(100) NOT NULL,
    doc_type_name character varying(100) NOT NULL,
    created_date timestamp with time zone default now() NOT NULL,
    updated_date timestamp with time zone default now() NOT NULL,
    is_deleted char default 'N' NOT NULL,
    detail character varying(1000),
    CONSTRAINT training_data_client_fkey FOREIGN KEY (client_name, task_name, doc_type_name)
        REFERENCES public.client_task_doc_type (client_name, task_name, doc_type_name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,  
    CONSTRAINT training_data_pkey PRIMARY KEY (training_data_id)
)
WITH (
    OIDS = FALSE
);
ALTER TABLE public.training_data
    OWNER to postgres; 
    
-- Table: public.model
CREATE TABLE public.model
(
    model_id bigint NOT NULL default nextval('model_id_seq'),
    model_name character varying(100) NOT NULL,
    model_type_name character varying(100) NOT NULL,
    doc_type_name character varying(100) NOT NULL,
    description character varying(1000),
    created_date timestamp with time zone default now() NOT NULL,
    updated_date timestamp with time zone default now() NOT NULL,
    is_deleted char default 'N' NOT NULL,
    is_model_used_for_prediction char default 'N' NOT NULL,
    model_path character varying(1000),
	model_metrics character varying(1000),
    CONSTRAINT model_model_type_fkey FOREIGN KEY (model_type_name)
        REFERENCES public.model_type (model_type_name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
	CONSTRAINT model_doc_type_fkey FOREIGN KEY (doc_type_name)
        REFERENCES public.doc_type (doc_type_name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,    
    CONSTRAINT model_pkey PRIMARY KEY (model_name)
)
WITH (
    OIDS = FALSE
);
ALTER TABLE public.model
    OWNER to postgres;     
    
-- Table: public.training_process
CREATE TABLE public.training_process
(
    training_process_id bigint NOT NULL default nextval('training_process_id_seq'),
    model_name character varying(100) NOT NULL,
    created_date timestamp with time zone default now() NOT NULL,
    updated_date timestamp with time zone default now() NOT NULL,
    is_deleted char default 'N' NOT NULL,
    status character varying(100) NOT NULL,
    trained_model_path character varying(1000),
	trained_model_metrics character varying(1000),
	detail character varying(1000),
    CONSTRAINT training_process_model_fkey FOREIGN KEY (model_name)
        REFERENCES public.model (model_name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,   
    CONSTRAINT training_process_pkey PRIMARY KEY (training_process_id)
)
WITH (
    OIDS = FALSE
);
ALTER TABLE public.training_process
    OWNER to postgres;  