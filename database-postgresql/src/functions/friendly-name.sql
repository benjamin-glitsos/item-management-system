CREATE FUNCTION friendly_name(
    first_name text
  , last_name text
  , other_names text)
RETURNS text
AS $$
    SELECT concat(first_name, other_names, last_name);
$$ LANGUAGE SQL STRICT IMMUTABLE;
