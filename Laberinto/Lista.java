import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;

/**
 * <p> Clase concreta para modelar la estructura de datos Lista</p>
 * <p>Esta clase implementa una Lista genérica, es decir que es homogénea pero
 * puede tener elementos de cualquier tipo.
 * @author Alejandro Hernández Mora <alejandrohmora@ciencias.unam.mx>
 * @version 1.1
 */
public class Lista<T> implements Coleccionable <T>, Listable <T> , Iterable <T>{

    /* Clase interna para construir la estructura */
    protected class Nodo <T> {
	    /* Referencias a los nodos anterior y siguiente */
        public Nodo anterior, siguiente;
        /* El elemento que almacena un nodo */
        public T elemento;

        /* Unico constructor de la clase */
        public Nodo(T elemento){

            this.elemento = elemento;
            this.anterior = getAnterior();
            this.siguiente = getSiguiente();
        }
        
        public boolean equals(Nodo n){
            if(this.elemento.equals(n.getElemento())){
                return true;
            }
            return false;
        }
    
        public Nodo getAnterior(){
            return anterior;
        }
    
        public Nodo getSiguiente(){
            return siguiente;
        }
    
        public void setSiguiente(Nodo siguiente){
            this.siguiente = siguiente;
        }
    
        public void setAnterior(Nodo anterior){
            this.anterior = anterior;
        }

        public T getElemento() {
            return elemento;
        }
        
        @Override
        public String toString() {
            return this.elemento.toString();
        }

    }
    protected class IteradorLista <T> implements Iterator <T>{
        /* La lista a recorrer*/
        protected Lista<T> lista;
        /* Elementos del centinela que recorre la lista*/
        protected Lista<T>.Nodo<T> anterior, siguiente;
        
        public IteradorLista(Lista<T> lista){
           siguiente = lista.cabeza;
           anterior = null;
        }

        @Override
        public boolean hasNext() {
            if (siguiente != null){
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            if(this.hasNext()){
                this.anterior=this.siguiente;
                this.siguiente=this.siguiente.getSiguiente();
                return this.anterior.getElemento();
            }
            return null;
        
        }

        @Override
        public void remove(){
	     throw new UnsupportedOperationException("Operacion no valida");    
        }
    }

    /* Atributos de la lista */
    protected Nodo <T> cabeza, cola;
    private int longitud;
    
    //Constructor por Omision
    public Lista (){
        this.cabeza = null;
        this.cola = null;
        this.longitud = 0;
    }


    public Lista (Nodo cabeza){

        this.cabeza = cabeza;
        this.cola = cabeza;
        this.longitud =1;
    }
    
    
    public Lista (Nodo cola, Nodo cabeza ){
        this.cola = cola;
        this.cabeza = cabeza;
        this.longitud =0;
    }
    /**
     * Método que nos dice si las lista está vacía.
     * @return <code>true</code> si el conjunto está vacío, <code>false</code>
     * en otro caso.
     */
    public boolean esVacia(){
        if (longitud == 0){
        return true;
        }else{
            return false;
        }
    }

    
    /**
     * Método para eliminar todos los elementos de una lista
     */
    public void vaciar(){
        cola = null;
        cabeza = null;
        longitud = 0;

    }
    /**
     * Método para obtener el tamaño de la lista
     * @return tamanio Número de elementos de la lista.
     **/
    public int getTamanio(){
        return longitud;
    }

    /**
     * Método para agregar un elemento a la lista.
     * @param elemento Objeto que se agregará a la lista.
     */

    public void agregar(T elemento) throws IllegalArgumentException{
        agregarAlFinal(elemento);
    }
        /**
     * Método para agregar al inicio un elemento a la lista.
     * @param elemento Objeto que se agregará al inicio de la lista.
     */
    
    public void agregarAlInicio(T elemento){
    	Nodo <T> nodo = new Nodo(elemento);
        if(longitud == 0){
            cabeza = nodo;
            cola = nodo;

        }else{
            nodo.setSiguiente(cabeza);
            cabeza.setAnterior(nodo);
            cabeza = nodo;
        }
        longitud ++;
    }   
    /**
     * Método para agregar al final un elemento a la lista.
     * @param elemento Objeto que se agregará al inicio de la lista.
     */
    public void agregarAlFinal(T elemento){
        Nodo nodo = new Nodo(elemento);
        if(longitud == 0){
            cabeza= nodo;
            cola = nodo;
        }else{
            cola.setSiguiente(nodo);
            nodo.setAnterior(cola);
            cola = nodo;
        }
        longitud ++;
    }
    /**
     * Método para verificar si un elemento pertenece a la lista.
     * @param elemento Objeto que se va a buscar en la lista.
     * @return <code>true</code> si el elemento esta en el lista y false en otro caso.
     */

