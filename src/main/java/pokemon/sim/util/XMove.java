/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon.sim.util;

import com.fasterxml.jackson.databind.JsonNode;
import pokemon.sim.entity.Move;
import pokemon.sim.entity.Pokemon;
import pokemon.sim.entity.PokemonType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Utility class for Pokemon move execution and management
 * Handles move loading, damage calculation, accuracy checks, type effectiveness, and special effects
 * 
 * @author May5th
 */
public class XMove {
    
    private static final Logger logger = LoggerFactory.getLogger(XMove.class);
    
    // Move cache for performance optimization
    private static final Map<String, Move> moveCache = new HashMap<>();
    private static final Map<String, JsonNode> moveJsonCache = new HashMap<>();
    
    // Type effectiveness cache
    private static final Map<String, PokemonType> typeCache = new HashMap<>();
    private static boolean typeDataLoaded = false;
    
    // XJson instance for loading move data
    private final XJson xJson;
    
    // Constants
    private static final String NO_VALUE_INDICATOR = "--";
    private static final int GUARANTEED_ACCURACY = 101; // Above 100% for guaranteed hits
    private static final int NO_DAMAGE = 0;
    
    // Type effectiveness multipliers
    private static final double SUPER_EFFECTIVE = 2.0;
    private static final double NOT_VERY_EFFECTIVE = 0.5;
    private static final double NO_EFFECT = 0.0;
    private static final double NORMAL_EFFECTIVE = 1.0;
    
    /**
     * Move execution result container
     */
    public static class MoveResult {
        private final boolean hit;
        private final int damage;
        private final boolean critical;
        private final String effect;
        private final String message;
        private final boolean ppReduced;
        private final double typeEffectiveness;
        private final String effectivenessMessage;
        
        public MoveResult(boolean hit, int damage, boolean critical, String effect, String message, 
                         boolean ppReduced, double typeEffectiveness, String effectivenessMessage) {
            this.hit = hit;
            this.damage = damage;
            this.critical = critical;
            this.effect = effect;
            this.message = message;
            this.ppReduced = ppReduced;
            this.typeEffectiveness = typeEffectiveness;
            this.effectivenessMessage = effectivenessMessage;
        }
        
        // Getters
        public boolean isHit() { return hit; }
        public int getDamage() { return damage; }
        public boolean isCritical() { return critical; }
        public String getEffect() { return effect; }
        public String getMessage() { return message; }
        public boolean isPpReduced() { return ppReduced; }
        public double getTypeEffectiveness() { return typeEffectiveness; }
        public String getEffectivenessMessage() { return effectivenessMessage; }
        
        public boolean isSuperEffective() { return typeEffectiveness > NORMAL_EFFECTIVE; }
        public boolean isNotVeryEffective() { return typeEffectiveness > NO_EFFECT && typeEffectiveness < NORMAL_EFFECTIVE; }
        public boolean isNoEffect() { return typeEffectiveness == NO_EFFECT; }
    }
    
    /**
     * Move category enumeration
     */
    public enum MoveCategory {
        PHYSICAL, SPECIAL, STATUS
    }
    
    /**
     * Constructor with XJson instance
     * @param xJson the XJson instance to use for loading move data
     */
    public XMove(XJson xJson) {
        this.xJson = xJson;
        ensureTypeDataLoaded();
    }
    
    /**
     * Default constructor - creates new XJson instance
     */
    public XMove() {
        this.xJson = new XJson();
        ensureTypeDataLoaded();
    }
    
    /**
     * Ensure type effectiveness data is loaded
     */
    private void ensureTypeDataLoaded() {
        if (!typeDataLoaded) {
            loadTypeEffectivenessData();
        }
    }
    
    /**
     * Load type effectiveness data from PokemonType.json
     */
    private void loadTypeEffectivenessData() {
        try {
            List<PokemonType> types = xJson.loadPokemonTypes("PokemonType.json");
            for (PokemonType type : types) {
                typeCache.put(type.getName(), type);
            }
            typeDataLoaded = true;
            logger.info("Loaded {} Pokemon types for effectiveness calculations", types.size());
        } catch (IOException e) {
            logger.error("Failed to load type effectiveness data", e);
        }
    }
    
