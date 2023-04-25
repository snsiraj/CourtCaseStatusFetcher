package com.in.tn.trichy.courtcase.service;

import com.in.tn.trichy.courtcase.domain.CaseHistoryRecord;
import com.in.tn.trichy.courtcase.domain.CaseHistorySummaryRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CaseHistoryService {
    private Logger log = LoggerFactory.getLogger(CaseHistoryService.class);
    private static final String COURT_SHOWBUSINESS_URI="https://services.ecourts.gov.in/ecourtindia_v4_bilingual/cases/s_show_business.php";
    private static final HttpClient CLIENT=HttpClient.newHttpClient();

    public CaseHistoryRecord getCaseHistoryFor(CaseHistorySummaryRecord caseHistorySummaryRecord){
        log.info("Fetching the Case History");
        String responseString="";
        ArrayList<String> paramList=new ArrayList<>();
        CaseHistoryRecord caseHistoryRecord=null;
        String queryString=getBodyString(caseHistorySummaryRecord);
        log.info("URL:{}",COURT_SHOWBUSINESS_URI);
        log.info("Form Data:{}",queryString);
        HttpRequest request=HttpRequest
                .newBuilder((URI.create(COURT_SHOWBUSINESS_URI)))
                .POST(HttpRequest.BodyPublishers.ofString(queryString))
                .setHeader("User-Agent","Chrome")
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("PHPSESSID","3buptau8586r3dllqo6eas0h6l")
                .build();
        log.info("Making REST Call");
        try {
            HttpResponse<String> response=CLIENT.send(request,HttpResponse.BodyHandlers.ofString());
            responseString= response.body();
            CaseHistoryResponse caseHistoryResponse=new CaseHistoryResponse(responseString);
            paramList.add(caseHistorySummaryRecord.BusinessDate());
            caseHistoryRecord = (CaseHistoryRecord) caseHistoryResponse.parseHTMLResponse(paramList);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return caseHistoryRecord;
    }

    private String getBodyString(CaseHistorySummaryRecord summaryRecord) {
        Date date=null;
        try {
             date=new SimpleDateFormat("dd-mm-yyyy").parse(summaryRecord.HearingDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyymmdd");
        Map<Object, Object> data = new HashMap<>();
        data.put("court_code","16");
        data.put("dist_code","8");
        data.put("nextdate1",dateFormat.format(date)); //Format : YYYYMMDD 20230427
        data.put("case_number1",summaryRecord.Cno());
        data.put("state_code","10");
        data.put("disposal_flag","Pending");
        data.put("businessDate",summaryRecord.BusinessDate()); //FORMAT: DD-MM-YYYY 20-04-2023
        data.put("court_no","12");
        data.put("lang","");
        data.put("appFlag","");

        StringBuilder formBodyBuilder = new StringBuilder();
        for (Map.Entry<Object, Object> singleEntry : data.entrySet()) {
            if (formBodyBuilder.length() > 0) {
                formBodyBuilder.append("&");
            }
            formBodyBuilder.append(URLEncoder.encode((String) singleEntry.getKey(), StandardCharsets.UTF_8));
            formBodyBuilder.append("=");
            formBodyBuilder.append(URLEncoder.encode((String) singleEntry.getValue(), StandardCharsets.UTF_8));
        }
        return formBodyBuilder.toString();
    }
}
