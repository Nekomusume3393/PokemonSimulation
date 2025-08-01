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
public class TeamComp {
    
    private List<PokemonDetail> pokemon1;
    private List<PokemonDetail> pokemon2;
    private List<PokemonDetail> pokemon3;
    private List<PokemonDetail> pokemon4;
    private List<PokemonDetail> pokemon5;
    private List<PokemonDetail> pokemon6;

    public TeamComp() {
    }

    public TeamComp(List<PokemonDetail> pokemon1, List<PokemonDetail> pokemon2, List<PokemonDetail> pokemon3, List<PokemonDetail> pokemon4, List<PokemonDetail> pokemon5, List<PokemonDetail> pokemon6) {
        this.pokemon1 = pokemon1;
        this.pokemon2 = pokemon2;
        this.pokemon3 = pokemon3;
        this.pokemon4 = pokemon4;
        this.pokemon5 = pokemon5;
        this.pokemon6 = pokemon6;
    }

    public List<PokemonDetail> getPokemon1() {
        return pokemon1;
    }

    public void setPokemon1(List<PokemonDetail> pokemon1) {
        this.pokemon1 = pokemon1;
    }

    public List<PokemonDetail> getPokemon2() {
        return pokemon2;
    }

    public void setPokemon2(List<PokemonDetail> pokemon2) {
        this.pokemon2 = pokemon2;
    }

    public List<PokemonDetail> getPokemon3() {
        return pokemon3;
    }

    public void setPokemon3(List<PokemonDetail> pokemon3) {
        this.pokemon3 = pokemon3;
    }

    public List<PokemonDetail> getPokemon4() {
        return pokemon4;
    }

    public void setPokemon4(List<PokemonDetail> pokemon4) {
        this.pokemon4 = pokemon4;
    }

    public List<PokemonDetail> getPokemon5() {
        return pokemon5;
    }

    public void setPokemon5(List<PokemonDetail> pokemon5) {
        this.pokemon5 = pokemon5;
    }

    public List<PokemonDetail> getPokemon6() {
        return pokemon6;
    }

    public void setPokemon6(List<PokemonDetail> pokemon6) {
        this.pokemon6 = pokemon6;
    }
}
