package aed.tp2;

public interface Secuencia<T> {

    public int longitud();
    public void agregar(T elem);//O(1)
    public T obtener(int indice);
    public void eliminar(int indice);
    public void modificarPosicion(int indice, T valor);

}
