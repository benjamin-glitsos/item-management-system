CREATE DOMAIN name AS VARCHAR(255)
CHECK (
    not_empty(VALUE)
AND no_leading_or_trailing_spaces(VALUE)
);
