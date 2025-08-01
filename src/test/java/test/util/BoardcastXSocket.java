/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.util;

import pokemon.sim.util.XSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import pokemon.sim.util.XDate;
import pokemon.sim.util.XSocket;

/**
 *
 * @author May5th
 */
public class BoardcastXSocket {
    
    private static final List<XSocket> clients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9999);
        System.out.println("Broadcast server running on port 9999...");

        while (true) {
            Socket socket = server.accept();
            System.out.println("New client connected: " + socket.getInetAddress());

            new Thread(() -> handleClient(socket)).start();
        }
    }

    private static void handleClient(Socket socket) {
        try {
            XSocket client = new XSocket(socket);
            clients.add(client);

            String name = client.receive();
            String time = XDate.format(LocalDateTime.now());
            broadcast(String.format("[System] (%s): '%s' has connected.", time, name));

            String msg;
            while ((msg = client.receive()) != null) {
                broadcast(msg);
            }

        } catch (IOException e) {
            System.out.println("A client disconnected.");
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}

            clients.removeIf(c -> c.getSocket().isClosed());
        }
    }

    private static void broadcast(String message) {
        for (XSocket client : clients) {
            try {
                client.send(message);
            } catch (IOException e) {
                System.out.println("Failed to send message to a client.");
            }
        }
    }
}
