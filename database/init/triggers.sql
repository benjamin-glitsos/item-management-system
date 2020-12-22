CREATE OR REPLACE FUNCTION users_open_modifiable_view()
RETURNS TRIGGER AS
$$
BEGIN
    IF TG_OP = 'UPDATE' THEN
        UPDATE users u SET email_address=NEW.email_address, password=NEW.password WHERE username=OLD.username;
        UPDATE meta m SET opens=NEW.opens, edits=NEW.edits WHERE id=OLD.id;
        RETURN NEW;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER users_open_modifiable_view_trigger
    INSTEAD OF UPDATE ON users_open
    FOR EACH ROW EXECUTE PROCEDURE users_open_modifiable_view();

-- TODO: test
-- update users_open set opens = 1 where username = 'bengyup';
