/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pokemon.sim.ui;

import pokemon.sim.entity.*;
import pokemon.sim.util.*;
import com.fasterxml.jackson.databind.JsonNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 *
 * @author May5th
 */
public class MultiplayerPokeDuelJFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MultiplayerPokeDuelJFrame.class.getName());
    
    // Network
    private XSocketUIImpl socket;
    private String myUsername;
    private String opponentUsername;
    
    // Battle State
    private List<Pokemon> myTeam;
    private List<Pokemon> opponentTeam;
    private Pokemon myCurrentPokemon;
    private Pokemon opponentCurrentPokemon;
    private int myCurrentPokemonIndex = 0;
    private int opponentCurrentPokemonIndex = 0;
    
    // Pokemon details with moves
    private Map<Pokemon, PokemonDetail> myPokemonDetails;
    private Map<Pokemon, PokemonDetail> opponentPokemonDetails;
    
    // HP tracking
    private Map<Pokemon, Integer> currentHP;
    private Map<Pokemon, Integer> maxHP;
    
    // Utilities
    private XJson xJson;
    private XJsonBranch xJsonBranch;
    private XMove xMove;
    
    // UI State
    private boolean myTurn = false;
    private boolean waitingForAction = false;
    private boolean inMoveSelection = false;
    private boolean battleEnded = false;

    /**
     * Creates new form MultiplayerPokeDuelJFrame
     */
    public MultiplayerPokeDuelJFrame() {
        initComponents();
    }
    
    /**
     * Constructor for multiplayer battles
     */
    public MultiplayerPokeDuelJFrame(XSocketUIImpl socket, String myUsername, String opponentUsername, 
                                    String[] myTeamNames, String[] opponentTeamNames) {
        initComponents();
        this.socket = socket;
        this.myUsername = myUsername;
        this.opponentUsername = opponentUsername;
        
        System.out.println("DEBUG: MultiplayerPokeDuelJFrame constructor - myUsername: " + myUsername + ", opponentUsername: " + opponentUsername);
        
        initializeUtilities();
        initializeMultiplayerBattle(myTeamNames, opponentTeamNames);
        setupSocketListener();
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Window closing handler
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                handleExit();
            }
        });
    }
    
    private void initializeUtilities() {
        xJson = new XJson();
        xJsonBranch = new XJsonBranch(xJson);
        xMove = new XMove(xJson);
        
        currentHP = new HashMap<>();
        maxHP = new HashMap<>();
        myPokemonDetails = new HashMap<>();
        opponentPokemonDetails = new HashMap<>();
    }
    
    private void initializeMultiplayerBattle(String[] myTeamNames, String[] opponentTeamNames) {
        try {
            // Load both teams
            myTeam = loadTeam(myTeamNames, myPokemonDetails);
            opponentTeam = loadTeam(opponentTeamNames, opponentPokemonDetails);
            
            // Initialize HP tracking
            initializeHP();
            
            // Set first Pokemon
            myCurrentPokemon = myTeam.get(0);
            opponentCurrentPokemon = opponentTeam.get(0);
            
            // Update UI
            updateBattleUI();
            
            // Determine who goes first (alphabetically by username)
            myTurn = myUsername.compareTo(opponentUsername) < 0;
            
            if (myTurn) {
                waitingForAction = true;
                enableAllButtons();
                showBattleMessage("Battle starts! Your turn.", "Choose your action!");
            } else {
                waitingForAction = false;
                disableAllButtons();
                showBattleMessage("Battle starts! Waiting for opponent...", "");
            }
            
        } catch (Exception e) {
            logger.severe("Failed to initialize multiplayer battle: " + e.getMessage());
            e.printStackTrace();
            showBattleMessage("Error loading battle data!", "Please restart the game.");
        }
    }
    
    private void setupSocketListener() {
        // Important: Set up a new listener for battle messages
        // The previous listener from matchmaking frame should be stopped
        System.out.println("DEBUG: Setting up battle socket listener");
        
        socket.listen(message -> {
            System.out.println("DEBUG: Battle frame received: '" + message + "'");
            
            // Only handle battle-related messages
            if (message != null && (message.startsWith("BATTLE_") || message.startsWith("CHAT|"))) {
                System.out.println("DEBUG: Message is battle-related, processing...");
                handleBattleMessage(message);
            } else {
                System.out.println("DEBUG: Message ignored (not battle-related): " + message);
            }
        });
        
        System.out.println("DEBUG: Battle socket listener setup complete");
    }
    
    private void handleBattleMessage(String message) {
        if (message == null || message.trim().isEmpty() || battleEnded) {
            System.out.println("DEBUG: Message filtered out - null/empty/battleEnded");
            return;
        }
        
        System.out.println("DEBUG: handleBattleMessage processing: " + message);
        
        String[] parts = message.split("\\|", -1);
        String command = parts[0];
        
        System.out.println("DEBUG: Command identified: " + command);
        
        SwingUtilities.invokeLater(() -> {
            System.out.println("DEBUG: In SwingUtilities.invokeLater - processing command: " + command);
            
            switch (command) {
                case "BATTLE_MOVE":
                    System.out.println("DEBUG: Processing BATTLE_MOVE");
                    if (parts.length >= 4) {
                        System.out.println("DEBUG: Calling handleOpponentMove with: " + parts[1] + ", " + parts[2] + ", " + parts[3]);
                        handleOpponentMove(parts[1], parts[2], Integer.parseInt(parts[3]));
                    } else {
                        System.out.println("DEBUG: BATTLE_MOVE has insufficient parts: " + parts.length);
                    }
                    break;
                    
                case "BATTLE_SWITCH":
                    System.out.println("DEBUG: Processing BATTLE_SWITCH");
                    if (parts.length >= 2) {
                        handleOpponentSwitch(Integer.parseInt(parts[1]));
                    }
                    break;
                    
                case "BATTLE_FORFEIT":
                    System.out.println("DEBUG: Processing BATTLE_FORFEIT");
                    handleOpponentForfeit();
                    break;
                    
                case "BATTLE_CHAT":
                    System.out.println("DEBUG: Processing BATTLE_CHAT");
                    if (parts.length >= 2) {
                        showBattleMessage("Opponent: " + parts[1], "");
                    }
                    break;
                    
                case "BATTLE_OPPONENT_DISCONNECTED":
                    System.out.println("DEBUG: Processing BATTLE_OPPONENT_DISCONNECTED");
                    handleOpponentDisconnect();
                    break;
                    
                default:
                    System.out.println("DEBUG: Unknown command: " + command);
                    break;
            }
        });
    }
    
    private void handleOpponentMove(String moveName, String sender, int damage) {
        System.out.println("DEBUG: handleOpponentMove called - move: " + moveName + ", sender: " + sender + ", damage: " + damage);
        System.out.println("DEBUG: Expected opponent: " + opponentUsername);
        
        if (!sender.equals(opponentUsername)) {
            System.out.println("DEBUG: Sender mismatch! Expected: " + opponentUsername + ", Got: " + sender);
            return;
        }
        
        System.out.println("DEBUG: Processing opponent move...");
        
        // Apply damage to my Pokemon
        if (damage > 0) {
            int newHP = Math.max(0, currentHP.get(myCurrentPokemon) - damage);
            currentHP.put(myCurrentPokemon, newHP);
            updateHPBars();
            System.out.println("DEBUG: Applied " + damage + " damage. New HP: " + newHP);
        }
        
        showBattleMessage("Foe " + opponentCurrentPokemon.getName() + " used " + moveName + "!", 
                         damage > 0 ? "It dealt " + damage + " damage!" : "But it missed!");
        
        // Check if my Pokemon fainted
        if (currentHP.get(myCurrentPokemon) <= 0) {
            handlePokemonFaint(true);
        } else {
            // My turn now - enable buttons after a delay
            javax.swing.Timer timer = new javax.swing.Timer(2000, e -> {
                myTurn = true;
                waitingForAction = true;
                enableAllButtons();
                showBattleMessage("What will " + myCurrentPokemon.getName() + " do?", "");
                System.out.println("DEBUG: Enabled buttons for player's turn");
            });
            timer.setRepeats(false);
            timer.start();
        }
    }
    
    private void handleOpponentSwitch(int pokemonIndex) {
        if (pokemonIndex >= opponentTeam.size()) return;
        
        System.out.println("DEBUG: Opponent switching to Pokemon index " + pokemonIndex);
        
        opponentCurrentPokemon = opponentTeam.get(pokemonIndex);
        opponentCurrentPokemonIndex = pokemonIndex;
        
        System.out.println("DEBUG: Opponent switched to " + opponentCurrentPokemon.getName());
        
        showBattleMessage("Foe withdrew their Pokemon!", 
                         "Foe sent out " + opponentCurrentPokemon.getName() + "!");
        
        // Update the visual display for the opponent's new Pokemon
        updatePokemonDisplay(opponentCurrentPokemon, false);
        updateHPBars();
        
        // My turn after opponent switches - enable buttons after a delay
        javax.swing.Timer timer = new javax.swing.Timer(2000, e -> {
            myTurn = true;
            waitingForAction = true;
            enableAllButtons();
            showBattleMessage("What will " + myCurrentPokemon.getName() + " do?", "");
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private void handleOpponentForfeit() {
        battleEnded = true;
        showBattleMessage("Opponent forfeited!", "You win!");
        disableAllButtons();
    }
    
    private void handleOpponentDisconnect() {
        battleEnded = true;
        showBattleMessage("Opponent disconnected!", "You win by default!");
        disableAllButtons();
    }
    
    private List<Pokemon> loadTeam(String[] teamNames, Map<Pokemon, PokemonDetail> detailsMap) throws IOException {
        List<Pokemon> team = new ArrayList<>();
        
        for (String pokemonName : teamNames) {
            if (pokemonName == null) continue;
            
            String fileName = findPokemonFileName(pokemonName);
            if (fileName != null) {
                Pokemon pokemon = xJsonBranch.loadPokemonByInclude(fileName);
                team.add(pokemon);
                
                PokemonDetail detail = createPokemonDetail(pokemon.getName());
                detailsMap.put(pokemon, detail);
            }
        }
        
        return team;
    }
    
    private PokemonDetail createPokemonDetail(String pokemonName) {
        PokemonDetail detail = new PokemonDetail();
        
        // Hard-coded movesets for all Generation 1 Pokemon
        switch (pokemonName) {
            // Starters and evolutions
            case "Bulbasaur":
                detail.setMove1("VineWhip");
                detail.setMove2("RazorLeaf");
                detail.setMove3("SleepPowder");
                detail.setMove4("Tackle");
                break;
            case "Ivysaur":
                detail.setMove1("RazorLeaf");
                detail.setMove2("SleepPowder");
                detail.setMove3("PoisonPowder");
                detail.setMove4("TakeDown");
                break;
            case "Venusaur":
                detail.setMove1("SolarBeam");
                detail.setMove2("RazorLeaf");
                detail.setMove3("SleepPowder");
                detail.setMove4("Toxic");
                break;
            case "Charmander":
                detail.setMove1("Ember");
                detail.setMove2("Scratch");
                detail.setMove3("Leer");
                detail.setMove4("Smokescreen");
                break;
            case "Charmeleon":
                detail.setMove1("Flamethrower");
                detail.setMove2("Slash");
                detail.setMove3("Ember");
                detail.setMove4("Smokescreen");
                break;
            case "Charizard":
                detail.setMove1("Flamethrower");
                detail.setMove2("FireBlast");
                detail.setMove3("Earthquake");
                detail.setMove4("Slash");
                break;
            case "Squirtle":
                detail.setMove1("WaterGun");
                detail.setMove2("Bubble");
                detail.setMove3("Withdraw");
                detail.setMove4("Tackle");
                break;
            case "Wartortle":
                detail.setMove1("WaterGun");
                detail.setMove2("Bite");
                detail.setMove3("Withdraw");
                detail.setMove4("BubbleBeam");
                break;
            case "Blastoise":
                detail.setMove1("Surf");
                detail.setMove2("HydroPump");
                detail.setMove3("IceBeam");
                detail.setMove4("Earthquake");
                break;
                
            // Electric types
            case "Pikachu":
                detail.setMove1("Thunderbolt");
                detail.setMove2("Thunder");
                detail.setMove3("QuickAttack");
                detail.setMove4("ThunderWave");
                break;
            case "Raichu":
                detail.setMove1("Thunder");
                detail.setMove2("Thunderbolt");
                detail.setMove3("ThunderWave");
                detail.setMove4("HyperBeam");
                break;
                
            // Psychic types
            case "Alakazam":
                detail.setMove1("Psychic");
                detail.setMove2("Psybeam");
                detail.setMove3("Recover");
                detail.setMove4("ThunderWave");
                break;
            case "Mewtwo":
                detail.setMove1("Psychic");
                detail.setMove2("IceBeam");
                detail.setMove3("Thunderbolt");
                detail.setMove4("Recover");
                break;
            case "Mew":
                detail.setMove1("Psychic");
                detail.setMove2("Thunderbolt");
                detail.setMove3("IceBeam");
                detail.setMove4("SoftBoiled");
                break;
                
            // Ghost types
            case "Gengar":
                detail.setMove1("Psychic");
                detail.setMove2("Thunderbolt");
                detail.setMove3("Hypnosis");
                detail.setMove4("DreamEater");
                break;
                
            // Dragon types
            case "Dragonite":
                detail.setMove1("HyperBeam");
                detail.setMove2("Thunder");
                detail.setMove3("Blizzard");
                detail.setMove4("FireBlast");
                break;
                
            // Normal types
            case "Snorlax":
                detail.setMove1("BodySlam");
                detail.setMove2("HyperBeam");
                detail.setMove3("Earthquake");
                detail.setMove4("Rest");
                break;
            case "Tauros":
                detail.setMove1("BodySlam");
                detail.setMove2("HyperBeam");
                detail.setMove3("Earthquake");
                detail.setMove4("Blizzard");
                break;
            case "Chansey":
                detail.setMove1("SoftBoiled");
                detail.setMove2("IceBeam");
                detail.setMove3("Thunderbolt");
                detail.setMove4("ThunderWave");
                break;
                
            // Water types
            case "Lapras":
                detail.setMove1("IceBeam");
                detail.setMove2("Blizzard");
                detail.setMove3("Surf");
                detail.setMove4("Psychic");
                break;
            case "Gyarados":
                detail.setMove1("HydroPump");
                detail.setMove2("HyperBeam");
                detail.setMove3("DragonRage");
                detail.setMove4("Thunderbolt");
                break;
            case "Starmie":
                detail.setMove1("Surf");
                detail.setMove2("Psychic");
                detail.setMove3("IceBeam");
                detail.setMove4("Thunderbolt");
                break;
            case "Vaporeon":
                detail.setMove1("Surf");
                detail.setMove2("IceBeam");
                detail.setMove3("AcidArmor");
                detail.setMove4("Rest");
                break;
                
            // Fire types
            case "Arcanine":
                detail.setMove1("FireBlast");
                detail.setMove2("BodySlam");
                detail.setMove3("HyperBeam");
                detail.setMove4("Reflect");
                break;
            case "Flareon":
                detail.setMove1("FireBlast");
                detail.setMove2("BodySlam");
                detail.setMove3("HyperBeam");
                detail.setMove4("QuickAttack");
                break;
            case "Magmar":
                detail.setMove1("FireBlast");
                detail.setMove2("Psychic");
                detail.setMove3("HyperBeam");
                detail.setMove4("ConfuseRay");
                break;
                
            // Electric types (more)
            case "Zapdos":
                detail.setMove1("Thunder");
                detail.setMove2("DrillPeck");
                detail.setMove3("ThunderWave");
                detail.setMove4("Agility");
                break;
            case "Jolteon":
                detail.setMove1("Thunderbolt");
                detail.setMove2("ThunderWave");
                detail.setMove3("PinMissile");
                detail.setMove4("DoubleKick");
                break;
            case "Electabuzz":
                detail.setMove1("Thunderbolt");
                detail.setMove2("Psychic");
                detail.setMove3("ThunderWave");
                detail.setMove4("HyperBeam");
                break;
                
            // Ice types
            case "Articuno":
                detail.setMove1("IceBeam");
                detail.setMove2("Blizzard");
                detail.setMove3("HyperBeam");
                detail.setMove4("Agility");
                break;
            case "Dewgong":
                detail.setMove1("IceBeam");
                detail.setMove2("Surf");
                detail.setMove3("BodySlam");
                detail.setMove4("Rest");
                break;
            case "Cloyster":
                detail.setMove1("IceBeam");
                detail.setMove2("Surf");
                detail.setMove3("HyperBeam");
                detail.setMove4("Explosion");
                break;
                
            // Fighting types
            case "Machamp":
                detail.setMove1("Submission");
                detail.setMove2("Earthquake");
                detail.setMove3("HyperBeam");
                detail.setMove4("BodySlam");
                break;
            case "Hitmonlee":
                detail.setMove1("HighJumpKick");
                detail.setMove2("BodySlam");
                detail.setMove3("MegaKick");
                detail.setMove4("Meditate");
                break;
            case "Hitmonchan":
                detail.setMove1("Submission");
                detail.setMove2("BodySlam");
                detail.setMove3("IcePunch");
                detail.setMove4("ThunderPunch");
                break;
                
            // Grass types
            case "Vileplume":
                detail.setMove1("SleepPowder");
                detail.setMove2("PetalDance");
                detail.setMove3("Acid");
                detail.setMove4("StunSpore");
                break;
            case "Victreebel":
                detail.setMove1("RazorLeaf");
                detail.setMove2("SleepPowder");
                detail.setMove3("Wrap");
                detail.setMove4("StunSpore");
                break;
            case "Exeggutor":
                detail.setMove1("Psychic");
                detail.setMove2("SleepPowder");
                detail.setMove3("EggBomb");
                detail.setMove4("StunSpore");
                break;
                
            // Rock/Ground types
            case "Golem":
                detail.setMove1("Earthquake");
                detail.setMove2("RockSlide");
                detail.setMove3("BodySlam");
                detail.setMove4("Explosion");
                break;
            case "Rhydon":
                detail.setMove1("Earthquake");
                detail.setMove2("RockSlide");
                detail.setMove3("BodySlam");
                detail.setMove4("Substitute");
                break;
            case "Onix":
                detail.setMove1("Earthquake");
                detail.setMove2("RockSlide");
                detail.setMove3("Bind");
                detail.setMove4("BodySlam");
                break;
                
            // Flying types
            case "Pidgeot":
                detail.setMove1("SkyAttack");
                detail.setMove2("HyperBeam");
                detail.setMove3("MirrorMove");
                detail.setMove4("Substitute");
                break;
            case "Fearow":
                detail.setMove1("DrillPeck");
                detail.setMove2("HyperBeam");
                detail.setMove3("MirrorMove");
                detail.setMove4("Substitute");
                break;
            case "Dodrio":
                detail.setMove1("DrillPeck");
                detail.setMove2("BodySlam");
                detail.setMove3("HyperBeam");
                detail.setMove4("Substitute");
                break;
                
            // Poison types
            case "Nidoking":
                detail.setMove1("Earthquake");
                detail.setMove2("Blizzard");
                detail.setMove3("Thunder");
                detail.setMove4("BodySlam");
                break;
            case "Nidoqueen":
                detail.setMove1("Earthquake");
                detail.setMove2("Blizzard");
                detail.setMove3("Thunder");
                detail.setMove4("BodySlam");
                break;
            case "Weezing":
                detail.setMove1("Sludge");
                detail.setMove2("Thunder");
                detail.setMove3("FireBlast");
                detail.setMove4("Explosion");
                break;
                
            // Bug types
            case "Scyther":
                detail.setMove1("Slash");
                detail.setMove2("SwordsDance");
                detail.setMove3("HyperBeam");
                detail.setMove4("Substitute");
                break;
            case "Pinsir":
                detail.setMove1("Submission");
                detail.setMove2("HyperBeam");
                detail.setMove3("SwordsDance");
                detail.setMove4("BodySlam");
                break;
                
            // Default moveset for any Pokemon not specifically listed
            default:
                detail.setMove1("Tackle");
                detail.setMove2("QuickAttack");
                detail.setMove3("BodySlam");
                detail.setMove4("HyperBeam");
                break;
        }
        
        return detail;
    }
    
    private String findPokemonFileName(String pokemonName) {
        Map<String, String> pokemonFiles = new HashMap<>();
        
        // Generation 1 Pokemon mappings
        pokemonFiles.put("Bulbasaur", "001.json");
        pokemonFiles.put("Ivysaur", "002.json");
        pokemonFiles.put("Venusaur", "003.json");
        pokemonFiles.put("Charmander", "004.json");
        pokemonFiles.put("Charmeleon", "005.json");
        pokemonFiles.put("Charizard", "006.json");
        pokemonFiles.put("Squirtle", "007.json");
        pokemonFiles.put("Wartortle", "008.json");
        pokemonFiles.put("Blastoise", "009.json");
        pokemonFiles.put("Caterpie", "010.json");
        pokemonFiles.put("Metapod", "011.json");
        pokemonFiles.put("Butterfree", "012.json");
        pokemonFiles.put("Weedle", "013.json");
        pokemonFiles.put("Kakuna", "014.json");
        pokemonFiles.put("Beedrill", "015.json");
        pokemonFiles.put("Pidgey", "016.json");
        pokemonFiles.put("Pidgeotto", "017.json");
        pokemonFiles.put("Pidgeot", "018.json");
        pokemonFiles.put("Rattata", "019.json");
        pokemonFiles.put("Raticate", "020.json");
        pokemonFiles.put("Spearow", "021.json");
        pokemonFiles.put("Fearow", "022.json");
        pokemonFiles.put("Ekans", "023.json");
        pokemonFiles.put("Arbok", "024.json");
        pokemonFiles.put("Pikachu", "025.json");
        pokemonFiles.put("Raichu", "026.json");
        pokemonFiles.put("Sandshrew", "027.json");
        pokemonFiles.put("Sandslash", "028.json");
        pokemonFiles.put("Nidoran♀", "029.json");
        pokemonFiles.put("Nidorina", "030.json");
        pokemonFiles.put("Nidoqueen", "031.json");
        pokemonFiles.put("Nidoran♂", "032.json");
        pokemonFiles.put("Nidorino", "033.json");
        pokemonFiles.put("Nidoking", "034.json");
        pokemonFiles.put("Clefairy", "035.json");
        pokemonFiles.put("Clefable", "036.json");
        pokemonFiles.put("Vulpix", "037.json");
        pokemonFiles.put("Ninetales", "038.json");
        pokemonFiles.put("Jigglypuff", "039.json");
        pokemonFiles.put("Wigglytuff", "040.json");
        pokemonFiles.put("Zubat", "041.json");
        pokemonFiles.put("Golbat", "042.json");
        pokemonFiles.put("Oddish", "043.json");
        pokemonFiles.put("Gloom", "044.json");
        pokemonFiles.put("Vileplume", "045.json");
        pokemonFiles.put("Paras", "046.json");
        pokemonFiles.put("Parasect", "047.json");
        pokemonFiles.put("Venonat", "048.json");
        pokemonFiles.put("Venomoth", "049.json");
        pokemonFiles.put("Diglett", "050.json");
        pokemonFiles.put("Dugtrio", "051.json");
        pokemonFiles.put("Meowth", "052.json");
        pokemonFiles.put("Persian", "053.json");
        pokemonFiles.put("Psyduck", "054.json");
        pokemonFiles.put("Golduck", "055.json");
        pokemonFiles.put("Mankey", "056.json");
        pokemonFiles.put("Primeape", "057.json");
        pokemonFiles.put("Growlithe", "058.json");
        pokemonFiles.put("Arcanine", "059.json");
        pokemonFiles.put("Poliwag", "060.json");
        pokemonFiles.put("Poliwhirl", "061.json");
        pokemonFiles.put("Poliwrath", "062.json");
        pokemonFiles.put("Abra", "063.json");
        pokemonFiles.put("Kadabra", "064.json");
        pokemonFiles.put("Alakazam", "065.json");
        pokemonFiles.put("Machop", "066.json");
        pokemonFiles.put("Machoke", "067.json");
        pokemonFiles.put("Machamp", "068.json");
        pokemonFiles.put("Bellsprout", "069.json");
        pokemonFiles.put("Weepinbell", "070.json");
        pokemonFiles.put("Victreebel", "071.json");
        pokemonFiles.put("Tentacool", "072.json");
        pokemonFiles.put("Tentacruel", "073.json");
        pokemonFiles.put("Geodude", "074.json");
        pokemonFiles.put("Graveler", "075.json");
        pokemonFiles.put("Golem", "076.json");
        pokemonFiles.put("Ponyta", "077.json");
        pokemonFiles.put("Rapidash", "078.json");
        pokemonFiles.put("Slowpoke", "079.json");
        pokemonFiles.put("Slowbro", "080.json");
        pokemonFiles.put("Magnemite", "081.json");
        pokemonFiles.put("Magneton", "082.json");
        pokemonFiles.put("Farfetch'd", "083.json");
        pokemonFiles.put("Doduo", "084.json");
        pokemonFiles.put("Dodrio", "085.json");
        pokemonFiles.put("Seel", "086.json");
        pokemonFiles.put("Dewgong", "087.json");
        pokemonFiles.put("Grimer", "088.json");
        pokemonFiles.put("Muk", "089.json");
        pokemonFiles.put("Shellder", "090.json");
        pokemonFiles.put("Cloyster", "091.json");
        pokemonFiles.put("Gastly", "092.json");
        pokemonFiles.put("Haunter", "093.json");
        pokemonFiles.put("Gengar", "094.json");
        pokemonFiles.put("Onix", "095.json");
        pokemonFiles.put("Drowzee", "096.json");
        pokemonFiles.put("Hypno", "097.json");
        pokemonFiles.put("Krabby", "098.json");
        pokemonFiles.put("Kingler", "099.json");
        pokemonFiles.put("Voltorb", "100.json");
        pokemonFiles.put("Electrode", "101.json");
        pokemonFiles.put("Exeggcute", "102.json");
        pokemonFiles.put("Exeggutor", "103.json");
        pokemonFiles.put("Cubone", "104.json");
        pokemonFiles.put("Marowak", "105.json");
        pokemonFiles.put("Hitmonlee", "106.json");
        pokemonFiles.put("Hitmonchan", "107.json");
        pokemonFiles.put("Lickitung", "108.json");
        pokemonFiles.put("Koffing", "109.json");
        pokemonFiles.put("Weezing", "110.json");
        pokemonFiles.put("Rhyhorn", "111.json");
        pokemonFiles.put("Rhydon", "112.json");
        pokemonFiles.put("Chansey", "113.json");
        pokemonFiles.put("Tangela", "114.json");
        pokemonFiles.put("Kangaskhan", "115.json");
        pokemonFiles.put("Horsea", "116.json");
        pokemonFiles.put("Seadra", "117.json");
        pokemonFiles.put("Goldeen", "118.json");
        pokemonFiles.put("Seaking", "119.json");
        pokemonFiles.put("Staryu", "120.json");
        pokemonFiles.put("Starmie", "121.json");
        pokemonFiles.put("Mr. Mime", "122.json");
        pokemonFiles.put("Scyther", "123.json");
        pokemonFiles.put("Jynx", "124.json");
        pokemonFiles.put("Electabuzz", "125.json");
        pokemonFiles.put("Magmar", "126.json");
        pokemonFiles.put("Pinsir", "127.json");
        pokemonFiles.put("Tauros", "128.json");
        pokemonFiles.put("Magikarp", "129.json");
        pokemonFiles.put("Gyarados", "130.json");
        pokemonFiles.put("Lapras", "131.json");
        pokemonFiles.put("Ditto", "132.json");
        pokemonFiles.put("Eevee", "133.json");
        pokemonFiles.put("Vaporeon", "134.json");
        pokemonFiles.put("Jolteon", "135.json");
        pokemonFiles.put("Flareon", "136.json");
        pokemonFiles.put("Porygon", "137.json");
        pokemonFiles.put("Omanyte", "138.json");
        pokemonFiles.put("Omastar", "139.json");
        pokemonFiles.put("Kabuto", "140.json");
        pokemonFiles.put("Kabutops", "141.json");
        pokemonFiles.put("Aerodactyl", "142.json");
        pokemonFiles.put("Snorlax", "143.json");
        pokemonFiles.put("Articuno", "144.json");
        pokemonFiles.put("Zapdos", "145.json");
        pokemonFiles.put("Moltres", "146.json");
        pokemonFiles.put("Dratini", "147.json");
        pokemonFiles.put("Dragonair", "148.json");
        pokemonFiles.put("Dragonite", "149.json");
        pokemonFiles.put("Mewtwo", "150.json");
        pokemonFiles.put("Mew", "151.json");
        
        return pokemonFiles.get(pokemonName);
    }
    
    private void initializeHP() {
        for (Pokemon pokemon : myTeam) {
            int hp = calculateMaxHP(pokemon);
            maxHP.put(pokemon, hp);
            currentHP.put(pokemon, hp);
        }
        
        for (Pokemon pokemon : opponentTeam) {
            int hp = calculateMaxHP(pokemon);
            maxHP.put(pokemon, hp);
            currentHP.put(pokemon, hp);
        }
    }
    
    private int calculateMaxHP(Pokemon pokemon) {
        if (pokemon.getStats() != null && !pokemon.getStats().isEmpty()) {
            int baseHP = pokemon.getStats().get(0).getHp();
            int level = 50;
            return ((2 * baseHP + 31) * level / 100) + level + 10;
        }
        return 100;
    }
    
    private void updateBattleUI() {
        updatePokemonDisplay(myCurrentPokemon, true);
        updatePokemonDisplay(opponentCurrentPokemon, false);
        updateHPBars();
    }
    
    private void updatePokemonDisplay(Pokemon pokemon, boolean isPlayer) {
        if (pokemon == null) return;
        
        System.out.println("DEBUG: Updating display for " + (isPlayer ? "player" : "opponent") + " Pokemon: " + pokemon.getName());
        
        JLabel nameLabel = isPlayer ? lblYourPokemonName : lblOpponentPokemonName;
        JLabel levelLabel = isPlayer ? lblYourPokemonLevel : lblOpponentPokemonLevel;
        JLabel imageLabel = isPlayer ? lblYourPokemonImage : lblOpponentPokemonImage;
        
        nameLabel.setText(pokemon.getName().toUpperCase());
        levelLabel.setText("LV 50");
        
        try {
            BufferedImage pokemonImage = isPlayer ? 
                XImage.getPokemonBackImage(xJson.getObjectMapper().valueToTree(pokemon)) :
                XImage.getPokemonFrontImage(xJson.getObjectMapper().valueToTree(pokemon));
                
            if (pokemonImage != null) {
                Image scaledImage = XImage.resize(pokemonImage, 300, 300);
                imageLabel.setIcon(new ImageIcon(scaledImage));
                System.out.println("DEBUG: Successfully updated image for " + pokemon.getName());
            } else {
                System.out.println("DEBUG: Failed to load image for " + pokemon.getName());
            }
        } catch (Exception e) {
            logger.warning("Failed to load Pokemon image: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void updateHPBars() {
        // Update my HP bar
        if (myCurrentPokemon != null) {
            int current = currentHP.getOrDefault(myCurrentPokemon, 0);
            int max = maxHP.getOrDefault(myCurrentPokemon, 1);
            int percentage = (current * 100) / max;
            pgbYourPokemonHP.setValue(percentage);
            pgbYourPokemonHP.setString(current + "/" + max);
            pgbYourPokemonHP.setStringPainted(true);
            
            if (percentage > 50) {
                pgbYourPokemonHP.setForeground(new Color(51, 255, 0));
            } else if (percentage > 20) {
                pgbYourPokemonHP.setForeground(Color.YELLOW);
            } else {
                pgbYourPokemonHP.setForeground(Color.RED);
            }
        }
        
        // Update opponent HP bar
        if (opponentCurrentPokemon != null) {
            int current = currentHP.getOrDefault(opponentCurrentPokemon, 0);
            int max = maxHP.getOrDefault(opponentCurrentPokemon, 1);
            int percentage = (current * 100) / max;
            pgbOpponentPokemonHP.setValue(percentage);
            pgbOpponentPokemonHP.setString(current + "/" + max);
            pgbOpponentPokemonHP.setStringPainted(true);
            
            if (percentage > 50) {
                pgbOpponentPokemonHP.setForeground(new Color(51, 255, 0));
            } else if (percentage > 20) {
                pgbOpponentPokemonHP.setForeground(Color.YELLOW);
            } else {
                pgbOpponentPokemonHP.setForeground(Color.RED);
            }
        }
    }
    
    private void showBattleMessage(String line1, String line2) {
        lblLine3.setText(line1);
        lblLine2.setText(line2);
    }
    
    private void showMoveSelection() {
        inMoveSelection = true;
        showBattleMessage("Choose a move:", "");
        
        PokemonDetail detail = myPokemonDetails.get(myCurrentPokemon);
        if (detail != null) {
            btnFight.setText(detail.getMove1() != null ? detail.getMove1() : "---");
            btnBag.setText(detail.getMove2() != null ? detail.getMove2() : "---");
            btnPokemon.setText(detail.getMove3() != null ? detail.getMove3() : "---");
            btnRun.setText(detail.getMove4() != null ? detail.getMove4() : "---");
        }
    }
    
    private void resetButtons() {
        btnFight.setText("FIGHT");
        btnBag.setText("BAG");
        btnPokemon.setText("POKÉMON");
        btnRun.setText("RUN");
        inMoveSelection = false;
    }
    
    private void executeMyMove(String moveName) {
        if (moveName == null || moveName.equals("---") || !myTurn) {
            showBattleMessage("No move in that slot!", "Choose another move.");
            return;
        }
        
        resetButtons();
        waitingForAction = false;
        myTurn = false;
        disableAllButtons();
        
        // Execute move locally
        XMove.MoveResult result = xMove.executeMove(myCurrentPokemon, opponentCurrentPokemon, moveName + ".json");
        
        // Apply damage locally
        if (result.isHit() && result.getDamage() > 0) {
            int newHP = Math.max(0, currentHP.get(opponentCurrentPokemon) - result.getDamage());
            currentHP.put(opponentCurrentPokemon, newHP);
            updateHPBars();
        }
        
        showBattleMessage(myCurrentPokemon.getName() + " used " + moveName + "!", result.getMessage());
        
        // Send move to opponent with all necessary information
        String battleMessage = "BATTLE_MOVE|" + moveName + "|" + myUsername + "|" + result.getDamage();
        socket.sendFromUI(battleMessage);
        System.out.println("DEBUG: Sent battle move: " + battleMessage);
        
        // Check if opponent Pokemon fainted
        if (currentHP.get(opponentCurrentPokemon) <= 0) {
            handlePokemonFaint(false);
        } else {
            // Wait for opponent's turn
            javax.swing.Timer timer = new javax.swing.Timer(2000, e -> {
                showBattleMessage("Waiting for opponent's move...", "");
            });
            timer.setRepeats(false);
            timer.start();
        }
    }
    
    private void handlePokemonFaint(boolean isMyPokemon) {
        Pokemon faintedPokemon = isMyPokemon ? myCurrentPokemon : opponentCurrentPokemon;
        showBattleMessage(faintedPokemon.getName() + " fainted!", "");
        
        javax.swing.Timer timer = new javax.swing.Timer(2000, e -> {
            if (isMyPokemon) {
                myCurrentPokemonIndex++;
                if (myCurrentPokemonIndex < myTeam.size()) {
                    myCurrentPokemon = myTeam.get(myCurrentPokemonIndex);
                    showBattleMessage("Go! " + myCurrentPokemon.getName() + "!", "");
                    updateBattleUI();
                    // Keep current turn state
                } else {
                    // I lost
                    battleEnded = true;
                    showBattleMessage("You lost the battle!", "");
                    disableAllButtons();
                }
            } else {
                // Opponent Pokemon fainted
                opponentCurrentPokemonIndex++;
                if (opponentCurrentPokemonIndex < opponentTeam.size()) {
                    opponentCurrentPokemon = opponentTeam.get(opponentCurrentPokemonIndex);
                    showBattleMessage("Foe sent out " + opponentCurrentPokemon.getName() + "!", "");
                    updateBattleUI();
                    
                    // My turn continues
                    myTurn = true;
                    waitingForAction = true;
                    enableAllButtons();
                } else {
                    // I won
                    battleEnded = true;
                    showBattleMessage("You defeated your opponent!", "You win!");
                    disableAllButtons();
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private void showPokemonSelection() {
        // Create and show Pokemon selection dialog
        ChoosingPokemonInBattleJDialog dialog = new ChoosingPokemonInBattleJDialog(
            this,
            true,
            myTeam,
            myCurrentPokemon,
            currentHP,
            maxHP
        );
        
        dialog.setVisible(true);
        
        if (dialog.isSelectionMade()) {
            Pokemon selectedPokemon = dialog.getSelectedPokemon();
            int selectedIndex = dialog.getSelectedIndex();
            
            if (selectedPokemon != null && selectedPokemon != myCurrentPokemon) {
                switchPokemon(selectedIndex);
            }
        } else {
            resetButtons();
            showBattleMessage("What will " + myCurrentPokemon.getName() + " do?", "");
        }
    }
    
    private void switchPokemon(int index) {
        if (index >= myTeam.size() || !myTurn) return;
        
        Pokemon newPokemon = myTeam.get(index);
        if (currentHP.get(newPokemon) <= 0) {
            showBattleMessage(newPokemon.getName() + " has fainted!", "Choose another Pokemon.");
            return;
        }
        
        myCurrentPokemon = newPokemon;
        myCurrentPokemonIndex = index;
        
        updateBattleUI();
        showBattleMessage("Go! " + myCurrentPokemon.getName() + "!", "");
        
        // Send switch to opponent with Pokemon index
        String switchMessage = "BATTLE_SWITCH|" + index;
        socket.sendFromUI(switchMessage);
        System.out.println("DEBUG: Sent switch message: " + switchMessage);
        
        // End my turn
        myTurn = false;
        waitingForAction = false;
        disableAllButtons();
        
        javax.swing.Timer timer = new javax.swing.Timer(2000, e -> {
            showBattleMessage("Waiting for opponent's move...", "");
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private void disableAllButtons() {
        btnFight.setEnabled(false);
        btnBag.setEnabled(false);
        btnPokemon.setEnabled(false);
        btnRun.setEnabled(false);
    }
    
    private void enableAllButtons() {
        btnFight.setEnabled(true);
        btnBag.setEnabled(true);
        btnPokemon.setEnabled(true);
        btnRun.setEnabled(true);
    }
    
    private void handleExit() {
        if (!battleEnded) {
            socket.sendFromUI("BATTLE_FORFEIT");
        }
        dispose();
        System.exit(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBattle = new javax.swing.JPanel();
        pnlYourPokemonStats = new javax.swing.JPanel();
        pgbYourPokemonHP = new javax.swing.JProgressBar();
        lblYourPokemonName = new javax.swing.JLabel();
        lblYourPokemonLevel = new javax.swing.JLabel();
        pnlOpponentPokemonStats = new javax.swing.JPanel();
        pgbOpponentPokemonHP = new javax.swing.JProgressBar();
        lblOpponentPokemonName = new javax.swing.JLabel();
        lblOpponentPokemonLevel = new javax.swing.JLabel();
        lblYourPokemonImage = new javax.swing.JLabel();
        lblOpponentPokemonImage = new javax.swing.JLabel();
        lblBackground = new javax.swing.JLabel();
        pnlLeft = new javax.swing.JPanel();
        pnlText = new javax.swing.JPanel();
        lblLine3 = new javax.swing.JLabel();
        lblLine2 = new javax.swing.JLabel();
        btnFight = new javax.swing.JButton();
        btnBag = new javax.swing.JButton();
        btnPokemon = new javax.swing.JButton();
        btnRun = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlBattle.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlYourPokemonStats.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblYourPokemonName.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblYourPokemonName.setText("YOUR POKÉMON");

        lblYourPokemonLevel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblYourPokemonLevel.setText("LV XX");

        javax.swing.GroupLayout pnlYourPokemonStatsLayout = new javax.swing.GroupLayout(pnlYourPokemonStats);
        pnlYourPokemonStats.setLayout(pnlYourPokemonStatsLayout);
        pnlYourPokemonStatsLayout.setHorizontalGroup(
            pnlYourPokemonStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlYourPokemonStatsLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pnlYourPokemonStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pgbYourPokemonHP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlYourPokemonStatsLayout.createSequentialGroup()
                        .addComponent(lblYourPokemonName, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblYourPokemonLevel)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        pnlYourPokemonStatsLayout.setVerticalGroup(
            pnlYourPokemonStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlYourPokemonStatsLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(pnlYourPokemonStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblYourPokemonName)
                    .addComponent(lblYourPokemonLevel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(pgbYourPokemonHP, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pnlBattle.add(pnlYourPokemonStats, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 540, 610, 150));

        pnlOpponentPokemonStats.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblOpponentPokemonName.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblOpponentPokemonName.setText("OPPONENT POKÉMON");

        lblOpponentPokemonLevel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblOpponentPokemonLevel.setText("LV XX");

        javax.swing.GroupLayout pnlOpponentPokemonStatsLayout = new javax.swing.GroupLayout(pnlOpponentPokemonStats);
        pnlOpponentPokemonStats.setLayout(pnlOpponentPokemonStatsLayout);
        pnlOpponentPokemonStatsLayout.setHorizontalGroup(
            pnlOpponentPokemonStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOpponentPokemonStatsLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pnlOpponentPokemonStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pgbOpponentPokemonHP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlOpponentPokemonStatsLayout.createSequentialGroup()
                        .addComponent(lblOpponentPokemonName, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOpponentPokemonLevel)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        pnlOpponentPokemonStatsLayout.setVerticalGroup(
            pnlOpponentPokemonStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlOpponentPokemonStatsLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(pnlOpponentPokemonStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOpponentPokemonName)
                    .addComponent(lblOpponentPokemonLevel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(pgbOpponentPokemonHP, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pnlBattle.add(pnlOpponentPokemonStats, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        lblYourPokemonImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblYourPokemonImage.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        pnlBattle.add(lblYourPokemonImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 380, 340, 330));

        lblOpponentPokemonImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOpponentPokemonImage.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        pnlBattle.add(lblOpponentPokemonImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 110, 340, 330));

        lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/new-battle-background.png"))); // NOI18N
        pnlBattle.add(lblBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, -1));

        pnlLeft.setBackground(new java.awt.Color(153, 102, 0));
        pnlLeft.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 0), 5));

        lblLine3.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lblLine3.setText("Line 1");

        lblLine2.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lblLine2.setText("Line 2");

        javax.swing.GroupLayout pnlTextLayout = new javax.swing.GroupLayout(pnlText);
        pnlText.setLayout(pnlTextLayout);
        pnlTextLayout.setHorizontalGroup(
            pnlTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTextLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLine3, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
                    .addComponent(lblLine2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlTextLayout.setVerticalGroup(
            pnlTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLine3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLine2)
                .addContainerGap(81, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlLeftLayout = new javax.swing.GroupLayout(pnlLeft);
        pnlLeft.setLayout(pnlLeftLayout);
        pnlLeftLayout.setHorizontalGroup(
            pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLeftLayout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addComponent(pnlText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );
        pnlLeftLayout.setVerticalGroup(
            pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLeftLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(pnlText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        btnFight.setBackground(new java.awt.Color(255, 51, 0));
        btnFight.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnFight.setForeground(new java.awt.Color(255, 255, 255));
        btnFight.setText("FIGHT");
        btnFight.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 5));
        btnFight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFightActionPerformed(evt);
            }
        });

        btnBag.setBackground(new java.awt.Color(255, 204, 0));
        btnBag.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnBag.setForeground(new java.awt.Color(255, 255, 255));
        btnBag.setText("BAG");
        btnBag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 5));
        btnBag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBagActionPerformed(evt);
            }
        });

        btnPokemon.setBackground(new java.awt.Color(0, 255, 102));
        btnPokemon.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnPokemon.setForeground(new java.awt.Color(255, 255, 255));
        btnPokemon.setText("POKÉMON");
        btnPokemon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 5));
        btnPokemon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPokemonActionPerformed(evt);
            }
        });

        btnRun.setBackground(new java.awt.Color(0, 153, 255));
        btnRun.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnRun.setForeground(new java.awt.Color(255, 255, 255));
        btnRun.setText("RUN");
        btnRun.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 5));
        btnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBattle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlLeft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnFight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPokemon, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlBattle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnFight, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                            .addComponent(btnBag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPokemon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFightActionPerformed
        // TODO add your handling code here:
        if (!waitingForAction || !myTurn) return;
        
        if (inMoveSelection) {
            PokemonDetail detail = myPokemonDetails.get(myCurrentPokemon);
            if (detail != null && detail.getMove1() != null) {
                executeMyMove(detail.getMove1());
            }
        } else {
            showMoveSelection();
        }
    }//GEN-LAST:event_btnFightActionPerformed

    private void btnBagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBagActionPerformed
        // TODO add your handling code here:
        if (!waitingForAction || !myTurn) return;
        
        if (inMoveSelection) {
            PokemonDetail detail = myPokemonDetails.get(myCurrentPokemon);
            if (detail != null && detail.getMove2() != null) {
                executeMyMove(detail.getMove2());
            }
        } else {
            showBattleMessage("No items available in multiplayer!", "Choose another action.");
        }
    }//GEN-LAST:event_btnBagActionPerformed

    private void btnPokemonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPokemonActionPerformed
        // TODO add your handling code here:
        if (!waitingForAction || !myTurn) return;
        
        if (inMoveSelection) {
            PokemonDetail detail = myPokemonDetails.get(myCurrentPokemon);
            if (detail != null && detail.getMove3() != null) {
                executeMyMove(detail.getMove3());
            }
        } else {
            showPokemonSelection();
        }
    }//GEN-LAST:event_btnPokemonActionPerformed

    private void btnRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunActionPerformed
        // TODO add your handling code here:
        if (!waitingForAction) return;
        
        if (inMoveSelection) {
            PokemonDetail detail = myPokemonDetails.get(myCurrentPokemon);
            if (detail != null && detail.getMove4() != null) {
                executeMyMove(detail.getMove4());
            }
        } else {
            int result = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to forfeit?", 
                "Forfeit Battle", 
                JOptionPane.YES_NO_OPTION);
                
            if (result == JOptionPane.YES_OPTION) {
                socket.sendFromUI("BATTLE_FORFEIT");
                battleEnded = true;
                showBattleMessage("You forfeited the battle!", "");
                disableAllButtons();
            }
        }
    }//GEN-LAST:event_btnRunActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MultiplayerPokeDuelJFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBag;
    private javax.swing.JButton btnFight;
    private javax.swing.JButton btnPokemon;
    private javax.swing.JButton btnRun;
    private javax.swing.JLabel lblBackground;
    private javax.swing.JLabel lblLine2;
    private javax.swing.JLabel lblLine3;
    private javax.swing.JLabel lblOpponentPokemonImage;
    private javax.swing.JLabel lblOpponentPokemonLevel;
    private javax.swing.JLabel lblOpponentPokemonName;
    private javax.swing.JLabel lblYourPokemonImage;
    private javax.swing.JLabel lblYourPokemonLevel;
    private javax.swing.JLabel lblYourPokemonName;
    private javax.swing.JProgressBar pgbOpponentPokemonHP;
    private javax.swing.JProgressBar pgbYourPokemonHP;
    private javax.swing.JPanel pnlBattle;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlOpponentPokemonStats;
    private javax.swing.JPanel pnlText;
    private javax.swing.JPanel pnlYourPokemonStats;
    // End of variables declaration//GEN-END:variables
}
