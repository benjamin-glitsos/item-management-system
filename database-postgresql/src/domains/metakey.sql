CREATE DOMAIN metakey AS VARCHAR(60)
CHECK (
    VALUE ~ '^[-a-z0-9]{1,24}:[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}$'
);
