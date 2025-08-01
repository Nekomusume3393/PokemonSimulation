/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon.sim.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.type.TypeReference;
import pokemon.sim.entity.Pokemon;
import pokemon.sim.entity.PokemonType;
import pokemon.sim.entity.Move;
import pokemon.sim.entity.Stats;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Utility class for handling JSON file operations and parsing Supports loading
 * JSON files and converting them to entity objects
 *
 * @author May5th
 */
public class XJson {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    
    
    /**
     * Get the ObjectMapper instance for external use
     * @return the ObjectMapper instance
     */
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
    private String basePath;
    
    /**
     * Constructor with base path for JSON files
     * @param basePath the base directory path where JSON files are located
     */
    public XJson(String basePath) {
        this.basePath = basePath;
    }
    
    /**
     * Default constructor - uses current directory as base path
     */
    public XJson() {
        this.basePath = "";
    }
    
    /**
     * Load and parse a JSON file into a JsonNode
     * @param filePath the path to the JSON file
     * @return JsonNode representation of the JSON file
     * @throws IOException if file cannot be read or parsed
     */
    public JsonNode loadJsonNode(String filePath) throws IOException {
        InputStream inputStream = null;
        
        // First try: Load from resources with exact path
        inputStream = tryLoadFromResources(filePath);
        if (inputStream != null) {
            return objectMapper.readTree(inputStream);
        }
        
        // Second try: Load from filesystem with base path
        String fullPath = basePath.isEmpty() ? filePath : basePath + File.separator + filePath;
        File file = new File(fullPath);
        if (file.exists()) {
            return objectMapper.readTree(file);
        }
        
        // Third try: Try alternative resource paths based on include paths
        inputStream = tryAlternativeResourcePaths(filePath);
        if (inputStream != null) {
            return objectMapper.readTree(inputStream);
        }
        
        // Fourth try: Load from filesystem in current directory
        file = new File(new File(filePath).getName());
        if (file.exists()) {
            return objectMapper.readTree(file);
        }
        
        throw new IOException("File not found: " + filePath + " (tried resources and filesystem paths)");
    }
    
    /**
     * Try to load a file from resources
     * @param filePath the file path
     * @return InputStream if found, null otherwise
     */
    private InputStream tryLoadFromResources(String filePath) {
        // Clean the path for resources (always use forward slashes)
        String resourcePath = filePath.replace('\\', '/');
        
        // Remove leading slash if present
        if (resourcePath.startsWith("/")) {
            resourcePath = resourcePath.substring(1);
        }
        
        return getClass().getClassLoader().getResourceAsStream(resourcePath);
    }
    
