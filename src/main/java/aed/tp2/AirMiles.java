package aed.tp2;

import java.util.*;

import aed.tp2.Estructuras.*;

public class AirMiles implements SistemaAirMiles {
    
    ABB<Integer, Usuario> usuarios;
    Usuario elMásAcumulador, elMásCanjeador;
    Trie<Promo> promociones;
    PriorityQueue<Usuario> maxHeapUsuarios;


    public AirMiles() {//O(1)
        usuarios = new ABB<Integer, Usuario>();

        elMásAcumulador = new Usuario(-1);//Los test piden que se devuelva -1 en caso de no haber ningún usuario
        elMásCanjeador = new Usuario(-1);

        promociones = new Trie<Promo>();
        promociones.definir("NoPromo", new Promo());

        Comparator<Usuario> comparator = (a,b) -> Double.compare(b.obtenerSaldo(), a.obtenerSaldo());
        maxHeapUsuarios = new PriorityQueue<>(comparator);
    }

    public void registrarUsuario(int id) {//O(logU) promedio y O(U)peor
        Usuario usuario = new Usuario(id);
        if(usuarios.tamaño() == 0){//Cambian "los más" si y sólo si no había nadie antes
            nuevoMásAcumulador(usuario);
            nuevoMásCanjeador(usuario);
        }
        usuarios.definir(id, usuario);//O(logU) promedio y O(U) peor por ser inserción en ABB
        maxHeapUsuarios.add(usuario);//O(logU) por ser inserción en Heap
    }

    public void registrarUsuarioConReferidor(int idReferido, int idReferidor) {//O(logU) promedio y O(U)peor
        Usuario referidor = usuarios.obtener(idReferidor);//O(logU) promedio y O(U) peor por ser búsqueda en ABB
        Usuario referido = new Usuario(idReferido);
        usuarios.definir(idReferido, referido);//O(logU) promedio y O(U) peor por ser inserción en ABB
        referidor.agregarReferencia(500, idReferido);
        referido.agregarReferencia(500, idReferidor);

        maxHeapUsuarios.add(referido);//O(logU) por ser inserción en Heap
        seModificóUsuario(referidor);
    }

    public void acumularMillas(int id, double distancia, String nombrePromo) {//O(logU + L) promedio y O(U + L)peor
        Promo promoción = promociones.obtener(nombrePromo);//O(L) por ser búsqueda en Trie
        Usuario usuario = usuarios.obtener(id);//O(logU) promedio y O(U) peor por ser búsqueda en ABB
        usuario.agregarAcumular(nombrePromo, promoción, distancia);
        seModificóUsuario(usuario);
    }

    public void canjearMillas(int id, double cantidad) {//O(logU) promedio y O(U)peor
        Usuario usuario = usuarios.obtener(id);//O(logU) promedio y O(U) peor por ser búsqueda en ABB
        usuario.agregarCanje(cantidad);
        seModificóUsuario(usuario);
    }

    public void transferirMillas(int idOrigen, int idDestino, double cantidad) {//O(logU) promedio y O(U) peor
        Usuario usuarioOrigen = usuarios.obtener(idOrigen);//O(logU) promedio y O(U) peor por ser búsqueda en ABB
        Usuario usuarioDestino = usuarios.obtener(idDestino);//O(logU) promedio y O(U) peor por ser búsqueda en ABB
        usuarioDestino.agregarTransferencia(cantidad, usuarioOrigen);//Suma millas
        usuarioOrigen.agregarTransferencia(-cantidad, usuarioDestino);//Resta millas
        seModificóUsuario(usuarioDestino);
        seModificóUsuario(usuarioOrigen);
    }

    public double consultarSaldo(int id) {//O(logU) promedio y O(U) peor
        Usuario usuario = usuarios.obtener(id);//O(logU) promedio y O(U) peor por ser búsqueda en ABB
        return usuario.obtenerSaldo();
    }

    public String consultarCategoria(int id) {//O(logU) promedio y O(U) peor
        Usuario usuario = usuarios.obtener(id);//O(logU) promedio y O(U) peor por ser búsqueda en ABB
        return usuario.obtenerCategoría();
    }

    public int[] rankingMillas(int n) {//O(nlogU)
        n = Math.min(n, usuarios.tamaño());
        int[] ids = new int[n];
        Usuario[] usuarios = new Usuario[n];
        for (int i = 0; i < n; i++){//Se repite min(n, U) veces, en el peor caso U veces. O(UlogU)
            Usuario usu = maxHeapUsuarios.poll();//O(logU)
            ids[i] = usu.obtenerId();//O(1)
            usuarios[i] = usu;
        }
        for (int i = 0; i < n; i++)//Se repite min(n, U) veces, en el peor caso U veces. O(UlogU)
            maxHeapUsuarios.add(usuarios[i]);//O(logU)
        return ids;
    }

    public void iniciarPromocion(String nombre, double factor) {//O(L)
        Promo promo = new Promo(factor);//O(1)
        promociones.definir(nombre, promo);//O(L)
    }

    public void iniciarPromocionConTope(String nombre, double factor, double tope) {//O(L)
        Promo promo = new Promo(factor, tope);//O(1)
        promociones.definir(nombre, promo);//O(L)
    }

    public void finalizarPromocion(String nombre) {//O(L)
        Promo promo = promociones.obtener(nombre);//O(L) por ser búsqueda en un Trie
        promo.finalizar();
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

        for (Usuario usu : usuarios) {//Se repite U veces, con U cantidad de usuarios//O(U)
            idActual = usu.obtenerId();
            transferidorExclusivo = usu.obtenerTransferidorExclusivo();//O(1)
            if(transferidorExclusivo != null){
                idTransferidorExclusivo = transferidorExclusivo.obtenerId();//O(1)
                if(idActual < idTransferidorExclusivo)//Evita tuplas repetidas (a,b) y (b,a)
                    if(transferidorExclusivo.obtenerTransferidorExclusivo() != null){//Si tiene un transfExc tiene que ser usu
                        int[] par = {idActual, idTransferidorExclusivo};
                        listaEnlazada.agregar(par);//O(1)
                    }
            }
        }

        int [][] res = new int[listaEnlazada.longitud()][];
        int i = 0;
        for (int[] par : listaEnlazada){ // Paso los pares a un arreglo, O(longitud). Se mantiene O(U)
            res[i] = par;
            i++;
        }
        return res;
    }

    private void seModificóUsuario(Usuario usuario){//O(logU)
        verificarSiEsElMásAcumulador(usuario);
        verificarSiEsElMásCanjeador(usuario);
        maxHeapUsuarios.remove(usuario);//Debería ser O(logU)
        maxHeapUsuarios.add(usuario);//Debería ser O(logU)
    }

    private void verificarSiEsElMásAcumulador(Usuario usuario){//O(1)
        if(usuario.obtenerMillasAcumuladas() > elMásAcumulador.obtenerMillasAcumuladas())
            nuevoMásAcumulador(usuario);
    }

    private void nuevoMásAcumulador(Usuario usuario){
        elMásAcumulador = usuario;
    }

    private void verificarSiEsElMásCanjeador(Usuario usuario){//O(1)
        if(usuario.obtenerMillasCanjeadas() > elMásCanjeador.obtenerMillasCanjeadas())
            nuevoMásCanjeador(usuario);
    }

    private void nuevoMásCanjeador(Usuario usuario){
        elMásCanjeador = usuario;
    }

    public Usuario obtenerUsuario(int id){//No la pide el TP, está para debugear
        return usuarios.obtener(id);//Por ser una búsqueda en un ABB y no un AVL, no podemos asegurar la complejidad
    }
}
