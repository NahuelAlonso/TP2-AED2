package aed.tp2;

import java.util.ArrayList;

import aed.tp2.Estructuras.Diccionario;
import aed.tp2.Estructuras.HeapsUsuarios;

public class AirMiles implements SistemaAirMiles {
    
    Diccionario<Integer, Usuario> usuarios;
    int idElMásAcumulador, idElMásCanjeador;
    double millasAcumuladasMáximas, millasCanjeadasMáximas;
    Diccionario<String, Promo> promociones;
    HeapsUsuarios heap;

    public AirMiles() {//O(1)
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void registrarUsuario(int id) {//O(logU) promedio y O(n)peor
        Usuario usuario = new Usuario();
        if(usuarios.tamaño() == 0){//Cambian "los más" solo si antes no había nadie
            nuevoMásAcumulador(usuario, id);
            nuevoMásCanjeador(usuario, id);
        }
        usuarios.definir(id, usuario);
    }

    public void registrarUsuarioConReferidor(int idReferido, int idReferidor) {//O(logU) promedio y O(n)peor
        Usuario referidor = usuarios.obtener(idReferidor);
        Usuario referido = new Usuario(500);
        usuarios.definir(idReferido, referido);
        referidor.agregarReferencia(idReferido);
        referido.agregarReferencia(idReferidor);
        verificarSiEsElMásAcumulador(referidor, idReferidor);//Sé que el referido no puede ser, ya que tiene menos o igual al referidor
        //No hay canjes, no puede cambiar "el más canjeador"
    }

    private void verificarSiEsElMásAcumulador(Usuario usuario, int id){
        if(usuario.millasAcumuladas() > millasAcumuladasMáximas)
            nuevoMásAcumulador(usuario, id);
    }

    private void nuevoMásAcumulador(Usuario usuario, int id){
        idElMásAcumulador = id;
        millasAcumuladasMáximas = usuario.millasAcumuladas();
    }

    public void acumularMillas(int id, double distancia, String nombrePromo) {//O(logU + L) promedio y O(n + L)peor
        Promo promoción = promociones.obtener(nombrePromo);
        Usuario usuario = usuarios.obtener(id);
        usuario.agregarAcumular(nombrePromo, promoción, distancia);
        verificarSiEsElMásAcumulador(usuario, id);
        //No hay canjes, no puede cambiar "el más canjeador"
    }

    public void canjearMillas(int id, double cantidad) {//O(logU) promedio y O(n)peor
        Usuario usuario = usuarios.obtener(id);
        usuario.agregarCanje(cantidad);
        verificarSiEsElMásCanjeador(usuario, id);
        //No hay acumulaciones, no puede cambiar "el más acumulador"
    }

    private void verificarSiEsElMásCanjeador(Usuario usuario, int id){
        if(usuario.millasCanjeadas() > millasCanjeadasMáximas)
            nuevoMásCanjeador(usuario, id);
    }

    private void nuevoMásCanjeador(Usuario usuario, int id){
        idElMásCanjeador = id;
        millasCanjeadasMáximas = usuario.millasCanjeadas();
    }

    public void transferirMillas(int idOrigen, int idDestino, double cantidad) {//O(logU) promedio y O(n)peor
        Usuario usuarioOrigen = usuarios.obtener(idOrigen);
        Usuario usuarioDestino = usuarios.obtener(idDestino);
        usuarioDestino.agregarTransferencia(cantidad, idOrigen);//Le suman
        usuarioOrigen.agregarTransferencia(-cantidad, idDestino);//Le restan
        verificarSiEsElMásAcumulador(usuarioDestino, idDestino);
        verificarSiEsElMásCanjeador(usuarioOrigen, idOrigen);//TODO:Es necesario?? Ver especificación
    }

    public double consultarSaldo(int id) {//O(logU) promedio y O(n)peor
        return usuarios.obtener(id).saldo();
    }

    public String consultarCategoria(int id) {//O(logU) promedio y O(n)peor
       return usuarios.obtener(id).categoría();
    }

    
    public int[] rankingMillas(int n) {//O(nlogU)
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void iniciarPromocion(String nombre, double factor) {//O(L)
        promociones.definir(nombre, new Promo(factor));
    }

    public void iniciarPromocionConTope(String nombre, double factor, double tope) {//O(L)
        promociones.definir(nombre, new Promo(factor, tope));
    }

    public void finalizarPromocion(String nombre) {//O(L)
        promociones.borrar(nombre);
    }

    public int elMasAcumulador() {//O(1)
        return idElMásAcumulador;
    }

    public int elMasCanjeador() {//O(1)
        return idElMásCanjeador;
    }

    public int[][] paresTransferidoresExclusivos() {//O(U)
        //Recorrer el diccionario
        //Ver para cada usuario si es transferidor exc
        //Si es de id menor a su transferidor, lo muestr
        throw new UnsupportedOperationException("Sin implementar");
    }
}
