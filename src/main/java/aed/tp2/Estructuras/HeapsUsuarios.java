package aed.tp2.Estructuras;

import java.util.ArrayList;
import java.util.List;

import aed.tp2.Usuario;

public class HeapsUsuarios {
    private ArrayList<HandleHeaps> heap;


    public HeapsUsuarios(){
        this.heap = new ArrayList<>();
    }
    
    public class HandleHeaps implements Handle<Usuario> {
        private int posicion;
        private Usuario usuario;

        private HandleHeaps(int pos, Usuario usuario){
            this.posicion = pos;
            this.usuario = usuario;
        }

    
        //Devuelve el valor del elemento
        public Usuario valor(){
            return usuario;
        }
    
        
        //Elimina al elemento de la colección
        public void eliminar(){
            if (posicion >= 0 && posicion < heap.size()){
                eliminarH(posicion);
            }
        }   

 
        //Modifica al elemento de la colección y su orden (de ser necesario)
        public void modificar(Usuario v){
            this.usuario = v;
            acomodar(posicion);
        }

      
        //Devuelve si el valor que representa el Handle es equivalente al que representa otro.
        public boolean equals(Handle<Usuario> u){
            return u != null && this.usuario.obtenerId() == u.valor().obtenerId();
        }
    
    }
    
    
    private double saldo(int i) {
        return heap.get(i).usuario.obtenerSaldo(); 
    }

    public int tamaño() { 
        return heap.size(); 
    }

    public Handle<Usuario> agregar(Usuario u) {
        HandleHeaps h = new HandleHeaps(heap.size(), u);
        heap.add(h);
        subir(h.posicion);
        return h;
    }

    private void acomodar(int i) {
        int padre = (i-1)/2;
        if (i > 0 && saldo(i)>saldo(padre)){
            subir(i);
        }else{
            bajar(i);
        }
    }


    private void eliminarH(int i) {
        int ultimo = heap.size()-1;
        if (i != ultimo ){
            intercambiar(i, ultimo);
            heap.get(ultimo).posicion = -1;
            heap.remove(ultimo);
        }
        acomodar(i);
    }

    private void subir(int i) {
        int padre = (i-1)/2;
        while (i>0 && saldo(i) > saldo(padre)){
            padre = (i-1)/2;
            intercambiar(i, padre);
            i = padre;
        }
    }

    private void bajar(int i) {
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

    private void intercambiar(int i, int j) {
        HandleHeaps a = heap.get(i);
        HandleHeaps b = heap.get(j);
        heap.set(i,b);
        b.posicion = i;
        heap.set(j,a);
        a.posicion = j;
    }
    


}
