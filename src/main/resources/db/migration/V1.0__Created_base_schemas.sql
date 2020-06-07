CREATE TABLE USERS
(
      ID SERIAL PRIMARY KEY
    , USERNAME VARCHAR(254)
    , PASSWORD VARCHAR(64)
    , CREATED_AT TIMESTAMP DEFAULT NOW()
    , UPDATED_AT TIMESTAMP
    , IS_ACTIVE BOOLEAN DEFAULT TRUE
);

CREATE TABLE APPLICATIONS
(
      ID SERIAL PRIMARY KEY
    , NAME VARCHAR(255)
    , CREATED_AT TIMESTAMP DEFAULT NOW()
    , USER_ID INTEGER REFERENCES USERS (ID)
    , IS_ACTIVE BOOLEAN DEFAULT TRUE
);

CREATE TABLE PROFILES
(
    USER_ID INTEGER NOT NULL REFERENCES USERS (ID)
  , PROFILES INTEGER
);

