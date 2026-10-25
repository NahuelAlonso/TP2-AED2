package aed.tp2.Estructuras;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<K extends Comparable<K>, V> implements Diccionario<K, V>, Iterable<V> {
    
    Nodo raiz;
    int cardinal;

    private class Nodo {
        Nodo padre;
        Nodo izquierda;
        Nodo derecha;
        K indice;
        V val;

        public Nodo(K indice, V val, Nodo padre){
            this.indice = indice;
            this.val = val;
            this.padre = padre;
            izquierda = null;
            derecha = null;
        }

        private boolean esMayorQuePadre(){
            return indice.compareTo(padre.indice) > 0;
        }

        private Nodo nodoSiguiente() {//Si no tiene siguiente, devuelve null
            Nodo aux = this;
            if(aux.derecha != null){
                aux = aux.derecha;
                while(aux.izquierda != null)
                    aux = aux.izquierda;
            }
            else{
                while(aux.padre != null && aux.esMayorQuePadre())
                    aux = aux.padre;
                aux = aux.padre;
            }
            return aux;
        }
    }

    public ABB() {
        raiz = null;
        cardinal = 0;
    }

    public int tamaño() {
        return cardinal;
    }

    public V minimo(){
        return minimoNodo().val;
    }
    private Nodo minimoNodo(){
        Nodo actual = raiz;
        while (actual.izquierda != null)
            actual = actual.izquierda;
        return actual;
    }

    public V maximo(){
        Nodo actual = raiz;
        while (actual.derecha != null)
            actual = actual.derecha;
        return actual.val;
    }

    public void definir(K indice, V val){
        Nodo actual = raiz;
        Nodo anterior = raiz;
        
        if(actual == null){
            raiz = new Nodo(indice, val, null);
        }
        else{
            while (actual != null){
                anterior = actual;
                int comp = (int) indice.compareTo(actual.indice);
                if(comp < 0)
                    actual = actual.izquierda;
                if(comp > 0)
                    actual = actual.derecha;
                if(comp == 0)
                    return;
            }
            actual = new Nodo(indice, val, anterior);
            if(actual.esMayorQuePadre())
                anterior.derecha = actual;
            else
                anterior.izquierda = actual;
        }
        cardinal++;
    }

    public V obtener(K indice){
        Nodo actual = raiz;
        while(actual != null && indice.compareTo(actual.indice) != 0){
            if(indice.compareTo(actual.indice) < 0)
                actual = actual.izquierda;
            else
                actual = actual.derecha;
        }
        return actual.val;
    }

    public void borrar(K indice){
        Nodo actual = raiz;

        while(actual != null && actual.indice != indice){
            int comp = indice.compareTo(actual.indice);
            if(comp == 0)
                break;
            else actual = (comp < 0)? actual.izquierda : actual.derecha;
        }

        if(actual == null)
            return;

        if(actual.izquierda != null && actual.derecha != null){
            Nodo siguiente = actual.nodoSiguiente();
            actual.val = siguiente.val;
            actual = siguiente;
        }

        Nodo hijo = (actual.izquierda != null) ? actual.izquierda : actual.derecha;
        if(hijo != null)
            hijo.padre = actual.padre;

        //Reemplazo la referencia del padre (o la de la raíz, si el que quería borrar es la raiz)
        if(actual.padre == null)
            raiz = hijo;
        else if(actual.padre.izquierda == actual)
            actual.padre.izquierda = hijo;
        else
            actual.padre.derecha = hijo;
        cardinal--;
    } 

    public String toString(){
        return "{" + inOrder(raiz) + "}";
    }

    private String inOrder(Nodo actual){
        String str = "";
        if (actual != null){
            if(actual.izquierda != null)
                str = inOrder(actual.izquierda) + ",";
            
            str = str + actual.val.toString();

            if(actual.derecha != null)
                str = str + "," + inOrder(actual.derecha);
        }
        return str;
    }

    public class ABB_Iterador implements Iterator<V>{
        private Nodo _actual;
        
        public ABB_Iterador(){
            _actual = null;
        }

        public boolean hasNext() {
            if(_actual == null)
                return cardinal > 0;
            else
                return _actual.nodoSiguiente() != null;
        }
        
        public V next() {
            if(_actual == null)
                _actual = minimoNodo();
            else
                _actual = _actual.nodoSiguiente();
            return _actual.val;
        }
    }

    public Iterator<V> iterator() {
        return new ABB_Iterador();
    }

}
