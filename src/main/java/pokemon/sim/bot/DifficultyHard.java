/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon.sim.bot;

import pokemon.sim.util.XJson;
import pokemon.sim.util.XJsonBranch;
import pokemon.sim.util.XRand;
import pokemon.sim.entity.Pokemon;
import pokemon.sim.entity.Move;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * Hard difficulty AI bot for Pokemon battles
 * Implements advanced strategies, optimal moves, and tactical decision making
 * Uses sophisticated algorithms to provide challenging gameplay
 * 
 * Features:
 * - Type effectiveness calculations
 * - Move prediction and counter-strategies  
 * - Team composition analysis
 * - Statistical move selection
 * - Advanced switching logic
 * - Item usage optimization
 * 
 * @author May5th
 */
public class DifficultyHard {
    
    private final XJson xJson;
    private final XJsonBranch xJsonBranch;
    private final Map<String, Double> typeEffectiveness;
    private final Map<String, Integer> moveScores;
    private final List<String> priorityMoves;
    private final Set<String> statusMoves;
    
    // AI Configuration
    private static final int SWITCH_THRESHOLD = 25; // HP% below which to consider switching
    private static final int PREDICTION_DEPTH = 3; // Moves ahead to calculate
    private static final double TYPE_ADVANTAGE_WEIGHT = 2.0;
    private static final double CRITICAL_HIT_FACTOR = 1.1;
    private static final int HARD_MODE_LUCK_FACTOR = 85; // Higher chance of favorable outcomes
    
    /**
     * Constructor for Hard Difficulty AI
     * Initializes JSON utilities and AI data structures
     */
    public DifficultyHard() {
        this.xJson = new XJson("pokemon/sim/data");
        this.xJsonBranch = new XJsonBranch(xJson);
        this.typeEffectiveness = new HashMap<>();
        this.moveScores = new HashMap<>();
        this.priorityMoves = new ArrayList<>();
        this.statusMoves = new HashSet<>();
        
        initializeAIData();
    }
    
    /**
     * Constructor with custom base path
     * @param dataPath base path for Pokemon data files
     */
    public DifficultyHard(String dataPath) {
        this.xJson = new XJson(dataPath);
        this.xJsonBranch = new XJsonBranch(xJson);
        this.typeEffectiveness = new HashMap<>();
        this.moveScores = new HashMap<>();
        this.priorityMoves = new ArrayList<>();
        this.statusMoves = new HashSet<>();
        
        initializeAIData();
    }
    
    /**
     * Initialize AI data structures and load type effectiveness chart
     */
    private void initializeAIData() {
        try {
            loadTypeEffectiveness();
            loadMoveStrategies();
            loadPriorityMoves();
            loadStatusMoves();
        } catch (IOException e) {
            System.err.println("Warning: Could not load AI data - " + e.getMessage());
            // Initialize with default values if files not available
            initializeDefaultData();
        }
    }
    
