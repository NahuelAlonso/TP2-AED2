package aed.tp2;

interface Diccionario<K, V> {
    public Diccionario<K,V> diccionarioVacío();
    public boolean está(V v);
    public void definir(K k, V v);
    public void definirRápido(K k, V v);//Toma como req que no exista la clave
    public V obtener(K k);
    public void borrar(K k);
    public int tamaño();
    public boolean igualdad(Diccionario<K, V> dict);
}

interface InterfaceUsuario {
    public void agregarTransferencia(double millas, int idReferido);
    public void agregarReferencia(int id);
    public void agregarAcumular(String nombrePromo, Promo promocion, double distancia);
    public void agregarCanje(double millas);
    public double millasAcumuladas();
    public double millasCanjeadas();
    public double saldo();
    public String categoría();
    
}

interface InterfacePromo {

    
}