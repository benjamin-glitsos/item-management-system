CREATE VIEW users_open AS
    SELECT
        u.username
      , u.email_address
      , u.first_name
      , u.last_name
      , u.other_names
      , u.password
      , m.additional_notes
      , m.metakey
      , m.opens
      , m.edits
      , m.is_deleted
      , m.created_at
      , m.created_by
      , m.edited_at
      , m.edited_by
      , m.deleted_at
      , m.deleted_by
    FROM users u
    INNER JOIN meta_open m ON u.meta_id = m.id;