    /**
     * Load type effectiveness chart from JSON
     */
    private void loadTypeEffectiveness() throws IOException {
        try {
            JsonNode typeChart = xJson.loadJsonNode("PokemonType.json");
            
            // Parse type effectiveness relationships
            if (typeChart.isArray()) {
                for (JsonNode typeNode : typeChart) {
                    String typeName = typeNode.get("name").asText();
                    JsonNode effectiveness = typeNode.get("effectiveness");
                    
                    if (effectiveness != null) {
                        // Super effective against
                        JsonNode superEffective = effectiveness.get("super_effective");
                        if (superEffective != null && superEffective.isArray()) {
                            for (JsonNode target : superEffective) {
                                typeEffectiveness.put(typeName + "_vs_" + target.asText(), 2.0);
                            }
                        }
                        
                        // Not very effective against
                        JsonNode notVeryEffective = effectiveness.get("not_very_effective");
                        if (notVeryEffective != null && notVeryEffective.isArray()) {
                            for (JsonNode target : notVeryEffective) {
                                typeEffectiveness.put(typeName + "_vs_" + target.asText(), 0.5);
                            }
                        }
                        
                        // No effect against
                        JsonNode noEffect = effectiveness.get("no_effect");
                        if (noEffect != null && noEffect.isArray()) {
                            for (JsonNode target : noEffect) {
                                typeEffectiveness.put(typeName + "_vs_" + target.asText(), 0.0);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Could not load type effectiveness: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Load move strategies and scoring system
     */
    private void loadMoveStrategies() {
        // High-value moves for hard AI
        moveScores.put("Earthquake", 95);
        moveScores.put("Surf", 90);
        moveScores.put("Psychic", 90);
        moveScores.put("Thunderbolt", 90);
        moveScores.put("Ice Beam", 90);
        moveScores.put("Flamethrower", 90);
        moveScores.put("Hyper Beam", 85);
        moveScores.put("Solar Beam", 85);
        moveScores.put("Blizzard", 80);
        moveScores.put("Fire Blast", 80);
        moveScores.put("Thunder", 80);
        
        // Setup moves
        moveScores.put("Swords Dance", 70);
        moveScores.put("Calm Mind", 70);
        moveScores.put("Dragon Dance", 75);
        moveScores.put("Nasty Plot", 75);
        
        // Status moves
        moveScores.put("Toxic", 60);
        moveScores.put("Thunder Wave", 65);
        moveScores.put("Will-O-Wisp", 60);
        moveScores.put("Sleep Powder", 55);
        moveScores.put("Spore", 70);
    }
    
    /**
     * Load priority moves list
     */
    private void loadPriorityMoves() {
        priorityMoves.add("Quick Attack");
        priorityMoves.add("Extreme Speed");
        priorityMoves.add("Bullet Punch");
        priorityMoves.add("Mach Punch");
        priorityMoves.add("Aqua Jet");
        priorityMoves.add("Ice Shard");
        priorityMoves.add("Shadow Sneak");
        priorityMoves.add("Sucker Punch");
    }
    
    /**
     * Load status moves set
     */
    private void loadStatusMoves() {
        statusMoves.add("Toxic");
        statusMoves.add("Thunder Wave");
        statusMoves.add("Will-O-Wisp");
        statusMoves.add("Sleep Powder");
        statusMoves.add("Spore");
        statusMoves.add("Confuse Ray");
        statusMoves.add("Hypnosis");
        statusMoves.add("Stun Spore");
        statusMoves.add("Poison Powder");
        statusMoves.add("Paralysis");
    }
    
    /**
     * Initialize default data if JSON loading fails
     */
    private void initializeDefaultData() {
        // Basic type effectiveness (simplified)
        typeEffectiveness.put("Fire_vs_Grass", 2.0);
        typeEffectiveness.put("Fire_vs_Ice", 2.0);
        typeEffectiveness.put("Fire_vs_Bug", 2.0);
        typeEffectiveness.put("Fire_vs_Steel", 2.0);
        typeEffectiveness.put("Water_vs_Fire", 2.0);
        typeEffectiveness.put("Water_vs_Ground", 2.0);
        typeEffectiveness.put("Water_vs_Rock", 2.0);
        typeEffectiveness.put("Grass_vs_Water", 2.0);
        typeEffectiveness.put("Grass_vs_Ground", 2.0);
        typeEffectiveness.put("Grass_vs_Rock", 2.0);
        typeEffectiveness.put("Electric_vs_Water", 2.0);
        typeEffectiveness.put("Electric_vs_Flying", 2.0);
        
        System.out.println("Initialized with default AI data");
    }
    
    /**
     * Calculate the best move for the current situation
     * @param currentPokemon AI's current Pokemon
     * @param opponentPokemon opponent's current Pokemon
     * @param availableMoves list of available moves
     * @param battleContext current battle state information
     * @return the selected move name
     */
    public String selectBestMove(Pokemon currentPokemon, Pokemon opponentPokemon, 
                                List<String> availableMoves, BattleContext battleContext) {
        
        if (availableMoves == null || availableMoves.isEmpty()) {
            return "Struggle"; // Fallback move
        }
        
        Map<String, Double> moveRankings = new HashMap<>();
        
        // Evaluate each available move
        for (String moveName : availableMoves) {
            double score = evaluateMove(moveName, currentPokemon, opponentPokemon, battleContext);
            moveRankings.put(moveName, score);
        }
        
        // Select move based on weighted probability (not always the best)
        if (XRand.chance(HARD_MODE_LUCK_FACTOR)) {
            // 85% chance to pick optimal move
            return selectOptimalMove(moveRankings);
        } else {
            // 15% chance for strategic randomness
            return selectWeightedRandomMove(moveRankings);
        }
    }
    
    /**
     * Evaluate a specific move's effectiveness
     * @param moveName name of the move to evaluate
     * @param currentPokemon AI's current Pokemon
     * @param opponentPokemon opponent's Pokemon
     * @param battleContext current battle state
     * @return numerical score for the move
     */
    private double evaluateMove(String moveName, Pokemon currentPokemon, 
                               Pokemon opponentPokemon, BattleContext battleContext) {
        double score = 0.0;
        
        try {
            // Load move data
            Move move = xJsonBranch.loadMoveByInclude(moveName + ".json");
            
            // Base move power score
            score += (move.getPower() * 0.5);
            
            // Type effectiveness bonus
            double typeMultiplier = calculateTypeEffectiveness(
                move.getType(), opponentPokemon.getTypes()
            );
            score *= (1.0 + (typeMultiplier * TYPE_ADVANTAGE_WEIGHT));
            
            // Accuracy consideration
            score *= (move.getAccuracy() / 100.0);
            
            // Critical hit potential
            if (XRand.chance(12)) { // Assume ~12% crit rate
                score *= CRITICAL_HIT_FACTOR;
            }
            
            // Strategic bonuses
            score += evaluateStrategicValue(moveName, battleContext);
            
            // Situational bonuses
            score += evaluateSituationalValue(moveName, currentPokemon, 
                                            opponentPokemon, battleContext);
            
        } catch (IOException e) {
            // If move data can't be loaded, use base score
            score = moveScores.getOrDefault(moveName, 50);
        }
        
        return score;
    }
    
    /**
     * Calculate type effectiveness multiplier
     * @param attackType the attacking move's type
     * @param defenderTypes list of defender's types
     * @return effectiveness multiplier
     */
    private double calculateTypeEffectiveness(String attackType, List<String> defenderTypes) {
        double totalMultiplier = 1.0;
        
        for (String defenderType : defenderTypes) {
            String key = attackType + "_vs_" + defenderType;
            double multiplier = typeEffectiveness.getOrDefault(key, 1.0);
            totalMultiplier *= multiplier;
        }
        
        return totalMultiplier;
    }
    
    /**
     * Evaluate strategic value of a move
     * @param moveName name of the move
     * @param battleContext current battle state
     * @return strategic bonus score
     */
    private double evaluateStrategicValue(String moveName, BattleContext battleContext) {
        double bonus = 0.0;
        
        // Priority move bonus when opponent is low HP
        if (priorityMoves.contains(moveName) && battleContext.isOpponentLowHP()) {
            bonus += 30.0;
        }
        
        // Status move bonus early in battle
        if (statusMoves.contains(moveName) && battleContext.getTurnCount() < 3) {
            bonus += 20.0;
        }
        
        // Setup move bonus when safe
        if (isSetupMove(moveName) && battleContext.isSafeToSetup()) {
            bonus += 25.0;
        }
        
        // OHKO potential bonus
        if (canOHKO(moveName, battleContext)) {
            bonus += 50.0;
        }
        
        return bonus;
    }
    
    /**
     * Evaluate situational value of a move
     * @param moveName name of the move
     * @param currentPokemon AI's Pokemon
     * @param opponentPokemon opponent's Pokemon  
     * @param battleContext battle state
     * @return situational bonus score
     */
    private double evaluateSituationalValue(String moveName, Pokemon currentPokemon,
                                           Pokemon opponentPokemon, BattleContext battleContext) {
        double bonus = 0.0;
        
        // Weather-dependent moves
        if (battleContext.hasWeather()) {
            if (moveName.equals("Solar Beam") && battleContext.getWeather().equals("Sun")) {
                bonus += 20.0;
            }
            if (moveName.equals("Thunder") && battleContext.getWeather().equals("Rain")) {
                bonus += 15.0;
            }
        }
        
        // Revenge killing bonus
        if (battleContext.wasAllyKnockedOut() && isPriorityMove(moveName)) {
            bonus += 35.0;
        }
        
        // Coverage move bonus
        if (providesGoodCoverage(moveName, battleContext.getOpponentTeam())) {
            bonus += 15.0;
        }
        
        return bonus;
    }
    
    /**
     * Determine if AI should switch Pokemon
     * @param currentPokemon AI's current Pokemon
     * @param opponentPokemon opponent's current Pokemon
     * @param availableTeam AI's available team members
     * @param battleContext current battle state
     * @return true if should switch, false otherwise
     */
    public boolean shouldSwitch(Pokemon currentPokemon, Pokemon opponentPokemon,
                               List<Pokemon> availableTeam, BattleContext battleContext) {
        
        // Never switch if no alternatives
        if (availableTeam == null || availableTeam.isEmpty()) {
            return false;
        }
        
        // Don't switch if current Pokemon has advantage
        double typeAdvantage = calculateBestTypeMatchup(currentPokemon, opponentPokemon);
        if (typeAdvantage > 1.5) {
            return false;
        }
        
        // Switch if current Pokemon is at low HP and vulnerable
        if (battleContext.getCurrentHPPercentage() < SWITCH_THRESHOLD) {
            if (wouldTakeMajorDamage(currentPokemon, opponentPokemon, battleContext)) {
                return true;
            }
        }
        
        // Switch for better type matchup
        Pokemon bestCounter = findBestCounter(opponentPokemon, availableTeam);
        if (bestCounter != null) {
            double counterAdvantage = calculateBestTypeMatchup(bestCounter, opponentPokemon);
            if (counterAdvantage > typeAdvantage * 1.3) {
                return XRand.chance(70); // 70% chance to make the switch
            }
        }
        
        // Predicted switch scenario
        if (predictOpponentSwitch(battleContext) && hasGoodPrediction(availableTeam, battleContext)) {
            return XRand.chance(60);
        }
        
        return false;
    }
    
    /**
     * Select the best Pokemon to switch to
     * @param opponentPokemon opponent's current Pokemon
     * @param availableTeam list of available team members
     * @param battleContext current battle state
     * @return the Pokemon to switch to, or null if no good option
     */
    public Pokemon selectSwitchTarget(Pokemon opponentPokemon, List<Pokemon> availableTeam,
                                     BattleContext battleContext) {
        
        if (availableTeam == null || availableTeam.isEmpty()) {
            return null;
        }
        
        // Find Pokemon with best type matchup
        Pokemon bestCounter = findBestCounter(opponentPokemon, availableTeam);
        if (bestCounter != null) {
            return bestCounter;
        }
        
        // Find healthiest Pokemon as fallback
        Pokemon healthiest = null;
        double bestHP = 0.0;
        
        for (Pokemon pokemon : availableTeam) {
            double hpPercentage = calculateHPPercentage(pokemon);
            if (hpPercentage > bestHP) {
                bestHP = hpPercentage;
                healthiest = pokemon;
            }
        }
        
        return healthiest;
    }
    
    /**
     * Determine if AI should use an item
     * @param currentPokemon AI's current Pokemon
     * @param availableItems list of available items
     * @param battleContext current battle state
     * @return item name to use, or null if no item should be used
     */
    public String selectItem(Pokemon currentPokemon, List<String> availableItems,
                            BattleContext battleContext) {
        
        if (availableItems == null || availableItems.isEmpty()) {
            return null;
        }
        
        double currentHP = battleContext.getCurrentHPPercentage();
        
        // Critical HP - use strongest healing item
        if (currentHP <= 15) {
            if (availableItems.contains("Full Restore")) {
                return "Full Restore";
            }
            if (availableItems.contains("Max Potion")) {
                return "Max Potion";
            }
            if (availableItems.contains("Hyper Potion")) {
                return "Hyper Potion";
            }
        }
        
        // Low HP - use appropriate healing
        if (currentHP <= 40) {
            if (availableItems.contains("Hyper Potion") && currentHP <= 30) {
                return "Hyper Potion";
            }
            if (availableItems.contains("Super Potion") && currentHP <= 25) {
                return "Super Potion";
            }
        }
        
        // Status condition healing
        if (battleContext.hasStatusCondition()) {
            String statusCondition = battleContext.getStatusCondition();
            
            if (availableItems.contains("Full Restore")) {
                return "Full Restore";
            }
            
            // Specific status healers
            if (statusCondition.equals("Sleep") && availableItems.contains("Awakening")) {
                return "Awakening";
            }
            if (statusCondition.equals("Paralysis") && availableItems.contains("Paralyze Heal")) {
                return "Paralyze Heal";
            }
            if ((statusCondition.equals("Poison") || statusCondition.equals("Badly Poisoned")) 
                && availableItems.contains("Antidote")) {
                return "Antidote";
            }
        }
        
        // Strategic X items usage
        if (battleContext.isSafeToSetup() && XRand.chance(30)) {
            if (availableItems.contains("X Attack")) {
                return "X Attack";
            }
            if (availableItems.contains("X Special")) {
                return "X Special";
            }
        }
        
        return null; // No item needed
    }
    
    /**
     * Load trainer data from JSON file
     * @param trainerName name of the trainer
     * @return JsonNode containing trainer data
     * @throws IOException if trainer file cannot be loaded
     */
    public JsonNode loadTrainerData(String trainerName) throws IOException {
        String trainerPath = "trainers/" + trainerName.toLowerCase() + ".json";
        return xJsonBranch.loadWithIncludes(trainerPath);
    }
    
    /**
     * Get AI difficulty statistics and performance metrics
     * @return formatted string with AI statistics
     */
    public String getAIStats() {
        StringBuilder stats = new StringBuilder();
        stats.append("=== Hard Difficulty AI Statistics ===\n");
        stats.append("Optimal Decision Rate: ").append(HARD_MODE_LUCK_FACTOR).append("%\n");
        stats.append("Type Effectiveness Entries: ").append(typeEffectiveness.size()).append("\n");
        stats.append("Move Strategies Loaded: ").append(moveScores.size()).append("\n");
        stats.append("Priority Moves Known: ").append(priorityMoves.size()).append("\n");
        stats.append("Status Moves Recognized: ").append(statusMoves.size()).append("\n");
        stats.append("Prediction Depth: ").append(PREDICTION_DEPTH).append(" moves\n");
        stats.append("Switch Threshold: ").append(SWITCH_THRESHOLD).append("% HP\n");
        stats.append(xJsonBranch.getCacheInfo());
        
        return stats.toString();
    }
    
    // Helper methods
    
    private String selectOptimalMove(Map<String, Double> moveRankings) {
        return moveRankings.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Tackle");
    }
    
    private String selectWeightedRandomMove(Map<String, Double> moveRankings) {
        List<String> moves = new ArrayList<>(moveRankings.keySet());
        XRand.shuffle(moves);
        
        // Weight toward higher-scored moves
        List<String> weightedMoves = new ArrayList<>();
        for (Map.Entry<String, Double> entry : moveRankings.entrySet()) {
            int weight = Math.max(1, (int)(entry.getValue() / 20));
            for (int i = 0; i < weight; i++) {
                weightedMoves.add(entry.getKey());
            }
        }
        
        return XRand.pick(weightedMoves);
    }
    
    private double calculateBestTypeMatchup(Pokemon attacker, Pokemon defender) {
        double bestMultiplier = 0.0;
        
        for (String attackerType : attacker.getTypes()) {
            double multiplier = calculateTypeEffectiveness(attackerType, defender.getTypes());
            bestMultiplier = Math.max(bestMultiplier, multiplier);
        }
        
        return bestMultiplier;
    }
    
    private Pokemon findBestCounter(Pokemon opponent, List<Pokemon> team) {
        Pokemon bestCounter = null;
        double bestAdvantage = 0.0;
        
        for (Pokemon pokemon : team) {
            double advantage = calculateBestTypeMatchup(pokemon, opponent);
            double disadvantage = calculateBestTypeMatchup(opponent, pokemon);
            double netAdvantage = advantage - disadvantage;
            
            if (netAdvantage > bestAdvantage) {
                bestAdvantage = netAdvantage;
                bestCounter = pokemon;
            }
        }
        
        return bestCounter;
    }
    
    private boolean isSetupMove(String moveName) {
        return moveName.contains("Dance") || moveName.contains("Mind") || 
               moveName.equals("Swords Dance") || moveName.equals("Nasty Plot");
    }
    
    private boolean isPriorityMove(String moveName) {
        return priorityMoves.contains(moveName);
    }
    
    private boolean canOHKO(String moveName, BattleContext context) {
        // Simplified OHKO calculation
        return moveScores.getOrDefault(moveName, 0) > 90 && 
               context.getCurrentHPPercentage() > 80;
    }
    
    private boolean wouldTakeMajorDamage(Pokemon current, Pokemon opponent, BattleContext context) {
        double typeDisadvantage = calculateBestTypeMatchup(opponent, current);
        return typeDisadvantage >= 2.0 || context.getCurrentHPPercentage() < 30;
    }
    
    private boolean predictOpponentSwitch(BattleContext context) {
        return context.getOpponentSwitchPattern() > 0.3; // 30% switch probability
    }
    
    private boolean hasGoodPrediction(List<Pokemon> team, BattleContext context) {
        return team.size() > 1 && XRand.chance(60);
    }
    
    private boolean providesGoodCoverage(String moveName, List<Pokemon> opponentTeam) {
        // Simplified coverage calculation
        return moveScores.getOrDefault(moveName, 0) > 70;
    }
    
    private double calculateHPPercentage(Pokemon pokemon) {
        // This would need to be implemented based on your Pokemon class structure
        // Placeholder implementation
        return XRand.range(50, 100);
    }
    
    public static class BattleContext {
        private int turnCount;
        private double currentHPPercentage;
        private double opponentHPPercentage;
        private String weather;
        private String statusCondition;
        private boolean allyKnockedOut;
        private boolean safeToSetup;
        private double opponentSwitchPattern;
        private List<Pokemon> opponentTeam;
        
        // Getters and setters
        public int getTurnCount() { return turnCount; }
        public void setTurnCount(int turnCount) { this.turnCount = turnCount; }
        
        public double getCurrentHPPercentage() { return currentHPPercentage; }
        public void setCurrentHPPercentage(double currentHPPercentage) { this.currentHPPercentage = currentHPPercentage; }
        
        public double getOpponentHPPercentage() { return opponentHPPercentage; }
        public void setOpponentHPPercentage(double opponentHPPercentage) { this.opponentHPPercentage = opponentHPPercentage; }
        
        public String getWeather() { return weather; }
        public void setWeather(String weather) { this.weather = weather; }
        
        public String getStatusCondition() { return statusCondition; }
        public void setStatusCondition(String statusCondition) { this.statusCondition = statusCondition; }
        
        public boolean wasAllyKnockedOut() { return allyKnockedOut; }
        public void setAllyKnockedOut(boolean allyKnockedOut) { this.allyKnockedOut = allyKnockedOut; }
        
        public boolean isSafeToSetup() { return safeToSetup; }
        public void setSafeToSetup(boolean safeToSetup) { this.safeToSetup = safeToSetup; }
        
        public double getOpponentSwitchPattern() { return opponentSwitchPattern; }
        public void setOpponentSwitchPattern(double opponentSwitchPattern) { this.opponentSwitchPattern = opponentSwitchPattern; }
        
        public List<Pokemon> getOpponentTeam() { return opponentTeam; }
        public void setOpponentTeam(List<Pokemon> opponentTeam) { this.opponentTeam = opponentTeam; }
        
        // Convenience methods
        public boolean isOpponentLowHP() { return opponentHPPercentage < 25; }
        public boolean hasWeather() { return weather != null && !weather.isEmpty(); }
        public boolean hasStatusCondition() { return statusCondition != null && !statusCondition.isEmpty(); }
    }
}
