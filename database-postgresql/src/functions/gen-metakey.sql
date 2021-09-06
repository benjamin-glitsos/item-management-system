CREATE FUNCTION gen_metakey(namespace text default 'meta')
RETURNS text AS $$
BEGIN
    RETURN CONCAT(namespace, ':', gen_random_uuid());
END;
$$ LANGUAGE plpgsql;
