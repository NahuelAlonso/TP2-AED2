package aed.tp2.Estructuras;

public interface Diccionario<K, V> {
    public void definir(K k, V v);
    public V obtener(K k);
    public void borrar(K k);
    public int tamaño();
}