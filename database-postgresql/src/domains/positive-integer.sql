CREATE DOMAIN positive_integer AS INTEGER
CHECK (
    VALUE >= 0
);
