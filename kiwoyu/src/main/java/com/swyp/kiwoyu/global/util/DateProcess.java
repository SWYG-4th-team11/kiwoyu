package com.swyp.kiwoyu.global.util;

import org.springframework.cglib.core.Local;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateProcess {

    public static int calculateDday(Date dueDate){
        return calculateDday(LocalDateTime.ofInstant(dueDate.toInstant(), ZoneId.systemDefault()));
    }

    public static int calculateDday(LocalDateTime dueDate){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal2.set(Calendar.YEAR, dueDate.getYear());
        cal2.set(Calendar.MONTH, dueDate.getMonthValue()-1);
        cal2.set(Calendar.DATE, dueDate.getDayOfMonth());

        int today = cal1.get(Calendar.DAY_OF_YEAR);
        int dueday = cal2.get(Calendar.DAY_OF_YEAR);

        int dDay;

//        if (today > dueday) {
//            cal2.add(Calendar.YEAR, 1);
//            dDay = cal1.getActualMaximum(Calendar.DAY_OF_YEAR) + dueday - today;
//        } else if (today < dueday) {
//            dDay = dueday - today;
//        } else {
//            dDay = 0;
//        }
        dDay = dueday - today;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("오늘: " + df.format(cal1.getTime()));
        System.out.println("목표일: " + df.format(cal2.getTime()));
        System.out.println("D-" + dDay);

        return dDay;
    }
}
