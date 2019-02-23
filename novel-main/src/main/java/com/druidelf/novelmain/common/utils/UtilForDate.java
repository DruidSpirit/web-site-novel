package com.druidelf.novelmain.common.utils;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Months;

import static com.druidelf.novelmain.enums.bussinessType.PeriodTypeEnum.*;


public class UtilForDate {
    /**
     * 返回当前年、月、周、日从开始到结束的毫秒时间戳
     * @param period
     * @return Long[]
     */
    public static Long[] toGetNowTime (String period) {
        Long[] longTime = new Long[2];
        DateTime dateTime = new DateTime();
        if (period.equals( PERIOD_YEAR.statusCode )){
            DateTime dateTime1 = new DateTime(dateTime.getYear(), 1, 1, 0, 0, 0, 0);
            DateTime dateTime2 = new DateTime(dateTime.getYear()+1, 1, 1, 0, 0, 0, 0);
            longTime[0] = dateTime1.getMillis();
            longTime[1] = dateTime2.getMillis();
            return longTime;
        }else if (period.equals( PERIOD_MONTH.statusCode )){
            DateTime dateTime1 = new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), 1, 0, 0, 0, 0);
            int nextMonth = dateTime.getMonthOfYear() + 1;
            if (nextMonth>12) nextMonth = 1;
            DateTime dateTime2 = new DateTime(dateTime.getYear(), nextMonth, 1, 0, 0, 0, 0);
            longTime[0] = dateTime1.getMillis();
            longTime[1] = dateTime2.getMillis();
            return longTime;
        }else if (period.equals( PERIOD_WEEK.statusCode )){
            DateTime dateTime1 = new DateTime(dateTime.getYear(), 1, 1, 0, 0, 0, 0);
            longTime[0] = dateTime1.getMillis()+(dateTime.getWeekOfWeekyear()-1)*7*86400000L;
            longTime[1] = dateTime1.getMillis()+(dateTime.getWeekOfWeekyear())*7*86400000L;
            return longTime;
        }else if (period.equals(PERIOD_DAY.statusCode )){
            DateTime dateTime1 = new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfYear(), 0, 0, 0, 0);
            DateTime dateTime2 = new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfYear()+1, 0, 0, 0, 0);
            longTime[0] = dateTime1.getMillis();
            longTime[1] = dateTime2.getMillis();
            return longTime;
        }

        return longTime;
    }

    /**
     * 给定一个起始时间，然后转换这个起始时间到现在有xx年x月x天x小时
     * @param startTimeArray
     * @return String
     */
    public static String nowTimeTOTranYmdH (int... startTimeArray) {


        DateTime nowTime = new DateTime();
        // 设置不传参数时的默认值
        int[] timeArray = {
                nowTime.getYear(),
                nowTime.getMonthOfYear(),
                nowTime.getDayOfMonth(),
                0
        };
        System.arraycopy(startTimeArray, 0, timeArray, 0, startTimeArray.length);
        DateTime starTime = new DateTime(timeArray[0],timeArray[1],timeArray[2], timeArray[3], 0, 0, 0);
        Months monthTime = Months.monthsBetween(starTime,nowTime);
        // 得到相差的年月
        int years = monthTime.getMonths()/12;
        int months = monthTime.getMonths()%12;
        int temYear = starTime.getYear()+years;
        int temMonth = starTime.getMonthOfYear();
        int temDay = starTime.getDayOfMonth();
        temYear = temYear + ( ( temMonth + months ) / 12 );
        temMonth = ( temMonth + months ) % 12;
        // 大月小月判断
        if ( temDay > 28 && temMonth == 2 ){
            temDay = 28;
        }
        if ( temDay > 30 && (temMonth==4||temMonth==6||temMonth==9||temMonth==11)){
            temDay = 30;
        }
        // 加上相差的年月
        DateTime temHourDate = new DateTime(temYear,temMonth,temDay, starTime.getHourOfDay(), 0, 0, 0);
        Hours hoursTime = Hours.hoursBetween(temHourDate,nowTime);
        int days = hoursTime.getHours()/24;
        int hours = hoursTime.getHours()%24;
        return years+"年"+months+"月"+days+"日"+hours+"小时";
    }
}
