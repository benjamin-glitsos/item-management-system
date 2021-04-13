CREATE INDEX CONCURRENTLY users_other_names_trigram_index
ON users
USING gin(cast(other_names as text) gin_trgm_ops);
