CREATE DOMAIN first_name AS VARCHAR(50)
CHECK (
    no_leading_or_trailing_spaces(VALUE)
);
