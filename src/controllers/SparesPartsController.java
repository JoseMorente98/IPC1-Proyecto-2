/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.SpareParts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SparesPartsController {
    private static SparesPartsController instance;
    public static SparesPartsController getInstance() {
        if (instance == null) {
            instance = new SparesPartsController();
        }
        return instance;
    }
    
    /***
     * VARIABLES 
     */
    private ObservableList<SpareParts> parts;
    private int count = 1;
    protected SpareParts first;

    public SparesPartsController() {
        parts = FXCollections.observableArrayList();
        first = null;
    }

    /**
     * CONSULTA SI ESTA VACIA LA LISTA
     */
    public boolean isEmpty() {
        return first == null;
    }
    
    public void initSpareParts() {
    }

    
    /**
     * AGREGAR REPUESTOS 
     */
    public void add(String name, String mark, String model, int stock, Double price) {
        SpareParts newSpareParts = new SpareParts(count, name, mark, model, stock, price);
        if (isEmpty()) {
            first = newSpareParts;
        } else {
            SpareParts aux = first;
            while (aux.getNext() != null) {
                aux = aux.getNext();
            }
            aux.setNext(newSpareParts);
        }
        count++;
    }

    /**
     * BUSCAR EN LA PILA
     */
    public SpareParts search(int id) {
        SpareParts aux = first;
        while (aux != null) {
            if (aux.getId() == id) {
                return aux;
            }
            aux = aux.getNext();
        }
        return null;
    }
    
    /**
     * BUSCAR POR NOMBRE 
     */
    public SpareParts searchForName(String name) {
        SpareParts aux = first;
        while (aux != null) {
            if (aux.getName().equalsIgnoreCase(name)) {
                return aux;
            }
            aux = aux.getNext();
        }
        return null;
    }
    
    /**
     * ACTUALIZAR 
     */
    public void edit(int id, String name, String mark, String model, int stock, Double price) {
        SpareParts aux = first;
        while (aux != null) {
            if (aux.getId() == id) {
                aux.setName(name);
                aux.setMark(mark);
                aux.setModel(model);
                aux.setStock(stock);
                aux.setPrice(price);
            }
            aux = aux.getNext();
        }
    }

    /**
     * ELIMINAR
     */
    public void delete(int id) {
        if (search(id) != null) {
            if (first.getId() == id) {
                first = first.getNext();
            } else {
                SpareParts aux = first;

                while (aux.getNext().getId() != id) {
                    aux = aux.getNext();
                }
                SpareParts next = aux.getNext().getNext();
                aux.setNext(next);
            }
        }
    }
    
    /**
     * OBTENER REPUESTOS 
     */
    public ObservableList<SpareParts> getSpareParts() {
        this.parts.clear();
        
        SpareParts aux = first;
        while (aux != null) {
            parts.add(aux);
            aux = aux.getNext();

        }
        return parts;
    }
    
    /**
     * OBTENER REPUESTOS POR MODELO Y MARCA 
     */
    public ObservableList<String> getSparePartsName(String model, String mark) {
        ObservableList<String> partsName = FXCollections.observableArrayList();

        SpareParts aux = first;
        while (aux != null) {
            if (aux.getModel().equalsIgnoreCase(model) && aux.getMark().equalsIgnoreCase(mark) && aux.getStock() > 0) {
                partsName.add(aux.getName());
            }
            aux = aux.getNext();

        }
        return partsName;
    }
    
    
    public void show() {
        SpareParts aux = first;
        while (aux != null) {
            System.out.println(aux.getName());    
            aux = aux.getNext();

        }
    }
    
}

