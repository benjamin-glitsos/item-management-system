CREATE DOMAIN last_name AS VARCHAR(70)
CHECK (
    not_empty(VALUE)
AND no_leading_or_trailing_spaces(VALUE)
);
