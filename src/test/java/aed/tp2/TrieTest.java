package aed.tp2;

import org.junit.jupiter.api.Test;

import aed.tp2.Estructuras.Trie;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class TrieTest {
    private Trie<Integer> trie;

    @BeforeEach
    void setUp() {
        trie = new Trie<>();
    }

    @Test
    void crearTrieVacio() {
        assertNotNull(trie);
        // Asumiendo que tengas un atributo o método tamaño, o puedes verificar inserciones directamente
        assertEquals(0, trie.tamaño()); 
    }

    @Test
    void definirYSolaparPalabras() {
        // Insertamos palabras básicas
        trie.definir("casa", 10);
        trie.definir("casamiento", 20);
        trie.definir("carro", 30);
        assertEquals(3, trie.tamaño());

        // Caso específico: "promo" y "promocion"
        trie.definir("promo", 100);
        trie.definir("promocion", 200);
        assertEquals(5, trie.tamaño());
    }

    @Test
    void borrarPalabraLargaProtegiendoCorta() {
        trie.definir("promo", 100);
        trie.definir("promocion", 200);
        assertEquals(2, trie.tamaño());

        // Borramos la palabra larga ("promocion")
        trie.borrar("promocion");
        
        // El tamaño debe disminuir a 1 (sobrevive "promo")
        assertEquals(1, trie.tamaño());

        // Volvemos a definir "promo" para chequear que no se rompió su nodo
        trie.definir("promo", 100);
        assertEquals(1, trie.tamaño(), "El tamaño no debería cambiar si 'promo' ya existía intacta");
    }

    @Test
    void borrarPalabraInexistente() {
        trie.definir("promo", 100);
        assertEquals(1, trie.tamaño());

        // Intentar borrar algo que no está no debería alterar el trie
        trie.borrar("inexistente");
        assertEquals(1, trie.tamaño());
    }
}
