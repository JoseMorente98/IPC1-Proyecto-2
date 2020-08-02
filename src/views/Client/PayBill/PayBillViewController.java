/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.Client.PayBill;

import beans.Bill;
import beans.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import controllers.BillController;
import controllers.TDAQueueCarsFinished;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import views.Client.ClientView;

public class PayBillViewController implements Initializable {

    @FXML TableView<Bill> tableView;
    @FXML TableColumn<Bill, String> id;
    @FXML TableColumn<Bill, String> car;
    @FXML TableColumn<Bill, String> service;
    @FXML TableColumn<Bill, String> date;   
    @FXML TableColumn<Bill, String> total;
    @FXML TableColumn<Bill, String> state;

    @FXML TextField eService, eDate, eTotal;
    @FXML Button aceptar, cancelar;
    @FXML ImageView imageView;
    @FXML StackPane stackPane;

    /*VARIABLES */
    Image image = new Image("resources/img/Automobile.png");
    Client client;

    public PayBillViewController() {
        client = ClientView.client;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        car.setCellValueFactory(new PropertyValueFactory<>("car"));
        service.setCellValueFactory(new PropertyValueFactory<>("service"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));
        state.setCellValueFactory(new PropertyValueFactory<>("state"));
        aceptar.setDisable(true);
        initTableView();
    }
    
    public void initTableView() {
        tableView.setItems(BillController.getInstance().getBills(client.getId()));
    }
    
    
    @FXML
    public void getBill(ActionEvent event){
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Bill bill = tableView.getSelectionModel().getSelectedItem();
            if (bill.getState().equalsIgnoreCase("SIN PAGAR")) {
                eService.setText(bill.getService());
                eDate.setText(String.valueOf(bill.getDate()));
                eTotal.setText(String.valueOf(bill.getTotal()));
                //imageView.setImage(new Image(bill.getPath()));
                aceptar.setDisable(false);
            } else {
                getAlert("That bill has already been paid.");
            }
            
        } else {
            getAlert("No invoice has been selected.");
        }
    }
 
    @FXML
    public void payBill(ActionEvent event){
        
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Bill b = tableView.getSelectionModel().getSelectedItem();
            TDAQueueCarsFinished.getInstance().editNodeWorkOrder("PAGADO", b.getIdWorkOrder());
            BillController.getInstance().editNodeBill("PAGADO", b.getId());
        } else {
            getAlert("No invoice has been selected.");
        }
        initTableView();
        
    }
    
    public void getAlert(String content) {

        JFXDialogLayout c = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(stackPane, c, JFXDialog.DialogTransition.CENTER);
        c.setHeading(new Text("Information"));
        c.setBody(new Text(content));
        JFXButton button = new JFXButton("Close");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });;
        c.setActions(button);

        dialog.show();
    }
}
