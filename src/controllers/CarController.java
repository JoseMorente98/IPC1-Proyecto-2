/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jose Morente
 */
public class CarController {
    private static CarController instance;
    private ObservableList<Car> cars;
    private int size = 1;
    public Car carClient;
    private Car start;
    private Car latest;
    
    public CarController() {
        cars = FXCollections.observableArrayList();
    }

    /**
     * @return the instance
     */
    public static CarController getInstance() {
        if(instance == null) {
            instance = new CarController();
        }
        return instance;
    }
    
    /**
     * INICIALIZAR DATOS
    **/
    public Car returnCars(){
        return start;
    }
    /*SETEA EN LA LISTA LOS CARROS DE UN CLIENTE EN ESPECIFICO PARA TRABAJAS LOS */
    /*METODOS DE LA LISTA SOLO CON ESOS CARROS*/
    public void setCarClient(Car c) {
        if(c != null) {
            Car aux = c;
            do {
                //this.addAtEnd(aux);
                aux = aux.getNext();
            } while(aux != c);
        } else {
            this.start = c;
        }
    }
    
    
    /**
     * MÉTODO PARA VERIFICAR SI ESTA VACÍA LA LISTA
     * @return true
     */
    public boolean isEmpty() {
        return start == null;
    }
    
    /**
     * MÉTODO PARA RETORNAR LOS NODOS DE LA LISTA CIRUCLAR SIMPLE
     * @return the cars
     */
    public ObservableList<Car> getCars() {
        this.cars.clear();
        if(!isEmpty()) {
            Car aux = start;
            do {
                cars.add(aux);
                aux = aux.getNext();
            } while(aux != start);
        }
        return cars;
    }
    
    /**
     * OBTENER LISTA
     */
    public void getAll() {
        if(!isEmpty()) {
            Car aux = start;
            do {
                aux = aux.getNext();
            } while(aux != start);
        }
    }
    /**
     * AGREGAR NUEVO NODO AL FINAL DE LA LISTA CIRCULAR SIMPLE
     * @param plate
     * @param brand
     * @param model
     * @param path
     */
    public void addAtEnd(String plate, String brand, String model, String path) {
        Car c = new Car(size, plate, brand, model, path);
        if(isEmpty()) {
            start = c;
            latest = c;
            latest.setNext(start);
        } else {
            latest.setNext(c);
            c.setNext(start);
            latest = c;
        }
        size++;
    }
    
    /**
     * AGREGAR NUEVO NODO AL FINAL DE LA LISTA CIRCULAR SIMPLE
     * @param c
     */
    public void addAtEnd(Car car) {
        Car c = new Car(size, car.getPlate(), car.getBrand(), car.getModel(), car.getPath());
        if(isEmpty()) {
            start = c;
            latest = c;
            latest.setNext(start);
        } else {
            latest.setNext(c);
            c.setNext(start);
            latest = c;
        }
        size++;
    }

    /**
     * BUSCAR NODO POR REFERENCIA EN LA LISTA CIRCULAR SIMPLE
     * @param plate
     * @return found
     */
    public boolean search(int id){
        Car aux = start;
        do{
            if (aux.getId() == id){
                return true;
            }
            else{
                aux = aux.getNext();
            }
        }while(aux != start);
        return false;
    }
    
    /**
     * BUSCAR NODO POR REFERENCIA EN LA LISTA CIRCULAR SIMPLE
     * @param plate
     * @return car
     */
    public Car getCar(String plate){
        Car aux = start;
        do{
            if (aux.getPlate().equals(plate)){
                return aux;
            }
            else{
                aux = aux.getNext();
            }
        }while(aux != start);
        return null;
    }
    
    /**
     * ELIMINAR NODO DE LA LISTA CIRCULAR SIMPLE
     * @param id
     */
    public void delete(int id) {
        if(search(id)) {
            if(start.getId() == id) {
                start = start.getNext();
                latest.setNext(start);
            } else {
                Car aux = start;
                while(aux.getNext().getId() != id) {
                    aux = aux.getNext();
                }
                if(aux.getNext() == latest) {
                    aux.setNext(start);
                    latest = aux;
                } else {
                    Car next = aux.getNext();
                    aux.setNext(next.getNext());
                }
            }
            size--;
        }
    }
    
    public void eliminar(int id){
        Car ant, act;
        ant = null;  
        act = start;
        if(start != null) {
            while((act != null)&&(act.getId() != id))
            {             
               ant = act;
               act = act.getNext();
            }    
         
            if((act == null)||(id != act.getId()))
            {
               System.out.println("\n ¡¡¡ No existe !!!");        
            }    
            else
            {  
               if (act == start)//caso 1: Elimina Primero 
               {
                  start = start.getNext();
                  latest.setNext(start);
                  act.setNext(null);
               }
               
               else if (act == latest)//caso 2: elimina ultimo
               {
                  ant.setNext(latest.getNext());
                  latest = ant;
                  act.setNext(null);
               }                        
               else  // caso 3: elimina en medio 
               {
                  ant.setNext(act.getNext());
                  act.setNext(null);
               }    
            }
         }
         else
         {
            System.out.print("\n¡¡¡ No hay Elementos !!!");
         }                                    
      }        
    
    /**
     * ACTUALIZAR NODO DE LA LISTA CIRCULAR SIMPLE
     * @param plate
     * @param brand
     * @param model
     * @param path
     */
    public void update(int id, String plate, String brand, String model, String path) {
        if(search(id)) {
            Car aux = start;
            do {
                if(aux.getId()== id) {
                    aux.setBrand(brand);
                    aux.setModel(model);
                    aux.setPath(path);
                    aux.setPlate(plate);
                }
                aux = aux.getNext();
            } while(aux != start);
        }
    }
    
    
    
    public void cleanList(){
        start = null;
        latest = null;
    }

}
