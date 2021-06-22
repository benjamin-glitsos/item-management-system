CREATE DOMAIN sku AS VARCHAR(35)
CHECK (
    VALUE ~ '^[-A-Z0-9]*$'
AND not_empty(VALUE)
);
