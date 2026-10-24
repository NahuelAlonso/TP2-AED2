package aed.tp2.Estructuras;

public class ListaEnlazada<T> implements Secuencia<T> {

    private class Nodo{
        T contenido;
        Nodo siguiente;
        Nodo anterior;

        Nodo(T cont){
            contenido = cont;
        }
    }
    
    private Nodo primero;
    private Nodo ultimo;
    private int longitud;
    

    public ListaEnlazada(){
        primero = null;
        ultimo = null;
        longitud = 0;
    }


    @Override 
    public int longitud(){
        return longitud;
    }

    @Override 
    public void agregar(T cont){
        Nodo nuevo = new Nodo(cont);
        nuevo.anterior = ultimo;
        if(ultimo == null){
            primero = nuevo;
        }
        else{
            ultimo.siguiente = nuevo;
        }
        ultimo = nuevo;
        longitud++;
    }

    @Override 
    public T obtener(int i){
        Nodo actual = primero;
        for(int n = 0; n < i; n++){
            actual = actual.siguiente;
        }
        return actual.contenido;
    }

    @Override 
    public void eliminar(int i){
        Nodo actual = primero;
        for(int n = 0; n < i; n++){
            actual = actual.siguiente;
        }
        if(actual.anterior == null){
            primero = actual.siguiente;
        }
        else{
            actual.anterior.siguiente = actual.siguiente;
        }
        if(actual.siguiente == null){
            ultimo = actual.anterior;
        }
        else{
            actual.siguiente.anterior = actual.anterior;
        }
        longitud--;
    }

    @Override 
    public void modificarPosicion(int i, T cont){
        Nodo actual = primero;
        for(int n = 0; n < i; n++){
            actual = actual.siguiente;
        }
        actual.contenido = cont;
    }
}
