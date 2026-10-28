package aed.tp2;
interface InterfazUsuario {

    public void agregarTransferencia(double millas, Usuario usuario);//O(1)
    public void agregarReferencia(double millas, int id);//O(1)
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
    public double factor();//O(1)
    public double tope();//O(1)  
    public boolean tieneTope();//O(1)
    public void consumirMillas(double cantidad);//O(1)
    public void finalizar();//O(1)
}