    /**
     * Load a move from JSON file with caching
     * @param moveFileName the move JSON filename
     * @return Move object or null if not found
     */
    public Move loadMove(String moveFileName) {
        // Check cache first
        if (moveCache.containsKey(moveFileName)) {
            return moveCache.get(moveFileName);
        }
        
        try {
            Move move = xJson.loadMove(moveFileName);
            if (move != null) {
                moveCache.put(moveFileName, move);
                logger.debug("Loaded and cached move: {}", move.getName());
            }
            return move;
        } catch (IOException e) {
            logger.error("Failed to load move from file: {}", moveFileName, e);
            return null;
        }
    }
    
    /**
     * Load move JSON data with caching
     * @param moveFileName the move JSON filename
     * @return JsonNode containing move data or null if not found
     */
    public JsonNode loadMoveJson(String moveFileName) {
        // Check cache first
        if (moveJsonCache.containsKey(moveFileName)) {
            return moveJsonCache.get(moveFileName);
        }
        
        try {
            JsonNode moveJson = xJson.loadJsonNode(moveFileName);
            if (moveJson != null) {
                moveJsonCache.put(moveFileName, moveJson);
                logger.debug("Loaded and cached move JSON: {}", moveFileName);
            }
            return moveJson;
        } catch (IOException e) {
            logger.error("Failed to load move JSON from file: {}", moveFileName, e);
            return null;
        }
    }
    
    /**
     * Execute a move between attacker and defender Pokemon
     * @param attacker the Pokemon using the move
     * @param defender the Pokemon receiving the move
     * @param moveFileName the filename of the move to execute
     * @return MoveResult containing execution details
     */
    public MoveResult executeMove(Pokemon attacker, Pokemon defender, String moveFileName) {
        Move move = loadMove(moveFileName);
        if (move == null) {
            return new MoveResult(false, 0, false, "", 
                "Move " + moveFileName + " could not be loaded!", false, NORMAL_EFFECTIVE, "");
        }
        
        return executeMove(attacker, defender, move);
    }
    
    /**
     * Execute a move between attacker and defender Pokemon
     * @param attacker the Pokemon using the move
     * @param defender the Pokemon receiving the move
     * @param move the Move object to execute
     * @return MoveResult containing execution details
     */
    public MoveResult executeMove(Pokemon attacker, Pokemon defender, Move move) {
        if (attacker == null || defender == null || move == null) {
            return new MoveResult(false, 0, false, "", "Invalid parameters for move execution!", 
                                false, NORMAL_EFFECTIVE, "");
        }
        
        logger.info("{} uses {}!", attacker.getName(), move.getName());
        
        // Check accuracy using XRand
        boolean hit = checkAccuracy(move);
        if (!hit) {
            return new MoveResult(false, 0, false, move.getEffect(), 
                attacker.getName() + "'s " + move.getName() + " missed!", true, NORMAL_EFFECTIVE, "");
        }
        
        // Calculate type effectiveness
        double typeEffectiveness = calculateTypeEffectiveness(move, defender);
        String effectivenessMessage = getEffectivenessMessage(typeEffectiveness);
        
        // Handle immunity (no effect)
        if (typeEffectiveness == NO_EFFECT) {
            return new MoveResult(true, 0, false, move.getEffect(), 
                "It had no effect on " + defender.getName() + "!", true, NO_EFFECT, effectivenessMessage);
        }
        
        // Calculate damage
        int baseDamage = calculateBaseDamage(attacker, defender, move);
        
        // Apply type effectiveness
        int damage = (int) (baseDamage * typeEffectiveness);
        
        // Check for critical hit using XRand
        boolean critical = XRand.crit(16); // 1/16 chance
        
        if (critical && damage > 0) {
            damage = (int) (damage * 1.5); // 1.5x damage for critical hits
        }
        
        // Build result message
        String message = buildExecutionMessage(attacker, defender, move, damage, critical, effectivenessMessage);
        
        return new MoveResult(true, damage, critical, move.getEffect(), message, true, 
                            typeEffectiveness, effectivenessMessage);
    }
    
