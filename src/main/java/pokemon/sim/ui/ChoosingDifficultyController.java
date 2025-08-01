/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pokemon.sim.ui;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author May5th
 */
public interface ChoosingDifficultyController {
    
    default void showJDialog(JDialog dialog) {
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    
    default void showChoosingPokemonJDialog(JFrame frame) {
        this.showJDialog(new ChoosingPokemonJDialog(frame, true));
    }
}
