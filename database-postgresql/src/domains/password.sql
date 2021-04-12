CREATE DOMAIN password AS VARCHAR(40)
CHECK (
    not_empty(VALUE)
);
