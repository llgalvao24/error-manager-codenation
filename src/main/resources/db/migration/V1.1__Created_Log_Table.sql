CREATE TABLE LOGS (
    ID SERIAL PRIMARY KEY
  , DESCRIPTION VARCHAR(255) NOT NULL
  , DETAILS VARCHAR(255) NOT NULL
  , ENVIRONMENT VARCHAR(100) NOT NULL
  , LEVEL VARCHAR(10) NOT NULL
  , LOG VARCHAR(255) NOT NULL
  , CREATED_AT TIMESTAMP DEFAULT NOW()
  , MODIFIED_AT TIMESTAMP
  , APPLICATION_ID INTEGER NOT NULL
  , ORIGIN VARCHAR(100) NOT NULL
)