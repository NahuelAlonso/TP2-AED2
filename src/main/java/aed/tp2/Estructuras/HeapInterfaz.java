package aed.tp2.Estructuras;

/**
 * HeapInterfaz
 */
public interface HeapInterfaz<T> {

    public void añadir(T t);//O(logU)//Reemplazar add
    public T máximo();//O(1) //Devuelve y quita el máximo //Reemplazar poll
    public void borrar(T t);//O(logU) //Reemplazar remove
    
}
