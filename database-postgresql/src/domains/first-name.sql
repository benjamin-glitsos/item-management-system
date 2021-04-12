CREATE DOMAIN first_name AS VARCHAR(50)
CHECK (
    not_empty(VALUE)
AND no_leading_or_trailing_spaces(VALUE)
);
