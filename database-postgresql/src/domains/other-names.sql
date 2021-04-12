CREATE DOMAIN other_names AS VARCHAR(120)
CHECK (
    not_empty(VALUE)
AND no_leading_or_trailing_spaces(VALUE)
);
