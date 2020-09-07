CREATE EXTENSION citext;

CREATE DOMAIN email AS citext
CHECK (
    VALUE ~ '^[a-zA-Z0-9.!#$%&''*+/=?^_\\`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$'
);

CREATE DOMAIN phone AS text
CHECK (
    VALUE ~ '^[0-9 +-]{6,20}$'
);

CREATE DOMAIN postcode AS text
CHECK (
    VALUE ~ '^[0-9\s-]{4}$'
);

CREATE DOMAIN hospital_number AS text
CHECK (
    VALUE ~ '^[0-9A-Z]{12}$'
);

CREATE DOMAIN medicare_number AS text
CHECK (
    VALUE ~ '^[0-9]{10}$'
);
