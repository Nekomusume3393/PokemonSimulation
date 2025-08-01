/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon.sim.util;

import org.slf4j.Logger;
import pokemon.sim.util.XLogging;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

/**
 *
 * @author May5th
 */
public class XSocketUIImpl extends XSocket {
    
    private static final Logger logger = XLogging.getLogger(XSocketUIImpl.class);

    private Thread listenerThread;
    private volatile boolean listening = false;
    private volatile Consumer<String> currentHandler = null;

    /**
     * Connects to a remote host and port with UI support.
     */
    public XSocketUIImpl(String host, int port) throws IOException {
        super(host, port);
    }

    /**
     * Wraps an existing socket (typically on server side).
     */
    public XSocketUIImpl(Socket socket) throws IOException {
        super(socket);
    }

    /**
     * Start listening for incoming text messages asynchronously.
     * UI-safe callback will be triggered via SwingUtilities.invokeLater.
     * @param onMessageReceived consumer to handle incoming message
     */
    public void listen(Consumer<String> onMessageReceived) {
        // Stop any existing listener
        stopListening();
        
        // Set the new handler
        currentHandler = onMessageReceived;
        listening = true;

        listenerThread = new Thread(() -> {
            try {
                String line;
                while (listening && (line = receive()) != null) {
                    final String msg = line;
                    if (currentHandler != null && listening) {
                        SwingUtilities.invokeLater(() -> {
                            if (currentHandler != null && listening) {
                                currentHandler.accept(msg);
                            }
                        });
                    }
                }
            } catch (IOException e) {
                if (listening) {
                    logger.warn("Socket closed or error during listening: " + e.getMessage());
                }
            } finally {
                listening = false;
            }
        });

        listenerThread.setDaemon(true);
        listenerThread.start();
    }

    /**
     * Stop listening for new messages (if active).
     */
    public void stopListening() {
        listening = false;
        currentHandler = null;
        if (listenerThread != null && listenerThread.isAlive()) {
            listenerThread.interrupt();
            try {
                listenerThread.join(100); // Wait up to 100ms for thread to stop
            } catch (InterruptedException e) {
                // Ignore
            }
        }
    }

    /**
     * Send a message safely from the UI thread (optionally with feedback).
     * @param message the message to send
     */
    public void sendFromUI(String message) {
        new Thread(() -> {
            try {
                send(message);
            } catch (IOException e) {
                logger.error("Failed to send from UI:", e);
            }
        }).start();
    }

    @Override
    public void close() {
        stopListening();
        super.close();
    }
}