    /**
     * Try alternative resource paths for files referenced in $include
     * @param originalPath the original file path from $include
     * @return InputStream if found, null otherwise
     */
    private InputStream tryAlternativeResourcePaths(String originalPath) {
        if (originalPath == null) return null;
        
        // Extract filename from path
        String fileName = new File(originalPath).getName();
        
        // List of possible resource paths to try
        String[] possiblePaths = {
            // Direct filename
            fileName,
            
            // All data directory paths
            "pokemon/sim/data/pokemon/" + fileName,
            "pokemon/sim/data/move/" + fileName,
            "pokemon/sim/data/item/" + fileName,
            "pokemon/sim/data/trainer/" + fileName,
            "pokemon/sim/data/" + fileName,  // For PokemonType.json and other config files
            
            // Without leading directories from $include paths
            "pokemon/" + fileName,
            "move/" + fileName,
            "item/" + fileName,
            "trainer/" + fileName,
            
            // Special handling for known files
            originalPath.equals("PokemonList.json") ? "pokemon/sim/data/PokemonList.json" : null,
            originalPath.equals("PokemonType.json") ? "pokemon/sim/data/PokemonType.json" : null,
            originalPath.equals("ItemList.json") ? "pokemon/sim/data/ItemList.json" : null,
            originalPath.equals("MoveList.json") ? "pokemon/sim/data/MoveList.json" : null,
            originalPath.equals("TrainerList.json") ? "pokemon/sim/data/TrainerList.json" : null,
            
            // Extract path components and try different combinations
            originalPath.startsWith("/") ? originalPath.substring(1) : originalPath
        };
        
        for (String path : possiblePaths) {
            if (path != null) {
                InputStream inputStream = tryLoadFromResources(path);
                if (inputStream != null) {
                    return inputStream;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Load and parse a JSON file into a specified class type
     * @param <T> the type to parse the JSON into
     * @param filePath the path to the JSON file
     * @param valueType the class type to parse into
     * @return object of type T parsed from JSON
     * @throws IOException if file cannot be read or parsed
     */
    public <T> T loadJson(String filePath, Class<T> valueType) throws IOException {
        JsonNode jsonNode = loadJsonNode(filePath);
        return objectMapper.treeToValue(jsonNode, valueType);
    }
    
    /**
     * Load and parse a JSON file into a List of specified type
     * @param <T> the type of objects in the list
     * @param filePath the path to the JSON file
     * @param valueType the class type of list elements
     * @return List of objects of type T
     * @throws IOException if file cannot be read or parsed
     */
    public <T> List<T> loadJsonList(String filePath, Class<T> valueType) throws IOException {
        JsonNode jsonNode = loadJsonNode(filePath);
        return objectMapper.convertValue(jsonNode, 
            objectMapper.getTypeFactory().constructCollectionType(List.class, valueType));
    }
    
    /**
     * Load a Pokemon from JSON file
     * @param filePath path to the Pokemon JSON file
     * @return Pokemon object
     * @throws IOException if file cannot be read or parsed
     */
    public Pokemon loadPokemon(String filePath) throws IOException {
        JsonNode jsonNode = loadJsonNode(filePath);
        
        Pokemon pokemon = new Pokemon();
        pokemon.setName(jsonNode.get("name").asText());
        pokemon.setNationalNumber(jsonNode.get("national_number").asText());
        pokemon.setSpecies(jsonNode.get("species").asText());
        pokemon.setHeight((float) jsonNode.get("height").asDouble());
        pokemon.setWeight((float) jsonNode.get("weight").asDouble());
        
        // Handle image list
        JsonNode imageNode = jsonNode.get("image");
        List<String> images = new ArrayList<>();
        if (imageNode.isArray()) {
            for (JsonNode img : imageNode) {
                images.add(img.asText());
            }
        }
        pokemon.setImage(images);
        
        // Handle type list
        JsonNode typeNode = jsonNode.get("type");
        List<String> types = new ArrayList<>();
        if (typeNode.isArray()) {
            for (JsonNode type : typeNode) {
                types.add(type.asText());
            }
        }
        pokemon.setTypes(types);
        
        // Handle stats
        JsonNode statsNode = jsonNode.get("stats");
        List<Stats> statsList = new ArrayList<>();
        if (statsNode.isArray()) {
            for (JsonNode stat : statsNode) {
                Stats stats = new Stats(
                    stat.get("hp").asInt(),
                    stat.get("atk").asInt(),
                    stat.get("def").asInt(),
                    stat.get("sp-atk").asInt(),
                    stat.get("sp-def").asInt(),
                    stat.get("speed").asInt()
                );
                statsList.add(stats);
            }
        }
        pokemon.setStats(statsList);
        
        return pokemon;
    }
    
    /**
     * Load a Move from JSON file
     * @param filePath path to the Move JSON file
     * @return Move object
     * @throws IOException if file cannot be read or parsed
     */
    public Move loadMove(String filePath) throws IOException {
        JsonNode jsonNode = loadJsonNode(filePath);
        
        return new Move(
            jsonNode.get("name").asText(),
            jsonNode.get("type").asText(),
            jsonNode.get("category").asText(),
            jsonNode.get("power").asInt(),
            jsonNode.get("accuracy").asInt(),
            jsonNode.get("pp").asInt(),
            jsonNode.get("effect").asText()
        );
    }
    
    /**
     * Load Pokemon types from JSON file
     * @param filePath path to the PokemonType JSON file
     * @return List of PokemonType objects
     * @throws IOException if file cannot be read or parsed
     */
    public List<PokemonType> loadPokemonTypes(String filePath) throws IOException {
        return loadJsonList(filePath, PokemonType.class);
    }
    
    /**
     * Check if a JsonNode contains an $include reference
     * @param jsonNode the JsonNode to check
     * @return true if the node contains an $include field
     */
    public boolean hasInclude(JsonNode jsonNode) {
        return jsonNode.has("$include");
    }
    
    /**
     * Get the $include path from a JsonNode
     * @param jsonNode the JsonNode containing the $include
     * @return the path specified in the $include field
     */
    public String getIncludePath(JsonNode jsonNode) {
        if (hasInclude(jsonNode)) {
            return jsonNode.get("$include").asText();
        }
        return null;
    }
    
    /**
     * Set the base path for JSON file loading
     * @param basePath the base directory path
     */
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
    
    /**
     * Get the current base path
     * @return the current base path
     */
    public String getBasePath() {
        return basePath;
    }
}
