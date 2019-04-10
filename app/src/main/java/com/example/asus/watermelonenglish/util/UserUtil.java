package com.example.asus.watermelonenglish.util;

import com.example.asus.watermelonenglish.bean.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by asus on 2019/3/27.
 */

public class UserUtil {
    public static int getWordType(User user){
        String level=user.getUserLevel().trim();
        int wordTypeId = 1;
        if(level.equals("英语四级")){
            wordTypeId = 1;
        }
        if(level.equals("英语六级")){
            wordTypeId = 1;
        }
        if(level.equals("英语托福")){
            wordTypeId = 1;
        }
        if(level.equals("英语雅思")){
            wordTypeId = 1;
        }
        return wordTypeId;
    }
    public static String getTodayDate(){
        Calendar calendar = Calendar.getInstance();
        int yearTag = calendar.get(Calendar.YEAR);//当前年
        int monthTag = calendar.get(Calendar.MONTH)+1;//当前月
        int dayTag = calendar.get(Calendar.DAY_OF_MONTH);//当前日
        return yearTag+"-"+monthTag+"-"+dayTag;
    }

    public static int longOfTwoDate(User user){
        Calendar calendar1 = Calendar.getInstance();
        int yearTag = calendar1.get(Calendar.YEAR);//当前年
        int monthTag = calendar1.get(Calendar.MONTH)+1;//当前月
        int dayTag = calendar1.get(Calendar.DAY_OF_MONTH);//当前日
        String second = yearTag+"-"+monthTag+"-"+dayTag;
        String first =user.getUserUseDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date firstdate = null;
        Date seconddate = null;
        try {
            firstdate = format.parse(first);
            seconddate = format.parse(second);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(firstdate);
        int cnt = 0;
        while (calendar.getTime().compareTo(seconddate) != 0) {
            calendar.add(Calendar.DATE, 1);
            cnt++;
        }
        return cnt;
    }
}
