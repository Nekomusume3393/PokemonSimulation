/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon.sim.socket;

import pokemon.sim.util.XSocket;
import pokemon.sim.util.XDate;
import pokemon.sim.util.XLogging;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

/**
 *
 * @author May5th
 */
public class MatchmakingServer {

    private static final Logger logger = XLogging.getLogger(MatchmakingServer.class);
    private static final int PORT = 9999;

    private static final List<PlayerConnection> queue = new CopyOnWriteArrayList<>();
    private static final Map<String, PlayerConnection> connections = new ConcurrentHashMap<>();
    private static final Map<String, PlayerConnection> matched = new ConcurrentHashMap<>();
    private static final Map<String, String[]> teamSelections = new ConcurrentHashMap<>();
    private static final Map<String, Boolean> teamConfirmed = new ConcurrentHashMap<>();
    private static final Map<String, String> battlePairs = new ConcurrentHashMap<>(); // Track who is battling whom

    public static void main(String[] args) throws IOException {
        logger.info("Server starting on port {}...", PORT);
        ServerSocket serverSocket = new ServerSocket(PORT);

        while (true) {
            XSocket socket = new XSocket(serverSocket.accept());
            new Thread(() -> handleClient(socket)).start();
        }
    }

    private static void handleClient(XSocket socket) {
        PlayerConnection currentPlayer = null;
        try {
            String input;
            while ((input = socket.receive()) != null) {
                logger.info("Received: {}", input);

                // Parse command and parts
                String[] parts = parseMessage(input);
                String command = parts[0];

                if ("JOIN_QUEUE".equals(command)) {
                    currentPlayer = handleJoin(socket, parts);
                } else if ("EXIT".equals(command)) {
                    if (currentPlayer != null) {
                        handleExit(currentPlayer.username);
                    }
                    break;
                } else if ("DECLINE".equals(command) && parts.length > 1) {
                    handleDecline(parts[1]);
                } else if ("ACCEPT".equals(command) && parts.length > 1) {
                    handleAccept(parts[1]);
                } else if ("REQUEST_QUEUE_LIST".equals(command)) {
                    handleList(socket);
                } else if ("CHAT".equals(command) && currentPlayer != null) {
                    // FIXED: Use currentPlayer directly instead of trying to parse username from message
                    handleChat(input, currentPlayer.username);
                } else if ("REQUEUE".equals(command) && parts.length > 1) {
                    handleRequeue(parts[1]);
                } else if ("TEAM_SELECTED".equals(command) && parts.length > 1) {
                    handleTeamSelection(parts);
                } else if (command.startsWith("BATTLE_")) {
                    handleBattleMessage(input, currentPlayer);
                }
            }
        } catch (IOException e) {
            logger.warn("Client disconnected unexpectedly: {}", e.getMessage());
        } finally {
            // Clean up if client disconnected without proper exit
            if (currentPlayer != null) {
                cleanupPlayer(currentPlayer.username);
            }
            try {
                socket.close();
            } catch (Exception e) {
                logger.warn("Error closing socket: {}", e.getMessage());
            }
        }
    }

