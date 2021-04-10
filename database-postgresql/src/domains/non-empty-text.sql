CREATE DOMAIN non_empty_text AS TEXT
CHECK (
    VALUE <> ''
);
