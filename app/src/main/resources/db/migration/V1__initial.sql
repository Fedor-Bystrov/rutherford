CREATE TABLE auth_user (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    last_login TIMESTAMP,
    application_name VARCHAR(64) NOT NULL,
    email TEXT NOT NULL,
    email_confirmed BOOLEAN NOT NULL DEFAULT FALSE,
    password_hash TEXT NOT NULL
);

CREATE UNIQUE INDEX users_email_application_name_unique_idx ON auth_user (LOWER(email), application_name);

CREATE TABLE auth_user_token (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    expiration TIMESTAMP,
    state VARCHAR(32) NOT NULL,
    token_hash TEXT NOT NULL,
    user_id UUID REFERENCES auth_user (id) NOT NULL
);

CREATE INDEX auth_user_token_token_hash_idx ON auth_user_token (token_hash);