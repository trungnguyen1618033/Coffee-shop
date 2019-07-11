/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Vector;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author PC
 */
public class Admin implements java.io.Serializable{
    private String admin_id, username, password;

  public Vector toVector()
    {
        Vector v = new Vector();
        v.add(admin_id);
        v.add(username);
        v.add(password);
        
        return v;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String ad_id) {
        this.admin_id = ad_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passWord) {
        this.password = passWord;
    }
    
    public Admin(String id, String name, String pass) {
        this.admin_id = id;
        this.username = name;
        this.password = pass;
    }
    
    public Admin(){
        this(null, null, null);
    }
    
}
