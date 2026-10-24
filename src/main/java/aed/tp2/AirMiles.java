package aed.tp2;

import aed.tp2.Estructuras.*;

public class AirMiles implements SistemaAirMiles {
    
    Diccionario<Integer, Usuario> usuarios;
    Usuario elMásAcumulador, elMásCanjeador;
    Trie<Promo> promociones;

    public AirMiles() {//O(1)
        usuarios = usuarios.diccionarioVacío();//Se va a tener que modificar
        promociones = new Trie<Promo>();
    }

    public void registrarUsuario(int id) {//O(logU) promedio y O(U)peor
        Usuario usuario = new Usuario();
        if(usuarios.tamaño() == 0){//Cambian "los más" ssi antes no había nadie
            nuevoMásAcumulador(usuario);
            nuevoMásCanjeador(usuario);
        }
        usuarios.definirRápido(id, usuario);
    }

    public void registrarUsuarioConReferidor(int idReferido, int idReferidor) {//O(logU) promedio y O(U)peor
        Usuario referidor = usuarios.obtener(idReferidor);
        Usuario referido = new Usuario(500);
        usuarios.definirRápido(idReferido, referido);
        referidor.agregarReferencia(idReferido);
        referido.agregarReferencia(idReferidor);
        verificarSiEsElMásAcumulador(referidor);//Sé que el referido no puede ser, ya que tiene menos o igual al referidor
        //No hay canjes, no puede cambiar "el más canjeador"
    }

    private void verificarSiEsElMásAcumulador(Usuario usuario){
        if(usuario.obtenerMillasAcumuladas() > elMásAcumulador.obtenerMillasAcumuladas())
            nuevoMásAcumulador(usuario);
    }

    private void nuevoMásAcumulador(Usuario usuario){
        elMásAcumulador = usuario;
    }

    public void acumularMillas(int id, double distancia, String nombrePromo) {//O(logU + L) promedio y O(U + L)peor
        Promo promoción = promociones.obtener(nombrePromo);
        Usuario usuario = usuarios.obtener(id);
        usuario.agregarAcumular(nombrePromo, promoción, distancia);
        verificarSiEsElMásAcumulador(usuario);
        //No hay canjes, no puede cambiar "el más canjeador"
    }

    public void canjearMillas(int id, double cantidad) {//O(logU) promedio y O(U)peor
        Usuario usuario = usuarios.obtener(id);
        usuario.agregarCanje(cantidad);
        verificarSiEsElMásCanjeador(usuario);
        //No hay acumulaciones, no puede cambiar "el más acumulador"
    }

    private void verificarSiEsElMásCanjeador(Usuario usuario){
        if(usuario.obtenerMillasCanjeadas() > elMásCanjeador.obtenerMillasCanjeadas())
            nuevoMásCanjeador(usuario);
    }

    private void nuevoMásCanjeador(Usuario usuario){
        elMásCanjeador = usuario;
    }

    public void transferirMillas(int idOrigen, int idDestino, double cantidad) {//O(logU) promedio y O(U)peor
        Usuario usuarioOrigen = usuarios.obtener(idOrigen);
        Usuario usuarioDestino = usuarios.obtener(idDestino);
        usuarioDestino.agregarTransferencia(cantidad, usuarioOrigen);//Le suman
        usuarioOrigen.agregarTransferencia(-cantidad, usuarioDestino);//Le restan
        verificarSiEsElMásAcumulador(usuarioDestino);
        //No necesito ver si es el más canjeador, porque las transferencias no cuentan para esto
    }

    public double consultarSaldo(int id) {//O(logU) promedio y O(U)peor
        return usuarios.obtener(id).obtenerSaldo();
    }

    public String consultarCategoria(int id) {//O(logU) promedio y O(U)peor
       return usuarios.obtener(id).obtenerCategoría();
    }

    public int[] rankingMillas(int n) {//O(nlogU)//TODO: Falta
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
        //Genera una lista enlazada vacía
        //Recorrer el diccionario
        //Verifico si el usuario de id=id_0 tiene transf exclusivo con id=id_1 con id_0<id_1 (para evitar repe)
        //Si no, sigo, si sí: verifico si el usuario id_1 también tiene transf excl a id_0
        //Si no, sigo, si sí: agrego el array [id_0, id_1] a la lista enlazada
        //Una vez terminado todo, transformo la lista enlazada en un array en O(U)

        //TODO:Este código está mal por varios motivos, tiene varios errores
        ListaEnlazada<int[]> res = new ListaEnlazada<int[]>();
        for (Usuario usu : usuarios) {
            int idActual = usu.obtenerId();
            Usuario transferidorExclusivo = usu.obtenerTransferidorExclusivo();
            int idTransferidorExclusivo = transferidorExclusivo.obtenerId();
            if(transferidorExclusivo != null && idActual < idTransferidorExclusivo)
                if(transferidorExclusivo.obtenerTransferidorExclusivo() != null)
                    res.agregar({idActual, idTransferidorExclusivo});
        }
        return res;
    }


}
