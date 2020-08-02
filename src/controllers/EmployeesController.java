
package controllers;

import beans.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmployeesController {
    private static EmployeesController instance;
    private ObservableList<Employee> employees;
    private int count = 1;
    private Employee first = null;
    
    
    public EmployeesController() {
        employees = FXCollections.observableArrayList();
        first = null;
        
    }
    
    
    public static EmployeesController getInstance() {
        if(instance == null) {
            instance = new EmployeesController();
        }
        return instance;
    }
    
    public void initEmployee() {
        addLast("Empleado 1", "Administrador", "admin", "admin");
    
    }

    
    /*OBTIENE EL LISTADO DE EMPLEADOS*/
    public ObservableList<Employee> getEmployees() {
        this.employees.clear();
        Employee actual = new Employee();
        actual = first;
        
        while (actual != null) {
            
            employees.add(actual);
            actual = actual.next;
        }
        return employees;
    }
    
    /*OBTENER LISTADO DE MECANICOS*/
    public ObservableList<String> getMechanics() {
        ObservableList<String> mechanics = FXCollections.observableArrayList();
        
        
        Employee actual = new Employee();
        actual = first;
        
        while (actual != null) {
            if (actual.getRole().equalsIgnoreCase("Mecanico") && actual.isState() == true ) {
                    mechanics.add(actual.getName());
            }
            actual = actual.next;
        }
        if (mechanics.size() > 0) {
            return mechanics;
        } else {
            mechanics.add("Lista de Espera");
        }
        return mechanics;
    }
    
    
    /*-----------METODO AGREGAR------*/
    
    
    public void addLast(String name, String role, String username, String password){
        Employee e = new Employee(count, name, role, username, password, true);
        
        if (first == null) {
            first = e;
        } else {
            Employee aux = first;
            while (aux.getNext() != null) {
                aux = aux.getNext();
            }
            aux.setNext(e);
            e.setPrevious(aux);

        }

        count++;
    }
    
    
    /*------------METODO BUSCAR---------------*/
    public Employee search(int id){
        Employee actual = new Employee();
        actual = first;
        while(actual != null){
            if (actual.getId() == id) {
                return actual;
            }
            actual = actual.next;
        }
        return null;
    }
    
    /*BUSCAR POR NOMBRE*/
    public Employee searchForName(String name){
        Employee actual = new Employee();
        actual = first;
        while(actual != null){
            if (actual.getName().equals(name)) {
                return actual;
            }
            actual = actual.next;
        }
        return null;
    }
    
    
    /*METODO EDITAR*/
    public void edit(int id, String name, String role, String username, String password, boolean state ){
        Employee actual = new Employee();
        actual = first;
        while(actual != null){
            if (actual.getId() == id) {
                actual.setName(name);
                actual.setPassword(password);
                actual.setRole(role);
                actual.setUsername(username);
                actual.setState(state);
        
            }
            actual = actual.next;
        }    
    }
    
    /*METODO ELIMINAR*/
    public void delete(int id){
                
        if (first != null) {
            Employee aux = first;
            Employee temp = null;
            while (aux != null) {
                if (aux.getId()== id) {
                    if (temp == null) {
                        first = first.getNext();
                        aux.setNext(null);
                        aux = first;

                    } else {
                        temp.setNext(aux.getNext());
                        aux.setNext(null);
                        aux = temp.getNext();
                    }
                } else {
                    temp = aux;
                    aux = aux.getNext();
                }
            }
        }
        
    }
    
    
    
    
    
    /*METODO PARA AUTENTICAR Y VERIFICAR SI EL USERNAME YA EXISTE EXISTE*/
    public Employee authenticate(String[] data){
        Employee actual = new Employee();
        actual = first;
        while(actual != null){
            if (data.length == 2) {
                if (actual.getUsername().equals(data[0]) && actual.getPassword().equals(data[1])) {
                    return actual;
                }
            } else {
                if (actual.getUsername().equals(data[0])) {
                    return actual;
                }
            }
            actual = actual.next;
            
        }
        return null;
    }
    
}
