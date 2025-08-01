/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon.sim.util;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author May5th
 */
public class XImage {
    
    private static final Logger logger = LoggerFactory.getLogger(XImage.class);
    private static final Map<String, BufferedImage> cache = new HashMap<>();
    private static final Map<String, BufferedImage> userProfileCache = new HashMap<>();
    
    // Resource paths
    private static final String POKEMON_IMAGE_PATH = "/pokemon/sim/image/pokemon/";
    private static final String TRAINER_IMAGE_PATH = "/pokemon/sim/image/trainer/";
    
    /**
     * Image type enumeration for better organization
     */
    public enum ImageType {
        POKEMON, TRAINER, USER_PROFILE
    }
    
    /**
     * Load Pokemon image from JSON data
     * @param pokemonJson JsonNode containing Pokemon data
     * @param imageIndex index of the image in the image array (0 for front, 1 for back, etc.)
     * @return BufferedImage or null if failed
     */
    public static BufferedImage getPokemonImage(JsonNode pokemonJson, int imageIndex) {
        try {
            JsonNode imageNode = pokemonJson.get("image");
            if (imageNode == null || !imageNode.isArray()) {
                logger.warn("[XImage] No image array found in Pokemon JSON");
                return null;
            }
            
            if (imageIndex >= imageNode.size()) {
                logger.warn("[XImage] Image index {} out of bounds for Pokemon with {} images", 
                           imageIndex, imageNode.size());
                return null;
            }
            
            String imageName = imageNode.get(imageIndex).asText();
            return getPokemonImageByName(imageName);
            
        } catch (Exception e) {
            logger.error("[XImage] Failed to load Pokemon image from JSON", e);
            return null;
        }
    }
    
    /**
     * Load Pokemon front image (convenience method)
     * @param pokemonJson JsonNode containing Pokemon data
     * @return BufferedImage or null if failed
     */
    public static BufferedImage getPokemonFrontImage(JsonNode pokemonJson) {
        return getPokemonImage(pokemonJson, 0);
    }
    
    /**
     * Load Pokemon back image (convenience method)
     * @param pokemonJson JsonNode containing Pokemon data
     * @return BufferedImage or null if failed
     */
    public static BufferedImage getPokemonBackImage(JsonNode pokemonJson) {
        return getPokemonImage(pokemonJson, 1);
    }
    
    /**
     * Load all Pokemon images from JSON data
     * @param pokemonJson JsonNode containing Pokemon data
     * @return List of BufferedImage objects
     */
    public static List<BufferedImage> getAllPokemonImages(JsonNode pokemonJson) {
        List<BufferedImage> images = new ArrayList<>();
        try {
            JsonNode imageNode = pokemonJson.get("image");
            if (imageNode == null || !imageNode.isArray()) {
                logger.warn("[XImage] No image array found in Pokemon JSON");
                return images;
            }
            
            for (int i = 0; i < imageNode.size(); i++) {
                String imageName = imageNode.get(i).asText();
                BufferedImage img = getPokemonImageByName(imageName);
                if (img != null) {
                    images.add(img);
                }
            }
            
        } catch (Exception e) {
            logger.error("[XImage] Failed to load Pokemon images from JSON", e);
        }
        return images;
    }
    
    /**
     * Load Pokemon image by filename
     * @param imageName the image filename
     * @return BufferedImage or null if failed
     */
    public static BufferedImage getPokemonImageByName(String imageName) {
        String cacheKey = "pokemon_" + imageName;
        if (cache.containsKey(cacheKey)) return cache.get(cacheKey);
        
        try {
            String fullPath = POKEMON_IMAGE_PATH + imageName;
            URL url = XImage.class.getResource(fullPath);
            if (url == null) throw new IOException("Pokemon image not found: " + fullPath);
            
            BufferedImage img = ImageIO.read(url);
            cache.put(cacheKey, img);
            return img;
            
        } catch (IOException e) {
            logger.error("[XImage] Failed to load Pokemon image: " + imageName, e);
            return null;
        }
    }
    
    /**
     * Load Trainer image from JSON data
     * @param trainerJson JsonNode containing Trainer data
     * @return BufferedImage or null if failed
     */
    public static BufferedImage getTrainerImage(JsonNode trainerJson) {
        try {
            JsonNode imageNode = trainerJson.get("image");
            if (imageNode == null) {
                logger.warn("[XImage] No image field found in Trainer JSON");
                return null;
            }
            
            String imageName = imageNode.asText();
            return getTrainerImageByName(imageName);
            
        } catch (Exception e) {
            logger.error("[XImage] Failed to load Trainer image from JSON", e);
            return null;
        }
    }
    
    /**
     * Load Trainer image by filename
     * @param imageName the image filename
     * @return BufferedImage or null if failed
     */
    public static BufferedImage getTrainerImageByName(String imageName) {
        String cacheKey = "trainer_" + imageName;
        if (cache.containsKey(cacheKey)) return cache.get(cacheKey);
        
        try {
            String fullPath = TRAINER_IMAGE_PATH + imageName;
            URL url = XImage.class.getResource(fullPath);
            if (url == null) throw new IOException("Trainer image not found: " + fullPath);
            
            BufferedImage img = ImageIO.read(url);
            cache.put(cacheKey, img);
            return img;
            
        } catch (IOException e) {
            logger.error("[XImage] Failed to load Trainer image: " + imageName, e);
            return null;
        }
    }
    
