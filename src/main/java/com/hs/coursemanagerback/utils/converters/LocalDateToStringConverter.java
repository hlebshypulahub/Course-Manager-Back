package com.hs.coursemanagerback.utils.converters;

import java.time.LocalDate;

public class LocalDateToStringConverter {

    public static String convert(LocalDate date) {
        if (date == null) {
            return "";
        }

        String day = date.getDayOfMonth() < 10 ? "0" + date.getDayOfMonth() : String.valueOf(date.getDayOfMonth());
        String month = date.getMonthValue() < 10 ? "0" + date.getMonthValue() : String.valueOf(date.getMonthValue());

        return day + "." + month + "." + date.getYear();
    }

}
