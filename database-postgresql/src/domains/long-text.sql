CREATE DOMAIN long_text AS VARCHAR(1048576)
CHECK (
    not_empty(VALUE)
);