    /**
     * Load an image directly from an external file
     * @param file the image file
     * @return BufferedImage or null if failed
     */
    public static BufferedImage fromFile(File file) {
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            logger.error("[XImage] Failed to read image from file: " + file.getAbsolutePath());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Load and cache user profile picture from file
     * @param file the profile picture file
     * @param userId unique identifier for the user
     * @return BufferedImage or null if failed
     */
    public static BufferedImage loadUserProfile(File file, String userId) {
        if (userProfileCache.containsKey(userId)) {
            return userProfileCache.get(userId);
        }
        
        try {
            BufferedImage img = ImageIO.read(file);
            if (img != null) {
                userProfileCache.put(userId, img);
                logger.info("[XImage] Successfully loaded profile picture for user: " + userId);
            }
            return img;
        } catch (IOException e) {
            logger.error("[XImage] Failed to load profile picture for user: " + userId, e);
            return null;
        }
    }
    
    /**
     * Update user profile picture
     * @param file the new profile picture file
     * @param userId unique identifier for the user
     * @return true if successful, false otherwise
     */
    public static boolean updateUserProfile(File file, String userId) {
        try {
            BufferedImage img = ImageIO.read(file);
            if (img != null) {
                userProfileCache.put(userId, img);
                logger.info("[XImage] Successfully updated profile picture for user: " + userId);
                return true;
            }
        } catch (IOException e) {
            logger.error("[XImage] Failed to update profile picture for user: " + userId, e);
        }
        return false;
    }
    
    /**
     * Get cached user profile picture
     * @param userId unique identifier for the user
     * @return BufferedImage or null if not found
     */
    public static BufferedImage getUserProfile(String userId) {
        return userProfileCache.get(userId);
    }
    
    /**
     * Remove user profile picture from cache
     * @param userId unique identifier for the user
     * @return true if removed, false if not found
     */
    public static boolean removeUserProfile(String userId) {
        return userProfileCache.remove(userId) != null;
    }
    
    /**
     * Load image based on type and identifier
     * @param type the type of image to load
     * @param identifier the identifier (filename, JSON node, user ID, etc.)
     * @return BufferedImage or null if failed
     */
    public static BufferedImage loadByType(ImageType type, Object identifier) {
        switch (type) {
            case POKEMON:
                if (identifier instanceof String) {
                    return getPokemonImageByName((String) identifier);
                } else if (identifier instanceof JsonNode) {
                    return getPokemonFrontImage((JsonNode) identifier);
                }
                break;
                
            case TRAINER:
                if (identifier instanceof String) {
                    return getTrainerImageByName((String) identifier);
                } else if (identifier instanceof JsonNode) {
                    return getTrainerImage((JsonNode) identifier);
                }
                break;
                
            case USER_PROFILE:
                if (identifier instanceof String) {
                    return getUserProfile((String) identifier);
                }
                break;
        }
        
        logger.warn("[XImage] Invalid type/identifier combination: {} / {}", type, identifier);
        return null;
    }
    
    /**
     * Clear all cached images
     */
    public static void clearCache() {
        cache.clear();
        userProfileCache.clear();
        logger.info("[XImage] All caches cleared");
    }
    
    /**
     * Clear specific cache type
     * @param type the type of cache to clear
     */
    public static void clearCache(ImageType type) {
        switch (type) {
            case USER_PROFILE:
                userProfileCache.clear();
                logger.info("[XImage] User profile cache cleared");
                break;
            default:
                // For other types, clear main cache entries with specific prefixes
                cache.entrySet().removeIf(entry -> {
                    String key = entry.getKey();
                    switch (type) {
                        case POKEMON:
                            return key.startsWith("pokemon_");
                        case TRAINER:
                            return key.startsWith("trainer_");
                        default:
                            return false;
                    }
                });
                logger.info("[XImage] {} cache cleared", type);
                break;
        }
    }
    
    /**
     * Get cache statistics
     * @return String containing cache information
     */
    public static String getCacheInfo() {
        int pokemonCount = (int) cache.keySet().stream().filter(k -> k.startsWith("pokemon_")).count();
        int trainerCount = (int) cache.keySet().stream().filter(k -> k.startsWith("trainer_")).count();
        int profileCount = userProfileCache.size();
        
        return String.format("Cache Info - Pokemon: %d, Trainer: %d, Profiles: %d, Total: %d", 
            pokemonCount, trainerCount, profileCount, cache.size() + profileCount);
    }
    
    /**
     * Resize image (for UI preview)
     * @param img the image to resize
     * @param w target width
     * @param h target height
     * @return resized Image or null if input is null
     */
    public static Image resize(BufferedImage img, int w, int h) {
        return img == null ? null : img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
    }
    
    /**
     * Create a square thumbnail from an image (useful for profile pictures)
     * @param img the source image
     * @param size the size of the square thumbnail
     * @return resized square Image or null if input is null
     */
    public static Image createSquareThumbnail(BufferedImage img, int size) {
        if (img == null) return null;
        
        int width = img.getWidth();
        int height = img.getHeight();
        int cropSize = Math.min(width, height);
        int x = (width - cropSize) / 2;
        int y = (height - cropSize) / 2;
        
        // Create square crop
        BufferedImage cropped = img.getSubimage(x, y, cropSize, cropSize);
        return cropped.getScaledInstance(size, size, Image.SCALE_SMOOTH);
    }
    
    /**
     * Check if an image exists in resources
     * @param type the type of image
     * @param imageName the image filename
     * @return true if image exists, false otherwise
     */
    public static boolean imageExists(ImageType type, String imageName) {
        String path;
        switch (type) {
            case POKEMON:
                path = POKEMON_IMAGE_PATH + imageName;
                break;
            case TRAINER:
                path = TRAINER_IMAGE_PATH + imageName;
                break;
            default:
                return false;
        }
        
        URL url = XImage.class.getResource(path);
        return url != null;
    }
}
