package aed.tp2;

public class Trie<T> implements Diccionario <String, T> {
    private Nodo raiz;
    private int tamaño;

    private class Nodo{
        T valor;
        Nodo [] hijos;
        int cantHijos;

        public Nodo() {
            this.valor = null;
            this.hijos = (Nodo[]) new Trie.Nodo[256];  //el número es mayor a todos los bits asociados a cada letra del abecedario (Ej: a=97, z=122, ú=250)
            cantHijos = 0;
        }
    }
    public Trie(){
        raiz = new Nodo();
        tamaño = 0;
    }
    public void definir(String k, T v){ //agrega un nodo por cada letra de la palabra k y guarda v en el ultimo

        //definir: Promoción
        // clave ya existente: Promo
        // itera: P-r-o-m-o-null
        // agrega un nodo nuevo por cada letra (1-c,2-i,3-o,4-n)
        Nodo actual = raiz;
        for (int i=0; i<k.length();i++){
            char c=k.charAt(i);
            if (actual.hijos[c]==null){
                actual.hijos[c] = new Nodo();
                actual.cantHijos++;
            }
            actual = actual.hijos[c];
        }
        if (actual.valor == null) {
            tamaño++; // clave nueva
        }
        actual.valor = v;
    }

    private Nodo buscar (String k){
        Nodo actual = raiz;
        for (int i=0; i<k.length() && actual!=null ;i++){       //agarra la raiz y va bajando por cada letra de k
            actual = actual.hijos[k.charAt(i)];                 //k pertenece al dict (cumple el requiere)
        }
        return actual;
    }

    public T obtener (String k){                                // devuelve el valor asociado a la palabra k
        Nodo nodo = buscar(k);
        if (nodo == null) {
            return null;
        }
        return nodo.valor;
    }

    public void borrar (String k){
        Nodo nodoFinal = buscar(k);
        if (nodoFinal == null || nodoFinal.valor == null) {
            return;                             //la palabra no está, no hay nada que borrar
        }

        Nodo actual = raiz;

        for(int i=0; i< k.length(); i++){
            char c = k.charAt(i);
            actual = actual.hijos[c];
        }

        actual.valor = null;
        tamaño--;                               //Fui al final de la palabra y le saqué el valor asociado

        if (actual.cantHijos > 0) {
            return;                             //hay palabras más largas que pasan por acá, no se suelta ninguna letra
        }

        Nodo ultimoNodoProtegido = raiz;
        int indiceCorte = 0;

        actual = raiz;
        for(int i=0; i<k.length(); i++){
            char c = k.charAt(i);

            if (actual.valor !=null || actual.cantHijos >1){
                ultimoNodoProtegido = actual;
                indiceCorte=i;
            }
            actual = actual.hijos[c];
        }                                       //Encuentro la letra desde donde la palabra es la única con ese nombre

        char letraASoltar = k.charAt(indiceCorte);
        ultimoNodoProtegido.hijos[letraASoltar] = null;
        ultimoNodoProtegido.cantHijos--;                //Suelto todas las letras posteriores a la de corte
    }
    
    public int tamaño (){return tamaño;}                        // devuelve la cantidad de palabras que hay




    public Diccionario<String,T> diccionarioVacío(){            //Funciones para que ande bien con el formato diccionario
        return new Trie<T>();
    }

    public void definirRápido(String k, T v){
        definir(k, v);
    }

    public boolean está(T v){                                           //Creo que no se usa, verifica que dado un valor busca una promo que 
        throw new UnsupportedOperationException("Sin implementar");     //lo tenga
    }

    public boolean igualdad(Diccionario<String,T> dict){                //Tampoco se usa, compara dos diccionarios de promo exactamente iguales
        throw new UnsupportedOperationException("Sin implementar");
    }
}