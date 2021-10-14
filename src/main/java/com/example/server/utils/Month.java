package com.example.server.utils;

import com.example.server.utils.exceptions.IncorrectMonthException;

import java.util.HashMap;
import java.util.Locale;

public class Month {
    private static HashMap<String, String> month = new HashMap<>();

    static {
        month.put("JANUARY", "01");
        month.put("FEBRUARY", "02");
        month.put("MARCH", "03");
        month.put("APRIL", "04");
        month.put("MAY", "05");
        month.put("JUNE", "06");
        month.put("JULY", "07");
        month.put("AUGUST", "08");
        month.put("SEPTEMBER", "09");
        month.put("OCTOBER", "10");
        month.put("NOVEMBER", "11");
        month.put("DECEMBER", "12");
    }

    public static String getNumeric(String month) throws IncorrectMonthException {
        month = month.toUpperCase(Locale.ROOT);

        String result = Month.month.get(month);

        if (result == null){
            throw new IncorrectMonthException(month);
        }

        return result;
    }

    public static String withLeadingZero(int day){
        if (day < 10){
            return "0" + day;
        }
        else{
            return String.valueOf(day);
        }
    }
}
