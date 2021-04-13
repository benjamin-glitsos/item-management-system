CREATE INDEX CONCURRENTLY items_name_trigram_index
ON items
USING gin(cast(name as text) gin_trgm_ops);
