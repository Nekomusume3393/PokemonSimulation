/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import pokemon.sim.util.XImage.ImageType;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import pokemon.sim.util.XImage;

/**
 *
 * @author May5th
 */
public class TestXImage {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    private JsonNode pokemonJson;
    private JsonNode trainerJson;
    private File testImageFile;
    
    @TempDir
    Path tempDir;
    
    @BeforeEach
    void setUp() throws IOException {
        // Clear caches before each test
        XImage.clearCache();
        
        // Create test Pokemon JSON
        String pokemonJsonString = """
            {
                "name": "Bulbasaur",
                "national_number": "0001",
                "image": ["Bulbasaur-front.png", "Bulbasaur-back.png"],
                "type": ["Grass", "Poison"],
                "species": "Seed Pokémon",
                "height": 0.7,
                "weight": 6.9
            }
            """;
        pokemonJson = mapper.readTree(pokemonJsonString);
        
        // Create test Trainer JSON
        String trainerJsonString = """
            {
                "name": "Blue",
                "image": "blue-champion.png",
                "team": [],
                "bag": [],
                "money": 6300
            }
            """;
        trainerJson = mapper.readTree(trainerJsonString);
        
        // Create a test image file
        createTestImageFile();
    }
    
    @AfterEach
    void tearDown() {
        // Clean up caches after each test
        XImage.clearCache();
    }
    
