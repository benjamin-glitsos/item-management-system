CREATE INDEX CONCURRENTLY items_name_trigram_index
ON items
USING gin(name gin_trgm_ops);
