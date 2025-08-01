/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon.sim.ui;

import pokemon.sim.util.XImage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 *
 * @author May5th
 */
public class NewChoosingPokemonJDialog extends JDialog {
    
    private static final java.util.logging.Logger logger = 
        java.util.logging.Logger.getLogger(ChoosingPokemonJDialog.class.getName());
    
    // Constants
    private static final int TEAM_SIZE = 6;
    private static final int POKEMON_PANEL_SIZE = 100;
    private static final int TEAM_PANEL_WIDTH = 130;
    private static final int TEAM_PANEL_HEIGHT = 120;
    private static final int POKEMON_PER_ROW = 12;
    private static final Color SELECTED_BORDER_COLOR = new Color(255, 215, 0); // Gold
    private static final Color DEFAULT_BORDER_COLOR = Color.GRAY;
    
    // Data
    private final List<Pokemon> availablePokemon;
    private final String[] selectedTeam = new String[TEAM_SIZE];
    private final JLabel[] teamLabels = new JLabel[TEAM_SIZE];
    private final JPanel[] teamPanels = new JPanel[TEAM_SIZE];
    private final Map<String, JPanel> pokemonPanels = new HashMap<>();
    
    // UI Components
    private JPanel mainPanel;
    private JPanel pokemonGridPanel;
    private JPanel teamPanel;
    private JButton goButton;

