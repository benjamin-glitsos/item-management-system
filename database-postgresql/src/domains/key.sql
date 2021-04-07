CREATE DOMAIN key AS VARCHAR(250)
CHECK (
    VALUE ~ '^[-_a-zA-Z0-9]*$'
);
