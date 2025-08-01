package test.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import pokemon.sim.entity.Move;
import pokemon.sim.entity.Pokemon;
import pokemon.sim.entity.PokemonType;
import pokemon.sim.entity.Stats;
import pokemon.sim.util.XMove.MoveResult;
import pokemon.sim.util.XMove.MoveCategory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import pokemon.sim.util.XJson;
import pokemon.sim.util.XMove;
import pokemon.sim.util.XRand;

/**
 * Comprehensive test suite for XMove utility class
 * Tests move execution, type effectiveness, caching, and integration with XJson and XRand
 * 
 * @author May5th
 */
class XMoveTest {
    
    private XMove xMove;
    private XJson mockXJson;
    private Pokemon testAttacker;
    private Pokemon testDefender;
    private Move testMove;
    private Move statusMove;
    private Move guaranteedMove;
    
    @TempDir
    Path tempDir;
    
    @BeforeEach
    void setUp() throws IOException {
        // Clear caches before each test
        XMove.clearCache();
        
        // Create mock XJson that will be used throughout tests
        mockXJson = mock(XJson.class);
        
        // Create test Pokemon
        setupTestPokemon();
        
        // Create test moves
        setupTestMoves();
        
        // Setup mock XJson responses BEFORE creating XMove
        setupMockXJsonResponses();
        
        // Now create XMove with properly mocked XJson
        try (MockedStatic<XRand> mockedXRand = Mockito.mockStatic(XRand.class)) {
            java.util.Random mockRandom = new java.util.Random(42);
            mockedXRand.when(() -> XRand.get()).thenReturn(mockRandom);
            
            xMove = new XMove(mockXJson);
        }
    }
    
    @AfterEach
    void tearDown() {
        XMove.clearCache();
    }
    
    /**
     * Setup test Pokemon with stats and types
     */
    private void setupTestPokemon() {
        // Create attacker (Pikachu - Electric type)
        testAttacker = new Pokemon();
        testAttacker.setName("Pikachu");
        testAttacker.setTypes(Arrays.asList("Electric"));
        
        Stats attackerStats = new Stats(35, 55, 40, 50, 50, 90); // Pikachu base stats
        testAttacker.setStats(Arrays.asList(attackerStats));
        
        // Create defender (Charizard - Fire/Flying type)
        testDefender = new Pokemon();
        testDefender.setName("Charizard");
        testDefender.setTypes(Arrays.asList("Fire", "Flying"));
        
        Stats defenderStats = new Stats(78, 84, 78, 109, 85, 100); // Charizard base stats
        testDefender.setStats(Arrays.asList(defenderStats));
    }
    
    /**
     * Setup test moves with different characteristics
     */
    private void setupTestMoves() {
        // Regular physical move
        testMove = new Move("Thunderbolt", "Electric", "Special", 90, 100, 15, "May cause paralysis");
        
        // Status move with no damage
        statusMove = new Move("Thunder Wave", "Electric", "Status", 0, 90, 20, "Causes paralysis");
        
        // Move with guaranteed accuracy and no power indicator
        guaranteedMove = new Move("Swift", "Normal", "Special", 60, 0, 20, "Never misses");
    }
    
    /**
     * Setup mock responses for XJson
     */
    private void setupMockXJsonResponses() throws IOException {
        // Mock move loading
        when(mockXJson.loadMove("Thunderbolt.json")).thenReturn(testMove);
        when(mockXJson.loadMove("ThunderWave.json")).thenReturn(statusMove);
        when(mockXJson.loadMove("Swift.json")).thenReturn(guaranteedMove);
        when(mockXJson.loadMove("NonExistent.json")).thenReturn(null);
        
        // Mock type effectiveness data - this is crucial for type calculations
        List<PokemonType> mockTypes = createMockTypeData();
        when(mockXJson.loadPokemonTypes("PokemonType.json")).thenReturn(mockTypes);
        
        // Mock JSON node loading
        ObjectMapper mapper = new ObjectMapper();
        JsonNode moveNode = mapper.valueToTree(testMove);
        when(mockXJson.loadJsonNode("Thunderbolt.json")).thenReturn(moveNode);
    }
    
