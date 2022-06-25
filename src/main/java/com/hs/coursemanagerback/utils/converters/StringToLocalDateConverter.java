package com.hs.coursemanagerback.utils.converters;

import java.time.LocalDate;

public final class StringToLocalDateConverter {
    private StringToLocalDateConverter() {
    }

    public static LocalDate convert(String dateString) {
        if (dateString != null && !dateString.isBlank()) {

            String[] parts = dateString.split("\\.");
            String day = parts[0];
            String month = parts[1];
            String year = parts[2];

            return LocalDate.parse(year + "-" + month + "-" + day);

        }

        return null;
    }
}