    // FIXED: Better message parsing with proper trimming and validation
    private static String[] parseMessage(String message) {
        if (message == null) {
            return new String[]{""};
        }

        String[] parts = message.split("\\|", -1); // -1 to preserve empty strings
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
            // Additional cleanup for any hidden characters
            parts[i] = parts[i].replaceAll("^\\s+|\\s+$", "");
        }
        return parts;
    }

    private static PlayerConnection handleJoin(XSocket socket, String[] parts) throws IOException {
        if (parts.length < 2) {
            logger.warn("Invalid JOIN_QUEUE message format");
            return null;
        }

        String username = parts[1].trim();
        String displayName = parts.length > 2 && !parts[2].isEmpty() ? parts[2].trim() : username.toUpperCase();
        String image = parts.length > 3 && !parts[3].isEmpty() ? parts[3].trim() : "default.png";

        // Debug output to verify parsing
        System.out.println("DEBUG: Parsed JOIN_QUEUE - Username: '" + username + "', DisplayName: '" + displayName + "', Image: '" + image + "'");

        PlayerConnection player = new PlayerConnection(username, displayName, image, socket);
        queue.add(player);
        connections.put(username, player);

        broadcastSystemMessage("'" + displayName + "' has joined the queue.");

        tryMatch();
        return player;
    }

    private static void tryMatch() {
        if (queue.size() >= 2) {
            PlayerConnection p1 = queue.remove(0);
            PlayerConnection p2 = queue.remove(0);

            matched.put(p1.username, p2);
            matched.put(p2.username, p1);

            p1.accepted = false;
            p2.accepted = false;

            // FIXED: Debug output to verify what's being sent
            String matchMsg1 = "MATCH_FOUND|" + p2.username + "|" + p2.displayName + "|" + p2.image;
            String matchMsg2 = "MATCH_FOUND|" + p1.username + "|" + p1.displayName + "|" + p1.image;

            System.out.println("DEBUG: Sending to " + p1.username + ": " + matchMsg1);
            System.out.println("DEBUG: Sending to " + p2.username + ": " + matchMsg2);

            send(p1, matchMsg1);
            send(p2, matchMsg2);
        }
    }

    private static void handleExit(String username) {
        cleanupPlayer(username);
    }

    private static void handleDecline(String username) {
        PlayerConnection player = connections.get(username);
        PlayerConnection opponent = matched.remove(username);

        if (opponent != null) {
            matched.remove(opponent.username);
            send(opponent, "OPPONENT_LEFT");
            queue.add(opponent);
            broadcastSystemMessage("Match declined. '" + opponent.displayName + "' returned to queue.");
        }

        if (player != null) {
            queue.add(player);
            broadcastSystemMessage("'" + player.displayName + "' declined the match and returned to queue.");
        }

        tryMatch();
    }

    private static void handleAccept(String username) {
        PlayerConnection player = connections.get(username);
        if (player == null) {
            return;
        }
        player.accepted = true;

        PlayerConnection opponent = matched.get(username);
        if (opponent != null && opponent.accepted) {
            // Both players accepted - tell them to select teams
            send(player, "SELECT_TEAM");
            send(opponent, "SELECT_TEAM");
            
            // Clear previous team selections if any
            teamSelections.remove(player.username);
            teamSelections.remove(opponent.username);
            teamConfirmed.put(player.username, false);
            teamConfirmed.put(opponent.username, false);
        }
    }

    private static void handleTeamSelection(String[] parts) {
        if (parts.length < 2) {
            logger.warn("Invalid TEAM_SELECTED message format");
            return;
        }

        String username = parts[1];
        
        // Parse the team (Pokemon names separated by commas)
        String[] team = new String[6];
        int teamSize = 0;
        for (int i = 2; i < parts.length && teamSize < 6; i++) {
            if (!parts[i].isEmpty()) {
                team[teamSize++] = parts[i];
            }
        }

        // Store the team selection
        teamSelections.put(username, team);
        teamConfirmed.put(username, true);

        PlayerConnection player = connections.get(username);
        PlayerConnection opponent = matched.get(username);

        if (player != null && opponent != null) {
            // Notify opponent that this player has confirmed
            send(opponent, "OPPONENT_TEAM_CONFIRMED");

            // Check if both players have confirmed their teams
            if (Boolean.TRUE.equals(teamConfirmed.get(username)) && 
                Boolean.TRUE.equals(teamConfirmed.get(opponent.username))) {
                
                // Both teams are ready - send team data to both players
                String[] playerTeam = teamSelections.get(username);
                String[] opponentTeam = teamSelections.get(opponent.username);

                // Build team strings
                String playerTeamStr = buildTeamString(playerTeam);
                String opponentTeamStr = buildTeamString(opponentTeam);

                // Track battle pairing
                battlePairs.put(username, opponent.username);
                battlePairs.put(opponent.username, username);

                // Send START_DUEL with both teams
                send(player, "START_DUEL|" + username + "|" + opponent.username + 
                     "|YOUR_TEAM|" + playerTeamStr + 
                     "|OPPONENT_TEAM|" + opponentTeamStr);
                     
                send(opponent, "START_DUEL|" + opponent.username + "|" + username + 
                      "|YOUR_TEAM|" + opponentTeamStr + 
                      "|OPPONENT_TEAM|" + playerTeamStr);

                // Clean up matchmaking data but keep connections for battle
                matched.remove(player.username);
                matched.remove(opponent.username);
                teamSelections.remove(player.username);
                teamSelections.remove(opponent.username);
                teamConfirmed.remove(player.username);
                teamConfirmed.remove(opponent.username);
            } else {
                // Waiting for opponent
                send(player, "WAITING_FOR_OPPONENT");
            }
        }
    }

    private static String buildTeamString(String[] team) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < team.length; i++) {
            if (team[i] != null && !team[i].isEmpty()) {
                if (sb.length() > 0) sb.append(",");
                sb.append(team[i]);
            }
        }
        return sb.toString();
    }

    private static void handleList(XSocket socket) {
        StringBuilder sb = new StringBuilder("QUEUE_LIST");
        for (PlayerConnection pc : queue) {
            sb.append("|").append(pc.username).append("|").append(pc.displayName);
        }
        send(socket, sb.toString());
    }

    // FIXED: Simplified chat handling - sender is now passed as parameter
    private static void handleChat(String input, String senderUsername) {
        String content = input.substring(5); // Remove "CHAT|" prefix

        System.out.println("DEBUG: Chat from '" + senderUsername + "': " + content);

        // Broadcast the message to ALL connected players EXCEPT the sender
        for (PlayerConnection pc : connections.values()) {
            if (!pc.username.equals(senderUsername)) {
                System.out.println("DEBUG: Sending chat to: " + pc.username);
                send(pc, "CHAT|" + content);
            } else {
                System.out.println("DEBUG: Skipping sender: " + pc.username);
            }
        }

        logger.info("Broadcasting chat message from {}: {}", senderUsername, content);
    }

    private static void send(PlayerConnection pc, String msg) {
        try {
            System.out.println("DEBUG: Attempting to send to " + pc.username + ": " + msg);
            pc.socket.send(msg);
            System.out.println("DEBUG: Successfully sent to " + pc.username);
        } catch (IOException e) {
            System.err.println("ERROR: Failed to send to " + pc.username + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void send(XSocket socket, String msg) {
        try {
            socket.send(msg);
        } catch (IOException e) {
            logger.warn("Failed to send: {}", e.getMessage());
        }
    }

    private static void broadcastSystemMessage(String msg) {
        String formatted = "[System] (" + XDate.format(LocalDateTime.now()) + "): " + msg;
        System.out.println("DEBUG: Broadcasting system message: " + formatted);

        for (PlayerConnection pc : connections.values()) {
            System.out.println("DEBUG: Sending system message to " + pc.username + ": " + formatted);
            send(pc, "CHAT|" + formatted);
        }
    }

    private static void cleanupPlayer(String username) {
        PlayerConnection player = connections.remove(username);

        if (player != null) {
            queue.remove(player);

            PlayerConnection opponent = matched.remove(username);
            if (opponent != null) {
                matched.remove(opponent.username);
                send(opponent, "OPPONENT_LEFT");
                queue.add(opponent);
                broadcastSystemMessage("'" + player.displayName + "' has left the match.");
            } else {
                broadcastSystemMessage("'" + player.displayName + "' has left the queue.");
            }
            
            // Clean up team selection data
            teamSelections.remove(username);
            teamConfirmed.remove(username);
            
            // Clean up battle pairing
            String battleOpponent = battlePairs.remove(username);
            if (battleOpponent != null) {
                battlePairs.remove(battleOpponent);
                PlayerConnection battleOpp = connections.get(battleOpponent);
                if (battleOpp != null) {
                    send(battleOpp, "BATTLE_OPPONENT_DISCONNECTED");
                }
            }
        }
    }

    private static void handleRequeue(String username) {
        PlayerConnection player = connections.get(username);
        if (player != null && !queue.contains(player)) {
            queue.add(player);
            broadcastSystemMessage("'" + player.displayName + "' has rejoined the queue.");
            tryMatch();
        }
    }
    
    private static void handleBattleMessage(String message, PlayerConnection sender) {
        if (sender == null) return;
        
        // Find who the sender is battling with
        String opponentUsername = battlePairs.get(sender.username);
        if (opponentUsername != null) {
            PlayerConnection opponent = connections.get(opponentUsername);
            if (opponent != null) {
                // Forward the battle message to the opponent
                send(opponent, message);
                System.out.println("DEBUG: Forwarded battle message from " + sender.username + " to " + opponentUsername + ": " + message);
            }
        }
    }
}

class PlayerConnection {

    String username;
    String displayName;
    String image;
    XSocket socket;
    boolean accepted;

    public PlayerConnection(String username, String displayName, String image, XSocket socket) {
        this.username = username;
        this.displayName = displayName;
        this.image = image;
        this.socket = socket;
        this.accepted = false;
    }
}
