CREATE TABLE meta (
    id SERIAL PRIMARY KEY
  , uuid UUID NOT NULL
  , created_at TIMESTAMP NOT NULL DEFAULT NOW()
  , created_by_id SMALLINT NOT NULL
  , opens SMALLINT NOT NULL DEFAULT 0
  , opened_at TIMESTAMP
  , opened_by_id SMALLINT
  , edits SMALLINT NOT NULL DEFAULT 0
  , edited_at TIMESTAMP
  , edited_by_id SMALLINT
  , deleted_at TIMESTAMP
  , deleted_by_id SMALLINT
  , restored_at TIMESTAMP
  , restored_by_id SMALLINT
  , is_deleted BOOLEAN NOT NULL DEFAULT FALSE
  , notes HTML
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY
  , meta_id SMALLINT UNIQUE NOT NULL
  , email_address EMAIL_ADDRESS NOT NULL
  , username VARCHAR(20) UNIQUE NOT NULL
  , password VARCHAR(20) NOT NULL
);

CREATE TABLE stock (
    id SERIAL PRIMARY KEY
  , meta_id SMALLINT UNIQUE NOT NULL
  , sku SKU UNIQUE NOT NULL
  , name VARCHAR(255) NOT NULL
  , break_even_price NUMERIC(2) NOT NULL
  , quantity_capacity INTEGER NOT NULL
);

CREATE TABLE transactions (
    id SERIAL PRIMARY KEY
  , meta_id SMALLINT UNIQUE NOT NULL
  , quantity INTEGER
  , price NUMERIC(2)
);
