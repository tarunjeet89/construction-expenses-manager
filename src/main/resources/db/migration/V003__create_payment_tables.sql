DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS payments_aud;
DROP TABLE IF EXISTS payment_documents;
DROP TABLE IF EXISTS payment_documents_aud;

CREATE TABLE IF NOT EXISTS payments
(
    id uuid NOT NULL,
    construction_member_id uuid  NOT NULL,
    amount numeric(10,2) NOT NULL,
    payment_status character varying(255) NOT NULL,
    payment_date date,
    payment_mode character varying(255) NOT NULL,
    payment_intent text NOT NULL,
    work_from date,
    work_to date,
    
    created_by character varying(255),
    updated_by character varying(255),
    created_on timestamp without time zone,
  	updated_on timestamp without time zone,
    
    CONSTRAINT payments_pkey PRIMARY KEY (id),
    CONSTRAINT payments__fk_construction_member_id FOREIGN KEY (construction_member_id)
        REFERENCES construction_members (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS payments_aud
(
    id uuid NOT NULL,
    construction_member_id uuid,
    amount numeric(10,2),
    payment_status character varying(255),
    payment_date date,
    payment_mode character varying(255),
    payment_intent text,
    work_from date,
    work_to date,
    
    created_by character varying(255),
    updated_by character varying(255),
    created_on timestamp without time zone,
  	updated_on timestamp without time zone,
    
	REV 					integer not null,
    REVTYPE 				SMALLINT,
    
    PRIMARY KEY (id, REV, REVTYPE)  
);

CREATE TABLE IF NOT EXISTS payment_documents
(
    id uuid NOT NULL,
    payment_id uuid NOT NULL,
    file_name character varying(255) NOT NULL,
    file_type character varying(255) NOT NULL,
    document_type character varying(255) NOT NULL,
    document_data oid NOT NULL,
    
    created_by character varying(255),
    updated_by character varying(255),
    created_on timestamp without time zone,
  	updated_on timestamp without time zone,
    
    CONSTRAINT payment_documents_pkey PRIMARY KEY (id),
    CONSTRAINT payment_documents__fk_payment_id FOREIGN KEY (payment_id)
        REFERENCES payments (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS payment_documents_aud
(
    id uuid NOT NULL,
    payment_id uuid,
    file_name character varying(255),
    file_type character varying(255),
    document_type character varying(255),
    document_data oid,
    
    created_by character varying(255),
    updated_by character varying(255),
    created_on timestamp without time zone,
  	updated_on timestamp without time zone,
    
	REV 					integer not null,
    REVTYPE 				SMALLINT,
    
    PRIMARY KEY (id, REV, REVTYPE) 
);
