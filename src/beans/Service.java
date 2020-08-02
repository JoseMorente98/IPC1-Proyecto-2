/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Stack;

/**
 *
 * @author Jose Morente
 */
public class Service {
    private int id;
    private String name;
    private String mark;
    private String model;
    private Stack sparePartList;
    private Double workPrice;
    private Double sparePartsPrice;
    private Boolean state;
    private Double total;
    public Service next;
    public Service() {
    }

    public Service(int id, String name, String mark, String model, Stack sparePartList, Double workPrice, Double sparePartsPrice, Double total, Boolean state) {
        this.id = id;
        this.name = name;
        this.mark = mark;
        this.model = model;
        this.sparePartList = sparePartList;
        this.workPrice = workPrice;
        this.sparePartsPrice = sparePartsPrice;
        this.total = total;
        this.state = state;
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

    public Stack getSparePartList() {
        return sparePartList;
    }

    public void setSparePartList(Stack sparePartList) {
        this.sparePartList = sparePartList;
    }

    public Double getWorkPrice() {
        return workPrice;
    }

    public void setWorkPrice(Double workPrice) {
        this.workPrice = workPrice;
    }

    public Double getSparePartsPrice() {
        return sparePartsPrice;
    }

    public void setSparePartsPrice(Double sparePartsPrice) {
        this.sparePartsPrice = sparePartsPrice;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Service getNext() {
        return next;
    }

    public void setNext(Service next) {
        this.next = next;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Service{" + "id=" + id + ", name=" + name + ", mark=" + mark + ", model=" + model + ", sparePartList=" + sparePartList + ", workPrice=" + workPrice + ", sparePartsPrice=" + sparePartsPrice + ", state=" + state + ", total=" + total + '}';
    }

}
