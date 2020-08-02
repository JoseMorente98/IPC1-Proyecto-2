/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.Client;

import beans.Client;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Jose Morente
 */
public class DashboardViewController implements Initializable {

    @FXML Text name;
    Client client;

    public DashboardViewController() {
        client = ClientView.client;
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setText(client.getFullName());
    }    
    
}
