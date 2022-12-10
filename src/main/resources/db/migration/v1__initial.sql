CREATE TABLE users (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    last_login TIMESTAMP,
    application_name VARCHAR(64) NOT NULL,
    email VARCHAR(320) NOT NULL,
    email_confirmed BOOLEAN NOT NULL DEFAULT FALSE,
    password_hash TEXT NOT NULL
);

-- TODO email should be unque ignoring case

CREATE UNIQUE INDEX users_email_application_name_unique_idx ON users (email, application_name);

CREATE TABLE user_refresh_tokens (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    expiration TIMESTAMP,
    state VARCHAR(32) NOT NULL,
    token_hash TEXT NOT NULL
    user_id UUID REFERENCES users (id)
);

CREATE INDEX user_refresh_tokens_token_hash_idx ON user_refresh_tokens (token_hash);