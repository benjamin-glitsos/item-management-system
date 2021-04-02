CREATE FUNCTION friendly_name(
    first_name text
  , last_name text
  , other_names text
  )
RETURNS text
AS '
return(first_name)
' LANGUAGE 'plr';
