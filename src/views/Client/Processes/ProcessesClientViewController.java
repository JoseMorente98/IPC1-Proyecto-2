/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.Client.Processes;

import beans.Client;
import beans.WorkOrder;
import controllers.TDAQueueCarsFinished;
import controllers.TDAQueueCarsInProcess;
import controllers.TDAQueueCarsWaiting;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import views.Client.ClientView;


public class ProcessesClientViewController implements Initializable {

    //CARS EN SERVICIO
    @FXML TableView<WorkOrder> tableView;
    @FXML TableColumn<WorkOrder, String> car;
    @FXML TableColumn<WorkOrder, String> service;
    @FXML TableColumn<WorkOrder, String> mechanic;
    @FXML TableColumn<WorkOrder, String> workPrice;
    @FXML TableColumn<WorkOrder, String> spPrice;
    @FXML TableColumn<WorkOrder, String> total;
    
    //CARS WAITING   
    @FXML TableView<WorkOrder> tableViewW;
    @FXML TableColumn<WorkOrder, String> carW;
    @FXML TableColumn<WorkOrder, String> serviceW;
    @FXML TableColumn<WorkOrder, String> mechanicW;
    @FXML TableColumn<WorkOrder, String> workPriceW;
    @FXML TableColumn<WorkOrder, String> spPriceW;
    @FXML TableColumn<WorkOrder, String> totalW;
    @FXML TableColumn<WorkOrder, String> dateW;
    
    
    //CARS FINISHED
    @FXML TableView<WorkOrder> tableViewFinished;
    @FXML TableColumn<WorkOrder, String> carFinished;
    @FXML TableColumn<WorkOrder, String> serviceFinished;
    @FXML TableColumn<WorkOrder, String> mechanicFinished;
    @FXML TableColumn<WorkOrder, String> workPriceFinished;
    @FXML TableColumn<WorkOrder, String> spPriceFinished;
    @FXML TableColumn<WorkOrder, String> totalFinished;
    @FXML TableColumn<WorkOrder, String> dateFinished;
    @FXML TableColumn<WorkOrder, String> stateFinished;
    
    @FXML ImageView imageView;
    
    /*VARIABLES*/
    Client client;

    public ProcessesClientViewController() {
        client = ClientView.client;
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        initTableViewCarsInProcess();
        initTableViewCarsFinished();
        initTableViewCarsWaiting();
        //CARS IN PROCESS
        car.setCellValueFactory(new PropertyValueFactory<>("carDetails"));
        service.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        mechanic.setCellValueFactory(new PropertyValueFactory<>("mechaic"));
        workPrice.setCellValueFactory(new PropertyValueFactory<>("workPrice"));
        spPrice.setCellValueFactory(new PropertyValueFactory<>("spPrice"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));
        
        ////CARS WAITING
        carW.setCellValueFactory(new PropertyValueFactory<>("carDetails"));
        serviceW.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        mechanicW.setCellValueFactory(new PropertyValueFactory<>("mechaic"));
        workPriceW.setCellValueFactory(new PropertyValueFactory<>("workPrice"));
        spPriceW.setCellValueFactory(new PropertyValueFactory<>("spPrice"));
        totalW.setCellValueFactory(new PropertyValueFactory<>("total"));
        dateW.setCellValueFactory(new PropertyValueFactory<>("date"));
        
    
        //CARS FINISHED
        carFinished.setCellValueFactory(new PropertyValueFactory<>("carDetails"));
        serviceFinished.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        mechanicFinished.setCellValueFactory(new PropertyValueFactory<>("mechaic"));
        workPriceFinished.setCellValueFactory(new PropertyValueFactory<>("workPrice"));
        spPriceFinished.setCellValueFactory(new PropertyValueFactory<>("spPrice"));
        totalFinished.setCellValueFactory(new PropertyValueFactory<>("total"));
        dateFinished.setCellValueFactory(new PropertyValueFactory<>("date"));
        stateFinished.setCellValueFactory(new PropertyValueFactory<>("state"));
    }   
    
    public void initTableViewCarsInProcess(){
        
        WorkOrder wo = TDAQueueCarsInProcess.getInstance().getTDAQueue();
        
        ObservableList<WorkOrder> carsInProcess = FXCollections.observableArrayList();

        while (wo != null) {
            if (wo.getClient().getId() == client.getId()) {
                carsInProcess.add(wo);
            }
            wo = wo.getNext();
        }

        tableView.setItems(carsInProcess);


 
    }
    
    
    
    //init table view cars finished
    public void initTableViewCarsFinished(){
        WorkOrder wol = TDAQueueCarsFinished.getInstance().getTDAQueue();
        ObservableList<WorkOrder> carsFinished = FXCollections.observableArrayList();
        while (wol != null) {
            if (wol.getClient().getId() == client.getId()) {
                carsFinished.add(wol);
            }
            wol = wol.getNext();
        }
        tableViewFinished.setItems(carsFinished);
    }
    
    
        //init table view cars finished
    public void initTableViewCarsWaiting(){
        WorkOrder wol = TDAQueueCarsWaiting.getInstance().getTDAQueue();
        ObservableList<WorkOrder> carsWaiting = FXCollections.observableArrayList();
        while (wol != null) {
            if (wol.getClient().getId() == client.getId()) {
                carsWaiting.add(wol);
            }
            wol = wol.getNext();
        }
        tableViewW.setItems(carsWaiting);
    } 
}
