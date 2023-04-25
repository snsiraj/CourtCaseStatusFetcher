CREATE TABLE IF NOT EXISTS CASE_HISTORY(ID IDENTITY PRIMARY KEY NOT NULL,
                                   CNO VARCHAR NOT NULL,
                                   BUSINESS_DATE DATE NOT NULL,
                                   PURPOSE_OF_HEARING VARCHAR NOT NULL,
                                   BUSINESS VARCHAR,
                                   NEXT_PURPOSE VARCHAR,
                                   NEXT_HEARING_DATE DATE NOT NULL
                                   );