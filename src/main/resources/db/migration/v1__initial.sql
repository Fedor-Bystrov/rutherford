CREATE TABLE users (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    last_login TIMESTAMP,
    application_name VARCHAR(64) NOT NULL,
    email VARCHAR(320) NOT NULL,
    email_confirmed BOOLEAN NOT NULL DEFAULT FALSE,
    password_hash TEXT NOT NULL
)

CREATE UNIQUE INDEX users_email_application_name_unique_idx ON users (email, application_name);


-- user_refresh_tokens
----------------------
-- id -> uuid
-- created_date
-- updated_date
-- expiration_date (nullable)
-- state
-- token_hash -> text
-- user_id
