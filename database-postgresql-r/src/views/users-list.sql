CREATE VIEW users_list AS
    SELECT
        username
      , email_address
      , friendly_name(first_name, last_name, other_names) AS name
      , created_at
      , edited_at
    FROM users_with_meta
    WHERE is_deleted = false;
