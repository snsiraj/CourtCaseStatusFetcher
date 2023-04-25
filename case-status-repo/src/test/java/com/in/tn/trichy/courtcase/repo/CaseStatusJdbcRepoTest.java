package com.in.tn.trichy.courtcase.repo;

import com.in.tn.trichy.courtcase.repo.domain.CaseHistoryDBRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CaseStatusJdbcRepoTest {
    private final String path="./../db/CASE_STATUS_TEST_H2.db";

    @ParameterizedTest
    @CsvSource(textBlock = """
            TNTP050010412022,12-12-2022,Issues,Issue notice to respondant by. 09.01.2023.,Issues,09-01-2023
                """)
    void saveCaseHistoryTest(String Cno,
                                    String BusinessDate,
                                    String PurposeOfHearing,
                                    String Business,
                                    String NextPurpose,
                                    String NextHearingDate){
        CaseHistoryDBRecord caseHistoryDBRecord=new CaseHistoryDBRecord(null,Cno,getSQLDate(BusinessDate),PurposeOfHearing,Business,NextPurpose,getSQLDate(NextHearingDate));
        CaseStatusRepo repo=new CaseStatusJdbcRepo(path);
        repo.saveCaseHistory(caseHistoryDBRecord);


    }
    private Date getSQLDate(String Date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date sqlDate=null;
        try {
            java.util.Date date = sdf.parse(Date);
            sqlDate=new Date(date.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return sqlDate;
    }

    @Test
     void getAllCaseHistoryTest(){
        CaseStatusRepo repo=new CaseStatusJdbcRepo(path);
        List<CaseHistoryDBRecord> caseHistoryDBRec = repo.getAllCaseHistory("TNTP050010412022");
        for(CaseHistoryDBRecord caseHistory:caseHistoryDBRec)
        System.out.println(caseHistory);
        assertNotNull(caseHistoryDBRec);

    }

    @Test
    void fetchLatestHearingDateList() {
        CaseStatusRepo repo=new CaseStatusJdbcRepo(path);

        assertNotNull(repo.fetchLatestHearingDateList("TNTP050010412022"));
    }
}