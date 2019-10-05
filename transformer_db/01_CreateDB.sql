--Use the created tablespace "transformer" instead of pg_default in the create database statement 

CREATE DATABASE transformerdb
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'    
    TABLESPACE = transformer
    CONNECTION LIMIT = -1; 