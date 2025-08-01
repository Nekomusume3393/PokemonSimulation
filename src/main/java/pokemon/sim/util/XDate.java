/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon.sim.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 *
 * @author May5th
 */
public class XDate {
    
    public static final String PATTERN_FULL = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_SHORT = "yyyy-MM-dd";
    
    public static final DateTimeFormatter FORMAT_FULL = DateTimeFormatter.ofPattern(PATTERN_FULL).withLocale(Locale.getDefault());
    public static final DateTimeFormatter FORMAT_SHORT = DateTimeFormatter.ofPattern(PATTERN_SHORT).withLocale(Locale.getDefault());
    
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }
    
    public static LocalDate parse(String date) {
        try {
            return LocalDate.parse(date, FORMAT_SHORT);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
    public static LocalDateTime parseDateTime(String dateTime) {
        try {
            return LocalDateTime.parse(dateTime, FORMAT_FULL);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
    public static String format(LocalDate date) {
        return date == null ? null : date.format(FORMAT_SHORT);
    }
    
    public static String format(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(FORMAT_FULL);
    }
}
