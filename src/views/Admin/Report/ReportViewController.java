/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.Admin.Report;

import controllers.ReportHTMLController;
import controllers.ReportPDFController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Jose Morente
 */
public class ReportViewController implements Initializable {
    @FXML 
    private VBox vbox;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void report_client() {
        ReportHTMLController.getInstance().getReportClient();
    }
    
    @FXML
    private void report_spare() {
        ReportHTMLController.getInstance().getTopSpareParts();
    }
    
    @FXML
    private void report_service() {
        ReportPDFController.getInstance().getTopService();
    }
    
    @FXML
    private void report_spareParts() {
        ReportPDFController.getInstance().getTopSpareParts();
    }
    
    @FXML
    private void report_cars() {
        ReportHTMLController.getInstance().getTop5Cars();
    }
}
