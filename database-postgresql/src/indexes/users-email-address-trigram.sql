CREATE INDEX CONCURRENTLY users_email_address_trigram_index
ON users
USING gin(cast(email_address as text) gin_trgm_ops);
