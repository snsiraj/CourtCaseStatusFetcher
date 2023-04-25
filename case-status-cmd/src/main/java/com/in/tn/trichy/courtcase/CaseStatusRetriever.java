package com.in.tn.trichy.courtcase;

import com.in.tn.trichy.courtcase.repo.CaseStatusJdbcRepo;
import com.in.tn.trichy.courtcase.service.CaseStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class CaseStatusRetriever {
    private static final Logger LOG= LoggerFactory.getLogger(CaseStatusRetriever.class);
    public static void main(String[] args) {
        LOG.info("Case Status Retriever ...Started!");
        Map<String,String> caseLists=new HashMap<>();
        caseLists.put("231700004862022","TNTP050007172022");
        CaseStatusService statusService=new CaseStatusService(new CaseStatusJdbcRepo("./../db/CASE_STATUS_STG_H2.db"));
        try{
            for(Map.Entry<String,String> c:caseLists.entrySet()){
                LOG.info("Procesing history for case ID:{}",c);
                statusService.processCaseStatus(c.getKey(),c.getValue());
            }

        }
        catch(Exception e){
            LOG.error(e.getMessage());
        }
        LOG.info("Case Status Retriever ...Ended!");
    }


}
