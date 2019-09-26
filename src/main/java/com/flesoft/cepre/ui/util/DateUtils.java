/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.util;

/**
 *
 * @author edrev
 */
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        //new java.util.Date
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = instant.atZone(defaultZoneId).toLocalDateTime();
        return localDateTime;
        //System.out.println("localDateTime : " + localDateTime);

        //return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String localDate(Date date) {
        SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        String out = formateador.format(date);
        return out.toUpperCase();
    }

    public static String localDate(Date date, String strDateFormat) {
        //String strDateFormat = "dd/MMMM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return (date == null) ? "--" : sdf.format(date);
    }

    public static String localDateTime(Date date) {
        String strDateFormat = "dd/MMMM/yy HH:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return (date == null) ? "--" : sdf.format(date);
    }

    public static String localTime(Date date) {
        String strDateFormat = "HH:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return (date == null) ? "--" : sdf.format(date);
    }
}
