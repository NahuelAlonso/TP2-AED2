package aed.tp2.Estructuras;

import java.util.ArrayList;
import aed.tp2.Usuario;

public class HeapsUsuarios {
    private ArrayList<HandleHeaps> heap;


    public HeapsUsuarios(){
        this.heap = new ArrayList<>();
    }
    
    public class HandleHeaps implements Handle<Usuario> {
        private int posicion; //-1 si no esta en el heap
        private Usuario usuario;

        private HandleHeaps(int pos, Usuario usuario){
            this.posicion = pos;
            this.usuario = usuario;
        }

    
        //Devuelve el valor del elemento
        public Usuario valor(){    //O(1)
            return usuario;
        }
    
        
        //Elimina al elemento de la colección
        public void eliminar(){    // O(log U)
            if (posicion >= 0 && posicion < heap.size()){
                eliminarPos(posicion);
            }
        }   

 
        //Modifica al elemento de la colección y su orden (de ser necesario)
        public void modificar(Usuario v){   // O(log U)
            this.usuario = v;
            acomodar(posicion);
        }

      
        //Devuelve si el valor que representa el Handle es equivalente al que representa otro.
        public boolean equals(Handle<Usuario> u){
            return u != null && this.usuario.obtenerId() == u.valor().obtenerId();
        }
    
    }
    
    private HandleHeaps obtenerHandle(Usuario u) { //O(1) obtiene el handle guardado en el usuario
        return u.handle();
    }

    
    private double saldo(int i) {  //O(1) Devuelve el saldo del usuario en la pos i
        return heap.get(i).usuario.obtenerSaldo(); 
    }

    public int tamaño() {  //O(1)
        return heap.size(); 
    }

    public Handle<Usuario> agregar(Usuario u) { //O(log U) Agregar al usuario al heap y guardarle su handle
        HandleHeaps h = new HandleHeaps(heap.size(), u);
        heap.add(h);
        u.setHandle(h);
        subir(h.posicion);
        return h;
    }


    public void actualizar (Usuario u){ //O(log U) Actualiza la posicion del usuario con el saldo cambiado
        HandleHeaps h = obtenerHandle(u);
        acomodar(h.posicion);
    }
    private void acomodar(int i) {
        int padre = (i-1)/2;
        if (i > 0 && saldo(i)>saldo(padre)){
            subir(i);
        }else{
            bajar(i);
        }
    }


    private void eliminarPos(int i) {
        int ultimo = heap.size()-1;
        if (i != ultimo ){
            intercambiar(i, ultimo);
            heap.get(ultimo).posicion = -1;
            heap.remove(ultimo);
        }
        acomodar(i);
    }
    public void eliminar (Usuario u){ // O(log U) Elimina el usuario del heap y elimina su handle
        HandleHeaps h = obtenerHandle(u);
        eliminarPos(h.posicion);
        u.setHandle(null);
    }

    private void subir(int i) { //O(log U) En caso de poder, sube al usuario en el heap hasta llegar a la posicion adecuada
        int padre = (i-1)/2;
        while (i>0 && saldo(i) > saldo(padre)){
            padre = (i-1)/2;
            intercambiar(i, padre);
            i = padre;
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
            if (izq < tamaño && saldo(izq)>saldo(der) && saldo(izq)>saldo(mayor)){
                mayor = izq;
            } 
            if (der < tamaño && saldo(der)>saldo(izq) && saldo(der)>saldo(mayor)){
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
    

    public Usuario maximo(){//O(1) obtiene el maximo y lo devuelve
        Usuario res = heap.get(0).usuario;
        eliminar(res);
        return res;
    }


}
