/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connection.DBConnection;
import entity.OrderDetails;
import entity.Orders;
import entity.Product;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class OrdersDAO {
    

    
      public static List<Orders> getList()
    {
        List<Orders> ds = new ArrayList<>();
        String sql = "SELECT * FROM Order";
                
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement st = cn.prepareStatement(sql);
                ResultSet rs = st.executeQuery())
        {
            while(rs.next()){
                Orders newitem = new Orders();
            
                
                newitem.setOrder_id(rs.getString(1));
                newitem.setAccount_id(rs.getString(2));
                newitem.setOrdertime(rs.getDate(3));
                newitem.setPrice(rs.getInt(4));
                newitem.setCustomerpay(rs.getInt(5));
                newitem.setPayback(rs.getInt(6));
                ds.add(newitem);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return ds;
    }
      
        public static Orders insert(String id_account, Date date){// thêm cả order và cả các orderdetails của nó
        String sql = "SELECT COUNT(order_id) FROM Orders";                    // tạo id mới cho order cần thêm vào database
        
        Orders new_order = new Orders();
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement st = cn.prepareStatement(sql);
                ResultSet rs = st.executeQuery();){
            
            if(rs.next()){
                int current_number_oftbOrder = rs.getInt(1);
                String newid = createid("ORD", String.valueOf(current_number_oftbOrder + 1), 10);
                new_order.setOrder_id(newid);
              
                
                sql = "INSERT INTO Orders VALUES (?, ?, ?, 0, 0, 0)";
                try(PreparedStatement st2 = cn.prepareStatement(sql)){
                    st2.setString(1, new_order.getOrder_id());
                    st2.setString(2, id_account);
                    st2.setDate(3, date);
                    
                    st2.executeUpdate();
                    
                   
                   
                    }

                        return new_order;
                    }
                
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
        
        public static int Update(String order_id, float price, float customerpay, float payback){
        String sql = "UPDATE Orders SET totalprice = ?, customerpay = ? ,payback = ? WHERE order_id = ?";                    
        
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement st = cn.prepareStatement(sql);)
        {
            st.setFloat(1, price);
            st.setFloat(2, customerpay);
            st.setFloat(3, payback);
            st.setString(4, order_id);
           
            
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
        
        
        
        
        
         public static int insert(Orders new_order)
    {
        String sql = "SELECT COUNT(order_id) FROM Orders";                      
        try(Connection cn = new DBConnection().getCon();
                PreparedStatement st = cn.prepareStatement(sql);
                ResultSet rs = st.executeQuery();){
            
            if(rs.next()){
                int current_number_oftbOrder = rs.getInt(1);
               
                
                sql = "INSERT INTO Order VALUES (?, ?, ?, ?, ?, ?)";
                int result = 0;
                do{
                     String newid = createid("ORD", String.valueOf(current_number_oftbOrder + 1), 10);
                    new_order.setAccount_id(newid);



                    try(PreparedStatement st2 = cn.prepareStatement(sql);){

                    st2.setString(1, new_order.getOrder_id());
                    st2.setString(2, new_order.getAccount_id());
                    st2.setDate(3, new_order.getOrdertime());
                    st2.setFloat(4, new_order.getPrice());
                    st2.setFloat(5, new_order.getCustomerpay());
                    st2.setFloat(6, new_order.getPayback());
                    
                    st2.executeUpdate();
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
