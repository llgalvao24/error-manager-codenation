CREATE TABLE USERS
(
      ID INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT
    , USERNAME VARCHAR(254)
    , PASSWORD VARCHAR(64)
    , CREATED_AT TIMESTAMP DEFAULT NOW()
    , UPDATED_AT TIMESTAMP
    , IS_ACTIVE BOOLEAN DEFAULT TRUE
);
CREATE TRIGGER TRG_SET_FALSE ON USERS AFTER UPDATE
DECLARE @id INTEGER
DECLARE @oldValue BOOLEAN
DECLARE @newValue BOOLEAN

select @newValue = i.IS_ACTIVE, @id = i.ID from inserted i
select @oldValue = d.IS_ACTIVE from deleted d

IF (@newValue <> @oldValue AND id is not null)
    BEGIN
        UPDATE APPLICATIONS SET IS_ACTIVE = FALSE WHERE USER_ID = @id;
    END
;


CREATE INDEX IDX_USERS_USERNAME ON USERS (lower(USERNAME));

CREATE TABLE APPLICATIONS
(
      ID INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT
    , NAME VARCHAR(255)
    , CREATED_AT TIMESTAMP DEFAULT NOW()
    , USER_ID INTEGER REFERENCES USERS (ID)
    , IS_ACTIVE BOOLEAN DEFAULT TRUE
);

CREATE INDEX IDX_APPLICATIONS_NAME ON APPLICATIONS (lower(NAME));

CREATE TABLE PROFILES
(
    USER_ID BIGINT NOT NULL REFERENCES USERS (ID)
    PROFILES INTEGER
);

CREATE INDEX IDX_PROFILES_USER_ID ON PROFILES (USER_ID);
