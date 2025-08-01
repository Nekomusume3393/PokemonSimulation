/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.util;

import pokemon.sim.util.XSocketUIImpl;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author May5th
 */
public class XSocketUILogin {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Socket Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 150);
            frame.setLayout(new BorderLayout());

            JPanel panel = new JPanel(new GridLayout(3, 1));
            JTextField nameField = new JTextField();
            JButton connectBtn = new JButton("Connect");

            panel.add(new JLabel("Enter your name:"));
            panel.add(nameField);
            panel.add(connectBtn);

            frame.add(panel, BorderLayout.CENTER);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            connectBtn.addActionListener(e -> {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Name cannot be empty.");
                    return;
                }

                try {
                    XSocketUIImpl socket = new XSocketUIImpl("localhost", 9999);
                    socket.sendFromUI(name);
                    new XSocketUIChat(name, socket);
                    frame.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Failed to connect: " + ex.getMessage());
                }
            });
        });
    }
}
