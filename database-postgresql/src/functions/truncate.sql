CREATE FUNCTION truncate(max_length integer, source text)
RETURNS text AS $$
DECLARE
    ellipsis text := ' ...';
    ellipsis_length integer := LENGTH(ellipsis);
    source_length integer := LENGTH(source);
    truncate_length integer := max_length - ellipsis_length;
BEGIN
    IF source_length <= truncate_length THEN
        RETURN source;
    ELSE
        RETURN left(source, truncate_length) || ellipsis;
    END IF;
END;
$$ LANGUAGE plpgsql;
