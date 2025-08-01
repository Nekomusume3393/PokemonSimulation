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
public interface WaitingController {
    
    default void showJDialog(JDialog dialog) {
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    default void showLoginJDialog(JFrame frame) {
        this.showJDialog(new LoginJDialog(frame, true));
    }

    default void showRegisterJDialog(JFrame frame) {
        this.showJDialog(new RegisterJDialog(frame, true));
    }
}
