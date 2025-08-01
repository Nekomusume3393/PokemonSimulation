/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon.sim.dao.impl;

import pokemon.sim.dao.UserDAO;
import pokemon.sim.entity.User;
import pokemon.sim.util.XJdbc;
import pokemon.sim.util.XQuery;

import java.util.List;

/**
 *
 * @author LINETH
 */
public class UserDAOImpl implements UserDAO {
    
    String createSql = "INSERT INTO Users (Username, Password, DisplayName, Image, Manager, PokemonOwned, WinCount, LoseCount) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    String updateSql = "UPDATE Users SET Password = ?, DisplayName = ?, Image = ?, Manager = ?, PokemonOwned = ?, WinCount = ?, LoseCount = ? WHERE Username = ?";
    String deleteSql = "DELETE FROM Users WHERE Username = ?";
    String findAllSql = "SELECT * FROM Users";
    String findByIdSql = "SELECT * FROM Users WHERE Username = ?";

    @Override
    public User create(User entity) {
        Object[] values = {
            entity.getUsername(),
            entity.getPassword(),
            entity.getDisplayName(),
            entity.getImage(),
            entity.isManager(),
            entity.getPokemonOwned(),
            entity.getWinCount(),
            entity.getLoseCount()
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override
    public void update(User entity) {
        Object[] values = {
            entity.getPassword(),
            entity.getDisplayName(),
            entity.getImage(),
            entity.isManager(),
            entity.getPokemonOwned(),
            entity.getWinCount(),
            entity.getLoseCount(),
            entity.getUsername()
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public void deleteById(String id) {
        XJdbc.executeUpdate(deleteSql, id);
    }

    @Override
    public List<User> findAll() {
        return XQuery.getBeanList(User.class, findAllSql);
    }

    @Override
    public User findById(String id) {
        return XQuery.getSingleBean(User.class, findByIdSql, id);
    }
}
