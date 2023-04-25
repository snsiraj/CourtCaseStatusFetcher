CREATE TABLE IF NOT EXISTS CASE_HISTORY(ID IDENTITY PRIMARY KEY NOT NULL,
                                   CNO VARCHAR NOT NULL,
                                   BUSINESS_DATE VARCHAR NOT NULL,
                                   PURPOSE_OF_HEARING VARCHAR NOT NULL,
                                   BUSINESS VARCHAR NOT NULL,
                                   NEXT_PURPOSE VARCHAR NOT NULL,
                                   NEXT_HEARING_DATE VARCHAR
                                   );