    public boolean contiene(T elemento) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.getElemento().equals(elemento)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }
    
    /**
     * Método para eliminar un elemento de la lista.
     * @param elemento Objeto que se eliminara de la lista.
     */


    public void eliminar(T elemento) throws NoSuchElementException{
        Nodo actual = cabeza;
        while(actual != null){
            if (actual.getElemento().equals(elemento)){
                if(actual == cabeza){
                    cabeza = cabeza.getSiguiente();
                }else if(actual == cola){
                    cola = cola.getAnterior();
                }else{
                actual.getSiguiente().setAnterior(actual.getAnterior());
                actual.getAnterior().setSiguiente(actual.getSiguiente());
            }    
            longitud --;
            return;
        }
        actual = actual.getSiguiente();
        }
        throw new NoSuchElementException("El elemento no está en la lista");
    }

    /**
     * Método que devuelve la posición en la lista que tiene la primera
     * aparición del <code> elemento</code>.
     * @param elemento El elemnto del cuál queremos saber su posición.
     * @return i la posición del elemento en la lista, -1, si no se encuentra en ésta.
     */
    @Override
    public int indiceDe(T elemento){
        int indice =0;
        for(T e: this){
            if(e.equals(elemento)){
                return indice;
            }
            indice ++;
        }
        return -1; //temporal
    }
    
    /**
     * Método que nos dice en qué posición está un elemento en la lista
     * @param i La posición cuyo elemento deseamos conocer.
     * @return <code> elemento </code> El elemento que contiene la lista, 
     * <code>null</code> si no se encuentra
     * @throws IndexOutOfBoundsException Si el índice es < 0 o >longitud()
     */
    public T getElemento(int i)throws IndexOutOfBoundsException{
        
            if(this.longitud<=i || i<0){
                throw new IndexOutOfBoundsException("operacion no valida");
            }
            Iterator it = this.iterator();
            int posicion = 0;
            T elemento = null;
            while(posicion<=i){
                elemento = (T) it.next();
                posicion ++;
            }
            return elemento;
        }
    
    /**
     * Método que devuelve una copia de la lista, pero en orden inverso
     * @return Una copia con la lista l revés.
     */

     public Lista<T> reversa() {
        Lista<T> reversedList = new Lista<>();
        Nodo<T> current = cabeza;
        while (current != null) {
            reversedList.agregarAlInicio(current.getElemento());
            current = current.getSiguiente();
        }
        return reversedList;
    }    
    
    
    /**
     * Método que devuelve una copi exacta de la lista
     * @return la copia de la lista.
     */
    public Lista<T> copia() {
        Lista<T> copia = new Lista<>();
        Nodo<T> actual = cabeza;
        while (actual != null) {
            copia.agregarAlFinal(actual.getElemento());
            actual = actual.getSiguiente();
        }
        return copia;
    }    
    
    /**
     * Método que nos dice si una lista es igual que otra.
     * @param o objeto a comparar con la lista.
     * @return <code>true</code> si son iguales, <code>false</code> en otro caso.
     */
    
    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        
        if (!(o instanceof Lista)){
            return false;
        }
        Lista <T> otraLista = (Lista <T>) o;
        if(this.longitud != otraLista.longitud){
            return false;
        }
        Nodo F1 = this.cabeza;
        Nodo F2 = otraLista.cabeza;
        while (F1 != null){
            if(!F1.elemento.equals(F2.elemento)){
                return false;
            }
            F1 = F1.siguiente;
            F2 = F2.siguiente;    
        }
            return true;

    }
    
    /**
     * Método que devuelve un iterador sobre la lista
     * @return java.util.Iterador -- iterador sobre la lista
     */
    @Override
    public java.util.Iterator<T> iterator() {
        return new IteradorLista<T>(this);
    }
  
    @Override
    public String toString() {
    StringBuilder sb = new StringBuilder("[");
    Nodo actual = cabeza;
    while (actual != null) {
        sb.append(actual.getElemento().toString());
        if (actual.getSiguiente() != null) {
            sb.append(", ");
        }
        actual = actual.getSiguiente();
    }
    sb.append("]");
    return sb.toString();
  }
    //Metodo para obtener la cabeza
    public Nodo<T> getHead() {
    return cabeza;
    }

    //Set de la cabeza
    public void setHead(Nodo<T> nodoCabeza) {
        cabeza = nodoCabeza;
    }
    
    //Set de la cola
    public void setTail(Nodo<T> nodo) {
        cola = nodo;
    }
    
    //Getter del Nodo
    protected Nodo<T> getNodo(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i >= longitud) {
            throw new IndexOutOfBoundsException("Posición inválida");
        }
        Nodo<T> actual = cabeza;
        for (int j = 0; j < i; j++) {
            actual = actual.getSiguiente();
        }
        return actual;
    }
    
}