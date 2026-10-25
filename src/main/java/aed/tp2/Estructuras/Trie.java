package aed.tp2.Estructuras;

public class Trie<T> implements Diccionario<String, T> {
    
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

    public void definir(String k, T v){//O(|k|) //Agrega un nodo por cada letra de la palabra k y guarda v en el ultimo
        // definir: Promoción
        // clave ya existente: Promo
        // itera: P-r-o-m-o-null
        // agrega un nodo nuevo por cada letra (1-c,2-i,3-o,4-n)
        Nodo actual = raiz;
        for (int i=0; i < k.length(); i++){
            char c = k.charAt(i);
            if (actual.hijos[c]==null){
                actual.hijos[c] = new Nodo();
                actual.cantHijos++;
            }
            actual = actual.hijos[c];
        }
        if (actual.valor == null)
            tamaño++; // clave nueva
        actual.valor = v;
    }
    
    public T obtener (String k){//O(|k|) //Devuelve el valor asociado a la palabra k
        Nodo nodo = buscar(k);
        if (nodo == null)
            return null;
        return nodo.valor;
    }

    private Nodo buscar(String k){//O(|K|) Si no existe devuelve null
        Nodo actual = raiz;
        for (int i=0; i < k.length() && actual != null ; i++)//Agarra la raiz y va bajando por cada letra de k
            actual = actual.hijos[k.charAt(i)];//k pertenece al dict (cumple el requiere)
        return actual;
    }

    public void borrar (String k){//O(|K|)
        Nodo nodoFinal = buscar(k);
        if (nodoFinal == null || nodoFinal.valor == null) {
            return;//La palabra no está, no hay nada que borrar
        }

        Nodo actual = raiz;

        for(int i=0; i< k.length(); i++){
            char c = k.charAt(i);
            actual = actual.hijos[c];
        }

        actual.valor = null;
        tamaño--;//Fui al final de la palabra y le saqué el valor asociado

        if (actual.cantHijos > 0) {
            return;//Hay palabras más largas que pasan por acá, no se suelta ninguna letra
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
        }//Encuentro la letra desde donde la palabra es la única con ese nombre

        char letraASoltar = k.charAt(indiceCorte);
        ultimoNodoProtegido.hijos[letraASoltar] = null;
        ultimoNodoProtegido.cantHijos--;//Suelto todas las letras posteriores a la de corte
    }
    
    public int tamaño (){//Devuelve la cantidad de palabras que hay
        return tamaño;
    }

    public void definirRápido(String k, T v){//No tiene sentido definirRápido
        definir(k, v);
    }

    public boolean está(T v){//Creo que no se usa, verifica que dado un valor busca una promo que 
        throw new UnsupportedOperationException("Sin implementar");//Lo tenga
    }

    public boolean igualdad(Diccionario<String,T> dict){//Tampoco se usa, compara dos diccionarios de promo exactamente iguales
        throw new UnsupportedOperationException("Sin implementar");
    }
}