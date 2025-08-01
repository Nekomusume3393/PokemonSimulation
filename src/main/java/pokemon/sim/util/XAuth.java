/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon.sim.util;

import pokemon.sim.entity.User;

/**
 *
 * @author May5th
 */
public class XAuth {
    
    public static User user = createMockUser();
    
    private static User createMockUser() {
        User u = new User();
        u.setUsername("lineth3393");
        u.setPassword("lineth0334158668");
        u.setDisplayName("LINETH");
        u.setImage("lineth.png");
        u.setManager(true);
        u.setPokemonOwned(151);
        u.setWinCount(69);
        u.setLoseCount(69);
        return u;
    }
}
