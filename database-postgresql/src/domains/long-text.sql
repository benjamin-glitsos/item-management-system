CREATE DOMAIN long_text AS TEXT
CHECK (
    not_empty(VALUE)
);
