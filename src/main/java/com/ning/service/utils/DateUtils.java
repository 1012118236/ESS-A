package com.ning.service.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2019/6/12 14:33
 */
public class DateUtils {
    private static Logger log = LoggerFactory.getLogger(DateUtils.class);
    /**
     * 默认日期格式, yyyy-MM-dd
     */
    public static String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 默认时间格式, yyyy-MM-dd HH:mm:ss
     */
    public static String DEFAULT_TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认格式时间, yyyy-MM
     */
    public static String DEFAULT_YYYY_MM="yyyy-MM";
    /**
     * 判断时间是否为空
     * @param date 接收Date参数
     * @return boolean true 表示接收的时间为 null
     */
    public static Boolean dateIsnull(Date date) {
        if (date == null) {
            return true;
        }
        return false;
    }

    /**
     * 传入多个时间 判空
     * @param  date 接收Date参数
     * @return Boolean 如果其中一个为null 返回true
     */
    public static Boolean timesIsNull(Date... date) {
        for (int i = 0; i < date.length; i++) {
            if (date[i] == null) { return true; }
        }
        return false;
    }
    /**
     * 比较时间的大小
     * @param 	 begin 		接收一个Date参数
     * @param 	 end		接收一个Date参数
     * @return   Boolean	如果begin>end 为true
     */
    public static Boolean begintimeGtEndtime(Date begin, Date end) {
        if (begin.getTime() > end.getTime()) {
            return true;
        }
        return false;
    }
    /**
     * 比较两个时间相等
     * @param 	 begin 		接收一个Date参数
     * @param 	 end   		接收一个Date参数
     * @return   Boolean    如果begin==end 为true
     */
    public static Boolean begintimeEqEndtime(Date begin, Date end) {
        if (begin.getTime() == end.getTime()) {
            return true;
        }
        return false;
    }
    /**
     * 获得当前时间
     * @return  Date()
     */
    public static Date getday() {
        return new Date();
    }
    /**
     * 获得当前时间 并格式化
     * @param 	simpleDateFormat  格式化如：yyyy-MM-dd
     * @return  Date
     */
    public static Date getday(String simpleDateFormat) {
        return getDateByDate(new Date(), simpleDateFormat);
    }
    /**
     * 获得获得当前  yyyy-MM-dd
     * @return  String   格式：yyyy-MM-dd HH:mm:ss
     */
    public static String getToday() {
        return getStringByDate(new Date(), null);
    }
    /**
     * 获得当前时间 并格式化
     * @param 	simpleDateFormat  格式化如：yyyy-MM-dd
     * @return  String
     */
    public static String getToday(String simpleDateFormat) {
        return getStringByDate(null, simpleDateFormat);
    }

