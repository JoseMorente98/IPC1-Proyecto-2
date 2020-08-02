/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.Client.MyCars;

import beans.Car;
import beans.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import controllers.CarController;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import views.Client.ClientView;

public class MyCarsController implements Initializable {

    private static MyCarsController instance;
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
    ImageView imageView;
    @FXML
    StackPane stackPane;

    /*VARIABLES */
    Image image = new Image("resources/img/Automobile.png");
    Client client;
    Car latest;
    Car start;
    Car aux;
    int count = 1;
    ObservableList observableList;
    /**
     * @return the instance
     *
     */
    public static MyCarsController getInstance() {
        if (instance == null) {
            instance = new MyCarsController();
        }
        return instance;
    }

    public MyCarsController() {
        client = ClientView.client;
        start = client.getCarList();
        CarController.getInstance().setCarClient(start);
        observableList = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ePath = new TextField();
        editar.setVisible(false);
        cancelar.setVisible(false);
        texto.setText("Agregar Carro");
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
        imageView.setImage(image);
        initTableView();
    }

    /**
     * INICIALIZAR DATOS EN TABLA
     */
    public void initTableView() {
        observableList.clear();
        tableView.setItems(CarController.getInstance().getCars());
        client.setCarList(CarController.getInstance().returnCars());
    }

    /**
     * AGREGAR DATOS
     */
    @FXML
    private void add(ActionEvent event) {
        if (getValidations() == true) {
            CarController.getInstance().addAtEnd(ePlate.getText(), eBrand.getText(), eModel.getText(), ePath.getText());
            count++;
            initTableView();
            clearFields();
        }
    }

    /**
     * ACTUALIZAR DATOS
     */
    @FXML
    private void update(ActionEvent event) {
        if (getValidations() == true) {
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                int id = tableView.getSelectionModel().getSelectedItem().getId();
                CarController.getInstance().update(id, ePlate.getText(), eBrand.getText(), eModel.getText(), ePath.getText());
            }
            aceptar.setVisible(true);
            editar.setVisible(false);
            cancelar.setVisible(false);
            texto.setText("Agregar Carro");
            initTableView();
            clearFields();
        }
    }

    @FXML
    private void delete(ActionEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            int id = tableView.getSelectionModel().getSelectedItem().getId();
            CarController.getInstance().delete(id);
            initTableView();
        } else {
            getAlert("Ningun item a sido seleccionado.");
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        clearFields();
        initTableView();
        aceptar.setVisible(true);
        editar.setVisible(false);
        cancelar.setVisible(false);

        texto.setText("Agregar Carro");
    }

    /*Actualizar*/
    @FXML
    private void getCars(ActionEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Car c = tableView.getSelectionModel().getSelectedItem();
            aceptar.setVisible(false);
            editar.setVisible(true);
            cancelar.setVisible(true);

            texto.setText("Actualizar Carro");
            ePlate.setText(c.getPlate());
            eBrand.setText(c.getBrand());
            eModel.setText(c.getModel());
            ePath.setText(c.getPath());
            imageView.setImage(new Image(c.getPath()));

        } else {
            getAlert("Ningun item a sido seleccionado.");
        }
    }

    @FXML
    private void subir(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG imagenes", "*.JPG"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            String path = "file:///" + selectedFile.toString().replace("/", File.separator);
            ePath.setText(path);
            imageView.setImage(new Image(path));
        }
    }

    /*VALIDA SI YA EXISTE EL NOMBRE DE USUARIO O SI DEJA CAMPOS EN BLANCO*/
    public boolean getValidations() {
        if (!ePlate.getText().isEmpty() && !eBrand.getText().isEmpty()
                && !eModel.getText().isEmpty() && !ePath.getText().isEmpty()) {
            return true;
        }
        getAlert("No puedes dejar los campos en blanco.");
        return false;
    }

    public void getAlert(String content) {
        JFXDialogLayout c = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(stackPane, c, JFXDialog.DialogTransition.CENTER);
        c.setHeading(new Text("Error!"));
        c.setBody(new Text(content));
        JFXButton button = new JFXButton("Close");
        button.setOnAction((ActionEvent event) -> {
            dialog.close();
        });;
        c.setActions(button);
        dialog.show();
    }

    /**
     * LIMPIAR CAMPOS DE TEXTO
     */
    public void clearFields() {
        ePlate.clear();
        eBrand.clear();
        eModel.clear();
        ePath.clear();
        imageView.setImage(image);
    }

    
}
