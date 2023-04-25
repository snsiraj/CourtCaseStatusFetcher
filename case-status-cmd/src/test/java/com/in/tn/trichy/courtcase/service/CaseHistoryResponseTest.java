package com.in.tn.trichy.courtcase.service;

import com.in.tn.trichy.courtcase.domain.CaseHistoryRecord;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

class CaseHistoryResponseTest {
    @Test
    void parseHtmlTest(){
        File input= new File(CaseHistoryResponse.class.getResource("/sampleresponse.txt").getFile());
        CaseHistoryResponse parserService =new CaseHistoryResponse(input);
        String a[]=new String[]{"20-04-2023"};
        ArrayList<String> paramList =new ArrayList<>();
        paramList.add("20-04-2023");
        CaseHistoryRecord historyRecord = (CaseHistoryRecord) parserService.parseHTMLResponse(paramList);
        System.out.println(historyRecord.Business());
    }

}