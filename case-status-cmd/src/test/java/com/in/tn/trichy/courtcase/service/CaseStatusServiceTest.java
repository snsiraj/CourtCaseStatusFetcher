package com.in.tn.trichy.courtcase.service;

import com.in.tn.trichy.courtcase.repo.CaseStatusJdbcRepo;
import org.junit.jupiter.api.Test;

class CaseStatusServiceTest {

    @Test
    void processCaseStatus() {
        CaseStatusService service=new CaseStatusService(new CaseStatusJdbcRepo("./../db/CASE_STATUS_TEST_H2.db"));
        service.processCaseStatus("231700004862022","TNTP050010412022");
    }
}