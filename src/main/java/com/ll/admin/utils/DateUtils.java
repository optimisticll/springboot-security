package com.ll.admin.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lihaoxuan
 * @date 2021/1/6 14:30
 */
public class DateUtils {
    public static void main(String[] args) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date()); //设置时间为当前时间
        ca.add(Calendar.MONTH, -1);
        Date end = ca.getTime(); //结果
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        System.out.println(sdf.format(end));
    }
}
