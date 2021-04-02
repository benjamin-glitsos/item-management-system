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

    ELSIF TG_OP = 'UPDATE' AND NEW.is_deleted=false AND OLD.is_deleted=true THEN
        PERFORM restore_delete_for_users_with_meta(
            OLD.username
        );

    ELSIF TG_OP = 'DELETE' THEN
        PERFORM hard_delete_for_users_with_meta(
            OLD.username
        );

    ELSIF TG_OP = 'UPDATE'
        AND NEW.* IS DISTINCT FROM OLD.*
        AND OLD.is_deleted=false THEN
        PERFORM update_for_users_with_meta(
            OLD.username
          , NEW.username
          , NEW.email_address
          , NEW.first_name
          , NEW.last_name
          , NEW.other_names
          , NEW.password
          , NEW.notes
        );

    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER users_with_meta
    INSTEAD OF INSERT OR UPDATE OR DELETE ON users_with_meta
    FOR EACH ROW EXECUTE PROCEDURE users_with_meta_trigger();
