CREATE INDEX CONCURRENTLY users_first_name_trigram_index
ON users
USING gin(cast(first_name as text) gin_trgm_ops);
