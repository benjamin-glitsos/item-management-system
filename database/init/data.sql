WITH user_meta_insert AS (
    INSERT INTO meta (created_by)
    VALUES (1)
    RETURNING id
)
INSERT INTO users (record_id, email_address, username, password) VALUES (
    (SELECT id FROM user_meta_insert)
  , 1
  , '$SUPER_ADMIN_EMAIL_ADDRESS'
  , '$SUPER_ADMIN_USERNAME'
  , '$SUPER_ADMIN_PASSWORD'
);

WITH user_meta_insert AS (
    INSERT INTO meta (created_by)
    VALUES (1)
    RETURNING id
)
INSERT INTO users (record_id, email_address, username, password) VALUES (
    (SELECT id FROM user_meta_insert)
  , 1
  , '$DEMO_ADMIN_EMAIL_ADDRESS'
  , '$DEMO_ADMIN_USERNAME'
  , '$DEMO_ADMIN_PASSWORD'
);
