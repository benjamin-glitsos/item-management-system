WITH meta_insert AS (
    INSERT INTO meta DEFAULT VALUES
    RETURNING id
)
INSERT INTO users (meta_id, email_address, username, password) VALUES (
    (SELECT id FROM meta_insert)
  , '$SUPER_ADMIN_EMAIL_ADDRESS'
  , '$SUPER_ADMIN_USERNAME'
  , '$SUPER_ADMIN_PASSWORD'
);

WITH meta_insert AS (
    INSERT INTO meta DEFAULT VALUES
    RETURNING id
)
INSERT INTO users (meta_id, email_address, username, password) VALUES (
    (SELECT id FROM meta_insert)
  , '$DEMO_ADMIN_EMAIL_ADDRESS'
  , '$DEMO_ADMIN_USERNAME'
  , '$DEMO_ADMIN_PASSWORD'
);
