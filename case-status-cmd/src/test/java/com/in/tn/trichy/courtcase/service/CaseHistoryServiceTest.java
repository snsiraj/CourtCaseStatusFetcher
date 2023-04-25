package com.in.tn.trichy.courtcase.service;

import com.in.tn.trichy.courtcase.domain.CaseHistorySummaryRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CaseHistoryServiceTest {
    @ParameterizedTest
    @CsvSource(textBlock = """
            TNTP050010412022,12-12-2022,09-01-2023,Issues
            """)
     void getCaseHistoryForTest(String Cno,String BusinessDate, String HearingDate, String PurposeOfHearing){
        CaseHistoryService caseHistoryService=new CaseHistoryService();
        CaseHistorySummaryRecord summaryRecord=new CaseHistorySummaryRecord(Cno,BusinessDate,HearingDate,PurposeOfHearing);
        System.out.println(caseHistoryService.getCaseHistoryFor(summaryRecord));
    }

}