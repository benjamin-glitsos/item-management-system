CREATE FUNCTION items_open_trigger()
RETURNS trigger
AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        PERFORM insert_for_items_open(
            NEW.sku
          , NEW.upc
          , NEW.name
          , NEW.description
          , NEW.acquisition_date
          , NEW.expiration_date
          , NEW.unit_cost
          , NEW.unit_price
          , NEW.quantity_available
          , NEW.quantity_sold
          , NEW.additional_notes
        );

    ELSIF TG_OP = 'UPDATE' AND NEW.opens > OLD.opens THEN
        PERFORM open_for_items_open(
            OLD.sku
        );

    ElSIF TG_OP = 'UPDATE' AND NEW.is_deleted IS true AND OLD.is_deleted IS false THEN
        PERFORM soft_delete_for_items_open(
            OLD.sku
        );

    ELSIF TG_OP = 'UPDATE' AND NEW.is_deleted IS false AND OLD.is_deleted IS true THEN
        PERFORM restore_delete_for_items_open(
            OLD.sku
        );

    ELSIF TG_OP = 'DELETE' THEN
        PERFORM hard_delete_for_items_open(
            OLD.sku
        );

    ELSIF TG_OP = 'UPDATE'
        AND NEW.* IS DISTINCT FROM OLD.*
        AND OLD.is_deleted IS false THEN
        PERFORM update_for_items_open(
            OLD.sku
          , NEW.sku
          , NEW.upc
          , NEW.name
          , NEW.description
          , NEW.acquisition_date
          , NEW.expiration_date
          , NEW.unit_cost
          , NEW.unit_price
          , NEW.quantity_available
          , NEW.quantity_sold
          , NEW.additional_notes
        );

    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER items_open
    INSTEAD OF INSERT OR UPDATE OR DELETE ON items_open
    FOR EACH ROW EXECUTE PROCEDURE items_open_trigger();
