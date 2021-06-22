CREATE INDEX CONCURRENTLY items_key_trigram_index
ON items
USING gin(cast(sku as text) gin_trgm_ops);
