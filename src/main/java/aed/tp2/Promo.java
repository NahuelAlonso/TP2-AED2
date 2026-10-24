package aed.tp2;

public class Promo implements InterfacePromo {
    private double factor;
    private double tope;
    private boolean tieneTope;

    public Promo(){
        factor =1;
        tope=0;
        tieneTope = false;
    }
    public Promo(double factor){
        this.factor =factor;
        this.tope=0;
        this.tieneTope = false;
    }
    public Promo(double factor, double tope){
        this.factor =factor;
        this.tope=tope;
        this.tieneTope = true;
    }

    public double factor(){
        return this.factor;
    }
    
    public double tope(){
        return this.tope;
    }

    public boolean tieneTope(){
        return this.tieneTope;
    }

    public void consumirMillas(double cantidad){
        if (this.tieneTope){
            this.tope = this.tope-cantidad;
        }
    }
}
