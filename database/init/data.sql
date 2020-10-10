INSERT INTO objects (key, name, description, is_in_main_menu)
VALUES
    ('users', 'Users', 'The user accounts for this system.', TRUE)
  , ('stock', 'Stock', 'The stock items that are in this inventory, or have been in the past.', TRUE)
  , ('transactions', 'Transactions', 'The transactions that have occured for each stock item.', FALSE)

INSERT INTO actions (key, name, description, colour)
VALUES
    ('options', 'Options', 'List the available actions.', NULL)
  , ('list', 'List', 'View the list of these items.', NULL)
  , ('open', 'Open', 'Open this item to view or edit it.', NULL)
  , ('save', 'Save', 'Save any edits you have made to the system.', NULL)
  , ('create', 'Create New', 'Create a new item.', NULL)
  , ('delete', 'Delete', 'Delete this item. (This is a soft delete.)', NULL)
  , ('restore', 'Restore', 'Restore this item which has been deleted.', NULL)
  , ('hard-delete', 'Hard Delete', 'Permanently delete this item. (This item won’t be recoverable once deleted. This is a hard delete.)', '#be382d')
  , ('buy', 'Buy', 'Record a transaction in which this stock was bought.', '#318a4a')
  , ('sell', 'Sell', 'Record a transaction in which this stock was sold.', '#be382d')

WITH user_records_insert AS (
    INSERT INTO records (uuid, created_by)
    VALUES (gen_random_uuid(), 1)
    RETURNING id
)
INSERT INTO users (record_id, email_address, username, password) VALUES (
    (SELECT id FROM user_records_insert)
  , 1
  , '$SUPER_ADMIN_EMAIL_ADDRESS'
  , '$SUPER_ADMIN_USERNAME'
  , '$SUPER_ADMIN_PASSWORD'
);

WITH user_records_insert AS (
    INSERT INTO records (uuid, created_by)
    VALUES (gen_random_uuid(), 1)
    RETURNING id
)
INSERT INTO users (record_id, email_address, username, password) VALUES (
    (SELECT id FROM user_records_insert)
  , 1
  , '$DEMO_ADMIN_EMAIL_ADDRESS'
  , '$DEMO_ADMIN_USERNAME'
  , '$DEMO_ADMIN_PASSWORD'
);
