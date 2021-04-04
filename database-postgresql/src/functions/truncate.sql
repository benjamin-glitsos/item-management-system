CREATE FUNCTION truncate(n integer, s text)
RETURNS text
AS $$
    SELECT LEFT(s, n - 3) || '...'
    -- TODO: use if/else to add ellipsis only if it exceeds (n - 3) and only if string is not null. Use declaring variables to make this neater.
$$ LANGUAGE plpgsql;
