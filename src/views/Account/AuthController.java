/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.Account;

import beans.Client;
import beans.Employee;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import controllers.ClientsController;
import controllers.EmployeesController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import views.Admin.Admin;
import views.Client.ClientView;

/**
 * FXML Controller class
 *
 * @author Jose Morente
 */
public class AuthController implements Initializable {

    /*VARIABLES*/
    @FXML
    private TextField user, pass, name, dpi, user2, pass2;
    @FXML private Text text;
    @FXML private Text text1;   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    
        
    @FXML
    private void authenticate(ActionEvent event) {
        String[] data = {user.getText(), pass.getText()};
        Employee emp = EmployeesController.getInstance().authenticate(data);
        Client cln = ClientsController.getInstance().authenticate(user.getText(), pass.getText());
        
        if (emp != null && cln == null) {
            if (emp.getRole().equals("Administrador")) {
                Admin.getInstance().start(Account.s);
            }
        } else if(emp == null && cln != null){
            ClientView.getInstance().start(Account.s, cln);
        } else {
            text1.setText("Usuario o contraseña incorrectos.");
        }
    }
    
    @FXML
    private void getAccess(ActionEvent event) {
        if (!name.getText().isEmpty() && !dpi.getText().isEmpty() &&
             !user2.getText().isEmpty() && !pass2.getText().isEmpty()) {
            
            try{
                Long.parseLong(dpi.getText());
                if (ClientsController.getInstance().verifications(user2.getText())) {
                    text.setText("El nombre de usuario ya existe.");
                    } else {
                        ClientsController.getInstance().addAtEnd(dpi.getText(), name.getText(), user2.getText(), pass2.getText(), "Normal");
                        Client c = ClientsController.getInstance().searchForUserName(user2.getText());
                        if (c != null) {
                            ClientView.getInstance().start(Account.s, c);
                        } else {
                            text.setText("Usuario no encontrado.");
                        }
                    }
            } catch(Exception e){
                text.setText("El valor DPI solo puede ser numérico.");
            }
        } else {
            text.setText("No puedes dejar los campos en blanco.");
        }
    }
        
}
