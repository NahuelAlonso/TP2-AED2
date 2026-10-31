package aed.tp2.Estructuras;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T>{
    private ArrayList<HandleHeaps> heap;
    private Comparator<T> orden;

    public Heap(Comparator<T> orden){
        this.heap = new ArrayList<>();
        this.orden = orden;
    }


    public class HandleHeaps implements Handle<T> {
        private int posicion; //-1 si no esta en el heap
        private T valor;

        private HandleHeaps(int pos, T valor){
            this.posicion = pos;
            this.valor = valor;
        }


        //Devuelve el valor del elemento
        public T valor(){    //O(1)
            return valor;
        }


        //Elimina al elemento de la colección
        public void eliminar(){    // O(log U)
            if (posicion >= 0 && posicion < heap.size()){
                eliminarPos(posicion);
            }
        }

        public Integer posicion(){
            return posicion;
        }

        //Modifica al elemento de la colección y su orden (de ser necesario)
        public void modificar(T v){   // O(log U)
            this.valor = v;
            acomodar(posicion);
        }


        //Devuelve si el valor que representa el Handle es equivalente al que representa otro.
        public boolean equals(Handle<T> u){
            return u != null && this.valor.equals(u.valor());
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

    /*public int obtenerPosicion(T e) {//O(n) Devuelve la posicion del elemento en el arreglo del heap, o -1 si no esta
        int res = -1;
        for (int i = 0; i < heap.size(); i++) {
            if (!heap.get(i).valor.equals(e)){
                res = i;
            }
        }
        return res;
    }
*/
    public int obtenerPosicion(HandleHeaps handle) {//O(n) Devuelve la posicion del elemento en el arreglo del heap, o -1 si no esta
            return handle.posicion;
        }

    public int tamaño() {  //O(1)
        return heap.size();
    }

    public HandleHeaps agregar(T u) { //O(log U) Agregar al usuario al heap y guardarle su handle
        HandleHeaps h = new HandleHeaps(heap.size(), u);
        heap.add(h);
        //u.setHandle(h);
        subir(h.posicion);
        return h;
    }

    private int comparador (int i, int j){
        return orden.compare(heap.get(i).valor, heap.get(j).valor);
    }

    // public void actualizar (T u){ //O(log U) Actualiza la posicion del usuario con el saldo cambiado
    //     acomodar(obtenerPosicion(u));
    // }

    public  void acomodar(int i) {
        int padre = (i-1)/2;
        if (comparador(i, padre)>0){
            subir(i);
        }else{
            bajar(i);
        }
    }


    private void eliminarPos(int i) {
        if (i == -1 || i >= heap.size()) return;

        int ultimo = heap.size() - 1;
        if (i == ultimo) {
            heap.remove(ultimo).posicion = -1;
            return;
        }
        
        intercambiar(i, ultimo);
        heap.remove(ultimo).posicion = -1;
        acomodar(i);
    }
    /*
        private void eliminarPos(int i) {
        int ultimo = heap.size()-1;
        if (i != ultimo ){
            intercambiar(i, ultimo);
            heap.get(ultimo).posicion = -1;
            heap.remove(ultimo);
        }
        acomodar(i);
    }
    */

    
    // public void eliminar (T u){ // O(log U) Elimina el usuario del heap y elimina su handle
    //     eliminarPos(obtenerPosicion(u));//TODO: Está bien la complejidad?
    //     //u.setHandle(null);
    // }

    private void subir(int i) { //O(log U) En caso de poder, sube al usuario en el heap hasta llegar a la posicion adecuada
        int padre = (i-1)/2;
        while (i>0 && comparador(i, padre)>0){
            intercambiar(i, padre);
            i = padre;
            padre = (i-1)/2;
            
        }
    }

    private void bajar(int i) { //O(log U) En caso de poder, baja al usuario mediante las ramas del heap hasta que llegue a la posicion adecuada
        int tamaño = heap.size();
        int izq = 2 * i + 1;
        int der = 2 * i + 2;
        int mayor = i;
        boolean listo = false;
        while (!listo) {
            izq = 2 * i + 1;
            der = 2 * i + 2;
            mayor = i;
            if (izq < tamaño && comparador(izq, mayor)>0){
                mayor = izq;
            }
            if (der < tamaño && comparador(der, mayor)>0){
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
        HandleHeaps a = heap.get(i);
        HandleHeaps b = heap.get(j);
        heap.set(i,b);
        b.posicion = i;
        heap.set(j,a);
        a.posicion = j;
    }


    public T maximo(){//O(1) obtiene el maximo y lo devuelve
        
        T res = heap.get(0).valor();
        int ultimo = heap.size() - 1;
        if (ultimo > 0){
            intercambiar(0, ultimo);
        }
        HandleHeaps max = heap.remove(ultimo);
        max.posicion = -1;
        if (!heap.isEmpty()){
            bajar(0);
        } 

        return res;
    }


}