    /**
     * Create mock type effectiveness data
     */
    private List<PokemonType> createMockTypeData() {
        List<PokemonType> types = new ArrayList<>();
        
        // Electric type (weak to Ground, resists Flying/Steel/Electric)
        PokemonType electric = new PokemonType();
        electric.setName("Electric");
        electric.setWeaknesses(Arrays.asList("Ground"));
        electric.setResistances(Arrays.asList("Flying", "Steel", "Electric"));
        electric.setImmunities(new ArrayList<>());
        types.add(electric);
        
        // Fire type (weak to Water/Ground/Rock, resists Fire/Grass/Ice/Bug/Steel/Fairy)
        PokemonType fire = new PokemonType();
        fire.setName("Fire");
        fire.setWeaknesses(Arrays.asList("Water", "Ground", "Rock"));
        fire.setResistances(Arrays.asList("Fire", "Grass", "Ice", "Bug", "Steel", "Fairy"));
        fire.setImmunities(new ArrayList<>());
        types.add(fire);
        
        // Flying type (weak to Electric/Ice/Rock, resists Grass/Fighting, immune to Ground)
        PokemonType flying = new PokemonType();
        flying.setName("Flying");
        flying.setWeaknesses(Arrays.asList("Electric", "Ice", "Rock"));
        flying.setResistances(Arrays.asList("Grass", "Fighting"));
        flying.setImmunities(Arrays.asList("Ground"));
        types.add(flying);
        
        // Normal type (weak to Fighting, immune to Ghost)
        PokemonType normal = new PokemonType();
        normal.setName("Normal");
        normal.setWeaknesses(Arrays.asList("Fighting"));
        normal.setResistances(new ArrayList<>());
        normal.setImmunities(Arrays.asList("Ghost"));
        types.add(normal);
        
        return types;
    }
    
    // ==================== Move Loading Tests ====================
    
    @Test
    @DisplayName("Should verify type effectiveness data is loaded")
    void testTypeEffectivenessDataLoading() {
        try (MockedStatic<XRand> mockedXRand = Mockito.mockStatic(XRand.class)) {
            java.util.Random mockRandom = new java.util.Random(42);
            mockedXRand.when(() -> XRand.get()).thenReturn(mockRandom);
            
            // Verify that XJson.loadPokemonTypes was called during XMove initialization
            try {
                verify(mockXJson, atLeastOnce()).loadPokemonTypes("PokemonType.json");
            } catch (IOException e) {
                fail("Mock verification should not throw IOException");
            }
            
            // Test that type effectiveness calculation works
            double effectiveness = xMove.getTypeEffectiveness("Electric", "Flying");
            assertEquals(2.0, effectiveness, 0.01, "Electric vs Flying should be super effective");
        }
    }
    
    @Test
    @DisplayName("Should load move from file successfully")
    void testLoadMove() {
        Move loadedMove = xMove.loadMove("Thunderbolt.json");
        
        assertNotNull(loadedMove, "Move should be loaded successfully");
        assertEquals("Thunderbolt", loadedMove.getName(), "Move name should match");
        assertEquals("Electric", loadedMove.getType(), "Move type should match");
        assertEquals(90, loadedMove.getPower(), "Move power should match");
        assertEquals(100, loadedMove.getAccuracy(), "Move accuracy should match");
    }
    
    @Test
    @DisplayName("Should cache loaded moves")
    void testMoveCaching() {
        // Load move twice
        Move firstLoad = xMove.loadMove("Thunderbolt.json");
        Move secondLoad = xMove.loadMove("Thunderbolt.json");
        
        assertNotNull(firstLoad, "First load should succeed");
        assertNotNull(secondLoad, "Second load should succeed");
        assertSame(firstLoad, secondLoad, "Second load should return cached instance");
        
        // Verify XJson was only called once
        try {
            verify(mockXJson, times(1)).loadMove("Thunderbolt.json");
        } catch (IOException e) {
            fail("IOException should not occur in mock verification");
        }
    }
    
