CREATE FUNCTION users_with_meta_trigger()
RETURNS trigger
AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        PERFORM insert_for_users_with_meta(
              NEW.username
            , NEW.email_address
            , NEW.first_name
            , NEW.last_name
            , NEW.other_names
            , NEW.password
            , NEW.notes
        );

    ELSIF TG_OP = 'UPDATE' AND NEW.opens > OLD.opens THEN
        PERFORM open_for_users_with_meta(
              OLD.username
        );

    ElSIF TG_OP = 'UPDATE' AND NEW.is_deleted=true AND OLD.is_deleted=false THEN
        PERFORM soft_delete_for_users_with_meta(
              OLD.username
        );

    -- Restore Delete --
    ELSIF TG_OP = 'UPDATE' AND NEW.is_deleted=false AND OLD.is_deleted=true THEN
        UPDATE meta SET is_deleted=false, edits=edits + 1
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
    ELSIF TG_OP = 'UPDATE'
        AND NEW.* IS DISTINCT FROM OLD.*
        AND OLD.is_deleted=false THEN
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
        SET edited_at=NOW(), notes=NEW.notes, edits=edits + 1
        WHERE id=(SELECT meta_id FROM users_update);
        RETURN NEW;

    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER users_with_meta
    INSTEAD OF INSERT OR UPDATE OR DELETE ON users_with_meta
    FOR EACH ROW EXECUTE PROCEDURE users_with_meta_trigger();
