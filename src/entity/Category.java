/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author PC
 */
public class Category extends Object {
    private String category_id, name;

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String id) {
        this.category_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category(String i, String n) {
        this.category_id = i;
        this.name = n;
    }
    
    public Category(){
        this(null, null);
    }
}
