CREATE FUNCTION insert_into_users_with_meta(
      username text
    , email_address text
    , first_name text
    , last_name text
    , other_names text
    , password text
    , notes text
)
RETURNS void
AS $$
BEGIN
    WITH insert_meta AS (
        INSERT INTO meta (metakey, notes)
        VALUES (generate_random_metakey('users'), notes)
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
        username
      , email_address
      , first_name
      , last_name
      , other_names
      , sha1_encrypt(password)
      , (SELECT id FROM insert_meta)
    );
END;
$$ LANGUAGE plpgsql;
