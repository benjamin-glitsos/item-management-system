WITH meta_insert_1 AS (SELECT insert_default_meta() AS id)
   , meta_insert_2 AS (SELECT insert_default_meta() AS id)
INSERT INTO users (meta_id, email_address, username, password) VALUES (
    (SELECT id FROM meta_insert_1)
  , '$SUPER_ADMIN_EMAIL_ADDRESS'
  , '$SUPER_ADMIN_USERNAME'
  , '$SUPER_ADMIN_PASSWORD'
),(
    (SELECT id FROM meta_insert_2)
  , '$DEMO_ADMIN_EMAIL_ADDRESS'
  , '$DEMO_ADMIN_USERNAME'
  , '$DEMO_ADMIN_PASSWORD'
);
