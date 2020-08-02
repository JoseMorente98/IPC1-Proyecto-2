/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.Admin.Service;

import beans.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import controllers.ServicesController;
import controllers.SparesPartsController;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Jose Morente
 */
public class ViewInsertServiceController implements Initializable {

     /*SINGLETON*/
    private static ViewInsertServiceController instance;
    public static ViewInsertServiceController getInstance(){
        if(instance == null){
            instance = new ViewInsertServiceController();
        }
        return instance;
    }
    /*---------------*/
    
    @FXML ComboBox list;
    @FXML Button add, remove, ok;
    @FXML TableView<SpareParts> tableView;
    @FXML TableColumn<SpareParts, Integer> id;
    @FXML TableColumn<SpareParts, String> name;
    @FXML TableColumn<SpareParts, String> price;
    @FXML TableColumn<SpareParts, String> quantity;
    @FXML TextField sName, sMark, sModel, sPrice, sQuantity;
    @FXML StackPane stackPane;
    @FXML Text textInfo;
    
    private Service serviceAux;
    ObservableList observable;
        Stack stack = new Stack();
            
    
    public ViewInsertServiceController() {
        serviceAux = ServicesController.getInstance().search(ServicesController.getInstance().getIdAux());
        observable = FXCollections.observableArrayList();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list.setItems(null);
        
        list.setItems(SparesPartsController.getInstance().getSparePartsName(serviceAux.getModel(), serviceAux.getMark()));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("stock"));
        initTableView();
        loadService();
    }    
    
    /*CARGA EL SERVICIO Y SUS PARTES*/
    
    public void loadService(){
        sName.setText(serviceAux.getName());
        sPrice.setText(String.valueOf(serviceAux.getWorkPrice()));
        sMark.setText(serviceAux.getMark());
        sModel.setText(serviceAux.getModel());
    }
    
    
    
     /*INICIALIZAR TABLA*/
    public void initTableView() {
        observable.clear();
        for (int i = 0; i < serviceAux.getSparePartList().size(); i++) {
            SpareParts s = (SpareParts)serviceAux.getSparePartList().get(i);
            observable.add(s);
        }
        tableView.setItems(observable);
       
    }
    
    @FXML
    public void addPartsToTable(ActionEvent event){
        if (list.getSelectionModel().getSelectedItem() != null && !sQuantity.getText().isEmpty()) {
            
            int quantiyParts = Integer.parseInt(sQuantity.getText());
            int tempStock = 0;
            Double tempPrice = 0.0;
            if (serviceAux.getSparePartList().size() != 0) {
                stack = serviceAux.getSparePartList();
            }  else {
                serviceAux.setSparePartList(stack);                
            }
            
            SpareParts s = new SpareParts();
            s = SparesPartsController.getInstance().searchForName(list.getSelectionModel().getSelectedItem().toString());
            
            if ( quantiyParts > 0 && s.getStock() >=  quantiyParts) {
                tempStock = s.getStock();
                tempPrice = s.getPrice();
                
                SpareParts sp = new SpareParts(s.getId(), s.getName(), s.getMark(), s.getModel(), quantiyParts, s.getPrice());
                
                stack.push(sp);
            
                
                Double price = serviceAux.getSparePartsPrice() + (s.getPrice() * quantiyParts);
                
               
                ServicesController.getInstance().edit(serviceAux.getId(), serviceAux.getName() , serviceAux.getMark() , serviceAux.getModel(), 
                stack, serviceAux.getWorkPrice() , price, true);
                
                
                
                
                ServicesViewController.getInstance().initTableView();
           
                int newStock = tempStock - quantiyParts;
                SparesPartsController.getInstance().edit(s.getId(), s.getName(), s.getMark(), s.getModel(), newStock, tempPrice);
        
            }
            

        } 
                
        initTableView();
    } 

    
    @FXML
    public void updateInfo(ActionEvent event){
        if (getValidations()) {
            ServicesController.getInstance().edit(serviceAux.getId(), sName.getText() , sMark.getText() , sModel.getText() , 
                    serviceAux.getSparePartList(), Double.parseDouble(sPrice.getText()) , serviceAux.getSparePartsPrice(), false);
            
            ServicesViewController.getInstance().initTableView();
            initTableView();
        } 
    }
    
    
    @FXML
    public void deleteFromTable(ActionEvent event){
        Stack stackAux = serviceAux.getSparePartList();
        Double newPrice = serviceAux.getSparePartsPrice();
        if (stackAux.size() > 1) {
            SpareParts s = (SpareParts)stackAux.peek();
            serviceAux.setSparePartsPrice(newPrice - (s.getStock() * s.getPrice()) );
            stackAux.pop();
            serviceAux.setSparePartList(stackAux);
        }
        initTableView();
        ServicesViewController.getInstance().initTableView();
            
        
    }
    
    /*VALIDA QUE LOS CAMPOS NO SEAN VACIOS*/
    public boolean getValidations(){
        if(!sName.getText().isEmpty() && !sMark.getText().isEmpty() &&
                 !sModel.getText().isEmpty() && !sPrice.getText().isEmpty()){
            
            Service s = ServicesController.getInstance().searchForName(sName.getText());
            if (s == null) {
                try {
                    Double.parseDouble(sPrice.getText());
                    return true;
                } catch (NumberFormatException e) {
                    textInfo.setText("No puede ingresar texto en campos numéricos.");
                    return false;
                }
            } else {
                if (s.getMark().equalsIgnoreCase(sMark.getText()) && s.getModel().equalsIgnoreCase(sModel.getText()) ) {
                    textInfo.setText("There is already a service with the same name");
                    return false;
                } else {
                    return true;
                }   
            }
            
        } else {
            textInfo.setText("No puedes dejar los campos en blanco.");
            return false;
            
         }
    }
    
}
