/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.Client;

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

/**
 * FXML Controller class
 *
 * @author Jose Morente
 */
public class ClientViewController implements Initializable {

    
    @FXML 
    private VBox vbox;
    @FXML
    private Parent fxml;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        changeView("/views/Client/DashboardView.fxml");
    }  
    
    @FXML
    public void open_Dash(){
        changeView("/views/Client/DashboardView.fxml");
    
    }
    
    @FXML 
    public void add_myCars( ActionEvent event ) {
        changeView("/views/Client/MyCars/MyCars.fxml");
    }
    @FXML 
    public void send_toService( ActionEvent event ) {
        changeView("/views/Client/CarToService/SendCarToService.fxml");
    }
    
    @FXML 
    public void open_Processes( ActionEvent event ) {
        changeView("/views/Client/Processes/ProcessesClientView.fxml");
    }
    
    @FXML 
    public void open_bill( ActionEvent event ) {
        changeView("/views/Client/PayBill/PayBillView.fxml");
    }
    
    
    @FXML 
    public void logOut( ActionEvent event ) throws Exception {
        Account.getInstance().start(ClientView.getInstance().getStage());
        ClientView.client = null;
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
}