    @Test
    @DisplayName("Should handle non-existent move gracefully")
    void testLoadNonExistentMove() {
        Move result = xMove.loadMove("NonExistent.json");
        
        assertNull(result, "Non-existent move should return null");
    }
    
    @Test
    @DisplayName("Should load move JSON data")
    void testLoadMoveJson() {
        JsonNode jsonNode = xMove.loadMoveJson("Thunderbolt.json");
        
        assertNotNull(jsonNode, "JSON node should be loaded");
        assertEquals("Thunderbolt", jsonNode.get("name").asText(), "JSON should contain correct move name");
    }
    
    // ==================== Move Execution Tests ====================
    
    @Test
    @DisplayName("Should execute move successfully with hit and damage")
    void testExecuteMoveSuccess() {
        // Mock XRand to always hit and not crit
        try (MockedStatic<XRand> mockedXRand = Mockito.mockStatic(XRand.class)) {
            java.util.Random mockRandom = new java.util.Random(42);
            mockedXRand.when(() -> XRand.chance(100)).thenReturn(true); // Always hit
            mockedXRand.when(() -> XRand.crit(16)).thenReturn(false); // No crit
            mockedXRand.when(() -> XRand.get()).thenReturn(mockRandom); // Seeded for consistency
            
            MoveResult result = xMove.executeMove(testAttacker, testDefender, testMove);
            
            assertTrue(result.isHit(), "Move should hit");
            assertTrue(result.getDamage() > 0, "Move should deal damage");
            assertFalse(result.isCritical(), "Move should not be critical");
            assertTrue(result.isPpReduced(), "PP should be reduced");
            assertNotNull(result.getMessage(), "Result should have a message");
        }
    }
    
    @Test
    @DisplayName("Should handle move miss")
    void testExecuteMoveMiss() {
        // Mock XRand to always miss
        try (MockedStatic<XRand> mockedXRand = Mockito.mockStatic(XRand.class)) {
            mockedXRand.when(() -> XRand.chance(100)).thenReturn(false); // Always miss
            
            MoveResult result = xMove.executeMove(testAttacker, testDefender, testMove);
            
            assertFalse(result.isHit(), "Move should miss");
            assertEquals(0, result.getDamage(), "Missed move should deal no damage");
            assertFalse(result.isCritical(), "Missed move should not be critical");
            assertTrue(result.getMessage().contains("missed"), "Message should indicate miss");
        }
    }
    
    @Test
    @DisplayName("Should handle critical hits")
    void testExecuteMoveCriticalHit() {
        // Mock XRand for hit and critical
        try (MockedStatic<XRand> mockedXRand = Mockito.mockStatic(XRand.class)) {
            java.util.Random mockRandom = new java.util.Random(42);
            mockedXRand.when(() -> XRand.chance(100)).thenReturn(true); // Always hit
            mockedXRand.when(() -> XRand.crit(16)).thenReturn(true); // Always crit
            mockedXRand.when(() -> XRand.get()).thenReturn(mockRandom); // Seeded for consistency
            
            MoveResult result = xMove.executeMove(testAttacker, testDefender, testMove);
            
            assertTrue(result.isHit(), "Move should hit");
            assertTrue(result.isCritical(), "Move should be critical");
            assertTrue(result.getDamage() > 0, "Critical hit should deal damage");
            assertTrue(result.getMessage().contains("Critical hit"), "Message should indicate critical hit");
        }
    }
    
    @Test
    @DisplayName("Should execute status move correctly")
    void testExecuteStatusMove() {
        // Mock XRand to always hit
        try (MockedStatic<XRand> mockedXRand = Mockito.mockStatic(XRand.class)) {
            mockedXRand.when(() -> XRand.chance(90)).thenReturn(true); // Hit based on accuracy
            
            MoveResult result = xMove.executeMove(testAttacker, testDefender, statusMove);
            
            assertTrue(result.isHit(), "Status move should hit");
            assertEquals(0, result.getDamage(), "Status move should deal no damage");
            assertNotNull(result.getEffect(), "Status move should have effect");
            assertEquals("Causes paralysis", result.getEffect(), "Effect should match move description");
        }
    }
    
