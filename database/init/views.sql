-- TODO: build this up from a list-view one that only includes the creator and editor?

-- CREATE VIEW users_list_view
-- CREATE VIEW stock_list_view
-- CREATE VIEW transactions_list_view
-- CREATE VIEW meta_open_view
-- CREATE VIEW users_open_view
-- CREATE VIEW stock_open_view
-- CREATE VIEW transactions_open_view

CREATE VIEW meta_list_view AS
    SELECT
        meta.id
      , meta.created_at
      , creator.username AS created_by
      , meta.opened_at
      , opener.username AS opened_by
      , meta.edited_at
      , editor.username AS edited_by
      , meta.deleted_at
      , meta.notes
    FROM meta
    LEFT JOIN users creator ON meta.created_by_id = creator.id
    LEFT JOIN users opener ON meta.opened_by_id = opener.id
    LEFT JOIN users editor ON meta.edited_by_id = editor.id;

CREATE VIEW meta_open_view AS
    SELECT
        meta.id
      , meta.created_at
      , creator.username AS created_by
      , meta.opens
      , meta.opened_at
      , opener.username AS opened_by
      , meta.edits
      , meta.edited_at
      , editor.username AS edited_by
      , meta.deleted_at
      , deletor.username AS deleted_by
      , meta.restored_at
      , restorer.username AS restored_by
      , meta.notes
    FROM meta
    LEFT JOIN users creator ON meta.created_by_id = creator.id
    LEFT JOIN users opener ON meta.opened_by_id = opener.id
    LEFT JOIN users editor ON meta.edited_by_id = editor.id
    LEFT JOIN users deletor ON meta.deleted_by_id = deletor.id
    LEFT JOIN users restorer ON meta.restored_by_id = restorer.id;
