CREATE FUNCTION insert_default_meta()
RETURNS integer AS $$
BEGIN
    INSERT INTO meta DEFAULT VALUES RETURNING id
    RETURN id
END;
$$ LANGUAGE plpgsql;
