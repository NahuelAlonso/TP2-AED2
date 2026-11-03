package aed.tp2;

import org.junit.jupiter.api.Test;

import aed.tp2.Estructuras.Heap;
import aed.tp2.Estructuras.Trie;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class HeapTest {
    private Heap<Integer> heapInt;
    private Heap<Usuario> heapUsu;

    private Integer[] heapIntAVector(Heap<Integer> heap){
        ArrayList<Integer> arr = new ArrayList<>();
        for(int i = 0; i < heap.tamaño(); i++)
            arr.add(heap.desencolar());
        return arr.toArray(new Integer[0]);
    }
    @BeforeEach
    void setUp() {
        heapInt = new Heap<Integer>((a,b) -> Integer.compare(a, b));//Ojo! Quizá está al revés!
        heapUsu = new Heap<Usuario>((a,b) -> Double.compare(a.obtenerSaldo(), b.obtenerSaldo()));
    }

    @Test
    void enterosInsertadosDesordenados() {
        int[] array = {5,1,7,2,6,0,3,8,9,4};
        Integer[] rta = new Integer[10];

        for (int i = 0; i < array.length; i++){
            rta[i] = i;
            heapInt.encolar(array[i]);
        }

        assertArrayEquals(rta, heapIntAVector(heapInt));
    }

    @Test
    void muchasVecesElMismoValor() {

        heapInt.encolar(5);
        heapInt.encolar(5);
        heapInt.encolar(3);
        heapInt.encolar(5);
        heapInt.encolar(4);

        assertEquals(5, heapInt.desencolar());
        assertEquals(5, heapInt.desencolar());
        assertEquals(5, heapInt.desencolar());
        assertEquals(4, heapInt.desencolar());
        assertEquals(3, heapInt.desencolar());
    }

    @Test
    void borrarUnElemento() {
        int[] array = {5,1,-1,7,2,6,0,11,3,8,9,4};
        Integer[] rta = new Integer[10];

        for (int i = 0; i < array.length; i++){
            rta[i] = i;
            heapInt.encolar(array[i]);
        }
        heapInt.borrar(-1);
        heapInt.borrar(11);

        assertArrayEquals(rta, heapIntAVector(heapInt));
    }

    @Test
    void usuariosBásico() {
        int[] array = {5,1,7,2,6,0,3,8,9,4};
        int[] rta = new int[10];

        for (int i = 0; i < array.length; i++){
            Usuario usu = new Usuario(i);
            usu.saldo = array[i];
            
            heapUsu.encolar(usu);

            rta[i] = i;
        }
        for(int i = 0; i < 10; i++){
            assertEquals(rta[i], heapUsu.desencolar().obtenerSaldo());
        }
    }
    @Test
    void usuariosConSaldoDuplicado() {
        int[] array = {5,1,5,7,7,2,6,0,3,8,9,4};

        for (int i = 0; i < array.length; i++){
            Usuario usu = new Usuario(i);
            usu.saldo = array[i];
            
            heapUsu.encolar(usu);
        }
        
        assertEquals(7, heapUsu.desencolar().id); // saldo 0
        assertEquals(1, heapUsu.desencolar().id); // saldo 1
        assertEquals(5, heapUsu.desencolar().id); // saldo 2
        assertEquals(8, heapUsu.desencolar().id); // saldo 3
        assertEquals(11, heapUsu.desencolar().id); // saldo 4

        // saldo 5: puede salir cualquiera de estos dos
        int id = heapUsu.desencolar().id;
        assertTrue(id == 0 || id == 2);

        // saldo 5: tiene que salir el otro
        id = heapUsu.desencolar().id;
        assertTrue(id == 0 || id == 2);

        assertEquals(6, heapUsu.desencolar().id); // saldo 6

        // saldo 7: puede salir cualquiera de estos dos
        id = heapUsu.desencolar().id;
        assertTrue(id == 3 || id == 4);

        // saldo 7: tiene que salir el otro
        id = heapUsu.desencolar().id;
        assertTrue(id == 3 || id == 4);

        assertEquals(9, heapUsu.desencolar().id);  // saldo 8
        assertEquals(10, heapUsu.desencolar().id); // saldo 9
    }

    @Test
    void borroUsuarioConSaldoDuplicado() {
        int[] array = {1,5,7,7,2,6,0,3,8,9,4};

        for (int i = 0; i < array.length; i++){
            Usuario usu = new Usuario(i+1);
            usu.saldo = array[i];
            
            heapUsu.encolar(usu);
        }
        
        assertEquals(7, heapUsu.desencolar().id); // saldo 0
        assertEquals(1, heapUsu.desencolar().id); // saldo 1
        assertEquals(5, heapUsu.desencolar().id); // saldo 2
        assertEquals(8, heapUsu.desencolar().id); // saldo 3
        assertEquals(11, heapUsu.desencolar().id); // saldo 4
        assertEquals(2, heapUsu.desencolar().id); // saldo 4

        assertEquals(6, heapUsu.desencolar().id); // saldo 6

        // saldo 7: puede salir cualquiera de estos dos
        int id = heapUsu.desencolar().id;
        assertTrue(id == 3 || id == 4);

        // saldo 7: tiene que salir el otro
        id = heapUsu.desencolar().id;
        assertTrue(id == 3 || id == 4);

        assertEquals(9, heapUsu.desencolar().id);  // saldo 8
        assertEquals(10, heapUsu.desencolar().id); // saldo 9
    }
}
