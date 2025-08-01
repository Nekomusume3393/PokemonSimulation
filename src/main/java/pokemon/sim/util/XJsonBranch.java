/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon.sim.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.File;
import pokemon.sim.entity.Pokemon;
import pokemon.sim.entity.PokemonType;
import pokemon.sim.entity.Move;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * Utility class for handling JSON files with $include references
 * Allows traversal from one JSON file to another through $include directives
 * Provides caching to avoid reloading the same files multiple times
 * @author May5th
 */
public class XJsonBranch {

    private final XJson xJson;
    private final Map<String, JsonNode> nodeCache;
    private final Map<String, Pokemon> pokemonCache;
    private final Map<String, Move> moveCache;
    private final Map<String, JsonNode> itemCache;
    private final Map<String, JsonNode> trainerCache;
    private final Set<String> loadingPaths; // To prevent circular includes
    
    /**
     * Constructor with XJson instance
     * @param xJson the XJson instance to use for file operations
     */
    public XJsonBranch(XJson xJson) {
        this.xJson = xJson;
        this.nodeCache = new HashMap<>();
        this.pokemonCache = new HashMap<>();
        this.moveCache = new HashMap<>();
        this.itemCache = new HashMap<>();
        this.trainerCache = new HashMap<>();
        this.loadingPaths = new HashSet<>();
    }
    
    /**
     * Default constructor - creates new XJson instance
     */
    public XJsonBranch() {
        this(new XJson());
    }
    
    /**
     * Constructor with base path
     * @param basePath the base directory path for JSON files
     */
    public XJsonBranch(String basePath) {
        this(new XJson(basePath));
    }
    
    /**
     * Load a JSON file and resolve all $include references
     * @param filePath the path to the JSON file
     * @return JsonNode with all includes resolved
     * @throws IOException if file cannot be read or circular reference detected
     */
    public JsonNode loadWithIncludes(String filePath) throws IOException {
        // Check for circular references
        if (loadingPaths.contains(filePath)) {
            throw new IOException("Circular include reference detected: " + filePath);
        }
        
        // Check cache first
        if (nodeCache.containsKey(filePath)) {
            return nodeCache.get(filePath);
        }
        
        loadingPaths.add(filePath);
        
        try {
            JsonNode jsonNode = xJson.loadJsonNode(filePath);
            JsonNode resolvedNode = resolveIncludes(jsonNode);
            
            // Cache the resolved node
            nodeCache.put(filePath, resolvedNode);
            
            return resolvedNode;
        } finally {
            loadingPaths.remove(filePath);
        }
    }
    
    /**
     * Recursively resolve $include references in a JsonNode
     * @param jsonNode the JsonNode to process
     * @return JsonNode with includes resolved
     * @throws IOException if included files cannot be loaded
     */
    private JsonNode resolveIncludes(JsonNode jsonNode) throws IOException {
        if (jsonNode.isArray()) {
            return resolveArrayIncludes(jsonNode);
        } else if (jsonNode.isObject() && xJson.hasInclude(jsonNode)) {
            return resolveSingleInclude(jsonNode);
        } else if (jsonNode.isObject()) {
            return resolveObjectIncludes(jsonNode);
        }
        
        return jsonNode; // Return as-is if no includes to resolve
    }
    
    /**
     * Resolve includes in an array JsonNode
     * @param arrayNode the array JsonNode
     * @return resolved array JsonNode
     * @throws IOException if included files cannot be loaded
     */
    private JsonNode resolveArrayIncludes(JsonNode arrayNode) throws IOException {
        List<JsonNode> resolvedNodes = new ArrayList<>();
        
        for (JsonNode element : arrayNode) {
            if (element.isObject() && xJson.hasInclude(element)) {
                // Load the included file
                String includePath = xJson.getIncludePath(element);
                JsonNode includedNode = loadWithIncludes(includePath);
                resolvedNodes.add(includedNode);
            } else {
                // Recursively resolve any nested includes
                resolvedNodes.add(resolveIncludes(element));
            }
        }
        
        // Convert back to JsonNode array
        return xJson.getObjectMapper().valueToTree(resolvedNodes);
    }
    
