package com.in.tn.trichy.courtcase.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CaseHistroySummaryServiceTest {
    @Test
     void getCaseHistorySummaryForTest(){
        CaseHistroySummaryService caseHistroySummaryService=new CaseHistroySummaryService();
        caseHistroySummaryService.getCaseHistorySummaryFor("231700004862022","TNTP050010412022");
    }

}