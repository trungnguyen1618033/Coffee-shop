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
public class Product extends Object {
    private String product_id, name, category_id;
    float price;
    
    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String id) {
        this.product_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }
  

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategoryId(String categoryId) {
        this.category_id = categoryId;
    }
    
    public Product(String i, String n, float p, String c){
        this.product_id = i;
        this.name = n ;
        this.price =  p;
        this.category_id = c; 
    }
    
    
    public Product(){
    }
}
