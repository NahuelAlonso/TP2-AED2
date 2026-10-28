package aed.tp2;

public class Promo implements InterfazPromo {
    
    private double factor;
    private double tope;
    private boolean tieneTope;

    public Promo(){//Sólo se debería usar para la default
        factor = 1;
        tope = 0;
        tieneTope = false;
    }

    public Promo(double factor){
        this.factor = factor;
        tope = 0;
        tieneTope = false;
    }

    public Promo(double factor, double tope){
        this.factor = factor;
        this.tope = tope;
        tieneTope = true;
    }

    public double factor(){
        return factor;
    }
    
    public double tope(){
        return tope;
    }

    public boolean tieneTope(){
        return tieneTope;
    }

    public void consumirMillas(double cantidad){
        if (this.tieneTope){
            tope -= cantidad;
            if (this.tope <= 0)
                finalizar();
        }
    }

    public void finalizar(){
        this.tope = 0;
        factor = 1;
        tieneTope = false;
    }
}
