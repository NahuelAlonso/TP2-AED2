package aed.tp2.Estructuras;

public interface Diccionario<K, V> {//TODO: Ver si se usan en cada implementación, en caso de que no, borrar
    public void definir(K k, V v);
    public V obtener(K k);
    public void borrar(K k);
    public int tamaño();
}