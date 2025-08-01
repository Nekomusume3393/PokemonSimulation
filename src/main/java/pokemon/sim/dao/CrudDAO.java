/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pokemon.sim.dao;

import java.util.List;

/**
 *
 * @author May5th
 */
public interface CrudDAO<T, ID> {
    
    T create(T entity);
    void update(T entity);
    void deleteById(ID id);
    List<T> findAll();
    T findById(ID id);
}
