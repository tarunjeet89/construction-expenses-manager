DROP TABLE IF EXISTS construction_members;
DROP TABLE IF EXISTS construction_members_aud;
DROP TABLE IF EXISTS construction_member_images;
DROP TABLE IF EXISTS construction_member_images_aud;
DROP TABLE IF EXISTS construction_member_documents;
DROP TABLE IF EXISTS construction_member_documents_aud;

CREATE TABLE IF NOT EXISTS construction_members
(
    id uuid NOT NULL,
    member_type character varying(255) NOT NULL,
	name character varying(255) NOT NULL,
	email character varying(255),
	first_name character varying(255),
	last_name character varying(255),
	dob date,
	phone_number character varying(20),
	alternate_phone_number character varying(20),
	address_line_1 text,
	address_line_2 text,
	city character varying(100),
	state character varying(100),
	pin_code integer,
	
    created_by character varying(255),
    updated_by character varying(255),
    created_on timestamp without time zone,
  	updated_on timestamp without time zone,
    
    CONSTRAINT construction_members_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS construction_members_aud
(
    id uuid NOT NULL,
    member_type character varying(255),
	name character varying(255),
	email character varying(255),
	first_name character varying(255),
	last_name character varying(255),
	dob date,
	phone_number character varying(20),
	alternate_phone_number character varying(20),
	address_line_1 text,
	address_line_2 text,
	city character varying(100),
	state character varying(100),
	pin_code integer,
	
    created_by character varying(255),
    updated_by character varying(255),
    created_on timestamp without time zone,
  	updated_on timestamp without time zone,
	
	REV 					integer not null,
    REVTYPE 				SMALLINT,
    
    PRIMARY KEY (id, REV, REVTYPE)  

);


CREATE TABLE IF NOT EXISTS construction_member_images
(
    id uuid NOT NULL,
    construction_member_id uuid NOT NULL,
    file_name character varying(255) NOT NULL,
    file_type character varying(50) NOT NULL,
    image_type character varying(50) NOT NULL,
    image_data oid NOT NULL,
    
    created_by character varying(255),
    updated_by character varying(255),
    created_on timestamp without time zone,
  	updated_on timestamp without time zone,
    
    CONSTRAINT construction_member_images_pkey PRIMARY KEY (id),
    CONSTRAINT construction_member_images__fk_construction_member_id FOREIGN KEY (construction_member_id)
        REFERENCES construction_members (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS construction_member_images_aud
(
    id uuid NOT NULL,
    construction_member_id uuid,
    file_name character varying(255),
    file_type character varying(50),
    image_type character varying(50),
    image_data oid,
    created_by character varying(255),
    updated_by character varying(255),
    created_on timestamp without time zone,
  	updated_on timestamp without time zone,
    
	REV 					integer not null,
    REVTYPE 				SMALLINT,
    
    PRIMARY KEY (id, REV, REVTYPE)  
);

CREATE TABLE IF NOT EXISTS construction_member_documents
(
    id uuid NOT NULL,
    construction_member_id uuid NOT NULL,
    file_name character varying(255) NOT NULL,
    file_type character varying(255) NOT NULL,
    document_type character varying(255) NOT NULL,
    document_data oid,
   
    created_by character varying(255),
    updated_by character varying(255),
    created_on timestamp without time zone,
  	updated_on timestamp without time zone,
   
    CONSTRAINT construction_member_documents_pkey PRIMARY KEY (id),
    CONSTRAINT construction_member_documents__fk_construction_member_id FOREIGN KEY (construction_member_id)
        REFERENCES construction_members (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS construction_member_documents_aud
(
    id uuid NOT NULL,
    construction_member_id uuid,
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
