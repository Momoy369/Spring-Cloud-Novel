package com.java2nb.common.utils;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Pemrosesan tanggal
 */
public class DateUtils {
    private final static Logger logger = LoggerFactory.getLogger(DateUtils.class);
    public final static String YEAR_PATTERN = "yyyy";
    public final static String MONTH_PATTERN = "MM";
    public final static String DAY_PATTERN = "dd";
    /**
     * Format waktu (tttt-BB-hh)
     */
    public final static String DATE_PATTERN = "dd-MM-yyyy";
    /**
     * Format waktu (tttt-BB-hh JJ: mm: dd)
     */
    public final static String DATE_TIME_PATTERN = "dd-MM-yyyy HH:mm:ss";

    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * Hitung berapa jarak sekarang, tidak akurat
     *
     * @param date
     * @return
     */
    public static String getTimeBefore(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "Hari";
        } else if (hour > 0) {
            r += hour + "Jam";
        } else if (min > 0) {
            r += min + "Menit";
        } else if (s > 0) {
            r += s + "Detik";
        }
        r += "Sebelumnya";
        return r;
    }

    /**
     * Hitung berapa jarak sekarang, akurat
     *
     * @param date
     * @return
     */
    public static String getTimeBeforeAccurate(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "Hari";
        }
        if (hour > 0) {
            r += hour + "Jam";
        }
        if (min > 0) {
            r += min + "Menit";
        }
        if (s > 0) {
            r += s + "Detik";
        }
        r += "Sebelumnya";
        return r;
    }

    /**
     * Dapatkan tanggal beberapa hari terakhir
     *
     * @param past
     * @return
     */
    @SneakyThrows
    public static String getPastDate(int past,Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - past);
        Date today = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(today);
    }

    /**
     * Dapatkan kumpulan tanggal dalam beberapa hari terakhir
     *
     * @param past
     * @return
     */
    public static List<String> getDateList(int past,Date date) {
        List<String> result = new ArrayList<>(past);
        for(int i = past - 1 ; i > 0 ; i--){
            result.add(getPastDate(i,date));
        }
        //Tanggal hari ini
        result.add(new SimpleDateFormat("dd-MM-yyyy").format(date));
        return result;

    }
}
