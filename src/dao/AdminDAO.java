/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.*;
import connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author PC
 */
public class AdminDAO {
    
//SELF EDITING
    public static Admin check(String username, String pass)
    {
        String sql = "SELECT * FROM Admin WHERE username = ? AND pass = ?";
        
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement st = cn.prepareStatement(sql);){
            st.setString(1, username);
            st.setString(2, pass);
            
            try(ResultSet rs = st.executeQuery();)
            {
                if(rs.next()){
                    Admin adresult = new Admin(rs.getString(1), username, pass);
                    return adresult;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    //SELF EDITING
     public static int changePass(Admin old_admin, String newpass)
    {
        String sql = "UPDATE Admin SET pass = ? WHERE ad_id = ? AND username = ? AND pass = ?";
        
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement pst = cn.prepareStatement(sql);){
            
            pst.setString(1, newpass);
            pst.setString(2, old_admin.getAdmin_id());
            pst.setString(3, old_admin.getUsername());
            pst.setString(4, old_admin.getPassword());
            
            return pst.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }
     
     public static int update(Admin old_admin, String newusername, String newpass, String newname)
    {
        String sql = "UPDATE tbAdmin SET username = ?, pass = ?, name = ? WHERE ad_id = ? AND username = ? AND pass = ?";
        
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement st = cn.prepareStatement(sql);)
        {
            st.setString(1, newusername);
            st.setString(2, newpass);
            st.setString(3, newname);
            st.setString(4, old_admin.getAdmin_id());
            st.setString(5, old_admin.getUsername());
            st.setString(6, old_admin.getPassword());
            
            return st.executeUpdate();
            
        } catch (SQLException ex) { 
            ex.printStackTrace();
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
// END SELF EDITING
     
     
     
     
     
    
}