    /**
     * Resolve a single include reference
     * @param includeNode the JsonNode containing the $include
     * @return the resolved JsonNode from the included file
     * @throws IOException if included file cannot be loaded
     */
    private JsonNode resolveSingleInclude(JsonNode includeNode) throws IOException {
        String includePath = xJson.getIncludePath(includeNode);
        return loadWithIncludes(includePath);
    }
    
    /**
     * Resolve includes in an object JsonNode
     * @param objectNode the object JsonNode
     * @return resolved object JsonNode
     * @throws IOException if included files cannot be loaded
     */
    private JsonNode resolveObjectIncludes(JsonNode objectNode) throws IOException {
        Map<String, JsonNode> resolvedObject = new HashMap<>();
        
        objectNode.fields().forEachRemaining(entry -> {
            try {
                resolvedObject.put(entry.getKey(), resolveIncludes(entry.getValue()));
            } catch (IOException e) {
                throw new RuntimeException("Failed to resolve includes in object", e);
            }
        });
        
        return xJson.getObjectMapper().valueToTree(resolvedObject);
    }
    
    /**
     * Load all Pokemon from PokemonList.json with includes resolved
     * @param pokemonListPath path to the PokemonList.json file
     * @return List of Pokemon objects
     * @throws IOException if files cannot be loaded
     */
    public List<Pokemon> loadAllPokemon(String pokemonListPath) throws IOException {
        JsonNode pokemonListNode = loadWithIncludes(pokemonListPath);
        List<Pokemon> pokemonList = new ArrayList<>();
        
        if (pokemonListNode.isArray()) {
            for (JsonNode pokemonNode : pokemonListNode) {
                Pokemon pokemon = parsePokemonFromNode(pokemonNode);
                pokemonList.add(pokemon);
            }
        }
        
        return pokemonList;
    }
    
    /**
     * Load all Items from ItemList.json with includes resolved
     * @param itemListPath path to the ItemList.json file
     * @return List of Item JsonNodes
     * @throws IOException if files cannot be loaded
     */
    public List<JsonNode> loadAllItems(String itemListPath) throws IOException {
        JsonNode itemListNode = loadWithIncludes(itemListPath);
        List<JsonNode> itemList = new ArrayList<>();
        
        if (itemListNode.isArray()) {
            for (JsonNode itemNode : itemListNode) {
                itemList.add(itemNode);
            }
        }
        
        return itemList;
    }
    
    /**
     * Load all Trainers from TrainerList.json with includes resolved
     * @param trainerListPath path to the TrainerList.json file
     * @return List of Trainer JsonNodes
     * @throws IOException if files cannot be loaded
     */
    public List<JsonNode> loadAllTrainers(String trainerListPath) throws IOException {
        JsonNode trainerListNode = loadWithIncludes(trainerListPath);
        List<JsonNode> trainerList = new ArrayList<>();
        
        if (trainerListNode.isArray()) {
            for (JsonNode trainerNode : trainerListNode) {
                trainerList.add(trainerNode);
            }
        }
        
        return trainerList;
    }
    
    /**
     * Load all Moves from MoveList.json with includes resolved
     * @param moveListPath path to the MoveList.json file
     * @return List of Move objects
     * @throws IOException if files cannot be loaded
     */
    public List<Move> loadAllMoves(String moveListPath) throws IOException {
        JsonNode moveListNode = loadWithIncludes(moveListPath);
        List<Move> moveList = new ArrayList<>();
        
        if (moveListNode.isArray()) {
            for (JsonNode moveNode : moveListNode) {
                Move move = parseMoveFromNode(moveNode);
                moveList.add(move);
            }
        }
        
        return moveList;
    }
    
