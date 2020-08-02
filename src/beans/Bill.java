/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Date;


public class Bill {
    private int id;
    private int idWorkOrder;
    private Client client;
    private String name;
    private String car;
    private String path;
    private String service;
    private Double total;
    private Date date;
    private String state;
    private Bill next;
    
    public Bill() {
    }

    public Bill(int id,int idWorkOder, Client client,String car, String path, String service, Double total, Date date, String state) {
        this.id = id;
        this.idWorkOrder = idWorkOder;
        this.client = client;
        this.name = client.getFullName();
        this.car = car;
        this.path = path;
        this.service = service;
        this.total = total;
        this.date = date;
        this.state = state;
        this.next = null;
    }
    
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdWorkOrder() {
        return idWorkOrder;
    }

    public void setIdWorkOrder(int idWorkOrder) {
        this.idWorkOrder = idWorkOrder;
    }
 
    
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Bill getNext() {
        return next;
    }

    public void setNext(Bill next) {
        this.next = next;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    
    
    
    
    
}
