CREATE DOMAIN last_name AS VARCHAR(70)
CHECK (
    no_leading_or_trailing_spaces(VALUE)
);
