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
      , creators.username AS created_by
      , meta.opened_at
      , openers.username AS opened_by
      , meta.edited_at
      , editors.username AS edited_by
      , meta.deleted_at
      , meta.notes
    FROM meta
    LEFT JOIN users creators ON meta.created_by_id = creators.id
    LEFT JOIN users openers ON meta.opened_by_id = openers.id
    LEFT JOIN users editors ON meta.edited_by_id = editors.id

CREATE VIEW meta_open_view AS
    SELECT
        meta.id
      , meta.created_at
      , creators.username AS created_by
      , meta.opens
      , meta.opened_at
      , openers.username AS opened_by
      , meta.edits
      , meta.edited_at
      , editors.username AS edited_by
      , meta.deleted_at
      , deletors.username AS deleted_by
      , meta.restored_at
      , restorer.username AS restored_by
      , meta.notes
    FROM meta
    LEFT JOIN users creators ON meta.created_by_id = creators.id
    LEFT JOIN users openers ON meta.opened_by_id = openers.id
    LEFT JOIN users editors ON meta.edited_by_id = editors.id
    LEFT JOIN users deletors ON meta.deleted_by_id = deletors.id
    LEFT JOIN users restorers ON meta.restored_by_id = restorers.id;
