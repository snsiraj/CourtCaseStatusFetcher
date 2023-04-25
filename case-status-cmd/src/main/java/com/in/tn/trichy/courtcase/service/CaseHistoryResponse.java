package com.in.tn.trichy.courtcase.service;

import com.in.tn.trichy.courtcase.domain.CaseHistoryRecord;
import com.in.tn.trichy.courtcase.util.HTMLParser;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;

public class CaseHistoryResponse extends HTMLParser {
    Logger log =LoggerFactory.getLogger(CaseHistoryResponse.class);
    public CaseHistoryResponse(String data){
        super(data);
    }
    public CaseHistoryResponse(File file){
        super(file);
    }

    @Override
    public Object parseHTMLResponse(ArrayList<String> paramList) {
        Elements ele = cssSelect("td");
        return new CaseHistoryRecord(paramList.get(0),ele.get(3).text(),ele.get(6).text(),ele.get(9).text());

    }


}
