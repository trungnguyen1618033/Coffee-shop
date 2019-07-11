/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connection.DBConnection;
import entity.OrderDetails;
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
public class OrderDetailsDAO {

    public static int insert(String order_id, String product_id, int quantity){// thêm cả order và cả các orderdetails của nó
        String sql = "INSERT INTO OrderDetails VALUES (?, ?, ?)";                   // tạo id mới cho order cần thêm vào database
        try(Connection cn = new DBConnection().getCon();){
                       
                    try(PreparedStatement st = cn.prepareStatement(sql)){
                            st.setString(1, order_id);
                            st.setString(2, product_id);
                            st.setInt(3, quantity);
                            
                            st.executeUpdate();
                        }
                        
                        return 1;
                    }
                
           
         catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(OrderDetailsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    
    public static int update(String order_id, String product_id, int quantity){// thêm cả order và cả các orderdetails của nó
        String sql = "Update OrderDetails  SET  quan = ? WHERE order_id = ? and product_id = ? ";                   // tạo id mới cho order cần thêm vào database
        try(Connection cn = new DBConnection().getCon();){
               
                  
                    try(PreparedStatement st = cn.prepareStatement(sql)){
                            st.setInt(1, quantity);
                            st.setString(2, order_id);
                            st.setString(3, product_id);
                           
                            st.executeUpdate();
                        }
                        
                        return 1;
                    }
                
           
         catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(OrderDetailsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }


     public static List<OrderDetails> getList()
    {
        List<OrderDetails> ds = new ArrayList<>();
        String sql = "SELECT * FROM OrderDetails";
                
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement st = cn.prepareStatement(sql);
                ResultSet rs = st.executeQuery())
        {
            
            while(rs.next()){
                OrderDetails newitem = new OrderDetails();
                newitem.setOrder_id(rs.getString(1));
                newitem.setProduct_id(rs.getString(2));
                newitem.setQuantity(rs.getInt(3));
                ds.add(newitem);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return ds;
    }
     
    public static int delete(String order_id_product_id)
    {
        String sql = "DELETE OrderDetails WHERE order_id = ?";
        
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement st = cn.prepareStatement(sql);){
            
            st.setString(1, order_id_product_id);
            
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(OrderDetailsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    
    
     public static int deletef(String food_id)
    {
        String sql = "DELETE OrderDetails WHERE food_id = ?";
        
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement st = cn.prepareStatement(sql);){
            
            st.setString(1, food_id);
            
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(OrderDetailsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
}
