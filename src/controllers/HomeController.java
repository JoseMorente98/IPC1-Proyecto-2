/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import views.Admin.AdminController;


public class HomeController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    @FXML
    public void changeToEmployee(){
        AdminController.getInstance().open_employee(new ActionEvent());
    }
    
    @FXML
    private void open_employee() {
      AdminController.getInstance().changeView("/views/Admin/Employee/EmployeeView.fxml");
        
    }
    @FXML
    private void open_home() {
        AdminController.getInstance().changeView("/views/Admin/Home.fxml");
        
    }
    @FXML
    private void open_services() {
        AdminController.getInstance().changeView("/views/Admin/Service/ServicesView.fxml");
        
    }
    @FXML
    private void open_parts() {
        AdminController.getInstance().changeView("/views/Admin/SpareParts/SparePartsView.fxml");
    }
    @FXML
    private void open_cars() {
        AdminController.getInstance().changeView("/views/Admin/Car/FXMLCarView.fxml");
    }
    @FXML
    private void open_clients() {
        AdminController.getInstance().changeView("/views/Admin/Client/FXMLClientView.fxml");
    }
    @FXML
    private void open_workOrder() {
        AdminController.getInstance().changeView("/views/Admin/WorkOrder/WorkOrderView.fxml");
    }
    @FXML
    private void open_processesView() {
        AdminController.getInstance().changeView("/views/Admin/Processes/ProcessesView.fxml");
    }
    @FXML
    private void open_reports() {
        AdminController.getInstance().changeView("/views/Admin/Report/ReportView.fxml");
    }
    
    
}
