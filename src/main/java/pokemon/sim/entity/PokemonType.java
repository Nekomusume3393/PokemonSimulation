/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon.sim.entity;

import java.util.List;

/**
 *
 * @author May5th
 */
public class PokemonType {
    
    public String name;
    public List<String> weaknesses;
    public List<String> resistances;
    public List<String> immunities;

    public PokemonType() {
    }

    public PokemonType(String name, List<String> weaknesses, List<String> resistances, List<String> immunities) {
        this.name = name;
        this.weaknesses = weaknesses;
        this.resistances = resistances;
        this.immunities = immunities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(List<String> weaknesses) {
        this.weaknesses = weaknesses;
    }

    public List<String> getResistances() {
        return resistances;
    }

    public void setResistances(List<String> resistances) {
        this.resistances = resistances;
    }

    public List<String> getImmunities() {
        return immunities;
    }

    public void setImmunities(List<String> immunities) {
        this.immunities = immunities;
    }

    @Override
    public String toString() {
        return String.format("%s: Weak = %s, Resist = %s, Immune = %s", 
                name,
                weaknesses,
                resistances,
                immunities);
    }
}
