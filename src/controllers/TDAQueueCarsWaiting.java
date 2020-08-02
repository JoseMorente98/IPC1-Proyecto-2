/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Car;
import beans.Client;
import beans.Employee;
import beans.Service;
import beans.WorkOrder;
import java.util.Date;


/*ESTA ES LA COLA DE CARROS QUE YA ESTAN SIENDO ATENDIDOS*/
public class TDAQueueCarsWaiting {
    
    private static TDAQueueCarsWaiting instance;
    public static TDAQueueCarsWaiting getInstance() {
        if (instance == null) {
            instance = new TDAQueueCarsWaiting();
        }
        return instance;
    }
    
    
    private WorkOrder firstNode;
    private int length;

    public TDAQueueCarsWaiting() {
        firstNode = null;
        length = 0;
    }

    private boolean isEmpty() {
        return firstNode == null;
    }
    
    
    public void push(WorkOrder w){
        WorkOrder node = new WorkOrder();
        node = w;
        
        if (isEmpty()) {
            firstNode = node;
            firstNode.setNext(null);

        } else {
            if (firstNode.getPriority() < w.getPriority()) {
                node.setNext(firstNode);
                firstNode = node;
            } else {
                WorkOrder ant = null;
                WorkOrder next = firstNode;
                while (next != null && w.getPriority() <= next.getPriority()) {
                    ant = next;
                    next = next.getNext();
                }
                node.setNext(next);
                ant.setNext(node);

            }

        }

    }
    

    public void readNodes() {
        WorkOrder auxiliaryNode = firstNode;
        System.out.println("esto es Lista de Espera");
        while (auxiliaryNode != null) {
            System.out.print(auxiliaryNode.getClientName()+ " <-- ");
            auxiliaryNode = auxiliaryNode.getNext();
        }
        System.out.println();
    }

    
    public WorkOrder getTDAQueue(){
        return firstNode;
    }
    public void pop() {
        if (!isEmpty()) {
            firstNode = firstNode.getNext();
            length--;
        }
    }

    public WorkOrder getfirstNode() {
        if (!isEmpty()) {
            return firstNode;
        }
        return null;
    }

    public int getLength() {
        return length;
    }

    public WorkOrder getLastNode() {
        WorkOrder lastNode = firstNode;
        while (lastNode.getNext() != null) {
            lastNode = lastNode.getNext();
        }
        return lastNode;
    }

    private WorkOrder searchNode(int id) {
        WorkOrder auxiliaryNode = firstNode;
        while (auxiliaryNode != null && auxiliaryNode.getId() != id) {
            auxiliaryNode = auxiliaryNode.getNext();
        }
        return auxiliaryNode;
    }
}
