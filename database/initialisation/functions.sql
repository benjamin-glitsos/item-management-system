CREATE FUNCTION insert_default_meta()
RETURNS INT AS $$
DECLARE
    meta_id INT;
BEGIN
    INSERT INTO meta DEFAULT VALUES
    RETURNING meta.id INTO meta_id;
    RETURN meta_id;
END;
$$ LANGUAGE plpgsql;
