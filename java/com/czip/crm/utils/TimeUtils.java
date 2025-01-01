package com.czip.crm.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    // 过去天数
    public static Date getDay(Integer day) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, - day);
        return c.getTime();
    }

    // 过去月份
    public static Date getMonth(Integer month) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, - month);
        return c.getTime();

    }

    // 过去年份
    public static Date getYear(Integer year) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, - year);
        return c.getTime();
    }

    /**
     * 获取前一天的时间
     * @return
     */

    public static String getYesTerDay() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH"); //设置时间格式
        Calendar calendar = Calendar.getInstance();   //得到日历
        calendar.setTime(new Date());//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        String yesterday = sdf.format(calendar.getTime());    //格式化前一天
        return yesterday;
    }

}
