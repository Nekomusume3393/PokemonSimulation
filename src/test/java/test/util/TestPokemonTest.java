/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author May5th
 */
public class TestPokemonTest {
    
    @Test
    void testAddition() {
        assertEquals(2 + 2, 4, "Basic math should work!");
    }
    
    @Test
    void testObject() {
        String name = "Charmander";
        assertNotNull(name);
        assertTrue(name.contains("Char"));
    }
    
}
