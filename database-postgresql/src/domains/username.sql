CREATE DOMAIN username AS VARCHAR(20)
CHECK (
    VALUE ~ '^[-_a-zA-Z0-9]*$'
);
