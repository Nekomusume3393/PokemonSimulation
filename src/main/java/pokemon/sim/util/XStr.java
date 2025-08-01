/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon.sim.util;

import java.text.Normalizer;
import java.util.Base64;
import java.util.Locale;
import java.util.UUID;

/**
 *
 * @author May5th
 */
public class XStr {
    
    public static boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }
    
    public static String valueOf(Object object) {
        return object == null ? "" : String.valueOf(object);
    }
    
    public static String encodeB64(String text) {
        if (text == null) return "";
        byte[] data = text.getBytes();
        return Base64.getEncoder().encodeToString(data);
    }
    
    public static String decodeB64(String text) {
        if (text == null) return "";
        byte[] data = Base64.getDecoder().decode(text);
        return new String(data);
    }
    
    public static String getKey(String... args) {
        if (args == null || args.length == 0) {
            args = new String[]{
                UUID.randomUUID().toString(),
                String.valueOf(System.currentTimeMillis())
            };
        }
        int hash = String.join("-", args).hashCode();
        return String.format("KEY-%08X", Math.abs(hash));
    }
    
    public static String capitalize(String text) {
        if (isBlank(text)) return "";
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
    
    public static String truncate(String text, int max) {
        if (text == null || text.length() <= max) return text;
        return text.substring(0, max) + "...";
    }
    
    public static String slugify(String input) {
        if (input == null) return "";
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return normalized.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
    }
    
    public static String camelToSnake(String input) {
        if (input == null) return "";
        return input.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }
    
    public static String repeat(String text, int times) {
        return text == null || times <= 0 ? "" : text.repeat(times);
    }
}
