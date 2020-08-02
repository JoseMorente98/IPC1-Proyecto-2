/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import beans.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Stack;

public class ServicesController {
    private static ServicesController instance;
    public static ServicesController getInstance() {
        if(instance == null) {
            instance = new ServicesController();
        }
        return instance;
    }
    
    /**
     *VARIABLES
     */
    private ObservableList<Service> services;
    private int count = 1;
    protected Service first;    
    protected ServiceRandom firstRandom;
    private int aux;
    Stack a = new Stack();
        
    public ServicesController() {
        services = FXCollections.observableArrayList();
        first = null;
    }
    
    /**
     * INICIALIZAR SERVICIOS 
     */
    public void initServices(){
        add("Diagnostico", "Any", "Any", a, 500.00, 0.0, true);
    }
    
    /**
     * CONSULTA SI ESTA VACIA LA LISTA
     */
    public boolean isEmpty() {
        return first == null;
    }
    
    /**
     * AGREGAR A LA LISTA DE SERVICIOS 
     */
    public void add(String name, String mark, String model, Stack stack,  Double workPrice, Double spPrice, boolean state){
        Service newService = new Service(count, name, mark, model, stack, workPrice, spPrice, (workPrice+ spPrice), state);
        
         if (isEmpty()) {
            first = newService;
        } else {
            Service aux = first;
            while (aux.getNext() != null) {
                aux = aux.getNext();
            }
            aux.setNext(newService);
        }
        count ++;
    }
    
    /**
     * OBTENER SERVICIOS 
     */
    public ObservableList<Service> getServices(){
        this.services.clear();
        Service actual = new Service();
        actual = first;
        while (actual != null) {
            services.add(actual);
            actual = actual.next;
        }
        return this.services;
    }
    
    
    /**
     * BUSCAR SERVICIO
     */
    public Service search(int id){
        Service aux = first;
        
        while (aux != null) {
            if (aux.getId() == id) {
                return aux;
            }
            aux = aux.getNext();
        }
        return null;
    }
    
    
    /**
     * BUSCAR SERVICIO POR NOMBRE 
     */
    public Service searchForName(String name){
        Service aux = first;
    
        while (aux != null) {
            if (aux.getName().equalsIgnoreCase(name)) {
                return aux;
            }
            aux = aux.getNext();
        }
        return null;
    }
    
    /**
     * MODIFICAR SERVICIO 
     */
    public void edit(int id, String name, String mark, String model, Stack list, Double workPrice, Double spPrice, boolean state) {
        Service actual = first;
        while( actual != null ) {
            
            if (id != 1 ) {
                if (actual.getId() == id) {
                    actual.setName(name);
                    actual.setModel(model);
                    actual.setMark(mark);
                    actual.setSparePartList(list);
                    actual.setSparePartsPrice(spPrice);
                    actual.setWorkPrice(workPrice);
                    actual.setTotal(spPrice+workPrice);
                    actual.setState(state);
                }
            
            }
            actual = actual.next;
        }
    }
    
    /**
     * ELIMINAR SERVICIO 
     */
    public void delete(int id) {
        if (id != 1) {
            if (search(id) != null) {
                if (first.getId() == id) {
                    first = first.getNext();
                } else {
                    Service aux = first;

                    while (aux.getNext().getId() != id) {
                        aux = aux.getNext();
                    }
                    Service next = aux.getNext().getNext();
                    aux.setNext(next);
                }
            }
        }
    }
    
    /**
     * AGREGAR A LA LISTA DE SERVICIOS RANDOM 
     */
    public void add(int id, Service service){
        ServiceRandom sr = new ServiceRandom(id, service);
         if (firstRandom == null) {
            firstRandom = sr;
        } else {
            ServiceRandom aux = firstRandom;
            while (aux.getNext() != null) {
                aux = aux.getNext();
            }
            aux.setNext(sr);
        }
    }
    
    /***
     * BUSCAR EN LA LISTA DE SERVICIO RANDOM 
     */
    public Service searchRandom(int id){
        ServiceRandom aux = firstRandom;
        
        while (aux != null) {
            System.out.println(aux.getService());
            if (aux.getId() == id) {
                System.out.println("SERVICIO ENCONTRADO" +aux.getService());
                return aux.getService();
            }
            aux = aux.getNext();
        }
        return null;
    }
    
    /**
     * OBTENER SERVICIO POR MODELO Y MARCA 
     */
    public ObservableList<String> getServiceName(String model, String mark) {
        ObservableList<String> serviceName = FXCollections.observableArrayList();
        Service aux = first;
        while (aux != null) {
            if (aux.getModel().equalsIgnoreCase(model) && aux.getMark().equalsIgnoreCase(mark) || aux.getModel().equals("Any")   ) {
                if (aux.getState() == true) {
                    System.out.println("ESTADO 1" + aux);
                    serviceName.add(aux.getName());
                }
            }
            aux = aux.getNext();
        }
        return serviceName;
    }
    
    /**
     * OBTENER SERVICIO ALEATORIO
     */
    public Service getServiceRandom(String model, String mark) {
        firstRandom = null;
        Service aux = first;
        int quantity = 1;
        
        //SI ES CERO DEJARLO COMO DIAGNOSTICO
        while (aux != null) {
            if (aux.getModel().equalsIgnoreCase(model) && aux.getMark().equalsIgnoreCase(mark) || aux.getModel().equals("Any")   ) {
                if (aux.getState() == true) {
                    System.out.println("ESTADO 2" + aux);
                    quantity++;
                    System.out.println(quantity);
                    add(quantity, aux);
                }
            }
            aux = aux.getNext();
        }
        int numeroRandom = valorAleatorio(quantity-1);
        System.out.println("ID RANDOM" + numeroRandom);
        return searchRandom(numeroRandom);
    }
        
    public int valorAleatorio(int n) {
        return (int) (Math.random() * n) + 1;
    }
    
    public void change(int id){
        this.aux = id;
    }
    
    public int getIdAux(){
        return aux;
    }
    
}

class ServiceRandom {
    private int id;
    private Service service;
    private ServiceRandom next;

    public ServiceRandom(int id, Service service) {
        this.id = id;
        this.service = service;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the service
     */
    public Service getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(Service service) {
        this.service = service;
    }

    /**
     * @return the next
     */
    public ServiceRandom getNext() {
        return next;
    }

    /**
     * @param next the next to set
     */
    public void setNext(ServiceRandom next) {
        this.next = next;
    }
    
}