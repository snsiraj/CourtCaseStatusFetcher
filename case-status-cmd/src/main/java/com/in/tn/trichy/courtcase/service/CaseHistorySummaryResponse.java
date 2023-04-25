package com.in.tn.trichy.courtcase.service;

import com.in.tn.trichy.courtcase.domain.CaseHistorySummaryRecord;
import com.in.tn.trichy.courtcase.util.HTMLParser;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;

public class CaseHistorySummaryResponse extends HTMLParser {
    Logger log =LoggerFactory.getLogger(CaseHistorySummaryResponse.class);
    public CaseHistorySummaryResponse(String data){
        super(data);
    }
    public CaseHistorySummaryResponse(File file){
        super(file);
    }

    @Override
    public Object parseHTMLResponse(ArrayList<String> paramList) {
        Elements ele = cssSelect("td");
        ArrayList<CaseHistorySummaryRecord> caseHistorySummaryRecordList=new ArrayList<>();
        for (int i=5;i<ele.size();i+=5){
            if(ele.get(i+1).text().compareToIgnoreCase("Pending")!=0)
            caseHistorySummaryRecordList.add(new CaseHistorySummaryRecord(paramList.get(0),ele.get(i).text(),ele.get(i+1).text(),ele.get(i+2).text()));
        }
        return caseHistorySummaryRecordList;
    }


}
