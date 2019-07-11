/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;
import java.util.Vector;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author PC
 */
public class Orders extends Object {
    private String order_id, account_id;
    private java.sql.Date ordertime;
    private float price, customerpay, payback;


    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String accountid) {
        this.account_id = accountid;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public float getCustomerpay() {
        return customerpay;
    }

    public void setCustomerpay(float customerpay) {
        this.customerpay = customerpay;
    }

    public float getPayback() {
        return payback;
    }

    public void setPayback(float payback) {
        this.payback = payback;
    }
    

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String id) {
        this.order_id = id;
    }

    public Date getDate() {
        return ordertime;
    }

    public void setDate(Date date) {
        this.ordertime = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    
    public Orders(String i  , String a, Date d, float p , float cb, float pb ){
        this.order_id = i;
        this.ordertime = d;
        this.account_id = a;
        this.price = p;
        this.customerpay = cb;
        this.payback = pb;
    }
    public Orders(String i , Date d , String a){
        this.order_id = i;
        this.ordertime = d;
        this.account_id = a;
    
    }
     
    public Orders() {
    }
    
}
