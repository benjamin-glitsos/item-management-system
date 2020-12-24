CREATE VIEW users_with_meta AS
    SELECT
        u.username
      , u.email_address
      , u.password
      , m.uuid
      , m.opens
      , m.edits
      , m.created_at
      , m.edited_at
      , m.deleted_at
      , m.restored_at
      , m.notes
    FROM users u
    INNER JOIN meta m ON u.meta_id = m.id;
