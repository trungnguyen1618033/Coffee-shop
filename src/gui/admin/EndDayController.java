/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.admin;

import com.jfoenix.controls.JFXDatePicker;
import dao.OrdersDAO;
import entity.OrderDetails;
import entity.Orders;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class EndDayController implements Initializable {

    private ObservableList<Orders> data; 
    @FXML
    private TableColumn<?, ?> tbCol_OrderID;
    @FXML
    private TableColumn<?, ?> tbCol_AccountID;
    @FXML
    private TableColumn<?, ?> tbCol_Price;
    @FXML
    private TableColumn<?, ?> tbCol_CustomerPay;
    @FXML
    private TableColumn<?, ?> tbCol_PayBack;
    @FXML
    private TableView<Orders> tbv_Orders;
    
    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    @FXML
    private TableColumn<?, ?> tbCol_Date;
    @FXML
    private DatePicker firstDateSelector;
    @FXML
    private DatePicker lastDateSelector;
    @FXML
    private Button btn_Load;
    @FXML
    private Label label_ofRevenue;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        

        conn = connection.DBConnection.getCon();
        data = FXCollections.observableArrayList();
        setCellOrderTable();
        
    }    
    private void LoadOrderFromDatabase(Date firstdate,Date lastdate) {
        data.clear();       
        try
        {
             String query = "select* "
                    + "From Orders "
                    + "where ordertime >= ? and ordertime <= ? ";
                            
            pst = conn.prepareStatement(query);
            pst.setDate(1, firstdate);
            pst.setDate(2, lastdate);
            rs = pst.executeQuery();
            while(rs.next())
            {
                data.add(new Orders(rs.getString(1),rs.getString(2),rs.getDate(3),rs.getFloat(4),rs.getFloat(5),rs.getFloat(6)));
            }
           
        }
        catch(SQLException ex)
        {
            
        }
        tbv_Orders.setItems(data);
        label_ofRevenue.setText(String.valueOf(TotalPrice()));
     
    }
    
    private void setCellOrderTable() {
        tbCol_OrderID.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        tbCol_AccountID.setCellValueFactory(new PropertyValueFactory<>("account_id"));
        tbCol_Date.setCellValueFactory(new PropertyValueFactory<>("ordertime"));
        tbCol_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tbCol_CustomerPay.setCellValueFactory(new PropertyValueFactory<>("customerpay"));
        tbCol_PayBack.setCellValueFactory(new PropertyValueFactory<>("payback"));
    }

    /*
    @FXML
    private void _LoadData(ActionEvent event) {
        LocalDate dt = dateSelector.getValue();
        LoadOrderFromDatabase(java.sql.Date.valueOf(dt));
        
    }
    */

    @FXML
    private void _Load(ActionEvent event) {
        if(firstDateSelector.getValue() == null || lastDateSelector.getValue() == null )
        {
            AlertMaker.AlertMaker.showErrorMessage("Warning", " Please pick LastDate and FirstDate");
        }
        else{
            
        LocalDate firstDate = firstDateSelector.getValue();
        LocalDate lastDate = lastDateSelector.getValue();
        if (firstDate.isAfter(lastDate))
        {
            AlertMaker.AlertMaker.showErrorMessage("Errors", "LastDate must be after FirstDate");
        }
        LoadOrderFromDatabase(java.sql.Date.valueOf(firstDate),java.sql.Date.valueOf(lastDate));
        }
        
    }
        
    public Float TotalPrice(){
       float total = 0 ;
       Orders or = new Orders();
       List <List<String>> arrList = new ArrayList();
       for (int i = 0; i < tbv_Orders.getItems().size(); i++)
       {
           or = tbv_Orders.getItems().get(i);
           total= total + or.getPrice();
       }
       
       return total;
    }
    
}