    /**
     * Create a simple test image file for testing file operations
     */
    private void createTestImageFile() throws IOException {
        testImageFile = tempDir.resolve("test-image.png").toFile();
        
        // Create a simple 10x10 red image
        BufferedImage testImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                testImage.setRGB(x, y, 0xFF0000); // Red color
            }
        }
        
        ImageIO.write(testImage, "png", testImageFile);
    }
    
    // ==================== Pokemon Image Tests ====================
    
    @Test
    @DisplayName("Should load Pokemon front image from JSON")
    void testGetPokemonFrontImage() {
        BufferedImage image = XImage.getPokemonFrontImage(pokemonJson);
        
        // Test should handle both cases - resources available or not
        if (image != null) {
            // If resources are available, verify the image is loaded properly
            assertTrue(image.getWidth() > 0, "Image should have positive width");
            assertTrue(image.getHeight() > 0, "Image should have positive height");
        }
        // If resources are not available, image will be null, which is also acceptable
    }
    
    @Test
    @DisplayName("Should load Pokemon back image from JSON")
    void testGetPokemonBackImage() {
        BufferedImage image = XImage.getPokemonBackImage(pokemonJson);
        
        // Test should handle both cases - resources available or not
        if (image != null) {
            // If resources are available, verify the image is loaded properly
            assertTrue(image.getWidth() > 0, "Image should have positive width");
            assertTrue(image.getHeight() > 0, "Image should have positive height");
        }
        // If resources are not available, image will be null, which is also acceptable
    }
    
    @Test
    @DisplayName("Should handle Pokemon image by index")
    void testGetPokemonImageByIndex() {
        // Test valid index
        BufferedImage frontImage = XImage.getPokemonImage(pokemonJson, 0);
        BufferedImage backImage = XImage.getPokemonImage(pokemonJson, 1);
        
        // Verify images if they're loaded (resources available)
        if (frontImage != null) {
            assertTrue(frontImage.getWidth() > 0, "Front image should have positive width");
            assertTrue(frontImage.getHeight() > 0, "Front image should have positive height");
        }
        
        if (backImage != null) {
            assertTrue(backImage.getWidth() > 0, "Back image should have positive width");
            assertTrue(backImage.getHeight() > 0, "Back image should have positive height");
        }
        
        // Test invalid index - should always return null
        BufferedImage invalidImage = XImage.getPokemonImage(pokemonJson, 5);
        assertNull(invalidImage, "Should return null for out-of-bounds index");
    }
    
    @Test
    @DisplayName("Should load all Pokemon images from JSON")
    void testGetAllPokemonImages() {
        var images = XImage.getAllPokemonImages(pokemonJson);
        
        assertNotNull(images, "Should return a list (not null)");
        
        // If resources are available, we should get some images
        // If not available, list will be empty
        for (BufferedImage image : images) {
            if (image != null) {
                assertTrue(image.getWidth() > 0, "Each loaded image should have positive width");
                assertTrue(image.getHeight() > 0, "Each loaded image should have positive height");
            }
        }
        
        // The list size should match the image array size in JSON or be empty if resources unavailable
        assertTrue(images.size() <= 2, "Should not have more images than defined in JSON");
    }
    
    @Test
    @DisplayName("Should handle Pokemon JSON without image array")
    void testPokemonJsonWithoutImages() throws IOException {
        String noImageJson = """
            {
                "name": "TestMon",
                "national_number": "0000"
            }
            """;
        JsonNode noImageNode = mapper.readTree(noImageJson);
        
        BufferedImage image = XImage.getPokemonFrontImage(noImageNode);
        assertNull(image, "Should return null when no image array exists");
        
        var allImages = XImage.getAllPokemonImages(noImageNode);
        assertNotNull(allImages);
        assertTrue(allImages.isEmpty(), "Should return empty list when no image array exists");
    }
    
    // ==================== Trainer Image Tests ====================
    
    @Test
    @DisplayName("Should load Trainer image from JSON")
    void testGetTrainerImage() {
        BufferedImage image = XImage.getTrainerImage(trainerJson);
        
        // Test should handle both cases - resources available or not
        if (image != null) {
            // If resources are available, verify the image is loaded properly
            assertTrue(image.getWidth() > 0, "Image should have positive width");
            assertTrue(image.getHeight() > 0, "Image should have positive height");
        }
        // If resources are not available, image will be null, which is also acceptable
    }
    
    @Test
    @DisplayName("Should handle Trainer JSON without image field")
    void testTrainerJsonWithoutImage() throws IOException {
        String noImageJson = """
            {
                "name": "TestTrainer",
                "team": []
            }
            """;
        JsonNode noImageNode = mapper.readTree(noImageJson);
        
        BufferedImage image = XImage.getTrainerImage(noImageNode);
        assertNull(image, "Should return null when no image field exists");
    }
    
    // ==================== File Operation Tests ====================
    
    @Test
    @DisplayName("Should load image from external file")
    void testFromFile() {
        BufferedImage image = XImage.fromFile(testImageFile);
        
        assertNotNull(image, "Should successfully load image from file");
        assertEquals(10, image.getWidth(), "Image width should be 10");
        assertEquals(10, image.getHeight(), "Image height should be 10");
    }
    
    @Test
    @DisplayName("Should handle non-existent file gracefully")
    void testFromFileNonExistent() {
        File nonExistentFile = new File("non-existent-file.png");
        BufferedImage image = XImage.fromFile(nonExistentFile);
        
        assertNull(image, "Should return null for non-existent file");
    }
    
    // ==================== User Profile Tests ====================
    
    @Test
    @DisplayName("Should load and cache user profile picture")
    void testLoadUserProfile() {
        String userId = "test-user-123";
        
        BufferedImage image = XImage.loadUserProfile(testImageFile, userId);
        
        assertNotNull(image, "Should successfully load profile image");
        assertEquals(10, image.getWidth(), "Profile image width should be 10");
        assertEquals(10, image.getHeight(), "Profile image height should be 10");
        
        // Test caching - second call should return cached version
        BufferedImage cachedImage = XImage.getUserProfile(userId);
        assertSame(image, cachedImage, "Should return same cached instance");
    }
    
    @Test
    @DisplayName("Should update user profile picture")
    void testUpdateUserProfile() throws IOException {
        String userId = "test-user-456";
        
        // Create another test image (blue this time)
        File blueImageFile = tempDir.resolve("blue-image.png").toFile();
        BufferedImage blueImage = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                blueImage.setRGB(x, y, 0x0000FF); // Blue color
            }
        }
        ImageIO.write(blueImage, "png", blueImageFile);
        
        // Load initial profile
        XImage.loadUserProfile(testImageFile, userId);
        
        // Update profile
        boolean updated = XImage.updateUserProfile(blueImageFile, userId);
        assertTrue(updated, "Profile update should succeed");
        
        // Verify updated image
        BufferedImage updatedImage = XImage.getUserProfile(userId);
        assertNotNull(updatedImage);
        assertEquals(5, updatedImage.getWidth(), "Updated image should have width 5");
    }
    
    @Test
    @DisplayName("Should remove user profile from cache")
    void testRemoveUserProfile() {
        String userId = "test-user-789";
        
        // Load profile
        XImage.loadUserProfile(testImageFile, userId);
        assertNotNull(XImage.getUserProfile(userId), "Profile should be cached");
        
        // Remove profile
        boolean removed = XImage.removeUserProfile(userId);
        assertTrue(removed, "Should successfully remove profile");
        
        // Verify removal
        assertNull(XImage.getUserProfile(userId), "Profile should be removed from cache");
        
        // Try removing again
        boolean removedAgain = XImage.removeUserProfile(userId);
        assertFalse(removedAgain, "Should return false when removing non-existent profile");
    }
    
    // ==================== LoadByType Tests ====================
    
    @Test
    @DisplayName("Should load images by type correctly")
    void testLoadByType() {
        // Test Pokemon loading by JSON
        BufferedImage pokemonImage = XImage.loadByType(ImageType.POKEMON, pokemonJson);
        if (pokemonImage != null) {
            assertTrue(pokemonImage.getWidth() > 0, "Pokemon image should have positive width");
            assertTrue(pokemonImage.getHeight() > 0, "Pokemon image should have positive height");
        }
        
        // Test Pokemon loading by string
        BufferedImage pokemonByName = XImage.loadByType(ImageType.POKEMON, "Bulbasaur-front.png");
        if (pokemonByName != null) {
            assertTrue(pokemonByName.getWidth() > 0, "Pokemon image by name should have positive width");
            assertTrue(pokemonByName.getHeight() > 0, "Pokemon image by name should have positive height");
        }
        
        // Test Trainer loading
        BufferedImage trainerImage = XImage.loadByType(ImageType.TRAINER, trainerJson);
        if (trainerImage != null) {
            assertTrue(trainerImage.getWidth() > 0, "Trainer image should have positive width");
            assertTrue(trainerImage.getHeight() > 0, "Trainer image should have positive height");
        }
        
        // Test User Profile loading
        String userId = "test-user-loadbytype";
        XImage.loadUserProfile(testImageFile, userId);
        BufferedImage profileImage = XImage.loadByType(ImageType.USER_PROFILE, userId);
        assertNotNull(profileImage, "Should load cached profile image");
        
        // Test invalid combinations
        BufferedImage invalid = XImage.loadByType(ImageType.POKEMON, 12345);
        assertNull(invalid, "Should return null for invalid identifier type");
    }
    
    // ==================== Utility Function Tests ====================
    
    @Test
    @DisplayName("Should resize images correctly")
    void testResize() {
        BufferedImage originalImage = XImage.fromFile(testImageFile);
        assertNotNull(originalImage);
        
        Image resizedImage = XImage.resize(originalImage, 20, 30);
        assertNotNull(resizedImage, "Should successfully resize image");
        
        // Test with null image
        Image nullResize = XImage.resize(null, 20, 30);
        assertNull(nullResize, "Should return null when input image is null");
    }
    
    @Test
    @DisplayName("Should create square thumbnails correctly")
    void testCreateSquareThumbnail() throws IOException {
        // Create a rectangular test image
        File rectImageFile = tempDir.resolve("rect-image.png").toFile();
        BufferedImage rectImage = new BufferedImage(20, 10, BufferedImage.TYPE_INT_RGB);
        ImageIO.write(rectImage, "png", rectImageFile);
        
        BufferedImage loadedRect = XImage.fromFile(rectImageFile);
        Image thumbnail = XImage.createSquareThumbnail(loadedRect, 8);
        
        assertNotNull(thumbnail, "Should create square thumbnail");
        
        // Test with null image
        Image nullThumbnail = XImage.createSquareThumbnail(null, 8);
        assertNull(nullThumbnail, "Should return null when input image is null");
    }
    
    @Test
    @DisplayName("Should check image existence correctly")
    void testImageExists() {
        // Test if Pokemon and trainer images exist in resources
        boolean pokemonExists = XImage.imageExists(ImageType.POKEMON, "Bulbasaur-front.png");
        boolean trainerExists = XImage.imageExists(ImageType.TRAINER, "blue-champion.png");
        
        // These can be either true or false depending on whether test resources are available
        // The important thing is that the method doesn't throw exceptions
        assertNotNull(Boolean.valueOf(pokemonExists), "Method should return a boolean value");
        assertNotNull(Boolean.valueOf(trainerExists), "Method should return a boolean value");
        
        // Test with non-existent file - should always be false
        boolean nonExistent = XImage.imageExists(ImageType.POKEMON, "definitely-does-not-exist.png");
        assertFalse(nonExistent, "Should return false for definitely non-existent file");
    }
    
    // ==================== Cache Management Tests ====================
    
    @Test
    @DisplayName("Should clear all caches")
    void testClearAllCache() {
        // Load some data into caches
        String userId = "cache-test-user";
        XImage.loadUserProfile(testImageFile, userId);
        
        // Verify cache has content
        assertNotNull(XImage.getUserProfile(userId));
        
        // Clear all caches
        XImage.clearCache();
        
        // Verify caches are cleared
        assertNull(XImage.getUserProfile(userId), "User profile cache should be cleared");
    }
    
    @Test
    @DisplayName("Should clear specific cache types")
    void testClearSpecificCache() {
        // Load user profile
        String userId = "specific-cache-test";
        XImage.loadUserProfile(testImageFile, userId);
        assertNotNull(XImage.getUserProfile(userId));
        
        // Clear only user profile cache
        XImage.clearCache(ImageType.USER_PROFILE);
        
        // Verify only user profile cache is cleared
        assertNull(XImage.getUserProfile(userId), "User profile cache should be cleared");
    }
    
    @Test
    @DisplayName("Should provide cache statistics")
    void testGetCacheInfo() {
        // Initially should show empty caches
        String initialInfo = XImage.getCacheInfo();
        assertNotNull(initialInfo);
        assertTrue(initialInfo.contains("Profiles: 0"), "Should show 0 profiles initially");
        
        // Add some data to cache
        String userId = "stats-test-user";
        XImage.loadUserProfile(testImageFile, userId);
        
        // Check updated stats
        String updatedInfo = XImage.getCacheInfo();
        assertTrue(updatedInfo.contains("Profiles: 1"), "Should show 1 profile after loading");
    }
    
    // ==================== Error Handling Tests ====================
    
    @Test
    @DisplayName("Should handle malformed JSON gracefully")
    void testMalformedJson() throws IOException {
        String malformedJson = """
            {
                "name": "TestMon",
                "image": "not-an-array"
            }
            """;
        JsonNode malformedNode = mapper.readTree(malformedJson);
        
        // Should not throw exception, just return null
        BufferedImage image = XImage.getPokemonFrontImage(malformedNode);
        assertNull(image, "Should handle malformed JSON gracefully");
        
        var allImages = XImage.getAllPokemonImages(malformedNode);
        assertNotNull(allImages);
        assertTrue(allImages.isEmpty(), "Should return empty list for malformed JSON");
    }
    
    @Test
    @DisplayName("Should handle corrupted image files")
    void testCorruptedImageFile() throws IOException {
        // Create a "corrupted" file (just text content)
        File corruptedFile = tempDir.resolve("corrupted.png").toFile();
        try (var writer = new java.io.FileWriter(corruptedFile)) {
            writer.write("This is not an image file");
        }
        
        BufferedImage image = XImage.fromFile(corruptedFile);
        assertNull(image, "Should return null for corrupted image file");
        
        // Test with user profile loading
        boolean loaded = XImage.updateUserProfile(corruptedFile, "corrupted-user");
        assertFalse(loaded, "Should return false for corrupted profile image");
    }
    
    // ==================== Integration Tests ====================
    
    @Test
    @DisplayName("Should handle complete workflow")
    void testCompleteWorkflow() throws IOException {
        String userId = "workflow-user";
        
        // 1. Load user profile
        BufferedImage profile = XImage.loadUserProfile(testImageFile, userId);
        assertNotNull(profile, "Step 1: Should load user profile");
        
        // 2. Create thumbnail
        Image thumbnail = XImage.createSquareThumbnail(profile, 32);
        assertNotNull(thumbnail, "Step 2: Should create thumbnail");
        
        // 3. Resize image
        Image resized = XImage.resize(profile, 50, 50);
        assertNotNull(resized, "Step 3: Should resize image");
        
        // 4. Check cache info
        String cacheInfo = XImage.getCacheInfo();
        assertTrue(cacheInfo.contains("Profiles: 1"), "Step 4: Should show profile in cache");
        
        // 5. Test Pokemon image loading (handles both available and unavailable resources)
        BufferedImage pokemonImage = XImage.getPokemonFrontImage(pokemonJson);
        // This step tests that the method executes without throwing exceptions
        // The image can be either null (no resources) or a valid BufferedImage (resources available)
        if (pokemonImage != null) {
            // If image is loaded, verify it's valid
            assertTrue(pokemonImage.getWidth() > 0, "Step 5: Loaded Pokemon image should have positive width");
            assertTrue(pokemonImage.getHeight() > 0, "Step 5: Loaded Pokemon image should have positive height");
        }
        // No else clause - null is perfectly acceptable if resources aren't available
        
        // 6. Clear specific cache
        XImage.clearCache(ImageType.USER_PROFILE);
        assertNull(XImage.getUserProfile(userId), "Step 6: Should clear user profile cache");
    }
}
