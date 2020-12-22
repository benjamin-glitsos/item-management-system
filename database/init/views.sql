CREATE VIEW users_with_meta AS
    SELECT
        u.email_address
      , u.username
      , u.password
      , m.created_at
      , m.edited_at
      , m.deleted_at
    FROM users u
    INNER JOIN meta m ON u.meta_id = m.id;
