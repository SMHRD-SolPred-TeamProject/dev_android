package com.example.solarpred;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyFormatter extends IndexAxisValueFormatter {

    @Override
    public String getFormattedValue(float value) {
       // System.out.println("value : " + value);
        int index = Math.round(value);
        String result = Integer.toString(index);
        int values = (int) (index);
        Long sysTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
        String dTime = formatter.format(sysTime);
        Calendar cal = Calendar.getInstance();

        try {
            Date date = formatter.parse(dTime);
            cal.setTime(date);
            cal.add(Calendar.SECOND,values);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String time = formatter.format(cal.getTime());
       // System.out.print("cal : " + time + "values : "+values);

        return dTime;
    }
}
