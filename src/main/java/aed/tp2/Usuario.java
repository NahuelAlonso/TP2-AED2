package aed.tp2;

import aed.tp2.Estructuras.ListaEnlazada;
import aed.tp2.Transacciones.Acumulación;
import aed.tp2.Transacciones.Canje;
import aed.tp2.Transacciones.Referencia;
import aed.tp2.Transacciones.Transacción;
import aed.tp2.Transacciones.Transferencia;

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


    public Usuario(){
        alMenosUnaTransferencia = false;
    }
    public Usuario(int millas){
        alMenosUnaTransferencia = false;
    }

    public void agregarTransferencia(double millas, Usuario usuario){//O(1)
        if(!alMenosUnaTransferencia)//Si es la primera transfe, sí o sí va a tener transfExclusivo
            transferidorExclusivo = usuario;

        //Si ya transfirió alguna vez pero el  usuario es diferente al transfExclusivo, entonces no tiene transfExclusivo
        if(alMenosUnaTransferencia && transferidorExclusivo != usuario)
            transferidorExclusivo = null;

        Transferencia transferencia = new Transferencia(millas, usuario.obtenerId());
        transferencias.agregar(transferencia);
        
        nuevaTransacción(transferencia);
        alMenosUnaTransferencia = true;
    }

    public void agregarReferencia(int id){//O(1)
        Referencia referencia = new Referencia(500, id);
        referencias.agregar(referencia);
        nuevaTransacción(referencia);
    }

    public void agregarAcumular(String nombrePromo, Promo promocion, double distancia){//O(1)
        //TODO: Falta, calcular promo y factorCat, restar del tope si es con tope
        
    }

    public void agregarCanje(double millas){//O(1)
        millasCanjeadas += millas;
        millas = -millas;

        Canje canje = new Canje(millas);
        canjes.agregar(canje);
        nuevaTransacción(canje);
    }

    private void nuevaTransacción(Transacción tr){//Todas las transacciones deben sumarse al saldo y a mA si son >0
        double millas = tr.obtenerMillas();
        if(millas > 0)
            millasAcumuladas += millas;

        saldo += millas;
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
        switch (factorCategoría) {
            case 1: return "Bronce";
            case 1.5: return "Plata";
            case 2: return "Oro";
            case 0: return "Platino";
        }
    }
    public int obtenerId(){
        return id;
    }
}
