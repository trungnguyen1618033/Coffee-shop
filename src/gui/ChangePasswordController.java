/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import AlertMaker.AlertMaker;
import com.jfoenix.controls.JFXPasswordField;
import dao.AdminDAO;
import entity.Account;
import entity.Admin;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ChangePasswordController implements Initializable {

    @FXML
    private Button btn_save;
    @FXML
    private Button btn_cancel;
    @FXML
    private JFXPasswordField tf_passwordConfirm;
    @FXML
    private JFXPasswordField tf_passwordNew;
    @FXML
    private JFXPasswordField tf_passwordOld;

    private Admin ad;
    private Account ac;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void _save(ActionEvent event) {
        String old = new String(tf_passwordOld.getText().trim());
        String passnew = new String(tf_passwordNew.getText().trim());
        String passcon = new String (tf_passwordConfirm.getText().trim());
        if(ad != null){
            if(!(ad.getPassword().equals(old))){
                AlertMaker alert = new AlertMaker();
                alert.showSimpleAlert("WARNING", "Old password is incorrect!");
            }
            if(!passcon.equals(passnew)){
                AlertMaker alert = new AlertMaker();
                alert.showSimpleAlert("WARNING", "Confirm password is not match!");
            }
            if(AdminDAO.changePass(ad, passcon) != 0){
                AlertMaker alert = new AlertMaker();
                alert.showSimpleAlert("Success", "Setting Update");
            }else{
                AlertMaker alert = new AlertMaker();
                alert.showSimpleAlert("WARNING", "Change password fail! Please check conection again!");
            }
        }
        
        if(ac != null){
            if(!(ac.getPassword().equals(old))){
                AlertMaker alert = new AlertMaker();
                alert.showSimpleAlert("WARNING", "Old password is incorrect!");
            }
            if(!passcon.equals(passnew)){
                AlertMaker alert = new AlertMaker();
                alert.showSimpleAlert("WARNING", "Confirm password is not match!");
            }
            if(AdminDAO.changePass(ad, passcon) != 0){
                AlertMaker alert = new AlertMaker();
                alert.showSimpleAlert("Success", "Setting Update");
            }else{
                AlertMaker alert = new AlertMaker();
                alert.showSimpleAlert("WARNING", "Change password fail! Please check conection again!");
            }
            
        }
            
    }

    @FXML
    private void _cancel(ActionEvent event) {
        ((Stage)tf_passwordConfirm.getScene().getWindow()).close();
    }
     public void setAdmin(Admin admin) {
        ad = admin;
    }
     public void setAccount(Account account) {
       ac = account;
    }
    
}
