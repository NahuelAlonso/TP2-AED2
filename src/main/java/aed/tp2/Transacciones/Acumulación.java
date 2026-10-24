package aed.tp2.Transacciones;

public class Acumulación implements Transacción{
    String nombrePromo;
    double factorPromo;
    double millasObtenidas;
    double distanciaVolada;
    
    public Acumulación(String nombrePromo, double factorPromo, double millasObtenidas, double distanciaVolada){
        this.nombrePromo = nombrePromo;
        this.factorPromo = factorPromo;
        this.millasObtenidas = millasObtenidas;
        this.distanciaVolada = distanciaVolada;
    }

    public double obtenerMillas(){
        return millasObtenidas;
    }
}
