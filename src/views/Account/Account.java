/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.Account;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jose Morente
 */
public class Account extends Application {

    /*Variables*/
    public static Stage s;
    /*SINGLETON*/
    private static Account instance;
    public static Account getInstance(){
        if(instance == null){
            instance = new Account();
        }
        return instance;
    }
    /*---------------*/

    public Account() {
    }
    
    
    
    @Override
    public void start(Stage stage) throws Exception {
        s = stage;
        Parent root = FXMLLoader.load(getClass().getResource("Account.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

}
