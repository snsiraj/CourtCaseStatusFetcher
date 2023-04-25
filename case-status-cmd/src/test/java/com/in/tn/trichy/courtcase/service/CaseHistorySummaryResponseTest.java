package com.in.tn.trichy.courtcase.service;

import com.in.tn.trichy.courtcase.domain.CaseHistoryRecord;
import com.in.tn.trichy.courtcase.domain.CaseHistorySummaryRecord;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

class CaseHistorySummaryResponseTest {
    @Test
     void parseHtmlTest(){
        File input= new File(CaseHistorySummaryResponseTest.class.getResource("/sampleresponse2.txt").getFile());
        CaseHistorySummaryResponse parserService =new CaseHistorySummaryResponse(input);
        ArrayList<String> paramList=new ArrayList<>();
        paramList.add("TNTP050010412022");
        ArrayList<CaseHistorySummaryRecord> caseHistorySummaryRecordList = (ArrayList<CaseHistorySummaryRecord>) parserService.parseHTMLResponse(paramList);
        for(CaseHistorySummaryRecord c:caseHistorySummaryRecordList)
        System.out.println(c);
    }

}