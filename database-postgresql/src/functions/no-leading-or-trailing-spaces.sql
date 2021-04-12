CREATE FUNCTION no_leading_or_trailing_spaces(value text)
RETURNS boolean AS $$
BEGIN
    RETURN value ~ '^[^\s]+(\s+[^\s]+)*$';
END;
$$ LANGUAGE plpgsql;
