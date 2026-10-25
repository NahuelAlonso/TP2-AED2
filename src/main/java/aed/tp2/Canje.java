package aed.tp2;

public class Canje implements Transacción{
    double precioEnMillas;

    public Canje(double precioEnMillas){
        this.precioEnMillas = precioEnMillas;
    }

    public double obtenerMillas(){
        return precioEnMillas;
    }
}
