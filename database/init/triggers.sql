CREATE OR REPLACE FUNCTION users_with_meta_modifiable_view()
RETURNS TRIGGER AS
$$
BEGIN
    IF TG_OP = 'UPDATE' THEN
        WITH users_insert AS (
            UPDATE users
            SET email_address=NEW.email_address, password=NEW.password
            WHERE username=OLD.username
            RETURNING OLD.meta_id
        )
        UPDATE meta SET edited_at=(SELECT NOW()) WHERE id=OLD.meta_id;
        RETURN NEW;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER users_with_meta_modifiable_view_trigger
    INSTEAD OF UPDATE ON users_with_meta
    FOR EACH ROW EXECUTE PROCEDURE users_with_meta_modifiable_view();

-- TODO:

-- database      | 2020-12-22 13:48:34.440 UTC [61] ERROR:  record "old" has no field "meta_id"
-- database      | 2020-12-22 13:48:34.440 UTC [61] CONTEXT:  SQL statement "WITH users_insert AS (
-- database      | 	            UPDATE users
-- database      | 	            SET email_address=NEW.email_address, password=NEW.password
-- database      | 	            WHERE username=OLD.username
-- database      | 	            RETURNING OLD.meta_id
-- database      | 	        )
-- database      | 	        UPDATE meta SET edited_at=(SELECT NOW()) WHERE id=OLD.meta_id"

-- database      | 2020-12-22 13:48:34.440 UTC [61] ERROR:  record "new" has no field "meta_id"
-- database      | 2020-12-22 13:48:34.440 UTC [61] CONTEXT:  SQL statement "WITH users_insert AS (
-- database      | 	            UPDATE users
-- database      | 	            SET email_address=NEW.email_address, password=NEW.password
-- database      | 	            WHERE username=OLD.username
-- database      | 	            RETURNING NEW.meta_id
-- database      | 	        )
-- database      | 	        UPDATE meta SET edited_at=(SELECT NOW()) WHERE id=OLD.meta_id"
