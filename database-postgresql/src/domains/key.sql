CREATE DOMAIN key AS VARCHAR(250)
CHECK (
    VALUE ~ '^[-A-Z0-9]*$'
AND not_empty(VALUE)
);
