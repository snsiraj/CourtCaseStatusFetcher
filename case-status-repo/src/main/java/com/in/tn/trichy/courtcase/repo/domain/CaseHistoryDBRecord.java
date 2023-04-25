package com.in.tn.trichy.courtcase.repo.domain;

import java.sql.Date;

public record CaseHistoryDBRecord(String Id,
                                  String Cno,
                                  Date BusinessDate,
                                  String PurposeOfHearing,
                                  String Business,
                                  String NextPurpose,
                                  Date NextHearingDate) {
}
