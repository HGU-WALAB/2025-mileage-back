package com.csee.swplus.mileage.util;

import java.time.LocalDate;
import java.time.Month;

public class SemesterUtil {
    public static String getCurrentSemester() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        Month month = now.getMonth();

        String semester = (month.getValue() <= 6) ? "01" : "02";
        return semester + " " + year;
    }
}