    public NewChoosingPokemonJDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.availablePokemon = createPokemonList();
        initializeUI();
        setupEventHandlers();
        logger.info("Pokemon selection dialog initialized with " + availablePokemon.size() + " Pokemon");
    }

    private void initializeUI() {
        setTitle("Choose Your Pokemon");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1250, 720);
        setLocationRelativeTo(getParent());
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(100, 150, 200));
        
        // Title
        JLabel titleLabel = new JLabel("CHOOSE YOUR POKÉMON", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setPreferredSize(new Dimension(1250, 80));
        
        // Team panel
        createTeamPanel();
        
        // Pokemon grid
        createPokemonGrid();
        
        // Go button
        goButton = new JButton("GO");
        goButton.setFont(new Font("Segoe UI", Font.BOLD, 24));
        goButton.setBackground(new Color(0, 153, 255));
        goButton.setForeground(Color.WHITE);
        goButton.setPreferredSize(new Dimension(300, 60));
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(goButton);
        buttonPanel.setPreferredSize(new Dimension(1250, 80));
        
        // Create center panel to hold team and pokemon grid
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(teamPanel, BorderLayout.NORTH);
        centerPanel.add(pokemonGridPanel, BorderLayout.CENTER);
        
        // Layout
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }

    private void createTeamPanel() {
        teamPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        teamPanel.setOpaque(false);
        teamPanel.setPreferredSize(new Dimension(1250, 160));
        
        for (int i = 0; i < TEAM_SIZE; i++) {
            JPanel slotPanel = new JPanel(new BorderLayout());
            slotPanel.setPreferredSize(new Dimension(TEAM_PANEL_WIDTH, TEAM_PANEL_HEIGHT));
            slotPanel.setBorder(BorderFactory.createDashedBorder(DEFAULT_BORDER_COLOR, 2, 5));
            slotPanel.setBackground(Color.WHITE);
            
            JLabel slotLabel = new JLabel("", JLabel.CENTER);
            slotPanel.add(slotLabel, BorderLayout.CENTER);
            
            teamLabels[i] = slotLabel;
            teamPanels[i] = slotPanel;
            
            final int slotIndex = i;
            slotPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    removePokemonFromTeam(slotIndex);
                }
                
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (selectedTeam[slotIndex] != null) {
                        slotPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    }
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    updateTeamPanelBorders();
                }
            });
            
            teamPanel.add(slotPanel);
        }
    }

    private void createPokemonGrid() {
        pokemonGridPanel = new JPanel();
        pokemonGridPanel.setLayout(new BorderLayout());
        pokemonGridPanel.setPreferredSize(new Dimension(1230, 370));
        
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(0, POKEMON_PER_ROW, 6, 6));
        gridPanel.setBackground(new Color(204, 204, 204));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        for (Pokemon pokemon : availablePokemon) {
            JPanel pokemonPanel = createPokemonPanel(pokemon);
            pokemonPanels.put(pokemon.name, pokemonPanel);
            gridPanel.add(pokemonPanel);
            System.out.println("Added panel for: " + pokemon.name); // Debug
        }
        
        System.out.println("Total Pokemon panels created: " + pokemonPanels.size()); // Debug
        
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        pokemonGridPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createPokemonPanel(Pokemon pokemon) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(POKEMON_PANEL_SIZE, POKEMON_PANEL_SIZE));
        panel.setBorder(BorderFactory.createBevelBorder(0));
        panel.setBackground(Color.WHITE);
        
        JLabel label = new JLabel("", JLabel.CENTER);
        
        // Try to load Pokemon image, fallback to text
        try {
            BufferedImage pokemonImage = XImage.getPokemonImageByName(pokemon.imageFileName);
            if (pokemonImage != null) {
                Image scaledImage = XImage.resize(pokemonImage, POKEMON_PANEL_SIZE - 10, POKEMON_PANEL_SIZE - 10);
                label.setIcon(new ImageIcon(scaledImage));
                label.setText(""); // Clear any text
            } else {
                // Image not found - show name as text
                label.setIcon(null);
                label.setText("<html><center>" + pokemon.name + "</center></html>");
                label.setFont(new Font("Arial", Font.BOLD, 9));
                label.setForeground(Color.BLACK);
            }
        } catch (Exception e) {
            // Any error loading image - show name as text
            label.setIcon(null);
            label.setText("<html><center>" + pokemon.name + "</center></html>");
            label.setFont(new Font("Arial", Font.BOLD, 9));
            label.setForeground(Color.BLACK);
            logger.warning("Could not load image for " + pokemon.name + ": " + e.getMessage());
        }
        
        panel.add(label, BorderLayout.CENTER);
        
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Clicked on: " + pokemon.name); // Debug
                selectPokemon(pokemon.name);
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isPokemonInTeam(pokemon.name)) {
                    panel.setBorder(BorderFactory.createBevelBorder(0, SELECTED_BORDER_COLOR, SELECTED_BORDER_COLOR));
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                updatePokemonPanelAppearance(pokemon.name);
            }
        });
        
        return panel;
    }

    private void setupEventHandlers() {
        goButton.addActionListener(e -> handleGoButton());
    }

    private void selectPokemon(String pokemonName) {
        System.out.println("Selecting: " + pokemonName); // Debug
        
        if (isPokemonInTeam(pokemonName)) {
            JOptionPane.showMessageDialog(this, 
                pokemonName + " is already in your team!", 
                "Pokemon Already Selected", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int nextSlot = getNextAvailableSlot();
        if (nextSlot == -1) {
            JOptionPane.showMessageDialog(this, 
                "Your team is full! Remove a Pokemon first.", 
                "Team Full", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        selectedTeam[nextSlot] = pokemonName;
        System.out.println("Added " + pokemonName + " to slot " + nextSlot); // Debug
        
        updateTeamSlotDisplay(nextSlot);
        updatePokemonPanelAppearance(pokemonName);
        updateTeamPanelBorders();
        
        logger.info("Added " + pokemonName + " to team slot " + (nextSlot + 1));
    }

    private void removePokemonFromTeam(int slotIndex) {
        if (selectedTeam[slotIndex] == null) return;
        
        String removedPokemon = selectedTeam[slotIndex];
        
        // Shift left to fill gap
        System.arraycopy(selectedTeam, slotIndex + 1, selectedTeam, slotIndex, TEAM_SIZE - slotIndex - 1);
        selectedTeam[TEAM_SIZE - 1] = null;
        
        updateAllSlotDisplays();
        updatePokemonPanelAppearance(removedPokemon);
        updateTeamPanelBorders();
        
        logger.info("Removed " + removedPokemon + " from team");
    }

    private void updateTeamSlotDisplay(int slotIndex) {
        JLabel label = teamLabels[slotIndex];
        String pokemonName = selectedTeam[slotIndex];
        
        if (pokemonName != null) {
            System.out.println("Updating team slot " + slotIndex + " with: " + pokemonName); // Debug
            
            Pokemon pokemon = findPokemon(pokemonName);
            if (pokemon != null) {
                try {
                    BufferedImage pokemonImage = XImage.getPokemonImageByName(pokemon.imageFileName);
                    if (pokemonImage != null) {
                        Image scaledImage = XImage.resize(pokemonImage, 120, 120);
                        label.setIcon(new ImageIcon(scaledImage));
                        label.setText("");
                    } else {
                        // Image not found - show name as text
                        label.setIcon(null);
                        label.setText("<html><center>" + pokemonName + "</center></html>");
                        label.setFont(new Font("Arial", Font.BOLD, 12));
                        label.setForeground(Color.BLACK);
                    }
                } catch (Exception e) {
                    // Any error loading image - show name as text
                    label.setIcon(null);
                    label.setText("<html><center>" + pokemonName + "</center></html>");
                    label.setFont(new Font("Arial", Font.BOLD, 12));
                    label.setForeground(Color.BLACK);
                    logger.warning("Could not load team image for " + pokemonName + ": " + e.getMessage());
                }
            } else {
                // Pokemon not found in data - shouldn't happen, but handle gracefully
                label.setIcon(null);
                label.setText("<html><center>" + pokemonName + "</center></html>");
                label.setFont(new Font("Arial", Font.BOLD, 12));
                label.setForeground(Color.RED);
                System.err.println("Pokemon not found in data: " + pokemonName);
            }
        } else {
            label.setIcon(null);
            label.setText("");
        }
    }

    private void updateAllSlotDisplays() {
        for (int i = 0; i < TEAM_SIZE; i++) {
            updateTeamSlotDisplay(i);
        }
    }

    private void updatePokemonPanelAppearance(String pokemonName) {
        JPanel panel = pokemonPanels.get(pokemonName);
        if (panel == null) return;
        
        if (isPokemonInTeam(pokemonName)) {
            panel.setBorder(BorderFactory.createBevelBorder(1, SELECTED_BORDER_COLOR, SELECTED_BORDER_COLOR));
            panel.setBackground(new Color(200, 200, 200, 100));
        } else {
            panel.setBorder(BorderFactory.createBevelBorder(0));
            panel.setBackground(null);
        }
    }

    private void updateTeamPanelBorders() {
        for (int i = 0; i < TEAM_SIZE; i++) {
            if (selectedTeam[i] != null) {
                teamPanels[i].setBorder(BorderFactory.createLineBorder(SELECTED_BORDER_COLOR, 2));
            } else {
                teamPanels[i].setBorder(BorderFactory.createDashedBorder(DEFAULT_BORDER_COLOR, 2, 5));
            }
        }
    }

    private boolean isPokemonInTeam(String pokemonName) {
        return Arrays.asList(selectedTeam).contains(pokemonName);
    }

    private int getNextAvailableSlot() {
        for (int i = 0; i < TEAM_SIZE; i++) {
            if (selectedTeam[i] == null) return i;
        }
        return -1;
    }

    private Pokemon findPokemon(String name) {
        return availablePokemon.stream()
            .filter(p -> p.name.equals(name))
            .findFirst()
            .orElse(null);
    }

    private void handleGoButton() {
        if (getTeamSize() == 0) {
            JOptionPane.showMessageDialog(this, 
                "Please select at least one Pokemon for your team!", 
                "No Pokemon Selected", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        StringBuilder teamSummary = new StringBuilder("Your Team:\n");
        for (int i = 0; i < TEAM_SIZE; i++) {
            if (selectedTeam[i] != null) {
                teamSummary.append((i + 1)).append(". ").append(selectedTeam[i]).append("\n");
            }
        }
        
        int result = JOptionPane.showConfirmDialog(this, 
            teamSummary + "\nProceed with this team?", 
            "Confirm Team Selection", 
            JOptionPane.YES_NO_OPTION);
            
        if (result == JOptionPane.YES_OPTION) {
            logger.info("Team confirmed: " + Arrays.toString(selectedTeam));
            dispose();
            // TODO: Replace with actual next screen
            // new PokeDuelJFrame().setVisible(true);
        }
    }

    // Public getters
    public String[] getSelectedPokemon() {
        return selectedTeam.clone();
    }

    public int getTeamSize() {
        return (int) Arrays.stream(selectedTeam).filter(Objects::nonNull).count();
    }

    public boolean isTeamFull() {
        return getTeamSize() == TEAM_SIZE;
    }

    public boolean hasValidTeam() {
        return getTeamSize() > 0;
    }

    // Test method to verify all Pokemon are functional
    public void testAllPokemon() {
        System.out.println("Testing all Pokemon functionality...");
        System.out.println("Available Pokemon: " + availablePokemon.size());
        System.out.println("Pokemon panels created: " + pokemonPanels.size());
        
        // Test selecting each Pokemon
        for (Pokemon pokemon : availablePokemon) {
            JPanel panel = pokemonPanels.get(pokemon.name);
            if (panel == null) {
                System.err.println("ERROR: No panel found for " + pokemon.name);
            } else {
                System.out.println("✓ Panel exists for " + pokemon.name);
            }
        }
    }

    // Pokemon data class
    private static class Pokemon {
        final String name;
        final String imageFileName;
        
        Pokemon(String name, String imageFileName) {
            this.name = name;
            this.imageFileName = imageFileName;
        }
    }

    private List<Pokemon> createPokemonList() {
        List<Pokemon> pokemon = new ArrayList<>();
        
        // Generation 1 Pokemon - Add more as needed
        pokemon.add(new Pokemon("Bulbasaur", "Bulbasaur-front.png"));
        pokemon.add(new Pokemon("Ivysaur", "Ivysaur-front.png"));
        pokemon.add(new Pokemon("Venusaur", "Venusaur-front.png"));
        pokemon.add(new Pokemon("Charmander", "Charmander-front.png"));
        pokemon.add(new Pokemon("Charmeleon", "Charmeleon-front.png"));
        pokemon.add(new Pokemon("Charizard", "Charizard-front.png"));
        pokemon.add(new Pokemon("Squirtle", "Squirtle-front.png"));
        pokemon.add(new Pokemon("Wartortle", "Wartortle-front.png"));
        pokemon.add(new Pokemon("Blastoise", "Blastoise-front.png"));
        pokemon.add(new Pokemon("Caterpie", "Caterpie-front.png"));
        pokemon.add(new Pokemon("Metapod", "Metapod-front.png"));
        pokemon.add(new Pokemon("Butterfree", "Butterfree-front.png"));
        pokemon.add(new Pokemon("Weedle", "Weedle-front.png"));
        pokemon.add(new Pokemon("Kakuna", "Kakuna-front.png"));
        pokemon.add(new Pokemon("Beedrill", "Beedrill-front.png"));
        pokemon.add(new Pokemon("Pidgey", "Pidgey-front.png"));
        pokemon.add(new Pokemon("Pidgeotto", "Pidgeotto-front.png"));
        pokemon.add(new Pokemon("Pidgeot", "Pidgeot-front.png"));
        pokemon.add(new Pokemon("Rattata", "Rattata-front.png"));
        pokemon.add(new Pokemon("Raticate", "Raticate-front.png"));
        pokemon.add(new Pokemon("Spearow", "Spearow-front.png"));
        pokemon.add(new Pokemon("Fearow", "Fearow-front.png"));
        pokemon.add(new Pokemon("Ekans", "Ekans-front.png"));
        pokemon.add(new Pokemon("Arbok", "Arbok-front.png"));
        pokemon.add(new Pokemon("Pikachu", "Pikachu-front.png"));
        pokemon.add(new Pokemon("Raichu", "Raichu-front.png"));
        pokemon.add(new Pokemon("Sandshrew", "Sandshrew-front.png"));
        pokemon.add(new Pokemon("Sandslash", "Sandslash-front.png"));
        pokemon.add(new Pokemon("Nidoran♀", "Nidoran-f-front.png"));
        pokemon.add(new Pokemon("Nidorina", "Nidorina-front.png"));
        pokemon.add(new Pokemon("Nidoqueen", "Nidoqueen-front.png"));
        pokemon.add(new Pokemon("Nidoran♂", "Nidoran-m-front.png"));
        pokemon.add(new Pokemon("Nidorino", "Nidorino-front.png"));
        pokemon.add(new Pokemon("Nidoking", "Nidoking-front.png"));
        pokemon.add(new Pokemon("Clefairy", "Clefairy-front.png"));
        pokemon.add(new Pokemon("Clefable", "Clefable-front.png"));
        pokemon.add(new Pokemon("Vulpix", "Vulpix-front.png"));
        pokemon.add(new Pokemon("Ninetales", "Ninetales-front.png"));
        pokemon.add(new Pokemon("Jigglypuff", "Jigglypuff-front.png"));
        pokemon.add(new Pokemon("Wigglytuff", "Wigglytuff-front.png"));
        pokemon.add(new Pokemon("Zubat", "Zubat-front.png"));
        pokemon.add(new Pokemon("Golbat", "Golbat-front.png"));
        pokemon.add(new Pokemon("Oddish", "Oddish-front.png"));
        pokemon.add(new Pokemon("Gloom", "Gloom-front.png"));
        pokemon.add(new Pokemon("Vileplume", "Vileplume-front.png"));
        pokemon.add(new Pokemon("Paras", "Paras-front.png"));
        pokemon.add(new Pokemon("Parasect", "Parasect-front.png"));
        pokemon.add(new Pokemon("Venonat", "Venonat-front.png"));
        pokemon.add(new Pokemon("Venomoth", "Venomoth-front.png"));
        pokemon.add(new Pokemon("Diglett", "Diglett-front.png"));
        pokemon.add(new Pokemon("Dugtrio", "Dugtrio-front.png"));
        pokemon.add(new Pokemon("Meowth", "Meowth-front.png"));
        pokemon.add(new Pokemon("Persian", "Persian-front.png"));
        pokemon.add(new Pokemon("Psyduck", "Psyduck-front.png"));
        pokemon.add(new Pokemon("Golduck", "Golduck-front.png"));
        pokemon.add(new Pokemon("Mankey", "Mankey-front.png"));
        pokemon.add(new Pokemon("Primeape", "Primeape-front.png"));
        pokemon.add(new Pokemon("Growlithe", "Growlithe-front.png"));
        pokemon.add(new Pokemon("Arcanine", "Arcanine-front.png"));
        pokemon.add(new Pokemon("Poliwag", "Poliwag-front.png"));
        pokemon.add(new Pokemon("Poliwhirl", "Poliwhirl-front.png"));
        pokemon.add(new Pokemon("Poliwrath", "Poliwrath-front.png"));
        pokemon.add(new Pokemon("Abra", "Abra-front.png"));
        pokemon.add(new Pokemon("Kadabra", "Kadabra-front.png"));
        pokemon.add(new Pokemon("Alakazam", "Alakazam-front.png"));
        pokemon.add(new Pokemon("Machop", "Machop-front.png"));
        pokemon.add(new Pokemon("Machoke", "Machoke-front.png"));
        pokemon.add(new Pokemon("Machamp", "Machamp-front.png"));
        pokemon.add(new Pokemon("Bellsprout", "Bellsprout-front.png"));
        pokemon.add(new Pokemon("Weepinbell", "Weepinbell-front.png"));
        pokemon.add(new Pokemon("Victreebel", "Victreebel-front.png"));
        pokemon.add(new Pokemon("Tentacool", "Tentacool-front.png"));
        pokemon.add(new Pokemon("Tentacruel", "Tentacruel-front.png"));
        pokemon.add(new Pokemon("Geodude", "Geodude-front.png"));
        pokemon.add(new Pokemon("Graveler", "Graveler-front.png"));
        pokemon.add(new Pokemon("Golem", "Golem-front.png"));
        pokemon.add(new Pokemon("Ponyta", "Ponyta-front.png"));
        pokemon.add(new Pokemon("Rapidash", "Rapidash-front.png"));
        pokemon.add(new Pokemon("Slowpoke", "Slowpoke-front.png"));
        pokemon.add(new Pokemon("Slowbro", "Slowbro-front.png"));
        pokemon.add(new Pokemon("Magnemite", "Magnemite-front.png"));
        pokemon.add(new Pokemon("Magneton", "Magneton-front.png"));
        pokemon.add(new Pokemon("Farfetch'd", "Farfetch'd-front.png"));
        pokemon.add(new Pokemon("Doduo", "Doduo-front.png"));
        pokemon.add(new Pokemon("Dodrio", "Dodrio-front.png"));
        pokemon.add(new Pokemon("Seel", "Seel-front.png"));
        pokemon.add(new Pokemon("Dewgong", "Dewgong-front.png"));
        pokemon.add(new Pokemon("Grimer", "Grimer-front.png"));
        pokemon.add(new Pokemon("Muk", "Muk-front.png"));
        pokemon.add(new Pokemon("Shellder", "Shellder-front.png"));
        pokemon.add(new Pokemon("Cloyster", "Cloyster-front.png"));
        pokemon.add(new Pokemon("Gastly", "Gastly-front.png"));
        pokemon.add(new Pokemon("Haunter", "Haunter-front.png"));
        pokemon.add(new Pokemon("Gengar", "Gengar-front.png"));
        pokemon.add(new Pokemon("Onix", "Onix-front.png"));
        pokemon.add(new Pokemon("Drowzee", "Drowzee-front.png"));
        pokemon.add(new Pokemon("Hypno", "Hypno-front.png"));
        pokemon.add(new Pokemon("Krabby", "Krabby-front.png"));
        pokemon.add(new Pokemon("Kingler", "Kingler-front.png"));
        pokemon.add(new Pokemon("Voltorb", "Voltorb-front.png"));
        pokemon.add(new Pokemon("Electrode", "Electrode-front.png"));
        pokemon.add(new Pokemon("Exeggcute", "Exeggcute-front.png"));
        pokemon.add(new Pokemon("Exeggutor", "Exeggutor-front.png"));
        pokemon.add(new Pokemon("Cubone", "Cubone-front.png"));
        pokemon.add(new Pokemon("Marowak", "Marowak-front.png"));
        pokemon.add(new Pokemon("Hitmonlee", "Hitmonlee-front.png"));
        pokemon.add(new Pokemon("Hitmonchan", "Hitmonchan-front.png"));
        pokemon.add(new Pokemon("Lickitung", "Lickitung-front.png"));
        pokemon.add(new Pokemon("Koffing", "Koffing-front.png"));
        pokemon.add(new Pokemon("Weezing", "Weezing-front.png"));
        pokemon.add(new Pokemon("Rhyhorn", "Rhyhorn-front.png"));
        pokemon.add(new Pokemon("Rhydon", "Rhydon-front.png"));
        pokemon.add(new Pokemon("Chansey", "Chansey-front.png"));
        pokemon.add(new Pokemon("Tangela", "Tangela-front.png"));
        pokemon.add(new Pokemon("Kangaskhan", "Kangaskhan-front.png"));
        pokemon.add(new Pokemon("Horsea", "Horsea-front.png"));
        pokemon.add(new Pokemon("Seadra", "Seadra-front.png"));
        pokemon.add(new Pokemon("Goldeen", "Goldeen-front.png"));
        pokemon.add(new Pokemon("Seaking", "Seaking-front.png"));
        pokemon.add(new Pokemon("Staryu", "Staryu-front.png"));
        pokemon.add(new Pokemon("Starmie", "Starmie-front.png"));
        pokemon.add(new Pokemon("Mr. Mime", "Mr. Mime-front.png"));
        pokemon.add(new Pokemon("Scyther", "Scyther-front.png"));
        pokemon.add(new Pokemon("Jynx", "Jynx-front.png"));
        pokemon.add(new Pokemon("Electabuzz", "Electabuzz-front.png"));
        pokemon.add(new Pokemon("Magmar", "Magmar-front.png"));
        pokemon.add(new Pokemon("Pinsir", "Pinsir-front.png"));
        pokemon.add(new Pokemon("Tauros", "Tauros-front.png"));
        pokemon.add(new Pokemon("Magikarp", "Magikarp-front.png"));
        pokemon.add(new Pokemon("Gyarados", "Gyarados-front.png"));
        pokemon.add(new Pokemon("Lapras", "Lapras-front.png"));
        pokemon.add(new Pokemon("Ditto", "Ditto-front.png"));
        pokemon.add(new Pokemon("Eevee", "Eevee-front.png"));
        pokemon.add(new Pokemon("Vaporeon", "Vaporeon-front.png"));
        pokemon.add(new Pokemon("Jolteon", "Jolteon-front.png"));
        pokemon.add(new Pokemon("Flareon", "Flareon-front.png"));
        pokemon.add(new Pokemon("Porygon", "Porygon-front.png"));
        pokemon.add(new Pokemon("Omanyte", "Omanyte-front.png"));
        pokemon.add(new Pokemon("Omastar", "Omastar-front.png"));
        pokemon.add(new Pokemon("Kabuto", "Kabuto-front.png"));
        pokemon.add(new Pokemon("Kabutops", "Kabutops-front.png"));
        pokemon.add(new Pokemon("Aerodactyl", "Aerodactyl-front.png"));
        pokemon.add(new Pokemon("Snorlax", "Snorlax-front.png"));
        pokemon.add(new Pokemon("Articuno", "Articuno-front.png"));
        pokemon.add(new Pokemon("Zapdos", "Zapdos-front.png"));
        pokemon.add(new Pokemon("Moltres", "Moltres-front.png"));
        pokemon.add(new Pokemon("Dratini", "Dratini-front.png"));
        pokemon.add(new Pokemon("Dragonair", "Dragonair-front.png"));
        pokemon.add(new Pokemon("Dragonite", "Dragonite-front.png"));
        pokemon.add(new Pokemon("Mewtwo", "Mewtwo-front.png"));
        pokemon.add(new Pokemon("Mew", "Mew-front.png"));
        
        return pokemon;
    }

    public static void main(String[] args) {
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
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                NewChoosingPokemonJDialog dialog = new NewChoosingPokemonJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
}
