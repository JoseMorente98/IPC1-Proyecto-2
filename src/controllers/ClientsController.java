/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Car;
import beans.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jose Morente
 */
public class ClientsController {
    private static ClientsController instance;
    private ObservableList<Client> clients;
    protected Client start;
    protected Client latest;
    private int count  = 1 ;

    public ClientsController() {
        clients = FXCollections.observableArrayList();
        start = null;
        latest = null;
        initClient();
    }
    
    /**
     * INICIALIZAR DATOS
    **/
    public void initClient() {
        addAtEnd("0", "Rafael Morente", "cliente", "12345", "Oro");
    
    }

    /**
     * @return the instance
     */
    public static ClientsController getInstance() {
        if(instance == null) {
            instance = new ClientsController();
        }
        return instance;
    }
    
    /**
     * MÉTODO PARA VERIFICAR SI ESTA VACÍA LA LISTA
     * @return true
     */
    public boolean isEmpty() {
        return start == null;
    }
    
    /**
     * AGREGAR NUEVO NODO AL FINAL DE LA LISTA CIRCULAR DOBLE
     * @param dpi
     * @param fullName
     * @param username
     * @param password
     * @param role
     */
    public void addAtEnd(String dpi, String fullName, String username, String password, String role) {
        
        Client c = new Client(count, Long.parseLong(dpi), fullName, username, password, role, null, 0);
        if(isEmpty()) {
            start = c;
            start.setNext(c);
            c.setPrevious(start);
            latest = c;
        } else {
            latest.setNext(c);
            c.setNext(start);
            c.setPrevious(latest);
            latest = c;
            start.setPrevious(latest);
        }
        count++;
    }
    
    /**
     * AGREGAR NUEVO NODO AL FINAL DE LA LISTA CIRCULAR DOBLE
     * @param client
     */
    public int add(Client client) {
        Client c = new Client(count, client.getDpi(), client.getFullName(), client.getUsername(), client.getPassword(), client.getRole(), client.getCarList(), 0);
        if(isEmpty()) {
            start = c;
            start.setNext(c);
            c.setPrevious(start);
            latest = c;
        } else {
            latest.setNext(c);
            c.setNext(start);
            c.setPrevious(latest);
            latest = c;
            start.setPrevious(latest);
        }
        count++;
        return count;
    }
    
    /**
     * MÉTODO PARA RETORNAR LOS NODOS DE LA LISTA CIRUCLAR DOBLE
     * @return the clients
     */
    public ObservableList<Client> getClients() {
        this.clients.clear();
        if(!isEmpty()) {
            Client aux = start;
            do {
                System.out.println(aux);
                clients.add(aux);
                aux = aux.getNext();
            } while(aux != start);
        }
        return clients;
    }
    
    /**
     * BUSCAR NODO POR REFERENCIA EN LA LISTA CIRCULAR DOBLE
     * @param dpi
     */
    public boolean search(int id) {
        Client aux = latest;
        do{
            if (aux.getId()== id){
                return true;
            } else{
                aux = aux.getNext();
            }
        }while(aux != latest);
        return false;
    }
    public Client searchForUserName(String username) {
        Client aux = latest;
        do{
            if (aux.getUsername().equals(username)){
                return aux;
            } else{
                aux = aux.getNext();
            }
        }while(aux != latest);
        return null;
    }
    
    public Client searchClient(int id) {
        Client aux = latest;
        do{
            if (aux.getId() == id){
                System.out.println(aux);
                return aux;
            } else{
                aux = aux.getNext();
            }
        }while(aux != latest);
        return null;
    }
    
    /**
     * BUSCAR NODO POR REFERENCIA EN LA LISTA CIRCULAR DOBLE
     * @param dpi
     */
    public Client getClient(int id) {
        Client aux = latest;
        do{
            if (aux.getId()== id){
                return aux;
            } else{
                aux = aux.getNext();
            }
        }while(aux != latest);
        return null;
    }
    
    /**
     * ACTUALIZAR NODO DE LA LISTA CIRCULAR DOBLE
     * @param dpi
     * @param fullName
     * @param username
     * @param password
     * @param role
     * @param carList
     */
    public void update(int id, long dpi, String fullName, String username, String password, String role, int count) {
        if(search(id)) {
            Client aux = start;
            do {
                if(aux.getDpi() == dpi) {
                    aux.setFullName(fullName);
                    aux.setUsername(username);
                    aux.setPassword(password);
                    aux.setRole(role);
                    if (count != 0) {
                        int auxCount = aux.getCount();
                        aux.setCount(auxCount + count);
                    }
                    if (aux.getCount() >=3 ) {
                        aux.setRole("Oro");
                    }
                }
                aux = aux.getNext();
            } while(aux != start);
        }
    }
    
    /**
     * ELIMINAR NODO DE LA LISTA CIRCULAR SIMPLE
     * @param dpi
     */
    public void delete(int id) {
        Client current = start;
        Client previous = latest;
        if(search(id)) {
            do {
                if(current.getId()== id) {
                    if(current == start) {
                        start = start.getNext();
                        latest.setNext(start);
                        start.setPrevious(latest);
                    } else if(current == latest) {
                        latest = previous;
                        start.setPrevious(latest);
                        latest.setNext(start);
                    } else {
                        previous.setNext(current.getNext());
                        current.getNext().setPrevious(previous);
                    }
                }
                previous = current;
                current = current.getNext();
            } while(current != start);
        }
    }
        
    public boolean verifications(String username){
        Client aux = latest;
        do{
            if (aux.getUsername().equals(username)){
                return true;
            } else{
                aux = aux.getNext();
            }
        }while(aux != latest);
        return false;
    }
    
    public Client authenticate(String username, String password){
        Client aux = latest;
        do{
            if (aux.getUsername().equals(username) && aux.getPassword().equals(password)){
                return aux;
            } else{
                aux = aux.getNext();
            }
        }while(aux != latest);
        return null;
    }
}
