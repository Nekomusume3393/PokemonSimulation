/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pokemon.sim.util.XStr;

/**
 *
 * @author May5th
 */
public class TestXStr {
    
    private static final Logger logger = LoggerFactory.getLogger(TestXStr.class);
    
    public static void main(String[] args) {
        testIsBlank();
        testValueOf();
        testBase64();
        testGetKey();
        testCapitalize();
        testTruncate();
        testSlugify();
        testCamelToSnake();
        testRepeat();
    }
    
    static void testIsBlank() {
        logger.info("isBlank tests:");
        logger.info("Null → {}", XStr.isBlank(null));
        logger.info("\"   \" → {}", XStr.isBlank("   "));
        logger.info("\"abc\" → {}", XStr.isBlank("abc"));
    }
    
    static void testValueOf() {
        logger.info("valueOf tests:");
        logger.info("null → '{}'", XStr.valueOf(null));
        logger.info("123 → '{}'", XStr.valueOf(123));
    }

    static void testBase64() {
        logger.info("Base64 tests:");
        String encoded = XStr.encodeB64("Hello World");
        logger.info("Encoded: {}", encoded);
        String decoded = XStr.decodeB64(encoded);
        logger.info("Decoded: {}", decoded);
        logger.info("Decode null: '{}'", XStr.decodeB64(null));
    }

    static void testGetKey() {
        logger.info("getKey tests:");
        logger.info("Random key: {}", XStr.getKey());
        logger.info("Custom key: {}", XStr.getKey("Test", "123"));
    }

    static void testCapitalize() {
        logger.info("capitalize tests:");
        logger.info("null → '{}'", XStr.capitalize(null));
        logger.info("\"\" → '{}'", XStr.capitalize(""));
        logger.info("\"java\" → '{}'", XStr.capitalize("java"));
    }

    static void testTruncate() {
        logger.info("truncate tests:");
        logger.info("Short: '{}'", XStr.truncate("short", 10));
        logger.info("Long:  '{}'", XStr.truncate("This is a long sentence", 10));
    }

    static void testSlugify() {
        logger.info("slugify tests:");
        logger.info("\"Xin chào thế giới!\" → {}", XStr.slugify("Xin chào thế giới!"));
        logger.info("null → '{}'", XStr.slugify(null));
    }

    static void testCamelToSnake() {
        logger.info("camelToSnake tests:");
        logger.info("\"camelCaseExample\" → {}", XStr.camelToSnake("camelCaseExample"));
        logger.info("null → '{}'", XStr.camelToSnake(null));
    }

    static void testRepeat() {
        logger.info("repeat tests:");
        logger.info("Repeat 'x' 5 times → '{}'", XStr.repeat("x", 5));
        logger.info("Repeat null → '{}'", XStr.repeat(null, 3));
    }
}
