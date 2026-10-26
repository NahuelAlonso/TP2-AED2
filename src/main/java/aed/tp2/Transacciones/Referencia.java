package aed.tp2.Transacciones;

public class Referencia implements Transacción{
    
    double millas;
    int id;

    public Referencia(double millas, int id){
        this.millas = millas;
        this.id = id;
    }

    public double obtenerMillas(){
        return millas;
    }

    public String toString(){
        return Double.toString(millas);
    }
}
