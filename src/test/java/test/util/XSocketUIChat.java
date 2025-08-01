/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.util;

import pokemon.sim.util.XDate;
import pokemon.sim.util.XSocketUIImpl;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

/**
 *
 * @author May5th
 */
public class XSocketUIChat {
    
    private final String name;
    private final XSocketUIImpl socket;

    public XSocketUIChat(String name, XSocketUIImpl socket) {
        this.name = name;
        this.socket = socket;
        createUI();
    }

    private void createUI() {
        JFrame frame = new JFrame("Chat - " + name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        JPanel inputPanel = new JPanel(new BorderLayout());
        JTextField inputField = new JTextField();
        JButton sendButton = new JButton("Send");
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        socket.listen(msg -> chatArea.append(msg + "\n"));

        sendButton.addActionListener(e -> sendMessage(inputField, chatArea));
        inputField.addActionListener(e -> sendMessage(inputField, chatArea));

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void sendMessage(JTextField inputField, JTextArea chatArea) {
        String content = inputField.getText().trim();
        if (!content.isEmpty()) {
            String timestamp = XDate.format(LocalDateTime.now());
            String message = String.format("%s [%s]: %s", name, timestamp, content);
            socket.sendFromUI(message);
            inputField.setText("");
        }
    }
}
