/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.util;

import pokemon.sim.util.XJdbc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author May5th
 */
public class TestXJdbc {

    private static final Logger logger = LoggerFactory.getLogger(TestXJdbc.class);

    public static void main(String[] args) {
        testQuerySuccess();
        testQueryWarn();
        testQuerySyntaxError();
        testQueryWrongTable();
        // testUpdateSuccess();
    }

    static void testQuerySuccess() {
        logger.info("Running testQuerySuccess...");
        String sql = "SELECT TOP 1 Name FROM Test"; // Must have Test table
        try (ResultSet rs = XJdbc.executeQuery(sql)) {
            if (rs.next()) {
                logger.info("Success: First name is '{}'", rs.getString("Name"));
            } else {
                logger.warn("No data found.");
            }
        } catch (Exception e) {
            logger.error("Exception in testQuerySuccess", e);
        }
    }
    
    static void testQueryWarn() {
        logger.info("Running testQueryWarn...");
        String sql = "SELECT TOP 1 Name FROM Test2"; // Must have blank Test2 table
        try (ResultSet rs = XJdbc.executeQuery(sql)) {
            if (rs.next()) {
                logger.info("Success: First name is '{}'", rs.getString("Name"));
            } else {
                logger.warn("No data found.");
            }
        } catch (Exception e) {
            logger.error("Exception in testQueryWarn", e);
        }
    }

    static void testQuerySyntaxError() {
        logger.info("Running testQuerySyntaxError...");
        String sql = "SELEKT * FROM Test"; // Syntax error
        try {
            XJdbc.executeQuery(sql);
        } catch (RuntimeException e) {
            logger.error("Expected error caught (syntax): {}", e.getMessage());
        }
    }

    static void testQueryWrongTable() {
        logger.info("Running testQueryWrongTable...");
        String sql = "SELECT * FROM Tet"; // Wrong table's name
        try {
            XJdbc.executeQuery(sql);
        } catch (RuntimeException e) {
            logger.error("Expected error caught (missing table): {}", e.getMessage());
        }
    }

    static void testUpdateSuccess() {
        logger.info("Running testUpdateSuccess...");
        String sql = "UPDATE Test SET Name = ? WHERE id = 1";
        int rows = XJdbc.executeUpdate(sql, "Vu Thanh Chung");
        logger.info("{} row(s) updated.", rows);
    }

}
