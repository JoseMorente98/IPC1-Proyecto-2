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
public class SpareParts {
    private int id;
    private String name;
    private String mark;
    private String model;
    private int stock;
    private Double price;
    private SpareParts next;
    
    public SpareParts() {
    }

    public SpareParts(int id, String name, String mark, String model, int stock, Double price) {
        this.id = id;
        this.name = name;
        this.mark = mark;
        this.model = model;
        this.stock = stock;
        this.price = price;
        this.next = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public SpareParts getNext() {
        return next;
    }

    public void setNext(SpareParts next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "SpareParts{" + "id=" + id + ", name=" + name + ", mark=" + mark + ", model=" + model + ", stock=" + stock + ", price=" + price + '}';
    }
        
}
