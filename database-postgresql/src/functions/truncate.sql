CREATE FUNCTION truncate(max_length integer, full_text text)
RETURNS text
AS $$
DECLARE
    ellipsis text := ' ...';
    ellipsis_length integer := LENGTH(ellipsis);
    full_text_length integer := LENGTH(full_text);
BEGIN
    IF full_text_length <= max_length THEN
        RETURN full_text;
    ELSE
        RETURN full_text || ellipsis;
    END IF;
END;
$$ LANGUAGE plpgsql;
