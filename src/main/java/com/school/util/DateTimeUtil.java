package com.school.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class DateTimeUtil {


    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";


    //str-->date

    public static Date strToDate(String dateTimeStr,String formatStr){
        DateTimeFormatter formatter  = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = formatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    public static Date strToDate(String dateTimeStr){
        DateTimeFormatter formatter  = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = formatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    //date--str

    /**
     * 日期和格式
     * @param date
     * @param formatStr
     * @return
     */
    public static String dateToStr(Date date,String formatStr){
        if (date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime =  new DateTime(date);
        return dateTime.toString(formatStr);
    }


    public static String dateToStr(Date date){
        if (date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime =  new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }

//    public static void main(String[] args) {
//        System.out.println(DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
//        System.out.println(DateTimeUtil.strToDate("2018-01-01 11:11:11","yyyy-MM-dd HH:mm:ss"));
//    }
}
