/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pokemon.sim.util.XDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 *
 * @author May5th
 */
public class TestXDate {
    
    private static final Logger logger = LoggerFactory.getLogger(TestXDate.class);
    
    public static void main(String[] args) {
        testNow();
        testParseValid();
        testParseInvalid();
        testFormat();
    }
    
    static void testNow() {
        logger.info("now(): {}", XDate.format(XDate.now()));
    }
    
    static void testParseValid() {
        logger.info("parse valid LocalDate:");
        LocalDate date = XDate.parse("2025-07-19");
        logger.info("parsed LocalDate: {}", XDate.format(date));
        
        logger.info("parse valid LocalDateTime:");
        LocalDateTime dateTime = XDate.parseDateTime("2025-07-19 09:00:00");
        logger.info("parsed LocalDateTime: {}", XDate.format(dateTime));
    }
    
    static void testParseInvalid() {
        logger.info("parse invalid LocalDate:");
        LocalDate invalidDate = XDate.parse("");
        logger.error("Failed to parse LocalDate: {}", XDate.format(invalidDate));
        
        logger.info("parse invalid LocalDateTime:");
        LocalDateTime invalidDateTime = XDate.parseDateTime("invalid-dateTime");
        logger.error("Failed to parse LocalDateTime: {}", XDate.format(invalidDateTime));
    }
    
    static void testFormat() {
        logger.info("format LocalDate:");
        LocalDate date = LocalDate.of(2008, 5, 5);
        logger.info("formatted LocalDate: {}", XDate.format(date));
        
        logger.info("format LocalDateTime:");
        LocalDateTime dateTime = LocalDateTime.of(2008, 5, 5, 9, 0, 0);
        logger.info("formatted LocalDateTime: {}", XDate.format(dateTime));
    }
}