    @Test
    @DisplayName("Should execute move by filename")
    void testExecuteMoveByFilename() {
        // Mock XRand to always hit
        try (MockedStatic<XRand> mockedXRand = Mockito.mockStatic(XRand.class)) {
            mockedXRand.when(() -> XRand.chance(100)).thenReturn(true);
            mockedXRand.when(() -> XRand.crit(16)).thenReturn(false);
            mockedXRand.when(() -> XRand.get()).thenReturn(new java.util.Random(42));
            
            MoveResult result = xMove.executeMove(testAttacker, testDefender, "Thunderbolt.json");
            
            assertTrue(result.isHit(), "Move should hit");
            assertTrue(result.getDamage() > 0, "Move should deal damage");
        }
    }
    
    @Test
    @DisplayName("Should handle invalid parameters")
    void testExecuteMoveInvalidParameters() {
        MoveResult result1 = xMove.executeMove(null, testDefender, testMove);
        MoveResult result2 = xMove.executeMove(testAttacker, null, testMove);
        MoveResult result3 = xMove.executeMove(testAttacker, testDefender, (Move) null);
        
        assertFalse(result1.isHit(), "Move with null attacker should fail");
        assertFalse(result2.isHit(), "Move with null defender should fail");
        assertFalse(result3.isHit(), "Move with null move should fail");
        
        assertTrue(result1.getMessage().contains("Invalid parameters"), "Should have error message");
        assertTrue(result2.getMessage().contains("Invalid parameters"), "Should have error message");
        assertTrue(result3.getMessage().contains("Invalid parameters"), "Should have error message");
    }
    
    // ==================== Type Effectiveness Tests ====================
    
    @Test
    @DisplayName("Should calculate super effective damage")
    void testSuperEffectiveDamage() {
        // Electric vs Flying should be super effective (2x)
        try (MockedStatic<XRand> mockedXRand = Mockito.mockStatic(XRand.class)) {
            java.util.Random mockRandom = new java.util.Random(42);
            mockedXRand.when(() -> XRand.chance(100)).thenReturn(true);
            mockedXRand.when(() -> XRand.crit(16)).thenReturn(false);
            mockedXRand.when(() -> XRand.get()).thenReturn(mockRandom);
            
            MoveResult result = xMove.executeMove(testAttacker, testDefender, testMove);
            
            assertTrue(result.isHit(), "Move should hit");
            assertTrue(result.isSuperEffective(), "Should be super effective against Flying type");
            assertEquals(2.0, result.getTypeEffectiveness(), 0.01, "Should have 2x effectiveness");
            assertTrue(result.getMessage().contains("super effective"), "Message should indicate super effectiveness");
        }
    }
    
    @Test
    @DisplayName("Should calculate type effectiveness correctly")
    void testTypeEffectivenessCalculation() {
        // Ensure type data is loaded by creating a fresh XMove instance
        try (MockedStatic<XRand> mockedXRand = Mockito.mockStatic(XRand.class)) {
            java.util.Random mockRandom = new java.util.Random(42);
            mockedXRand.when(() -> XRand.get()).thenReturn(mockRandom);
            
            // Test direct type effectiveness calculation
            double effectiveness = xMove.getTypeEffectiveness("Electric", "Flying");
            assertEquals(2.0, effectiveness, 0.01, "Electric vs Flying should be 2x effective");
            
            double resistanceEffectiveness = xMove.getTypeEffectiveness("Electric", "Electric");
            assertEquals(0.5, resistanceEffectiveness, 0.01, "Electric vs Electric should be 0.5x effective");
        }
    }
    
