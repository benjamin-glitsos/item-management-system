CREATE DOMAIN password AS VARCHAR(40)
CHECK (
    no_leading_or_trailing_spaces(VALUE)
);
