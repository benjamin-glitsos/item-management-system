CREATE DOMAIN email_address AS citext
CHECK (
    VALUE ~ '^[a-zA-Z0-9.!#$%&''*+/=?^_\\`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$'
);

CREATE DOMAIN sha1 AS VARCHAR(40);

CREATE DOMAIN metakey AS VARCHAR(14)
CHECK ();
