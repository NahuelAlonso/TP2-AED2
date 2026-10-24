package aed.tp2;
interface InterfazUsuario {

    public void agregarTransferencia(double millas, Usuario usuario);//O(1)
    public void agregarReferencia(int id);//O(1)
    public void agregarAcumular(String nombrePromo, Promo promocion, double distancia);//O(1)
    public void agregarCanje(double millas);//O(1)
    public Usuario obtenerTransferidorExclusivo();//O(1)
    public double obtenerMillasAcumuladas();//O(1)
    public double obtenerMillasCanjeadas();//O(1)
    public double obtenerSaldo();//O(1)
    public String obtenerCategoría();//O(1)
    public int obtenerId();//O(1)
    
}

interface InterfazPromo {

    public double obtenerFactor();//O(1)
    public double obtenerTope();//O(1)

}
