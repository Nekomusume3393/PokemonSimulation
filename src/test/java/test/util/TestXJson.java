/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.util;

import pokemon.sim.util.XJson;
import pokemon.sim.util.XJsonBranch;
import pokemon.sim.entity.Pokemon;
import pokemon.sim.entity.PokemonType;
import pokemon.sim.entity.Move;
import pokemon.sim.entity.Stats;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Testing XJson and XJsonBranch
 * @author May5th
 */
public class TestXJson {

    private final XJson xJson;
    private final XJsonBranch xJsonBranch;
    
    public TestXJson() {
        this.xJson = new XJson();
        this.xJsonBranch = new XJsonBranch();
    }
    
    public static void main(String[] args) {
        TestXJson example = new TestXJson();
        
        try {
            example.demonstrateBasicLoading();
            example.demonstrateIncludeResolution();
            example.demonstrateAdvancedUsage();
        } catch (IOException e) {
            System.err.println("Example failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Demonstrate basic JSON loading without includes
     */
    public void demonstrateBasicLoading() throws IOException {
        System.out.println("=== Basic JSON Loading ===");
        
        // Load individual Pokemon
        Pokemon bulbasaur = xJson.loadPokemon("pokemon/sim/data/pokemon/001.json");
        System.out.println("Loaded: " + bulbasaur.getName());
        System.out.println("  National #: " + bulbasaur.getNationalNumber());
        System.out.println("  Types: " + bulbasaur.getTypes());
        System.out.println("  Species: " + bulbasaur.getSpecies());
        
        // Show stats
        if (!bulbasaur.getStats().isEmpty()) {
            Stats stats = bulbasaur.getStats().get(0);
            System.out.printf("  Base Stats: HP=%d, ATK=%d, DEF=%d, SP.ATK=%d, SP.DEF=%d, SPEED=%d%n",
                stats.getHp(), stats.getAtk(), stats.getDef(), 
                stats.getSpAtk(), stats.getSpDef(), stats.getSpeed());
        }
        
        // Load a move
        try {
            Move absorb = xJson.loadMove("pokemon/sim/data/move/absorb.json");
            System.out.println("\nLoaded Move: " + absorb.getName());
            System.out.println("  Type: " + absorb.getType());
            System.out.println("  Power: " + absorb.getPower());
            System.out.println("  Effect: " + absorb.getEffect());
        } catch (Exception e) {
            System.out.println("Could not load move (file may not exist): " + e.getMessage());
        }
        
        // Load all Pokemon types
        List<PokemonType> types = xJson.loadPokemonTypes("pokemon/sim/data/PokemonType.json");
        System.out.println("\nLoaded " + types.size() + " Pokemon types");
        
        System.out.println();
    }
    
    /**
     * Demonstrate $include resolution functionality
     */
    public void demonstrateIncludeResolution() throws IOException {
        System.out.println("=== Include Resolution Demo ===");
        
        // Load all Pokemon using $include resolution
        List<Pokemon> allPokemon = xJsonBranch.loadAllPokemon("pokemon/sim/data/PokemonList.json");
        System.out.println("Loaded " + allPokemon.size() + " Pokemon with include resolution");
        
        // Show first 5 Pokemon
        System.out.println("\nFirst 5 Pokemon:");
        for (int i = 0; i < Math.min(5, allPokemon.size()); i++) {
            Pokemon pokemon = allPokemon.get(i);
            System.out.printf("%d. %s (#%s) - %s%n", 
                i + 1, pokemon.getName(), pokemon.getNationalNumber(), pokemon.getTypes());
        }
        
        // Load specific Pokemon by include path
        Pokemon pikachu = xJsonBranch.loadPokemonByInclude("/pokemon/sim/data/pokemon/025.json");
        System.out.println("\nLoaded Pikachu by include path: " + pikachu.getName());
        
        // Demonstrate caching
        long startTime = System.currentTimeMillis();
        Pokemon pikachu2 = xJsonBranch.loadPokemonByInclude("/pokemon/sim/data/pokemon/025.json");
        long loadTime = System.currentTimeMillis() - startTime;
        System.out.println("Second load (cached): " + pikachu2.getName() + " (took " + loadTime + "ms)");
        
        System.out.println(xJsonBranch.getCacheInfo());
        System.out.println();
    }
    
    /**
     * Demonstrate advanced usage patterns
     */
    public void demonstrateAdvancedUsage() throws IOException {
        System.out.println("=== Advanced Usage Examples ===");
        
        // Load all data
        List<Pokemon> allPokemon = xJsonBranch.loadAllPokemon("pokemon/sim/data/PokemonList.json");
        List<PokemonType> allTypes = xJson.loadPokemonTypes("pokemon/sim/data/PokemonType.json");
        
        // Analyze Pokemon by type
        analyzeByType(allPokemon, "Fire");
        analyzeByType(allPokemon, "Water");
        analyzeByType(allPokemon, "Electric");
        
        // Find dual-type Pokemon
        List<Pokemon> dualTypes = allPokemon.stream()
            .filter(p -> p.getTypes().size() >= 2)
            .collect(Collectors.toList());
        System.out.println("\nDual-type Pokemon: " + dualTypes.size());
        
        // Show a few dual-type examples
        dualTypes.stream()
            .limit(5)
            .forEach(p -> System.out.println("  " + p.getName() + " - " + p.getTypes()));
        
        // Type effectiveness analysis
        System.out.println("\nType Effectiveness Analysis:");
        allTypes.stream()
            .filter(t -> t.getName().equals("Electric"))
            .findFirst()
            .ifPresent(electricType -> {
                System.out.println("Electric type:");
                System.out.println("  Super effective against: " + 
                    findSuperEffectiveTargets(electricType, allTypes));
                System.out.println("  Weak to: " + electricType.getWeaknesses());
                System.out.println("  Resists: " + electricType.getResistances());
            });
        
        System.out.println();
    }
    
    /**
     * Analyze Pokemon of a specific type
     */
    private void analyzeByType(List<Pokemon> allPokemon, String typeName) {
        List<Pokemon> typeList = allPokemon.stream()
            .filter(p -> p.getTypes().contains(typeName))
            .collect(Collectors.toList());
            
        System.out.println(typeName + "-type Pokemon: " + typeList.size());
        
        // Show first few examples
        typeList.stream()
            .limit(3)
            .forEach(p -> System.out.println("  " + p.getName() + " (#" + p.getNationalNumber() + ")"));
    }
    
    /**
     * Find types that this type is super effective against
     */
    private List<String> findSuperEffectiveTargets(PokemonType attackingType, List<PokemonType> allTypes) {
        return allTypes.stream()
            .filter(defendingType -> defendingType.getWeaknesses().contains(attackingType.getName()))
            .map(PokemonType::getName)
            .collect(Collectors.toList());
    }
    
    /**
     * Example of loading Pokemon in a range
     */
    public void loadPokemonRange(int start, int end) throws IOException {
        System.out.println("Loading Pokemon #" + start + " to #" + end + ":");
        
        for (int i = start; i <= end; i++) {
            try {
                String fileName = String.format("%03d.json", i);
                String includePath = "/pokemon/sim/data/pokemon/" + fileName;
                Pokemon pokemon = xJsonBranch.loadPokemonByInclude(includePath);
                System.out.println("  #" + pokemon.getNationalNumber() + ": " + pokemon.getName());
            } catch (IOException e) {
                System.out.println("  #" + String.format("%03d", i) + ": [File not found]");
            }
        }
    }
    
    /**
     * Example of Pokemon team building
     */
    public void buildSampleTeam() throws IOException {
        System.out.println("=== Building Sample Team ===");
        
        // Define a sample team by Pokemon numbers
        int[] teamNumbers = {1, 25, 6, 9, 65, 144}; // Bulbasaur, Pikachu, Charizard, Blastoise, Alakazam, Articuno
        
        for (int number : teamNumbers) {
            try {
                String fileName = String.format("%03d.json", number);
                String includePath = "/pokemon/sim/data/pokemon/" + fileName;
                Pokemon pokemon = xJsonBranch.loadPokemonByInclude(includePath);
                
                System.out.println(pokemon.getName() + " (" + String.join("/", pokemon.getTypes()) + ")");
                if (!pokemon.getStats().isEmpty()) {
                    Stats stats = pokemon.getStats().get(0);
                    int total = stats.getHp() + stats.getAtk() + stats.getDef() + 
                              stats.getSpAtk() + stats.getSpDef() + stats.getSpeed();
                    System.out.println("  Base Stat Total: " + total);
                }
            } catch (IOException e) {
                System.out.println("Could not load Pokemon #" + number + ": " + e.getMessage());
            }
        }
    }
}
