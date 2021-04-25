CREATE FUNCTION users_open_trigger()
RETURNS trigger
AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        PERFORM insert_for_users_open(
            NEW.username
          , NEW.email_address
          , NEW.first_name
          , NEW.last_name
          , NEW.other_names
          , NEW.password
          , NEW.additional_notes
        );

    ELSIF TG_OP = 'UPDATE' AND NEW.opens > OLD.opens THEN
        PERFORM open_for_users_open(
            OLD.username
        );

    ElSIF TG_OP = 'UPDATE' AND NEW.is_deleted IS true AND OLD.is_deleted IS false THEN
        PERFORM soft_delete_for_users_open(
            OLD.username
        );

    ELSIF TG_OP = 'UPDATE' AND NEW.is_deleted IS false AND OLD.is_deleted IS true THEN
        PERFORM restore_delete_for_users_open(
            OLD.username
        );

    ELSIF TG_OP = 'DELETE' THEN
        PERFORM hard_delete_for_users_open(
            OLD.username
        );

    ELSIF TG_OP = 'UPDATE'
        AND NEW.* IS DISTINCT FROM OLD.*
        AND OLD.is_deleted IS false THEN
        PERFORM update_for_users_open(
            OLD.username
          , NEW.username
          , NEW.email_address
          , NEW.first_name
          , NEW.last_name
          , NEW.other_names
          , NEW.password
          , NEW.additional_notes
        );

    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER users_open
    INSTEAD OF INSERT OR UPDATE OR DELETE ON users_open
    FOR EACH ROW EXECUTE PROCEDURE users_open_trigger();
