CREATE DOMAIN name AS VARCHAR(255)
CHECK (
    no_leading_or_trailing_spaces(VALUE)
);
