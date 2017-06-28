package com.gpdata.template.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /**
     * method 将字符串类型的日期转换为一个timestamp（时间戳记java.sql.Timestamp）
     *
     * @return dataTime timestamp
     */
    public static Timestamp strToTimestamp(String date, String pattern) {
        Timestamp dateTime = null;
        java.text.DateFormat df2 = new SimpleDateFormat(pattern);
        try {
            Date date2 = df2.parse(date);
            dateTime = new Timestamp(date2.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    /***
     * 将timeType转换后的浮点型的值
     */
    public static long getTimeToLong(String date, String format) {
        long time = 0L;
        String timeShowFormat = format;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(timeShowFormat);
            ParsePosition pos = new ParsePosition(0);
            time = formatter.parse(date, pos).getTime();
        } catch (Exception ex) {
            //System.err.println("Format " + timeShowFormat + " error : " + ex);
        }
        return time;
    }


    public static Long getStartMonth() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.add(Calendar.MONTH, 0);
        todayStart.set(Calendar.DAY_OF_MONTH, 1);// 当月第一天
        todayStart.set(Calendar.HOUR_OF_DAY, 0); // HOUR是 整点时间 HOUR_OF_DAY是当天0点
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime().getTime();
    }

    public static Long getStartWeek() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.DAY_OF_WEEK, 2);// 当月第一天
        todayStart.set(Calendar.HOUR_OF_DAY, 0); // HOUR是 整点时间 HOUR_OF_DAY是当天0点
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime().getTime();
    }

    public static Long getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0); // HOUR是 整点时间 HOUR_OF_DAY是当天0点
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime().getTime();
    }

    public static Long getStartHourTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR, 0); // HOUR是 整点时间 HOUR_OF_DAY是当天0点
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime().getTime();
    }

    /**
     * 获取当年的第一天
     *
     * @return
     */
    public static long getCurrYearFirst() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear).getTime();
    }

    /**
     * 获取当年的最后一天
     *
     * @return
     */
    public static Date getCurrYearLast() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

    public static Long getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime().getTime();
    }

    public static Long getNowTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.get(Calendar.HOUR_OF_DAY); // HOUR是 整点时间 HOUR_OF_DAY是当天0点
        todayStart.get(Calendar.MINUTE);
        todayStart.get(Calendar.SECOND);
        todayStart.get(Calendar.MILLISECOND);
        return todayStart.getTime().getTime();
    }

    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
        List<Date> lDate = new ArrayList<Date>();
        lDate.add(beginDate);// 把开始时间加入集合
        Calendar cal = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
                lDate.add(cal.getTime());
            } else {
                break;
            }
        }
        lDate.add(endDate);// 把结束时间加入集合
        return lDate;
    }

    public static List<String> countDay(long fTime, long lTime) {
        // fTime = 1438498591;
        // lTime = 1442419199;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date dBegin = format.parse(format.format(fTime * 1000));
            Date dEnd = format.parse(format.format(lTime * 1000));
            List<Date> listDate = getDatesBetweenTwoDate(dBegin, dEnd);

            List<String> listdate = new ArrayList<String>();
            for (int i = 0; i < listDate.size(); i++) {

                listdate.add(format.format(listDate.get(i)).replaceAll("-", ""));
            }
            return listdate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    public static List<String> countWeek(long fTime, long lTime) {
        // fTime = 1438498591;
        // lTime = 1442419199;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date dBegin = format.parse(format.format(fTime * 1000));
            Date dEnd = format.parse(format.format(lTime * 1000));
            List<Date> listDate = getDatesBetweenTwoDate(dBegin, dEnd);

            Set<String> listdate = new TreeSet<>();
            listDate.forEach(dt -> {
                MyDate myDate = getWeek(formatDate(dt, "yyyyMMdd"), "yyyyMMdd");
                listdate.add(myDate.getYear()
                        + "" + (myDate.getMonth() > 9 ? myDate.getMonth() : "0" + myDate.getMonth())
                        + "" + (myDate.getWeekInMonth() > 9 ? myDate.getWeekInMonth() : "0" + myDate.getWeekInMonth()));
            });
            return listdate.stream().collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    public static Date longToDate(long longStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Long time = new Long(longStr);
            String d = format.format(time * 1000);
            Date date = format.parse(d);
            return date;
        } catch (Exception e) {

        }
        return null;

    }

    /**
     * 根据日期字符串判断当月第几周
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static MyDate getWeek(String str, String format) {
        try {
            MyDate MyDate = new MyDate();
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(str);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            //第几周
            MyDate.setDayInWeek(calendar.get(Calendar.DAY_OF_WEEK));
            MyDate.setDayInWeekInMonth(calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
            MyDate.setDayInMonth(calendar.get(Calendar.DAY_OF_MONTH));
            MyDate.setDayInYear(calendar.get(Calendar.DAY_OF_YEAR));
            MyDate.setWeekInMonth(calendar.get(Calendar.WEEK_OF_MONTH));
            MyDate.setWeekInYear(calendar.get(Calendar.WEEK_OF_YEAR));
            MyDate.setYear(calendar.get(Calendar.YEAR));
            MyDate.setMonth(calendar.get(Calendar.MONTH) + 1);
            return MyDate;
        } catch (Exception e) {
            logger.error("Exception:{}", e);
        }
        return null;
    }

    //按照给定的格式化字符串格式化日期
    public static String formatDate(Date date, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(date);
    }

    //按照给定的格式化字符串解析日期
    public static Date parseDate(String dateStr, String formatStr) {
        Date date = null;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            date = sdf.parse(dateStr);
            return date;
        } catch (Exception e) {
            logger.error("Exception:{}", e);
        }
        return date;
    }

    public static void main(String[] args) {
//		 System.out.println(DateUtils.strToTimestamp("2011-06-20", "yyyy-MM-dd").getTime());
        System.out.println(DateUtils.getTimeToLong("2015-04-29 20:31:12", "yyyy-MM-dd HH:mm:ss") / 1000);
        // System.out.println(DateUtils.strToTimestamp("2011.06.20",
        // "yyyy.MM.dd").getTime());
        long fTime = 1438498591;
        long lTime = 1442419199;
        System.out.println(DateUtils.countDay(fTime, lTime));
        System.out.println(DateUtils.countWeek(fTime, lTime));
//        System.out.println(DateUtils.getStartMonth() / 1000);
//        System.out.println(DateUtils.getEndTime() / 1000);
        String a = "2017-02-16";
//        System.out.println(getTimeToLong(a, "yyyy-MM-dd hh:mm:ss"));
//        System.out.println(getTimeToLong(a, "yyyy-MM-dd"));
//        MyDate myDate = getWeek(a, "yyyy-MM-dd");
//        System.out.println(myDate);
//        Date d1 = parseDate("20170505", "yyyyMMdd");
//        System.out.println(d1);
//        String d2 = formatDate(d1, "yyyy-MM-dd");
//        System.out.println(d2);
        a = "20160106";
        MyDate myDate = getWeek(a, "yyyyMMdd");
        System.out.println(myDate);
        Date d = new Date();
        String fms = formatDate(d, "hh:mm:ss");
        System.out.println(fms);
    }

    /**
     * 获取今日前一天0点到24点的map
     **/

    public Map<String, Object> getBetweenDay() {
        Map<String, Object> map = new HashMap<String, Object>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date zero = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        String format = simpleDateFormat.format(zero);
        String format1 = simpleDateFormat1.format(zero);
        map.put("fromDate", format);
        map.put("toDate", format1);
        return map;

    }

    /**
     * 在当前时间的基础上，加上或减去指定的天数
     * @param day
     * @return
     */
    public static Date getSomeDay(int day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

}
class MyDate {

    private Integer dayInWeek;
    private Integer dayInWeekInMonth;
    private Integer dayInMonth;
    private Integer dayInYear;
    private Integer weekInMonth;
    private Integer weekInYear;
    private Integer year;
    private Integer month;

    public Integer getDayInWeek() {
        return dayInWeek;
    }

    public void setDayInWeek(Integer dayInWeek) {
        this.dayInWeek = dayInWeek;
    }

    public Integer getDayInMonth() {
        return dayInMonth;
    }

    public void setDayInMonth(Integer dayInMonth) {
        this.dayInMonth = dayInMonth;
    }

    public Integer getDayInYear() {
        return dayInYear;
    }

    public void setDayInYear(Integer dayInYear) {
        this.dayInYear = dayInYear;
    }

    public Integer getWeekInMonth() {
        return weekInMonth;
    }

    public void setWeekInMonth(Integer weekInMonth) {
        this.weekInMonth = weekInMonth;
    }

    public Integer getWeekInYear() {
        return weekInYear;
    }

    public void setWeekInYear(Integer weekInYear) {
        this.weekInYear = weekInYear;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDayInWeekInMonth() {
        return dayInWeekInMonth;
    }

    public void setDayInWeekInMonth(Integer dayInWeekInMonth) {
        this.dayInWeekInMonth = dayInWeekInMonth;
    }
}
