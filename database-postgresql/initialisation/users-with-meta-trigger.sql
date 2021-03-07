CREATE FUNCTION users_with_meta_trigger()
RETURNS TRIGGER AS $$
BEGIN
    -- Insert --
    IF TG_OP = 'INSERT' THEN
        WITH insert_meta AS (
            INSERT INTO meta (metakey, notes)
            VALUES (gen_random_metakey('users'), NEW.notes)
            RETURNING id
        )
        INSERT INTO users (
            username
          , email_address
          , first_name
          , last_name
          , other_names
          , password
          , meta_id
        )
        VALUES (
            NEW.username
          , NEW.email_address
          , NEW.first_name
          , NEW.last_name
          , NEW.other_names
          , sha1_encrypt(NEW.password)
          , (SELECT id FROM insert_meta)
        );
        RETURN null;

    -- Open --
    ELSIF TG_OP = 'UPDATE' AND NEW.opens > OLD.opens THEN
        UPDATE meta SET opens=OLD.opens + 1
        WHERE id=(
            SELECT meta_id
            FROM users
            WHERE username=OLD.username
        );
        RETURN null;

    -- Soft Delete --
    ElSIF TG_OP = 'UPDATE' AND NEW.is_deleted=true AND OLD.is_deleted=false THEN
        UPDATE meta SET is_deleted=true, deleted_at=NOW()
        WHERE id=(
            SELECT meta_id
            FROM users
            WHERE username=OLD.username
        );
        RETURN null;

    -- Restore Delete --
    ELSIF TG_OP = 'UPDATE' AND NEW.is_deleted=false AND OLD.is_deleted=true THEN
        UPDATE meta SET is_deleted=false, restored_at=NOW()
        WHERE id=(
            SELECT meta_id
            FROM users
            WHERE username=OLD.username
        );
        RETURN null;

    -- Hard Delete --
    ELSIF TG_OP = 'DELETE' THEN
        WITH delete_users AS (
            DELETE FROM users
            WHERE username=OLD.username
            RETURNING meta_id
        )
        DELETE FROM meta
        WHERE id=(
            SELECT meta_id
            FROM users
            WHERE username=OLD.username
        );
        RETURN null;

    -- Update --
    ELSIF TG_OP = 'UPDATE' AND NEW.* IS DISTINCT FROM OLD.* THEN
        WITH users_update AS (
            UPDATE users
            SET username=NEW.username
              , email_address=NEW.email_address
              , first_name=NEW.first_name
              , last_name=NEW.last_name
              , other_names=NEW.other_names
              , password=sha1_encrypt(NEW.password)
            WHERE username=OLD.username
            RETURNING meta_id
        )
        UPDATE meta
        SET edits=edits + 1, edited_at=NOW(), notes=NEW.notes
        WHERE id=(SELECT meta_id FROM users_update);
        RETURN NEW;

    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER users_with_meta
    INSTEAD OF INSERT OR UPDATE OR DELETE ON users_with_meta
    FOR EACH ROW EXECUTE PROCEDURE users_with_meta_trigger();
