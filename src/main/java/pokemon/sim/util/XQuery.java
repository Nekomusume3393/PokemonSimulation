/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon.sim.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author May5th
 */
public class XQuery {
    
    private static final Logger logger = LoggerFactory.getLogger(XQuery.class);
    
    public static <B> B getSingleBean(Class<B> beanClass, String sql, Object... values) {
        List<B> list = getBeanList(beanClass, sql, values);
        return list.isEmpty() ? null : list.get(0);
    }
    
    public static <B> List<B> getBeanList(Class<B> beanClass, String sql, Object... values) {
        List<B> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(
                System.getenv().getOrDefault("DB_URL", "jdbc:sqlserver://localhost;databaseName=PokemonSimulation;encrypt=true;trustServerCertificate=true"),
                System.getenv().getOrDefault("DB_USER", "sa"),
                System.getenv().getOrDefault("DB_PASS", "lineth0334158668")
            );
                PreparedStatement stmt = prepare(conn, sql, values);
                ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                list.add(readBean(rs, beanClass));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to map query result to bean: " + beanClass.getSimpleName(), e);
        }
        return list;
    }
    
    public static PreparedStatement prepare(Connection conn, String sql, Object... values) throws SQLException {
        PreparedStatement stmt = sql.trim().startsWith("{") ? conn.prepareCall(sql) : conn.prepareStatement(sql);
        for (int i = 0; i < values.length; i++) {
            stmt.setObject(i + 1, values[i]);
        }
        return stmt;
    }
    
    public static <B> B readBean(ResultSet rs, Class<B> beanClass) throws Exception {
        B bean = beanClass.getDeclaredConstructor().newInstance();
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();
        
        Method[] methods = beanClass.getDeclaredMethods();
        for (Method method : methods) {
            String name = method.getName();
            if (name.startsWith("set") && method.getParameterCount() == 1) {
                String column = name.substring(3).toLowerCase(Locale.ROOT);
                
                for (int i = 1; i <= columnCount; i++) {
                    String colName = meta.getColumnLabel(i).toLowerCase(Locale.ROOT);
                    if (colName.equals(column)) {
                        Object value = rs.getObject(i);
                        try {
                            method.invoke(bean, value);
                        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
                            logger.warn("[WARN] Failed to set property '{}' with value '{}'", name, value);
                        }
                        break;
                    }
                }
            }
        }
        return bean;
    }
}
