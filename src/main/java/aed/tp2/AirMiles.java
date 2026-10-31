package aed.tp2;

import java.util.*;

import aed.tp2.Estructuras.*;

public class AirMiles implements SistemaAirMiles {
    
    ABB<Integer, Usuario> usuarios;
    Usuario elMásAcumulador, elMásCanjeador;
    Trie<Promo> promociones;
    Heap<Usuario> maxHeapUsuarios;


    public AirMiles() {//O(1)
        usuarios = new ABB<Integer, Usuario>();

        elMásAcumulador = new Usuario(-1);
        elMásCanjeador = new Usuario(-1);

        promociones = new Trie<Promo>();
        promociones.definir("NoPromo", new Promo());

        Comparator<Usuario> comparator = (a,b) -> Double.compare(a.obtenerSaldo(), b.obtenerSaldo());
        maxHeapUsuarios = new Heap<Usuario>(comparator);
    }

    public void registrarUsuario(int id) {//O(logU) promedio y O(U)peor
        Usuario usuario = new Usuario(id);
        if(usuarios.tamaño() == 0){//Cambian "los más" ssi no había nadie antes
            nuevoMásAcumulador(usuario);
            nuevoMásCanjeador(usuario);
        }
        usuarios.definir(id, usuario);
        Heap<Usuario>.HandleHeaps h = maxHeapUsuarios.agregar(usuario);
        usuario.setHandle(h);
        seModificóUsuario(usuario);
        
    }

    public void registrarUsuarioConReferidor(int idReferido, int idReferidor) {//O(logU) promedio y O(U)peor
        Usuario referidor = usuarios.obtener(idReferidor);
        Usuario referido = new Usuario(idReferido);
        usuarios.definir(idReferido, referido);
        referidor.agregarReferencia(500, idReferido);
        referido.agregarReferencia(500, idReferidor);

        Heap<Usuario>.HandleHeaps h = maxHeapUsuarios.agregar(referido);
        referido.setHandle(h);
        seModificóUsuario(referidor);
    }

    public void acumularMillas(int id, double distancia, String nombrePromo) {//O(logU + L) promedio y O(U + L)peor
        Promo promoción = promociones.obtener(nombrePromo);
        Usuario usuario = usuarios.obtener(id);
        usuario.agregarAcumular(nombrePromo, promoción, distancia);
        seModificóUsuario(usuario);
    }

    public void canjearMillas(int id, double cantidad) {//O(logU) promedio y O(U)peor
        Usuario usuario = usuarios.obtener(id);
        usuario.agregarCanje(cantidad);
        seModificóUsuario(usuario);
    }

    public void transferirMillas(int idOrigen, int idDestino, double cantidad) {//O(logU) promedio y O(U)peor
        Usuario usuarioOrigen = usuarios.obtener(idOrigen);
        Usuario usuarioDestino = usuarios.obtener(idDestino);
        usuarioDestino.agregarTransferencia(cantidad, usuarioOrigen);//Le suman
        usuarioOrigen.agregarTransferencia(-cantidad, usuarioDestino);//Le restan
        seModificóUsuario(usuarioDestino);
        seModificóUsuario(usuarioOrigen);
    }

    public double consultarSaldo(int id) {//O(logU) promedio y O(U)peor
        return usuarios.obtener(id).obtenerSaldo();
    }

    public String consultarCategoria(int id) {//O(logU) promedio y O(U)peor
        return usuarios.obtener(id).obtenerCategoría();
    }

    public int[] rankingMillas(int n) {//O(nlogU)
        n = Math.min(n, usuarios.tamaño());
        int[] ids = new int[n];
        Usuario[] usuarios = new Usuario[n];
        for (int i = 0; i < n; i++){
            Usuario usu = maxHeapUsuarios.maximo();
            ids[i] = usu.obtenerId();
            usuarios[i] = usu;
        }
        for (int i = 0; i < n; i++)
            maxHeapUsuarios.agregar(usuarios[i]);
        return ids;
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
        return elMásAcumulador.obtenerId();
    }

    public int elMasCanjeador() {//O(1)
        return elMásCanjeador.obtenerId();
    }

    public int[][] paresTransferidoresExclusivos() {//O(U)
        //Genera una lista enlazada vacía
        //Recorrer el diccionario
        //Verifico si el usuario de id=id_0 tiene transf exclusivo con id=id_1 con id_0<id_1 (para evitar repe)
        //Si no, sigo, si sí: verifico si el usuario id_1 también tiene transf excl a id_0
        //Si no, sigo, si sí: agrego el array [id_0, id_1] a la lista enlazada
        //Una vez terminado todo, transformo la lista enlazada en un array en O(U)

        ListaEnlazada<int[]> listaEnlazada = new ListaEnlazada<int[]>();
        int idActual, idTransferidorExclusivo;
        Usuario transferidorExclusivo;

        for (Usuario usu : usuarios) {
            idActual = usu.obtenerId();
            transferidorExclusivo = usu.obtenerTransferidorExclusivo();
            if(transferidorExclusivo != null){
                idTransferidorExclusivo = transferidorExclusivo.obtenerId();
                if(idActual < idTransferidorExclusivo)
                    if(transferidorExclusivo.obtenerTransferidorExclusivo() != null){//Si tiene un transfExc tiene que ser usu
                        int[] par = {idActual, idTransferidorExclusivo};
                        listaEnlazada.agregar(par);
                    }
            }
        }

        return listaEnlazada.convertirAVector(new int[listaEnlazada.longitud()][]);
    }

    private void seModificóUsuario(Usuario usuario){
        verificarSiEsElMásAcumulador(usuario);
        verificarSiEsElMásCanjeador(usuario);

        int posicion = usuario.handle().posicion();
        maxHeapUsuarios.acomodar(posicion);

        //maxHeapUsuarios.eliminar(usuario);//Debería ser O(logU)
        //maxHeapUsuarios.agregar(usuario);//Debería ser O(logU)
    }

    private void verificarSiEsElMásAcumulador(Usuario usuario){
        if(usuario.obtenerMillasAcumuladas() > elMásAcumulador.obtenerMillasAcumuladas())
            nuevoMásAcumulador(usuario);
    }

    private void nuevoMásAcumulador(Usuario usuario){
        elMásAcumulador = usuario;
    }

        private void verificarSiEsElMásCanjeador(Usuario usuario){
        if(usuario.obtenerMillasCanjeadas() > elMásCanjeador.obtenerMillasCanjeadas())
            nuevoMásCanjeador(usuario);
    }

    private void nuevoMásCanjeador(Usuario usuario){
        elMásCanjeador = usuario;
    }

    public Usuario obtenerUsuario(int id){
        return usuarios.obtener(id);
    }
}
