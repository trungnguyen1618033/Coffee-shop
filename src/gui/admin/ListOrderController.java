/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.admin;

import entity.Account;
import entity.OrderDetails;
import entity.Orders;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ListOrderController implements Initializable {

    @FXML
    private Label label_ofOrderDetailsID;
    @FXML
    private Label label_ofProductID;
    @FXML
    private Label label_ofQuantity;
    @FXML
    private Label label_ofName;
    @FXML
    private Label label_ofOrderID;
    @FXML
    private Label label_ofAccountID;
    @FXML
    private Label label_ofOrderTime;
    @FXML
    private Label label_ofPrice;
    @FXML
    private Label label_ofCustomerPay;
    @FXML
    private Label label_ofPayBack;
    @FXML
    private TableView<Orders> tbv_Orders;
    @FXML
    private TableColumn<?, ?> tbCol_OrderID;
    @FXML
    private TableColumn<?, ?> tbCol_AccountID;
    @FXML
    private TableColumn<?, ?> tbCol_OrderTime;
    @FXML
    private TableColumn<?, ?> tbCol_Price;
    @FXML
    private TableColumn<?, ?> tbCol_CustomerPay;
    @FXML
    private TableColumn<?, ?> tbCol_PayBack;
    @FXML
    private TableView<OrderDetails> tbv_OrderDetails;
    @FXML
    private TableColumn<?, ?> tbCol_OrderDetailsID;
    @FXML
    private TableColumn<?, ?> tbCol_ProductID;
    @FXML
    private TableColumn<?, ?> tbCol_Quantity;
    @FXML
    private TableColumn<?, ?> tbCol_DetailPrice;

    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<Orders> data; 
    private ObservableList<OrderDetails> data2;
    @FXML
    private Button btn_xemChiTiet;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        conn = connection.DBConnection.getCon();
        data = FXCollections.observableArrayList();
        data2 = FXCollections.observableArrayList();
        setCellOrderTable();
        LoadOrderFromDatabase();
        setBindings();
        

    }    

    private void LoadOrderFromDatabase() {
        data.clear();

        try
        {
            pst = conn.prepareStatement("Select* from Orders");
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
        
    }

    private void setBindings() {
        {
       tbv_Orders.setOnMouseClicked((MouseEvent event) -> {
           Orders o = tbv_Orders.getItems().get(tbv_Orders.getSelectionModel().getSelectedIndex());
           label_ofOrderID.setText(o.getOrder_id());
           label_ofAccountID.setText(o.getAccount_id());
           label_ofOrderTime.setText(o.getOrdertime().toString());
           label_ofPrice.setText(String.valueOf(o.getPrice()));
           label_ofCustomerPay.setText(String.valueOf(o.getCustomerpay()));
           label_ofPayBack.setText(String.valueOf(o.getPayback()));
          
           
           try {
               // bindings to another table
                LoadDBOrderDetails(label_ofOrderID.getText());
           } catch (SQLException ex)
           {
               Logger.getLogger(ListOrderController.class.getName()).log(Level.SEVERE, null, ex);
           }
           BindingOrderDetails();
           }
       );
       setCell();
    }
    }

    private void setCellOrderTable() {
        tbCol_OrderID.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        tbCol_AccountID.setCellValueFactory(new PropertyValueFactory<>("account_id"));
        tbCol_OrderTime.setCellValueFactory(new PropertyValueFactory<>("ordertime"));
        tbCol_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tbCol_CustomerPay.setCellValueFactory(new PropertyValueFactory<>("customerpay"));
        tbCol_PayBack.setCellValueFactory(new PropertyValueFactory<>("payback"));
    }

    private void LoadDBOrderDetails(String orderid) throws SQLException {
        
        data2.clear();
        

        try 
        {
            
            String query = "SELECT od.order_id, od.product_id, od.quan, p.price * od.quan as 'Price' "
                    + "FROM OrderDetails od inner join Product p on od.product_id = p.product_id "
                    + "WHERE od.order_id = ? ";
            pst = conn.prepareStatement(query);
            pst.setString(1, orderid);
            rs = pst.executeQuery();

            while(rs.next())
            {
                data2.add(new OrderDetails(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getFloat(4)));
            }

            
            tbv_OrderDetails.setItems(data2);
            
           
        }
        catch(SQLException ex)
        {
            
        }
        finally 
        {
            pst.close();
        }
    }

    private void BindingOrderDetails() {
        
        tbv_OrderDetails.setOnMouseClicked((MouseEvent e)->
        {
            OrderDetails od = tbv_OrderDetails.getItems().get(tbv_OrderDetails.getSelectionModel().getSelectedIndex());
            label_ofOrderDetailsID.setText(od.getOrder_id());
            label_ofProductID.setText(od.getProduct_id());
            label_ofQuantity.setText(String.valueOf(od.getQuantity()));
        }
        );
    }
     private void setCell()
    {
        tbCol_OrderDetailsID.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        tbCol_ProductID.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        tbCol_Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tbCol_DetailPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    
    @FXML
    private void _XemChiTiet(ActionEvent event) throws SQLException {
        
        LoadDBOrderDetails(label_ofOrderID.getText());
        
      
            
        /*
        tbv_Orders.setOnMouseClicked((MouseEvent e)->
        {
            Orders o = tbv_Orders.getItems().get(tbv_Orders.getSelectionModel().getSelectedIndex());
            String tempoID = o.getOrder_id();
            String query = "Select* from OrderDetails"
                    + "where order_id = '?'";

            try 
            {
                
                pst = conn.prepareStatement(query);

                pst.setString(1, tempoID);
                System.out.println(query);
                rs = pst.executeQuery();
               

                while(rs.next())
                {
                    data2.add(new OrderDetails(rs.getString(1),rs.getString(2),rs.getInt(3)));
                }
            }
            catch(SQLException ex)
            {
                
            }
            tbv_OrderDetails.setItems(data2);
        }
        );
    */
    }
    
    
    
}
