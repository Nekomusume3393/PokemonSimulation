/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author LINETH
 */
public class TestJDBC {
    
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=PokemonSimulation;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "lineth0334158668";
        
        try (Connection conn = DriverManager.getConnection(url, username, password)){
            System.out.println("Database connected successfully!");
        } catch (SQLException e) {
            System.out.println("Database failed to connect!");
            e.printStackTrace();
        }
    }
    
}
