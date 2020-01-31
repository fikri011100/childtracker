package com.titi.remotbayi.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalculateDateImmunization {

    public static String formatDate(int date) {
        int year;
        if (date == 0) {
            return "Baru Lahir";
        } else if (date > 12) {
            if (date % 12 == 0) {
                year = date / 12;
                return year + " Tahun";
            } else {
                year = date / 12;
                int plusMonth = date - 12 * year;
                return year + " Tahun " + plusMonth + " Bulan";
            }
        } else {
            return date + " Bulan";
        }
    }

    public static String startDate(int bulan, String tglLahir) {
        String date;
        int month, year, monthMin, yearPlus, day;
        String m, d;
        month = Integer.parseInt(tglLahir.substring(6, 7));
        year = Integer.parseInt(tglLahir.substring(0, 4));
        day = Integer.parseInt(tglLahir.substring(tglLahir.length() - 2));
        month = month + bulan + 1;
        if (month > 12) {
            yearPlus = month / 12;
            year = year + yearPlus;
            monthMin = month - 12 * yearPlus;
            m = String.valueOf(monthMin);
            d = String.valueOf(day);
            if (monthMin < 10) {
                m = "0" + monthMin;
            }
            if (day < 10) {
                d = "0" + day;
            }
            date = year + "-" + m + "-" + d + " 07:00:00";
            return date;
        } else {
            m = String.valueOf(month);
            d = String.valueOf(day);
            if (month < 10) {
                m = "0" + month;
            }
            if (day < 10) {
                d = "0" + day;
            }
            date = year + "-" + m + "-" + d + " 07:00:00";
            return date;
        }
    }

    public static String endDate(int bulan, String tglLahir) {
        String date;
        int month, year, monthMin, yearPlus, day;
        String m, d;
        month = Integer.parseInt(tglLahir.substring(6, 7));
        year = Integer.parseInt(tglLahir.substring(0, 4));
        day = Integer.parseInt(tglLahir.substring(tglLahir.length() - 2));
        month = month + bulan + 1;
        if (month > 12) {
            yearPlus = month / 12;
            year = year + yearPlus;
            monthMin = month - 12 * yearPlus;
            m = String.valueOf(monthMin);
            d = String.valueOf(day);
            if (monthMin < 10) {
                m = "0" + monthMin;
            }
            if (day < 10) {
                d = "0" + day;
            }
            date = year + "-" + m + "-" + d + " 15:00:00";
            return date;
        } else {
            m = String.valueOf(month);
            d = String.valueOf(day);
            if (month < 10) {
                m = "0" + month;
            }
            if (day < 10) {
                d = "0" + day;
            }
            date = year + "-" + m + "-" + d + " 15:00:00";
            return date;
        }
    }

}