    /**
     * Check if a move hits based on its accuracy using XRand
     * @param move the move to check
     * @return true if the move hits, false if it misses
     */
    public boolean checkAccuracy(Move move) {
        // Handle "--" accuracy (guaranteed hit)
        if (move.getAccuracy() == 0 || isNoValueIndicator(String.valueOf(move.getAccuracy()))) {
            logger.debug("Move {} has guaranteed accuracy", move.getName());
            return true;
        }
        
        // Handle accuracy greater than 100 (also guaranteed)
        if (move.getAccuracy() >= GUARANTEED_ACCURACY) {
            return true;
        }
        
        // Use XRand for accuracy check
        boolean hit = XRand.chance(move.getAccuracy());
        
        logger.debug("Accuracy check for {}: {}% chance, hit: {}", 
            move.getName(), move.getAccuracy(), hit);
        
        return hit;
    }
    
    /**
     * Calculate base damage dealt by a move (before type effectiveness)
     * @param attacker the attacking Pokemon
     * @param defender the defending Pokemon  
     * @param move the move being used
     * @return base damage amount
     */
    public int calculateBaseDamage(Pokemon attacker, Pokemon defender, Move move) {
        // Handle "--" power (no damage)
        if (move.getPower() == 0 || isNoValueIndicator(String.valueOf(move.getPower()))) {
            logger.debug("Move {} deals no damage (status move or -- power)", move.getName());
            return NO_DAMAGE;
        }
        
        // Get move category
        MoveCategory category = getMoveCategory(move);
        if (category == MoveCategory.STATUS) {
            return NO_DAMAGE;
        }
        
        // Get attacker and defender stats (using first stats entry if available)
        int attackStat = getAttackStat(attacker, category);
        int defenseStat = getDefenseStat(defender, category);
        
        // Basic damage formula: ((Attack/Defense) * Power * Level) / 50
        // Simplified for demonstration - real Pokemon uses more complex formula
        int level = 50; // Assume level 50 for now
        double baseDamage = ((double) attackStat / defenseStat) * move.getPower() * level / 50.0;
        
        // Add random factor using XRand (85-100% of calculated damage)
        double randomFactor = 0.85 + (XRand.get().nextDouble() * 0.15);
        int finalDamage = Math.max(1, (int) (baseDamage * randomFactor));
        
        logger.debug("Base damage calculation for {}: base={}, final={}", 
            move.getName(), (int)baseDamage, finalDamage);
        
        return finalDamage;
    }
    
    /**
     * Calculate type effectiveness multiplier
     * @param move the move being used
     * @param defender the defending Pokemon
     * @return effectiveness multiplier
     */
    public double calculateTypeEffectiveness(Move move, Pokemon defender) {
        if (move == null || defender == null || defender.getTypes() == null || defender.getTypes().isEmpty()) {
            return NORMAL_EFFECTIVE;
        }
        
        String moveType = move.getType();
        double totalEffectiveness = NORMAL_EFFECTIVE;
        
        // Calculate effectiveness against each of the defender's types
        for (String defenderType : defender.getTypes()) {
            double typeMultiplier = getTypeMultiplier(moveType, defenderType);
            totalEffectiveness *= typeMultiplier;
        }
        
        logger.debug("Type effectiveness: {} vs {}: {}", 
            moveType, defender.getTypes(), totalEffectiveness);
        
        return totalEffectiveness;
    }
    
    /**
     * Get type effectiveness multiplier between attacking and defending types
     * @param attackingType the type of the move
     * @param defendingType the type of the defending Pokemon
     * @return effectiveness multiplier
     */
    private double getTypeMultiplier(String attackingType, String defendingType) {
        PokemonType defenderTypeData = typeCache.get(defendingType);
        if (defenderTypeData == null) {
            logger.warn("Unknown defending type: {}", defendingType);
            return NORMAL_EFFECTIVE;
        }
        
        // Check for immunity
        if (defenderTypeData.getImmunities() != null && 
            defenderTypeData.getImmunities().contains(attackingType)) {
            return NO_EFFECT;
        }
        
        // Check for weakness (super effective)
        if (defenderTypeData.getWeaknesses() != null && 
            defenderTypeData.getWeaknesses().contains(attackingType)) {
            return SUPER_EFFECTIVE;
        }
        
        // Check for resistance (not very effective)
        if (defenderTypeData.getResistances() != null && 
            defenderTypeData.getResistances().contains(attackingType)) {
            return NOT_VERY_EFFECTIVE;
        }
        
        // Normal effectiveness
        return NORMAL_EFFECTIVE;
    }
    
