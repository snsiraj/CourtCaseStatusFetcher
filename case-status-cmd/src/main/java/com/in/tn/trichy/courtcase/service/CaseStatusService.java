package com.in.tn.trichy.courtcase.service;

import com.in.tn.trichy.courtcase.domain.CaseHistoryRecord;
import com.in.tn.trichy.courtcase.domain.CaseHistorySummaryRecord;
import com.in.tn.trichy.courtcase.repo.CaseStatusJdbcRepo;
import com.in.tn.trichy.courtcase.repo.CaseStatusRepo;
import com.in.tn.trichy.courtcase.repo.domain.CaseHistoryDBRecord;
import com.in.tn.trichy.courtcase.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class CaseStatusService {
    Logger log = LoggerFactory.getLogger(CaseStatusService.class);
    CaseStatusRepo repo=null;
    public CaseStatusService(CaseStatusRepo repo){
        this.repo=repo;
    }
    private CaseStatusRepo getRepo(){
        if(repo==null)
            repo=new CaseStatusJdbcRepo("./../db/CASE_STATUS_STG_H2.db");
        return repo;
    }
    public void processCaseStatus(String caseId,String caseNo){

        List<CaseHistorySummaryRecord> caseHistorySummaryRecordArrayList =null;
        Date latestHearingDate=getRepo().fetchLatestHearingDateList(caseNo);
        caseHistorySummaryRecordArrayList = getLatestSummary(latestHearingDate,retrieveCaseHistorySummary(caseId,caseNo));
        if(!caseHistorySummaryRecordArrayList.isEmpty()){
            for(CaseHistorySummaryRecord caseHistorySummary:caseHistorySummaryRecordArrayList) {
                CaseHistoryRecord caseHistory = retrieveCaseHistory(caseHistorySummary);
                storeCaseStatus(getRepo(),caseHistorySummary,caseHistory);
            }
        }
        else
            log.info("NO RECORDS TO UPDATE");

    }
    private List<CaseHistorySummaryRecord> getLatestSummary(Date latestHearingDate, List<CaseHistorySummaryRecord> caseHistorySummaryRecordArrayList){
        boolean flag=false;
        int listPtr=caseHistorySummaryRecordArrayList.size()-1;
        while(listPtr>-1 && !flag){
            Date dateStr=DateUtil.getUtilDate(caseHistorySummaryRecordArrayList.get(listPtr).HearingDate());
            if(dateStr.compareTo(latestHearingDate)==0){
                flag=true;
                caseHistorySummaryRecordArrayList.subList(0,listPtr+1).clear();
            }
            listPtr--;
        }
        return caseHistorySummaryRecordArrayList;
    }
    private List<CaseHistorySummaryRecord> retrieveCaseHistorySummary(String caseNo, String cino) {
        CaseHistroySummaryService caseHistroySummaryService=new CaseHistroySummaryService();
        List<CaseHistorySummaryRecord> caseHistorySummaryList = caseHistroySummaryService.getCaseHistorySummaryFor(caseNo,cino);
        log.info("Total Case History Summary Retrieved:{}",caseHistorySummaryList.size());
        log.debug("Case History Summary List:{}",caseHistorySummaryList);
        return caseHistorySummaryList;
    }
    private CaseHistoryRecord retrieveCaseHistory(CaseHistorySummaryRecord summaryRecord) {
        CaseHistoryService historyService=new CaseHistoryService();
        CaseHistoryRecord response=historyService.getCaseHistoryFor(summaryRecord);
        log.info("Total Case History Retrieved:{}",summaryRecord.Cno());
        log.debug("Case History :{}",response);
        return response;
    }
    public void storeCaseStatus(CaseStatusRepo repo,CaseHistorySummaryRecord summaryRecord, CaseHistoryRecord historyRecord){
        CaseHistoryDBRecord caseHistoryDBRecord=new CaseHistoryDBRecord(null,
                summaryRecord.Cno(),
                DateUtil.getSQLDate(summaryRecord.BusinessDate()),
                summaryRecord.PurposeOfHearing(),
                historyRecord.Business(),
                historyRecord.NextPurpose(),
                DateUtil.getSQLDate(summaryRecord.HearingDate()));
        repo.saveCaseHistory(caseHistoryDBRecord);
        String cno=summaryRecord.Cno();
        log.info("Record {} stored successfully",cno);
    }

}
