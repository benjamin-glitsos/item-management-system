CREATE DOMAIN metakey AS VARCHAR(24)
CHECK (
    VALUE ~ '^[-_a-zA-Z0-9]{1,8}:[-_a-zA-Z0-9]{12}$'
);
