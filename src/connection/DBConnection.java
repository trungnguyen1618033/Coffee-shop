/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author PC
 */
public class DBConnection {
   
    
   
    public static Connection getCon()
    {
        Connection con = null;
        
        try
        {
            //1. nap driver jdbc4
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            //2. tao ket noi den co so du lieu
            String url =  "jdbc:sqlserver://localhost:1433;databaseName=COFFEESHOP;user=sa;password=123456789";
            con = DriverManager.getConnection(url);
            
            System.out.println("Connected");

 
            
        }catch(SQLException | ClassNotFoundException ex){
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return con;
    }
}