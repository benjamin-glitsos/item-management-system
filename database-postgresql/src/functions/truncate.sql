CREATE FUNCTION truncate(max_length integer, full_text text)
RETURNS text
AS $$
DECLARE
    ellipsis text := '...';
    ellipsis_length integer := 3;
BEGIN
    RETURN LEFT(full_text, max_length - ellipsis_length) || ellipsis;
END;
$$ LANGUAGE plpgsql;

-- TODO: use if/else to add ellipsis only if it exceeds (n - 3) and only if string is not null. Use declaring variables to make this neater.
