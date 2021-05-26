CREATE DOMAIN metakey AS VARCHAR(24)
CHECK (
    VALUE ~ '^[a-zA-Z0-9]{1,8}_[a-zA-Z0-9]{12}$'
);
