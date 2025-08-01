/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pokemon.sim.ui;

import pokemon.sim.entity.*;
import pokemon.sim.util.*;
import pokemon.sim.bot.DifficultyHard;
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
public class PokeDuelJFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PokeDuelJFrame.class.getName());

    // Battle State
    private List<Pokemon> playerTeam;
    private List<Pokemon> opponentTeam;
    private Pokemon currentPlayerPokemon;
    private Pokemon currentOpponentPokemon;
    private int currentPlayerPokemonIndex = 0;
    private int currentOpponentPokemonIndex = 0;

    // Pokemon details with moves
    private Map<Pokemon, PokemonDetail> playerPokemonDetails;
    private Map<Pokemon, PokemonDetail> opponentPokemonDetails;

    // HP tracking
    private Map<Pokemon, Integer> currentHP;
    private Map<Pokemon, Integer> maxHP;

    // Items
    private List<String> opponentItems;
    private int opponentMoney;

    // Utilities
    private XJson xJson;
    private XJsonBranch xJsonBranch;
    private XMove xMove;
    private DifficultyHard aiBot;

    // UI State
    private boolean waitingForAction = true;
    private boolean inMoveSelection = false;
    private boolean inItemSelection = false;

    // Battle context for AI
    private DifficultyHard.BattleContext battleContext;
    private int turnCount = 0;

    /**
     * Creates new form PokeDuelJFrame
     */
    public PokeDuelJFrame() {
        initComponents();
        initializeUtilities();
        initializeBattle();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Constructor that accepts selected team from dialog
     */
    public PokeDuelJFrame(String[] selectedPokemonNames) {
        initComponents();
        initializeUtilities();
        initializeBattleWithTeam(selectedPokemonNames);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Constructor for multiplayer battles with both teams specified
     */
    public PokeDuelJFrame(String[] playerTeamNames, String[] opponentTeamNames) {
        initComponents();
        initializeUtilities();
        initializeMultiplayerBattle(playerTeamNames, opponentTeamNames);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initializeMultiplayerBattle(String[] playerTeamNames, String[] opponentTeamNames) {
        try {
            // Handle empty or null teams
            if (playerTeamNames == null || playerTeamNames.length == 0) {
                logger.warning("No player team selected, using default team");
                playerTeamNames = new String[]{"Pikachu", "Charizard", "Blastoise"};
            }

            if (opponentTeamNames == null || opponentTeamNames.length == 0) {
                logger.warning("No opponent team selected, using default team");
                opponentTeamNames = new String[]{"Alakazam", "Dragonite", "Lapras"};
            }

            // Load both teams
            playerTeam = loadPlayerTeam(playerTeamNames);
            opponentTeam = loadCustomOpponentTeam(opponentTeamNames);

            // Initialize HP tracking
            initializeHP();

            // Set first Pokemon
            currentPlayerPokemon = playerTeam.get(0);
            currentOpponentPokemon = opponentTeam.get(0);

            // Update UI
            updateBattleUI();

            // Show initial message
            showBattleMessage("Battle starts!", "Choose your action!");

        } catch (Exception e) {
            logger.severe("Failed to initialize multiplayer battle: " + e.getMessage());
            e.printStackTrace();
            showBattleMessage("Error loading battle data!", "Please restart the game.");
        }
    }

    private List<Pokemon> loadCustomOpponentTeam(String[] selectedPokemonNames) throws IOException {
        List<Pokemon> team = new ArrayList<>();

        for (String pokemonName : selectedPokemonNames) {
            if (pokemonName == null) {
                continue;
            }

            // Find Pokemon file by name
            String fileName = findPokemonFileName(pokemonName);
            if (fileName != null) {
                Pokemon pokemon = xJsonBranch.loadPokemonByInclude(fileName);
                team.add(pokemon);

                // Use the same move generation as player Pokemon
                PokemonDetail detail = createPlayerPokemonDetail(pokemon.getName());
                opponentPokemonDetails.put(pokemon, detail);
            }
        }

        return team;
    }

    private void initializeUtilities() {
        xJson = new XJson();
        xJsonBranch = new XJsonBranch(xJson);
        xMove = new XMove(xJson);
        aiBot = new DifficultyHard();

        currentHP = new HashMap<>();
        maxHP = new HashMap<>();
        playerPokemonDetails = new HashMap<>();
        opponentPokemonDetails = new HashMap<>();
        opponentItems = new ArrayList<>();
        opponentMoney = 0;

        battleContext = new DifficultyHard.BattleContext();
    }

    private void initializeBattle() {
        // Default initialization with sample teams
        String[] defaultTeam = {"Pikachu", "Charizard", "Blastoise"};
        initializeBattleWithTeam(defaultTeam);
    }

    private void initializeBattleWithTeam(String[] selectedPokemonNames) {
        try {
            // Handle empty or null team
            if (selectedPokemonNames == null || selectedPokemonNames.length == 0) {
                logger.warning("No team selected, using default team");
                selectedPokemonNames = new String[]{"Pikachu", "Charizard", "Blastoise"};
            }

            // Load player team
            playerTeam = loadPlayerTeam(selectedPokemonNames);

            // Load opponent team (Blue Champion)
            opponentTeam = loadOpponentTeam();

            // Initialize HP tracking
            initializeHP();

            // Set first Pokemon
            currentPlayerPokemon = playerTeam.get(0);
            currentOpponentPokemon = opponentTeam.get(0);

            // Update UI
            updateBattleUI();

            // Show initial message
            showBattleMessage("Blue wants to battle!", "Choose your action!");

        } catch (Exception e) {
            logger.severe("Failed to initialize battle: " + e.getMessage());
            e.printStackTrace();
            showBattleMessage("Error loading battle data!", "Please restart the game.");
        }
    }

    private List<Pokemon> loadPlayerTeam(String[] selectedPokemonNames) throws IOException {
        List<Pokemon> team = new ArrayList<>();

        for (String pokemonName : selectedPokemonNames) {
            if (pokemonName == null) {
                continue;
            }

            // Find Pokemon file by name
            String fileName = findPokemonFileName(pokemonName);
            if (fileName != null) {
                Pokemon pokemon = xJsonBranch.loadPokemonByInclude(fileName);
                team.add(pokemon);

                // Hard-code moves for player Pokemon
                PokemonDetail detail = createPlayerPokemonDetail(pokemon.getName());
                playerPokemonDetails.put(pokemon, detail);
            }
        }

        return team;
    }

    private PokemonDetail createPlayerPokemonDetail(String pokemonName) {
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
                detail.setMove4("Body Slam");
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
        // Map Pokemon names to their JSON file names
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

    private List<Pokemon> loadOpponentTeam() throws IOException {
        List<Pokemon> team = new ArrayList<>();

        // Load Blue's champion team - XJsonBranch will resolve all $include references
        JsonNode blueData = xJsonBranch.loadWithIncludes("pokemon/sim/data/trainer/blue_champion.json");

        // Get trainer info
        String trainerName = blueData.get("name").asText();
        String trainerImage = blueData.has("image") ? blueData.get("image").asText() : null;
        logger.info("Loading team for trainer: " + trainerName
                + (trainerImage != null ? " (image: " + trainerImage + ")" : ""));

        // Load trainer's money reward
        if (blueData.has("money")) {
            opponentMoney = blueData.get("money").asInt();
        }

        // Load trainer's bag items
        opponentItems = new ArrayList<>();
        JsonNode bagNode = blueData.get("bag");
        if (bagNode != null && bagNode.isArray() && bagNode.size() > 0) {
            JsonNode bagItems = bagNode.get(0); // First bag configuration
            bagItems.forEach(itemNode -> {
                if (itemNode != null && itemNode.has("name")) {
                    opponentItems.add(itemNode.get("name").asText());
                }
            });
            logger.info("Loaded " + opponentItems.size() + " items for trainer");
        }

        // Get the first team configuration
        JsonNode teamData = blueData.get("team");
        if (teamData == null || !teamData.isArray() || teamData.size() == 0) {
            throw new IOException("No team data found for trainer");
        }

        JsonNode firstTeam = teamData.get(0);

        // Process each Pokemon in the team
        for (int i = 1; i <= 6; i++) {
            String pokemonKey = "pokemon" + i;
            JsonNode pokemonArrayNode = firstTeam.get(pokemonKey);

            if (pokemonArrayNode != null && pokemonArrayNode.isArray() && pokemonArrayNode.size() > 0) {
                JsonNode pokemonData = pokemonArrayNode.get(0); // Get first element of array

                // Parse Pokemon data (already resolved by XJsonBranch)
                Pokemon pokemon = parsePokemonFromResolvedNode(pokemonData);
                team.add(pokemon);

                // Parse moves if available
                JsonNode movesNode = pokemonData.get("moves");
                if (movesNode != null && movesNode.isArray() && movesNode.size() > 0) {
                    JsonNode movesArray = movesNode.get(0); // Get first moves configuration
                    PokemonDetail detail = parsePokemonMoves(movesArray);
                    opponentPokemonDetails.put(pokemon, detail);
                } else {
                    // No moves specified, use default
                    PokemonDetail detail = new PokemonDetail();
                    opponentPokemonDetails.put(pokemon, detail);
                }

                logger.info("Loaded " + pokemon.getName() + " with moves");
            }
        }

        logger.info("Loaded " + trainerName + "'s team with " + team.size() + " Pokemon");
        return team;
    }

    private Pokemon parsePokemonFromResolvedNode(JsonNode pokemonNode) {
        Pokemon pokemon = new Pokemon();

        // Basic info
        pokemon.setName(pokemonNode.get("name").asText());
        pokemon.setNationalNumber(pokemonNode.get("national_number").asText());
        pokemon.setSpecies(pokemonNode.get("species").asText());
        pokemon.setHeight((float) pokemonNode.get("height").asDouble());
        pokemon.setWeight((float) pokemonNode.get("weight").asDouble());

        // Image array
        JsonNode imageNode = pokemonNode.get("image");
        if (imageNode != null && imageNode.isArray()) {
            List<String> images = new ArrayList<>();
            imageNode.forEach(img -> images.add(img.asText()));
            pokemon.setImage(images);
        }

        // Type array
        JsonNode typeNode = pokemonNode.get("type");
        if (typeNode != null && typeNode.isArray()) {
            List<String> types = new ArrayList<>();
            typeNode.forEach(type -> types.add(type.asText()));
            pokemon.setTypes(types);
        }

        // Stats array
        JsonNode statsNode = pokemonNode.get("stats");
        if (statsNode != null && statsNode.isArray()) {
            List<Stats> statsList = new ArrayList<>();
            statsNode.forEach(statNode -> {
                Stats stats = new Stats(
                        statNode.get("hp").asInt(),
                        statNode.get("atk").asInt(),
                        statNode.get("def").asInt(),
                        statNode.get("sp-atk").asInt(),
                        statNode.get("sp-def").asInt(),
                        statNode.get("speed").asInt()
                );
                statsList.add(stats);
            });
            pokemon.setStats(statsList);
        }

        return pokemon;
    }

    private PokemonDetail parsePokemonMoves(JsonNode movesNode) {
        PokemonDetail detail = new PokemonDetail();
        List<String> moveNames = new ArrayList<>();

        // Each element in the moves array should be a resolved move object
        movesNode.forEach(moveNode -> {
            if (moveNode != null && moveNode.has("name")) {
                moveNames.add(moveNode.get("name").asText());
            }
        });

        // Assign moves to the detail object
        if (moveNames.size() > 0) {
            detail.setMove1(moveNames.get(0));
        }
        if (moveNames.size() > 1) {
            detail.setMove2(moveNames.get(1));
        }
        if (moveNames.size() > 2) {
            detail.setMove3(moveNames.get(2));
        }
        if (moveNames.size() > 3) {
            detail.setMove4(moveNames.get(3));
        }

        return detail;
    }

    private void initializeHP() {
        // Initialize HP for all Pokemon
        for (Pokemon pokemon : playerTeam) {
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
        // Use base HP stat with level scaling
        if (pokemon.getStats() != null && !pokemon.getStats().isEmpty()) {
            int baseHP = pokemon.getStats().get(0).getHp();
            int level = 50; // Default level
            return ((2 * baseHP + 31) * level / 100) + level + 10;
        }
        return 100; // Default HP
    }

    private void updateBattleUI() {
        // Update player Pokemon
        updatePokemonDisplay(currentPlayerPokemon, true);

        // Update opponent Pokemon
        updatePokemonDisplay(currentOpponentPokemon, false);

        // Update HP bars
        updateHPBars();
    }

    private void updatePokemonDisplay(Pokemon pokemon, boolean isPlayer) {
        if (pokemon == null) {
            return;
        }

        JLabel nameLabel = isPlayer ? lblYourPokemonName : lblOpponentPokemonName;
        JLabel levelLabel = isPlayer ? lblYourPokemonLevel : lblOpponentPokemonLevel;
        JLabel imageLabel = isPlayer ? lblYourPokemonImage : lblOpponentPokemonImage;

        // Update name
        nameLabel.setText(pokemon.getName().toUpperCase());

        // Update level
        levelLabel.setText("LV 50"); // Default level

        // Update image
        try {
            BufferedImage pokemonImage = isPlayer
                    ? XImage.getPokemonBackImage(xJson.getObjectMapper().valueToTree(pokemon))
                    : XImage.getPokemonFrontImage(xJson.getObjectMapper().valueToTree(pokemon));

            if (pokemonImage != null) {
                Image scaledImage = XImage.resize(pokemonImage, 300, 300);
                imageLabel.setIcon(new ImageIcon(scaledImage));
            }
        } catch (Exception e) {
            logger.warning("Failed to load Pokemon image: " + e.getMessage());
        }
    }

    private void updateHPBars() {
        // Update player HP bar
        if (currentPlayerPokemon != null) {
            int current = currentHP.getOrDefault(currentPlayerPokemon, 0);
            int max = maxHP.getOrDefault(currentPlayerPokemon, 1);
            int percentage = (current * 100) / max;
            pgbYourPokemonHP.setValue(percentage);
            pgbYourPokemonHP.setString(current + "/" + max);
            pgbYourPokemonHP.setStringPainted(true);

            // Color based on HP percentage
            if (percentage > 50) {
                pgbYourPokemonHP.setForeground(new Color(51, 255, 0));
            } else if (percentage > 20) {
                pgbYourPokemonHP.setForeground(Color.YELLOW);
            } else {
                pgbYourPokemonHP.setForeground(Color.RED);
            }
        }

        // Update opponent HP bar
        if (currentOpponentPokemon != null) {
            int current = currentHP.getOrDefault(currentOpponentPokemon, 0);
            int max = maxHP.getOrDefault(currentOpponentPokemon, 1);
            int percentage = (current * 100) / max;
            pgbOpponentPokemonHP.setValue(percentage);
            pgbOpponentPokemonHP.setString(current + "/" + max);
            pgbOpponentPokemonHP.setStringPainted(true);

            // Color based on HP percentage
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
        lblLine1.setText(line1);
        lblLine2.setText(line2);
    }

    private void showMoveSelection() {
        inMoveSelection = true;
        showBattleMessage("Choose a move:", "");

        PokemonDetail detail = playerPokemonDetails.get(currentPlayerPokemon);
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
        inItemSelection = false;
    }

    private void executePlayerMove(String moveName) {
        if (moveName == null || moveName.equals("---")) {
            showBattleMessage("No move in that slot!", "Choose another move.");
            return;
        }

        resetButtons();
        waitingForAction = false;

        // Execute player's move
        XMove.MoveResult result = xMove.executeMove(currentPlayerPokemon, currentOpponentPokemon, moveName + ".json");

        // Apply damage
        if (result.isHit() && result.getDamage() > 0) {
            int newHP = Math.max(0, currentHP.get(currentOpponentPokemon) - result.getDamage());
            currentHP.put(currentOpponentPokemon, newHP);
            updateHPBars();
        }

        showBattleMessage(currentPlayerPokemon.getName() + " used " + moveName + "!",
                result.getMessage());

        // Check if opponent fainted
        if (currentHP.get(currentOpponentPokemon) <= 0) {
            handlePokemonFaint(false);
            return;
        }

        // Delay before opponent's turn
        javax.swing.Timer timer = new javax.swing.Timer(2000, e -> executeOpponentTurn());
        timer.setRepeats(false);
        timer.start();
    }

    private void executeOpponentTurn() {
        // Update battle context for AI
        updateBattleContext();

        // Check if AI should use an item
        String itemToUse = aiBot.selectItem(currentOpponentPokemon, opponentItems, battleContext);
        if (itemToUse != null && opponentItems.contains(itemToUse)) {
            // Use item
            useOpponentItem(itemToUse);
            opponentItems.remove(itemToUse); // Remove used item
            return;
        }

        // Check if AI should switch Pokemon
        List<Pokemon> availableOpponentPokemon = new ArrayList<>();
        for (int i = 0; i < opponentTeam.size(); i++) {
            Pokemon p = opponentTeam.get(i);
            if (p != currentOpponentPokemon && currentHP.get(p) > 0) {
                availableOpponentPokemon.add(p);
            }
        }

        if (aiBot.shouldSwitch(currentOpponentPokemon, currentPlayerPokemon,
                availableOpponentPokemon, battleContext)) {
            Pokemon switchTarget = aiBot.selectSwitchTarget(currentPlayerPokemon,
                    availableOpponentPokemon, battleContext);
            if (switchTarget != null) {
                switchOpponentPokemon(switchTarget);
                return;
            }
        }

        // Get opponent's available moves
        PokemonDetail opponentDetail = opponentPokemonDetails.get(currentOpponentPokemon);
        List<String> availableMoves = new ArrayList<>();
        if (opponentDetail.getMove1() != null) {
            availableMoves.add(opponentDetail.getMove1());
        }
        if (opponentDetail.getMove2() != null) {
            availableMoves.add(opponentDetail.getMove2());
        }
        if (opponentDetail.getMove3() != null) {
            availableMoves.add(opponentDetail.getMove3());
        }
        if (opponentDetail.getMove4() != null) {
            availableMoves.add(opponentDetail.getMove4());
        }

        // AI selects move
        String selectedMove = aiBot.selectBestMove(currentOpponentPokemon, currentPlayerPokemon,
                availableMoves, battleContext);

        // Execute opponent's move
        XMove.MoveResult result = xMove.executeMove(currentOpponentPokemon, currentPlayerPokemon,
                selectedMove + ".json");

        // Apply damage
        if (result.isHit() && result.getDamage() > 0) {
            int newHP = Math.max(0, currentHP.get(currentPlayerPokemon) - result.getDamage());
            currentHP.put(currentPlayerPokemon, newHP);
            updateHPBars();
        }

        showBattleMessage("Foe " + currentOpponentPokemon.getName() + " used " + selectedMove + "!",
                result.getMessage());

        // Check if player Pokemon fainted
        if (currentHP.get(currentPlayerPokemon) <= 0) {
            handlePokemonFaint(true);
            return;
        }

        // Return control to player
        javax.swing.Timer timer = new javax.swing.Timer(2000, e -> {
            waitingForAction = true;
            showBattleMessage("What will " + currentPlayerPokemon.getName() + " do?", "");
        });
        timer.setRepeats(false);
        timer.start();

        turnCount++;
    }

    private void useOpponentItem(String itemName) {
        showBattleMessage("Foe Blue used " + itemName + "!", "");

        // Apply item effect
        switch (itemName) {
            case "Full Restore":
                currentHP.put(currentOpponentPokemon, maxHP.get(currentOpponentPokemon));
                updateHPBars();
                showBattleMessage(currentOpponentPokemon.getName() + "'s HP was restored!",
                        "Status conditions were healed!");
                break;
            case "Max Potion":
                currentHP.put(currentOpponentPokemon, maxHP.get(currentOpponentPokemon));
                updateHPBars();
                showBattleMessage(currentOpponentPokemon.getName() + "'s HP was restored!", "");
                break;
            case "Hyper Potion":
                int healAmount = 200;
                int currentHp = currentHP.get(currentOpponentPokemon);
                int maxHp = maxHP.get(currentOpponentPokemon);
                int newHp = Math.min(currentHp + healAmount, maxHp);
                currentHP.put(currentOpponentPokemon, newHp);
                updateHPBars();
                showBattleMessage(currentOpponentPokemon.getName() + " recovered HP!", "");
                break;
            // Add more item effects as needed
        }

        // Return control to player after item use
        javax.swing.Timer timer = new javax.swing.Timer(2000, e -> {
            waitingForAction = true;
            showBattleMessage("What will " + currentPlayerPokemon.getName() + " do?", "");
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void switchOpponentPokemon(Pokemon newPokemon) {
        currentOpponentPokemon = newPokemon;

        // Find the index
        for (int i = 0; i < opponentTeam.size(); i++) {
            if (opponentTeam.get(i) == newPokemon) {
                currentOpponentPokemonIndex = i;
                break;
            }
        }

        showBattleMessage("Foe Blue withdrew " + currentOpponentPokemon.getName() + "!",
                "Foe Blue sent out " + newPokemon.getName() + "!");
        updateBattleUI();

        // Return control to player
        javax.swing.Timer timer = new javax.swing.Timer(2000, e -> {
            waitingForAction = true;
            showBattleMessage("What will " + currentPlayerPokemon.getName() + " do?", "");
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void updateBattleContext() {
        battleContext.setTurnCount(turnCount);
        battleContext.setCurrentHPPercentage(
                (currentHP.get(currentOpponentPokemon) * 100.0) / maxHP.get(currentOpponentPokemon)
        );
        battleContext.setOpponentHPPercentage(
                (currentHP.get(currentPlayerPokemon) * 100.0) / maxHP.get(currentPlayerPokemon)
        );
        battleContext.setSafeToSetup(battleContext.getCurrentHPPercentage() > 70);
        battleContext.setOpponentTeam(new ArrayList<>(playerTeam)); // AI sees player team as opponent
    }

    private void handlePokemonFaint(boolean isPlayer) {
        Pokemon faintedPokemon = isPlayer ? currentPlayerPokemon : currentOpponentPokemon;
        showBattleMessage(faintedPokemon.getName() + " fainted!", "");

        javax.swing.Timer timer = new javax.swing.Timer(2000, e -> {
            if (isPlayer) {
                // Find next available Pokemon for player
                int nextAvailable = findNextAvailablePokemon(playerTeam, -1);

                if (nextAvailable != -1) {
                    currentPlayerPokemon = playerTeam.get(nextAvailable);
                    currentPlayerPokemonIndex = nextAvailable;
                    showBattleMessage("Go! " + currentPlayerPokemon.getName() + "!", "");
                    updateBattleUI();
                    waitingForAction = true;
                } else {
                    // No more Pokemon available - player lost
                    showBattleMessage("You lost the battle!", "Blue wins!");
                    disableAllButtons();
                }
            } else {
                // Find next available Pokemon for opponent
                int nextAvailable = findNextAvailablePokemon(opponentTeam, -1);

                if (nextAvailable != -1) {
                    currentOpponentPokemon = opponentTeam.get(nextAvailable);
                    currentOpponentPokemonIndex = nextAvailable;
                    showBattleMessage("Blue sent out " + currentOpponentPokemon.getName() + "!", "");
                    updateBattleUI();
                    waitingForAction = true;
                } else {
                    // No more Pokemon available - player won
                    showBattleMessage("You defeated Blue!",
                            "You won $" + opponentMoney + "!");
                    disableAllButtons();
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Find the next available Pokemon with HP > 0
     *
     * @param team The team to search
     * @param excludeIndex Index to exclude (typically the current Pokemon)
     * @return Index of next available Pokemon, or -1 if none found
     */
    private int findNextAvailablePokemon(List<Pokemon> team, int excludeIndex) {
        for (int i = 0; i < team.size(); i++) {
            if (i != excludeIndex && team.get(i) != null) {
                Integer hp = currentHP.get(team.get(i));
                if (hp != null && hp > 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    private void disableAllButtons() {
        btnFight.setEnabled(false);
        btnBag.setEnabled(false);
        btnPokemon.setEnabled(false);
        btnRun.setEnabled(false);
    }

    private void showPokemonSelection() {
        // Create and show Pokemon selection dialog
        ChoosingPokemonInBattleJDialog dialog = new ChoosingPokemonInBattleJDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                true,
                playerTeam,
                currentPlayerPokemon,
                currentHP,
                maxHP
        );

        dialog.setVisible(true);

        // Check if a selection was made
        if (dialog.isSelectionMade()) {
            Pokemon selectedPokemon = dialog.getSelectedPokemon();
            int selectedIndex = dialog.getSelectedIndex();

            if (selectedPokemon != null) {
                switchPokemon(selectedIndex);
            }
        } else {
            // User cancelled - return to main menu
            resetButtons();
            showBattleMessage("What will " + currentPlayerPokemon.getName() + " do?", "");
        }
    }

    private void switchPokemon(int index) {
        if (index >= playerTeam.size()) {
            return;
        }

        Pokemon newPokemon = playerTeam.get(index);
        if (currentHP.get(newPokemon) <= 0) {
            showBattleMessage(newPokemon.getName() + " has fainted!", "Choose another Pokemon.");
            return;
        }

        if (newPokemon == currentPlayerPokemon) {
            showBattleMessage(newPokemon.getName() + " is already in battle!", "");
            return;
        }

        currentPlayerPokemon = newPokemon;
        currentPlayerPokemonIndex = index;

        updateBattleUI();
        showBattleMessage("Go! " + currentPlayerPokemon.getName() + "!", "");

        // Clear waiting state during switch
        waitingForAction = false;

        // Opponent gets a free turn after a switch
        javax.swing.Timer timer = new javax.swing.Timer(2000, e -> executeOpponentTurn());
        timer.setRepeats(false);
        timer.start();
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
        pnlYourPokemonStat = new javax.swing.JPanel();
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
        pnlOutline = new javax.swing.JPanel();
        pnlContent = new javax.swing.JPanel();
        lblLine1 = new javax.swing.JLabel();
        lblLine2 = new javax.swing.JLabel();
        btnFight = new javax.swing.JButton();
        btnPokemon = new javax.swing.JButton();
        btnBag = new javax.swing.JButton();
        btnRun = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlBattle.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlYourPokemonStat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        pgbYourPokemonHP.setForeground(new java.awt.Color(51, 255, 0));

        lblYourPokemonName.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblYourPokemonName.setText("YOUR POKÉMON");

        lblYourPokemonLevel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblYourPokemonLevel.setText("LV XX");

        javax.swing.GroupLayout pnlYourPokemonStatLayout = new javax.swing.GroupLayout(pnlYourPokemonStat);
        pnlYourPokemonStat.setLayout(pnlYourPokemonStatLayout);
        pnlYourPokemonStatLayout.setHorizontalGroup(
            pnlYourPokemonStatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlYourPokemonStatLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pnlYourPokemonStatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pgbYourPokemonHP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlYourPokemonStatLayout.createSequentialGroup()
                        .addComponent(lblYourPokemonName, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(lblYourPokemonLevel)))
                .addGap(22, 22, 22))
        );
        pnlYourPokemonStatLayout.setVerticalGroup(
            pnlYourPokemonStatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlYourPokemonStatLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(pnlYourPokemonStatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblYourPokemonName)
                    .addComponent(lblYourPokemonLevel, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(pgbYourPokemonHP, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pnlBattle.add(pnlYourPokemonStat, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 540, 610, 150));

        pnlOpponentPokemonStats.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        pgbOpponentPokemonHP.setForeground(new java.awt.Color(51, 255, 0));

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
                .addGroup(pnlOpponentPokemonStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlOpponentPokemonStatsLayout.createSequentialGroup()
                        .addComponent(lblOpponentPokemonName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(lblOpponentPokemonLevel))
                    .addComponent(pgbOpponentPokemonHP, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        pnlOpponentPokemonStatsLayout.setVerticalGroup(
            pnlOpponentPokemonStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlOpponentPokemonStatsLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(pnlOpponentPokemonStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOpponentPokemonName)
                    .addComponent(lblOpponentPokemonLevel, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(pgbOpponentPokemonHP, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        pnlBattle.add(pnlOpponentPokemonStats, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 610, 150));

        lblYourPokemonImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblYourPokemonImage.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        pnlBattle.add(lblYourPokemonImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 380, 340, 330));

        lblOpponentPokemonImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOpponentPokemonImage.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        pnlBattle.add(lblOpponentPokemonImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 110, 340, 330));

        lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/new-battle-background.png"))); // NOI18N
        pnlBattle.add(lblBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 710));

        pnlOutline.setBackground(new java.awt.Color(153, 102, 0));
        pnlOutline.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 0), 5));

        pnlContent.setBackground(new java.awt.Color(255, 255, 255));

        lblLine1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lblLine1.setText("Line 1");

        lblLine2.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lblLine2.setText("Line 2");
        lblLine2.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                lblLine2VetoableChange(evt);
            }
        });

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLine1, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
                    .addComponent(lblLine2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLine1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLine2)
                .addContainerGap(82, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlOutlineLayout = new javax.swing.GroupLayout(pnlOutline);
        pnlOutline.setLayout(pnlOutlineLayout);
        pnlOutlineLayout.setHorizontalGroup(
            pnlOutlineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOutlineLayout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addComponent(pnlContent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );
        pnlOutlineLayout.setVerticalGroup(
            pnlOutlineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOutlineLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(pnlContent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        btnFight.setBackground(new java.awt.Color(255, 51, 0));
        btnFight.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnFight.setForeground(new java.awt.Color(255, 255, 255));
        btnFight.setText("FIGHT");
        btnFight.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 5));
        btnFight.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFightActionPerformed(evt);
            }
        });

        btnPokemon.setBackground(new java.awt.Color(0, 255, 102));
        btnPokemon.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnPokemon.setForeground(new java.awt.Color(255, 255, 255));
        btnPokemon.setText("POKÉMON");
        btnPokemon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 5));
        btnPokemon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPokemon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPokemonActionPerformed(evt);
            }
        });

        btnBag.setBackground(new java.awt.Color(255, 204, 0));
        btnBag.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnBag.setForeground(new java.awt.Color(255, 255, 255));
        btnBag.setText("BAG");
        btnBag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 5));
        btnBag.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBagActionPerformed(evt);
            }
        });

        btnRun.setBackground(new java.awt.Color(0, 153, 255));
        btnRun.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnRun.setForeground(new java.awt.Color(255, 255, 255));
        btnRun.setText("RUN");
        btnRun.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 5));
        btnRun.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlBattle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlOutline, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnPokemon, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(btnFight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnBag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRun, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlBattle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnBag, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                            .addComponent(btnFight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRun, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPokemon, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(pnlOutline, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblLine2VetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_lblLine2VetoableChange
        // TODO add your handling code here:
        // Ignore this
    }//GEN-LAST:event_lblLine2VetoableChange

    private void btnFightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFightActionPerformed
        // TODO add your handling code here:
        if (!waitingForAction) {
            return;
        }

        if (inMoveSelection) {
            PokemonDetail detail = playerPokemonDetails.get(currentPlayerPokemon);
            if (detail != null && detail.getMove1() != null) {
                executePlayerMove(detail.getMove1());
            }
        } else {
            showMoveSelection();
        }
    }//GEN-LAST:event_btnFightActionPerformed

    private void btnBagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBagActionPerformed
        // TODO add your handling code here:
        if (!waitingForAction) {
            return;
        }

        if (inMoveSelection) {
            PokemonDetail detail = playerPokemonDetails.get(currentPlayerPokemon);
            if (detail != null && detail.getMove2() != null) {
                executePlayerMove(detail.getMove2());
            }
        } else {
            showBattleMessage("No items available!", "Choose another action.");
        }
    }//GEN-LAST:event_btnBagActionPerformed

    private void btnPokemonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPokemonActionPerformed
        // TODO add your handling code here:
        if (!waitingForAction) {
            return;
        }

        if (inMoveSelection) {
            PokemonDetail detail = playerPokemonDetails.get(currentPlayerPokemon);
            if (detail != null && detail.getMove3() != null) {
                executePlayerMove(detail.getMove3());
            }
        } else {
            // Show Pokemon selection dialog
            showPokemonSelection();
        }
    }//GEN-LAST:event_btnPokemonActionPerformed

    private void btnRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunActionPerformed
        // TODO add your handling code here:
        if (!waitingForAction) {
            return;
        }

        if (inMoveSelection) {
            PokemonDetail detail = playerPokemonDetails.get(currentPlayerPokemon);
            if (detail != null && detail.getMove4() != null) {
                executePlayerMove(detail.getMove4());
            }
        } else {
            showBattleMessage("Can't run from a trainer battle!", "Choose another action.");
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
        java.awt.EventQueue.invokeLater(() -> {
            // Show team selection dialog first
            NewChoosingPokemonJDialog dialog = new NewChoosingPokemonJDialog(null, true);
            dialog.setVisible(true);

            // Get selected team
            String[] selectedTeam = dialog.getSelectedPokemon();

            // Check if user selected a team
            if (dialog.hasValidTeam()) {
                new PokeDuelJFrame(selectedTeam).setVisible(true);
            } else {
                // Default team if no selection
                new PokeDuelJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBag;
    private javax.swing.JButton btnFight;
    private javax.swing.JButton btnPokemon;
    private javax.swing.JButton btnRun;
    private javax.swing.JLabel lblBackground;
    private javax.swing.JLabel lblLine1;
    private javax.swing.JLabel lblLine2;
    private javax.swing.JLabel lblOpponentPokemonImage;
    private javax.swing.JLabel lblOpponentPokemonLevel;
    private javax.swing.JLabel lblOpponentPokemonName;
    private javax.swing.JLabel lblYourPokemonImage;
    private javax.swing.JLabel lblYourPokemonLevel;
    private javax.swing.JLabel lblYourPokemonName;
    private javax.swing.JProgressBar pgbOpponentPokemonHP;
    private javax.swing.JProgressBar pgbYourPokemonHP;
    private javax.swing.JPanel pnlBattle;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlOpponentPokemonStats;
    private javax.swing.JPanel pnlOutline;
    private javax.swing.JPanel pnlYourPokemonStat;
    // End of variables declaration//GEN-END:variables
}
