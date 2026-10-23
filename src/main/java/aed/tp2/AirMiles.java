package aed.tp2;

public class AirMiles implements SistemaAirMiles {
    /*COMPLETAR*/

    public AirMiles() {//O(1)
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void registrarUsuario(int id) {//O(logU) promedio y O(n)peor
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void registrarUsuarioConReferidor(int id, int ref) {//O(logU) promedio y O(n)peor
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void acumularMillas(int id, double distancia, String nombrePromo) {//O(logU + L) promedio y O(n + L)peor
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void canjearMillas(int id, double cantidad) {//O(logU) promedio y O(n)peor
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void transferirMillas(int origen, int destino, double cantidad) {//O(logU) promedio y O(n)peor
        throw new UnsupportedOperationException("Sin implementar");
    }

    public double consultarSaldo(int id) {//O(logU) promedio y O(n)peor
        throw new UnsupportedOperationException("Sin implementar");
    }

    public String consultarCategoria(int id) {//O(logU) promedio y O(n)peor
       throw new UnsupportedOperationException("Sin implementar");
    }

    public int[] rankingMillas(int n) {//O(nlogn)
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void iniciarPromocion(String nombre, double factor) {//O(L)
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void iniciarPromocionConTope(String nombre, double factor, double tope) {//O(L)
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void finalizarPromocion(String nombre) {//O(L)
        throw new UnsupportedOperationException("Sin implementar");
    }

    public int elMasAcumulador() {//O(1)
        throw new UnsupportedOperationException("Sin implementar");
    }

    public int elMasCanjeador() {//O(1)
        throw new UnsupportedOperationException("Sin implementar");
    }

    public int[][] paresTransferidoresExclusivos() {//O(U)
        throw new UnsupportedOperationException("Sin implementar");
    }
}