    /**
     * Get effectiveness message for display
     * @param effectiveness the effectiveness multiplier
     * @return message string
     */
    private String getEffectivenessMessage(double effectiveness) {
        if (effectiveness == NO_EFFECT) {
            return "It had no effect!";
        } else if (effectiveness > NORMAL_EFFECTIVE) {
            return "It's super effective!";
        } else if (effectiveness < NORMAL_EFFECTIVE && effectiveness > NO_EFFECT) {
            return "It's not very effective...";
        }
        return "";
    }
    
    /**
     * Get move category from move data
     * @param move the move to categorize
     * @return MoveCategory enum value
     */
    public MoveCategory getMoveCategory(Move move) {
        String category = move.getCategory().toLowerCase();
        switch (category) {
            case "physical":
                return MoveCategory.PHYSICAL;
            case "special":
                return MoveCategory.SPECIAL;
            case "status":
            default:
                return MoveCategory.STATUS;
        }
    }
    
    /**
     * Get appropriate attack stat based on move category
     * @param pokemon the Pokemon
     * @param category the move category
     * @return attack stat value
     */
    private int getAttackStat(Pokemon pokemon, MoveCategory category) {
        if (pokemon.getStats() == null || pokemon.getStats().isEmpty()) {
            return 50; // Default value
        }
        
        var stats = pokemon.getStats().get(0); // Use first stats entry
        return category == MoveCategory.PHYSICAL ? stats.getAtk() : stats.getSpAtk();
    }
    
    /**
     * Get appropriate defense stat based on move category
     * @param pokemon the Pokemon
     * @param category the move category
     * @return defense stat value
     */
    private int getDefenseStat(Pokemon pokemon, MoveCategory category) {
        if (pokemon.getStats() == null || pokemon.getStats().isEmpty()) {
            return 50; // Default value
        }
        
        var stats = pokemon.getStats().get(0); // Use first stats entry
        return category == MoveCategory.PHYSICAL ? stats.getDef() : stats.getSpDef();
    }
    
    /**
     * Check if a string value represents the "no value" indicator
     * @param value the value to check
     * @return true if the value is "--" or equivalent
     */
    private boolean isNoValueIndicator(String value) {
        return value != null && (value.equals(NO_VALUE_INDICATOR) || value.trim().equals(NO_VALUE_INDICATOR));
    }
    
    /**
     * Build execution message for move result
     * @param attacker the attacking Pokemon
     * @param defender the defending Pokemon
     * @param move the move used
     * @param damage damage dealt
     * @param critical whether it was a critical hit
     * @param effectivenessMessage type effectiveness message
     * @return formatted message string
     */
    private String buildExecutionMessage(Pokemon attacker, Pokemon defender, Move move, int damage, 
                                       boolean critical, String effectivenessMessage) {
        StringBuilder message = new StringBuilder();
        
        if (damage > 0) {
            if (critical) {
                message.append("Critical hit! ");
            }
            message.append(String.format("%s dealt %d damage to %s!", 
                move.getName(), damage, defender.getName()));
            
            if (!effectivenessMessage.isEmpty()) {
                message.append(" ").append(effectivenessMessage);
            }
        } else {
            // Status move or no damage
            message.append(String.format("%s used %s!", attacker.getName(), move.getName()));
            if (move.getEffect() != null && !move.getEffect().isEmpty()) {
                message.append(" ").append(move.getEffect());
            }
        }
        
        return message.toString();
    }
    
    /**
     * Load multiple moves from a list of filenames
     * @param moveFileNames list of move JSON filenames
     * @return list of loaded Move objects (nulls filtered out)
     */
    public List<Move> loadMoves(List<String> moveFileNames) {
        List<Move> moves = new ArrayList<>();
        
        for (String fileName : moveFileNames) {
            Move move = loadMove(fileName);
            if (move != null) {
                moves.add(move);
            }
        }
        
        return moves;
    }
    
