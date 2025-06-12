CREATE TABLE mst_users (
       id SERIAL PRIMARY KEY,
       email VARCHAR(255) NOT NULL UNIQUE,
       password VARCHAR(255) NOT NULL,
       username VARCHAR NOT NULL UNIQUE,
       role_id INT REFERENCES mst_roles(id) NULL,

       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
       created_by VARCHAR(255) NULL,
       updated_at TIMESTAMP,
       updated_by VARCHAR(255),
       is_active BOOLEAN DEFAULT TRUE,
       is_delete BOOLEAN NOT NULL DEFAULT FALSE,
       deleted_by VARCHAR(36),
       deleted_at TIMESTAMP
);