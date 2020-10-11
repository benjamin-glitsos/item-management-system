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
