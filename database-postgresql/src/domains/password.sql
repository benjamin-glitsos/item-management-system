CREATE DOMAIN password AS VARCHAR(40)
CHECK (
    VALUE <> ''
);
