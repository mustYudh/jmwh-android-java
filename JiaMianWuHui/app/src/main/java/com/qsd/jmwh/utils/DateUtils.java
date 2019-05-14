package com.qsd.jmwh.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <pre>
 *     author : mks
 *     e-mail : xxx@xx
 *     time   : 2018/06/23
 *     desc   :
 * </pre>
 */

public class DateUtils {

    //时间戳转字符串
    public static String getStrTime(String timeStamp) {
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long l = Long.valueOf(timeStamp);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }





    /**
     * 计算时间差
     *
     * @param starTime 开始时间
     * @param endTime  结束时间
     * @return 返回时间差
     */
    public static String getTimeDifference(long starTime, long endTime) {
     /*  *计算time2减去time1的差值 差值只设置 几天 几个小时 或 几分钟  * 根据差值返回多长之间前或多长时间后  * */
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long diff;
        String flag;
        if (starTime < endTime) {
            diff = endTime - starTime;
            flag = "前";
        } else {
            diff = starTime - endTime;
            flag = "后";
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day != 0) return day + "天" + flag;
        if (hour != 0) return hour + "小时" + flag;
        if (min != 0) return min + "分钟" + flag;
        return "刚刚";
    }
}
