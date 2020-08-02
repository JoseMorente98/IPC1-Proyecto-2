/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Bill;
import beans.Client;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BillController {
    
    private static BillController instance;
    
    /*SINGLETON*/
    public static BillController getInstance() {
        if(instance == null) {
            instance = new BillController();
        }
        return instance;
    }
    
    private ObservableList<Bill> bills;
        
    private int count = 1;
    private Bill first;
    
    public BillController() {
        
        bills = FXCollections.observableArrayList();
        first = null;
        
    }
    
    
    /*CONSULTA SI ESTA VACIA LA LISTA*/
    public boolean isEmpty() {
        return first == null;
    }
    /*METODO AGREGAR*/
    public void add(int idWO, Client client, String car, String path, String service, Double total, Date date){
    
        Bill newBill = new Bill(count,idWO, client, car, path, service, total, date, "SIN PAGAR");
        
         if (isEmpty()) {
            // Inicializa la lista agregando como inicio al nuevo nodo.
            first = newBill;
        // Caso contrario recorre la lista hasta llegar al ultimo nodo
            //y agrega el nuevo.
        } else {
            // Crea ua copia de la lista.
            Bill aux = first;
            // Recorre la lista hasta llegar al ultimo nodo.
            while (aux.getNext() != null) {
                aux = aux.getNext();
            }
            // Agrega el nuevo nodo al final de la lista.
            aux.setNext(newBill);
        }
        
        count ++;
    }
    
    
    /*METODO MOSTRAR */
    public ObservableList<Bill> getBills(int id){
        bills.clear();
        Bill b = first;
        while(b!=null){
            if (b.getClient().getId() == id) {
                bills.add(b);
            }
            b = b.getNext();
        }
        return bills;
        
    }
    
    
    /*BUSCAR SERVICIO*/
    public Bill search(int id){
        Bill aux = first;
    
        while (aux != null) {
            if (aux.getId() == id) {
                return aux;
            }
            aux = aux.getNext();
        }
        return null;
    }
    
    //EDITA LA ORDEN Y LA VUELVE PAGADA
    public void editNodeBill(String d, int id){
        Bill actual = new Bill();
        actual = first;
        
        while (actual != null) {
            if (actual.getId() == id) {
                actual.setState(d);
            }
            actual = actual.getNext();
        }
        
    }
    
}
