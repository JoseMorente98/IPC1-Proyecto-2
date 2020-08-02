
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author Jose Morente
 */
public class Car {
    private int id;
    private String plate;
    private String brand;
    private String model;
    private String path;
    private Car next;
    

    public Car() {
    }

    public Car(int id, String plate, String brand, String model, String path) {
        this.id = id;
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.path = path;
    }

    /**
     * @return the plate
     */
    public String getPlate() {
        return plate;
    }

    /**
     * @param plate the plate to set
     */
    public void setPlate(String plate) {
        this.plate = plate;
    }

    /**
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the next
     */
    public Car getNext() {
        return next;
    }

    /**
     * @param next the next to set
     */
    public void setNext(Car next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Car{" + "plate=" + plate + ", brand=" + brand + ", model=" + model + ", path=" + path + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}

