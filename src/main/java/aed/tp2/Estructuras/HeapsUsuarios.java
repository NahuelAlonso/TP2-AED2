package aed.tp2.Estructuras;

import java.util.ArrayList;
import java.util.List;

import aed.tp2.Usuario;

public class HeapsUsuarios {
    private ArrayList<HandleHeaps> heap;

    public ArrayList<HandleHeaps> crearHeap(){
        throw new UnsupportedOperationException("Sin implementar");
    }

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

        /**
         * Devuelve el valor del elemento
         * 
         */
        public Usuario valor(){
            return usuario;
        }
    
        /**
         * Elimina al elemento de la colección
        * 
        */
        public void eliminar(){
            throw new UnsupportedOperationException("Sin implementar");
        }

        /**
        * Modifica al elemento de la colección y su orden (de ser necesario)
        * 
        */
        public void modificar(Usuario v){
            throw new UnsupportedOperationException("Sin implementar");
        }

        /**
        * Devuelve si el valor que representa el Handle es equivalente al que representa otro.
        * 
        */
        public boolean equals(Handle<Usuario> u){
            throw new UnsupportedOperationException("Sin implementar");
        }
    
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

    public List<Integer> ranking(int n){
        throw new UnsupportedOperationException("Sin implementar");

    }

    private void eliminar(int i) {
        throw new UnsupportedOperationException("Sin implementar");
    }
    private int subir(int i) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    private void bajar(int i) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    private void intercambiar(int i, int j) {
        throw new UnsupportedOperationException("Sin implementar");

    }
    


}
