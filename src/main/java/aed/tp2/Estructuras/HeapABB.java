package aed.tp2.Estructuras;

import java.util.ArrayList;
import java.util.Comparator;

import aed.tp2.Usuario;

public class HeapABB<T> {
    private ABB<Integer, HandleHeapsAbb> heapAbb;
    private Comparator<T> orden;

    public HeapABB(Comparator<T> orden){
        this.heapAbb = new ABB<Integer, HandleHeapsAbb>();
        this.orden = orden;
    }

    private class Nodo {
        Nodo padre;
        Nodo izquierda;
        Nodo derecha;
        Integer indice;
        T val;

        public Nodo(Integer indice, T val, Nodo padre){
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

    public class HandleHeapsAbb implements Handle<T> {
        private Nodo nodo;

        private HandleHeapsAbb(T valor){
            this.nodo.val = valor;
        }


        //Devuelve el valor del elemento
        public T valor(){    //O(1)
            return nodo.val;
        }


        //Elimina al elemento de la colección
        public void eliminar(){    // O(log U)
            if (nodo.indice >= 0 && nodo.indice < heapAbb.cardinal){
                eliminarPos(nodo.indice);
            }
        }


        //Modifica al elemento de la colección y su orden (de ser necesario)
        public void modificar(T v){   // O(log U)
            this.nodo.val = v;
            acomodar(nodo.indice);
        }


        //Devuelve si el valor que representa el Handle es equivalente al que representa otro.
        public boolean equals(Handle<T> u){
            return u != null && this.nodo.val.equals(u.valor());
        }

    }

    // private HandleHeaps obtenerHandle(Usuario u) { //O(1) obtiene el handle guardado en el usuario
    //     return u.handle();
    // }
    // private T saldo(int i) {  //O(1) Devuelve el saldo del usuario en la pos i
    //     return heap.get(i).valor();
    // }

    // 

    // public int obtenerPosicion(Usuario u){
    //     return u.handle().posicion;
    // }

    public int obtenerPosicion(HandleHeapsAbb handle) {//O(n) Devuelve la posicion del elemento en el arreglo del heap, o -1 si no esta
        return handle.nodo.indice;
    }

    public int tamaño() {  //O(1)
        return heapAbb.cardinal;
    }

    public HandleHeapsAbb agregar(T u) { //O(log U) Agregar al usuario al heap y guardarle su handle
        HandleHeapsAbb h = new HandleHeapsAbb(u);
        heapAbb.definir(heapAbb.cardinal, h);
        //u.setHandle(h);
        subir(h.nodo.indice);
        return h;
    }

    private int comparador (int i, int j){
        return orden.compare(heapAbb.obtener(i).valor(), heapAbb.obtener(j).valor());
    }

    // public void actualizar (T u){ //O(log U) Actualiza la posicion del usuario con el saldo cambiado
    //     acomodar(obtenerPosicion(u));
    // }

    private void acomodar(int i) {
        int padre = (i-1)/2;
        if (comparador(i, padre)>0){
            subir(i);
        }else{
            bajar(i);
        }
    }


    private void eliminarPos(int i) {
        int ultimo = tamaño()-1;
        if (i != ultimo ){
            intercambiar(i, ultimo);
            heapAbb.obtener(ultimo).nodo.indice = -1;
            heapAbb.borrar(ultimo);
        }
        acomodar(i);
    }
    
    // public void eliminar (T u){ // O(log U) Elimina el usuario del heap y elimina su handle
    //     eliminarPos(obtenerPosicion(u));
    //     //u.setHandle(null);
    // }

    private void subir(int i) { //O(log U) En caso de poder, sube al usuario en el heap hasta llegar a la posicion adecuada
        int padre = (i-1)/2;
        while (i>0 && comparador(i, padre)>0){
            padre = (i-1)/2;
            intercambiar(i, padre);
            i = padre;
        }
    }

    private void bajar(int i) { //O(log U) En caso de poder, baja al usuario mediante las ramas del heap hasta que llegue a la posicion adecuada
        int tamaño = tamaño();
        int izq = 2 * i + 1;
        int der = 2 * i + 2;
        int mayor = i;
        boolean listo = false;
        while (!listo) {
            izq = 2 * i + 1;
            der = 2 * i + 2;
            mayor = i;
            if (izq < tamaño && comparador(izq, der)>0 && comparador(izq, mayor)>0){
                mayor = izq;
            }
            if (der < tamaño && comparador(der, izq)>0 && comparador(der, mayor)>0){
                mayor = der;
            }
            if (mayor == i){
                listo = true;
            }else{
                intercambiar(i, mayor);
                i = mayor;
            }
        }
    }

    private void intercambiar(int i, int j) {   //O(1) intercambia las posiciones de los Handles en las posiciones i y j
        HandleHeapsAbb a = heapAbb.obtener(i);
        HandleHeapsAbb b = heapAbb.obtener(j);
        T valor1 = a.valor();
        T valor2 = b.valor();
        a.modificar(valor2);
        b.modificar(valor1);
    }


    public T maximo(){//O(1) obtiene el maximo y lo devuelve
        
        T res = heapAbb.obtener(0).valor();
        int ultimo = heapAbb.cardinal - 1;
        if (ultimo > 0){
            intercambiar(0, ultimo);
        }
        HandleHeapsAbb max = heapAbb.obtener(ultimo);
        heapAbb.borrar(ultimo);
        max.nodo.indice = -1;
        if (tamaño()>0){
            bajar(0);
        } 
        return res;
    }
}
