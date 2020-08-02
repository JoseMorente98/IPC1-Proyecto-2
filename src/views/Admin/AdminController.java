package views.Admin;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import views.Account.Account;
import views.Account.AccountController;


public class AdminController implements Initializable {

    /*SINGLETON*/
    private static AdminController instance;
    public static AdminController getInstance(){
        if(instance == null){
            instance = new AdminController();
        }
        return instance;
    }
    /*---------------*/
    
    
    @FXML 
    private VBox vbox;
    @FXML
    private Parent fxml;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        changeView("/views/Admin/Home.fxml");
    }    
    @FXML
    public void open_employee(ActionEvent event) {
      changeView("/views/Admin/Employee/EmployeeView.fxml");
        
    }
    @FXML
    private void open_home(ActionEvent event) {
        changeView("/views/Admin/Home.fxml");
        
    }
    @FXML
    private void open_services(ActionEvent event) {
        changeView("/views/Admin/Service/ServicesView.fxml");
        
    }
    @FXML
    private void open_parts(ActionEvent event) {
        changeView("/views/Admin/SpareParts/SparePartsView.fxml");
        
    }
    @FXML
    private void open_cars(ActionEvent event) {
        changeView("/views/Admin/Car/FXMLCarView.fxml");
    }
    @FXML
    private void open_clients(ActionEvent event) {
        changeView("/views/Admin/Client/FXMLClientView.fxml");
    }
    @FXML
    private void open_workOrder(ActionEvent event) {
        changeView("/views/Admin/WorkOrder/WorkOrderView.fxml");
    }
    @FXML
    private void open_processesView(ActionEvent event) {
        changeView("/views/Admin/Processes/ProcessesView.fxml");
    }
    @FXML
    private void open_reports(ActionEvent event) {
        changeView("/views/Admin/Report/ReportView.fxml");
    }
    @FXML 
    public void logOut( ActionEvent event ) throws Exception {
        Account.getInstance().start(Admin.s);
    }
    public void changeView(String component){
        try {
            fxml = (Parent) FXMLLoader.load(getClass().getResource(component));
            vbox.getChildren().removeAll();
            vbox.getChildren().setAll(fxml);
        } catch (IOException ex) {
            System.out.println(ex);
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void employee() {
      changeView("/views/Admin/Employee/EmployeeView.fxml");
        
    }
}