package com.in.tn.trichy.courtcase.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateUtil {
    private DateUtil(){
        throw new IllegalStateException("Date Utility Class");
    }
    private static Logger log = LoggerFactory.getLogger(DateUtil.class);
    public static java.util.Date getUtilDate(String dateString){
        java.util.Date parsedDate=null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        try {
            parsedDate= formatter.parse(dateString);
        } catch (ParseException e) {
          log.error("Unable to parse to Util date:{}",dateString);
          e.printStackTrace();
        }
        return parsedDate;

    }
    public static Date getSQLDate(String s){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date sqlDate=null;
        try {
            java.util.Date date = sdf.parse(s);
            sqlDate=new Date(date.getTime());
        } catch (ParseException e) {
            log.error("Unable to parse to SQL date:{}",s);
            e.printStackTrace();
        }
        return sqlDate;
    }
}
