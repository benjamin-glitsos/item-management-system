CREATE DOMAIN other_names AS VARCHAR(120)
CHECK (
    no_leading_or_trailing_spaces(VALUE)
);
