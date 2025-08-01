/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package pokemon.sim.ui;

import pokemon.sim.entity.Pokemon;
import pokemon.sim.util.XImage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

/**
 *
 * @author May5th
 */
public class ChoosingPokemonInBattleJDialog extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ChoosingPokemonInBattleJDialog.class.getName());
    
    private List<Pokemon> playerTeam;
    private Pokemon currentPokemon;
    private Map<Pokemon, Integer> currentHP;
    private Map<Pokemon, Integer> maxHP;
    private int selectedIndex = -1;
    private boolean selectionMade = false;
    
    // UI Components arrays for easier access
    private JLabel[] pokemonNameLabels;
    private JLabel[] pokemonLevelLabels;
    private JLabel[] pokemonImageLabels;
    private JProgressBar[] pokemonHPBars;
    private JPanel[] pokemonPanels;

    /**
     * Creates new form ChoosingPokemonInBattleJDialog
     */
    public ChoosingPokemonInBattleJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    /**
     * Constructor with team data
     */
    public ChoosingPokemonInBattleJDialog(java.awt.Frame parent, boolean modal, 
                                         List<Pokemon> playerTeam, 
                                         Pokemon currentPokemon,
                                         Map<Pokemon, Integer> currentHP,
                                         Map<Pokemon, Integer> maxHP) {
        super(parent, modal);
        this.playerTeam = playerTeam;
        this.currentPokemon = currentPokemon;
        this.currentHP = currentHP;
        this.maxHP = maxHP;
        
        initComponents();
        initializeUIArrays();
        customizeUI();
        displayPokemonData();
    }
    
    private void initializeUIArrays() {
        // Initialize arrays for easy access to UI components
        pokemonNameLabels = new JLabel[] {
            lblPokemon1Name, lblPokemon2Name, lblPokemon3Name, 
            lblPokemon4Name, lblPokemon5Name
        };
        
        pokemonLevelLabels = new JLabel[] {
            lblPokemon1Level, lblPokemon2Level, lblPokemon3Level,
            lblPokemon4Level, lblPokemon5Level
        };
        
        pokemonImageLabels = new JLabel[] {
            lblPokemon1Image, lblPokemon2Image, lblPokemon3Image,
            lblPokemon4Image, lblPokemon5Image
        };
        
        pokemonHPBars = new JProgressBar[] {
            pgbPokemon1HP, pgbPokemon2HP, pgbPokemon3HP,
            pgbPokemon4HP, pgbPokemon5HP
        };
        
        pokemonPanels = new JPanel[] {
            pnlPokemon1, pnlPokemon2, pnlPokemon3,
            pnlPokemon4, pnlPokemon5
        };
    }
    
    private void customizeUI() {
        // Set dialog properties
        setTitle("Choose a Pokémon");
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        // Customize panels
        for (JPanel panel : pokemonPanels) {
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        // Customize current Pokemon panel
        pnlCurrentPokemon.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 3));
        pnlCurrentPokemon.setBackground(new Color(240, 240, 240));
    }
    
    private void displayPokemonData() {
        if (playerTeam == null || currentHP == null || maxHP == null) {
            logger.warning("Team data not properly initialized");
            return;
        }
        
        // Display current Pokemon
        displayCurrentPokemon();
        
        // Display team members (excluding current Pokemon)
        int displayIndex = 0;
        for (int i = 0; i < playerTeam.size() && displayIndex < 5; i++) {
            Pokemon pokemon = playerTeam.get(i);
            
            // Skip if this is the current Pokemon (already shown separately)
            if (pokemon == currentPokemon) {
                continue;
            }
            
            displayPokemonInSlot(pokemon, displayIndex, i);
            displayIndex++;
        }
        
        // Hide unused slots
        for (int i = displayIndex; i < 5; i++) {
            pokemonPanels[i].setVisible(false);
        }
    }
    
    private void displayCurrentPokemon() {
        if (currentPokemon == null) return;
        
        lblCurrentPokemonName.setText(currentPokemon.getName().toUpperCase());
        lblCurrentPokemonLevel.setText("LV 50"); // Default level
        
        // Display HP
        int current = currentHP.getOrDefault(currentPokemon, 0);
        int max = maxHP.getOrDefault(currentPokemon, 1);
        int percentage = (current * 100) / max;
        
        pgbCurrentPokemonHP.setValue(percentage);
        pgbCurrentPokemonHP.setString(current + "/" + max);
        pgbCurrentPokemonHP.setStringPainted(true);
        updateHPBarColor(pgbCurrentPokemonHP, percentage);
        
        // Display image
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode pokemonJson = mapper.valueToTree(currentPokemon);
            BufferedImage pokemonImage = XImage.getPokemonFrontImage(pokemonJson);
            
            if (pokemonImage != null) {
                Image scaledImage = XImage.resize(pokemonImage, 90, 90);
                lblCurrentPokemonImage.setIcon(new ImageIcon(scaledImage));
            }
        } catch (Exception e) {
            logger.warning("Failed to load current Pokemon image: " + e.getMessage());
        }
    }
    
    private void displayPokemonInSlot(Pokemon pokemon, int slotIndex, int teamIndex) {
        if (slotIndex >= 5) return;
        
        // Set name and level
        pokemonNameLabels[slotIndex].setText(pokemon.getName().toUpperCase());
        pokemonLevelLabels[slotIndex].setText("LV 50");
        
        // Set HP bar
        int current = currentHP.getOrDefault(pokemon, 0);
        int max = maxHP.getOrDefault(pokemon, 1);
        int percentage = (current * 100) / max;
        
        pokemonHPBars[slotIndex].setValue(percentage);
        pokemonHPBars[slotIndex].setString(current + "/" + max);
        pokemonHPBars[slotIndex].setStringPainted(true);
        updateHPBarColor(pokemonHPBars[slotIndex], percentage);
        
        // Display image
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode pokemonJson = mapper.valueToTree(pokemon);
            BufferedImage pokemonImage = XImage.getPokemonFrontImage(pokemonJson);
            
            if (pokemonImage != null) {
                Image scaledImage = XImage.resize(pokemonImage, 60, 60);
                pokemonImageLabels[slotIndex].setIcon(new ImageIcon(scaledImage));
            }
        } catch (Exception e) {
            logger.warning("Failed to load Pokemon image: " + e.getMessage());
        }
        
        // Disable panel if Pokemon has fainted
        if (current <= 0) {
            pokemonPanels[slotIndex].setEnabled(false);
            pokemonPanels[slotIndex].setBackground(new Color(200, 200, 200));
            pokemonNameLabels[slotIndex].setForeground(Color.GRAY);
            pokemonLevelLabels[slotIndex].setForeground(Color.GRAY);
        } else {
            pokemonPanels[slotIndex].setEnabled(true);
            pokemonPanels[slotIndex].setBackground(Color.WHITE);
        }
        
        // Store the actual team index in the panel's client property
        pokemonPanels[slotIndex].putClientProperty("teamIndex", teamIndex);
    }
    
    private void updateHPBarColor(JProgressBar hpBar, int percentage) {
        if (percentage > 50) {
            hpBar.setForeground(new Color(51, 255, 0)); // Green
        } else if (percentage > 20) {
            hpBar.setForeground(Color.YELLOW);
        } else {
            hpBar.setForeground(Color.RED);
        }
    }
    
    private void selectPokemon(int slotIndex) {
        // Get the actual team index from the panel
        Integer teamIndex = (Integer) pokemonPanels[slotIndex].getClientProperty("teamIndex");
        if (teamIndex == null) return;
        
        Pokemon selectedPokemon = playerTeam.get(teamIndex);
        
        // Check if Pokemon has fainted
        if (currentHP.getOrDefault(selectedPokemon, 0) <= 0) {
            JOptionPane.showMessageDialog(this, 
                selectedPokemon.getName() + " has no energy left to battle!",
                "Cannot Select", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Check if it's the current Pokemon
        if (selectedPokemon == currentPokemon) {
            JOptionPane.showMessageDialog(this, 
                selectedPokemon.getName() + " is already in battle!",
                "Cannot Select", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Confirm selection
        int result = JOptionPane.showConfirmDialog(this,
            "Switch to " + selectedPokemon.getName() + "?",
            "Confirm Switch",
            JOptionPane.YES_NO_OPTION);
            
        if (result == JOptionPane.YES_OPTION) {
            selectedIndex = teamIndex;
            selectionMade = true;
            dispose();
        }
    }
    
    // Public getters
    public int getSelectedIndex() {
        return selectedIndex;
    }
    
    public boolean isSelectionMade() {
        return selectionMade;
    }
    
    public Pokemon getSelectedPokemon() {
        if (selectionMade && selectedIndex >= 0 && selectedIndex < playerTeam.size()) {
            return playerTeam.get(selectedIndex);
        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBackground = new javax.swing.JPanel();
        pnlText = new javax.swing.JPanel();
        lblChooseAPokemon = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        pnlCurrentPokemon = new javax.swing.JPanel();
        pgbCurrentPokemonHP = new javax.swing.JProgressBar();
        lblCurrentPokemonImage = new javax.swing.JLabel();
        lblCurrentPokemonName = new javax.swing.JLabel();
        lblCurrentPokemonLevel = new javax.swing.JLabel();
        pnlPokemon1 = new javax.swing.JPanel();
        lblPokemon1Name = new javax.swing.JLabel();
        pgbPokemon1HP = new javax.swing.JProgressBar();
        lblPokemon1Level = new javax.swing.JLabel();
        lblPokemon1Image = new javax.swing.JLabel();
        pnlPokemon2 = new javax.swing.JPanel();
        lblPokemon2Name = new javax.swing.JLabel();
        pgbPokemon2HP = new javax.swing.JProgressBar();
        lblPokemon2Level = new javax.swing.JLabel();
        lblPokemon2Image = new javax.swing.JLabel();
        pnlPokemon3 = new javax.swing.JPanel();
        lblPokemon3Name = new javax.swing.JLabel();
        pgbPokemon3HP = new javax.swing.JProgressBar();
        lblPokemon3Level = new javax.swing.JLabel();
        lblPokemon3Image = new javax.swing.JLabel();
        pnlPokemon4 = new javax.swing.JPanel();
        lblPokemon4Name = new javax.swing.JLabel();
        pgbPokemon4HP = new javax.swing.JProgressBar();
        lblPokemon4Level = new javax.swing.JLabel();
        lblPokemon4Image = new javax.swing.JLabel();
        pnlPokemon5 = new javax.swing.JPanel();
        lblPokemon5Name = new javax.swing.JLabel();
        pgbPokemon5HP = new javax.swing.JProgressBar();
        lblPokemon5Level = new javax.swing.JLabel();
        lblPokemon5Image = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlBackground.setBackground(new java.awt.Color(0, 153, 255));

        lblChooseAPokemon.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblChooseAPokemon.setText("Choose a POKÉMON.");

        javax.swing.GroupLayout pnlTextLayout = new javax.swing.GroupLayout(pnlText);
        pnlText.setLayout(pnlTextLayout);
        pnlTextLayout.setHorizontalGroup(
            pnlTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTextLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(lblChooseAPokemon, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlTextLayout.setVerticalGroup(
            pnlTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblChooseAPokemon, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnCancel.setBackground(new java.awt.Color(255, 51, 0));
        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("CANCEL");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        pgbCurrentPokemonHP.setForeground(new java.awt.Color(51, 255, 0));

        lblCurrentPokemonName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblCurrentPokemonName.setText("CURRENT POKÉMON");

        lblCurrentPokemonLevel.setText("LV XX");

        javax.swing.GroupLayout pnlCurrentPokemonLayout = new javax.swing.GroupLayout(pnlCurrentPokemon);
        pnlCurrentPokemon.setLayout(pnlCurrentPokemonLayout);
        pnlCurrentPokemonLayout.setHorizontalGroup(
            pnlCurrentPokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCurrentPokemonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCurrentPokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pgbCurrentPokemonHP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlCurrentPokemonLayout.createSequentialGroup()
                        .addComponent(lblCurrentPokemonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlCurrentPokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCurrentPokemonName, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                            .addComponent(lblCurrentPokemonLevel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnlCurrentPokemonLayout.setVerticalGroup(
            pnlCurrentPokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCurrentPokemonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCurrentPokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCurrentPokemonImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlCurrentPokemonLayout.createSequentialGroup()
                        .addComponent(lblCurrentPokemonName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCurrentPokemonLevel)
                        .addGap(0, 50, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pgbCurrentPokemonHP, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlPokemon1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlPokemon1MouseClicked(evt);
            }
        });

        lblPokemon1Name.setText("POKÉMON 1");

        lblPokemon1Level.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPokemon1Level.setText("LV XX");

        javax.swing.GroupLayout pnlPokemon1Layout = new javax.swing.GroupLayout(pnlPokemon1);
        pnlPokemon1.setLayout(pnlPokemon1Layout);
        pnlPokemon1Layout.setHorizontalGroup(
            pnlPokemon1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPokemon1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPokemon1Image, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPokemon1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pgbPokemon1HP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlPokemon1Layout.createSequentialGroup()
                        .addComponent(lblPokemon1Name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPokemon1Level, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlPokemon1Layout.setVerticalGroup(
            pnlPokemon1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPokemon1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPokemon1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPokemon1Image, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlPokemon1Layout.createSequentialGroup()
                        .addGroup(pnlPokemon1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPokemon1Name)
                            .addComponent(lblPokemon1Level))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addComponent(pgbPokemon1HP, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pnlPokemon2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlPokemon2MouseClicked(evt);
            }
        });

        lblPokemon2Name.setText("POKÉMON 2");

        lblPokemon2Level.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPokemon2Level.setText("LV XX");

        javax.swing.GroupLayout pnlPokemon2Layout = new javax.swing.GroupLayout(pnlPokemon2);
        pnlPokemon2.setLayout(pnlPokemon2Layout);
        pnlPokemon2Layout.setHorizontalGroup(
            pnlPokemon2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPokemon2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPokemon2Image, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPokemon2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pgbPokemon2HP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlPokemon2Layout.createSequentialGroup()
                        .addComponent(lblPokemon2Name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPokemon2Level, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlPokemon2Layout.setVerticalGroup(
            pnlPokemon2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPokemon2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPokemon2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPokemon2Image, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlPokemon2Layout.createSequentialGroup()
                        .addGroup(pnlPokemon2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPokemon2Name)
                            .addComponent(lblPokemon2Level))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addComponent(pgbPokemon2HP, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pnlPokemon3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlPokemon3MouseClicked(evt);
            }
        });

        lblPokemon3Name.setText("POKÉMON 3");

        lblPokemon3Level.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPokemon3Level.setText("LV XX");

        javax.swing.GroupLayout pnlPokemon3Layout = new javax.swing.GroupLayout(pnlPokemon3);
        pnlPokemon3.setLayout(pnlPokemon3Layout);
        pnlPokemon3Layout.setHorizontalGroup(
            pnlPokemon3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPokemon3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPokemon3Image, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPokemon3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pgbPokemon3HP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlPokemon3Layout.createSequentialGroup()
                        .addComponent(lblPokemon3Name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPokemon3Level, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlPokemon3Layout.setVerticalGroup(
            pnlPokemon3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPokemon3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPokemon3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPokemon3Image, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlPokemon3Layout.createSequentialGroup()
                        .addGroup(pnlPokemon3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPokemon3Name)
                            .addComponent(lblPokemon3Level))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addComponent(pgbPokemon3HP, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pnlPokemon4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlPokemon4MouseClicked(evt);
            }
        });

        lblPokemon4Name.setText("POKÉMON 4");

        lblPokemon4Level.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPokemon4Level.setText("LV XX");

        javax.swing.GroupLayout pnlPokemon4Layout = new javax.swing.GroupLayout(pnlPokemon4);
        pnlPokemon4.setLayout(pnlPokemon4Layout);
        pnlPokemon4Layout.setHorizontalGroup(
            pnlPokemon4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPokemon4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPokemon4Image, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPokemon4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pgbPokemon4HP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlPokemon4Layout.createSequentialGroup()
                        .addComponent(lblPokemon4Name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPokemon4Level, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlPokemon4Layout.setVerticalGroup(
            pnlPokemon4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPokemon4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPokemon4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPokemon4Image, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlPokemon4Layout.createSequentialGroup()
                        .addGroup(pnlPokemon4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPokemon4Name)
                            .addComponent(lblPokemon4Level))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addComponent(pgbPokemon4HP, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pnlPokemon5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlPokemon5MouseClicked(evt);
            }
        });

        lblPokemon5Name.setText("POKÉMON 5");

        lblPokemon5Level.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPokemon5Level.setText("LV XX");

        javax.swing.GroupLayout pnlPokemon5Layout = new javax.swing.GroupLayout(pnlPokemon5);
        pnlPokemon5.setLayout(pnlPokemon5Layout);
        pnlPokemon5Layout.setHorizontalGroup(
            pnlPokemon5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPokemon5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPokemon5Image, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPokemon5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pgbPokemon5HP, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                    .addGroup(pnlPokemon5Layout.createSequentialGroup()
                        .addComponent(lblPokemon5Name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPokemon5Level, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlPokemon5Layout.setVerticalGroup(
            pnlPokemon5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPokemon5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPokemon5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPokemon5Image, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlPokemon5Layout.createSequentialGroup()
                        .addGroup(pnlPokemon5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPokemon5Name)
                            .addComponent(lblPokemon5Level))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addComponent(pgbPokemon5HP, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlBackgroundLayout = new javax.swing.GroupLayout(pnlBackground);
        pnlBackground.setLayout(pnlBackgroundLayout);
        pnlBackgroundLayout.setHorizontalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBackgroundLayout.createSequentialGroup()
                        .addComponent(pnlText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBackgroundLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(pnlCurrentPokemon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlPokemon5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlPokemon4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlPokemon1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlPokemon2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlPokemon3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnlBackgroundLayout.setVerticalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBackgroundLayout.createSequentialGroup()
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBackgroundLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlPokemon1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlPokemon2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlPokemon3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlBackgroundLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(pnlCurrentPokemon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPokemon4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPokemon5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pnlPokemon1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlPokemon1MouseClicked
        // TODO add your handling code here:
        if (pnlPokemon1.isEnabled()) {
            selectPokemon(0);
        }
    }//GEN-LAST:event_pnlPokemon1MouseClicked

    private void pnlPokemon2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlPokemon2MouseClicked
        // TODO add your handling code here:
        if (pnlPokemon2.isEnabled()) {
            selectPokemon(1);
        }
    }//GEN-LAST:event_pnlPokemon2MouseClicked

    private void pnlPokemon3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlPokemon3MouseClicked
        // TODO add your handling code here:
        if (pnlPokemon3.isEnabled()) {
            selectPokemon(2);
        }
    }//GEN-LAST:event_pnlPokemon3MouseClicked

    private void pnlPokemon4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlPokemon4MouseClicked
        // TODO add your handling code here:
        if (pnlPokemon4.isEnabled()) {
            selectPokemon(3);
        }
    }//GEN-LAST:event_pnlPokemon4MouseClicked

    private void pnlPokemon5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlPokemon5MouseClicked
        // TODO add your handling code here:
        if (pnlPokemon5.isEnabled()) {
            selectPokemon(4);
        }
    }//GEN-LAST:event_pnlPokemon5MouseClicked

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        selectionMade = false;
        selectedIndex = -1;
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

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

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ChoosingPokemonInBattleJDialog dialog = new ChoosingPokemonInBattleJDialog(new javax.swing.JFrame(), true);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JLabel lblChooseAPokemon;
    private javax.swing.JLabel lblCurrentPokemonImage;
    private javax.swing.JLabel lblCurrentPokemonLevel;
    private javax.swing.JLabel lblCurrentPokemonName;
    private javax.swing.JLabel lblPokemon1Image;
    private javax.swing.JLabel lblPokemon1Level;
    private javax.swing.JLabel lblPokemon1Name;
    private javax.swing.JLabel lblPokemon2Image;
    private javax.swing.JLabel lblPokemon2Level;
    private javax.swing.JLabel lblPokemon2Name;
    private javax.swing.JLabel lblPokemon3Image;
    private javax.swing.JLabel lblPokemon3Level;
    private javax.swing.JLabel lblPokemon3Name;
    private javax.swing.JLabel lblPokemon4Image;
    private javax.swing.JLabel lblPokemon4Level;
    private javax.swing.JLabel lblPokemon4Name;
    private javax.swing.JLabel lblPokemon5Image;
    private javax.swing.JLabel lblPokemon5Level;
    private javax.swing.JLabel lblPokemon5Name;
    private javax.swing.JProgressBar pgbCurrentPokemonHP;
    private javax.swing.JProgressBar pgbPokemon1HP;
    private javax.swing.JProgressBar pgbPokemon2HP;
    private javax.swing.JProgressBar pgbPokemon3HP;
    private javax.swing.JProgressBar pgbPokemon4HP;
    private javax.swing.JProgressBar pgbPokemon5HP;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JPanel pnlCurrentPokemon;
    private javax.swing.JPanel pnlPokemon1;
    private javax.swing.JPanel pnlPokemon2;
    private javax.swing.JPanel pnlPokemon3;
    private javax.swing.JPanel pnlPokemon4;
    private javax.swing.JPanel pnlPokemon5;
    private javax.swing.JPanel pnlText;
    // End of variables declaration//GEN-END:variables
}
