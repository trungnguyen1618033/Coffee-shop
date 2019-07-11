    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.account;

import AlertMaker.AlertMaker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import com.jfoenix.controls.JFXButton;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import dao.OrderDetailsDAO;
import dao.OrdersDAO;
import dao.ProductDAO;
import entity.Account;
import entity.OrderDetails;
import entity.Orders;
import entity.Product;
import gui.ChangePasswordController;
import gui.admin.ListProductController;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.*;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class WorkSpaceController implements Initializable {

    @FXML
    private Button btn_Settings;
    
    Account ac;
    @FXML
    private TableView<Product> tbv_Product;
    @FXML
    private TableColumn<?, ?> tbCol_productID;
    @FXML
    private TableColumn<?, ?> tbCol_Name;
    @FXML
    private TableColumn<?, ?> tbCol_Category;
    @FXML
    private TableColumn<?, ?> tbCol_Price;

    Entry<Orders,ArrayList<OrderDetails>> entry;
    
    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<Product> data; 
    private ObservableList<OrderDetails> data2;
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<OrderDetails> tbv_Orders;
    @FXML
    private TableColumn<?, ?> tbCol_OrderID;
    @FXML
    private TableColumn<?, ?> tbCol_ProductID;
    @FXML
    private TableColumn<?, ?> tbCol_quantity;
    @FXML
    private TableColumn<?, ?> tbCol_detailPrice;

    @FXML
    private JFXButton jBtn_Add;
    @FXML
    private JFXButton jBtn_Edit;
    @FXML
    private JFXButton jBtn_Delete;

    @FXML
    private Label label_ofProductID;
    @FXML
    private Label label_ofName;
    @FXML
    private Label label_ofPrice;
    @FXML
    private Label label_ofOrderID;
    @FXML
    private Label label_ofAccountID;
    @FXML
    private JFXTextField jtf_customerPay;
    
    LocalDate today = LocalDate.now( ZoneId.of( "Asia/Ho_Chi_Minh" ) );
    
    ObservableList<String> list = FXCollections.observableArrayList("Nước ngọt", "Cà phê", "Trà sửa", "Sinh tố", "Những thứ khác", "Danh sach");
    
    
// danh sách order hiện tại
    @FXML
    private Spinner<Integer> spinner = new Spinner<Integer>();
    @FXML
    private Label label_ofQ;
    @FXML
    private Label label_ofpID;
    @FXML
    private Label label_ofPr;
    @FXML
    private Button btn_Exit;
    @FXML
    private ComboBox<String> cbb_Category;
    @FXML
    private JFXButton jBtn_Search;
    @FXML
    private Label lb_payBack;
    @FXML
    private Label lb_TotalPrice;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        conn = connection.DBConnection.getCon();
        data = FXCollections.observableArrayList();
        data2 = FXCollections.observableArrayList();
        cbb_Category.setItems(list);
        
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 1);
 
        this.spinner.setValueFactory(valueFactory);  
        setCellProductTable();
        LoadProductFromDB();
        bindingsProductfromTableView();
        bindingOrderDetails();
    }    
    private void bindingsProductfromTableView()
    {
       tbv_Product.setOnMouseClicked((MouseEvent event) -> {
        Product p = tbv_Product.getItems().get(tbv_Product.getSelectionModel().getSelectedIndex());
        label_ofProductID.setText(p.getProduct_id());
        label_ofName.setText(p.getName());
        label_ofPrice.setText(String.format("%,.0f",p.getPrice()));
       });
    }
    private void setCellProductTable()
    {
        tbCol_productID.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        tbCol_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbCol_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tbCol_Category.setCellValueFactory(new PropertyValueFactory<>("category_id"));
    }
    


    @FXML
    private void _Settings(ActionEvent event) throws IOException {
        
        Stage stage = new Stage();
        FXMLLoader  loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/ChangePassword.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ChangePasswordController controller = loader.getController();
        controller.setAccount(ac);
        stage.setScene(scene);
        stage.show();
        
        
    }
    private void LoadProductFromDB()
    {
        data.clear();
        try {
            pst = conn.prepareStatement("Select * from Product");
            rs = pst.executeQuery();
            
            while (rs.next())
            {
                data.add(new Product(rs.getString(1),rs.getString(2), +rs.getFloat(3),rs.getString(4)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ListProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tbv_Product.setItems(data);  
    }
    
    public void LoadInfoOrder(){
        Orders orders =  OrdersDAO.insert(label_ofAccountID.getText(), java.sql.Date.valueOf(this.today));
        label_ofOrderID.setText(orders.getOrder_id());
        
    }
    
    public void setAccount(Account account) {
       ac = account;
       label_ofAccountID.setText(ac.getAccount_id());
       
    }

 
    
    private void loadOrdersDetailFromDB()
    {
        data2.clear();
            
    }
    private String autoOrderID()
    {
        
        
        String orderID = "oID00000";
        try {
            pst = conn.prepareStatement("SELECT max(order_id) from Orders");
            rs = pst.executeQuery();
            if (rs.next())
            {
                int n = Integer.parseInt(orderID.substring(3)) + 1;
                int m = String.valueOf(n).length();
                orderID = orderID.substring(0, 8 - m) + String.valueOf(n);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(WorkSpaceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderID;
    }
    private String getRealTimeDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy//MM//dd HH:mm:ss" );
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    @FXML
    private void _Add(ActionEvent event) throws SQLException {
        
        
        String orderid = label_ofOrderID.getText();
        String productID = label_ofProductID.getText();
        int quantity = spinner.getValue();
        
        //kiem tra 
        if(orderid.isEmpty()){
             AlertMaker.showErrorMessage("Error", "Please make orders first");
        }
        else{
        if(CheckProduct(orderid, productID)){
            int q = 0;
            q = Integer.valueOf(label_ofQ.getText());
        
            int i = OrderDetailsDAO.update(orderid, productID, quantity + q );
        
            if (i == 1)
            {
                
                AlertMaker.showSimpleAlert("Add", "Succesfully!");
                setCell();
                LoadData(label_ofOrderID.getText());
                
            }
        }
        else
        {
            String Quantity = String.valueOf(quantity + 1);
            if (Quantity.isEmpty())
            {
                AlertMaker.showErrorMessage("Error", "Please fills in quantity text field");
            }
        
            int i = OrderDetailsDAO.insert(orderid, productID, quantity);
        
            if (i == 1)
            {
                
                AlertMaker.showSimpleAlert("Add", "Succesfully!");
                setCell();
                LoadData(label_ofOrderID.getText());
                
            }
        }
        }
    } 
    
    private void setCell()
    {
        tbCol_OrderID.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        tbCol_ProductID.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        tbCol_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tbCol_detailPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }
    private void bindingOrderDetails()
    {
        tbv_Orders.setOnMouseClicked((MouseEvent e) -> 
        {
            OrderDetails od = tbv_Orders.getItems().get(tbv_Orders.getSelectionModel().getSelectedIndex());
            label_ofpID.setText(od.getProduct_id());
            label_ofQ.setText(String.valueOf(od.getQuantity()));
            label_ofPr.setText(String.format("%,.0f", od.getPrice()));
        });
    }
    private void LoadData(String orderid) throws SQLException
    {
        data2.clear();
        
        try 
        {
            String query = "SELECT od.order_id, od.product_id, od.quan , od.quan * p.price as 'Price'"
                    + "FROM Product p inner join OrderDetails od on p.product_id = od.product_id "
                    + "WHERE order_id = ?";
                            
            pst = conn.prepareStatement(query);
            pst.setString(1, orderid);
            rs = pst.executeQuery();
            

            
            while(rs.next())
            {
                data2.add(new OrderDetails(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getFloat(4)));
            }
            tbv_Orders.setItems(data2);
            lb_TotalPrice.setText(String.valueOf(TotalPrice()));
           
        }
        catch(SQLException ex)
        {
             AlertMaker.showSimpleAlert("Error", "Fail loader!");
        }
        finally 
        {
            pst.close();
        }
        
        
        
    }
    
    public float TotalPrice(){
       float total = 0 ;
       OrderDetails or = new OrderDetails();
       List <List<String>> arrList = new ArrayList();
       for (int i = 0; i < tbv_Orders.getItems().size(); i++)
       {
           or = tbv_Orders.getItems().get(i);
           total= total + or.getPrice();
       }
       return total;
    }
    
    
    @FXML
    private void _Edit(ActionEvent event) {
        
        String order_id = label_ofOrderID.getText();

            
        //kiem tra 
        if(order_id.isEmpty()){
             AlertMaker.showErrorMessage("Error", "Please make orders first");
        }
 
        else{
            String product_id = label_ofpID.getText();
            int quantity = Integer.parseInt(label_ofQ.getText());
            float price = Float.valueOf(label_ofPr.getText());
            
            String query = "Update OrderDetails "
                    + "Set quan = ? "
                    + "where order_id = ? and product_id = ? ";
            
            try
            {
                pst = conn.prepareStatement(query);
                pst.setInt(1, spinner.getValue());
                pst.setString(2, order_id);
                pst.setString(3, product_id);
                int i = pst.executeUpdate();
                if (i == 1)
                {
                    AlertMaker.showSimpleAlert("Update", "Succesfully!");
                    LoadData(label_ofOrderID.getText());
                    Clearlabel();
                }
            }
            catch(SQLException ex)
            {
                 AlertMaker.showSimpleAlert("Error", "Fail update!");
            }
        }
        
    }

    @FXML
    private void _Delete(ActionEvent event) {
        String order_id = label_ofOrderID.getText();

        
           //kiem tra 
        if(order_id.isEmpty()){
             AlertMaker.showErrorMessage("Error", "Please make orders first");
        }
        else{
            String product_id = label_ofpID.getText();
            int quantity = Integer.parseInt(label_ofQ.getText());
            float price = Float.valueOf(label_ofPr.getText());
            String query = "Delete from OrderDetails "
                + "where order_id = ? and product_id = ? and quan = ? ";
        try
        {
            pst = conn.prepareStatement(query);
            pst.setString(1, order_id);
            pst.setString(2, product_id);
            pst.setInt(3, quantity);
            int i = pst.executeUpdate();
            if (i == 1)
            {
                AlertMaker.showSimpleAlert("Delete", "Successfully!");
                LoadData(label_ofOrderID.getText());
                Clearlabel();
            }
        }
        catch(SQLException ex)
        {
             AlertMaker.showSimpleAlert("Error", "Fail update!");
        }
        }
    }

    @FXML
    private void _TaoHoaDon(ActionEvent event) {
        
        LoadInfoOrder();
    }

    @FXML
    private void _Payment(ActionEvent event) {
        if(label_ofOrderID.getText().isEmpty())
        {
            AlertMaker.showErrorMessage("Warning", "Please make orders first");
        }
        else {
        Float totalprice = Float.valueOf(lb_TotalPrice.getText());
        Float customerpay = Float.valueOf(jtf_customerPay.getText());
            if (jtf_customerPay.toString().isEmpty())
            {
                AlertMaker.showErrorMessage("Warning", "Please fills in customerPay.");
            }
        
            Float payback = customerpay - totalprice;
            if(payback < 0 ){
                AlertMaker.showErrorMessage("Warning", "Please fills in customerPay.");
            }
            else{
                lb_payBack.setText(String.valueOf(payback));
                String orderid = label_ofOrderID.getText();
                OrdersDAO.Update(orderid, totalprice, customerpay, payback);
                lb_TotalPrice.setText("");
                jtf_customerPay.setText("");
                lb_payBack.setText("");
                Clearlabel();
                loadOrdersDetailFromDB();
            }
        }
        
        
    }

    private void Clearlabel() {
        label_ofpID.setText("");
        label_ofPr.setText("");
        label_ofQ.setText("");
        label_ofOrderID.setText("");
    }

    @FXML
    private void _Exit(ActionEvent event) {
         try
        {
            Stage stage = (Stage) btn_Settings.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Login.fxml"));
            Parent root = (Parent) loader.load();
 
            
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();   
            
        }
        catch(IOException ex)
        {
        }
    }

    @FXML
    private void _Search(ActionEvent event) {
        
        int i = cbb_Category.getSelectionModel().getSelectedIndex();
        
        switch(i){
               
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                SearchProduct(i);
                break;
            case 5:
                LoadProductFromDB();
                break;
            default: 
                AlertMaker.showErrorMessage("Warning", "Please pick Category.");
                LoadProductFromDB();
                break;
             
                
        }
         
    }
    
    public void SearchProduct(int category){

      
        data.clear();
        String query = "Select* "
                     + "From Product p "
                     + "Where p.category_id = ?";
        try {
            pst = conn.prepareStatement(query);
                
            pst.setInt(1, category);
            rs = pst.executeQuery();
            
        while (rs.next())
        {
            data.add(new Product(rs.getString(1),rs.getString(2), +rs.getFloat(3),rs.getString(4)));
        }
        } catch (SQLException ex) {
            Logger.getLogger(ListProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tbv_Product.setItems(data);  
        }
    
    private void PriceVoice(){
        String sourceFile = "C:\\Users\\PC\\Documents\\GitHub\\Coffee-Shop\\src\\report\\report.jrxml";
        try{
            JasperReport jr = JasperCompileManager.compileReport(sourceFile);
            HashMap<String, Object>para = new HashMap<>();
            para.put("id", label_ofOrderID.getText());
            
            ArrayList<OrderDetails> plist = new ArrayList<>();
            for(OrderDetails od : data2){
                plist.add(new OrderDetails(od.getOrder_id(), od.getProduct_id(), od.getQuantity(), od.getPrice()));
            }
            JRBeanCollectionDataSource jcs = new JRBeanCollectionDataSource(plist);
            JasperPrint jp = JasperFillManager.fillReport(jr, para, jcs);
            JasperViewer.viewReport(jp);
            
        }catch(JRException ex)
        {
            Logger.getLogger(WorkSpaceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void _PayBack(KeyEvent event) {
        
        if(event.getCode() == KeyCode.ENTER){
           
             if(jtf_customerPay.getText().isEmpty())
        {
            AlertMaker alert = new AlertMaker();
            alert.showSimpleAlert(" WARNING","Please input Customer Pay");
            
        }else{
            Float totalprice = Float.valueOf(lb_TotalPrice.getText());
            Float customerpay = Float.valueOf(jtf_customerPay.getText());
            Float payback = customerpay - totalprice;
            //String.format("%,.2f", payback);
            lb_payBack.setText(String.format("%,.0f",payback) );}
           
        }
    }
    
    public boolean CheckProduct(String order_id, String product_id){
        String query = "Select * "
                     + "From OrderDetails p "
                     + "Where p.order_id = ? and p.product_id = ?";
        try {
            pst = conn.prepareStatement(query);
                
            pst.setString(1, order_id);
            pst.setString(2, product_id);
            rs = pst.executeQuery();
            
            if(rs.next() == false )
            {
                return false;
            }
            return true;
        }catch(SQLException ex){
            Logger.getLogger(ListProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
