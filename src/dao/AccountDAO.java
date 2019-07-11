/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connection.DBConnection;
import entity.Account;
import entity.Admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class AccountDAO {
    
//SELF EDITING
      
    public static Account check(String username, String pass)
    {
        
        Account empresult = null;
        String sql = "SELECT * FROM Account WHERE username = ? AND pass = ?";
        
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement st = cn.prepareStatement(sql);){
        
            st.setString(1, username);
            st.setString(2, pass);
            
            try(ResultSet rs = st.executeQuery();){
                if(rs.next()){
                    empresult = new Account();
                    empresult.setAccount_id(rs.getString(1));
                    empresult.setUsername(rs.getString(2));
                    empresult.setPassword(rs.getString(3));
                    empresult.setName(rs.getString(4));
                    return empresult;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return empresult;
    }
     
      public static int changePass(Account old_emp, String newpass)
    {
        String sql = "UPDATE tbEmployee SET pass = ? WHERE em_id = ?, username = ?, pass = ?";
        
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement st = cn.prepareStatement(sql);){
            
            st.setString(1, newpass);
            st.setString(2, old_emp.getAccount_id());
            st.setString(3, old_emp.getUsername());
            st.setString(4, old_emp.getPassword());
            
            return st.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
   
    public static int update(Account old_acc, String newusername, String newpass, String newname, java.sql.Date newbirth, String newaddr, String newemail, String newphone)
    {
       
        String sql = "UPDATE Account SET username = ?, pass = ?, name = ?, birth = ?, addr = ?, email = ?, phone = ? WHERE em_id = ? and username = ? and pass = ?";
        
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement st = cn.prepareStatement(sql);){
            
            st.setString(1, newusername);
            st.setString(2, newpass);
            st.setString(3, newname);
            st.setDate(4, newbirth);
            st.setString(5, newaddr);
            st.setString(6, newemail);
            st.setString(7, newphone);
            st.setString(8, old_acc.getAccount_id());
            st.setString(9, old_acc.getUsername());
            st.setString(10, old_acc.getPassword());
            
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
     //String i, String username, String pass, String ten, Date birth, String add, String em, String pho
    
    
    
    public static int insert(Account new_emp)
    {
        String sql = "SELECT COUNT(em_id) FROM Account";                        // tạo id mới cho employee cần thêm vào database
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement st = cn.prepareStatement(sql);
                ResultSet rs = st.executeQuery();){
            
            if(rs.next()){
                int current_number_oftbEmployee = rs.getInt(1);
                
                sql = "INSERT Account VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                int result = 0;
                do{
                    String newid = createid("AC", String.valueOf(++current_number_oftbEmployee), 10);
                    new_emp.setAccount_id(newid);



                    try(PreparedStatement st2 = cn.prepareStatement(sql);){

                        st2.setString(1, new_emp.getAccount_id());
                        st2.setString(2, new_emp.getUsername());
                        st2.setString(3, new_emp.getPassword());
                        st2.setString(4, new_emp.getName());
                        st2.setDate(5, new_emp.getBirth_day());
                        st2.setString(6, new_emp.getAddress());
                        st2.setString(7, new_emp.getEmail());
                        st2.setString(8, new_emp.getPhone());

                        result = st2.executeUpdate();
                    }
                } while(result == 0);
                return result;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
     public static int insert()
    {
        Account new_ac = new Account();
        String sql = "SELECT COUNT(ac_id) FROM Account";                        // tạo id mới cho employee cần thêm vào database
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement st = cn.prepareStatement(sql);
                ResultSet rs = st.executeQuery();){
            
            if(rs.next()){
                int current_number_oftbEmployee = rs.getInt(1);
                
                sql = "INSERT Account VALUES (?, '', '', '', '', '', '', '')";
                int result = 0;
                do{
                    String newid = createid("AC", String.valueOf(++current_number_oftbEmployee), 10);
                    new_ac.setAccount_id(newid);



                    try(PreparedStatement st2 = cn.prepareStatement(sql);){

                        st2.setString(1, new_ac.getAccount_id());

                        result = st2.executeUpdate();
                    }
                } while(result == 0);
                return result;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    
      public static int delete(String em_id)
    {
        String sql = "DELETE Account WHERE em_id = ?";
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement pst = cn.prepareStatement(sql);){
            
            pst.setString(1, em_id);
            
            return pst.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    
// WARNING: những DAO có dùng hàm createid thì các record đã tạo rồi sẽ không xoá. Tức là ko nên tạo method delete() để xoá record trong table
    private static String createid(String startid, String number_want_toset, int idsize) {
        String str_result = "";
        
        int blank = idsize - (startid.length() + number_want_toset.length());
        str_result += startid;
        for(int i = 0; i < blank; i++){
            str_result += "0";
        }
        str_result += number_want_toset;
        
        return str_result;
    }
}
