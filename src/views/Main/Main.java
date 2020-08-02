package views.Main;

import controllers.EmployeesController;
import controllers.ServicesController;
import javafx.application.Application;
import javafx.stage.Stage;
import views.Account.Account;
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        
        EmployeesController.getInstance().initEmployee();
        ServicesController.getInstance().initServices();
        
        Account.getInstance().start(stage);
        //Admin.getInstance().start(stage);
        //ClientView.getInstance().start(stage, ClientsController.getInstance().searchClient(1));
    }

    public static void main(String[] args) {
        
        launch(args);
    }
    
    
}
