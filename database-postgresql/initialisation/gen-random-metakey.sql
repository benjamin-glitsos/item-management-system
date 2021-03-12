CREATE FUNCTION gen_random_metakey(table_name text)
RETURNS text AS $$
DECLARE
    allowed_chars text[] := '{-,_,0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z}';
    length integer := 10;
    table_key text := '';
    record_key text := '';
BEGIN
    CASE
        WHEN table_name = 'users' THEN table_key := 'u';
        ELSE table_key := '0';
    END CASE;
    FOR i IN 1..length LOOP
      record_key := record_key || allowed_chars[1+random()*(array_length(allowed_chars, 1)-1)];
    END LOOP;
    RETURN CONCAT_WS('~', table_key, record_key);
END;
$$ LANGUAGE plpgsql;
