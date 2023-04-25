package com.in.tn.trichy.courtcase.repo;

import com.in.tn.trichy.courtcase.repo.domain.CaseHistoryDBRecord;
import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CaseStatusJdbcRepo implements CaseStatusRepo {
    private static final Logger log= LoggerFactory.getLogger(CaseStatusJdbcRepo.class);

    private final DataSource dataSource;
    private static final String H2_DB_URL="jdbc:h2:file:%s;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM '/Volumes/WDBlack/repos/CourtCaseStatusFetcher/db_init.sql'";
    public CaseStatusJdbcRepo(String dbFileName) {
        JdbcDataSource jdbcDataSource =new JdbcDataSource();
        jdbcDataSource.setURL(H2_DB_URL.formatted(dbFileName));
        dataSource=jdbcDataSource;
    }
    private static final String INSERT_CASE_STATUS_HISTORY_SQL= """
            INSERT INTO CASE_HISTORY (CNO,BUSINESS_DATE,PURPOSE_OF_HEARING,BUSINESS,NEXT_PURPOSE,NEXT_HEARING_DATE)
            VALUES (?,?,?,?,?,?)
            """;

    @Override
    public void saveCaseHistory(CaseHistoryDBRecord caseHistory) {
        log.info("Saving Case History");
        try(PreparedStatement ps=dataSource.getConnection().prepareStatement(INSERT_CASE_STATUS_HISTORY_SQL)){
            ps.setString(1, caseHistory.Cno());
            ps.setDate(2, caseHistory.BusinessDate());
            ps.setString(3, caseHistory.PurposeOfHearing());
            ps.setString(4, caseHistory.Business());
            ps.setString(5, caseHistory.NextPurpose());
            ps.setDate(6, caseHistory.NextHearingDate());
            ps.execute();
            log.info("Saved Case History");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static final String SELECT_CASE_STATUS_HISTORY_SQL= """
    SELECT * FROM CASE_HISTORY WHERE CNO=?
    """;
    @Override
    public List<CaseHistoryDBRecord> getAllCaseHistory(String caseNo) {
        List<CaseHistoryDBRecord> caseHistoryDBRecordList=null;
        try (PreparedStatement statement=dataSource.getConnection().prepareStatement(SELECT_CASE_STATUS_HISTORY_SQL)){
            statement.setString(1,caseNo);
            ResultSet resultSet =statement.executeQuery();
                    caseHistoryDBRecordList =new ArrayList<>();
            while (resultSet.next()){
                CaseHistoryDBRecord caseHistoryDBRecord=new CaseHistoryDBRecord(resultSet.getString(1),
                                                                                resultSet.getString(2),
                                                                                resultSet.getDate(3),
                                                                                resultSet.getString(4),
                                                                                resultSet.getString(5),
                                                                                resultSet.getString(6),
                                                                                resultSet.getDate(7));
                caseHistoryDBRecordList.add(caseHistoryDBRecord);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return caseHistoryDBRecordList;
    }
    private static final String SELECT_NEXT_HEARING_DATE_SQL= """
    SELECT max(NEXT_HEARING_DATE) FROM CASE_HISTORY WHERE CNO=?
    """;
    @Override
    public Date fetchLatestHearingDateList(String cino) {
        Date latestHearingDate = null;
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_NEXT_HEARING_DATE_SQL)) {
            statement.setString(1, cino);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                latestHearingDate = resultSet.getTimestamp(1);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return latestHearingDate;
    }
}
