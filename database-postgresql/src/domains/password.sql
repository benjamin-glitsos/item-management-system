CREATE DOMAIN password AS VARCHAR(40)
CHECK (
    not_empty(VALUE)
AND no_leading_or_trailing_spaces(VALUE)
);
