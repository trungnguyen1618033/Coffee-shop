/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import AlertMaker.AlertMaker;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dao.AccountDAO;
import dao.AdminDAO;
import entity.Account;
import entity.Admin;
import gui.account.WorkSpaceController;
import gui.admin.AdminWorkSpaceController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField jtf_userName;
    @FXML
    private JFXPasswordField jpf_passWord;
    @FXML
    private JFXButton btn_cancel;
    
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    @FXML
    private JFXButton btn_Login;
    @FXML
    private AnchorPane anchorPane;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        // TODO
        con = connection.DBConnection.getCon();
    }    
    
    private String getUsernameAdmin(){
        String username="";
        try {
            pst = con.prepareStatement("Select username from admin where username = ?");
            pst.setString(1, jtf_userName.getText());
            rs = pst.executeQuery();
            if(rs.next())
                username = rs.getString(1);
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
         return username;
    }
     private String getPasswordAdmin(){
        String password="";
        try {
           
            pst = con.prepareStatement("Select pass from admin where username = ?");
            pst.setString(1, jtf_userName.getText());
            rs = pst.executeQuery();
            if(rs.next())
                password = rs.getString(1);
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
         return password;
    }
     
     private String getUsernameAccount(){
        String username="";
        try {
            pst = con.prepareStatement("Select username from Account where username = ?");
            pst.setString(1, jtf_userName.getText());
            rs = pst.executeQuery();
            if(rs.next())
                username = rs.getString(1);
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
         return username;
    }
     private String getPasswordAccount(){
        String password="";
        try {
           
            pst = con.prepareStatement("Select pass from Account where username = ?");
            pst.setString(1, jtf_userName.getText());
            rs = pst.executeQuery();
            if(rs.next())
                password = rs.getString(1);
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
         return password;
    }


    @FXML
    private void _cancel(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void _login(ActionEvent event) throws IOException {
        String username = jtf_userName.getText().trim();
        String password = jpf_passWord.getText().trim();
        
        
        Admin ad = AdminDAO.check(username, password);
        Account ac = AccountDAO.check(username, password);
        
        if(ad != null){
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.close();
            
            FXMLLoader  loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/admin/AdminWorkSpace.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            AdminWorkSpaceController controller = loader.getController();
            controller.setAdmin(ad);
            stage.setScene(scene);
            stage.show();
        }
        
        else if(ac != null){
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.close();
            
            FXMLLoader  loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/account/WorkSpace.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            WorkSpaceController controller = loader.getController();
            controller.setAccount(ac);
            stage.setScene(scene);
            stage.show();
        
        }
        else{
            AlertMaker alert = new AlertMaker();
            alert.showSimpleAlert("LOGIN WARNING","Login fail! Please check your Username and Password!");
        }
    }

    @FXML
    private void _enterLogin(KeyEvent event) throws IOException {
        if(event.getCode() == KeyCode.ENTER){
        String username = jtf_userName.getText().trim();
        String password = jpf_passWord.getText().trim();
        
        
        Admin ad = AdminDAO.check(username, password);
        Account ac = AccountDAO.check(username, password);
        
        if(ad != null){
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.close();
            
            FXMLLoader  loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/admin/AdminWorkSpace.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            AdminWorkSpaceController controller = loader.getController();
            controller.setAdmin(ad);
            stage.setScene(scene);
            stage.show();
        }
        
        else if(ac != null){
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.close();
            
            FXMLLoader  loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/account/WorkSpace.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            WorkSpaceController controller = loader.getController();
            controller.setAccount(ac);
            stage.setScene(scene);
            stage.show();
        
        }
        else{
            AlertMaker alert = new AlertMaker();
            alert.showSimpleAlert("LOGIN WARNING","Login fail! Please check your Username and Password!");
        }
        }
    }
    
}
