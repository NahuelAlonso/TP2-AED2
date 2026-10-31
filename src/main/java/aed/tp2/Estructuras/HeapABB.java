package aed.tp2.Estructuras;
import java.util.Comparator;


public class HeapABB<T> {
    private ABB<Integer, HandleHeapsAbb> heapAbb;
    private Comparator<T> orden;

    public HeapABB(Comparator<T> orden){
        this.heapAbb = new ABB<Integer, HandleHeapsAbb>();
        this.orden = orden;
    }

    public class HandleHeapsAbb implements Handle<T> {
        private Integer indice;
        private T val;

        private HandleHeapsAbb(T valor, Integer indice){
            this.val = valor;
            this.indice = indice;
        }

        //Devuelve el valor del elemento
        public T valor(){    //O(1)
            return val;
        }

        //Elimina al elemento de la colección
        public void eliminar(){    // O(log U)
            if (indice >= 0 && indice < heapAbb.cardinal){
                eliminarPos(indice);
            }
        }

        //Modifica al elemento de la colección y su orden (de ser necesario)
        public void modificar(T v){   // O(log U)
            this.val = v;
            acomodar(indice);
        }

        //Devuelve si el valor que representa el Handle es equivalente al que representa otro.
        public boolean equals(Handle<T> u){
            return u != null && this.val.equals(u.valor());
        }
    }

    public int obtenerPosicion(HandleHeapsAbb handle) {//O(n) Devuelve la posicion del elemento en el arreglo del heap, o -1 si no esta
        return handle.indice;
    }

    public int tamaño() {  //O(1)
        return heapAbb.cardinal;
    }

    public HandleHeapsAbb agregar(T u) { //O(log U) Agregar al usuario al heap y guardarle su handle
        HandleHeapsAbb h = new HandleHeapsAbb(u, heapAbb.cardinal);
        heapAbb.definir(heapAbb.cardinal, h);
        subir(h.indice);
        return h;
    }

    private int comparador (int i, int j){
        return orden.compare(heapAbb.obtener(i).valor(), heapAbb.obtener(j).valor());
    }

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
            heapAbb.obtener(ultimo).indice = -1;
            heapAbb.borrar(ultimo);
        }
        acomodar(i);
    }
    
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
        max.indice = -1;
        if (tamaño()>0){
            bajar(0);
        } 
        return res;
    }
}
