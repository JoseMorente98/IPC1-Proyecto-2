/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.Client;

import beans.Client;
import controllers.ClientsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jose Morente
 */
public class ClientView {
    
    public static Client client;
    
     /*SINGLETON*/
    private static ClientView instance;
    public static ClientView getInstance(){
        if(instance == null){
            instance = new ClientView();
        }
        return instance;
    }
    /*---------------*/

    /*VARIABLES*/
    Stage stageView;
    
    public ClientView() {
    }
    

    public void start(Stage stage, Client cln) {
        
        stageView = stage;
        client = cln;
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ClientView.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {}
    }
    
    public Stage getStage(){
        return this.stageView;
    }
    
}