    /**
     * 获得时间段内的所有时间，不包含结束时间
     * @param begin 开始时间
     * @param end	     结束时间
     * @param simpleDateFormat     是否对时间格式化,默认 yyyy-MM-dd
     * @return   List<Date>
     */
    public static List<Date> listDateBtnDate(Date begin, Date end, String simpleDateFormat){
        if(StringUtils.isBlank(simpleDateFormat)) simpleDateFormat=DEFAULT_DATE_PATTERN;
        List<Date> list=new ArrayList<Date>();
        if(begintimeGtEndtime(begin, end)){log.error("*****begin gt end"); return list;}
        if(begintimeEqEndtime(begin, end)){log.error("*****begin eq end"); return list;}
        try {
            Calendar cal=Calendar.getInstance();
            cal.setTime(begin);
            while(true){
                if(end.after(cal.getTime())){
                    list.add(cal.getTime());
                }else{
                    break;
                }
                cal.add(Calendar.DATE, 1);
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 得到两个时间内所有时间集合  格式yyyy-MM-dd
     * @param startDate
     * @param endDate
     * @return   List<String>
     */
    public static List<String> listStringBtnString(String startDate, String endDate) {
        List<String> list = new ArrayList<String>();
        list.add(startDate);
        if (!startDate.equals(endDate)) {
            try {
                Calendar cal = Calendar.getInstance();
                Date stadate = getDateByString(startDate);
                Date enddate = getDateByString(endDate);
                cal.setTime(stadate);
                boolean continFlag = true;
                while (continFlag) {
                    cal.add(Calendar.DATE, 1);
                    if (enddate.after(cal.getTime())) {
                        list.add(getStringByDate(cal.getTime(), null));
                    } else {
                        break;
                    }
                }
            } catch ( Exception e) {
                e.printStackTrace();
            }
            list.add(endDate);
        }

        return list;
    }

    /**
     *
     * 获得时间段内时间集合 格式yyyy-MM      输入"2019-01-02", "2019-02-03"
     * @param startDate
     * @param endDate
     * @return   List<String>    得到  [2019-01, 2019-02]
     */
    public static List<String> listStringBtnMonthString(String startDate,String endDate){
        List<String> result=new ArrayList<String>();
        try {
            Calendar min=Calendar.getInstance();
            Calendar max=Calendar.getInstance();
            Date stadate = getDateByString(startDate,DEFAULT_YYYY_MM);
            Date enddate = getDateByString(startDate,DEFAULT_YYYY_MM);
            min.setTime(stadate);
            min.set(min.get(Calendar.YEAR),min.get(Calendar.MONTH),1);
            max.setTime(enddate);
            max.set(max.get(Calendar.YEAR),max.get(Calendar.MONTH),2);
            Calendar curr = min;
            while(curr.before(max)){
                result.add(getStringByDate(curr.getTime(), null));
                curr.add(Calendar.MONTH, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获得日期范围内的所有日期
     * @param startDate
     * @param endDate
     * @param format
     * @return   List<String>
     */
    public static List<String> listStringBtnString(String startDate,String endDate,String format){
        if(StringUtils.isBlank(format))format=DEFAULT_DATE_PATTERN;
        List<String> list=new ArrayList<String>();
        list.add(startDate);
        try {
            Calendar cal=Calendar.getInstance();
            Date stadate = getDateByString(startDate,format);
            Date enddate =getDateByString(endDate,format);
            cal.setTime(stadate);
            boolean continFlag=true;
            while(continFlag){
                cal.add(Calendar.DATE, 1);
                if(enddate.after(cal.getTime())){
                    list.add(getStringByDate(cal.getTime(), null));
                }else{
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        list.add(endDate);
        return list;
    }


    /**
     * 获得两个日期 间隔的所有时间，interval分钟一个点, 不包含结束时间
     * @param startDate   yyyy-MM-dd HH:mm:ss
     * @param endDate     yyyy-MM-dd HH:mm:ss
     * @param interval 间隔多少分钟
     * @return List<String>
     */
    public static List<String> listMinuteBtnDateStr(String startDate, String endDate, int interval) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> list = new ArrayList<String>();
        list.add(startDate);
        if (!startDate.equals(endDate)) {
            try {
                Calendar cal = Calendar.getInstance();
                Date stadate = sdf.parse(startDate);
                Date enddate = sdf.parse(endDate);
                cal.setTime(stadate);
                boolean continFlag = true;
                while (continFlag) {
                    cal.add(Calendar.MINUTE, interval);
                    if (enddate.after(cal.getTime())) {
                        list.add(sdf.format(cal.getTime()));
                    } else {
                        break;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return list;
    }



    /**
     * 获取包括开始时间和结束时间的在内的所有天的数组，其中开始日期和结束日期都为整天
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 时间数组
     */
    public static Date[] getArrayDays(Date startDate, Date endDate) {
        Calendar aCalendar = Calendar.getInstance();
        Calendar bCalendar = Calendar.getInstance();
        aCalendar.setTime(startDate);
        bCalendar.setTime(endDate);
        int days = 0;
        List<Date> dates = new LinkedList<Date>();
        while (aCalendar.before(bCalendar)) {
            dates.add(aCalendar.getTime());
            days++;
            aCalendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return dates.toArray(new Date[days]);
    }





    /**
     * 根据日期获得 昨日 当月 上月 本年
     * @param str
     * @param type  0昨日 1当月 2上月  3本年 4 前天 5 上上月 6 明日 7下月
     * @return String
     */
    public static synchronized String getDateStr(String str,Integer type){
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date date=str==null?new Date():sdf.parse(str);
            Calendar cal=Calendar.getInstance();
            cal.setTime(date);
            switch(type){
                case 0:cal.add(Calendar.DATE,-1);
                    break;
                case 1:cal.set(Calendar.DATE, 1);
                    break;
                case 2:cal.add(Calendar.MONTH,-1);
                    cal.set(Calendar.DATE, 1);
                    break;
                case 3:cal.set(Calendar.MONTH,0);
                    cal.set(Calendar.DATE, 1);
                    break;
                case 4:cal.add(Calendar.DATE,-2);
                    break;
                case 5:cal.add(Calendar.MONTH,-2);
                    cal.set(Calendar.DATE, 1);
                    break;
                case 6:cal.add(Calendar.DATE,1);
                    break;
                case 7:cal.add(Calendar.MONTH,1);
                    cal.set(Calendar.DATE, 1);
                    break;
            }
            Date time=cal.getTime();
            return sdf.format(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     *
     * 根据年月日初始化日期
     * @param year
     * @param month
     * @param day
     * @return   Date
     */
    public static Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    /**  **/
    /**
     *
     * 根据年月日 时 分 秒初始化日期
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return   Date
     */
    public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    /**
     * 將日期加上offset小时，如果offset是正值，时间往后移，如果为负值往前移
     * @param date 时间
     * @param offset 时间偏移小时
     * @return 偏移后的时间
     */
    public Date addHour(Date date, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, offset);
        return calendar.getTime();
    }

    /**
     *
     * 将String时间加减n天 并返回String
     * @param 	 date  		String类型
     * @param 	 n     		加减天数
     * @param 	 simpleDateFormat 格式化类型 与传入的date对应
     * @return   String
     */
    public static String getNextDate(String date, int n, String simpleDateFormat) {
        try {
            if (StringUtils.isBlank(simpleDateFormat)) return null;
            Date d = getDateByString(date, simpleDateFormat);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            calendar.add(Calendar.DAY_OF_MONTH, n);
            return getStringByDate(calendar.getTime(), simpleDateFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     *
     * 将Date时间加减n天 并返回String
     * @param date
     * @param n
     * @param simpleDateFormat
     * @return   String
     */
    public static String getNextDate(Date date, int n, String simpleDateFormat) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, n);
            return getStringByDate(calendar.getTime(), simpleDateFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建目的: 根据时间 获取之后或之前的某天
     * @param date 时间
     * @param n 加一天或者减一天 1 -1
     */
    public static Date getNextDate(Date date, int n) {
        if (date == null)  return null;
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.DAY_OF_MONTH, n);
        return ca.getTime();
    }
    /**
     * 时间月份加减n
     * @param date  String类型时间
     * @param n		加减的次数，  1往后推一月
     * @param format    与date对应的格式
     * @return   String
     */
    public static String getNextMonth(String date,int n,String format){
        try {
            if(StringUtils.isBlank(format)) return null;
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(getDateByString(date,format));
            calendar.add(Calendar.MONTH, n);
            return getStringByDate(calendar.getTime(), format);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 时间月份加减n
     * @param date
     * @param n		          加减的次数，  1往后推一月
     * @param format    不传默认yyyy-MM-dd
     * @return   Date
     */
    public static Date getNextMonth(Date date,int n,String format){
        try {
            if(StringUtils.isBlank(format)) format=DEFAULT_DATE_PATTERN;
            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.MONTH, n);
            return calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static int YEAR = Calendar.YEAR, MONTH = Calendar.MONTH,DATE = Calendar.DATE,
            HOUR = Calendar.HOUR,MINUTE = Calendar.MINUTE, SECOND = Calendar.SECOND, MILLISECOND = Calendar.MILLISECOND;

    /**
     * 将时间  根据 年 或 月 或 日 或 时 或 分 或秒 或 毫秒  周 加减
     * @param date 当前时间
     * @param n    时间增加的量
     * @param type   com.sgcc.sgd5000.utils.DateUtils.DATE,YEAR,MONTH,HOUR...
     * @return Date
     */
    public static Date addTime(Date date, int n, int type) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        switch (type) {
            case Calendar.YEAR : {
                cal.add(1, n);
                break;
            }
            case Calendar.MONTH : {
                cal.add(2, n);
                break;
            }
            case Calendar.DATE : {
                cal.add(5, n);
                break;
            }
            case Calendar.MINUTE : {
                cal.add(12, n);
                break;
            }
            case Calendar.HOUR : {
                cal.add(11, n);
                break;
            }
            case Calendar.SECOND : {
                cal.add(13, n);
                break;
            }
            case Calendar.MILLISECOND : {
                cal.add(14, n);
                break;
            }
            default : {
                return null;
            }
        }
        date = cal.getTime();
        return date;
    }
    /**
     * String 转化  Timestamp    时间戳和日期格式都可以
     * @param date
     * @return Timestamp
     */
    public static Timestamp getTimestampByString(String date) {
        DateFormat format = new SimpleDateFormat(getFormatStr(date));
        format.setLenient(false);
        Timestamp ts = null;
        try {
            ts = new Timestamp(format.parse(date).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ts;
    }
    /**
     * String 转化  Timestamp    时间戳和日期格式都可以
     * @param date
     * @return Timestamp
     */
    public static Timestamp getTimestampByString(String date,String dateFormat) {
        Timestamp ts = null;
        try {
            ts = new Timestamp(getDateByString(date,dateFormat).getTime());
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return ts;
    }
    /**
     * String 转化为 timeStamp
     * 时间戳和日期格式都可以
     * @param obj
     * @return Timestamp
     */
    public static Timestamp getTimestampByString(Object obj) {
        if (obj == null)
            return null;
        String str = String.valueOf(obj);
        if (StringUtils.isBlank(str))
            return null;
        DateFormat format = new SimpleDateFormat(getFormatStr(str));
        format.setLenient(false);
        Timestamp ts = null;
        try {
            ts = new Timestamp(format.parse(str).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ts;
    }


    /**
     * 根据字符串格式  获取格式化字符串 默认  "yyyy-MM-dd HH:mm:ss"; 可以格式yyyy-MM-dd
     * @param date
     * @return String
     */
    private static String getFormatStr(String date){
        String format = DEFAULT_TIMESTAMP_PATTERN;			//默认  yyyy-MM-dd HH:mm:ss
        if (isDate(date)) {										//判断符合条件   yyyy-MM-dd
            format = DEFAULT_DATE_PATTERN;
        }
        return format;
    }

    /**
     效验String 是否为合法的日期格式
     */
    /**
     *
     * 效验String 是否为合法的日期格式
     * @param date
     * @param simpleDateFormat   验证格式，与date格式对应
     * @return   boolean
     */
    public static boolean isValidDate(String date,String simpleDateFormat) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；"yyyy/MM/dd HH:mm:ss"
        SimpleDateFormat format = new SimpleDateFormat(simpleDateFormat);
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(date);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }
    /**
     * 判断字符串是否为日期格式
     * @param strDate
     * @return   boolean
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 判断是否是日期(日期和时间之间有一个或多个空格)
     * @param datTime
     * @return   boolean
     */
    public static boolean isDateTime(String datTime) {
        String timeRegex = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        return Pattern.matches(timeRegex, datTime);
    }
    /**
     * 判断日趋是否整天的0点0分0秒
     *
     * @param date
     *            时间
     * @return 是或否
     */
    public static boolean isDay(Date date) {
        boolean result = false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int millseconds = calendar.get(Calendar.MILLISECOND);
        int seconds = calendar.get(Calendar.SECOND);
        int minute = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        result = (millseconds == 0) && (seconds == 0) && (minute == 0)
                && (hour == 0);
        return result;
    }

    /**
     * 判断日趋是否月初1号的0点0分0秒
     *
     * @param date 时间
     * @return 是或否
     */
    public static boolean isMonth(Date date) {
        boolean result = false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int millseconds = calendar.get(Calendar.MILLISECOND);
        int seconds = calendar.get(Calendar.SECOND);
        int minute = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        result = (millseconds == 0) && (seconds == 0) && (minute == 0)
                && (hour == 0) && (day == 1);
        return result;
    }

    /**
     * 判断日趋是否整小时
     *
     * @param date 时间
     * @return 是或否
     */
    public static boolean isHour(Date date) {
        boolean result = false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int millseconds = calendar.get(Calendar.MILLISECOND);
        int seconds = calendar.get(Calendar.SECOND);
        int minute = calendar.get(Calendar.MINUTE);

        result = (millseconds == 0) && (seconds == 0) && (minute == 0);
        return result;
    }


    /**
     *
     * 获取两时间相差 秒数
     * @param startTime		 yyyy-MM-dd hh:mm:ss
     * @param endTime		 yyyy-MM-dd hh:mm:ss
     * @return int
     */
    public static int getMinute(String startTime,String endTime) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(DEFAULT_TIMESTAMP_PATTERN);
        try {
            long from = simpleFormat.parse(startTime).getTime();
            long to = simpleFormat.parse(endTime).getTime();
            int time = (int) ((to - from)/(1000));// * 60 * 60 * 24
            return time;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获得两日期的分钟间隔(为电量计算做的辅助类)  再加上parameter分钟
     * @param startTime
     * @param endTime
     * @return int
     */
    public static int getMinuteInterval(Date startTime, Date endTime,int parameter) {
        return (int) ((endTime.getTime() - startTime.getTime()) / (1000 * 60)) + parameter;
    }
    /**
     * 获取起止时间内的分钟数
     * @param startDate 开始时间
     * @param endDate 截止时间
     * @return 分钟数
     */
    public static long getMinutesBetween(Date startDate, Date endDate) {
        long minutes = (endDate.getTime() - startDate.getTime()) / (60 * 1000);
        if ((endDate.getTime() - startDate.getTime()) % (60 * 1000) != 0) {
            minutes++;
        }
        return minutes;
    }
    /**
     * 获取起止时间内的小时数
     *
     * @param startDate  开始时间
     * @param endDate  截止时间
     * @return long 小时数
     */
    public long getHoursBetween(Date startDate, Date endDate) {
        long hours = (endDate.getTime() - startDate.getTime())
                / (60 * 60 * 1000);
        if ((endDate.getTime() - startDate.getTime()) % (60 * 60 * 1000) != 0) {
            hours++;
        }
        return hours;

    }
    /**
     * 获得两日期的天数间隔
     * @param startTime
     * @param endTime
     * @return int
     */
    public static int getDayInterval(Date startTime, Date endTime) {
        /*if(getDateByDate(startTime, null).getTime()>=getDateByDate(endTime, null).getTime())return 0;*/
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        int cnt = 0;
        while (calendar.getTime().compareTo(endTime) != 0) {
            calendar.add(Calendar.DATE, 1);
            cnt++;
        }
        return cnt+1;
    }

    /**
     * 获得两日期的月数间隔
     * @param startTime
     * @param endTime
     * @return int
     */
    public static int getMonthInterval(Date startTime, Date endTime) {
        /*if(getDateByDate(startTime, "yyyy-MM").getTime()>=getDateByDate(endTime, "yyyy-MM").getTime())return 0;*/
        Calendar startcal = Calendar.getInstance();
        startcal.setTime(startTime);
        Calendar endcal = Calendar.getInstance();
        endcal.setTime(endTime);
        return (endcal.get(Calendar.YEAR) - startcal.get(Calendar.YEAR)) * 12
                + (endcal.get(Calendar.MONTH) - startcal.get(Calendar.MONTH))
                + 1;

    }

    /**
     * 获得两日期的年数间隔
     * @param startTime
     * @param endTime
     * @return int
     */
    public static int getYearInterval(Date startTime, Date endTime) {
        Calendar startcal = Calendar.getInstance();
        startcal.setTime(startTime);
        Calendar endcal = Calendar.getInstance();
        endcal.setTime(endTime);
        return endcal.get(Calendar.YEAR) - startcal.get(Calendar.YEAR) ;
    }

    /**
     *
     * 将日期转化为日期
     * @param date
     * @param simpleDateFormat  格式化类型默认 yyyy-MM-dd
     * @return Date
     */
    public static Date getDateByDate(Date date, String simpleDateFormat) {
        if (date == null) { return null; }
        if (simpleDateFormat == null || "".equals(simpleDateFormat))
            simpleDateFormat = DEFAULT_DATE_PATTERN;
        SimpleDateFormat sf = new SimpleDateFormat(simpleDateFormat);
        try {
            return sf.parse(sf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 將日期去掉小时、分钟、秒和毫秒，保留时间到整天的零点零分  如2019-01-01 22:22:11 得到 2019-01-01 00:00:00
     * @param date  时间
     * @return 调整后的时间
     */
    public static Date truncDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        System.out.println(truncDay(new Date()));
    }
    /**
     * 将字符串时间格式化为整点字符串
     * @param timeStr   yyyy-mm-dd  HH:mm:ss
     * @return String
     */
    public static String getMinuteByTime(String timeStr){
        SimpleDateFormat sdf = new SimpleDateFormat( DEFAULT_TIMESTAMP_PATTERN);
        Calendar calendar = Calendar.getInstance();
        Date date;
        try {
            date = sdf.parse(timeStr);
            calendar.setTime(date);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(calendar.getTime());

    }
    /**
     * 将date根据格式转化为String
     * @param date 日期
     * @param simpleDateFormat 默认 yyyy-MM-dd
     * @return String
     */
    public static String getStringByDate(Date date, String simpleDateFormat) {
        if (date == null) { return null; }
        if (simpleDateFormat == null || "".equals(simpleDateFormat))
            simpleDateFormat = DEFAULT_DATE_PATTERN;
        SimpleDateFormat sf = new SimpleDateFormat(simpleDateFormat);
        return sf.format(date);

    }
    /**
     * Stirng ---> date 默认
     * @param date
     * @return Date
     */
    public static Date getDateByString(String date) {
        return getDateByString(date,null);
    }
    /**
     * Stirng ---> date
     * @param date
     * @param simpleDateFormat  默认 yyyy-MM-dd HH:mm:ss
     * @return Date
     */
    public static Date getDateByString(String date, String simpleDateFormat) {
        if (date == null) {
            return null;
        }
        if (StringUtils.isBlank(simpleDateFormat))
            simpleDateFormat = DEFAULT_DATE_PATTERN;
        SimpleDateFormat sf = new SimpleDateFormat(simpleDateFormat);
        try {
            return sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     *
     * timestamp 转化为  date
     * @param time
     * @return   Date
     */
    public static Date getDateByTimestamp(Timestamp time){
        return new Date(time.getTime());
    }

    /**
     * String 转化String
     * @param date
     * @param simpleDateFormat
     * @return String
     */
    public static String getStringByString(String date, String simpleDateFormat) {
        if (date == null) {
            return null;
        }
        if (StringUtils.isBlank(simpleDateFormat))
            simpleDateFormat =DEFAULT_DATE_PATTERN;
        SimpleDateFormat sf = new SimpleDateFormat(simpleDateFormat);
        try {
            return sf.format(sf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * timestamp 转化为String
     * @param timestamp
     * @return  String (年-月-日 时:分:秒)
     */
    public static String getStringByTimestamp(Timestamp timestamp){
        return getStringByTimestamp(timestamp,DEFAULT_TIMESTAMP_PATTERN);
    }
    /**
     * timestamp 格式话String
     * @param timestamp
     * @param simpleDateFormat  格式化  必须写
     * @return String
     *
     */
    public static String getStringByTimestamp(Timestamp timestamp,String simpleDateFormat){
        if(timestamp==null ){
            return "";
        }else{
            SimpleDateFormat sdf=new SimpleDateFormat(simpleDateFormat);
            if(sdf.format(timestamp).equals("0000-00-00 00:00:00")){return "";}
            try {
                Date date=sdf.parse(timestamp.toString());
                return sdf.format(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return "";
        }
    }

    /**
     * date 转化  timestamp
     * @param date
     * @return   Timestamp
     */
    public static Timestamp getTimestampByDate(Date date){
        SimpleDateFormat sdf =new SimpleDateFormat(DEFAULT_TIMESTAMP_PATTERN);
        String string=sdf.format(date);
        return Timestamp.valueOf(string);

    }
    /**
     *  date 转化  timestamp
     * @param date
     * @param simpleDateFormat   默认yyyy-MM-dd HH:MM:ss
     * @return   Timestamp
     */
    public static Timestamp getTimestampByDate(Date date,String simpleDateFormat){
        if(simpleDateFormat==null) simpleDateFormat=DEFAULT_TIMESTAMP_PATTERN;
        SimpleDateFormat sdf =new SimpleDateFormat(DEFAULT_TIMESTAMP_PATTERN);
        String string=sdf.format(date);
        return Timestamp.valueOf(string);

    }

    /**
     * 根据时间戳获得时间字符串
     * @param time
     * @return String (年-月-日 时:分:秒)
     */
    @SuppressWarnings("deprecation")
    public static String getDateStrByTimestamp(Timestamp time) {
        String timestr = "";
        if (time == null) {return null;}
        int month = time.getMonth() + 1;
        String monthstr = "";
        if (month < 10) {
            monthstr += "0" + month;
        } else {
            monthstr = Integer.toString(month);
        }
        int day = time.getDate();
        String daystr = "";
        if (day < 10) {
            daystr += "0" + day;
        } else {
            daystr = Integer.toString(day);
        }
        int hour = time.getHours();
        String hourstr = "";
        if (hour < 10) {
            hourstr += "0" + hour;
        } else {
            hourstr = Integer.toString(hour);
        }
        int minute = time.getMinutes();
        String minutestr = "";
        if (minute < 10) {
            minutestr += "0" + minute;
        } else {
            minutestr = Integer.toString(minute);
        }
        int second = time.getSeconds();
        String secondstr = "";
        if (second < 10) {
            secondstr += "0" + second;
        } else {
            secondstr = Integer.toString(second);
        }
        timestr = time.getYear() + 1900 + "-" + monthstr + "-" + daystr
                + " " + hourstr + ":" + minutestr + ":" + secondstr;
        return timestr;
    }
    /**
     * 得到月初的时间
     * @param time  String 类型
     * @return 月初
     * @throws ParseException
     */
    public static String getStringBeginOfMonth(String time){
        SimpleDateFormat sdf=new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        Calendar cal=Calendar.getInstance();
        try {
            Date date=sdf.parse(time);
            cal.setTime(date);
            cal.add(Calendar.MONTH, 0);
            cal.set(Calendar.DAY_OF_MONTH, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdf.format(cal.getTime());
    }

    /**
     * 获得时间月初   如 2019-01-22 得到 2019-01-01
     * @param date
     * @return Date
     */
    public static Date getCurrentMounthFristDay(Date date){
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }
    /**
     * 获得时间 的下个月月初 如2012-02-02 得到 2012-03-01
     * @param date
     * @return Date
     */
    public static Date getNextMounthDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }
    /**
     *
     * 获得月份的上一月初或下一月初       如2017-11-13 n=-1 得到2017-10-01；n=0得到2017-11-01
     * @param date
     * @param  n  		月份加减
     * @return  Date
     */
    public static Date getNextMounthDate(Date date, Integer n) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.MONTH, n);
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 获得月最后一天 获得时间月初 如 2019-01-22 得到 2019-01-31
     *
     * @param date
     * @return Date
     */
    public static Date getCurrentMounthLastDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.getTime();
    }

    /**
     * 根据月份最后一天
     *
     * @param time
     * @return String
     */
    public static String getLastDayOfMonth(String time) {
        SimpleDateFormat sf = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        int year = 0;
        int month = 0;
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        year = cal.get(Calendar.YEAR);// 获取年份
        month = cal.get(Calendar.MONTH);// 获取月份
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return sf.format(cal.getTime());
    }

    /**
     * 将月份加减i，得到某月月末  如 i=1;2012-02-02 得到 2012-03-31
     * @param date
     * @return Date
     *
     */
    public static Date getNextMounthAfterDate(Date date,int i) {
        if (date != null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.MONTH, i);
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }
    /**
     * 得到某年的1月1号
     *
     * @param timeStr
     * @return String
     */
    public static String getYearFirstDayByTime(String timeStr){
        if(StringUtils.isBlank(timeStr))return null;
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        Calendar calendar = Calendar.getInstance();
        Date date;
        try {
            date = sdf.parse(timeStr);
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, 0);
            calendar.set(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(calendar.getTime());
    }
    /**
     *
     * 获得传入时间的1月1号
     * @param timeStr
     * @return   Date
     */
    public static Date getDateYearMonthFirstDay(String timeStr){
        if(StringUtils.isBlank(timeStr))return null;
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        Calendar calendar = Calendar.getInstance();
        Date date;
        try {
            date = sdf.parse(timeStr);
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, 0);
            calendar.set(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  calendar.getTime();
    }
    /**
     * 获得本年本月第一天
     * @return Timestamp
     */
    @SuppressWarnings("deprecation")
    public  static  Timestamp getTimeStampYearMonthFirstDay(){
        String str=null;
        Date date=new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM");
        str=df.format(date);
        String[] strs=str.split("-");
        Timestamp datetemp=new Timestamp(Integer.parseInt(strs[0])-1900,Integer.parseInt(strs[1])-1, 1, 0, 0, 0, 0);
        return datetemp;
    }

    /**
     * 按照给定的时间间隔（秒）处理成满足时间间隔的时间，例如时间间隔传入的是900s（15分钟），时间采用去除多余秒的方式调整为00、15、30、45分
     * @param date 时间
     * @param period 时间间隔（s）
     * @return 调整后的时间
     */
    public static Date truncPeriod(Date date, int period) {
        long millseconds = date.getTime();
        millseconds = (millseconds / (1000 * period)) * 1000 * period;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millseconds);
        return calendar.getTime();
    }


    /**
     * 判断两个日期是否在同一个月里面
     * @param d1  日期1
     * @param d2 日期2
     * @return boolean
     */
    public static boolean isSameMonth(Date d1, Date d2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1);
        int sy = cal.get(Calendar.YEAR);
        int sm = cal.get(Calendar.MONTH);
        cal.setTime(d2);
        int ey = cal.get(Calendar.YEAR);
        int em = cal.get(Calendar.MONTH);
        return ((ey - sy) * 12 + em - sm) == 0;
    }


    /**
     *
     * 获得时间的下一年 的1月1号
     * @param date
     * @return   Date
     */
    public   static Date  getNextYear(Date  date){
        Calendar  cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, 1);
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        return  cal.getTime();

    }

    /**
     * 得到时间的分钟  如   2017-7-5 10:25:23  得到25
     * @param date
     * @return   Integer
     */
    public static  Integer minute(Date date){
        SimpleDateFormat  sDateFormat=new SimpleDateFormat("mm");
        if(date==null) return -1;
        return  Integer.valueOf(sDateFormat.format(date)) ;
    }

    /**
     *
     * 时间 加或 减  n 分钟
     * @param date
     * @param min
     * @param simpleDateFormat   默认"yyyy-MM-dd HH:mm:ss";
     * @return   Date
     */
    public   static  Date  getNextMinter(Date date,Integer  min,String simpleDateFormat){
        if(date==null )  return   null;
        if(min==null) return  null;
        if(simpleDateFormat==null || "".equals(simpleDateFormat)) simpleDateFormat=DEFAULT_TIMESTAMP_PATTERN;
        SimpleDateFormat format = new SimpleDateFormat(simpleDateFormat);

        Long time=date.getTime()+min*60*1000;
        try {
            return format.parse(format.format(time)) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
