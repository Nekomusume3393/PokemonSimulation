/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon.sim.entity;

/**
 *
 * @author May5th
 */
public class User {
    
    private String username;
    private String password;
    private String displayName;
    private String image;
    private boolean manager;
    private int pokemonOwned;
    private int winCount;
    private int loseCount;

    public User() {
    }

    public User(String username, String password, String displayName, String image, boolean manager, int pokemonOwned, int winCount, int loseCount) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.image = image;
        this.manager = manager;
        this.pokemonOwned = pokemonOwned;
        this.winCount = winCount;
        this.loseCount = loseCount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public int getPokemonOwned() {
        return pokemonOwned;
    }

    public void setPokemonOwned(int pokemonOwned) {
        this.pokemonOwned = pokemonOwned;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getLoseCount() {
        return loseCount;
    }

    public void setLoseCount(int loseCount) {
        this.loseCount = loseCount;
    }
}
