CREATE INDEX CONCURRENTLY users_username_trigram_index
ON users
USING gin(cast(username as text) gin_trgm_ops);
