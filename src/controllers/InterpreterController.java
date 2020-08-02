
package controllers;

import beans.Car;
import beans.Client;
import beans.SpareParts;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Stack;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import views.Admin.Client.FXMLClientViewController;
import views.Admin.Employee.EmployeeViewController;
import views.Admin.Service.ServicesViewController;
import views.Admin.SpareParts.SparePartsViewController;

public class InterpreterController {
    private static InterpreterController instance;    
    public static InterpreterController getInstance() {
        if(instance == null) {
            instance = new InterpreterController();
        }
        return instance;
    }

    public InterpreterController() {
    }
    
    /**
     * SELECCIONAR ARCHIVO A ABRIR
     * @String extensions
     * @String description
     */
    public void openSelecFile(String extensions, String description){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter(description, extensions));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            interpret(selectedFile, extensions);            
        }
    }
    
    
    /**
     * VARIABLE DE SEPARACIÓN 
     */
    public static final String SEPARATOR = "#";

    public void interpret(File file, String text) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
        archivo = file;
        fr = new FileReader (archivo);
        br = new BufferedReader(fr);

        String line;
        while((line=br.readLine())!=null){
            String[] fields = line.split(SEPARATOR);
            for (int i = 0; i < fields.length; i++) {
                if (fields[i] != null) {
                    //INTERPRETE EMPLEADO
                    if (text.equalsIgnoreCase("*.emp")) {
                        EmployeesController.getInstance().addLast(fields[i], fields[i+1], fields[i+2], fields[i+3]);
                        EmployeeViewController.getInstance().initTableView();
                    //INTERPRETE REPUESTOS
                    } else if(text.equals("*.rep")) {
                        SparesPartsController.getInstance().add(fields[i], fields[i+1], fields[i+2], Integer.parseInt(fields[i+3]), Double.parseDouble(fields[i+4]));
                        SparePartsViewController.getInstance().initTableView();
                    //INTERPRETE SERVICIOS
                    } else if(text.equals("*.ser")) {
                        String[] parts = fields[i+3].split(";");
                        Stack a = new Stack();
                        Double sPrice = 00.0;
                        for (int j = 0; j < parts.length; j++) {
                            SpareParts s = SparesPartsController.getInstance().search(Integer.parseInt(parts[j]));
                            if (s != null) {
                                SpareParts temp = new SpareParts(s.getId(), s.getName(), s.getMark(), s.getModel(), 1, s.getPrice());
                                a.add(temp);
                                sPrice = sPrice + s.getPrice();
                            }
                        }
                        ServicesController.getInstance().add(fields[i], fields[i+1], fields[i+2],  a, Double.parseDouble(fields[i+4]), sPrice, true);
                        ServicesViewController.getInstance().initTableView();
                    //INTERPRETE CLIENTES Y AUTOS
                    } else if(text.equals("*.cliaut")) {
                        Client c = new Client(0, Long.parseLong(fields[i]), fields[i+1], fields[i+2], fields[i+3], fields[i+4]);
                        Car start;
                        start = c.getCarList();
                        CarController.getInstance().setCarClient(start);
                        String[] cars = fields[i+5].split(";");
                        //RECORRER LISTA DE CARROS
                        for (int j = 0; j < cars.length; j++) {
                            String[] detailsCar = cars[j].split(",");
                            CarController.getInstance().addAtEnd(detailsCar[0], detailsCar[1], detailsCar[2], detailsCar[3]);
                        }
                        c.setCarList(CarController.getInstance().returnCars());
                        ClientsController.getInstance().add(c);
                        FXMLClientViewController.getInstance().initTableView();
                    }
                }
                    break;
                }   
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            try{                    
                if( null != fr ){   
                    fr.close();     
                }                  
            }catch (Exception e2){ 
                e2.printStackTrace();
            }
        }
    }
        
}
