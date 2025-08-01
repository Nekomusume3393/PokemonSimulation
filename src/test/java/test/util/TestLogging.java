/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author May5th
 */
public class TestLogging {
    
    private static final Logger logger = LoggerFactory.getLogger(TestLogging.class);
    
    public static void main(String[] args) {
        logger.info("Logging: This is an information message");
        logger.warn("Logging: This is a warning.");
        logger.error("Logging: This is an error.");
    }
    
}
