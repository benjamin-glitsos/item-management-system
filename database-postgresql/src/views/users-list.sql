CREATE VIEW users_list AS
    SELECT
        username
      , email_address
      , first_name
      , last_name
      , other_names
      , created_at
      , edited_at
    FROM users_with_meta
    WHERE is_deleted = false;
