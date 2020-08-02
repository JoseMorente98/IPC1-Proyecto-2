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
public class TDAQueueCarsInProcess {
    
    private static TDAQueueCarsInProcess instance;
    public static TDAQueueCarsInProcess getInstance() {
        if (instance == null) {
            instance = new TDAQueueCarsInProcess();
        }
        return instance;
    }
    
    
    private WorkOrder firstNode;
    private int length;

    public TDAQueueCarsInProcess() {
        firstNode = null;
        length = 0;
    }

    private boolean isEmpty() {
        return firstNode == null;
    }
    
    public void push(WorkOrder wo) {
        
        
        WorkOrder node = new WorkOrder();
        node = wo;
        
        if (isEmpty()) {
            firstNode = node;
            firstNode.setNext(null);

        } else {
       

            if (firstNode.getPriority() < wo.getPriority()) {
                node.setNext(firstNode);
                firstNode = node;
            } else {
                WorkOrder ant = null;
                WorkOrder next = firstNode;
                while (next != null && wo.getPriority() <= next.getPriority()) {
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
