package com.in.tn.trichy.courtcase.service;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaseHistroySummaryService {
    private Logger log = LoggerFactory.getLogger(CaseHistroySummaryService.class);
    private static final String COURT_SHOWSUMMARY_URI="https://services.ecourts.gov.in/ecourtindia_v4_bilingual/cases/o_civil_case_history.php";
    private static final HttpClient CLIENT=HttpClient.newHttpClient();
    public List<CaseHistorySummaryRecord> getCaseHistorySummaryFor(String caseNo, String cino) {
        log.info("Fetching the Case History ..started");
        String responseString = "";
        String queryString= getBodyString(caseNo,cino);
        ArrayList<CaseHistorySummaryRecord> caseHistorySummaryRecordList=null;
        ArrayList<String> paramList=new ArrayList<>();
        log.debug("URL:" + COURT_SHOWSUMMARY_URI);
        log.debug("Form Data:{}",queryString);
        HttpRequest request = HttpRequest
                .newBuilder((URI.create(COURT_SHOWSUMMARY_URI)))
                .POST(HttpRequest.BodyPublishers.ofString(queryString))
                .setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("PHPSESSID", "3buptau8586r3dllqo6eas0h6l")
                .build();
        log.info("Making REST Call");
        try {
            HttpResponse<String> response=CLIENT.send(request,HttpResponse.BodyHandlers.ofString());
            responseString= response.body();
            log.debug(responseString);
            CaseHistorySummaryResponse caseHistorySummaryResponse=new CaseHistorySummaryResponse(responseString);
            paramList.add(cino);
            caseHistorySummaryRecordList= (ArrayList<CaseHistorySummaryRecord>) caseHistorySummaryResponse.parseHTMLResponse(paramList);
            log.info("Fetching the Case History ..ended");
        } catch (IOException e) {
            log.error("IOException Occured");
            e.printStackTrace();
        } catch (InterruptedException e) {
            log.error("Interrupted Exception Occured");
            e.printStackTrace();
            throw new RuntimeException(e);

        }
        return caseHistorySummaryRecordList;
    }

    private String getBodyString(String caseNo,String cino) {
        Map<Object, Object> data = new HashMap<>();
        data.put("court_code","16");
        data.put("dist_code","8");
        data.put("state_code","10");
        data.put("case_no",caseNo);//"231600000972022"
        data.put("cino",cino);
        data.put("lang","");
        data.put("str1","");
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
