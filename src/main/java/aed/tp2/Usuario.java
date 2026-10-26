package aed.tp2;

import aed.tp2.Estructuras.*;
import aed.tp2.Transacciones.*;

public class Usuario implements InterfazUsuario {
    int id;
    double factorCategoría;
    double millasAcumuladas;
    double millasCanjeadas;
    double saldo;
    boolean alMenosUnaTransferencia;//Se puede reemplazar por transferencias.longitud() == 0, parece más prolijo así
    Usuario transferidorExclusivo;
    ListaEnlazada<Transferencia> transferencias;
    ListaEnlazada<Referencia> referencias;
    ListaEnlazada<Acumulación> acumulaciones;
    ListaEnlazada<Canje> canjes;


    public Usuario(int id){
        this.id = id;
        millasAcumuladas = 0;
        millasCanjeadas = 0;
        saldo = 0;
        alMenosUnaTransferencia = false;
        transferidorExclusivo = null;
        transferencias = new ListaEnlazada<Transferencia>();
        referencias = new ListaEnlazada<Referencia>();
        acumulaciones = new ListaEnlazada<Acumulación>();
        canjes = new ListaEnlazada<Canje>();

        actualizarFactorCategoría();//Podría poner factorCat = 1, pero es más escalable de esta forma
    }

    public void agregarTransferencia(double millas, Usuario usuario){//O(1)
        if(!alMenosUnaTransferencia)//Si es la primera transfe, sí o sí va a tener transfExclusivo
            transferidorExclusivo = usuario;

        //Si ya transfirió alguna vez pero el  usuario es diferente al transfExclusivo, entonces no tiene transfExclusivo
        if(alMenosUnaTransferencia && transferidorExclusivo != usuario)
            transferidorExclusivo = null;

        Transferencia transferencia = new Transferencia(millas, usuario.obtenerId());
        transferencias.agregar(transferencia);
        
        boolean sumaCanjeadas = millas < 0;

        nuevaTransacción(transferencia, sumaCanjeadas, false);//Nunca suma a acumuladas, puede sumar a canjeadas si resta
        alMenosUnaTransferencia = true;
    }

    public void agregarReferencia(double millas, int id){//O(1)
        Referencia referencia = new Referencia(millas, id);
        referencias.agregar(referencia);
        nuevaTransacción(referencia, false, true);
    }

    public void agregarAcumular(String nombrePromo, Promo promoción, double distancia){//O(1)
        double millas, millasSinTope;
        double factorPromoción = promoción.factor();
        double tope = promoción.tope();
        Acumulación acumulación;

        millasSinTope = distancia * factorCategoría * factorPromoción;
        promoción.consumirMillas(millasSinTope);
        
        if(promoción.tieneTope() && tope < millasSinTope)
            millas = tope + factorCategoría * (distancia - tope) / (factorPromoción * factorCategoría);
        else
            millas = millasSinTope;
        
        acumulación = new Acumulación(nombrePromo, factorPromoción, millas, distancia);
        acumulaciones.agregar(acumulación);
        nuevaTransacción(acumulación, false, true);
    }

    public void agregarCanje(double millas){//O(1)
        millas = -millas;

        Canje canje = new Canje(millas);
        canjes.agregar(canje);
        nuevaTransacción(canje, true, false);
    }

    private void nuevaTransacción(Transacción tr, boolean sumaCanjeadas, boolean sumaAcumuladas){
        double millas = tr.obtenerMillas();
        if(sumaCanjeadas)
            millasCanjeadas += Math.abs(millas);
        if(sumaAcumuladas)
            millasAcumuladas += millas;
        
        actualizarFactorCategoría();
        saldo += millas;
    }
    
    private void actualizarFactorCategoría(){
        if(millasAcumuladas < 10000)
            factorCategoría = 1;
        else if (millasAcumuladas < 50000)
            factorCategoría = 1.5;
        else if (millasAcumuladas < 1000000)
            factorCategoría = 2;
        else
            factorCategoría = 0;
    }

    public Usuario obtenerTransferidorExclusivo(){//O(1)
        return transferidorExclusivo;//Es null si se transfirió a varios usuarios o si nunca se transfirió
    }
    public double obtenerMillasAcumuladas(){//O(1)
        return millasAcumuladas;
    }
    public double obtenerMillasCanjeadas(){//O(1)
        return  millasCanjeadas;
    }
    public double obtenerSaldo(){//O(1)
        return saldo;
    }
    
    public String obtenerCategoría(){//O(1)
        actualizarFactorCategoría();
        String res = "Error";
        if(factorCategoría == 1)
            res = "Bronce";
        if(factorCategoría == 1.5)
            res = "Plata";
        if(factorCategoría == 2)
            res =  "Oro";
        if(factorCategoría == 0)
            res =  "Platino";

        //System.err.println(factorCategoría + ", " + res );
        return res;
    }
    
    public int obtenerId(){
        return id;
    }

    @Override 
    public String toString(){
        String res = "";
        res += "id: " + id + "\n";
        res += "fC: " + factorCategoría +"\n";
        res += "mA: " + millasAcumuladas + "\n";
        res += "MC: " + millasCanjeadas + "\n";
        res += "saldo: " + saldo + "\n";
        res += "caj: " + canjes.toString() + "\n";
        res += "tra: " + transferencias.toString() + "\n";
        res += "acc: " + acumulaciones.toString() + "\n";
        res += "ref: " + referencias.toString() + "\n";

        return res;
    }
}
