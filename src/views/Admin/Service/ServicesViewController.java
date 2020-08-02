package views.Admin.Service;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import beans.Service;
import beans.SpareParts;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import controllers.InterpreterController;
import controllers.ServicesController;
import java.io.IOException;
import java.util.Stack;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ServicesViewController implements Initializable {

    /*SINGLETON*/
    private static ServicesViewController instance;

    public static ServicesViewController getInstance() {
        if (instance == null) {
            instance = new ServicesViewController();
        }
        return instance;
    }
    /*---------------*/

    /*VARIABLES*/
    @FXML
    TableView<Service> tableView;
    @FXML
    TableColumn<Service, Integer> id;
    @FXML
    TableColumn<Service, String> name;
    @FXML
    TableColumn<Service, String> mark;
    @FXML
    TableColumn<Service, String> model;
    @FXML
    TableColumn<Service, String> workPrice;
    @FXML
    TableColumn<Service, String> spPrice;
    @FXML
    StackPane stackPane;

    @FXML
    Button cancelar, editar;
    @FXML
    TextField sName, sMark, sModel, sPrice, filter;
    public static Service s;
    private SortedList<Service> sortedData;
    private FilteredList<Service> filteredData;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTableView();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        mark.setCellValueFactory(new PropertyValueFactory<>("mark"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        workPrice.setCellValueFactory(new PropertyValueFactory<>("workPrice"));
        spPrice.setCellValueFactory(new PropertyValueFactory<>("sparePartsPrice"));
        //BUSCAR EN TABLA
        filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(data -> {
                // Si el texto esta vacio muestra todo
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                // Comparar con cada tipo de filtro
                String lowerCaseFilter = newValue.toLowerCase();
                
                if (data.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (data.getModel().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } else if (data.getMark().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false; // Does not match.
            });
        });
        sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }

    /*INICIALIZAR TABLA*/
    public void initTableView() {
        try {
            ObservableList<Service> observableList = ServicesController.getInstance().getServices();
            filteredData = new FilteredList<>(observableList, p -> true);
            tableView.setItems(observableList);
            sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedData);
        } catch (NullPointerException e) {
            System.out.println(e.getCause());
        }
    }

    @FXML

    /*ELIMINA UN SERVICIO*/
    private void delete_Service(ActionEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            
            if (tableView.getSelectionModel().getSelectedItem().getId() != 1) {
                ServicesController.getInstance().delete(tableView.getSelectionModel().getSelectedItem().getId());
                tableView.getSelectionModel().clearSelection();
                initTableView();
            } else {
                getAlert("Tu no puedes eliminar este servicio.");
            }
       
        } else {
            getAlert("Ningun item a sido seleccionado.");
        }
    }

    /*AGREGA UN SERVICIO*/
    @FXML
    private void add_Service() {
        if (getValidations()) {
            ServicesController.getInstance().add(sName.getText(), sMark.getText(), sModel.getText(),new Stack() ,  Double.parseDouble(sPrice.getText()), 0.0, false);
            initTableView();
            clearFields();
        }
    }

    @FXML
    private void bulkLoad(ActionEvent event) {
        InterpreterController.getInstance().openSelecFile("*.ser", "SER Files");
       
    }
    @FXML
    private void open_Dialog(ActionEvent event) throws IOException {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            if (tableView.getSelectionModel().getSelectedItem().getId() != 1) {
                ServicesController.getInstance().change(tableView.getSelectionModel().getSelectedItem().getId());
            JFXDialogLayout content = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            try {
                Parent stack = (Parent) FXMLLoader.load(getClass().getResource("/views/Admin/Service/ViewInsertService.fxml"));;
                content.setBody(stack);

            } catch (Exception e) {
            }

            JFXButton button = new JFXButton("Close");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialog.close();
                }
            });;
            content.setActions(button);
            dialog.show();
            } else {
                getAlert("Tu no puedes editar este servicio");
            }
        } else {
            getAlert("Ningun item a sido seleccionado.");
        }
    }

    /*VALIDA QUE LOS CAMPOS NO SEAN VACIOS*/
    public boolean getValidations() {
        if (!sName.getText().isEmpty() && !sMark.getText().isEmpty()
                && !sModel.getText().isEmpty() && !sPrice.getText().isEmpty()) {

            try {
                Double.parseDouble(sPrice.getText());
                return true;
            } catch (NumberFormatException e) {
                getAlert("No puede ingresar texto en campos numéricos.");
                return false;
            }

        } else {
            getAlert("No puedes dejar los campos en blanco.");
            return false;

        }
    }
    
    
    
    /*MUESTRA UNA ALERTA CUANDO UN ELEMNTO NO SE HA SELECCIONADO*/
    public void getAlert(String content) {
        JFXDialogLayout c = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(stackPane, c, JFXDialog.DialogTransition.CENTER);
        c.setHeading(new Text("Error!"));
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
    
    public void clearFields(){
       sName.clear();
       sMark.clear();
       sModel.clear();
       sPrice.clear();
    }
}