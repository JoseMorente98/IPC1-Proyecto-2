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
public class ColaPrioridad {

    public class Nodo {

        int valor;
        int prioridad;
        Nodo siguiente;
        
    public void Nodo(){
        this.valor = 0;
        this.prioridad = 0;
        this.siguiente = null;
    }
    
    // Métodos get y set para los atributos.

        
        
        
        
    }
    
    
    private Nodo inicio;
    // Variable para registrar el tamaño de la lista.
    private int longitud;
    
     public void ColaPrioridad(){
        inicio = null;
        longitud = 0;
    }
     
    public boolean esVacia(){
        return inicio == null;
    }
    
    public void inserta(int elemento, int prioridad) {
        System.out.println("elememnto"  + elemento);
        Nodo p = new Nodo(), q;
        boolean encontrado = false;
        p= inicio;
        
        while ((p.siguiente != null) && (!encontrado)) {
            if (p.siguiente.prioridad == prioridad )
                    encontrado = true;
            else p = p.siguiente;
        
        q = p.siguiente;
        p.siguiente = new Nodo();
        p = p.siguiente;
        p.valor = (elemento);
        p.prioridad = (prioridad);
        p.siguiente = (q);
        
        
            
        }
        
    }
    
    public void show(){
        Nodo aux = inicio;

        while(aux != null){
           System.out.println("");
            System.out.println("el elemento es " + aux.valor + " la prioridad " + aux.prioridad );
            aux = aux.siguiente;
       }
        
    }    
    
    
//    private Celda cola;
//
//    public ColaPrioridad() {
//        cola = new Celda();
//        cola.sig = null;
//    }
//
//    public boolean vacia() {
//        return (cola == null);
//    }
//
//    public int primero_prioridad() throws Exception {
//        if (vacia()) {
//            throw new NullPointerException();
//        }
//        return cola.prioridad;
//    }
//
//
//    public void inserta(int elemento, int prioridad) {
//        
//        Celda p, q;
//        boolean encontrado = false;
//        p = cola;
//        while ((p.sig != null) && (!encontrado)) {
//            if (p.sig.prioridad == prioridad )
//                    encontrado = true;
//            else p = p.sig;
//        }
//        q = p.sig;
//        p.sig = new Celda();
//        p = p.sig;
//        p.elemento = elemento;
//        p.prioridad = prioridad;
//        p.sig = q;
//    }
//
//    public void suprime() {
//        if (vacia()) {
//            throw new NullPointerException();
//        }
//        cola = cola.sig;
//    }
//    
//    
//    public void show(){
//        Celda aux = cola;
//        
//        while(aux != null){
//            System.out.println("");
//            System.out.println("el elemento es " + aux.elemento + " la prioridad " + aux.prioridad );
//            aux = aux.sig;
//        }
//        
//        Celda temp = aux;
//        
//        
//        
//        
//    }
}
