CREATE DOMAIN last_name AS VARCHAR(70)
CHECK (
    not_empty(VALUE)
);