    /**
     * Validate if a move can be used (basic PP check could be added here)
     * @param move the move to validate
     * @return true if move can be used
     */
    public boolean validateMove(Move move) {
        if (move == null) {
            return false;
        }
        
        // Basic validation - move exists and has valid data
        return move.getName() != null && !move.getName().trim().isEmpty();
    }
    
    /**
     * Get move power, handling "--" values
     * @param move the move to check
     * @return power value (0 for "--" or status moves)
     */
    public int getMovePower(Move move) {
        if (move == null) return 0;
        
        // Check if power is "--" by examining the raw value
        if (move.getPower() == 0) {
            // Could be either 0 power or "--", treat as no damage
            return NO_DAMAGE;
        }
        
        return move.getPower();
    }
    
    /**
     * Get move accuracy, handling "--" values
     * @param move the move to check
     * @return accuracy value (101 for guaranteed hits)
     */
    public int getMoveAccuracy(Move move) {
        if (move == null) return 0;
        
        // Check if accuracy is "--" by examining the raw value
        if (move.getAccuracy() == 0) {
            // Treat as guaranteed hit
            return GUARANTEED_ACCURACY;
        }
        
        return move.getAccuracy();
    }
    
    /**
     * Get type effectiveness between two specific types
     * @param attackingType the attacking type
     * @param defendingType the defending type
     * @return effectiveness multiplier
     */
    public double getTypeEffectiveness(String attackingType, String defendingType) {
        ensureTypeDataLoaded();
        return getTypeMultiplier(attackingType, defendingType);
    }
    
    /**
     * Check if a type is immune to another type
     * @param attackingType the attacking type
     * @param defendingType the defending type
     * @return true if immune (no effect)
     */
    public boolean isImmune(String attackingType, String defendingType) {
        return getTypeEffectiveness(attackingType, defendingType) == NO_EFFECT;
    }
    
    /**
     * Check if a type is weak to another type
     * @param attackingType the attacking type
     * @param defendingType the defending type
     * @return true if weak (super effective)
     */
    public boolean isWeak(String attackingType, String defendingType) {
        return getTypeEffectiveness(attackingType, defendingType) == SUPER_EFFECTIVE;
    }
    
    /**
     * Check if a type resists another type
     * @param attackingType the attacking type
     * @param defendingType the defending type
     * @return true if resistant (not very effective)
     */
    public boolean isResistant(String attackingType, String defendingType) {
        return getTypeEffectiveness(attackingType, defendingType) == NOT_VERY_EFFECTIVE;
    }
    
    /**
     * Clear all caches
     */
    public static void clearCache() {
        moveCache.clear();
        moveJsonCache.clear();
        typeCache.clear();
        typeDataLoaded = false;
        logger.info("Move caches cleared");
    }
    
    /**
     * Get cache statistics
     * @return string containing cache information
     */
    public static String getCacheInfo() {
        return String.format("Move Cache Info - Moves: %d, JSON: %d, Types: %d", 
            moveCache.size(), moveJsonCache.size(), typeCache.size());
    }
    
    /**
     * Check if a move is cached
     * @param moveFileName the move filename to check
     * @return true if move is in cache
     */
    public static boolean isCached(String moveFileName) {
        return moveCache.containsKey(moveFileName);
    }
    
    /**
     * Get all cached move names
     * @return list of cached move filenames
     */
    public static List<String> getCachedMoveNames() {
        return new ArrayList<>(moveCache.keySet());
    }
    
    /**
     * Preload commonly used moves for performance
     * @param moveFileNames list of move filenames to preload
     */
    public void preloadMoves(List<String> moveFileNames) {
        logger.info("Preloading {} moves...", moveFileNames.size());
        
        for (String fileName : moveFileNames) {
            loadMove(fileName);
        }
        
        logger.info("Preloading complete. Cache now contains {} moves", moveCache.size());
    }
    
    /**
     * Get the underlying XJson instance
     * @return the XJson instance
     */
    public XJson getXJson() {
        return xJson;
    }
}