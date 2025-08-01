/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon.sim.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 *
 * @author May5th
 */
public class XJdbc {
    
    private static final Logger logger = LoggerFactory.getLogger(XJdbc.class);
    private static final String DB_URL;
    private static final String DB_USER;
    private static final String DB_PASS;
    private static final String DB_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    
    static {
        DB_URL = System.getenv().getOrDefault("DB_URL", 
                "jdbc:sqlserver://localhost;databaseName=PokemonSimulation;encrypt=true;trustServerCertificate=true");
        DB_USER = System.getenv().getOrDefault("DB_USER", "sa");
        DB_PASS = System.getenv().getOrDefault("DB_PASS", "lineth0334158668");
        
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            logger.error("JDBC Driver not found: ", e);
        }
    }
    
    public static int executeUpdate(String sql, Object... values) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                PreparedStatement stmt = prepare(conn, sql, values)) {
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Update failed: " + sql, e);
        }
    }
    
    public static ResultSet executeQuery(String sql, Object... values) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement stmt = prepare(conn, sql, values);
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Query failed: " + sql, e);
        }
    }
    
    public static <T> T getValue(String sql, Object... values) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                PreparedStatement stmt = prepare(conn, sql, values);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return (T) rs.getObject(1);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Get value failed: " + sql, e);
        }
    }
    
    private static PreparedStatement prepare(Connection conn, String sql, Object... values) throws SQLException {
        PreparedStatement stmt = sql.trim().startsWith("{") ? conn.prepareCall(sql) : conn.prepareStatement(sql);
        for (int i = 0; i < values.length; i++) {
            stmt.setObject(i + 1, values[i]);
        }
        return stmt;
    }
}
