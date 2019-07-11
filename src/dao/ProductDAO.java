/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connection.DBConnection;
import entity.Product;
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
public class ProductDAO {
    
     public static List<Product> getList()
        {
            List<Product> ds = new ArrayList<>();
            String sql = "SELECT * FROM Product ";
                    
            try(Connection cn = new DBConnection().getCon();
                    PreparedStatement st = cn.prepareStatement(sql);
                    ResultSet rs = st.executeQuery();)
            {
                while(rs.next())
                {
                    Product newrecord = new Product();
                    newrecord.setProduct_id(rs.getString(1));
                    newrecord.setName(rs.getString(2));
                    newrecord.setPrice(rs.getFloat(3));
                    newrecord.setCategoryId(rs.getString(4));
                    
                    ds.add(newrecord);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return ds;
        }
     public static int insert(Product new_food)
    {
        String sql = "SELECT COUNT(food_id) FROM tbFood";                        // tạo id mới cho product cần thêm vào database
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement st = cn.prepareStatement(sql);
                ResultSet rs = st.executeQuery();){
            
            if(rs.next()){
                int current_number_oftbFood = rs.getInt(1);
                
                sql = "INSERT Product VALUES (?, ?, ?, ?, ?, ?)";                    // thêm product mới vào database
                int result = 0;
                do{
                    String newid = createid("Pr", String.valueOf(++current_number_oftbFood), 10);
                    new_food.setProduct_id(newid);


                    try(PreparedStatement st2 = cn.prepareStatement(sql);){

                        st2.setString(1, new_food.getProduct_id());
                        st2.setString(2, new_food.getName());
                        st2.setFloat(3, new_food.getPrice());
                        st2.setString(4, new_food.getCategory_id());

                        result = st2.executeUpdate();
                    }
                }while(result == 0);
                return result;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
     
    public static int insert()
    {
        Product new_product = new Product();
        String sql = "SELECT COUNT(product_id) FROM Product";                        // tạo id mới cho product cần thêm vào database
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement st = cn.prepareStatement(sql);
                ResultSet rs = st.executeQuery();){
            
            if(rs.next()){
                int current_number_oftbFood = rs.getInt(1);
                
                sql = "INSERT Product VALUES (?, '', 0, '')";                    // thêm product mới vào database
                int result = 0;
                do{
                    String newid = createid("Pr", String.valueOf(++current_number_oftbFood), 10);
                    new_product.setProduct_id(newid);


                    try(PreparedStatement st2 = cn.prepareStatement(sql);){

                        st2.setString(1, new_product.getProduct_id());

                        result = st2.executeUpdate();
                    }
                }while(result == 0);
                return result;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
     public static int update(Product old_product, String newname, float newprice, int category)
    {
        String sql = "UPDATE Product SET name = ?, price = ?, category = ? WHERE product_id = ?";
        
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement pst = cn.prepareStatement(sql);){
            
            pst.setString(1, newname);
            pst.setFloat(2, newprice);
            pst.setString(3, old_product.getProduct_id());
            
            return pst.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    
       public static int delete(String product_id)
    {
        String sql = "DELETE Product WHERE product_id = ?";
        
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement pst = cn.prepareStatement(sql);){
            
            pst.setString(1, product_id);
            
            return pst.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
       
       
       public static void Search(int category){
            String sql = "Select Product WHERE category_id = ?";
            try(Connection cn = new DBConnection().getCon();
                PreparedStatement pst = cn.prepareStatement(sql);){
            
            pst.setInt(1, category);
            
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
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
