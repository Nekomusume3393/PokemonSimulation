/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

class Pokemon {
    public String name;
    public int level;
    
    public Pokemon() {}
    public Pokemon(String name, int level) {
        this.name = name;
        this.level = level;
    }
}

/**
 *
 * @author LINETH
 */
public class TestJSON {
    
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Pokemon p = new Pokemon("Pikachu", 12);
        
        String json = mapper.writeValueAsString(p);
        System.out.println("JSON: " + json);
        
        Pokemon deserialized = mapper.readValue(json, Pokemon.class);
        System.out.println("Deserialized: " + deserialized.name + ", level " + deserialized.level);
    }
    
}
