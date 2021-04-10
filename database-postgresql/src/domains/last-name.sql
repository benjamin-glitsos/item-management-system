CREATE DOMAIN last_name AS VARCHAR(70)
CHECK (
    VALUE <> ''
);
