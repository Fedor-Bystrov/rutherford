CREATE TABLE auth_user (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    last_login TIMESTAMP,
    application_name VARCHAR(64) NOT NULL,
    email TEXT NOT NULL,
    email_confirmed BOOLEAN NOT NULL DEFAULT FALSE,
    salt BYTEA NOT NULL,
    password_hash BYTEA NOT NULL
);

CREATE UNIQUE INDEX users_email_application_name_unique_idx ON auth_user (LOWER(email), application_name);
