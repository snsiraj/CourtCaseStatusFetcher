package com.in.tn.trichy.courtcase.server.resource;

import com.in.tn.trichy.courtcase.repo.CaseStatusJdbcRepo;
import com.in.tn.trichy.courtcase.repo.CaseStatusRepo;
import com.in.tn.trichy.courtcase.repo.domain.CaseHistoryDBRecord;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/cases")
public class CaseInformationResource {
    private static final Logger LOG = LoggerFactory.getLogger(CaseInformationResource.class);
    @GET
    @Path("/prehistorical")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CaseHistoryDBRecord> getCaseHistory(@QueryParam("caseid") String caseId,
                                       @QueryParam("caseno") String caseNo){
        CaseStatusRepo repo=new CaseStatusJdbcRepo("/Volumes/WDBlack/repos/CourtCaseStatusFetcher/db/CASE_STATUS_TEST_H2.db.mv.db");
        List<CaseHistoryDBRecord> allCaseHistory = repo.getAllCaseHistory(caseNo);
        LOG.info("Fetching case history for:{}",caseNo);
        LOG.info(allCaseHistory.toString());
        return allCaseHistory;
    }
}
