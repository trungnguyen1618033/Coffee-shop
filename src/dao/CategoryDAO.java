/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connection.DBConnection;
import entity.Category;
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
public class CategoryDAO {
    
    public static int getListAll(){
        List<Category> ds = new ArrayList<>();
        String sql = "SELECT * FROM Category";
        
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement st = cn.prepareStatement(sql);
                ResultSet rs = st.executeQuery();
                )
        {
         while(rs.next())
                    {
                        Category newrecord = new Category();
                        newrecord.setCategory_id(rs.getString(1));
                        newrecord.setName(rs.getString(2));
                        
                        ds.add(newrecord);
                    }    
        }   catch (SQLException ex){
            ex.printStackTrace();
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
  
    
    public static int insert(Category new_category)
    {
        String sql = "SELECT COUNT(category_id) FROM Category";                        // tạo id mới cho category cần thêm vào database
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement st = cn.prepareStatement(sql);
                ResultSet rs = st.executeQuery();){
            
            if(rs.next()){
                int current_number_ofCategory = rs.getInt(1);
                
                sql = "INSERT Category VALUES (?, ?)";                    // thêm category mới vào database
                int result = 0;
                do{
                    String newid = createid("CA", String.valueOf(++current_number_ofCategory), 10);
                    new_category.setCategory_id(newid);


                    try(PreparedStatement st2 = cn.prepareStatement(sql);){

                        st2.setString(1, new_category.getCategory_id());
                        st2.setString(2, new_category.getName());
                   
                        result = st2.executeUpdate();
                    }
                }while(result == 0);
                return result;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    
    public static int update(Category old_category, String newname)
    {
        String sql = "UPDATE Category SET name = ? WHERE category_id = ?";
        
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement pst = cn.prepareStatement(sql);){
            
            pst.setString(1, newname);
            pst.setString(2, old_category.getName());
            
            return pst.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    
    
    public static int delete(String category_id)
    {
        String sql = "DELETE Catogery WHERE category_id = ?";
        
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement pst = cn.prepareStatement(sql);){
            
            pst.setString(1, category_id);
            
            return pst.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
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
