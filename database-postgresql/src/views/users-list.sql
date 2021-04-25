CREATE VIEW users_list AS
    SELECT
        u.username
      , u.email_address
      , u.first_name
      , u.last_name
      , u.other_names
      , m.created_at
      , m.edited_at
    FROM users u
    INNER JOIN meta_list m ON u.meta_id = m.id;
