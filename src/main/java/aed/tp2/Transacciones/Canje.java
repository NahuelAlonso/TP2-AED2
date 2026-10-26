package aed.tp2.Transacciones;

public class Canje implements Transacción{
    double precioEnMillas;

    public Canje(double precioEnMillas){
        this.precioEnMillas = precioEnMillas;
    }

    public double obtenerMillas(){
        return precioEnMillas;
    }
    
    public String toString(){
        return Double.toString(precioEnMillas);
    }
}
