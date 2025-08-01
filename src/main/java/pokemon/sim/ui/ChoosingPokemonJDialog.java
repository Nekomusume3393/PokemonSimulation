/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package pokemon.sim.ui;

import pokemon.sim.util.XImage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author May5th
 */
public class ChoosingPokemonJDialog extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ChoosingPokemonJDialog.class.getName());

    private String[] selectedPokemon = new String[6]; // Array to store selected Pokemon names
    private JLabel[] teamLabels; // Array of team slot labels
    private JPanel[] teamPanels; // Array of team slot panels
    private Map<String, String> pokemonImageMap; // Map Pokemon names to image files

    // Constants
    private static final Color SELECTED_BORDER_COLOR = new Color(255, 215, 0); // Gold
    private static final Color DEFAULT_BORDER_COLOR = Color.GRAY;
    private static final int TEAM_SIZE = 6;

    /**
     * Creates new form ChoosePokemon
     */
    public ChoosingPokemonJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initializePokemonSelection();
    }

    private void initializePokemonSelection() {
        // Initialize team arrays
        teamLabels = new JLabel[]{lblFirstPokemon, lblSecondPokemon, lblThirdPokemon,
            lblFourthPokemon, lblFifthPokemon, lblSixthPokemon};
        teamPanels = new JPanel[]{pnlFirstPokemon, pnlSecondPokemon, pnlThirdPokemon,
            pnlFourthPokemon, pblFifthPokemon, pnlSixthPokemon};

        // Initialize Pokemon image mapping
        initializePokemonImageMap();

        // Add click listeners to available Pokemon panels
        addPokemonClickListeners();

        // Add click listeners to team slot panels
        addTeamSlotClickListeners();

        // Set initial borders for team panels
        updateTeamPanelBorders();

        logger.info("Pokemon selection system initialized");
    }

    /**
     * Initialize mapping of Pokemon names to their image files
     */
    private void initializePokemonImageMap() {
        pokemonImageMap = new HashMap<>();
        pokemonImageMap.put("Charizard", "Charizard-front.png");
        pokemonImageMap.put("Blastoise", "Blastoise-front.png");
        pokemonImageMap.put("Venusaur", "Venusaur-front.png");
        pokemonImageMap.put("Pikachu", "Pikachu-front.png");
        pokemonImageMap.put("Nidoking", "Nidoking-front.png");
        pokemonImageMap.put("Clefable", "Clefable-front.png");

        pokemonImageMap.put("Bulbasaur", "Bulbasaur-front.png");
        pokemonImageMap.put("Ivysaur", "Ivysaur-front.png");
        pokemonImageMap.put("Charmander", "Charmander-front.png");
        pokemonImageMap.put("Charmeleon", "Charmeleon-front.png");
        pokemonImageMap.put("Squirtle", "Squirtle-front.png");
        pokemonImageMap.put("Wartortle", "Wartortle-front.png");
        pokemonImageMap.put("Caterpie", "Caterpie-front.png");
        pokemonImageMap.put("Metapod", "Metapod-front.png");
        pokemonImageMap.put("Butterfree", "Butterfree-front.png");
        pokemonImageMap.put("Weedle", "Weedle-front.png");
        pokemonImageMap.put("Kakuna", "Kakuna-front.png");
        pokemonImageMap.put("Beedrill", "Beedrill-front.png");
        pokemonImageMap.put("Pidgey", "Pidgey-front.png");
        pokemonImageMap.put("Pidgeotto", "Pidgeotto-front.png");
        pokemonImageMap.put("Pidgeot", "Pidgeot-front.png");
        pokemonImageMap.put("Rattata", "Rattata-front.png");
        pokemonImageMap.put("Raticate", "Raticate-front.png");
        pokemonImageMap.put("Spearow", "Spearow-front.png");
        pokemonImageMap.put("Fearow", "Fearow-front.png");
        pokemonImageMap.put("Ekans", "Ekans-front.png");
        pokemonImageMap.put("Arbok", "Arbok-front.png");
        pokemonImageMap.put("Raichu", "Raichu-front.png");
        pokemonImageMap.put("Sandshrew", "Sandshrew-front.png");
        pokemonImageMap.put("Sandslash", "Sandslash-front.png");
        pokemonImageMap.put("Nidoran♀", "Nidoran-f-front.png");
        pokemonImageMap.put("Nidorina", "Nidorina-front.png");
        pokemonImageMap.put("Nidoqueen", "Nidoqueen-front.png");
        pokemonImageMap.put("Nidoran♂", "Nidoran-m-front.png");
        pokemonImageMap.put("Nidorino", "Nidorino-front.png");
        pokemonImageMap.put("Clefairy", "Clefairy-front.png");
        pokemonImageMap.put("Vulpix", "Vulpix-front.png");
        pokemonImageMap.put("Ninetales", "Ninetales-front.png");
        pokemonImageMap.put("Jigglypuff", "Jigglypuff-front.png");
        pokemonImageMap.put("Wigglytuff", "Wigglytuff-front.png");
        pokemonImageMap.put("Zubat", "Zubat-front.png");
        pokemonImageMap.put("Golbat", "Golbat-front.png");
        pokemonImageMap.put("Oddish", "Oddish-front.png");
        pokemonImageMap.put("Gloom", "Gloom-front.png");
        pokemonImageMap.put("Vileplume", "Vileplume-front.png");
        pokemonImageMap.put("Paras", "Paras-front.png");
        pokemonImageMap.put("Parasect", "Parasect-front.png");
        pokemonImageMap.put("Venonat", "Venonat-front.png");
        pokemonImageMap.put("Venomoth", "Venomoth-front.png");
        pokemonImageMap.put("Diglett", "Diglett-front.png");
        pokemonImageMap.put("Dugtrio", "Dugtrio-front.png");
        pokemonImageMap.put("Meowth", "Meowth-front.png");
        pokemonImageMap.put("Persian", "Persian-front.png");
        pokemonImageMap.put("Psyduck", "Psyduck-front.png");
        pokemonImageMap.put("Golduck", "Golduck-front.png");
        pokemonImageMap.put("Mankey", "Mankey-front.png");
        pokemonImageMap.put("Primeape", "Primeape-front.png");
        pokemonImageMap.put("Growlithe", "Growlithe-front.png");
        pokemonImageMap.put("Arcanine", "Arcanine-front.png");
        pokemonImageMap.put("Poliwag", "Poliwag-front.png");
        pokemonImageMap.put("Poliwhirl", "Poliwhirl-front.png");
        pokemonImageMap.put("Poliwrath", "Poliwrath-front.png");
        pokemonImageMap.put("Abra", "Abra-front.png");
        pokemonImageMap.put("Kadabra", "Kadabra-front.png");
        pokemonImageMap.put("Alakazam", "Alakazam-front.png");
        pokemonImageMap.put("Machop", "Machop-front.png");
        pokemonImageMap.put("Machoke", "Machoke-front.png");
        pokemonImageMap.put("Machamp", "Machamp-front.png");
        pokemonImageMap.put("Bellsprout", "Bellsprout-front.png");
        pokemonImageMap.put("Weepinbell", "Weepinbell-front.png");
        pokemonImageMap.put("Victreebel", "Victreebel-front.png");
        pokemonImageMap.put("Tentacool", "Tentacool-front.png");
        pokemonImageMap.put("Tentacruel", "Tentacruel-front.png");
        pokemonImageMap.put("Geodude", "Geodude-front.png");
        pokemonImageMap.put("Graveler", "Graveler-front.png");
        pokemonImageMap.put("Golem", "Golem-front.png");
        pokemonImageMap.put("Ponyta", "Ponyta-front.png");
        pokemonImageMap.put("Rapidash", "Rapidash-front.png");
        pokemonImageMap.put("Slowpoke", "Slowpoke-front.png");
        pokemonImageMap.put("Slowbro", "Slowbro-front.png");
        pokemonImageMap.put("Magnemite", "Magnemite-front.png");
        pokemonImageMap.put("Magneton", "Magneton-front.png");
        pokemonImageMap.put("Farfetch'd", "Farfetch'd-front.png");
        pokemonImageMap.put("Doduo", "Doduo-front.png");
        pokemonImageMap.put("Dodrio", "Dodrio-front.png");
        pokemonImageMap.put("Seel", "Seel-front.png");
        pokemonImageMap.put("Dewgong", "Dewgong-front.png");
        pokemonImageMap.put("Grimer", "Grimer-front.png");
        pokemonImageMap.put("Muk", "Muk-front.png");
        pokemonImageMap.put("Shellder", "Shellder-front.png");
        pokemonImageMap.put("Cloyster", "Cloyster-front.png");
        pokemonImageMap.put("Gastly", "Gastly-front.png");
        pokemonImageMap.put("Haunter", "Haunter-front.png");
        pokemonImageMap.put("Gengar", "Gengar-front.png");
        pokemonImageMap.put("Onix", "Onix-front.png");
        pokemonImageMap.put("Drowzee", "Drowzee-front.png");
        pokemonImageMap.put("Hypno", "Hypno-front.png");
        pokemonImageMap.put("Krabby", "Krabby-front.png");
        pokemonImageMap.put("Kingler", "Kingler-front.png");
        pokemonImageMap.put("Voltorb", "Voltorb-front.png");
        pokemonImageMap.put("Electrode", "Electrode-front.png");
        pokemonImageMap.put("Exeggcute", "Exeggcute-front.png");
        pokemonImageMap.put("Exeggutor", "Exeggutor-front.png");
        pokemonImageMap.put("Cubone", "Cubone-front.png");
        pokemonImageMap.put("Marowak", "Marowak-front.png");
        pokemonImageMap.put("Hitmonlee", "Hitmonlee-front.png");
        pokemonImageMap.put("Hitmonchan", "Hitmonchan-front.png");
        pokemonImageMap.put("Lickitung", "Lickitung-front.png");
        pokemonImageMap.put("Koffing", "Koffing-front.png");
        pokemonImageMap.put("Weezing", "Weezing-front.png");
        pokemonImageMap.put("Rhyhorn", "Rhyhorn-front.png");
        pokemonImageMap.put("Rhydon", "Rhydon-front.png");
        pokemonImageMap.put("Chansey", "Chansey-front.png");
        pokemonImageMap.put("Tangela", "Tangela-front.png");
        pokemonImageMap.put("Kangaskhan", "Kangaskhan-front.png");
        pokemonImageMap.put("Horsea", "Horsea-front.png");
        pokemonImageMap.put("Seadra", "Seadra-front.png");
        pokemonImageMap.put("Goldeen", "Goldeen-front.png");
        pokemonImageMap.put("Seaking", "Seaking-front.png");
        pokemonImageMap.put("Staryu", "Staryu-front.png");
        pokemonImageMap.put("Starmie", "Starmie-front.png");
        pokemonImageMap.put("Mr. Mime", "Mr. Mime-front.png");
        pokemonImageMap.put("Scyther", "Scyther-front.png");
        pokemonImageMap.put("Jynx", "Jynx-front.png");
        pokemonImageMap.put("Electabuzz", "Electabuzz-front.png");
        pokemonImageMap.put("Magmar", "Magmar-front.png");
        pokemonImageMap.put("Pinsir", "Pinsir-front.png");
        pokemonImageMap.put("Tauros", "Tauros-front.png");
        pokemonImageMap.put("Magikarp", "Magikarp-front.png");
        pokemonImageMap.put("Gyarados", "Gyarados-front.png");
        pokemonImageMap.put("Lapras", "Lapras-front.png");
        pokemonImageMap.put("Ditto", "Ditto-front.png");
        pokemonImageMap.put("Eevee", "Eevee-front.png");
        pokemonImageMap.put("Vaporeon", "Vaporeon-front.png");
        pokemonImageMap.put("Jolteon", "Jolteon-front.png");
        pokemonImageMap.put("Flareon", "Flareon-front.png");
        pokemonImageMap.put("Porygon", "Porygon-front.png");
        pokemonImageMap.put("Omanyte", "Omanyte-front.png");
        pokemonImageMap.put("Omastar", "Omastar-front.png");
        pokemonImageMap.put("Kabuto", "Kabuto-front.png");
        pokemonImageMap.put("Kabutops", "Kabutops-front.png");
        pokemonImageMap.put("Aerodactyl", "Aerodactyl-front.png");
        pokemonImageMap.put("Snorlax", "Snorlax-front.png");
        pokemonImageMap.put("Articuno", "Articuno-front.png");
        pokemonImageMap.put("Zapdos", "Zapdos-front.png");
        pokemonImageMap.put("Moltres", "Moltres-front.png");
        pokemonImageMap.put("Dratini", "Dratini-front.png");
        pokemonImageMap.put("Dragonair", "Dragonair-front.png");
        pokemonImageMap.put("Dragonite", "Dragonite-front.png");
        pokemonImageMap.put("Mewtwo", "Mewtwo-front.png");
        pokemonImageMap.put("Mew", "Mew-front.png");
    }

    /**
     * Add click listeners to available Pokemon panels in the scroll area
     */
    private void addPokemonClickListeners() {
        addPokemonPanelListener(pnlCharizard, "Charizard");
        addPokemonPanelListener(pnlBlastoise, "Blastoise");
        addPokemonPanelListener(pnlVenusaur, "Venusaur");
        addPokemonPanelListener(pnlPikachu, "Pikachu");
        addPokemonPanelListener(pnlNidoking, "Nidoking");
        addPokemonPanelListener(pnlClefable, "Clefable");
        addPokemonPanelListener(pnlBulbasaur, "Bulbasaur");
        addPokemonPanelListener(pnlIvysaur, "Ivysaur");
        addPokemonPanelListener(pnlCharmander, "Charmander");
        addPokemonPanelListener(pnlCharmeleon, "Charmeleon");
        addPokemonPanelListener(pnlSquirtle, "Squirtle");
        addPokemonPanelListener(pnlWartortle, "Wartortle");
        addPokemonPanelListener(pnlCaterpie, "Caterpie");
        addPokemonPanelListener(pnlMetapod, "Metapod");
        addPokemonPanelListener(pnlButterfree, "Butterfree");
        addPokemonPanelListener(pnlWeedle, "Weedle");
        addPokemonPanelListener(pnlKakuna, "Kakuna");
        addPokemonPanelListener(pnlBeedrill, "Beedrill");
        addPokemonPanelListener(pnlPidgey, "Pidgey");
        addPokemonPanelListener(pnlPidgeotto, "Pidgeotto");
        addPokemonPanelListener(pnlPidgeot, "Pidgeot");
        addPokemonPanelListener(pnlRattata, "Rattata");
        addPokemonPanelListener(pnlRaticate, "Raticate");
        addPokemonPanelListener(pnlSpearow, "Spearow");
        addPokemonPanelListener(pnlFearow, "Fearow");
        addPokemonPanelListener(pnlEkans, "Ekans");
        addPokemonPanelListener(pnlArbok, "Arbok");
        addPokemonPanelListener(pnlRaichu, "Raichu");
        addPokemonPanelListener(pnlSandshrew, "Sandshrew");
        addPokemonPanelListener(pnlSandslash, "Sandslash");
        addPokemonPanelListener(pnlNidoranF, "Nidoran♀");
        addPokemonPanelListener(pnlNidorina, "Nidorina");
        addPokemonPanelListener(pnlNidoqueen, "Nidoqueen");
        addPokemonPanelListener(pnlNidoranM, "Nidoran♂");
        addPokemonPanelListener(pnlNidorino, "Nidorino");
        addPokemonPanelListener(pnlClefairy, "Clefairy");
        addPokemonPanelListener(pnlVulpix, "Vulpix");
        addPokemonPanelListener(pnlNinetales, "Ninetales");
        addPokemonPanelListener(pnlJigglypuff, "Jigglypuff");
        addPokemonPanelListener(pnlWigglytuff, "Wigglytuff");
        addPokemonPanelListener(pnlZubat, "Zubat");
        addPokemonPanelListener(pnlGolbat, "Golbat");
        addPokemonPanelListener(pnlOddish, "Oddish");
        addPokemonPanelListener(pnlGloom, "Gloom");
        addPokemonPanelListener(pnlVileplume, "Vileplume");
        addPokemonPanelListener(pnlParas, "Paras");
        addPokemonPanelListener(pnlParasect, "Parasect");
        addPokemonPanelListener(pnlVenonat, "Venonat");
        addPokemonPanelListener(pnlVenomoth, "Venomoth");
        addPokemonPanelListener(pnlDiglett, "Diglett");
        addPokemonPanelListener(pnlDugtrio, "Dugtrio");
        addPokemonPanelListener(pnlMeowth, "Meowth");
        addPokemonPanelListener(pnlPersian, "Persian");
        addPokemonPanelListener(pnlPsyduck, "Psyduck");
        addPokemonPanelListener(pnlGolduck, "Golduck");
        addPokemonPanelListener(pnlMankey, "Mankey");
        addPokemonPanelListener(pnlPrimeape, "Primeape");
        addPokemonPanelListener(pnlGrowlithe, "Growlithe");
        addPokemonPanelListener(pnlArcanine, "Arcanine");
        addPokemonPanelListener(pnlPoliwag, "Poliwag");
        addPokemonPanelListener(pnlPoliwhirl, "Poliwhirl");
        addPokemonPanelListener(pnlPoliwrath, "Poliwrath");
        addPokemonPanelListener(pnlAbra, "Abra");
        addPokemonPanelListener(pnlKadabra, "Kadabra");
        addPokemonPanelListener(pnlAlakazam, "Alakazam");
        addPokemonPanelListener(pnlMachop, "Machop");
        addPokemonPanelListener(pnlMachoke, "Machoke");
        addPokemonPanelListener(pnlMachamp, "Machamp");
        addPokemonPanelListener(pnlBellsprout, "Bellsprout");
        addPokemonPanelListener(pnlWeepinbell, "Weepinbell");
        addPokemonPanelListener(pnlVictreebel, "Victreebel");
        addPokemonPanelListener(pnlTentacool, "Tentacool");
        addPokemonPanelListener(pnlTentacruel, "Tentacruel");
        addPokemonPanelListener(pnlGeodude, "Geodude");
        addPokemonPanelListener(pnlGraveler, "Graveler");
        addPokemonPanelListener(pnlGolem, "Golem");
        addPokemonPanelListener(pnlPonyta, "Ponyta");
        addPokemonPanelListener(pnlRapidash, "Rapidash");
        addPokemonPanelListener(pnlSlowpoke, "Slowpoke");
        addPokemonPanelListener(pnlSlowbro, "Slowbro");
        addPokemonPanelListener(pnlMagnemite, "Magnemite");
        addPokemonPanelListener(pnlMagneton, "Magneton");
        addPokemonPanelListener(pnlFarfetchd, "Farfetch'd");
        addPokemonPanelListener(pnlDoduo, "Doduo");
        addPokemonPanelListener(pnlDodrio, "Dodrio");
        addPokemonPanelListener(pnlSeel, "Seel");
        addPokemonPanelListener(pnlDewgong, "Dewgong");
        addPokemonPanelListener(pnlGrimer, "Grimer");
        addPokemonPanelListener(pnlMuk, "Muk");
        addPokemonPanelListener(pnlShellder, "Shellder");
        addPokemonPanelListener(pnlCloyster, "Cloyster");
        addPokemonPanelListener(pnlGastly, "Gastly");
        addPokemonPanelListener(pnlHaunter, "Haunter");
        addPokemonPanelListener(pnlGengar, "Gengar");
        addPokemonPanelListener(pnlOnix, "Onix");
        addPokemonPanelListener(pnlDrowzee, "Drowzee");
        addPokemonPanelListener(pnlHypno, "Hypno");
        addPokemonPanelListener(pnlKrabby, "Krabby");
        addPokemonPanelListener(pnlKingler, "Kingler");
        addPokemonPanelListener(pnlVoltorb, "Voltorb");
        addPokemonPanelListener(pnlElectrode, "Electrode");
        addPokemonPanelListener(pnlExeggcute, "Exeggcute");
        addPokemonPanelListener(pnlExeggutor, "Exeggutor");
        addPokemonPanelListener(pnlCubone, "Cubone");
        addPokemonPanelListener(pnlMarowak, "Marowak");
        addPokemonPanelListener(pnlHitmonlee, "Hitmonlee");
        addPokemonPanelListener(pnlHitmonchan, "Hitmonchan");
        addPokemonPanelListener(pnlLickitung, "Lickitung");
        addPokemonPanelListener(pnlKoffing, "Koffing");
        addPokemonPanelListener(pnlWeezing, "Weezing");
        addPokemonPanelListener(pnlRhyhorn, "Rhyhorn");
        addPokemonPanelListener(pnlRhydon, "Rhydon");
        addPokemonPanelListener(pnlChansey, "Chansey");
        addPokemonPanelListener(pnlTangela, "Tangela");
        addPokemonPanelListener(pnlKangaskhan, "Kangaskhan");
        addPokemonPanelListener(pnlHorsea, "Horsea");
        addPokemonPanelListener(pnlSeadra, "Seadra");
        addPokemonPanelListener(pnlGoldeen, "Goldeen");
        addPokemonPanelListener(pnlSeaking, "Seaking");
        addPokemonPanelListener(pnlStaryu, "Staryu");
        addPokemonPanelListener(pnlStarmie, "Starmie");
        addPokemonPanelListener(pnlMrMime, "Mr. Mime");
        addPokemonPanelListener(pnlScyther, "Scyther");
        addPokemonPanelListener(pnlJynx, "Jynx");
        addPokemonPanelListener(pnlElectabuzz, "Electabuzz");
        addPokemonPanelListener(pnlMagmar, "Magmar");
        addPokemonPanelListener(pnlPinsir, "Pinsir");
        addPokemonPanelListener(pnlTauros, "Tauros");
        addPokemonPanelListener(pnlMagikarp, "Magikarp");
        addPokemonPanelListener(pnlGyarados, "Gyarados");
        addPokemonPanelListener(pnlLapras, "Lapras");
        addPokemonPanelListener(pnlDitto, "Ditto");
        addPokemonPanelListener(pnlEevee, "Eevee");
        addPokemonPanelListener(pnlVaporeon, "Vaporeon");
        addPokemonPanelListener(pnlJolteon, "Jolteon");
        addPokemonPanelListener(pnlFlareon, "Flareon");
        addPokemonPanelListener(pnlPorygon, "Porygon");
        addPokemonPanelListener(pnlOmanyte, "Omanyte");
        addPokemonPanelListener(pnlOmastar, "Omastar");
        addPokemonPanelListener(pnlKabuto, "Kabuto");
        addPokemonPanelListener(pnlKabutops, "Kabutops");
        addPokemonPanelListener(pnlAerodactyl, "Aerodactyl");
        addPokemonPanelListener(pnlSnorlax, "Snorlax");
        addPokemonPanelListener(pnlArticuno, "Articuno");
        addPokemonPanelListener(pnlZapdos, "Zapdos");
        addPokemonPanelListener(pnlMoltres, "Moltres");
        addPokemonPanelListener(pnlDratini, "Dratini");
        addPokemonPanelListener(pnlDragonair, "Dragonair");
        addPokemonPanelListener(pnlDragonite, "Dragonite");
        addPokemonPanelListener(pnlMewtwo, "Mewtwo");
        addPokemonPanelListener(pnlMew, "Mew");
    }

    /**
     * Add click listener to a specific Pokemon panel
     */
    private void addPokemonPanelListener(JPanel panel, String pokemonName) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectPokemon(pokemonName);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isPokemonInTeam(pokemonName)) {
                    panel.setBorder(BorderFactory.createBevelBorder(
                            javax.swing.border.BevelBorder.RAISED, SELECTED_BORDER_COLOR, SELECTED_BORDER_COLOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!isPokemonInTeam(pokemonName)) {
                    panel.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
                }
            }
        });
    }

    /**
     * Add click listeners to team slot panels
     */
    private void addTeamSlotClickListeners() {
        for (int i = 0; i < TEAM_SIZE; i++) {
            final int slotIndex = i;
            teamPanels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    removePokemonFromTeam(slotIndex);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (selectedPokemon[slotIndex] != null) {
                        teamPanels[slotIndex].setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    updateTeamPanelBorders();
                }
            });
        }
    }

    /**
     * Select a Pokemon and add it to the team
     */
    private void selectPokemon(String pokemonName) {
        // Check if Pokemon is already in team
        if (isPokemonInTeam(pokemonName)) {
            JOptionPane.showMessageDialog(this,
                    pokemonName + " is already in your team!",
                    "Pokemon Already Selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Find next available slot
        int nextSlot = getNextAvailableSlot();
        if (nextSlot == -1) {
            JOptionPane.showMessageDialog(this,
                    "Your team is full! Remove a Pokemon first.",
                    "Team Full",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Add Pokemon to team
        selectedPokemon[nextSlot] = pokemonName;
        updateTeamSlotDisplay(nextSlot);
        updateAvailablePokemonDisplay();
        updateTeamPanelBorders();

        logger.info("Added " + pokemonName + " to team slot " + (nextSlot + 1));
    }

    /**
     * Remove a Pokemon from the team and shift remaining Pokemon
     */
    private void removePokemonFromTeam(int slotIndex) {
        if (selectedPokemon[slotIndex] == null) {
            return; // No Pokemon in this slot
        }

        String removedPokemon = selectedPokemon[slotIndex];

        // Shift Pokemon left to fill the gap
        for (int i = slotIndex; i < TEAM_SIZE - 1; i++) {
            selectedPokemon[i] = selectedPokemon[i + 1];
        }
        selectedPokemon[TEAM_SIZE - 1] = null; // Clear the last slot

        // Update all team slot displays
        updateAllTeamSlotDisplays();
        updateAvailablePokemonDisplay();
        updateTeamPanelBorders();

        logger.info("Removed " + removedPokemon + " from team slot " + (slotIndex + 1));
    }

    /**
     * Check if a Pokemon is already in the team
     */
    private boolean isPokemonInTeam(String pokemonName) {
        for (String pokemon : selectedPokemon) {
            if (pokemonName.equals(pokemon)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the next available team slot
     */
    private int getNextAvailableSlot() {
        for (int i = 0; i < TEAM_SIZE; i++) {
            if (selectedPokemon[i] == null) {
                return i;
            }
        }
        return -1; // Team is full
    }

    /**
     * Update the display of a specific team slot
     */
    private void updateTeamSlotDisplay(int slotIndex) {
        JLabel label = teamLabels[slotIndex];
        String pokemonName = selectedPokemon[slotIndex];

        if (pokemonName != null) {
            // Load and set Pokemon image
            String imageName = pokemonImageMap.get(pokemonName);
            if (imageName != null) {
                BufferedImage pokemonImage = XImage.getPokemonImageByName(imageName);
                if (pokemonImage != null) {
                    // Resize image to fit the label
                    Image scaledImage = XImage.resize(pokemonImage, 120, 120);
                    label.setIcon(new ImageIcon(scaledImage));
                } else {
                    // Fallback text if image fails to load
                    label.setIcon(null);
                    label.setText(pokemonName);
                }
            }
        } else {
            // Clear the slot
            label.setIcon(null);
            label.setText("");
        }
    }

    /**
     * Update all team slot displays
     */
    private void updateAllTeamSlotDisplays() {
        for (int i = 0; i < TEAM_SIZE; i++) {
            updateTeamSlotDisplay(i);
        }
    }

    /**
     * Update the visual appearance of available Pokemon panels
     */
    private void updateAvailablePokemonDisplay() {
        // Update Charizard panel
        updateAvailablePokemonPanel(pnlCharizard, "Charizard");
        updateAvailablePokemonPanel(pnlBlastoise, "Blastoise");
        updateAvailablePokemonPanel(pnlVenusaur, "Venusaur");
        updateAvailablePokemonPanel(pnlPikachu, "Pikachu");
        updateAvailablePokemonPanel(pnlNidoking, "Nidoking");
        updateAvailablePokemonPanel(pnlClefable, "Clefable");
    }

    /**
     * Update the appearance of a specific available Pokemon panel
     */
    private void updateAvailablePokemonPanel(JPanel panel, String pokemonName) {
        if (isPokemonInTeam(pokemonName)) {
            // Make selected Pokemon appear dimmed/disabled
            panel.setBorder(BorderFactory.createBevelBorder(
                    javax.swing.border.BevelBorder.LOWERED, SELECTED_BORDER_COLOR, SELECTED_BORDER_COLOR));
            panel.setBackground(new Color(200, 200, 200, 100));
        } else {
            // Reset to normal appearance
            panel.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            panel.setBackground(null);
        }
    }

    /**
     * Update borders for team panels
     */
    private void updateTeamPanelBorders() {
        for (int i = 0; i < TEAM_SIZE; i++) {
            if (selectedPokemon[i] != null) {
                // Pokemon present - show filled border
                teamPanels[i].setBorder(BorderFactory.createLineBorder(SELECTED_BORDER_COLOR, 2));
            } else {
                // Empty slot - show dashed border
                teamPanels[i].setBorder(BorderFactory.createDashedBorder(DEFAULT_BORDER_COLOR, 2, 5));
            }
        }
    }

    /**
     * Get the current team selection
     */
    public String[] getSelectedPokemon() {
        return selectedPokemon.clone();
    }

    /**
     * Get the number of Pokemon in the team
     */
    public int getTeamSize() {
        int count = 0;
        for (String pokemon : selectedPokemon) {
            if (pokemon != null) {
                count++;
            }
        }
        return count;
    }

    /**
     * Check if team is full
     */
    public boolean isTeamFull() {
        return getTeamSize() == TEAM_SIZE;
    }

    /**
     * Check if team has at least one Pokemon
     */
    public boolean hasValidTeam() {
        return getTeamSize() > 0;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        pnlChoosePokemon = new javax.swing.JPanel();
        pnlCharizard = new javax.swing.JPanel();
        lblCharizard = new javax.swing.JLabel();
        pnlBlastoise = new javax.swing.JPanel();
        lblBlastoise = new javax.swing.JLabel();
        pnlVenusaur = new javax.swing.JPanel();
        lblVenusaur = new javax.swing.JLabel();
        pnlPikachu = new javax.swing.JPanel();
        lblPikachu = new javax.swing.JLabel();
        pnlNidoking = new javax.swing.JPanel();
        lblNidoking = new javax.swing.JLabel();
        pnlClefable = new javax.swing.JPanel();
        lblClefable = new javax.swing.JLabel();
        pnlBulbasaur = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlIvysaur = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        pnlCharmeleon = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        pnlCaterpie = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        pnlMetapod = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        pnlSquirtle = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pnlWartortle = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        pnlCharmander = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        pnlButterfree = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        pnlWeedle = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        pnlKakuna = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        pnlPidgey = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        pnlPidgeotto = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        pnlPidgeot = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        pnlRattata = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        pnlEkans = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        pnlArbok = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        pnlRaichu = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        pnlSandshrew = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        pnlSandslash = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        pnlNidoranF = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        pnlNidoqueen = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        pnlNidoranM = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        pnlNidorino = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        pnlGloom = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        pnlOddish = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        pnlGolbat = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        pnlZubat = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        pnlWigglytuff = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        pnlJigglypuff = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        pnlNinetales = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        pnlVulpix = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        pnlClefairy = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        pnlVileplume = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        pnlParas = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        pnlParasect = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        pnlVenonat = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        pnlVenomoth = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        pnlDiglett = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        pnlDugtrio = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        pnlMeowth = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        pnlPersian = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        pnlPsyduck = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        pnlGolduck = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        pnlNidorina = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        pnlTentacruel = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        pnlPoliwhirl = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        pnlMagneton = new javax.swing.JPanel();
        jLabel76 = new javax.swing.JLabel();
        pnlPonyta = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        pnlGolem = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        pnlBellsprout = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        pnlGraveler = new javax.swing.JPanel();
        jLabel69 = new javax.swing.JLabel();
        pnlMankey = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        pnlSlowbro = new javax.swing.JPanel();
        jLabel74 = new javax.swing.JLabel();
        pnlVictreebel = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        pnlArcanine = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        pnlMachop = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        pnlTentacool = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        pnlPoliwag = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        pnlSeel = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        pnlDoduo = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        pnlWeepinbell = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        pnlAbra = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        pnlAlakazam = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        pnlGeodude = new javax.swing.JPanel();
        jLabel68 = new javax.swing.JLabel();
        pnlMachoke = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        pnlPoliwrath = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        pnlSlowpoke = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        pnlFarfetchd = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();
        pnlMagnemite = new javax.swing.JPanel();
        jLabel75 = new javax.swing.JLabel();
        pnlDodrio = new javax.swing.JPanel();
        jLabel79 = new javax.swing.JLabel();
        pnlRapidash = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        pnlKadabra = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        pnlPrimeape = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        pnlGrowlithe = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        pnlMachamp = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        pnlGrimer = new javax.swing.JPanel();
        jLabel82 = new javax.swing.JLabel();
        pnlDewgong = new javax.swing.JPanel();
        jLabel81 = new javax.swing.JLabel();
        pnlStarmie = new javax.swing.JPanel();
        jLabel115 = new javax.swing.JLabel();
        pnlPinsir = new javax.swing.JPanel();
        jLabel121 = new javax.swing.JLabel();
        pnlFlareon = new javax.swing.JPanel();
        jLabel130 = new javax.swing.JLabel();
        pnlDrowzee = new javax.swing.JPanel();
        jLabel90 = new javax.swing.JLabel();
        pnlGoldeen = new javax.swing.JPanel();
        jLabel112 = new javax.swing.JLabel();
        pnlKrabby = new javax.swing.JPanel();
        jLabel92 = new javax.swing.JLabel();
        pnlHitmonchan = new javax.swing.JPanel();
        jLabel101 = new javax.swing.JLabel();
        pnlDragonair = new javax.swing.JPanel();
        jLabel142 = new javax.swing.JLabel();
        pnlTauros = new javax.swing.JPanel();
        jLabel122 = new javax.swing.JLabel();
        pnlMagmar = new javax.swing.JPanel();
        jLabel120 = new javax.swing.JLabel();
        pnlOmastar = new javax.swing.JPanel();
        jLabel133 = new javax.swing.JLabel();
        pnlRhyhorn = new javax.swing.JPanel();
        jLabel105 = new javax.swing.JLabel();
        pnlVoltorb = new javax.swing.JPanel();
        jLabel94 = new javax.swing.JLabel();
        pnlKingler = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        pnlGastly = new javax.swing.JPanel();
        jLabel86 = new javax.swing.JLabel();
        pnlMagikarp = new javax.swing.JPanel();
        jLabel123 = new javax.swing.JLabel();
        pnlMuk = new javax.swing.JPanel();
        jLabel83 = new javax.swing.JLabel();
        pnlPorygon = new javax.swing.JPanel();
        jLabel131 = new javax.swing.JLabel();
        pnlShellder = new javax.swing.JPanel();
        jLabel84 = new javax.swing.JLabel();
        pnlCloyster = new javax.swing.JPanel();
        jLabel85 = new javax.swing.JLabel();
        pnlKangaskhan = new javax.swing.JPanel();
        jLabel109 = new javax.swing.JLabel();
        pnlMewtwo = new javax.swing.JPanel();
        jLabel144 = new javax.swing.JLabel();
        pnlElectrode = new javax.swing.JPanel();
        jLabel95 = new javax.swing.JLabel();
        pnlWeezing = new javax.swing.JPanel();
        jLabel104 = new javax.swing.JLabel();
        pnlCubone = new javax.swing.JPanel();
        jLabel98 = new javax.swing.JLabel();
        pnlJolteon = new javax.swing.JPanel();
        jLabel129 = new javax.swing.JLabel();
        pnlMew = new javax.swing.JPanel();
        jLabel145 = new javax.swing.JLabel();
        pnlZapdos = new javax.swing.JPanel();
        jLabel139 = new javax.swing.JLabel();
        pnlOmanyte = new javax.swing.JPanel();
        jLabel132 = new javax.swing.JLabel();
        pnlJynx = new javax.swing.JPanel();
        jLabel118 = new javax.swing.JLabel();
        pnlLapras = new javax.swing.JPanel();
        jLabel125 = new javax.swing.JLabel();
        pnlTangela = new javax.swing.JPanel();
        jLabel108 = new javax.swing.JLabel();
        pnlAerodactyl = new javax.swing.JPanel();
        jLabel136 = new javax.swing.JLabel();
        pnlSeaking = new javax.swing.JPanel();
        jLabel113 = new javax.swing.JLabel();
        pnlRhydon = new javax.swing.JPanel();
        jLabel106 = new javax.swing.JLabel();
        pnlChansey = new javax.swing.JPanel();
        jLabel107 = new javax.swing.JLabel();
        pnlDratini = new javax.swing.JPanel();
        jLabel141 = new javax.swing.JLabel();
        pnlMarowak = new javax.swing.JPanel();
        jLabel99 = new javax.swing.JLabel();
        pnlGengar = new javax.swing.JPanel();
        jLabel88 = new javax.swing.JLabel();
        pnlElectabuzz = new javax.swing.JPanel();
        jLabel119 = new javax.swing.JLabel();
        pnlHorsea = new javax.swing.JPanel();
        jLabel110 = new javax.swing.JLabel();
        pnlExeggutor = new javax.swing.JPanel();
        jLabel97 = new javax.swing.JLabel();
        pnlScyther = new javax.swing.JPanel();
        jLabel117 = new javax.swing.JLabel();
        pnlKabutops = new javax.swing.JPanel();
        jLabel135 = new javax.swing.JLabel();
        pnlKoffing = new javax.swing.JPanel();
        jLabel103 = new javax.swing.JLabel();
        pnlVaporeon = new javax.swing.JPanel();
        jLabel128 = new javax.swing.JLabel();
        pnlMoltres = new javax.swing.JPanel();
        jLabel140 = new javax.swing.JLabel();
        pnlStaryu = new javax.swing.JPanel();
        jLabel114 = new javax.swing.JLabel();
        pnlEevee = new javax.swing.JPanel();
        jLabel127 = new javax.swing.JLabel();
        pnlGyarados = new javax.swing.JPanel();
        jLabel124 = new javax.swing.JLabel();
        pnlDragonite = new javax.swing.JPanel();
        jLabel143 = new javax.swing.JLabel();
        pnlHaunter = new javax.swing.JPanel();
        jLabel87 = new javax.swing.JLabel();
        pnlSeadra = new javax.swing.JPanel();
        jLabel111 = new javax.swing.JLabel();
        pnlHitmonlee = new javax.swing.JPanel();
        jLabel100 = new javax.swing.JLabel();
        pnlSnorlax = new javax.swing.JPanel();
        jLabel137 = new javax.swing.JLabel();
        pnlExeggcute = new javax.swing.JPanel();
        jLabel96 = new javax.swing.JLabel();
        pnlLickitung = new javax.swing.JPanel();
        jLabel102 = new javax.swing.JLabel();
        pnlMrMime = new javax.swing.JPanel();
        jLabel116 = new javax.swing.JLabel();
        pnlKabuto = new javax.swing.JPanel();
        jLabel134 = new javax.swing.JLabel();
        pnlOnix = new javax.swing.JPanel();
        jLabel89 = new javax.swing.JLabel();
        pnlDitto = new javax.swing.JPanel();
        jLabel126 = new javax.swing.JLabel();
        pnlHypno = new javax.swing.JPanel();
        jLabel91 = new javax.swing.JLabel();
        pnlArticuno = new javax.swing.JPanel();
        jLabel138 = new javax.swing.JLabel();
        pnlBeedrill = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        pnlRaticate = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        pnlSpearow = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        pnlFearow = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        pnlFirstPokemon = new javax.swing.JPanel();
        lblFirstPokemon = new javax.swing.JLabel();
        pnlSecondPokemon = new javax.swing.JPanel();
        lblSecondPokemon = new javax.swing.JLabel();
        pnlThirdPokemon = new javax.swing.JPanel();
        lblThirdPokemon = new javax.swing.JLabel();
        pnlFourthPokemon = new javax.swing.JPanel();
        lblFourthPokemon = new javax.swing.JLabel();
        pblFifthPokemon = new javax.swing.JPanel();
        lblFifthPokemon = new javax.swing.JLabel();
        pnlSixthPokemon = new javax.swing.JPanel();
        lblSixthPokemon = new javax.swing.JLabel();
        btnGo = new javax.swing.JButton();
        lblBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 0));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("CHOOSE YOUR POKÉMON");
        panel.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, 100));

        pnlChoosePokemon.setBackground(new java.awt.Color(204, 204, 204));

        pnlCharizard.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblCharizard.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCharizard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Charizard-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlCharizardLayout = new javax.swing.GroupLayout(pnlCharizard);
        pnlCharizard.setLayout(pnlCharizardLayout);
        pnlCharizardLayout.setHorizontalGroup(
            pnlCharizardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCharizard, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlCharizardLayout.setVerticalGroup(
            pnlCharizardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCharizard, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlBlastoise.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblBlastoise.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBlastoise.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Blastoise-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlBlastoiseLayout = new javax.swing.GroupLayout(pnlBlastoise);
        pnlBlastoise.setLayout(pnlBlastoiseLayout);
        pnlBlastoiseLayout.setHorizontalGroup(
            pnlBlastoiseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBlastoiseLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblBlastoise, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlBlastoiseLayout.setVerticalGroup(
            pnlBlastoiseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBlastoiseLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblBlastoise, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlVenusaur.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblVenusaur.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblVenusaur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Venusaur-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlVenusaurLayout = new javax.swing.GroupLayout(pnlVenusaur);
        pnlVenusaur.setLayout(pnlVenusaurLayout);
        pnlVenusaurLayout.setHorizontalGroup(
            pnlVenusaurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblVenusaur, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlVenusaurLayout.setVerticalGroup(
            pnlVenusaurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblVenusaur, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlPikachu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblPikachu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPikachu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Pikachu-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlPikachuLayout = new javax.swing.GroupLayout(pnlPikachu);
        pnlPikachu.setLayout(pnlPikachuLayout);
        pnlPikachuLayout.setHorizontalGroup(
            pnlPikachuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblPikachu, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlPikachuLayout.setVerticalGroup(
            pnlPikachuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblPikachu, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlNidoking.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblNidoking.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNidoking.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Nidoking-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlNidokingLayout = new javax.swing.GroupLayout(pnlNidoking);
        pnlNidoking.setLayout(pnlNidokingLayout);
        pnlNidokingLayout.setHorizontalGroup(
            pnlNidokingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblNidoking, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlNidokingLayout.setVerticalGroup(
            pnlNidokingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblNidoking, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlClefable.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblClefable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClefable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Clefable-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlClefableLayout = new javax.swing.GroupLayout(pnlClefable);
        pnlClefable.setLayout(pnlClefableLayout);
        pnlClefableLayout.setHorizontalGroup(
            pnlClefableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblClefable, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlClefableLayout.setVerticalGroup(
            pnlClefableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblClefable, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlBulbasaur.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Bulbasaur-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlBulbasaurLayout = new javax.swing.GroupLayout(pnlBulbasaur);
        pnlBulbasaur.setLayout(pnlBulbasaurLayout);
        pnlBulbasaurLayout.setHorizontalGroup(
            pnlBulbasaurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlBulbasaurLayout.setVerticalGroup(
            pnlBulbasaurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlIvysaur.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Ivysaur-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlIvysaurLayout = new javax.swing.GroupLayout(pnlIvysaur);
        pnlIvysaur.setLayout(pnlIvysaurLayout);
        pnlIvysaurLayout.setHorizontalGroup(
            pnlIvysaurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlIvysaurLayout.setVerticalGroup(
            pnlIvysaurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlCharmeleon.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Charmeleon-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlCharmeleonLayout = new javax.swing.GroupLayout(pnlCharmeleon);
        pnlCharmeleon.setLayout(pnlCharmeleonLayout);
        pnlCharmeleonLayout.setHorizontalGroup(
            pnlCharmeleonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlCharmeleonLayout.setVerticalGroup(
            pnlCharmeleonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlCaterpie.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Caterpie-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlCaterpieLayout = new javax.swing.GroupLayout(pnlCaterpie);
        pnlCaterpie.setLayout(pnlCaterpieLayout);
        pnlCaterpieLayout.setHorizontalGroup(
            pnlCaterpieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlCaterpieLayout.setVerticalGroup(
            pnlCaterpieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlMetapod.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Metapod-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlMetapodLayout = new javax.swing.GroupLayout(pnlMetapod);
        pnlMetapod.setLayout(pnlMetapodLayout);
        pnlMetapodLayout.setHorizontalGroup(
            pnlMetapodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlMetapodLayout.setVerticalGroup(
            pnlMetapodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlSquirtle.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Squirtle-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlSquirtleLayout = new javax.swing.GroupLayout(pnlSquirtle);
        pnlSquirtle.setLayout(pnlSquirtleLayout);
        pnlSquirtleLayout.setHorizontalGroup(
            pnlSquirtleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlSquirtleLayout.setVerticalGroup(
            pnlSquirtleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlWartortle.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Wartortle-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlWartortleLayout = new javax.swing.GroupLayout(pnlWartortle);
        pnlWartortle.setLayout(pnlWartortleLayout);
        pnlWartortleLayout.setHorizontalGroup(
            pnlWartortleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlWartortleLayout.setVerticalGroup(
            pnlWartortleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlCharmander.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Charmander-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlCharmanderLayout = new javax.swing.GroupLayout(pnlCharmander);
        pnlCharmander.setLayout(pnlCharmanderLayout);
        pnlCharmanderLayout.setHorizontalGroup(
            pnlCharmanderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlCharmanderLayout.setVerticalGroup(
            pnlCharmanderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlButterfree.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Butterfree-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlButterfreeLayout = new javax.swing.GroupLayout(pnlButterfree);
        pnlButterfree.setLayout(pnlButterfreeLayout);
        pnlButterfreeLayout.setHorizontalGroup(
            pnlButterfreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlButterfreeLayout.setVerticalGroup(
            pnlButterfreeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlWeedle.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Weedle-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlWeedleLayout = new javax.swing.GroupLayout(pnlWeedle);
        pnlWeedle.setLayout(pnlWeedleLayout);
        pnlWeedleLayout.setHorizontalGroup(
            pnlWeedleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlWeedleLayout.setVerticalGroup(
            pnlWeedleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlKakuna.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Kakuna-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlKakunaLayout = new javax.swing.GroupLayout(pnlKakuna);
        pnlKakuna.setLayout(pnlKakunaLayout);
        pnlKakunaLayout.setHorizontalGroup(
            pnlKakunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlKakunaLayout.setVerticalGroup(
            pnlKakunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlPidgey.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Pidgey-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlPidgeyLayout = new javax.swing.GroupLayout(pnlPidgey);
        pnlPidgey.setLayout(pnlPidgeyLayout);
        pnlPidgeyLayout.setHorizontalGroup(
            pnlPidgeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlPidgeyLayout.setVerticalGroup(
            pnlPidgeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlPidgeotto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Pidgeotto-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlPidgeottoLayout = new javax.swing.GroupLayout(pnlPidgeotto);
        pnlPidgeotto.setLayout(pnlPidgeottoLayout);
        pnlPidgeottoLayout.setHorizontalGroup(
            pnlPidgeottoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlPidgeottoLayout.setVerticalGroup(
            pnlPidgeottoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlPidgeot.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Pidgeot-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlPidgeotLayout = new javax.swing.GroupLayout(pnlPidgeot);
        pnlPidgeot.setLayout(pnlPidgeotLayout);
        pnlPidgeotLayout.setHorizontalGroup(
            pnlPidgeotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlPidgeotLayout.setVerticalGroup(
            pnlPidgeotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlRattata.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Rattata-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlRattataLayout = new javax.swing.GroupLayout(pnlRattata);
        pnlRattata.setLayout(pnlRattataLayout);
        pnlRattataLayout.setHorizontalGroup(
            pnlRattataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlRattataLayout.setVerticalGroup(
            pnlRattataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlEkans.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Ekans-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlEkansLayout = new javax.swing.GroupLayout(pnlEkans);
        pnlEkans.setLayout(pnlEkansLayout);
        pnlEkansLayout.setHorizontalGroup(
            pnlEkansLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlEkansLayout.setVerticalGroup(
            pnlEkansLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlArbok.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Arbok-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlArbokLayout = new javax.swing.GroupLayout(pnlArbok);
        pnlArbok.setLayout(pnlArbokLayout);
        pnlArbokLayout.setHorizontalGroup(
            pnlArbokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlArbokLayout.setVerticalGroup(
            pnlArbokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlRaichu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Raichu-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlRaichuLayout = new javax.swing.GroupLayout(pnlRaichu);
        pnlRaichu.setLayout(pnlRaichuLayout);
        pnlRaichuLayout.setHorizontalGroup(
            pnlRaichuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlRaichuLayout.setVerticalGroup(
            pnlRaichuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlSandshrew.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Sandshrew-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlSandshrewLayout = new javax.swing.GroupLayout(pnlSandshrew);
        pnlSandshrew.setLayout(pnlSandshrewLayout);
        pnlSandshrewLayout.setHorizontalGroup(
            pnlSandshrewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlSandshrewLayout.setVerticalGroup(
            pnlSandshrewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlSandslash.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Sandslash-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlSandslashLayout = new javax.swing.GroupLayout(pnlSandslash);
        pnlSandslash.setLayout(pnlSandslashLayout);
        pnlSandslashLayout.setHorizontalGroup(
            pnlSandslashLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlSandslashLayout.setVerticalGroup(
            pnlSandslashLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlNidoranF.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Nidoran-f-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlNidoranFLayout = new javax.swing.GroupLayout(pnlNidoranF);
        pnlNidoranF.setLayout(pnlNidoranFLayout);
        pnlNidoranFLayout.setHorizontalGroup(
            pnlNidoranFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlNidoranFLayout.setVerticalGroup(
            pnlNidoranFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlNidoqueen.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Nidoqueen-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlNidoqueenLayout = new javax.swing.GroupLayout(pnlNidoqueen);
        pnlNidoqueen.setLayout(pnlNidoqueenLayout);
        pnlNidoqueenLayout.setHorizontalGroup(
            pnlNidoqueenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlNidoqueenLayout.setVerticalGroup(
            pnlNidoqueenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlNidoranM.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Nidoran-m-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlNidoranMLayout = new javax.swing.GroupLayout(pnlNidoranM);
        pnlNidoranM.setLayout(pnlNidoranMLayout);
        pnlNidoranMLayout.setHorizontalGroup(
            pnlNidoranMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlNidoranMLayout.setVerticalGroup(
            pnlNidoranMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlNidorino.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Nidorino-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlNidorinoLayout = new javax.swing.GroupLayout(pnlNidorino);
        pnlNidorino.setLayout(pnlNidorinoLayout);
        pnlNidorinoLayout.setHorizontalGroup(
            pnlNidorinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlNidorinoLayout.setVerticalGroup(
            pnlNidorinoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlGloom.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Gloom-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlGloomLayout = new javax.swing.GroupLayout(pnlGloom);
        pnlGloom.setLayout(pnlGloomLayout);
        pnlGloomLayout.setHorizontalGroup(
            pnlGloomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlGloomLayout.setVerticalGroup(
            pnlGloomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlOddish.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Oddish-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlOddishLayout = new javax.swing.GroupLayout(pnlOddish);
        pnlOddish.setLayout(pnlOddishLayout);
        pnlOddishLayout.setHorizontalGroup(
            pnlOddishLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlOddishLayout.setVerticalGroup(
            pnlOddishLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlGolbat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Golbat-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlGolbatLayout = new javax.swing.GroupLayout(pnlGolbat);
        pnlGolbat.setLayout(pnlGolbatLayout);
        pnlGolbatLayout.setHorizontalGroup(
            pnlGolbatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlGolbatLayout.setVerticalGroup(
            pnlGolbatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlZubat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Zubat-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlZubatLayout = new javax.swing.GroupLayout(pnlZubat);
        pnlZubat.setLayout(pnlZubatLayout);
        pnlZubatLayout.setHorizontalGroup(
            pnlZubatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlZubatLayout.setVerticalGroup(
            pnlZubatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlWigglytuff.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Wigglytuff-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlWigglytuffLayout = new javax.swing.GroupLayout(pnlWigglytuff);
        pnlWigglytuff.setLayout(pnlWigglytuffLayout);
        pnlWigglytuffLayout.setHorizontalGroup(
            pnlWigglytuffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlWigglytuffLayout.setVerticalGroup(
            pnlWigglytuffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlJigglypuff.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Jigglypuff-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlJigglypuffLayout = new javax.swing.GroupLayout(pnlJigglypuff);
        pnlJigglypuff.setLayout(pnlJigglypuffLayout);
        pnlJigglypuffLayout.setHorizontalGroup(
            pnlJigglypuffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlJigglypuffLayout.setVerticalGroup(
            pnlJigglypuffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlNinetales.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Ninetales-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlNinetalesLayout = new javax.swing.GroupLayout(pnlNinetales);
        pnlNinetales.setLayout(pnlNinetalesLayout);
        pnlNinetalesLayout.setHorizontalGroup(
            pnlNinetalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlNinetalesLayout.setVerticalGroup(
            pnlNinetalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlVulpix.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Vulpix-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlVulpixLayout = new javax.swing.GroupLayout(pnlVulpix);
        pnlVulpix.setLayout(pnlVulpixLayout);
        pnlVulpixLayout.setHorizontalGroup(
            pnlVulpixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlVulpixLayout.setVerticalGroup(
            pnlVulpixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlClefairy.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Clefairy-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlClefairyLayout = new javax.swing.GroupLayout(pnlClefairy);
        pnlClefairy.setLayout(pnlClefairyLayout);
        pnlClefairyLayout.setHorizontalGroup(
            pnlClefairyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlClefairyLayout.setVerticalGroup(
            pnlClefairyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlVileplume.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Vileplume-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlVileplumeLayout = new javax.swing.GroupLayout(pnlVileplume);
        pnlVileplume.setLayout(pnlVileplumeLayout);
        pnlVileplumeLayout.setHorizontalGroup(
            pnlVileplumeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlVileplumeLayout.setVerticalGroup(
            pnlVileplumeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlParas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Paras-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlParasLayout = new javax.swing.GroupLayout(pnlParas);
        pnlParas.setLayout(pnlParasLayout);
        pnlParasLayout.setHorizontalGroup(
            pnlParasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlParasLayout.setVerticalGroup(
            pnlParasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlParasect.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Parasect-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlParasectLayout = new javax.swing.GroupLayout(pnlParasect);
        pnlParasect.setLayout(pnlParasectLayout);
        pnlParasectLayout.setHorizontalGroup(
            pnlParasectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlParasectLayout.setVerticalGroup(
            pnlParasectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlVenonat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Venonat-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlVenonatLayout = new javax.swing.GroupLayout(pnlVenonat);
        pnlVenonat.setLayout(pnlVenonatLayout);
        pnlVenonatLayout.setHorizontalGroup(
            pnlVenonatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlVenonatLayout.setVerticalGroup(
            pnlVenonatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlVenomoth.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Venomoth-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlVenomothLayout = new javax.swing.GroupLayout(pnlVenomoth);
        pnlVenomoth.setLayout(pnlVenomothLayout);
        pnlVenomothLayout.setHorizontalGroup(
            pnlVenomothLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlVenomothLayout.setVerticalGroup(
            pnlVenomothLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlDiglett.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Diglett-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlDiglettLayout = new javax.swing.GroupLayout(pnlDiglett);
        pnlDiglett.setLayout(pnlDiglettLayout);
        pnlDiglettLayout.setHorizontalGroup(
            pnlDiglettLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlDiglettLayout.setVerticalGroup(
            pnlDiglettLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlDugtrio.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Dugtrio-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlDugtrioLayout = new javax.swing.GroupLayout(pnlDugtrio);
        pnlDugtrio.setLayout(pnlDugtrioLayout);
        pnlDugtrioLayout.setHorizontalGroup(
            pnlDugtrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlDugtrioLayout.setVerticalGroup(
            pnlDugtrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlMeowth.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Meowth-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlMeowthLayout = new javax.swing.GroupLayout(pnlMeowth);
        pnlMeowth.setLayout(pnlMeowthLayout);
        pnlMeowthLayout.setHorizontalGroup(
            pnlMeowthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlMeowthLayout.setVerticalGroup(
            pnlMeowthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlPersian.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Persian-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlPersianLayout = new javax.swing.GroupLayout(pnlPersian);
        pnlPersian.setLayout(pnlPersianLayout);
        pnlPersianLayout.setHorizontalGroup(
            pnlPersianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlPersianLayout.setVerticalGroup(
            pnlPersianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlPsyduck.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Psyduck-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlPsyduckLayout = new javax.swing.GroupLayout(pnlPsyduck);
        pnlPsyduck.setLayout(pnlPsyduckLayout);
        pnlPsyduckLayout.setHorizontalGroup(
            pnlPsyduckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlPsyduckLayout.setVerticalGroup(
            pnlPsyduckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlGolduck.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Golduck-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlGolduckLayout = new javax.swing.GroupLayout(pnlGolduck);
        pnlGolduck.setLayout(pnlGolduckLayout);
        pnlGolduckLayout.setHorizontalGroup(
            pnlGolduckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlGolduckLayout.setVerticalGroup(
            pnlGolduckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlNidorina.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Nidorina-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlNidorinaLayout = new javax.swing.GroupLayout(pnlNidorina);
        pnlNidorina.setLayout(pnlNidorinaLayout);
        pnlNidorinaLayout.setHorizontalGroup(
            pnlNidorinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlNidorinaLayout.setVerticalGroup(
            pnlNidorinaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlTentacruel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Tentacruel-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlTentacruelLayout = new javax.swing.GroupLayout(pnlTentacruel);
        pnlTentacruel.setLayout(pnlTentacruelLayout);
        pnlTentacruelLayout.setHorizontalGroup(
            pnlTentacruelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlTentacruelLayout.setVerticalGroup(
            pnlTentacruelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlPoliwhirl.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Poliwhirl-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlPoliwhirlLayout = new javax.swing.GroupLayout(pnlPoliwhirl);
        pnlPoliwhirl.setLayout(pnlPoliwhirlLayout);
        pnlPoliwhirlLayout.setHorizontalGroup(
            pnlPoliwhirlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel55, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlPoliwhirlLayout.setVerticalGroup(
            pnlPoliwhirlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel55, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlMagneton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel76.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Magneton-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlMagnetonLayout = new javax.swing.GroupLayout(pnlMagneton);
        pnlMagneton.setLayout(pnlMagnetonLayout);
        pnlMagnetonLayout.setHorizontalGroup(
            pnlMagnetonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel76, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlMagnetonLayout.setVerticalGroup(
            pnlMagnetonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel76, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlPonyta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Ponyta-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlPonytaLayout = new javax.swing.GroupLayout(pnlPonyta);
        pnlPonyta.setLayout(pnlPonytaLayout);
        pnlPonytaLayout.setHorizontalGroup(
            pnlPonytaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel71, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlPonytaLayout.setVerticalGroup(
            pnlPonytaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel71, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlGolem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Golem-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlGolemLayout = new javax.swing.GroupLayout(pnlGolem);
        pnlGolem.setLayout(pnlGolemLayout);
        pnlGolemLayout.setHorizontalGroup(
            pnlGolemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlGolemLayout.setVerticalGroup(
            pnlGolemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlBellsprout.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Bellsprout-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlBellsproutLayout = new javax.swing.GroupLayout(pnlBellsprout);
        pnlBellsprout.setLayout(pnlBellsproutLayout);
        pnlBellsproutLayout.setHorizontalGroup(
            pnlBellsproutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlBellsproutLayout.setVerticalGroup(
            pnlBellsproutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlGraveler.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Graveler-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlGravelerLayout = new javax.swing.GroupLayout(pnlGraveler);
        pnlGraveler.setLayout(pnlGravelerLayout);
        pnlGravelerLayout.setHorizontalGroup(
            pnlGravelerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlGravelerLayout.setVerticalGroup(
            pnlGravelerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlMankey.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Mankey-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlMankeyLayout = new javax.swing.GroupLayout(pnlMankey);
        pnlMankey.setLayout(pnlMankeyLayout);
        pnlMankeyLayout.setHorizontalGroup(
            pnlMankeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlMankeyLayout.setVerticalGroup(
            pnlMankeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlSlowbro.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Slowbro-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlSlowbroLayout = new javax.swing.GroupLayout(pnlSlowbro);
        pnlSlowbro.setLayout(pnlSlowbroLayout);
        pnlSlowbroLayout.setHorizontalGroup(
            pnlSlowbroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlSlowbroLayout.setVerticalGroup(
            pnlSlowbroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlVictreebel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Victreebel-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlVictreebelLayout = new javax.swing.GroupLayout(pnlVictreebel);
        pnlVictreebel.setLayout(pnlVictreebelLayout);
        pnlVictreebelLayout.setHorizontalGroup(
            pnlVictreebelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlVictreebelLayout.setVerticalGroup(
            pnlVictreebelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlArcanine.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Arcanine-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlArcanineLayout = new javax.swing.GroupLayout(pnlArcanine);
        pnlArcanine.setLayout(pnlArcanineLayout);
        pnlArcanineLayout.setHorizontalGroup(
            pnlArcanineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlArcanineLayout.setVerticalGroup(
            pnlArcanineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlMachop.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Machop-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlMachopLayout = new javax.swing.GroupLayout(pnlMachop);
        pnlMachop.setLayout(pnlMachopLayout);
        pnlMachopLayout.setHorizontalGroup(
            pnlMachopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlMachopLayout.setVerticalGroup(
            pnlMachopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlTentacool.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Tentacool-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlTentacoolLayout = new javax.swing.GroupLayout(pnlTentacool);
        pnlTentacool.setLayout(pnlTentacoolLayout);
        pnlTentacoolLayout.setHorizontalGroup(
            pnlTentacoolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlTentacoolLayout.setVerticalGroup(
            pnlTentacoolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlPoliwag.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Poliwag-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlPoliwagLayout = new javax.swing.GroupLayout(pnlPoliwag);
        pnlPoliwag.setLayout(pnlPoliwagLayout);
        pnlPoliwagLayout.setHorizontalGroup(
            pnlPoliwagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlPoliwagLayout.setVerticalGroup(
            pnlPoliwagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlSeel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Seel-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlSeelLayout = new javax.swing.GroupLayout(pnlSeel);
        pnlSeel.setLayout(pnlSeelLayout);
        pnlSeelLayout.setHorizontalGroup(
            pnlSeelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlSeelLayout.setVerticalGroup(
            pnlSeelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlDoduo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel78.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Doduo-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlDoduoLayout = new javax.swing.GroupLayout(pnlDoduo);
        pnlDoduo.setLayout(pnlDoduoLayout);
        pnlDoduoLayout.setHorizontalGroup(
            pnlDoduoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel78, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlDoduoLayout.setVerticalGroup(
            pnlDoduoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel78, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlWeepinbell.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Weepinbell-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlWeepinbellLayout = new javax.swing.GroupLayout(pnlWeepinbell);
        pnlWeepinbell.setLayout(pnlWeepinbellLayout);
        pnlWeepinbellLayout.setHorizontalGroup(
            pnlWeepinbellLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel64, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlWeepinbellLayout.setVerticalGroup(
            pnlWeepinbellLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel64, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlAbra.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Abra-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlAbraLayout = new javax.swing.GroupLayout(pnlAbra);
        pnlAbra.setLayout(pnlAbraLayout);
        pnlAbraLayout.setHorizontalGroup(
            pnlAbraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlAbraLayout.setVerticalGroup(
            pnlAbraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlAlakazam.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Alakazam-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlAlakazamLayout = new javax.swing.GroupLayout(pnlAlakazam);
        pnlAlakazam.setLayout(pnlAlakazamLayout);
        pnlAlakazamLayout.setHorizontalGroup(
            pnlAlakazamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlAlakazamLayout.setVerticalGroup(
            pnlAlakazamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlGeodude.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Geodude-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlGeodudeLayout = new javax.swing.GroupLayout(pnlGeodude);
        pnlGeodude.setLayout(pnlGeodudeLayout);
        pnlGeodudeLayout.setHorizontalGroup(
            pnlGeodudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel68, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlGeodudeLayout.setVerticalGroup(
            pnlGeodudeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel68, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlMachoke.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Machoke-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlMachokeLayout = new javax.swing.GroupLayout(pnlMachoke);
        pnlMachoke.setLayout(pnlMachokeLayout);
        pnlMachokeLayout.setHorizontalGroup(
            pnlMachokeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlMachokeLayout.setVerticalGroup(
            pnlMachokeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlPoliwrath.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Poliwrath-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlPoliwrathLayout = new javax.swing.GroupLayout(pnlPoliwrath);
        pnlPoliwrath.setLayout(pnlPoliwrathLayout);
        pnlPoliwrathLayout.setHorizontalGroup(
            pnlPoliwrathLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlPoliwrathLayout.setVerticalGroup(
            pnlPoliwrathLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlSlowpoke.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel73.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Slowpoke-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlSlowpokeLayout = new javax.swing.GroupLayout(pnlSlowpoke);
        pnlSlowpoke.setLayout(pnlSlowpokeLayout);
        pnlSlowpokeLayout.setHorizontalGroup(
            pnlSlowpokeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlSlowpokeLayout.setVerticalGroup(
            pnlSlowpokeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlFarfetchd.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel77.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Farfetch'd-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlFarfetchdLayout = new javax.swing.GroupLayout(pnlFarfetchd);
        pnlFarfetchd.setLayout(pnlFarfetchdLayout);
        pnlFarfetchdLayout.setHorizontalGroup(
            pnlFarfetchdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel77, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlFarfetchdLayout.setVerticalGroup(
            pnlFarfetchdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel77, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlMagnemite.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel75.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Magnemite-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlMagnemiteLayout = new javax.swing.GroupLayout(pnlMagnemite);
        pnlMagnemite.setLayout(pnlMagnemiteLayout);
        pnlMagnemiteLayout.setHorizontalGroup(
            pnlMagnemiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlMagnemiteLayout.setVerticalGroup(
            pnlMagnemiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlDodrio.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel79.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Dodrio-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlDodrioLayout = new javax.swing.GroupLayout(pnlDodrio);
        pnlDodrio.setLayout(pnlDodrioLayout);
        pnlDodrioLayout.setHorizontalGroup(
            pnlDodrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel79, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlDodrioLayout.setVerticalGroup(
            pnlDodrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel79, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlRapidash.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel72.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Rapidash-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlRapidashLayout = new javax.swing.GroupLayout(pnlRapidash);
        pnlRapidash.setLayout(pnlRapidashLayout);
        pnlRapidashLayout.setHorizontalGroup(
            pnlRapidashLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlRapidashLayout.setVerticalGroup(
            pnlRapidashLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlKadabra.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Kadabra-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlKadabraLayout = new javax.swing.GroupLayout(pnlKadabra);
        pnlKadabra.setLayout(pnlKadabraLayout);
        pnlKadabraLayout.setHorizontalGroup(
            pnlKadabraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlKadabraLayout.setVerticalGroup(
            pnlKadabraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlPrimeape.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Primeape-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlPrimeapeLayout = new javax.swing.GroupLayout(pnlPrimeape);
        pnlPrimeape.setLayout(pnlPrimeapeLayout);
        pnlPrimeapeLayout.setHorizontalGroup(
            pnlPrimeapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlPrimeapeLayout.setVerticalGroup(
            pnlPrimeapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlGrowlithe.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Growlithe-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlGrowlitheLayout = new javax.swing.GroupLayout(pnlGrowlithe);
        pnlGrowlithe.setLayout(pnlGrowlitheLayout);
        pnlGrowlitheLayout.setHorizontalGroup(
            pnlGrowlitheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlGrowlitheLayout.setVerticalGroup(
            pnlGrowlitheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlMachamp.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Machamp-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlMachampLayout = new javax.swing.GroupLayout(pnlMachamp);
        pnlMachamp.setLayout(pnlMachampLayout);
        pnlMachampLayout.setHorizontalGroup(
            pnlMachampLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlMachampLayout.setVerticalGroup(
            pnlMachampLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlGrimer.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel82.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Grimer-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlGrimerLayout = new javax.swing.GroupLayout(pnlGrimer);
        pnlGrimer.setLayout(pnlGrimerLayout);
        pnlGrimerLayout.setHorizontalGroup(
            pnlGrimerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel82, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlGrimerLayout.setVerticalGroup(
            pnlGrimerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel82, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlDewgong.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel81.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Dewgong-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlDewgongLayout = new javax.swing.GroupLayout(pnlDewgong);
        pnlDewgong.setLayout(pnlDewgongLayout);
        pnlDewgongLayout.setHorizontalGroup(
            pnlDewgongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel81, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlDewgongLayout.setVerticalGroup(
            pnlDewgongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel81, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlStarmie.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel115.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Starmie-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlStarmieLayout = new javax.swing.GroupLayout(pnlStarmie);
        pnlStarmie.setLayout(pnlStarmieLayout);
        pnlStarmieLayout.setHorizontalGroup(
            pnlStarmieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlStarmieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel115, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlStarmieLayout.setVerticalGroup(
            pnlStarmieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlStarmieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel115, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlPinsir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel121.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel121.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Pinsir-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlPinsirLayout = new javax.swing.GroupLayout(pnlPinsir);
        pnlPinsir.setLayout(pnlPinsirLayout);
        pnlPinsirLayout.setHorizontalGroup(
            pnlPinsirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlPinsirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel121, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlPinsirLayout.setVerticalGroup(
            pnlPinsirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlPinsirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel121, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlFlareon.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel130.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Flareon-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlFlareonLayout = new javax.swing.GroupLayout(pnlFlareon);
        pnlFlareon.setLayout(pnlFlareonLayout);
        pnlFlareonLayout.setHorizontalGroup(
            pnlFlareonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlFlareonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel130, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlFlareonLayout.setVerticalGroup(
            pnlFlareonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlFlareonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel130, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlDrowzee.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel90.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Drowzee-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlDrowzeeLayout = new javax.swing.GroupLayout(pnlDrowzee);
        pnlDrowzee.setLayout(pnlDrowzeeLayout);
        pnlDrowzeeLayout.setHorizontalGroup(
            pnlDrowzeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlDrowzeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel90, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlDrowzeeLayout.setVerticalGroup(
            pnlDrowzeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(pnlDrowzeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel90, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlGoldeen.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel112.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Goldeen-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlGoldeenLayout = new javax.swing.GroupLayout(pnlGoldeen);
        pnlGoldeen.setLayout(pnlGoldeenLayout);
        pnlGoldeenLayout.setHorizontalGroup(
            pnlGoldeenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlGoldeenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel112, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlGoldeenLayout.setVerticalGroup(
            pnlGoldeenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlGoldeenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel112, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlKrabby.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel92.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Krabby-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlKrabbyLayout = new javax.swing.GroupLayout(pnlKrabby);
        pnlKrabby.setLayout(pnlKrabbyLayout);
        pnlKrabbyLayout.setHorizontalGroup(
            pnlKrabbyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlKrabbyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel92, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlKrabbyLayout.setVerticalGroup(
            pnlKrabbyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(pnlKrabbyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel92, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlHitmonchan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel101.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Hitmonchan-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlHitmonchanLayout = new javax.swing.GroupLayout(pnlHitmonchan);
        pnlHitmonchan.setLayout(pnlHitmonchanLayout);
        pnlHitmonchanLayout.setHorizontalGroup(
            pnlHitmonchanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlHitmonchanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel101, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlHitmonchanLayout.setVerticalGroup(
            pnlHitmonchanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlHitmonchanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel101, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlDragonair.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel142.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel142.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Dragonair-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlDragonairLayout = new javax.swing.GroupLayout(pnlDragonair);
        pnlDragonair.setLayout(pnlDragonairLayout);
        pnlDragonairLayout.setHorizontalGroup(
            pnlDragonairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlDragonairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel142, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlDragonairLayout.setVerticalGroup(
            pnlDragonairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlDragonairLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel142, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlTauros.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel122.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Tauros-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlTaurosLayout = new javax.swing.GroupLayout(pnlTauros);
        pnlTauros.setLayout(pnlTaurosLayout);
        pnlTaurosLayout.setHorizontalGroup(
            pnlTaurosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlTaurosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel122, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlTaurosLayout.setVerticalGroup(
            pnlTaurosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlTaurosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel122, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlMagmar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel120.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Magmar-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlMagmarLayout = new javax.swing.GroupLayout(pnlMagmar);
        pnlMagmar.setLayout(pnlMagmarLayout);
        pnlMagmarLayout.setHorizontalGroup(
            pnlMagmarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlMagmarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel120, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlMagmarLayout.setVerticalGroup(
            pnlMagmarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlMagmarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel120, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlOmastar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel133.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel133.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Omastar-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlOmastarLayout = new javax.swing.GroupLayout(pnlOmastar);
        pnlOmastar.setLayout(pnlOmastarLayout);
        pnlOmastarLayout.setHorizontalGroup(
            pnlOmastarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlOmastarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel133, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlOmastarLayout.setVerticalGroup(
            pnlOmastarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlOmastarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel133, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlRhyhorn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel105.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Rhyhorn-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlRhyhornLayout = new javax.swing.GroupLayout(pnlRhyhorn);
        pnlRhyhorn.setLayout(pnlRhyhornLayout);
        pnlRhyhornLayout.setHorizontalGroup(
            pnlRhyhornLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlRhyhornLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel105, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlRhyhornLayout.setVerticalGroup(
            pnlRhyhornLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlRhyhornLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel105, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlVoltorb.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel94.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Voltorb-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlVoltorbLayout = new javax.swing.GroupLayout(pnlVoltorb);
        pnlVoltorb.setLayout(pnlVoltorbLayout);
        pnlVoltorbLayout.setHorizontalGroup(
            pnlVoltorbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlVoltorbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel94, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlVoltorbLayout.setVerticalGroup(
            pnlVoltorbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlVoltorbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel94, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlKingler.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel93.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Kingler-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlKinglerLayout = new javax.swing.GroupLayout(pnlKingler);
        pnlKingler.setLayout(pnlKinglerLayout);
        pnlKinglerLayout.setHorizontalGroup(
            pnlKinglerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlKinglerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel93, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlKinglerLayout.setVerticalGroup(
            pnlKinglerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(pnlKinglerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel93, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlGastly.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel86.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Gastly-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlGastlyLayout = new javax.swing.GroupLayout(pnlGastly);
        pnlGastly.setLayout(pnlGastlyLayout);
        pnlGastlyLayout.setHorizontalGroup(
            pnlGastlyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlGastlyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel86, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlGastlyLayout.setVerticalGroup(
            pnlGastlyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(pnlGastlyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel86, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlMagikarp.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel123.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel123.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Magikarp-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlMagikarpLayout = new javax.swing.GroupLayout(pnlMagikarp);
        pnlMagikarp.setLayout(pnlMagikarpLayout);
        pnlMagikarpLayout.setHorizontalGroup(
            pnlMagikarpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlMagikarpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel123, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlMagikarpLayout.setVerticalGroup(
            pnlMagikarpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlMagikarpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel123, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlMuk.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel83.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Muk-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlMukLayout = new javax.swing.GroupLayout(pnlMuk);
        pnlMuk.setLayout(pnlMukLayout);
        pnlMukLayout.setHorizontalGroup(
            pnlMukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel83, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlMukLayout.setVerticalGroup(
            pnlMukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel83, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlPorygon.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel131.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel131.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Porygon-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlPorygonLayout = new javax.swing.GroupLayout(pnlPorygon);
        pnlPorygon.setLayout(pnlPorygonLayout);
        pnlPorygonLayout.setHorizontalGroup(
            pnlPorygonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlPorygonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel131, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlPorygonLayout.setVerticalGroup(
            pnlPorygonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlPorygonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel131, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlShellder.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel84.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Shellder-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlShellderLayout = new javax.swing.GroupLayout(pnlShellder);
        pnlShellder.setLayout(pnlShellderLayout);
        pnlShellderLayout.setHorizontalGroup(
            pnlShellderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel84, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlShellderLayout.setVerticalGroup(
            pnlShellderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel84, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlCloyster.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel85.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Cloyster-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlCloysterLayout = new javax.swing.GroupLayout(pnlCloyster);
        pnlCloyster.setLayout(pnlCloysterLayout);
        pnlCloysterLayout.setHorizontalGroup(
            pnlCloysterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(pnlCloysterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel85, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlCloysterLayout.setVerticalGroup(
            pnlCloysterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlCloysterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel85, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlKangaskhan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel109.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Kangaskhan-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlKangaskhanLayout = new javax.swing.GroupLayout(pnlKangaskhan);
        pnlKangaskhan.setLayout(pnlKangaskhanLayout);
        pnlKangaskhanLayout.setHorizontalGroup(
            pnlKangaskhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlKangaskhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel109, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlKangaskhanLayout.setVerticalGroup(
            pnlKangaskhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlKangaskhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel109, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlMewtwo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel144.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel144.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Mewtwo-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlMewtwoLayout = new javax.swing.GroupLayout(pnlMewtwo);
        pnlMewtwo.setLayout(pnlMewtwoLayout);
        pnlMewtwoLayout.setHorizontalGroup(
            pnlMewtwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlMewtwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel144, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlMewtwoLayout.setVerticalGroup(
            pnlMewtwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlMewtwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel144, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlElectrode.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel95.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Electrode-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlElectrodeLayout = new javax.swing.GroupLayout(pnlElectrode);
        pnlElectrode.setLayout(pnlElectrodeLayout);
        pnlElectrodeLayout.setHorizontalGroup(
            pnlElectrodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlElectrodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel95, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlElectrodeLayout.setVerticalGroup(
            pnlElectrodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlElectrodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel95, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlWeezing.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel104.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Weezing-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlWeezingLayout = new javax.swing.GroupLayout(pnlWeezing);
        pnlWeezing.setLayout(pnlWeezingLayout);
        pnlWeezingLayout.setHorizontalGroup(
            pnlWeezingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlWeezingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel104, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlWeezingLayout.setVerticalGroup(
            pnlWeezingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlWeezingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel104, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlCubone.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel98.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel98.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Cubone-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlCuboneLayout = new javax.swing.GroupLayout(pnlCubone);
        pnlCubone.setLayout(pnlCuboneLayout);
        pnlCuboneLayout.setHorizontalGroup(
            pnlCuboneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlCuboneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel98, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlCuboneLayout.setVerticalGroup(
            pnlCuboneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlCuboneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel98, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlJolteon.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel129.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Jolteon-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlJolteonLayout = new javax.swing.GroupLayout(pnlJolteon);
        pnlJolteon.setLayout(pnlJolteonLayout);
        pnlJolteonLayout.setHorizontalGroup(
            pnlJolteonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlJolteonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel129, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlJolteonLayout.setVerticalGroup(
            pnlJolteonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlJolteonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel129, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlMew.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel145.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel145.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Mew-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlMewLayout = new javax.swing.GroupLayout(pnlMew);
        pnlMew.setLayout(pnlMewLayout);
        pnlMewLayout.setHorizontalGroup(
            pnlMewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlMewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel145, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlMewLayout.setVerticalGroup(
            pnlMewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlMewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel145, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlZapdos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel139.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel139.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Zapdos-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlZapdosLayout = new javax.swing.GroupLayout(pnlZapdos);
        pnlZapdos.setLayout(pnlZapdosLayout);
        pnlZapdosLayout.setHorizontalGroup(
            pnlZapdosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlZapdosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel139, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlZapdosLayout.setVerticalGroup(
            pnlZapdosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlZapdosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel139, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlOmanyte.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel132.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel132.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Omanyte-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlOmanyteLayout = new javax.swing.GroupLayout(pnlOmanyte);
        pnlOmanyte.setLayout(pnlOmanyteLayout);
        pnlOmanyteLayout.setHorizontalGroup(
            pnlOmanyteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlOmanyteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel132, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlOmanyteLayout.setVerticalGroup(
            pnlOmanyteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlOmanyteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel132, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlJynx.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel118.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Jynx-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlJynxLayout = new javax.swing.GroupLayout(pnlJynx);
        pnlJynx.setLayout(pnlJynxLayout);
        pnlJynxLayout.setHorizontalGroup(
            pnlJynxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlJynxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel118, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlJynxLayout.setVerticalGroup(
            pnlJynxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlJynxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel118, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlLapras.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel125.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Lapras-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlLaprasLayout = new javax.swing.GroupLayout(pnlLapras);
        pnlLapras.setLayout(pnlLaprasLayout);
        pnlLaprasLayout.setHorizontalGroup(
            pnlLaprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlLaprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel125, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlLaprasLayout.setVerticalGroup(
            pnlLaprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlLaprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel125, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlTangela.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel108.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Tangela-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlTangelaLayout = new javax.swing.GroupLayout(pnlTangela);
        pnlTangela.setLayout(pnlTangelaLayout);
        pnlTangelaLayout.setHorizontalGroup(
            pnlTangelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlTangelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel108, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlTangelaLayout.setVerticalGroup(
            pnlTangelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlTangelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel108, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlAerodactyl.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel136.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel136.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Aerodactyl-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlAerodactylLayout = new javax.swing.GroupLayout(pnlAerodactyl);
        pnlAerodactyl.setLayout(pnlAerodactylLayout);
        pnlAerodactylLayout.setHorizontalGroup(
            pnlAerodactylLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlAerodactylLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel136, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlAerodactylLayout.setVerticalGroup(
            pnlAerodactylLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlAerodactylLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel136, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlSeaking.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel113.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Seaking-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlSeakingLayout = new javax.swing.GroupLayout(pnlSeaking);
        pnlSeaking.setLayout(pnlSeakingLayout);
        pnlSeakingLayout.setHorizontalGroup(
            pnlSeakingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlSeakingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel113, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlSeakingLayout.setVerticalGroup(
            pnlSeakingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlSeakingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel113, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlRhydon.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel106.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Rhydon-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlRhydonLayout = new javax.swing.GroupLayout(pnlRhydon);
        pnlRhydon.setLayout(pnlRhydonLayout);
        pnlRhydonLayout.setHorizontalGroup(
            pnlRhydonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlRhydonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel106, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlRhydonLayout.setVerticalGroup(
            pnlRhydonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlRhydonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel106, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlChansey.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel107.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Chansey-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlChanseyLayout = new javax.swing.GroupLayout(pnlChansey);
        pnlChansey.setLayout(pnlChanseyLayout);
        pnlChanseyLayout.setHorizontalGroup(
            pnlChanseyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlChanseyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel107, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlChanseyLayout.setVerticalGroup(
            pnlChanseyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlChanseyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel107, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlDratini.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel141.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel141.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Dratini-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlDratiniLayout = new javax.swing.GroupLayout(pnlDratini);
        pnlDratini.setLayout(pnlDratiniLayout);
        pnlDratiniLayout.setHorizontalGroup(
            pnlDratiniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlDratiniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel141, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlDratiniLayout.setVerticalGroup(
            pnlDratiniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlDratiniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel141, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlMarowak.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel99.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Marowak-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlMarowakLayout = new javax.swing.GroupLayout(pnlMarowak);
        pnlMarowak.setLayout(pnlMarowakLayout);
        pnlMarowakLayout.setHorizontalGroup(
            pnlMarowakLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlMarowakLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel99, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlMarowakLayout.setVerticalGroup(
            pnlMarowakLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlMarowakLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel99, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlGengar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel88.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Gengar-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlGengarLayout = new javax.swing.GroupLayout(pnlGengar);
        pnlGengar.setLayout(pnlGengarLayout);
        pnlGengarLayout.setHorizontalGroup(
            pnlGengarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlGengarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel88, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlGengarLayout.setVerticalGroup(
            pnlGengarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(pnlGengarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel88, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlElectabuzz.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel119.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Electabuzz-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlElectabuzzLayout = new javax.swing.GroupLayout(pnlElectabuzz);
        pnlElectabuzz.setLayout(pnlElectabuzzLayout);
        pnlElectabuzzLayout.setHorizontalGroup(
            pnlElectabuzzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlElectabuzzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel119, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlElectabuzzLayout.setVerticalGroup(
            pnlElectabuzzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlElectabuzzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel119, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlHorsea.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel110.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Horsea-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlHorseaLayout = new javax.swing.GroupLayout(pnlHorsea);
        pnlHorsea.setLayout(pnlHorseaLayout);
        pnlHorseaLayout.setHorizontalGroup(
            pnlHorseaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlHorseaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel110, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlHorseaLayout.setVerticalGroup(
            pnlHorseaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlHorseaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel110, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlExeggutor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel97.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Exeggutor-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlExeggutorLayout = new javax.swing.GroupLayout(pnlExeggutor);
        pnlExeggutor.setLayout(pnlExeggutorLayout);
        pnlExeggutorLayout.setHorizontalGroup(
            pnlExeggutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlExeggutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel97, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlExeggutorLayout.setVerticalGroup(
            pnlExeggutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlExeggutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel97, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlScyther.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel117.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Scyther-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlScytherLayout = new javax.swing.GroupLayout(pnlScyther);
        pnlScyther.setLayout(pnlScytherLayout);
        pnlScytherLayout.setHorizontalGroup(
            pnlScytherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlScytherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel117, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlScytherLayout.setVerticalGroup(
            pnlScytherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlScytherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel117, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlKabutops.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel135.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel135.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Kabutops-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlKabutopsLayout = new javax.swing.GroupLayout(pnlKabutops);
        pnlKabutops.setLayout(pnlKabutopsLayout);
        pnlKabutopsLayout.setHorizontalGroup(
            pnlKabutopsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlKabutopsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel135, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlKabutopsLayout.setVerticalGroup(
            pnlKabutopsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlKabutopsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel135, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlKoffing.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel103.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Koffing-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlKoffingLayout = new javax.swing.GroupLayout(pnlKoffing);
        pnlKoffing.setLayout(pnlKoffingLayout);
        pnlKoffingLayout.setHorizontalGroup(
            pnlKoffingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlKoffingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel103, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlKoffingLayout.setVerticalGroup(
            pnlKoffingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlKoffingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel103, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlVaporeon.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel128.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Vaporeon-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlVaporeonLayout = new javax.swing.GroupLayout(pnlVaporeon);
        pnlVaporeon.setLayout(pnlVaporeonLayout);
        pnlVaporeonLayout.setHorizontalGroup(
            pnlVaporeonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlVaporeonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel128, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlVaporeonLayout.setVerticalGroup(
            pnlVaporeonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlVaporeonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel128, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlMoltres.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel140.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel140.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Moltres-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlMoltresLayout = new javax.swing.GroupLayout(pnlMoltres);
        pnlMoltres.setLayout(pnlMoltresLayout);
        pnlMoltresLayout.setHorizontalGroup(
            pnlMoltresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlMoltresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel140, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlMoltresLayout.setVerticalGroup(
            pnlMoltresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlMoltresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel140, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlStaryu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel114.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Staryu-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlStaryuLayout = new javax.swing.GroupLayout(pnlStaryu);
        pnlStaryu.setLayout(pnlStaryuLayout);
        pnlStaryuLayout.setHorizontalGroup(
            pnlStaryuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlStaryuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel114, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlStaryuLayout.setVerticalGroup(
            pnlStaryuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlStaryuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel114, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlEevee.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel127.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel127.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Eevee-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlEeveeLayout = new javax.swing.GroupLayout(pnlEevee);
        pnlEevee.setLayout(pnlEeveeLayout);
        pnlEeveeLayout.setHorizontalGroup(
            pnlEeveeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlEeveeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel127, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlEeveeLayout.setVerticalGroup(
            pnlEeveeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlEeveeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel127, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlGyarados.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel124.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Gyarados-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlGyaradosLayout = new javax.swing.GroupLayout(pnlGyarados);
        pnlGyarados.setLayout(pnlGyaradosLayout);
        pnlGyaradosLayout.setHorizontalGroup(
            pnlGyaradosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlGyaradosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel124, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlGyaradosLayout.setVerticalGroup(
            pnlGyaradosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlGyaradosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel124, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlDragonite.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel143.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel143.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Dragonite-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlDragoniteLayout = new javax.swing.GroupLayout(pnlDragonite);
        pnlDragonite.setLayout(pnlDragoniteLayout);
        pnlDragoniteLayout.setHorizontalGroup(
            pnlDragoniteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlDragoniteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel143, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlDragoniteLayout.setVerticalGroup(
            pnlDragoniteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlDragoniteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel143, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlHaunter.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel87.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Haunter-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlHaunterLayout = new javax.swing.GroupLayout(pnlHaunter);
        pnlHaunter.setLayout(pnlHaunterLayout);
        pnlHaunterLayout.setHorizontalGroup(
            pnlHaunterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlHaunterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel87, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlHaunterLayout.setVerticalGroup(
            pnlHaunterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(pnlHaunterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel87, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlSeadra.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel111.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Seadra-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlSeadraLayout = new javax.swing.GroupLayout(pnlSeadra);
        pnlSeadra.setLayout(pnlSeadraLayout);
        pnlSeadraLayout.setHorizontalGroup(
            pnlSeadraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlSeadraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel111, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlSeadraLayout.setVerticalGroup(
            pnlSeadraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlSeadraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel111, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlHitmonlee.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel100.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Hitmonlee-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlHitmonleeLayout = new javax.swing.GroupLayout(pnlHitmonlee);
        pnlHitmonlee.setLayout(pnlHitmonleeLayout);
        pnlHitmonleeLayout.setHorizontalGroup(
            pnlHitmonleeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlHitmonleeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel100, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlHitmonleeLayout.setVerticalGroup(
            pnlHitmonleeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlHitmonleeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel100, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlSnorlax.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel137.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel137.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Snorlax-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlSnorlaxLayout = new javax.swing.GroupLayout(pnlSnorlax);
        pnlSnorlax.setLayout(pnlSnorlaxLayout);
        pnlSnorlaxLayout.setHorizontalGroup(
            pnlSnorlaxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlSnorlaxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel137, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlSnorlaxLayout.setVerticalGroup(
            pnlSnorlaxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlSnorlaxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel137, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlExeggcute.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel96.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Exeggcute-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlExeggcuteLayout = new javax.swing.GroupLayout(pnlExeggcute);
        pnlExeggcute.setLayout(pnlExeggcuteLayout);
        pnlExeggcuteLayout.setHorizontalGroup(
            pnlExeggcuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlExeggcuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel96, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlExeggcuteLayout.setVerticalGroup(
            pnlExeggcuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlExeggcuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel96, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlLickitung.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel102.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Lickitung-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlLickitungLayout = new javax.swing.GroupLayout(pnlLickitung);
        pnlLickitung.setLayout(pnlLickitungLayout);
        pnlLickitungLayout.setHorizontalGroup(
            pnlLickitungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlLickitungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel102, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlLickitungLayout.setVerticalGroup(
            pnlLickitungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlLickitungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel102, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlMrMime.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel116.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Mr. Mime-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlMrMimeLayout = new javax.swing.GroupLayout(pnlMrMime);
        pnlMrMime.setLayout(pnlMrMimeLayout);
        pnlMrMimeLayout.setHorizontalGroup(
            pnlMrMimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlMrMimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel116, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlMrMimeLayout.setVerticalGroup(
            pnlMrMimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlMrMimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel116, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlKabuto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel134.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel134.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Kabuto-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlKabutoLayout = new javax.swing.GroupLayout(pnlKabuto);
        pnlKabuto.setLayout(pnlKabutoLayout);
        pnlKabutoLayout.setHorizontalGroup(
            pnlKabutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlKabutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel134, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlKabutoLayout.setVerticalGroup(
            pnlKabutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlKabutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel134, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlOnix.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel89.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Onix-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlOnixLayout = new javax.swing.GroupLayout(pnlOnix);
        pnlOnix.setLayout(pnlOnixLayout);
        pnlOnixLayout.setHorizontalGroup(
            pnlOnixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlOnixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel89, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlOnixLayout.setVerticalGroup(
            pnlOnixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(pnlOnixLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel89, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlDitto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel126.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel126.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Ditto-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlDittoLayout = new javax.swing.GroupLayout(pnlDitto);
        pnlDitto.setLayout(pnlDittoLayout);
        pnlDittoLayout.setHorizontalGroup(
            pnlDittoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlDittoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel126, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlDittoLayout.setVerticalGroup(
            pnlDittoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlDittoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel126, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlHypno.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel91.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Hypno-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlHypnoLayout = new javax.swing.GroupLayout(pnlHypno);
        pnlHypno.setLayout(pnlHypnoLayout);
        pnlHypnoLayout.setHorizontalGroup(
            pnlHypnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlHypnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel91, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlHypnoLayout.setVerticalGroup(
            pnlHypnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(pnlHypnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel91, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlArticuno.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel138.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel138.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Articuno-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlArticunoLayout = new javax.swing.GroupLayout(pnlArticuno);
        pnlArticuno.setLayout(pnlArticunoLayout);
        pnlArticunoLayout.setHorizontalGroup(
            pnlArticunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlArticunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel138, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );
        pnlArticunoLayout.setVerticalGroup(
            pnlArticunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlArticunoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel138, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        );

        pnlBeedrill.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Beedrill-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlBeedrillLayout = new javax.swing.GroupLayout(pnlBeedrill);
        pnlBeedrill.setLayout(pnlBeedrillLayout);
        pnlBeedrillLayout.setHorizontalGroup(
            pnlBeedrillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlBeedrillLayout.setVerticalGroup(
            pnlBeedrillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlRaticate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Raticate-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlRaticateLayout = new javax.swing.GroupLayout(pnlRaticate);
        pnlRaticate.setLayout(pnlRaticateLayout);
        pnlRaticateLayout.setHorizontalGroup(
            pnlRaticateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlRaticateLayout.setVerticalGroup(
            pnlRaticateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlSpearow.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Spearow-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlSpearowLayout = new javax.swing.GroupLayout(pnlSpearow);
        pnlSpearow.setLayout(pnlSpearowLayout);
        pnlSpearowLayout.setHorizontalGroup(
            pnlSpearowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlSpearowLayout.setVerticalGroup(
            pnlSpearowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        pnlFearow.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/pokemon/Fearow-front.png"))); // NOI18N

        javax.swing.GroupLayout pnlFearowLayout = new javax.swing.GroupLayout(pnlFearow);
        pnlFearow.setLayout(pnlFearowLayout);
        pnlFearowLayout.setHorizontalGroup(
            pnlFearowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        pnlFearowLayout.setVerticalGroup(
            pnlFearowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlChoosePokemonLayout = new javax.swing.GroupLayout(pnlChoosePokemon);
        pnlChoosePokemon.setLayout(pnlChoosePokemonLayout);
        pnlChoosePokemonLayout.setHorizontalGroup(
            pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChoosePokemonLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlChoosePokemonLayout.createSequentialGroup()
                        .addGroup(pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlChoosePokemonLayout.createSequentialGroup()
                                .addComponent(pnlEkans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlArbok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlPikachu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlChoosePokemonLayout.createSequentialGroup()
                                .addComponent(pnlButterfree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlWeedle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlKakuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlChoosePokemonLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(pnlRaichu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlSandshrew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlSandslash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlNidoranF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlNidorina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlNidoqueen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlNidoranM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlNidorino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlChoosePokemonLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlBeedrill, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlPidgey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlPidgeotto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlPidgeot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlRattata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlRaticate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlSpearow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlFearow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlChoosePokemonLayout.createSequentialGroup()
                        .addComponent(pnlMrMime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlScyther, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlJynx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlElectabuzz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlMagmar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlPinsir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlTauros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlMagikarp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlGyarados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlLapras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlDitto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlChoosePokemonLayout.createSequentialGroup()
                        .addComponent(pnlEevee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlVaporeon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlJolteon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlFlareon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlPorygon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlOmanyte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlOmastar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlKabuto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlKabutops, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlAerodactyl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlSnorlax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlChoosePokemonLayout.createSequentialGroup()
                        .addComponent(pnlArticuno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlZapdos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlMoltres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlDratini, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlDragonair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlDragonite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlMewtwo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlMew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlChoosePokemonLayout.createSequentialGroup()
                        .addComponent(pnlRhyhorn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlRhydon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlChansey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlTangela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlKangaskhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlHorsea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlSeadra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlGoldeen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlSeaking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlStaryu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlStarmie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlChoosePokemonLayout.createSequentialGroup()
                        .addGroup(pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlChoosePokemonLayout.createSequentialGroup()
                                .addComponent(pnlMuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlShellder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlCloyster, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlChoosePokemonLayout.createSequentialGroup()
                                .addComponent(pnlVoltorb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlElectrode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlExeggcute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(6, 6, 6)
                        .addGroup(pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlChoosePokemonLayout.createSequentialGroup()
                                .addComponent(pnlExeggutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlCubone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlMarowak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlHitmonlee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlHitmonchan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlLickitung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlKoffing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlWeezing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlChoosePokemonLayout.createSequentialGroup()
                                .addComponent(pnlGastly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlHaunter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlGengar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlOnix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlDrowzee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlHypno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlKrabby, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlKingler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlChoosePokemonLayout.createSequentialGroup()
                            .addComponent(pnlRapidash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addComponent(pnlSlowpoke, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addComponent(pnlSlowbro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addComponent(pnlMagnemite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addComponent(pnlMagneton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addComponent(pnlFarfetchd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addComponent(pnlDoduo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addComponent(pnlDodrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addComponent(pnlSeel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addComponent(pnlDewgong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(pnlGrimer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlChoosePokemonLayout.createSequentialGroup()
                                .addComponent(pnlMankey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlPrimeape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlGrowlithe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlArcanine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlPoliwag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlPoliwhirl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlPoliwrath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlAbra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlKadabra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlAlakazam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlMachop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlChoosePokemonLayout.createSequentialGroup()
                                .addComponent(pnlMachoke, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlMachamp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlBellsprout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlWeepinbell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlVictreebel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlTentacool, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlTentacruel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlGeodude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlGraveler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlGolem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlPonyta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlChoosePokemonLayout.createSequentialGroup()
                                .addComponent(pnlBulbasaur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlIvysaur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlVenusaur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlCharmander, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlCharmeleon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlCharizard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlSquirtle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlWartortle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlBlastoise, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlCaterpie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlMetapod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlChoosePokemonLayout.createSequentialGroup()
                                .addComponent(pnlNidoking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlClefairy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlClefable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlVulpix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlNinetales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlJigglypuff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlWigglytuff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlZubat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlGolbat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlOddish, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlGloom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlChoosePokemonLayout.createSequentialGroup()
                                .addComponent(pnlVileplume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlParas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlParasect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlVenonat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlVenomoth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlDiglett, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlDugtrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlMeowth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlPersian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlPsyduck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(pnlGolduck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        pnlChoosePokemonLayout.setVerticalGroup(
            pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChoosePokemonLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlBulbasaur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlIvysaur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlVenusaur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlBlastoise, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlCharmander, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlCharmeleon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlCaterpie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlMetapod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlSquirtle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlWartortle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlCharizard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlButterfree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlWeedle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlKakuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlPidgey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlPidgeotto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlPidgeot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlRattata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlBeedrill, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlRaticate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlSpearow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlFearow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(pnlEkans, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlArbok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlRaichu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlSandshrew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlSandslash, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlNidoranF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlNidorina, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlNidoqueen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlNidoranM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlNidorino, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(pnlPikachu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlClefairy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlVulpix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlNinetales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlJigglypuff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlWigglytuff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlZubat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlGolbat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlOddish, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlGloom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlNidoking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlClefable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlVileplume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlParas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlParasect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlVenonat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlVenomoth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlDiglett, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlDugtrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlMeowth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlPersian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlPsyduck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlGolduck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlMankey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlPrimeape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlGrowlithe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlArcanine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlPoliwag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlPoliwhirl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlPoliwrath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlAbra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlKadabra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlAlakazam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlMachop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlMachoke, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlMachamp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlBellsprout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlWeepinbell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlVictreebel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlTentacool, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlTentacruel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlGeodude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlGraveler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlGolem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlPonyta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlRapidash, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSlowpoke, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSlowbro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMagnemite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMagneton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlFarfetchd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDoduo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDodrio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSeel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDewgong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlGrimer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlMuk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlShellder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlGastly, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlHaunter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlGengar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlOnix, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDrowzee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlHypno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlKrabby, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlKingler, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlCloyster, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlVoltorb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlElectrode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlExeggcute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlExeggutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlCubone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlMarowak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlHitmonlee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlHitmonchan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlLickitung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlKoffing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlWeezing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlRhyhorn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlRhydon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlChansey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlTangela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlKangaskhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlHorsea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlSeadra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlGoldeen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlSeaking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlStaryu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlStarmie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlMrMime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlScyther, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlJynx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlElectabuzz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlMagmar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlPinsir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlTauros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlMagikarp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlGyarados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlLapras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlDitto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlEevee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlVaporeon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlJolteon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlFlareon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlPorygon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlOmanyte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlOmastar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlKabuto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlKabutops, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlAerodactyl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlSnorlax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pnlChoosePokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlArticuno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlZapdos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlMoltres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlDratini, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlDragonair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlDragonite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlMewtwo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlMew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        scrollPane.setViewportView(pnlChoosePokemon);

        panel.add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 1230, 370));

        javax.swing.GroupLayout pnlFirstPokemonLayout = new javax.swing.GroupLayout(pnlFirstPokemon);
        pnlFirstPokemon.setLayout(pnlFirstPokemonLayout);
        pnlFirstPokemonLayout.setHorizontalGroup(
            pnlFirstPokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFirstPokemon, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
        );
        pnlFirstPokemonLayout.setVerticalGroup(
            pnlFirstPokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFirstPokemon, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );

        panel.add(pnlFirstPokemon, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 130, 120));

        javax.swing.GroupLayout pnlSecondPokemonLayout = new javax.swing.GroupLayout(pnlSecondPokemon);
        pnlSecondPokemon.setLayout(pnlSecondPokemonLayout);
        pnlSecondPokemonLayout.setHorizontalGroup(
            pnlSecondPokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSecondPokemon, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
        );
        pnlSecondPokemonLayout.setVerticalGroup(
            pnlSecondPokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSecondPokemon, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );

        panel.add(pnlSecondPokemon, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, 130, 120));

        javax.swing.GroupLayout pnlThirdPokemonLayout = new javax.swing.GroupLayout(pnlThirdPokemon);
        pnlThirdPokemon.setLayout(pnlThirdPokemonLayout);
        pnlThirdPokemonLayout.setHorizontalGroup(
            pnlThirdPokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblThirdPokemon, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
        );
        pnlThirdPokemonLayout.setVerticalGroup(
            pnlThirdPokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblThirdPokemon, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );

        panel.add(pnlThirdPokemon, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 120, 130, 120));

        javax.swing.GroupLayout pnlFourthPokemonLayout = new javax.swing.GroupLayout(pnlFourthPokemon);
        pnlFourthPokemon.setLayout(pnlFourthPokemonLayout);
        pnlFourthPokemonLayout.setHorizontalGroup(
            pnlFourthPokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFourthPokemon, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
        );
        pnlFourthPokemonLayout.setVerticalGroup(
            pnlFourthPokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFourthPokemon, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );

        panel.add(pnlFourthPokemon, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 120, -1, 120));

        javax.swing.GroupLayout pblFifthPokemonLayout = new javax.swing.GroupLayout(pblFifthPokemon);
        pblFifthPokemon.setLayout(pblFifthPokemonLayout);
        pblFifthPokemonLayout.setHorizontalGroup(
            pblFifthPokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFifthPokemon, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
        );
        pblFifthPokemonLayout.setVerticalGroup(
            pblFifthPokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFifthPokemon, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );

        panel.add(pblFifthPokemon, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 120, 130, 120));

        javax.swing.GroupLayout pnlSixthPokemonLayout = new javax.swing.GroupLayout(pnlSixthPokemon);
        pnlSixthPokemon.setLayout(pnlSixthPokemonLayout);
        pnlSixthPokemonLayout.setHorizontalGroup(
            pnlSixthPokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSixthPokemon, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
        );
        pnlSixthPokemonLayout.setVerticalGroup(
            pnlSixthPokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSixthPokemon, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );

        panel.add(pnlSixthPokemon, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 120, 130, 120));

        btnGo.setBackground(new java.awt.Color(0, 153, 255));
        btnGo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnGo.setForeground(new java.awt.Color(255, 255, 255));
        btnGo.setText("GO");
        btnGo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoActionPerformed(evt);
            }
        });
        panel.add(btnGo, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 640, 300, 60));

        lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/sim/image/16-9-background.png"))); // NOI18N
        panel.add(lblBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, 720));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoActionPerformed
        // TODO add your handling code here:
        if (!hasValidTeam()) {
            JOptionPane.showMessageDialog(this,
                    "Please select at least one Pokemon for your team!",
                    "No Pokemon Selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Show team summary
        StringBuilder teamSummary = new StringBuilder("Your Team:\n");
        for (int i = 0; i < TEAM_SIZE; i++) {
            if (selectedPokemon[i] != null) {
                teamSummary.append((i + 1)).append(". ").append(selectedPokemon[i]).append("\n");
            }
        }

        int result = JOptionPane.showConfirmDialog(this,
                teamSummary.toString() + "\nProceed with this team?",
                "Confirm Team Selection",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            logger.info("Team confirmed: " + java.util.Arrays.toString(selectedPokemon));
            // TODO: Proceed to next screen or return selected team
            dispose();
            
        }
    }//GEN-LAST:event_btnGoActionPerformed

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
                ChoosingPokemonJDialog dialog = new ChoosingPokemonJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnGo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JLabel lblBackground;
    private javax.swing.JLabel lblBlastoise;
    private javax.swing.JLabel lblCharizard;
    private javax.swing.JLabel lblClefable;
    private javax.swing.JLabel lblFifthPokemon;
    private javax.swing.JLabel lblFirstPokemon;
    private javax.swing.JLabel lblFourthPokemon;
    private javax.swing.JLabel lblNidoking;
    private javax.swing.JLabel lblPikachu;
    private javax.swing.JLabel lblSecondPokemon;
    private javax.swing.JLabel lblSixthPokemon;
    private javax.swing.JLabel lblThirdPokemon;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblVenusaur;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel pblFifthPokemon;
    private javax.swing.JPanel pnlAbra;
    private javax.swing.JPanel pnlAerodactyl;
    private javax.swing.JPanel pnlAlakazam;
    private javax.swing.JPanel pnlArbok;
    private javax.swing.JPanel pnlArcanine;
    private javax.swing.JPanel pnlArticuno;
    private javax.swing.JPanel pnlBeedrill;
    private javax.swing.JPanel pnlBellsprout;
    private javax.swing.JPanel pnlBlastoise;
    private javax.swing.JPanel pnlBulbasaur;
    private javax.swing.JPanel pnlButterfree;
    private javax.swing.JPanel pnlCaterpie;
    private javax.swing.JPanel pnlChansey;
    private javax.swing.JPanel pnlCharizard;
    private javax.swing.JPanel pnlCharmander;
    private javax.swing.JPanel pnlCharmeleon;
    private javax.swing.JPanel pnlChoosePokemon;
    private javax.swing.JPanel pnlClefable;
    private javax.swing.JPanel pnlClefairy;
    private javax.swing.JPanel pnlCloyster;
    private javax.swing.JPanel pnlCubone;
    private javax.swing.JPanel pnlDewgong;
    private javax.swing.JPanel pnlDiglett;
    private javax.swing.JPanel pnlDitto;
    private javax.swing.JPanel pnlDodrio;
    private javax.swing.JPanel pnlDoduo;
    private javax.swing.JPanel pnlDragonair;
    private javax.swing.JPanel pnlDragonite;
    private javax.swing.JPanel pnlDratini;
    private javax.swing.JPanel pnlDrowzee;
    private javax.swing.JPanel pnlDugtrio;
    private javax.swing.JPanel pnlEevee;
    private javax.swing.JPanel pnlEkans;
    private javax.swing.JPanel pnlElectabuzz;
    private javax.swing.JPanel pnlElectrode;
    private javax.swing.JPanel pnlExeggcute;
    private javax.swing.JPanel pnlExeggutor;
    private javax.swing.JPanel pnlFarfetchd;
    private javax.swing.JPanel pnlFearow;
    private javax.swing.JPanel pnlFirstPokemon;
    private javax.swing.JPanel pnlFlareon;
    private javax.swing.JPanel pnlFourthPokemon;
    private javax.swing.JPanel pnlGastly;
    private javax.swing.JPanel pnlGengar;
    private javax.swing.JPanel pnlGeodude;
    private javax.swing.JPanel pnlGloom;
    private javax.swing.JPanel pnlGolbat;
    private javax.swing.JPanel pnlGoldeen;
    private javax.swing.JPanel pnlGolduck;
    private javax.swing.JPanel pnlGolem;
    private javax.swing.JPanel pnlGraveler;
    private javax.swing.JPanel pnlGrimer;
    private javax.swing.JPanel pnlGrowlithe;
    private javax.swing.JPanel pnlGyarados;
    private javax.swing.JPanel pnlHaunter;
    private javax.swing.JPanel pnlHitmonchan;
    private javax.swing.JPanel pnlHitmonlee;
    private javax.swing.JPanel pnlHorsea;
    private javax.swing.JPanel pnlHypno;
    private javax.swing.JPanel pnlIvysaur;
    private javax.swing.JPanel pnlJigglypuff;
    private javax.swing.JPanel pnlJolteon;
    private javax.swing.JPanel pnlJynx;
    private javax.swing.JPanel pnlKabuto;
    private javax.swing.JPanel pnlKabutops;
    private javax.swing.JPanel pnlKadabra;
    private javax.swing.JPanel pnlKakuna;
    private javax.swing.JPanel pnlKangaskhan;
    private javax.swing.JPanel pnlKingler;
    private javax.swing.JPanel pnlKoffing;
    private javax.swing.JPanel pnlKrabby;
    private javax.swing.JPanel pnlLapras;
    private javax.swing.JPanel pnlLickitung;
    private javax.swing.JPanel pnlMachamp;
    private javax.swing.JPanel pnlMachoke;
    private javax.swing.JPanel pnlMachop;
    private javax.swing.JPanel pnlMagikarp;
    private javax.swing.JPanel pnlMagmar;
    private javax.swing.JPanel pnlMagnemite;
    private javax.swing.JPanel pnlMagneton;
    private javax.swing.JPanel pnlMankey;
    private javax.swing.JPanel pnlMarowak;
    private javax.swing.JPanel pnlMeowth;
    private javax.swing.JPanel pnlMetapod;
    private javax.swing.JPanel pnlMew;
    private javax.swing.JPanel pnlMewtwo;
    private javax.swing.JPanel pnlMoltres;
    private javax.swing.JPanel pnlMrMime;
    private javax.swing.JPanel pnlMuk;
    private javax.swing.JPanel pnlNidoking;
    private javax.swing.JPanel pnlNidoqueen;
    private javax.swing.JPanel pnlNidoranF;
    private javax.swing.JPanel pnlNidoranM;
    private javax.swing.JPanel pnlNidorina;
    private javax.swing.JPanel pnlNidorino;
    private javax.swing.JPanel pnlNinetales;
    private javax.swing.JPanel pnlOddish;
    private javax.swing.JPanel pnlOmanyte;
    private javax.swing.JPanel pnlOmastar;
    private javax.swing.JPanel pnlOnix;
    private javax.swing.JPanel pnlParas;
    private javax.swing.JPanel pnlParasect;
    private javax.swing.JPanel pnlPersian;
    private javax.swing.JPanel pnlPidgeot;
    private javax.swing.JPanel pnlPidgeotto;
    private javax.swing.JPanel pnlPidgey;
    private javax.swing.JPanel pnlPikachu;
    private javax.swing.JPanel pnlPinsir;
    private javax.swing.JPanel pnlPoliwag;
    private javax.swing.JPanel pnlPoliwhirl;
    private javax.swing.JPanel pnlPoliwrath;
    private javax.swing.JPanel pnlPonyta;
    private javax.swing.JPanel pnlPorygon;
    private javax.swing.JPanel pnlPrimeape;
    private javax.swing.JPanel pnlPsyduck;
    private javax.swing.JPanel pnlRaichu;
    private javax.swing.JPanel pnlRapidash;
    private javax.swing.JPanel pnlRaticate;
    private javax.swing.JPanel pnlRattata;
    private javax.swing.JPanel pnlRhydon;
    private javax.swing.JPanel pnlRhyhorn;
    private javax.swing.JPanel pnlSandshrew;
    private javax.swing.JPanel pnlSandslash;
    private javax.swing.JPanel pnlScyther;
    private javax.swing.JPanel pnlSeadra;
    private javax.swing.JPanel pnlSeaking;
    private javax.swing.JPanel pnlSecondPokemon;
    private javax.swing.JPanel pnlSeel;
    private javax.swing.JPanel pnlShellder;
    private javax.swing.JPanel pnlSixthPokemon;
    private javax.swing.JPanel pnlSlowbro;
    private javax.swing.JPanel pnlSlowpoke;
    private javax.swing.JPanel pnlSnorlax;
    private javax.swing.JPanel pnlSpearow;
    private javax.swing.JPanel pnlSquirtle;
    private javax.swing.JPanel pnlStarmie;
    private javax.swing.JPanel pnlStaryu;
    private javax.swing.JPanel pnlTangela;
    private javax.swing.JPanel pnlTauros;
    private javax.swing.JPanel pnlTentacool;
    private javax.swing.JPanel pnlTentacruel;
    private javax.swing.JPanel pnlThirdPokemon;
    private javax.swing.JPanel pnlVaporeon;
    private javax.swing.JPanel pnlVenomoth;
    private javax.swing.JPanel pnlVenonat;
    private javax.swing.JPanel pnlVenusaur;
    private javax.swing.JPanel pnlVictreebel;
    private javax.swing.JPanel pnlVileplume;
    private javax.swing.JPanel pnlVoltorb;
    private javax.swing.JPanel pnlVulpix;
    private javax.swing.JPanel pnlWartortle;
    private javax.swing.JPanel pnlWeedle;
    private javax.swing.JPanel pnlWeepinbell;
    private javax.swing.JPanel pnlWeezing;
    private javax.swing.JPanel pnlWigglytuff;
    private javax.swing.JPanel pnlZapdos;
    private javax.swing.JPanel pnlZubat;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
}
