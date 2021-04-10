CREATE DOMAIN first_name AS VARCHAR(50)
CHECK (
    VALUE <> ''
);
