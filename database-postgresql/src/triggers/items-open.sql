CREATE FUNCTION items_open_trigger()
RETURNS trigger
AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        PERFORM insert_for_items_open(
            NEW.key
          , NEW.name
          , NEW.description
          , NEW.additional_notes
        );

    ELSIF TG_OP = 'UPDATE' AND NEW.opens > OLD.opens THEN
        PERFORM open_for_items_open(
            OLD.key
        );

    ElSIF TG_OP = 'UPDATE' AND NEW.is_deleted=true AND OLD.is_deleted=false THEN
        PERFORM soft_delete_for_items_open(
            OLD.key
        );

    ELSIF TG_OP = 'UPDATE' AND NEW.is_deleted=false AND OLD.is_deleted=true THEN
        PERFORM restore_delete_for_items_open(
            OLD.key
        );

    ELSIF TG_OP = 'DELETE' THEN
        PERFORM hard_delete_for_items_open(
            OLD.key
        );

    ELSIF TG_OP = 'UPDATE'
        AND NEW.* IS DISTINCT FROM OLD.*
        AND OLD.is_deleted=false THEN
        PERFORM update_for_items_open(
            OLD.key
          , NEW.key
          , NEW.name
          , NEW.description
          , NEW.additional_notes
        );

    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER items_open
    INSTEAD OF INSERT OR UPDATE OR DELETE ON items_open
    FOR EACH ROW EXECUTE PROCEDURE items_open_trigger();
