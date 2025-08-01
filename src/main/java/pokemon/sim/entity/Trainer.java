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
public class Trainer {
    
    private String name;
    private String image;
    private List<TeamComp> team;
    private List<Bag> bag;
    private int money; // Give you money after you won the battle

    public Trainer() {
    }

    public Trainer(String name, String image, List<TeamComp> team, List<Bag> bag, int money) {
        this.name = name;
        this.image = image;
        this.team = team;
        this.bag = bag;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<TeamComp> getTeam() {
        return team;
    }

    public void setTeam(List<TeamComp> team) {
        this.team = team;
    }

    public List<Bag> getBag() {
        return bag;
    }

    public void setBag(List<Bag> bag) {
        this.bag = bag;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