    /**
     * Load a specific Pokemon by its include path
     * @param pokemonIncludePath the $include path to the Pokemon JSON
     * @return Pokemon object
     * @throws IOException if file cannot be loaded
     */
    public Pokemon loadPokemonByInclude(String pokemonIncludePath) throws IOException {
        // Check cache first
        if (pokemonCache.containsKey(pokemonIncludePath)) {
            return pokemonCache.get(pokemonIncludePath);
        }
        
        // Convert include path to actual resource path
        String resourcePath = convertIncludePathToResourcePath(pokemonIncludePath, "pokemon");
        
        JsonNode pokemonNode = loadWithIncludes(resourcePath);
        Pokemon pokemon = parsePokemonFromNode(pokemonNode);
        
        // Cache the pokemon
        pokemonCache.put(pokemonIncludePath, pokemon);
        
        return pokemon;
    }
    
    /**
     * Load a specific Move by its include path
     * @param moveIncludePath the $include path to the Move JSON
     * @return Move object
     * @throws IOException if file cannot be loaded
     */
    public Move loadMoveByInclude(String moveIncludePath) throws IOException {
        // Check cache first
        if (moveCache.containsKey(moveIncludePath)) {
            return moveCache.get(moveIncludePath);
        }
        
        // Convert include path to actual resource path
        String resourcePath = convertIncludePathToResourcePath(moveIncludePath, "move");
        
        JsonNode moveNode = loadWithIncludes(resourcePath);
        Move move = parseMoveFromNode(moveNode);
        
        // Cache the move
        moveCache.put(moveIncludePath, move);
        
        return move;
    }
    
    /**
     * Load a specific Item by its include path
     * @param itemIncludePath the $include path to the Item JSON
     * @return Item JsonNode
     * @throws IOException if file cannot be loaded
     */
    public JsonNode loadItemByInclude(String itemIncludePath) throws IOException {
        // Check cache first
        if (itemCache.containsKey(itemIncludePath)) {
            return itemCache.get(itemIncludePath);
        }
        
        // Convert include path to actual resource path
        String resourcePath = convertIncludePathToResourcePath(itemIncludePath, "item");
        
        JsonNode itemNode = loadWithIncludes(resourcePath);
        
        // Cache the item
        itemCache.put(itemIncludePath, itemNode);
        
        return itemNode;
    }
    
    /**
     * Load a specific Trainer by its include path
     * @param trainerIncludePath the $include path to the Trainer JSON
     * @return Trainer JsonNode
     * @throws IOException if file cannot be loaded
     */
    public JsonNode loadTrainerByInclude(String trainerIncludePath) throws IOException {
        // Check cache first
        if (trainerCache.containsKey(trainerIncludePath)) {
            return trainerCache.get(trainerIncludePath);
        }
        
        // Convert include path to actual resource path
        String resourcePath = convertIncludePathToResourcePath(trainerIncludePath, "trainer");
        
        JsonNode trainerNode = loadWithIncludes(resourcePath);
        
        // Cache the trainer
        trainerCache.put(trainerIncludePath, trainerNode);
        
        return trainerNode;
    }
    
    /**
     * Convert an $include path to the actual resource path
     * @param includePath the path from $include field
     * @param expectedType expected type ("pokemon", "move", "item", "trainer", etc.)
     * @return converted resource path
     */
    private String convertIncludePathToResourcePath(String includePath, String expectedType) {
        if (includePath == null) return null;
        
        String fileName = new File(includePath).getName();
        
        // Based on the 5-directory structure
        switch (expectedType.toLowerCase()) {
            case "pokemon":
                return "pokemon/sim/data/pokemon/" + fileName;
            case "move":
                return "pokemon/sim/data/move/" + fileName;
            case "item":
                return "pokemon/sim/data/item/" + fileName;
            case "trainer":
                return "pokemon/sim/data/trainer/" + fileName;
            case "data":
            case "config":
                return "pokemon/sim/data/" + fileName;
            default:
                // Auto-detect from the include path
                if (includePath.contains("/pokemon/")) {
                    return "pokemon/sim/data/pokemon/" + fileName;
                } else if (includePath.contains("/move/")) {
                    return "pokemon/sim/data/move/" + fileName;
                } else if (includePath.contains("/item/")) {
                    return "pokemon/sim/data/item/" + fileName;
                } else if (includePath.contains("/trainer/")) {
                    return "pokemon/sim/data/trainer/" + fileName;
                } else if (includePath.contains("/data/")) {
                    return "pokemon/sim/data/" + fileName;
                } else {
                    // Try to detect by file name patterns
                    String lowerFileName = fileName.toLowerCase();
                    if (lowerFileName.contains("pokemon")) {
                        return "pokemon/sim/data/pokemon/" + fileName;
                    } else if (lowerFileName.contains("move")) {
                        return "pokemon/sim/data/move/" + fileName;
                    } else if (lowerFileName.contains("item")) {
                        return "pokemon/sim/data/item/" + fileName;
                    } else if (lowerFileName.contains("trainer")) {
                        return "pokemon/sim/data/trainer/" + fileName;
                    } else if (lowerFileName.contains("type") || lowerFileName.contains("list")) {
                        return "pokemon/sim/data/" + fileName;
                    } else {
                        return fileName; // Fallback to just the filename
                    }
                }
        }
    }
    
