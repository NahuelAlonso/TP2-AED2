package aed.tp2.Estructuras;

public interface ColaPrioridad<T> {
    
    public void encolar(T t); //Encola un elemnto en O(log U)
    public void borrar(T t); //Toma un elemento y lo elimina en O(logU)
    public T desencolar(); //Elimina y devuelve el de mayor prioridad en O(log U)
    public T máximo(); //Devuelve el máximo sin eliminarlo O(1)
    public int tamaño();

}
