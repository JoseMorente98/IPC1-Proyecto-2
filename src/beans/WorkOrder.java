/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Date;

/**
 *
 * @author Jose Morente
 */
public class WorkOrder {
    private int id;
    private int priority;
    private Car car;
    private Client client;
    private Employee employee;
    private Date date;
    private Service service;
    private String state;
    private String clientName;
    private String clientRole;
    private String carDetails;
    private String serviceName;
    private String mechaic;
    private String imagePath;
    private Double workPrice;
    private Double spPrice;
    private Double total;
    private WorkOrder next;

    public WorkOrder() {
    }

    public WorkOrder(int id, Car car, Client client, Employee employee, Service service,Date date,  String state, int priority) {
        this.id = id;
        this.priority = priority;
        this.car = car;
        this.client = client;
        this.employee = employee;
        this.date = date;
        this.service = service;
        this.state = state;
        this.clientName = client.getFullName();
        this.clientRole = client.getRole();
        this.carDetails = car.getBrand()+ "/" + car.getModel();
        this.imagePath = car.getPath();
        this.serviceName = service.getName();
        this.workPrice = service.getWorkPrice();
        this.spPrice = service.getSparePartsPrice();
        this.total = service.getTotal();
        if (employee != null) {
        this.mechaic = employee.getName();
        }
        this.next = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCarDetails() {
        return carDetails;
    }

    public void setCarDetails(String carDetails) {
        this.carDetails = carDetails;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public WorkOrder getNext() {
        return next;
    }

    public void setNext(WorkOrder next) {
        this.next = next;
    }

    public String getMechaic() {
        return mechaic;
    }

    public void setMechaic(String mechaic) {
        this.mechaic = mechaic;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Double getWorkPrice() {
        return workPrice;
    }

    public void setWorkPrice(Double workPrice) {
        this.workPrice = workPrice;
    }

    public Double getSpPrice() {
        return spPrice;
    }

    public void setSpPrice(Double spPrice) {
        this.spPrice = spPrice;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public int getPriority() {
        return priority;
    }

    public String getClientRole() {
        return clientRole;
    }

    public void setClientRole(String clientRole) {
        this.clientRole = clientRole;
    }

    @Override
    public String toString() {
        return "WorkOrder{" + "id=" + id + ", priority=" + priority + ", client=" + client + ", employee=" + employee + ", date=" + date + ", service=" + service + ", state=" + state + ", clientName=" + clientName + ", clientRole=" + clientRole + ", carDetails=" + carDetails + ", serviceName=" + serviceName + ", mechaic=" + mechaic + ", imagePath=" + imagePath + ", workPrice=" + workPrice + ", spPrice=" + spPrice + ", total=" + total + '}';
    }
    
    
}
