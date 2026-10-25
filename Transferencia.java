package aed.tp2.Transacciones;

public class Transferencia implements Transacción{
    double millas;
    int id;

    public Transferencia(double millas, int id){
        this.id = id;
        this.millas = millas;
    }

    public double obtenerMillas(){
        return millas;
    }
}
