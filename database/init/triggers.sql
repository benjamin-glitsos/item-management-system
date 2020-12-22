CREATE OR REPLACE FUNCTION users_with_meta_modifiable_view()
RETURNS TRIGGER AS
$$
BEGIN
    IF TG_OP = 'UPDATE' THEN
        WITH users_insert AS (
            UPDATE users
            SET email_address=NEW.email_address, password=NEW.password
            WHERE username=OLD.username
            RETURNING meta_id
        )
        UPDATE meta
        SET edited_at=NOW(), notes=NEW.notes
        WHERE id=(SELECT meta_id FROM users_insert);
        RETURN NEW; -- TODO: make it RETURN NULL ?
    ELSIF TG_OP = 'DELETE' THEN
        UPDATE meta SET deleted_at=NOW()
        WHERE id=(
            SELECT meta_id
            FROM users
            WHERE username=OLD.username
        );
        RETURN NULL;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS users_with_meta_modifiable_view_trigger
    ON public.users_with_meta;
CREATE TRIGGER users_with_meta_modifiable_view_trigger
    INSTEAD OF INSERT OR UPDATE OR DELETE ON users_with_meta
    FOR EACH ROW EXECUTE PROCEDURE users_with_meta_modifiable_view();

-- delete from users_with_meta where username='bengyup'
