
package views.Account;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class AccountController implements Initializable {

    @FXML
    private VBox vbox;
    
    @FXML
    private Parent fxml;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int a = (int) vbox.getLayoutX() * 31;
        transition("/views/Account/Login.fxml", (a));
    }    
    @FXML
    private void open_logIn(ActionEvent event) {
        int a = (int) vbox.getLayoutX() * 31;
        transition("/views/Account/Login.fxml", (a));
        
    }
    @FXML
    private void open_signIn(ActionEvent event) {
        transition("/views/Account/SignIn.fxml",30) ;
    }
    
    
    
    public void transition(String route, int position){
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
        t.setToX(position);
        t.play();
        t.setOnFinished((e) -> {
            try {
                fxml = (Parent)FXMLLoader.load(getClass().getResource(route));
                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);
            } catch (IOException ex) {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
    }    
    
    
}
