/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.Admin.Employee;


import controllers.EmployeesController;
import controllers.InterpreterController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import beans.Employee;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.BufferedReader;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;

public class EmployeeViewController implements Initializable {

    
     /*SINGLETON*/
    private static EmployeeViewController instance;
    public static EmployeeViewController getInstance(){
        if(instance == null){
            instance = new EmployeeViewController();
        }
        return instance;
    }
    /*---------------*/

    
    /*VARIABLES*/
    private ObservableList<String> roles;
    @FXML TableView<Employee> tableView;
    @FXML TableColumn<Employee, Integer> id;
    @FXML TableColumn<Employee, String> name;
    @FXML TableColumn<Employee, String> username;
    @FXML TableColumn<Employee, String> password;
    @FXML TableColumn<Employee, String> role;
    
    
    @FXML ComboBox combo;
    @FXML TextField eName, eUsername, ePassword, filter;
    @FXML Button aceptar, editar, eliminar, cancelar;
    @FXML Text texto;
    @FXML StackPane stackPane;
    private SortedList<Employee> sortedData;
    private FilteredList<Employee> filteredData;
    public int count = 1;
    private File file;
    private InputStream inputStream;
    private BufferedReader bufferedReader;
    
    /*CONSTRUCTOR*/
    public EmployeeViewController() {
        
    }
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        editar.setVisible(false);
        cancelar.setVisible(false);
        texto.setText("Agregar Empleado");
        String[] array = {"Administrador", "Mecanico", "Cobrador"};
        roles = FXCollections.observableArrayList(array);
        combo.setItems(roles);
        
        
        initTableView();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
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
                } else if (data.getUsername().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } else if (data.getRole().toLowerCase().contains(lowerCaseFilter)) {
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
        try{
            ObservableList<Employee> observableList = EmployeesController.getInstance().getEmployees();
            filteredData = new FilteredList<>(observableList, p -> true);
            tableView.setItems(observableList);
            sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedData);
        } catch(Exception e){
            
        }
    }
    
    /*METODO AGREGAR DEL BOTON*/
    @FXML
    private void add_Employee(ActionEvent event) {
        if (getValidations() == true) {
            EmployeesController.getInstance().addLast(eName.getText(), combo.getSelectionModel().getSelectedItem().toString(), eUsername.getText(), ePassword.getText());
            eName.clear();
            combo.getSelectionModel().clearSelection();
            ePassword.clear();
            eUsername.clear();
            initTableView();
        }
    }
    /*METODO EDITAR DEL BOTON*/
    @FXML
    private void update_Employee(ActionEvent event) {
        if (getValidations() == true) {
            EmployeesController.getInstance().edit(tableView.getSelectionModel().getSelectedItem().getId(),  
                    eName.getText(), combo.getSelectionModel().getSelectedItem().toString(), eUsername.getText(), ePassword.getText(), true);
            eName.clear();
            ePassword.clear();
            eUsername.clear();
            combo.getSelectionModel().clearSelection();
            initTableView();
            aceptar.setVisible(true);
            editar.setVisible(false);
            cancelar.setVisible(false);
            texto.setText("Add a new Employee");
        }
    }
    
   /*METODO ELIMINAR*/         
   @FXML
   private void delete_Employee(ActionEvent event) {
       if (tableView.getSelectionModel().getSelectedItem() != null) {
            EmployeesController.getInstance().delete(tableView.getSelectionModel().getSelectedItem().getId());
            tableView.getSelectionModel().clearSelection();
            initTableView();
        } else {
           getAlert(" Ningún item ha sido seleccionado.");
       }
    } 
    
   /*LLENA LOS CAMPOS CON EL OBJETO SELECCIONADO*/
    @FXML
    private void update(ActionEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Employee e = EmployeesController.getInstance().search(tableView.getSelectionModel().getSelectedItem().getId());
            if (e != null) {
                aceptar.setVisible(false);
                editar.setVisible(true);
                cancelar.setVisible(true);
                texto.setText("Edit a user");

                eName.setText(e.getName());
                eUsername.setText(e.getUsername());
                ePassword.setText(e.getPassword());
                
            }
        } else {
           getAlert(" Ningún item ha sido seleccionado.");
       }
    }
    
    @FXML
    private void cancel(ActionEvent event) {
        eName.clear();
        ePassword.clear();
        eUsername.clear();
        combo.getSelectionModel().clearSelection();
        initTableView();
        aceptar.setVisible(true);
        editar.setVisible(false);
        cancelar.setVisible(false);
        texto.setText("Add a new Employee");
    }
    @FXML
    private void bulkLoad(ActionEvent event) {
        InterpreterController.getInstance().openSelecFile("*.emp", "EMP Files");
    }
    public BufferedReader read(String nombre) {
	try {
            file = new File(nombre);
            inputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	} catch (IOException ex) {
			
	}
	return bufferedReader;
    }
    
    
    /*VALIDA SI YA EXISTE EL NOMBRE DE USUARIO O SI DEJA CAMPOS EN BLANCO*/
    public boolean getValidations(){
        if(!eName.getText().isEmpty() && !eUsername.getText().isEmpty() &&
                 !ePassword.getText().isEmpty() && combo.getSelectionModel().getSelectedItem() != null){
            String[] data = {eUsername.getText()};
             Employee e = EmployeesController.getInstance().authenticate(data);
             if (e == null) {
                 return true;
             } else {
                 getAlert("Username already exist");
                 return false;
             }
        } else {
            getAlert("No puedes dejar los campos en blanco.");
            return false;
            
         }
    }
    

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
}
