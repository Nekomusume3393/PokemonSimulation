/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.util;

import pokemon.sim.util.XSocket;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author May5th
 */
public class ClientXSocket {
    
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            XSocket client = new XSocket("localhost", 9999);
            System.out.println("Connected to server!");

            while (true) {
                System.out.print("You: ");
                String msg = scanner.nextLine();
                if (msg.equalsIgnoreCase("exit")) break;

                client.send(msg);
                String response = client.receive();
                System.out.println("Server: " + response);
            }

            client.close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
