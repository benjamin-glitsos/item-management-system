-- TODO: build this up from a list-view one that only includes the creator and editor?

-- CREATE VIEW records_list_view
-- CREATE VIEW users_list_view
-- CREATE VIEW stock_list_view
-- CREATE VIEW transactions_list_view
-- CREATE VIEW records_open_view
-- CREATE VIEW users_open_view
-- CREATE VIEW stock_open_view
-- CREATE VIEW transactions_open_view

CREATE VIEW records_open_view AS
    SELECT
        records.uuid
      , records.created_at
      , creators.username AS created_by
      , records.opens
      , records.opened_at
      , openers.username AS opened_by
      , records.edits
      , records.edited_at
      , editors.username AS edited_by
      , records.deleted_at
      , deletors.username AS deleted_by
      , records.restored_at
      , restorer.username AS restored_by
      , records.notes
    FROM records
    LEFT JOIN users creators ON records.created_by_id = creators.id
    LEFT JOIN users openers ON records.opened_by_id = openers.id
    LEFT JOIN users editors ON records.edited_by_id = editors.id
    LEFT JOIN users deletors ON records.deleted_by_id = deletors.id
    LEFT JOIN users restorers ON records.restored_by_id = restorers.id;
