
package views.Admin;

import beans.Employee;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Admin {
    /*SINGLETON*/
    private static Admin instance;
    public static Admin getInstance(){
        if(instance == null){
            instance = new Admin();
        }
        return instance;
    }
    /*---------------*/

    public Admin() {
    }
    
    /*VARIABLES*/
    public static Stage s;
    public void start(Stage stage) {
        s = stage;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {}
    }
    
    public Stage getStage(){
        return this.s;
    }
}
