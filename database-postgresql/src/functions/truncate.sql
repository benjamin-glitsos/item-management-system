CREATE FUNCTION truncate(max_length integer, full_text text)
RETURNS text
AS $$
DECLARE
    ellipsis text := ' ...';
    ellipsis_length integer := LENGTH(ellipsis);
    full_text_length integer := LENGTH(full_text);
    truncate_length integer := max_length - ellipsis_length;
BEGIN
    IF full_text_length <= truncate_length THEN
        RETURN full_text;
    ELSE
        RETURN LEFT(full_text, truncate_length) || ellipsis;
    END IF;
END;
$$ LANGUAGE plpgsql;
