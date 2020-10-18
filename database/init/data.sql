WITH user_meta_insert AS (
    INSERT INTO meta (uuid, created_by_id)
    VALUES (gen_random_uuid(), 1)
    RETURNING id
)
INSERT INTO users (meta_id, email_address, username, password) VALUES (
    (SELECT id FROM user_meta_insert)
  , '$SUPER_ADMIN_EMAIL_ADDRESS'
  , '$SUPER_ADMIN_USERNAME'
  , '$SUPER_ADMIN_PASSWORD'
);

WITH user_meta_insert AS (
    INSERT INTO meta (uuid, created_by_id)
    VALUES (gen_random_uuid(), 1)
    RETURNING id
)
INSERT INTO users (meta_id, email_address, username, password) VALUES (
    (SELECT id FROM user_meta_insert)
  , '$DEMO_ADMIN_EMAIL_ADDRESS'
  , '$DEMO_ADMIN_USERNAME'
  , '$DEMO_ADMIN_PASSWORD'
);
