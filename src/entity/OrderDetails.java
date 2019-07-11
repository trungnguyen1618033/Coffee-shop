/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Vector;

/**
 *
 * @author PC
 */
public class OrderDetails extends Object {
    private String order_id, product_id ;
    private int quantity;
    private float price;

    public OrderDetails() {
       
    }
    
    public float getPrice()
    {
        return price;
    }
    public void setPrice(float price)
    {
        this.price = price;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String orderId) {
        this.order_id = orderId;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String productId) {
        this.product_id = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public OrderDetails( String o, String p, int q, float price){
        this.order_id = o;
        this.product_id = p;
        this.quantity = q;
        this.price = price;
    }
    
}
