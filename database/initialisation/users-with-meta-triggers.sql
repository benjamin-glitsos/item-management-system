CREATE FUNCTION users_with_meta_modifiable_view()
RETURNS TRIGGER AS $$
BEGIN
    -- Insert --
    IF TG_OP = 'INSERT' THEN
        WITH insert_meta AS (
            INSERT INTO meta (notes)
            VALUES (NEW.notes)
            RETURNING id
        )
        INSERT INTO users (meta_id, email_address, username, password)
        VALUES (
            (SELECT id FROM insert_meta)
          , NEW.email_address
          , NEW.username
          , NEW.password
        );
        RETURN NULL;

    -- Open --
    ELSIF TG_OP = 'UPDATE' AND NEW.opens > OLD.opens THEN
        UPDATE meta SET opens=OLD.opens + 1
        WHERE id=(
            SELECT meta_id
            FROM users
            WHERE username=OLD.username
        );
        RETURN NULL;

    -- Restore Delete --
    ELSIF TG_OP = 'UPDATE' AND NEW.restored_at IS NOT NULL THEN
        UPDATE meta SET restored_at=NOW()
        WHERE id=(
            SELECT meta_id
            FROM users
            WHERE username=OLD.username
        );
        RETURN NULL;

    -- Soft Delete --
    ElSIF TG_OP = 'UPDATE' AND NEW.deleted_at IS NOT NULL THEN
        UPDATE meta SET deleted_at=NOW()
        WHERE id=(
            SELECT meta_id
            FROM users
            WHERE username=OLD.username
        );
        RETURN NULL;

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
        RETURN NULL;

    -- Update --
    ELSIF TG_OP = 'UPDATE' THEN
        WITH users_update AS (
            UPDATE users
            SET username=NEW.username
              , email_address=NEW.email_address
              , password=NEW.password
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

CREATE TRIGGER users_with_meta_modifiable_view_trigger
    INSTEAD OF INSERT OR UPDATE OR DELETE ON users_with_meta
    FOR EACH ROW EXECUTE PROCEDURE users_with_meta_modifiable_view();
