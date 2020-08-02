/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.Admin.Processes;

import beans.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import controllers.BillController;
import controllers.ClientsController;
import controllers.EmployeesController;
import controllers.ServicesController;
import controllers.TDAQueueCarsFinished;
import controllers.TDAQueueCarsInProcess;
import controllers.TDAQueueCarsWaiting;
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

/**
 * FXML Controller class
 *
 * @author Jose Morente
 */
public class ProcessesViewController implements Initializable {
    
    @FXML TableView<WorkOrder> tableView;
    @FXML TableColumn<WorkOrder, String> client;
    @FXML TableColumn<WorkOrder, String> car;
    @FXML TableColumn<WorkOrder, String> service;
    @FXML TableColumn<WorkOrder, String> mechanic;
    @FXML TableColumn<WorkOrder, String> workPrice;
    @FXML TableColumn<WorkOrder, String> spPrice;
    @FXML TableColumn<WorkOrder, String> total;
    @FXML TableColumn<WorkOrder, String> clientRole;
    
    //CARS WAITING
    
    @FXML TableView<WorkOrder> tableViewW;
    @FXML TableColumn<WorkOrder, String> clientW;
    @FXML TableColumn<WorkOrder, String> carW;
    @FXML TableColumn<WorkOrder, String> serviceW;
    @FXML TableColumn<WorkOrder, String> mechanicW;
    @FXML TableColumn<WorkOrder, String> workPriceW;
    @FXML TableColumn<WorkOrder, String> spPriceW;
    @FXML TableColumn<WorkOrder, String> totalW;
    @FXML TableColumn<WorkOrder, String> dateW;
    @FXML TableColumn<WorkOrder, String> roleW;
    
    
    ///CARS FINISHED
    @FXML TableView<WorkOrder> tableViewFinished;
    @FXML TableColumn<WorkOrder, String> clientFinished;
    @FXML TableColumn<WorkOrder, String> carFinished;
    @FXML TableColumn<WorkOrder, String> serviceFinished;
    @FXML TableColumn<WorkOrder, String> mechanicFinished;
    @FXML TableColumn<WorkOrder, String> workPriceFinished;
    @FXML TableColumn<WorkOrder, String> spPriceFinished;
    @FXML TableColumn<WorkOrder, String> totalFinished;
    @FXML TableColumn<WorkOrder, String> dateFinished;
    @FXML TableColumn<WorkOrder, String> roleFinished;
    @FXML TableColumn<WorkOrder, String> stateFinished;
    
    
    @FXML TextField eClient, eCar, eService, eMechanic, eWP, eSP, eTotal;
    @FXML TextField wClient, wCar, wService;
    @FXML ImageView imageView;
    @FXML ImageView wImageView;
    @FXML StackPane stackPane;
    @FXML StackPane stackPane2;
    @FXML ComboBox list;
    Image image = new Image("resources/img/Automobile.png");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        initTableViewCarsInProcess();
        initTableViewCarsFinished();
        initTableViewCarsWaiting();
        initMechanicList();
        //CARS IN PROCESS
        client.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        car.setCellValueFactory(new PropertyValueFactory<>("carDetails"));
        service.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        mechanic.setCellValueFactory(new PropertyValueFactory<>("mechaic"));
        workPrice.setCellValueFactory(new PropertyValueFactory<>("workPrice"));
        spPrice.setCellValueFactory(new PropertyValueFactory<>("spPrice"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));
        clientRole.setCellValueFactory(new PropertyValueFactory<>("clientRole"));
        
        
        //CARS WAITING
        clientW.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        carW.setCellValueFactory(new PropertyValueFactory<>("carDetails"));
        serviceW.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        workPriceW.setCellValueFactory(new PropertyValueFactory<>("workPrice"));
        spPriceW.setCellValueFactory(new PropertyValueFactory<>("spPrice"));
        totalW.setCellValueFactory(new PropertyValueFactory<>("total"));
        dateW.setCellValueFactory(new PropertyValueFactory<>("date"));
        roleW.setCellValueFactory(new PropertyValueFactory<>("clientRole"));
        
        
        //CARS FINISHED
        clientFinished.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        carFinished.setCellValueFactory(new PropertyValueFactory<>("carDetails"));
        serviceFinished.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        mechanicFinished.setCellValueFactory(new PropertyValueFactory<>("mechaic"));
        workPriceFinished.setCellValueFactory(new PropertyValueFactory<>("workPrice"));
        spPriceFinished.setCellValueFactory(new PropertyValueFactory<>("spPrice"));
        totalFinished.setCellValueFactory(new PropertyValueFactory<>("total"));
        dateFinished.setCellValueFactory(new PropertyValueFactory<>("date"));
        roleFinished.setCellValueFactory(new PropertyValueFactory<>("clientRole"));
        stateFinished.setCellValueFactory(new PropertyValueFactory<>("state"));
        
    }   
    
    public void initTableViewCarsInProcess(){
        WorkOrder wo = TDAQueueCarsInProcess.getInstance().getTDAQueue();
        ObservableList<WorkOrder> carsInProcess = FXCollections.observableArrayList();

        while (wo != null) {
            carsInProcess.add(wo);
            wo = wo.getNext();
        }

        tableView.setItems(carsInProcess); 
    }
    
    ///INIT TABLE VIEW CARS WAITING
    public void initTableViewCarsWaiting(){
        initMechanicList();
        WorkOrder wo = TDAQueueCarsWaiting.getInstance().getTDAQueue();
        ObservableList<WorkOrder> carsWaiting = FXCollections.observableArrayList();

        while (wo != null) {
            carsWaiting.add(wo);
            wo = wo.getNext();
        }
        tableViewW.setItems(carsWaiting); 
    }
    
    //init table view cars finished
    public void initTableViewCarsFinished(){
        WorkOrder wol = TDAQueueCarsFinished.getInstance().getTDAQueue();
        ObservableList<WorkOrder> carsFinished = FXCollections.observableArrayList();
        while (wol != null) {
            carsFinished.add(wol);
            wol = wol.getNext();
        }
        tableViewFinished.setItems(carsFinished);
    }
    //llena las lista de los mecanicos
    public void initMechanicList(){
        list.setItems(EmployeesController.getInstance().getMechanics());
    }
    
    @FXML 
    public void getNextCliet(){
        WorkOrder wo = TDAQueueCarsInProcess.getInstance().getfirstNode();
        
        if (wo != null) {
            eClient.setText(wo.getClientName());
            eCar.setText(wo.getCarDetails());
            eService.setText(wo.getServiceName());
            eMechanic.setText(wo.getMechaic());
            eSP.setText(String.valueOf(wo.getSpPrice()));
            eWP.setText(String.valueOf(wo.getWorkPrice()));
            eTotal.setText(String.valueOf(wo.getTotal()));
            imageView.setImage(new Image(wo.getImagePath()));
        } else {
             getAlert("No hay órdenes de trabajo.", stackPane);
        }
    }
    
    //OBTIENE EL PRIMER ELEMENTO DE LA LISTA DE ESPERA
    @FXML 
    public void getNextWaitingOrder(){
        
        WorkOrder w = TDAQueueCarsWaiting.getInstance().getfirstNode();
            if (w != null) {
                wClient.setText(w.getClientName());
                wCar.setText(w.getCarDetails());
                wService.setText(w.getServiceName());
                wImageView.setImage(new Image(w.getImagePath()));
            } else {
                getAlert("No hay órdenes de trabajo.", stackPane2);
            }
    }
    //ENVIA LA ORDEN DE TRABAJO EN LISTA DE ESPERA A LISTA DE CARROS EN SERVICIO
    @FXML
    public void sendToService(){
        
        if (list.getSelectionModel().getSelectedItem() != null) {
            if (!list.getSelectionModel().getSelectedItem().toString().equalsIgnoreCase("Lista de Espera")) {
                WorkOrder wo = TDAQueueCarsWaiting.getInstance().getfirstNode();
                if (wo != null) {
                     Employee e= EmployeesController.getInstance().searchForName(list.getSelectionModel().getSelectedItem().toString());
                     
                    WorkOrder w = new WorkOrder(wo.getId(), wo.getCar(), wo.getClient(), e, wo.getService(),wo.getDate(), "EN SERVICIO", wo.getPriority());
                    TDAQueueCarsInProcess.getInstance().push(w);
                    TDAQueueCarsWaiting.getInstance().pop();
                    getAlert("El auto fue enviado a la lista de autos en servicio.", stackPane2);
                    initTableViewCarsInProcess();
                    initTableViewCarsWaiting();
                    initMechanicList();
                }else {
                    getAlert("Seleccione un mecánico para la orden de trabajo.", stackPane2);
                }
                
            } else {
                getAlert("Seleccione un mecánico para la orden de trabajo.", stackPane2);
            }
        }
    }
    
    
    
    @FXML
    public void endPocess(){
        //OBTIENE EL PRIMER ELEMENTO DE LA COLA
        WorkOrder w = TDAQueueCarsInProcess.getInstance().getfirstNode();
        if (w != null) {
        
        ServicesController.getInstance().edit(w.getService().getId(), w.getService().getName(), w.getService().getMark(), w.getService().getModel(), w.getService().getSparePartList(), w.getService().getWorkPrice(), w.getService().getSparePartsPrice(), true);
        
        //CREA UNA NUEVA ODEN DE TRABAJO CON ESE ELEMENTO PARA QUE NO SE ENVÍEN 
        //LOS POSTERIORES A ESE ELEMENTO, COMO ES UNA LISTA, SI SE ENVÍA SOLO ASÍ
        //TAMBIEN SE ENVÍAN LOS ELEMENTOS SIGUIENTES
        WorkOrder wEnd = new WorkOrder(w.getId(), w.getCar(), 
                        w.getClient(), w.getEmployee(), w.getService(), w.getDate(), "SIN PAGAR", w.getPriority());
        
        //HACE PUSH A LA LISTA DE CARROS TERMINADOS
        TDAQueueCarsFinished.getInstance().push(wEnd);
        
        
        //HACE POP A LA LISTA DE CARROS EN ATENCION
        TDAQueueCarsInProcess.getInstance().pop();
        
        //EDITA EL EMPLEADO PARA QUE VUELVA A ESTAR DISPONIBLE
        EmployeesController.getInstance().edit(w.getEmployee().getId(), w.getEmployee().getName(), w.getEmployee().getRole(), w.getEmployee().getUsername(), w.getEmployee().getPassword(), true);
        
        //ENVÍA UNA FACTURA AL CLIENTE
        BillController.getInstance().add(w.getId(), w.getClient(), w.getCarDetails(), w.getCar().getPath(), w.getServiceName(), w.getTotal(), w.getDate());
        
        Client c = w.getClient();
        ClientsController.getInstance().update(c.getId(), c.getDpi(), c.getFullName(), c.getUsername(), c.getPassword(), c.getRole(), 1);
        
        getAlert("El auto fue enviado a la lista de servicios completados.", stackPane);
        initTableViewCarsInProcess();
        initTableViewCarsFinished();
        initTableViewCarsWaiting();
        cancel();
        } else {
            getAlert("No hay órdenes de trabajo.", stackPane);
        }
    }
    
    
        /*MUESTRA UNA ALERTA CUANDO UN ELEMNTO NO SE HA SELECCIONADO*/
    public void getAlert(String content, StackPane pane) {

        JFXDialogLayout c = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(pane, c, JFXDialog.DialogTransition.CENTER);
        c.setHeading(new Text("Información"));
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
        
    @FXML
    public void cancel(){
        eClient.setText("");
        eCar.setText("");
        eService.setText("");
        eMechanic.setText("");
        eSP.setText("");
        eWP.setText("");
        eTotal.setText("");
        wClient.setText("");
        wCar.setText("");
        wService.setText("");
        imageView.setImage(image);
        wImageView.setImage(image);
    }
    
}
