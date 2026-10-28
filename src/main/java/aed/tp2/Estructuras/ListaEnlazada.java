package aed.tp2.Estructuras;

public class ListaEnlazada<T> implements Secuencia<T> {

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
    
    public void agregar(T cont){//O(1)
        Nodo nuevo = new Nodo(cont);
        nuevo.anterior = ultimo;
        if(ultimo == null)
            primero = nuevo;
        else
            ultimo.siguiente = nuevo;
        ultimo = nuevo;
        longitud++;
    }

    //No tiene sentido que tome un parámetro pero no pudimos hacerlo más prolijo
    public T[] convertirAVector(T[] vector){//O(|L|), con |L| la longitud de la lista enlazada
        Nodo actual = primero;
        int i = 0;
        while(actual != null){//Se repite hasta que no haya siguiente, |L| veces. O(|L|)
            vector[i++] = actual.contenido;
            actual = actual.siguiente;
        }
        return vector;
    }

    public int longitud(){//No se usa. Está por completitud. O(1)
        return longitud;
    }
    
    public T obtener(int i){//No se usa. Está por completitud. O(i)
        Nodo actual = primero;
        for(int n = 0; n < i; n++)
            actual = actual.siguiente;
        return actual.contenido;
    }

    public void eliminar(int i){//No se usa. Está por completitud. O(i)
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

    public void modificarPosicion(int i, T cont){//No se usa. Está por completitud. O(i)
        Nodo actual = primero;
        for(int n = 0; n < i; n++)
            actual = actual.siguiente;
        actual.contenido = cont;
    }

    public String toString(){//No lo pide la consigna, está para debuggear y por completitud
        String res = "";
        Nodo actual = primero;
        while (actual != null){
            res += actual.contenido.toString() + ", ";
            actual = actual.siguiente;
        }
        return res;
    }
}
