CREATE DOMAIN first_name AS VARCHAR(50)
CHECK (
    not_empty(VALUE)
);
