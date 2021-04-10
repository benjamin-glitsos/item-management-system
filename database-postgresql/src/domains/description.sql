CREATE DOMAIN description AS TEXT
CHECK (
    VALUE <> ''
);
