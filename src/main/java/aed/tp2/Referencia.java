package aed.tp2;

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
}
