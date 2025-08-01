/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon.sim.entity;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author May5th
 */
public class Pokemon {
    
    private String name;
    private String nationalNumber;
    private List<String> image;
    private List<String> types;
    private String species;
    private float height;
    private float weight;
    private List<Stats> stats;
    
    public Pokemon() {
        this.image = new ArrayList<>();
        this.types = new ArrayList<>();
        this.stats = new ArrayList<>();
    }
    
    public void addStats(Stats statSet) {
        this.stats.add(statSet);
    }

    public Pokemon(String name, String nationalNumber, List<String> image, List<String> types, String species, float height, float weight, List<Stats> stats) {
        this.name = name;
        this.nationalNumber = nationalNumber;
        this.image = image;
        this.types = types;
        this.species = species;
        this.height = height;
        this.weight = weight;
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationalNumber() {
        return nationalNumber;
    }

    public void setNationalNumber(String nationalNumber) {
        this.nationalNumber = nationalNumber;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public List<Stats> getStats() {
        return stats;
    }

    public void setStats(List<Stats> stats) {
        this.stats = stats;
    }
}
