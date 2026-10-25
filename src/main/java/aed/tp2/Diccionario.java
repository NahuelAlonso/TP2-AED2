package aed.tp2;

public interface Diccionario<K, V> {
    public boolean está(V v);
    public void definir(K k, V v);
    public void definirRápido(K k, V v);//Toma como req que no exista la clave
    public V obtener(K k);
    public void borrar(K k);
    public int tamaño();
    public boolean igualdad(Diccionario<K, V> dict);
}