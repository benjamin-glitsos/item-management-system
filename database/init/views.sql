CREATE VIEW users_with_meta AS
    SELECT
        u.email_address
      , u.username
      , u.password
      , m.uuid
      , m.created_at
      , m.edited_at
      , m.deleted_at
      , m.notes
    FROM users u
    INNER JOIN meta m ON u.meta_id = m.id;
