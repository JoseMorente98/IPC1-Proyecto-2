/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.Admin.WorkOrder;

import beans.Employee;
import beans.WorkOrder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import controllers.EmployeesController;
import controllers.TDAQueueCarsInProcess;
import controllers.TDAQueueCarsWaiting;
import controllers.WorkOrderController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class WorkOrderViewController implements Initializable {

    
    /*VARIABLES*/
    private ObservableList<String> employees;
    @FXML ComboBox list;
    @FXML TextField clientName, carD, serviceName;
    @FXML TableView<WorkOrder> tableView;
    @FXML TableColumn<WorkOrder, Integer> id;
    @FXML TableColumn<WorkOrder, String> client;
    @FXML TableColumn<WorkOrder, String> car;
    @FXML TableColumn<WorkOrder, String> service;
    @FXML TableColumn<WorkOrder, String> state;
    @FXML TableColumn<WorkOrder, String> date;
    @FXML ImageView imageView;
    @FXML StackPane stackPane;
    WorkOrder workOrder;
    Image image = new Image("resources/img/Automobile.png");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        initMechanics();
        initTableView();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        client.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        car.setCellValueFactory(new PropertyValueFactory<>("carDetails"));
        service.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        state.setCellValueFactory(new PropertyValueFactory<>("state"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        
    }    
    
    /*INICIALIZAR TABLA*/
    public void initTableView() {
        try {
            ObservableList<WorkOrder> observableList = WorkOrderController.getInstance().getOrders();
            tableView.setItems(observableList);
        } catch (Exception e) {

        }
    }
    
    public void initMechanics(){
        list.setItems(EmployeesController.getInstance().getMechanics());
    }
    
    @FXML
    public void getOrderWork(){
        
          WorkOrder wo =tableView.getSelectionModel().getSelectedItem();
        
        if (wo != null) {
            if (wo.getState().equalsIgnoreCase("SIN ATENDER")) {
                workOrder = tableView.getSelectionModel().getSelectedItem();
                clientName.setText(workOrder.getClientName());
                carD.setText(workOrder.getCarDetails());
                serviceName.setText(workOrder.getServiceName());
                imageView.setImage( new Image(workOrder.getImagePath()));
            } else {
                getAlert("Esta orden de trabajo ya estaba autorizada.");
            }
        }else {
            getAlert("Ningún item ha sido seleccionado.");
        }
    }
    
    
    @FXML 
    public void sendToService(){
        
        String texto = "";
        
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            
            
        if (list.getSelectionModel().getSelectedItem() != null  ) {
            
            
            if (!list.getSelectionModel().getSelectedItem().equals("Lista de Espera")) {
                
                Employee e = EmployeesController.getInstance().searchForName(list.getSelectionModel().getSelectedItem().toString());
                EmployeesController.getInstance().edit(e.getId(), e.getName(), e.getRole(), e.getUsername(), e.getPassword(), false);
                workOrder = tableView.getSelectionModel().getSelectedItem();
                workOrder.setEmployee(e);
                workOrder.setMechaic(e.getName());
                workOrder.setState("EN SERVICIO");
                
                WorkOrder selected = workOrder;
                //Crea una nueva orden de trabajo, a partir de la enviada, esto es por que
                //si se envía la que se seleccionó, esta al ser una lista trae con siguo siguientes
                //y se termian enviando los siguientes tambien.
                WorkOrder te = new WorkOrder(selected.getId(), selected.getCar(), 
                        selected.getClient(), selected.getEmployee(), selected.getService(), selected.getDate(), selected.getState(), selected.getPriority());
                //ENVÍA LAS ORDENES DE TRABAJO A LA LISTA DE CARROS EN ATENCION 
                TDAQueueCarsInProcess.getInstance().push(te);

                
                
                
                
            } else {
                
                
                workOrder.setMechaic("----");
                workOrder.setState("EN ESPERA");
                
                WorkOrder selected = workOrder;
                //Crea una nueva orden de trabajo, a partir de la enviada, esto es por que
                //si se envía la que se seleccionó, esta al ser una lista trae con siguo siguientes
                //y se termian enviando los siguientes tambien.
                WorkOrder te = new WorkOrder(selected.getId(), selected.getCar(), selected.getClient(), 
                        selected.getEmployee(), selected.getService(), selected.getDate(), selected.getState(), selected.getPriority());


                //ENVÍA LAS ORDENES DE TRABAJO A LA LISTA DE CARROS EN ESPERA 
                TDAQueueCarsWaiting.getInstance().push(te);

                
                
                
            }
            initMechanics();
            initTableView();
            getAlert("El auto del cliente está siendo reparado.");
            clear();
        }
            
        } else {
            getAlert("Ningún artículo ha sido seleccionado");
        }
        
        
    }
    
    public void clear(){
        list.getSelectionModel().clearSelection();
        clientName.setText("");
        carD.setText("");
        serviceName.setText("");
        imageView.setImage(image);
    }
    
    
     /*MUESTRA UNA ALERTA CUANDO UN ELEMNTO NO SE HA SELECCIONADO*/
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

