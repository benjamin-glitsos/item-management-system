CREATE DOMAIN other_names AS VARCHAR(120)
CHECK (
    VALUE <> ''
);