    /**
     * Parse a Pokemon object from a JsonNode
     * @param jsonNode the JsonNode containing Pokemon data
     * @return Pokemon object
     */
    private Pokemon parsePokemonFromNode(JsonNode jsonNode) {
        try {
            return xJson.getObjectMapper().treeToValue(jsonNode, Pokemon.class);
        } catch (JsonProcessingException | IllegalArgumentException e) {
            // If Jackson auto-mapping fails, use manual parsing
            try {
                return parseManualPokemon(jsonNode);
            } catch (Exception ex) {
                throw new RuntimeException("Failed to parse Pokemon from JsonNode", ex);
            }
        }
    }
    
    /**
     * Manually parse Pokemon from JsonNode (fallback method)
     * @param jsonNode the JsonNode containing Pokemon data
     * @return Pokemon object
     */
    private Pokemon parseManualPokemon(JsonNode jsonNode) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(jsonNode.get("name").asText());
        pokemon.setNationalNumber(jsonNode.get("national_number").asText());
        pokemon.setSpecies(jsonNode.get("species").asText());
        pokemon.setHeight((float) jsonNode.get("height").asDouble());
        pokemon.setWeight((float) jsonNode.get("weight").asDouble());
        
        // Handle image and type arrays
        List<String> images = new ArrayList<>();
        JsonNode imageNode = jsonNode.get("image");
        if (imageNode.isArray()) {
            imageNode.forEach(img -> images.add(img.asText()));
        }
        pokemon.setImage(images);
        
        List<String> types = new ArrayList<>();
        JsonNode typeNode = jsonNode.get("type");
        if (typeNode.isArray()) {
            typeNode.forEach(type -> types.add(type.asText()));
        }
        pokemon.setTypes(types);
        
        return pokemon;
    }
    
    /**
     * Parse a Move object from a JsonNode
     * @param jsonNode the JsonNode containing Move data
     * @return Move object
     */
    private Move parseMoveFromNode(JsonNode jsonNode) {
        try {
            return xJson.getObjectMapper().treeToValue(jsonNode, Move.class);
        } catch (JsonProcessingException | IllegalArgumentException e) {
            throw new RuntimeException("Failed to parse Move from JsonNode", e);
        }
    }
    
    /**
     * Clear all caches
     */
    public void clearCache() {
        nodeCache.clear();
        pokemonCache.clear();
        moveCache.clear();
        itemCache.clear();
        trainerCache.clear();
    }
    
    /**
     * Get cache statistics
     * @return String containing cache information
     */
    public String getCacheInfo() {
        return String.format("Cache Info - Nodes: %d, Pokemon: %d, Moves: %d, Items: %d, Trainers: %d", 
            nodeCache.size(), pokemonCache.size(), moveCache.size(), itemCache.size(), trainerCache.size());
    }
    
    /**
     * Check if a file path is currently being loaded (for circular reference detection)
     * @param filePath the file path to check
     * @return true if the path is currently being loaded
     */
    public boolean isLoading(String filePath) {
        return loadingPaths.contains(filePath);
    }
    
    /**
     * Get the underlying XJson instance
     * @return the XJson instance
     */
    public XJson getXJson() {
        return xJson;
    }
}
