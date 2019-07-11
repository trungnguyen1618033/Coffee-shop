/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;
import java.util.Vector;

/**
 *
 * @author PC
 */


public class Account {
    private String account_id, username, password, name, phone, email, address ;

    public Account(String username, String pass, String ten, Date birth, String add, String em, String pho ) {
        this.username = username;
        this.password = pass;
        this.name = ten;
        this.birth_day = birth;
        this.address = add;
        this.email = em;
        this.phone = pho;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    private java.sql.Date birth_day;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(Date birth_day) {
        this.birth_day = birth_day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Account(String i, String username, String pass, String ten, Date birth, String add, String em, String pho ){
        this.account_id = i;
        this.username = username;
        this.password = pass;
        this.name = ten;
        this.birth_day = birth;
        this.address = add;
        this.email = em;
        this.phone = pho;
    }
    
    
    
    public Account(){
        
    }
            
}
