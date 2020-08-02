/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.Client.CarToService;

import beans.Car;
import beans.Client;
import beans.Service;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import controllers.CarController;
import controllers.ServicesController;
import controllers.WorkOrderController;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import views.Client.ClientView;

public class SendCarToServiceController implements Initializable {

    private static SendCarToServiceController instance;
    @FXML
    TableView<Car> tableView;
    @FXML
    TableColumn<Car, String> tableColumnPlate;
    @FXML
    TableColumn<Car, String> tableColumnBrand;
    @FXML
    TableColumn<Car, String> tableColumnModel;
    @FXML
    TableColumn<Car, ImageView> tableColumnPath;

    @FXML
    TextField ePlate, eBrand, eModel, ePath, filter;
    @FXML
    Button aceptar, editar, eliminar, cancelar, subir;
    @FXML
    Text texto;
    @FXML
    ComboBox comboService;
    @FXML
    ImageView imageView;
    @FXML
    StackPane stackPane;

    /*VARIABLES */
    Image image = new Image("resources/img/Automobile.png");
    Client client;
    Car start = new Car();
    Car start2 = new Car();

    /**
     * @return SINGLETON
     *
     */
    public static SendCarToServiceController getInstance() {
        if (instance == null) {
            instance = new SendCarToServiceController();
        }
        return instance;
    }

    public SendCarToServiceController() {
        client = ClientView.client;
        start = client.getCarList();
        start2 = start;
        start = null;
        CarController.getInstance().setCarClient(start2);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTableView();
        ePlate.setEditable(false);
        eBrand.setEditable(false);
        eModel.setEditable(false);

        imageView.setImage(image);
        tableColumnPlate.setCellValueFactory(new PropertyValueFactory<>("plate"));
        tableColumnBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        tableColumnModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        tableColumnPath.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Car, ImageView>, ObservableValue<ImageView>>() {
            @Override
            public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Car, ImageView> param) {
                ImageView imageView = new ImageView(new Image(param.getValue().getPath()));
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(100);
                imageView.setFitWidth(150);
                return new SimpleObjectProperty<>(imageView);
            }
        });
    }

    /**
     * INICIALIZAR DATOS EN TABLA
     */
    public void initTableView() {
        ObservableList<Car> observableList = FXCollections.observableArrayList();
        if (client.getCarList() != null) {

            Car aux = client.getCarList();
            do {
                observableList.add(aux);
                aux = aux.getNext();

            } while (aux != client.getCarList());
        }
        tableView.setItems(observableList);
    }

    @FXML
    public void getCar() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            /*OBTIENE EL CARRO*/
            Car car = tableView.getSelectionModel().getSelectedItem();
            imageView.setImage(new Image(car.getPath()));
            ePlate.setText(car.getPlate());
            eBrand.setText(car.getBrand());
            eModel.setText(car.getModel());

            /*LLAMA LOS SERVICIOS DISPONIBLES*/
            comboService.setItems(ServicesController.getInstance().getServiceName(eModel.getText(), eBrand.getText()));
        }
    }

    @FXML
    public void sendMyCar() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            if (comboService.getSelectionModel().getSelectedItem() != null) {
                //METODO RANDOM
                if(comboService.getSelectionModel().getSelectedItem().toString().equalsIgnoreCase("Diagnostico")) {
                    Service service = ServicesController.getInstance().searchForName(comboService.getSelectionModel().getSelectedItem().toString());
                    Service serviceRandom = ServicesController.getInstance().getServiceRandom(eModel.getText(), eBrand.getText());
                    if(serviceRandom!=null) {
                        if(!serviceRandom.getName().equalsIgnoreCase("Diagnostico")) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Detalles");
                            alert.setHeaderText(service.getName());
                            alert.setContentText("¿Desea que se le realice un servicio de " + serviceRandom.getName() + "?");

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK){
                                WorkOrderController.getInstance().add(tableView.getSelectionModel().getSelectedItem(), client, serviceRandom, null, "SIN ATENDER");
                            } else {
                                cancel();
                            }
                        } else {
                            getAlert("Error de sistema intente nuevamente.", "Diagnostico");
                        }
                    } else {
                        getAlert("No se encontro un servicio adecuado para su vehículo.", "Detalles");
                        cancel();
                    }
                } else {
                    Service service = ServicesController.getInstance().searchForName(comboService.getSelectionModel().getSelectedItem().toString());
                    WorkOrderController.getInstance().add(tableView.getSelectionModel().getSelectedItem(), client, service, null, "SIN ATENDER");
                    getAlert("Orden de trabajo generada exitosamente.", "Detalles");
                    cancel();
                }
            } else {
                getAlert("Por favor, seleccione un servicio para su coche.", "Error!");
            }
        } else {
            getAlert("Por favor, seleccione el auto que desea enviar al servicio.", "Error!");
        }

    }

    @FXML
    public void cancel() {
        imageView.setImage(image);
        ePlate.setText("");
        eBrand.setText("");
        eModel.setText("");
        tableView.getSelectionModel().clearSelection();

    }

    /*MUESTRA UNA ALERTA CUANDO UN ELEMNTO NO SE HA SELECCIONADO*/
    public void getAlert(String content, String content2) {
        JFXDialogLayout c = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(stackPane, c, JFXDialog.DialogTransition.CENTER);
        c.setHeading(new Text(content2));
        c.setBody(new Text(content));
        JFXButton button = new JFXButton("Cerrar");
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
