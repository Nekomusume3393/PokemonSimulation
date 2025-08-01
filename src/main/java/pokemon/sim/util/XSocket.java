/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon.sim.util;

import java.io.*;
import java.net.Socket;

/**
 *
 * @author May5th
 */
public class XSocket {

    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public XSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    /**
     * Connects to a server at the specified host and port.
     */
    public XSocket(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    /**
     * Sends a text message to the server.
     */
    public void send(String message) throws IOException {
        writer.write(message);
        writer.newLine();
        writer.flush();
    }

    /**
     * Receives a line of text from the server.
     */
    public String receive() throws IOException {
        return reader.readLine();
    }

    /**
     * Sends a serializable object.
     */
    public void sendObject(Serializable obj) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(obj);
        out.flush();
    }

    /**
     * Receives a serializable object.
     */
    public Object receiveObject() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        return in.readObject();
    }

    /**
     * Closes the socket and associated streams.
     */
    public void close() {
        try {
            reader.close();
        } catch (IOException ignored) {
        }
        try {
            writer.close();
        } catch (IOException ignored) {
        }
        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }

    /**
     * Returns the underlying socket.
     */
    public Socket getSocket() {
        return socket;
    }
}
