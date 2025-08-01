/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.util;

import pokemon.sim.util.XSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author May5th
 */
public class ServerXSocket {
    
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9999);
        System.out.println("Server started on port 9999...");

        while (true) {
            Socket socket = server.accept();
            System.out.println("Client connected: " + socket.getInetAddress());

            new Thread(() -> {
                try {
                    XSocket xs = new XSocket(socket);
                    String input;
                    while ((input = xs.receive()) != null) {
                        System.out.println("Received: " + input);
                        xs.send(input);
                    }
                    xs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
