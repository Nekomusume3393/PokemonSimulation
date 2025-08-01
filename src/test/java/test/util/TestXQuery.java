/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pokemon.sim.util.XQuery;

import java.util.List;

/**
 *
 * @author May5th
 */
public class TestXQuery {
    
    private static final Logger logger = LoggerFactory.getLogger(TestXQuery.class);
    
    public static void main(String[] args) {
        testGetBeanList();
        testGetSingleBean();
        testGetSingleBeanWarn();
        testWrongColumn();
        testWrongTable();
    }
    
    static void testGetBeanList() {
        logger.info("testGetBeanList:");
        List<Person> people = XQuery.getBeanList(Person.class, "SELECT Id, Name FROM Test");
        for (Person p : people) {
            logger.info("{} - {}", p.getId(), p.getName());
        }
    }
    
    static void testGetSingleBean() {
        logger.info("testGetSingleBean:");
        Person p = XQuery.getSingleBean(Person.class, "SELECT Id, Name FROM Test WHERE Id = ?", 1);
        if (p != null) {
            logger.info("Found: {}", p.getName());
        } else {
            logger.warn("No record found.");
        }
    }
    
    static void testGetSingleBeanWarn() {
        logger.info("testGetSingleBeanWarn:");
        Person p = XQuery.getSingleBean(Person.class, "SELECT Id, Name FROM Test WHERE Id = ?", 7);
        if (p != null) {
            logger.info("Found: {}", p.getName());
        } else {
            logger.warn("No record found.");
        }
    }
    
    static void testWrongColumn() {
        logger.info("testWrongColumn:");
        try {
            XQuery.getBeanList(Person.class, "SELECT Id, Name, InvalidColumn FROM Test");
        } catch (RuntimeException e) {
            logger.error("Expected error (invalid column): {}", e.getMessage());
        }
    }

    static void testWrongTable() {
        logger.info("testWrongTable:");
        try {
            XQuery.getBeanList(Person.class, "SELECT id, name FROM NoSuchTable");
        } catch (RuntimeException e) {
            logger.error("Expected error (table not found): {}", e.getMessage());
        }
    }
    
    public static class Person {
        private int id;
        private String name;

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}
