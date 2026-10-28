package aed.tp2;

public class Promo implements InterfazPromo {
    
    private double factor;
    private double tope;
    private boolean tieneTope;

    public Promo(){//Sólo se debería usar para la promo default //O(1)
        factor = 1;
        tope = 0;
        tieneTope = false;
    }

    public Promo(double factor){//O(1)
        this.factor = factor;
        tope = 0;
        tieneTope = false;
    }

    public Promo(double factor, double tope){//O(1)
        this.factor = factor;
        this.tope = tope;
        tieneTope = true;
    }

    public double factor(){//O(1)
        return factor;
    }
    
    public double tope(){//O(1)
        return tope;
    }

    public boolean tieneTope(){//O(1)
        return tieneTope;
    }

    public void consumirMillas(double cantidad){//O(1)
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
