package aed.tp2.Estructuras;

import java.util.Iterator;

public class ListaEnlazada<T> implements Secuencia<T>, Iterable<T> {

    private Nodo primero;
    private Nodo ultimo;
    private int longitud;
    
    private class Nodo{
        T contenido;
        Nodo siguiente;
        Nodo anterior;

        Nodo(T cont){
            contenido = cont;
        }
    }    

    public ListaEnlazada(){
        primero = null;
        ultimo = null;
        longitud = 0;
    }

    public int longitud(){
        return longitud;
    }

    public void agregar(T cont){
        Nodo nuevo = new Nodo(cont);
        nuevo.anterior = ultimo;
        if(ultimo == null)
            primero = nuevo;
        else
            ultimo.siguiente = nuevo;
        ultimo = nuevo;
        longitud++;
    }

    public T obtener(int i){
        Nodo actual = primero;
        for(int n = 0; n < i; n++)
            actual = actual.siguiente;
        return actual.contenido;
    }

    public void eliminar(int i){
        Nodo actual = primero;
        for(int n = 0; n < i; n++)
            actual = actual.siguiente;
        if(actual.anterior == null)
            primero = actual.siguiente;
        else
            actual.anterior.siguiente = actual.siguiente;
        if(actual.siguiente == null)
            ultimo = actual.anterior;
        else
            actual.siguiente.anterior = actual.anterior;
        longitud--;
    }

    public void modificarPosicion(int i, T cont){
        Nodo actual = primero;
        for(int n = 0; n < i; n++)
            actual = actual.siguiente;
        actual.contenido = cont;
    }

    private class Iterador implements Iterator<T>{
        private Nodo actual = primero;

        @Override 
        public boolean hasNext(){
            return actual != null;
        }

        @Override 
        public T next(){
            T cont = actual.contenido;
            actual = actual.siguiente;
            return cont;
        }

    }

    @Override 
    public Iterator<T> iterator(){
        return new Iterador();
    }
}
