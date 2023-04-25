package com.in.tn.trichy.courtcase.repo;

import com.in.tn.trichy.courtcase.repo.domain.CaseHistoryDBRecord;

import java.util.Date;
import java.util.List;

public interface CaseStatusRepo {
    void saveCaseHistory(CaseHistoryDBRecord caseHistory);
    List<CaseHistoryDBRecord> getAllCaseHistory(String caseNo);
    static CaseStatusRepo getCaseStatusHistoryRepo(String dbFileName){
        return new CaseStatusJdbcRepo(dbFileName);
    }

    Date fetchLatestHearingDateList(String cino);
}
