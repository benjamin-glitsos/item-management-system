CREATE INDEX CONCURRENTLY items_key_trigram_index
ON items
USING gin(cast(key as text) gin_trgm_ops);
