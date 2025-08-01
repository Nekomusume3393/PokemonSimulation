/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon.sim.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author May5th
 */
public class XLogging {
    
    private static final Map<Class<?>, Logger> loggerMap = new HashMap<>();

    /**
     * Get a logger associated with the given class.
     * @param clazz the class for which to retrieve the logger
     * @return Logger instance
     */
    public static Logger getLogger(Class<?> clazz) {
        return loggerMap.computeIfAbsent(clazz, LoggerFactory::getLogger);
    }

    /**
     * Print a message with visual divider.
     * Useful for debugging or separating test sections.
     * @param label section label
     */
    public static void divider(String label) {
        System.out.println("\n" + "-".repeat(72));
        System.out.println("[ " + label + " ]");
        System.out.println("-".repeat(72));
    }
}
