CREATE INDEX IDX_PROFILES_USER_ID ON PROFILES (USER_ID);

<!--criação da trigger-->

CREATE OR REPLACE FUNCTION update_user_to_false()
  RETURNS trigger AS
$BODY$
BEGIN
	IF NEW.is_active <> OLD.is_active AND NEW.is_active = false THEN
		 UPDATE applications SET is_active = false WHERE user_id = NEW.id;
	END IF;

	RETURN NEW;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;

CREATE TRIGGER TRG_UPDATE_USER_TO_FALSE
  BEFORE UPDATE
  ON USERS
  FOR EACH ROW
  EXECUTE PROCEDURE update_user_to_false();