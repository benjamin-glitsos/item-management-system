CREATE EXTENSION citext;

CREATE DOMAIN email_address AS citext
CHECK (
    VALUE ~ '^[a-zA-Z0-9.!#$%&''*+/=?^_\\`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$'
);

CREATE DOMAIN html AS text
CHECK (
    VALUE ~ '^\<.*\>.*\<.*\>$'
);

CREATE DOMAIN hex_colour AS text
CHECK (
    VALUE ~ '^#[0-9A-Z]{6}$'
);

CREATE DOMAIN sku AS text
CHECK (
    VALUE ~ '^[-0-9A-Z]{1,20}$'
);
