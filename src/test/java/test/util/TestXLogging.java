/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.util;

import org.slf4j.Logger;
import pokemon.sim.util.XLogging;

/**
 *
 * @author May5th
 */
public class TestXLogging {
    
    private static final Logger logger = XLogging.getLogger(TestXLogging.class);

    public static void main(String[] args) {
        XLogging.divider("Logging Levels");

        logger.trace("This is a TRACE message");
        logger.debug("This is a DEBUG message");
        logger.info("This is an INFO message");
        logger.warn("This is a WARN message");
        logger.error("This is an ERROR message");

        XLogging.divider("Loop Logging Example");
        for (int i = 1; i <= 3; i++) {
            logger.info("Processing item {}", i);
        }

        XLogging.divider("Exception Logging");
        try {
            throw new IllegalStateException("Simulated failure");
        } catch (IllegalStateException e) {
            logger.error("Caught an exception:", e);
        }
    }
}
