CREATE DOMAIN upc AS VARCHAR(12)
CHECK (
    VALUE ~ '^[0-9]*$'
AND not_empty(VALUE)
);
