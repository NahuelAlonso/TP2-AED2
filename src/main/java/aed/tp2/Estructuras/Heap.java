package aed.tp2.Estructuras;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T>{
    private ArrayList<HandleHeaps> vector;
    private Comparator<T> orden;

    public Heap(Comparator<T> orden){
        this.vector = new ArrayList<>(); //Heap
        this.orden = orden; //Cómo se va a ordenar el heap
    }

    public class HandleHeaps implements Handle<T> {
        private int posicion; //-1 si no esta en el heap
        private T valor; // Usuario guardado

        private HandleHeaps(int pos, T valor){
            this.posicion = pos;
            this.valor = valor;
        }


        //Devuelve el valor del elemento
        public T valor(){    //O(1)
            return valor;
        }


        //Elimina al elemento de la colección
        public void eliminar(){    // O(log U) -- ya que elimina la posicion del heap en O(1) y luego reacomoda con su ultimo en O(log U)
            if (posicion >= 0 && posicion < vector.size()){
                eliminarPos(posicion);
            }
        }

        public Integer posicion(){
            return posicion;
        }

        //Modifica al elemento de la colección y su orden (de ser necesario)
        public void modificar(T v){   // O(log U) -- ya que modifica el valor del usuario y lo reacomoda dentro de la lista
            this.valor = v;
            acomodar(posicion);
        }


        //Devuelve si el valor que representa el Handle es equivalente al que representa otro.
        public boolean equals(Handle<T> u){ //O(1)
            return u != null && this.valor.equals(u.valor()); 
        }

    }

    public int obtenerPosicion(HandleHeaps handle) {//O(1) Devuelve la posicion del elemento en el arreglo del heap, o -1 si no esta
            return handle.posicion;
        }

    public int tamaño() {  //O(1)
        return vector.size();
    }

    public HandleHeaps agregar(T u) { //O(log U) Agrega al usuario al heap y lo acomoda
        HandleHeaps h = new HandleHeaps(vector.size(), u);
        vector.add(h);
        //u.setHandle(h);
        subir(h.posicion);
        return h;
    }

    private int comparador (int i, int j){ //O(1) funcion que utiliza el orden pasado
        return orden.compare(vector.get(i).valor, vector.get(j).valor);
    }

    // public void actualizar (T u){ //O(log U) Actualiza la posicion del usuario con el saldo cambiado
    //     acomodar(obtenerPosicion(u));
    // }

    public  void acomodar(int i) {//O(log U) ya que intenta subir o bajar el elemento en la posicion i
        int padre = (i-1)/2;
        if (comparador(i, padre)>0){
            subir(i);
        }else{
            bajar(i);
        }
    }


    public void eliminarPos(int i) { //O(log U) elimina el usuario en la pos i y lo intercambia con el ultimo para luego acomodarlo
        if (i == -1 || i >= vector.size()) return;

        int ultimo = vector.size() - 1;
        if (i == ultimo) {
            vector.remove(ultimo).posicion = -1;
            return;
        }
        
        intercambiar(i, ultimo);
        vector.remove(ultimo).posicion = -1;
        acomodar(i);
    }

    private void subir(int i) { //O(log U) En caso de poder, sube al usuario en el heap hasta llegar a la posicion adecuada recorriendo la lista segun su orden
        int padre = (i-1)/2;
        while (i>0 && comparador(i, padre)>=0){
            intercambiar(i, padre);
            i = padre;
            padre = (i-1)/2;
            
        }
    }

    private void bajar(int i) { //O(log U) En caso de poder, baja al usuario mediante las ramas del heap hasta que llegue a la posicion adecuada recorriendo la lista segun su orden
        int tamaño = vector.size();
        int izq = 2 * i + 1;
        int der = 2 * i + 2;
        int mayor = i;
        boolean listo = false;
        while (!listo) {
            izq = 2 * i + 1;
            der = 2 * i + 2;
            mayor = i;
            if (izq < tamaño && comparador(izq, mayor)>=0){
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
        HandleHeaps a = vector.get(i);
        HandleHeaps b = vector.get(j);
        vector.set(i,b);
        b.posicion = i;
        vector.set(j,a);
        a.posicion = j;
    }


    public T maximo(){//O(log U) obtiene el maximo y lo devuelve en O(1) para luego eliminarlo de la lista y acomodar el ultimo en O(log U) 
        
        T res = vector.get(0).valor();
        int ultimo = vector.size() - 1;
        if (ultimo > 0){
            intercambiar(0, ultimo);
        }
        HandleHeaps max = vector.remove(ultimo);
        max.posicion = -1;
        if (!vector.isEmpty()){
            bajar(0);
        } 

        return res;
    }


}