    @Test
    @DisplayName("Should handle immunity correctly")
    void testImmunity() {
        // Create a Normal-type move that should be immune against Ghost
        Move normalMove = new Move("Tackle", "Normal", "Physical", 50, 100, 35, "A physical attack");
        
        // Create a Ghost-type Pokemon (immune to Normal moves)
        Pokemon ghostPokemon = new Pokemon();
        ghostPokemon.setName("Gengar");
        ghostPokemon.setTypes(Arrays.asList("Ghost"));
        ghostPokemon.setStats(Arrays.asList(new Stats(60, 65, 60, 130, 75, 110)));
        
        try {
            // Create extended type data that includes Ghost type
            List<PokemonType> extendedTypes = createMockTypeData();
            
            // Add Ghost type (immune to Normal and Fighting)
            PokemonType ghost = new PokemonType();
            ghost.setName("Ghost");
            ghost.setWeaknesses(Arrays.asList("Ghost", "Dark"));
            ghost.setResistances(Arrays.asList("Poison", "Bug"));
            ghost.setImmunities(Arrays.asList("Normal", "Fighting"));
            extendedTypes.add(ghost);
            
            // Create a fresh mock XJson with the updated type data
            XJson freshMockXJson = mock(XJson.class);
            when(freshMockXJson.loadMove("Tackle.json")).thenReturn(normalMove);
            when(freshMockXJson.loadPokemonTypes("PokemonType.json")).thenReturn(extendedTypes);
            
            // IMPORTANT: Clear the static type cache to force reload
            XMove.clearCache();
            
            // Create a fresh XMove instance that will load the new type data
            try (MockedStatic<XRand> mockedXRand = Mockito.mockStatic(XRand.class)) {
                java.util.Random mockRandom = new java.util.Random(42);
                mockedXRand.when(() -> XRand.get()).thenReturn(mockRandom);
                mockedXRand.when(() -> XRand.chance(100)).thenReturn(true);
                
                XMove freshXMove = new XMove(freshMockXJson);
                
                // First, verify the type effectiveness calculation directly
                double effectiveness = freshXMove.getTypeEffectiveness("Normal", "Ghost");
                assertEquals(0.0, effectiveness, 0.01, "Normal vs Ghost should have no effect (immunity)");
                
                // Now test the full move execution
                MoveResult result = freshXMove.executeMove(testAttacker, ghostPokemon, normalMove);
                
                assertTrue(result.isHit(), "Move should hit but have no effect");
                assertTrue(result.isNoEffect(), "Should have no effect due to Ghost immunity to Normal");
                assertEquals(0.0, result.getTypeEffectiveness(), 0.01, "Type effectiveness should be 0.0");
                assertEquals(0, result.getDamage(), "Should deal no damage");
                assertTrue(result.getMessage().contains("no effect"), "Message should indicate no effect");
            }
            
        } catch (IOException e) {
            fail("Mock setup should not fail: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("Should identify type relationships correctly")
    void testTypeRelationshipMethods() {
        try (MockedStatic<XRand> mockedXRand = Mockito.mockStatic(XRand.class)) {
            java.util.Random mockRandom = new java.util.Random(42);
            mockedXRand.when(() -> XRand.get()).thenReturn(mockRandom);
            
            assertTrue(xMove.isWeak("Electric", "Flying"), "Electric should be super effective vs Flying");
            assertTrue(xMove.isResistant("Electric", "Electric"), "Electric should resist Electric");
            assertFalse(xMove.isImmune("Electric", "Flying"), "Electric should not be immune to Flying");
        }
    }
    
    // ==================== Special Value Handling Tests ====================
    
    @Test
    @DisplayName("Should handle guaranteed accuracy moves")
    void testGuaranteedAccuracy() {
        // Swift has 0 accuracy, which means guaranteed hit
        MoveResult result = xMove.executeMove(testAttacker, testDefender, guaranteedMove);
        
        assertTrue(result.isHit(), "Guaranteed accuracy move should always hit");
    }
    
    @Test
    @DisplayName("Should handle moves with no power")
    void testNoPowerMoves() {
        // Status moves should deal no damage
        try (MockedStatic<XRand> mockedXRand = Mockito.mockStatic(XRand.class)) {
            mockedXRand.when(() -> XRand.chance(90)).thenReturn(true);
            
            MoveResult result = xMove.executeMove(testAttacker, testDefender, statusMove);
            
            assertTrue(result.isHit(), "Status move should hit");
            assertEquals(0, result.getDamage(), "Status move should deal no damage");
        }
    }
    
    @Test
    @DisplayName("Should get move power correctly")
    void testGetMovePower() {
        assertEquals(90, xMove.getMovePower(testMove), "Should return correct power");
        assertEquals(0, xMove.getMovePower(statusMove), "Status move should have 0 power");
        assertEquals(60, xMove.getMovePower(guaranteedMove), "Should return correct power for guaranteed move");
    }
    
    @Test
    @DisplayName("Should get move accuracy correctly")
    void testGetMoveAccuracy() {
        assertEquals(100, xMove.getMoveAccuracy(testMove), "Should return correct accuracy");
        assertEquals(90, xMove.getMoveAccuracy(statusMove), "Should return correct accuracy");
        assertEquals(101, xMove.getMoveAccuracy(guaranteedMove), "Guaranteed move should have 101 accuracy");
    }
    
    // ==================== Move Category Tests ====================
    
    @Test
    @DisplayName("Should identify move categories correctly")
    void testGetMoveCategory() {
        assertEquals(MoveCategory.SPECIAL, xMove.getMoveCategory(testMove), "Should identify special move");
        assertEquals(MoveCategory.STATUS, xMove.getMoveCategory(statusMove), "Should identify status move");
        
        Move physicalMove = new Move("Tackle", "Normal", "Physical", 40, 100, 35, "");
        assertEquals(MoveCategory.PHYSICAL, xMove.getMoveCategory(physicalMove), "Should identify physical move");
    }
    
    // ==================== Accuracy and Damage Tests ====================
    
    @Test
    @DisplayName("Should check accuracy correctly")
    void testCheckAccuracy() {
        // Test guaranteed accuracy
        assertTrue(xMove.checkAccuracy(guaranteedMove), "Guaranteed accuracy should always hit");
        
        // Test with mocked XRand
        try (MockedStatic<XRand> mockedXRand = Mockito.mockStatic(XRand.class)) {
            mockedXRand.when(() -> XRand.chance(100)).thenReturn(true);
            assertTrue(xMove.checkAccuracy(testMove), "Should hit with 100% accuracy");
            
            mockedXRand.when(() -> XRand.chance(100)).thenReturn(false);
            assertFalse(xMove.checkAccuracy(testMove), "Should miss when XRand returns false");
        }
    }
    
    @Test
    @DisplayName("Should calculate base damage correctly")
    void testCalculateBaseDamage() {
        try (MockedStatic<XRand> mockedXRand = Mockito.mockStatic(XRand.class)) {
            java.util.Random mockRandom = new java.util.Random(42);
            mockedXRand.when(() -> XRand.get()).thenReturn(mockRandom);
            
            int damage = xMove.calculateBaseDamage(testAttacker, testDefender, testMove);
            
            assertTrue(damage > 0, "Should calculate positive damage");
            
            int statusDamage = xMove.calculateBaseDamage(testAttacker, testDefender, statusMove);
            assertEquals(0, statusDamage, "Status move should calculate 0 damage");
        }
    }
    
    // ==================== Utility Method Tests ====================
    
    @Test
    @DisplayName("Should load multiple moves")
    void testLoadMoves() {
        List<String> moveFiles = Arrays.asList("Thunderbolt.json", "ThunderWave.json", "Swift.json");
        List<Move> moves = xMove.loadMoves(moveFiles);
        
        assertEquals(3, moves.size(), "Should load all three moves");
        assertEquals("Thunderbolt", moves.get(0).getName(), "First move should be Thunderbolt");
        assertEquals("Thunder Wave", moves.get(1).getName(), "Second move should be Thunder Wave");
        assertEquals("Swift", moves.get(2).getName(), "Third move should be Swift");
    }
    
    @Test
    @DisplayName("Should validate moves correctly")
    void testValidateMove() {
        assertTrue(xMove.validateMove(testMove), "Valid move should pass validation");
        assertFalse(xMove.validateMove(null), "Null move should fail validation");
        
        Move invalidMove = new Move(null, "Electric", "Special", 90, 100, 15, "");
        assertFalse(xMove.validateMove(invalidMove), "Move with null name should fail validation");
        
        Move emptyNameMove = new Move("", "Electric", "Special", 90, 100, 15, "");
        assertFalse(xMove.validateMove(emptyNameMove), "Move with empty name should fail validation");
    }
    
    @Test
    @DisplayName("Should preload moves correctly")
    void testPreloadMoves() {
        List<String> moveFiles = Arrays.asList("Thunderbolt.json", "ThunderWave.json");
        
        // Verify moves are not cached initially
        assertFalse(XMove.isCached("Thunderbolt.json"), "Should not be cached initially");
        assertFalse(XMove.isCached("ThunderWave.json"), "Should not be cached initially");
        
        // Preload moves
        xMove.preloadMoves(moveFiles);
        
        // Verify moves are now cached
        assertTrue(XMove.isCached("Thunderbolt.json"), "Should be cached after preload");
        assertTrue(XMove.isCached("ThunderWave.json"), "Should be cached after preload");
    }
    
    // ==================== Cache Management Tests ====================
    
    @Test
    @DisplayName("Should manage cache correctly")
    void testCacheManagement() {
        // Load a move to populate cache
        xMove.loadMove("Thunderbolt.json");
        
        // Verify cache contains the move
        assertTrue(XMove.isCached("Thunderbolt.json"), "Move should be cached");
        
        // Get cache info
        String cacheInfo = XMove.getCacheInfo();
        assertNotNull(cacheInfo, "Cache info should not be null");
        assertTrue(cacheInfo.contains("Moves: 1"), "Cache info should show 1 move");
        
        // Get cached move names
        List<String> cachedNames = XMove.getCachedMoveNames();
        assertTrue(cachedNames.contains("Thunderbolt.json"), "Cached names should contain Thunderbolt.json");
        
        // Clear cache
        XMove.clearCache();
        assertFalse(XMove.isCached("Thunderbolt.json"), "Move should not be cached after clear");
    }
    
    // ==================== Error Handling Tests ====================
    
    @Test
    @DisplayName("Should handle XJson errors gracefully")
    void testXJsonErrorHandling() throws IOException {
        // Mock XJson to throw IOException
        when(mockXJson.loadMove("ErrorMove.json")).thenThrow(new IOException("File not found"));
        
        Move result = xMove.loadMove("ErrorMove.json");
        
        assertNull(result, "Should return null when XJson throws IOException");
    }
    
    @Test
    @DisplayName("Should handle Pokemon without stats")
    void testPokemonWithoutStats() {
        Pokemon noStatsPokemon = new Pokemon();
        noStatsPokemon.setName("TestMon");
        noStatsPokemon.setTypes(Arrays.asList("Normal"));
        // No stats set
        
        try (MockedStatic<XRand> mockedXRand = Mockito.mockStatic(XRand.class)) {
            java.util.Random mockRandom = new java.util.Random(42);
            mockedXRand.when(() -> XRand.chance(100)).thenReturn(true);
            mockedXRand.when(() -> XRand.crit(16)).thenReturn(false);
            mockedXRand.when(() -> XRand.get()).thenReturn(mockRandom);
            
            MoveResult result = xMove.executeMove(testAttacker, noStatsPokemon, testMove);
            
            assertTrue(result.isHit(), "Move should still execute");
            assertTrue(result.getDamage() >= 0, "Should handle missing stats gracefully");
        }
    }
    
    @Test
    @DisplayName("Should handle Pokemon without types")
    void testPokemonWithoutTypes() {
        Pokemon noTypesPokemon = new Pokemon();
        noTypesPokemon.setName("TestMon");
        noTypesPokemon.setStats(Arrays.asList(new Stats(50, 50, 50, 50, 50, 50)));
        // No types set
        
        try (MockedStatic<XRand> mockedXRand = Mockito.mockStatic(XRand.class)) {
            java.util.Random mockRandom = new java.util.Random(42);
            mockedXRand.when(() -> XRand.chance(100)).thenReturn(true);
            mockedXRand.when(() -> XRand.crit(16)).thenReturn(false);
            mockedXRand.when(() -> XRand.get()).thenReturn(mockRandom);
            
            MoveResult result = xMove.executeMove(testAttacker, noTypesPokemon, testMove);
            
            assertTrue(result.isHit(), "Move should still execute");
            assertEquals(1.0, result.getTypeEffectiveness(), 0.01, "Should default to normal effectiveness");
        }
    }
    
    // ==================== Integration Tests ====================
    
    @Test
    @DisplayName("Should handle complete battle scenario")
    void testCompleteBattleScenario() {
        try (MockedStatic<XRand> mockedXRand = Mockito.mockStatic(XRand.class)) {
            // Setup for a complete battle scenario
            java.util.Random mockRandom = new java.util.Random(42);
            mockedXRand.when(() -> XRand.chance(anyInt())).thenReturn(true); // Always hit
            mockedXRand.when(() -> XRand.crit(16)).thenReturn(false); // No crits initially
            mockedXRand.when(() -> XRand.get()).thenReturn(mockRandom);
            
            // Execute several moves
            MoveResult result1 = xMove.executeMove(testAttacker, testDefender, "Thunderbolt.json");
            MoveResult result2 = xMove.executeMove(testDefender, testAttacker, "ThunderWave.json");
            
            // Then a critical hit
            mockedXRand.when(() -> XRand.crit(16)).thenReturn(true);
            MoveResult result3 = xMove.executeMove(testAttacker, testDefender, "Swift.json");
            
            // Verify results
            assertTrue(result1.isHit(), "First move should hit");
            assertTrue(result1.isSuperEffective(), "First move should be super effective (Electric vs Flying)");
            assertTrue(result2.isHit() && result2.getDamage() == 0, "Status move should hit with no damage");
            assertTrue(result3.isHit() && result3.isCritical(), "Third move should be critical hit");
            
            // Verify caching worked
            assertTrue(XMove.isCached("Thunderbolt.json"), "Moves should be cached");
            assertTrue(XMove.isCached("ThunderWave.json"), "Moves should be cached");
            assertTrue(XMove.isCached("Swift.json"), "Moves should be cached");
        }
    }
    
    @Test
    @DisplayName("Should get XJson instance")
    void testGetXJsonInstance() {
        XJson retrievedXJson = xMove.getXJson();
        assertSame(mockXJson, retrievedXJson, "Should return the same XJson instance");
    }
    
    // ==================== Performance Tests ====================
    
    @Test
    @DisplayName("Should perform well with repeated operations")
    void testPerformance() {
        try (MockedStatic<XRand> mockedXRand = Mockito.mockStatic(XRand.class)) {
            java.util.Random mockRandom = new java.util.Random(42);
            mockedXRand.when(() -> XRand.chance(anyInt())).thenReturn(true);
            mockedXRand.when(() -> XRand.crit(16)).thenReturn(false);
            mockedXRand.when(() -> XRand.get()).thenReturn(mockRandom);
            
            // Execute the same move many times to test caching performance
            long startTime = System.currentTimeMillis();
            
            for (int i = 0; i < 1000; i++) {
                MoveResult result = xMove.executeMove(testAttacker, testDefender, "Thunderbolt.json");
                assertTrue(result.isHit(), "Move should hit on iteration " + i);
            }
            
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            
            // Should complete quickly due to caching (arbitrary threshold)
            assertTrue(duration < 1000, "1000 move executions should complete in under 1 second");
            
            // Verify XJson was only called once due to caching
            try {
                verify(mockXJson, times(1)).loadMove("Thunderbolt.json");
            } catch (IOException e) {
                fail("Mock verification should not throw IOException");
            }
        }
    }
}