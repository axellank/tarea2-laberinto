package listapilacola1;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
/*
 * <p> Clase concreta para modelar la estructura de datos Lista</p>
 * <p>Esta clase implementa una Lista genérica, es decir que es homogénea pero
 * puede tener elementos de cualquier tipo.
 * @author Alejandro Hernández Mora <alejandrohmora@ciencias.unam.mx>
 * @version 1.1
 */
public class Lista<T> implements Coleccionable<T>, Listable<T>{

    /* Clase interna para construir la estructura */
    class Nodo {
        /* Referencias a los nodos anterior y siguiente */
        public Nodo anterior, siguiente;
        /* El elemento que almacena un nodo */
        public T elemento;

        /* Unico constructor de la clase */
        public Nodo(T elemento) {

            this.elemento = elemento;
            this.anterior = getAnterior();
            this.siguiente = getSiguiente();
        }

        public boolean equals(Nodo n) {
            if (this.elemento.equals(n.getElemento())) {
                return true;
            }
            return false;
        }

        public Nodo getAnterior() {
            return anterior;
        }

        public Nodo getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(Nodo siguiente) {
            this.siguiente = siguiente;
        }

        public void setAnterior(Nodo anterior) {
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

    private class IteradorLista<T> implements Iterator<T> {
        /* La lista a recorrer */
        private Lista<T> lista;
        /* Elementos del centinela que recorre la lista */
        private Lista<T>.Nodo anterior, siguiente;

        public IteradorLista(Lista<T> lista) {
            siguiente = lista.cabeza;
            anterior = null;
        }

        @Override
        public boolean hasNext() {
            if (siguiente != null) {
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            if (this.hasNext()) {
                this.anterior = this.siguiente;
                this.siguiente = this.siguiente.getSiguiente();
                return anterior.getElemento();
            }
            return null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Operacion no valida");
        }
    }

    /* Atributos de la lista */
    protected Nodo cabeza;
    protected Nodo cola;
    protected int longitud;

    // Constructor por Omision
    public Lista() {
        this.cabeza = null;
        this.cola = null;
        this.longitud = 0;
    }

    public Lista(Nodo cabeza) {

        this.cabeza = cabeza;
        this.cola = cabeza;
        this.longitud = 1;
    }

    public Lista(Nodo cola, Nodo cabeza) {
        this.cola = cola;
        this.cabeza = cabeza;
        this.longitud = 2;
    }

    /**
     * Método que nos dice si las lista está vacía.
     * 
     * @return <code>true</code> si el conjunto está vacío, <code>false</code>
     *         en otro caso.
     */
    public boolean esVacia() {
        return longitud == 0;
    }

    /**
     * Método para eliminar todos los elementos de una lista
     */
    public void vaciar() {
        cola = null;
        cabeza = null;
        longitud = 0;

    }

    /**
     * Método para obtener el tamaño de la lista
     * 
     * @return tamanio Número de elementos de la lista.
     **/
    public int getTamanio() {
        return longitud;
    }

    /**
     * Método para agregar un elemento a la lista.
     * 
     * @param elemento Objeto que se agregará a la lista.
     */

    public void agregar(T elemento) throws IllegalArgumentException {
        agregarAlFinal(elemento);
    }

    /**
     * Método para agregar al inicio un elemento a la lista.
     * 
     * @param elemento Objeto que se agregará al inicio de la lista.
     */

    public void agregarAlInicio(T elemento) {
        Nodo nodo = new Nodo(elemento);
        if (longitud == 0) {
            cabeza = nodo;
            cola = nodo;
        } else {
            nodo.setSiguiente(cabeza);
            cabeza.setAnterior(nodo);
            cabeza = nodo;
        }
        longitud++;
    }

    /**
     * Método para agregar al final un elemento a la lista.
     * 
     * @param elemento Objeto que se agregará al inicio de la lista.
     */
    public void agregarAlFinal(T elemento) {
        Nodo nodo = new Nodo(elemento);
        if (longitud == 0) {
            cabeza = nodo;
            cola = nodo;
        } else {
            cola.setSiguiente(nodo);
            nodo.setAnterior(cola);
            cola = nodo;
        }
        longitud++;
    }

    /**
     * Método para verificar si un elemento pertenece a la lista.
     * 
     * @param elemento Objeto que se va a buscar en la lista.
     * @return <code>true</code> si el elemento esta en el lista y false en otro
     *         caso.
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
     * 
     * @param elemento Objeto que se eliminara de la lista.
     */

    public void eliminar(T elemento) throws NoSuchElementException {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.getElemento().equals(elemento)) {
                if (actual == cabeza) {
                    cabeza = actual.getSiguiente();
                    if (cabeza != null) {
                        cabeza.setAnterior(null);
                    } else {
                        cola = null;
                    }
                } else {
                    actual.getAnterior().setSiguiente(actual.getSiguiente());
                    if (actual.getSiguiente() != null) {
                        actual.getSiguiente().setAnterior(actual.getAnterior());
                    } else {
                        cola = actual.getAnterior();
                    }
                }
                longitud--;
                return;
            }
            actual = actual.getSiguiente();
        }
        throw new NoSuchElementException();
    }

    /**
     * Método que devuelve la posición en la lista que tiene la primera
     * aparición del <code> elemento</code>.
     * 
     * @param elemento El elemnto del cuál queremos saber su posición.
     * @return i la posición del elemento en la lista, -1, si no se encuentra en
     *         ésta.
     */

    @Override
    public int indiceDe(T elemento) {
        int indice = 0;
        for (T e : this) {
            if (e.equals(elemento)) {
                return indice;
            }
            indice++;
        }
        return -1; // Debería devolver -1 si el elemento no se encuentra en la lista.
    }

    /**
     * Método que nos dice en qué posición está un elemento en la lista
     * 
     * @param i La posición cuyo elemento deseamos conocer.
     * @return <code> elemento </code> El elemento que contiene la lista,
     *         <code>null</code> si no se encuentra
     * @throws IndexOutOfBoundsException Si el índice es < 0 o >longitud()
     */
    public T getElemento(int i) throws IndexOutOfBoundsException {
        if (longitud <= i || i < 0)
            throw new IndexOutOfBoundsException();

        Iterator<T> it = iterator();
        int posición = 0;

        while (it.hasNext() && posición < i) {
            it.next();
            posición++;
        }
        return it.next();
    }

    /**
     * Método que devuelve una copia de la lista, pero en orden inverso
     * 
     * @return Una copia con la lista l revés.
     */

    public Lista<T> reversa() {
        Lista<T> reversedList = new Lista<>();
        Nodo current = cabeza;
        while (current != null) {
            reversedList.agregarAlInicio(current.getElemento());
            current = current.getSiguiente();
        }
        return reversedList;
    }

    /**
     * Método que devuelve una copi exacta de la lista
     * 
     * @return la copia de la lista.
     */
    public Lista<T> copia() {
        Lista<T> copia = new Lista<>();
        Nodo actual = cabeza;
        while (actual != null) {
            copia.agregarAlFinal(actual.getElemento());
            actual = actual.getSiguiente();
        }
        return copia;
    }

    /**
     * Método que nos dice si una lista es igual que otra.
     * 
     * @param o objeto a comparar con la lista.
     * @return <code>true</code> si son iguales, <code>false</code> en otro caso.
     */

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Lista)) {
            return false;
        }
        Lista<T> otraLista = (Lista<T>) o;
        if (this.longitud != otraLista.longitud) {
            return false;
        }
        Nodo F1 = this.cabeza;
        Nodo F2 = otraLista.cabeza;
        while (F1 != null) {
            if (!F1.elemento.equals(F2.elemento)) {
                return false;
            }
            F1 = F1.siguiente;
            F2 = F2.siguiente;
        }
        return true;

    }

    /**
     * Método que devuelve un iterador sobre la lista
     * 
     * @return java.util.Iterador -- iterador sobre la lista
     */
    @Override
    public java.util.Iterator<T> iterator() {
        return new IteradorLista<T>(this);
    }

    /**
     * Método que devuelve una copia de la lista.
     * 
     * @param <T> Debe ser un tipo que extienda Comparable, para poder distinguir
     *            el orden de los elementos en la lista.
     * @param l   La lista de elementos comparables.
     * @return copia de la lista ordenada.
     */
    public static <T extends Comparable<T>> Lista<T> mergesort(Lista<T> l) {

        if (l.getTamanio() == 0 || l.getTamanio() == 1) {
            return l;
        }

        Lista<T> aux1 = new Lista<>();
        Lista<T> aux2 = new Lista<>();
        Iterator it = l.iterator();
        int longI = l.getTamanio();
        int recorridos = 0;

        while (it.hasNext()) {
            if (recorridos <= longI / 2) {
                aux1.agregarAlFinal((T) it.next());
                recorridos++;
            } else {
                aux2.agregarAlFinal((T) it.next());
            }
        }
        aux1 = mergesort(aux1);
        aux2 = mergesort(aux2);

        Lista<T> ordenada = merge(aux1, aux2);

        return ordenada;
    }

    // Merge
    public static <T extends Comparable<T>> Lista<T> merge(Lista<T> l1, Lista<T> l2) {
        Lista<T> result = new Lista<>();
        Iterator<T> it1 = l1.iterator();
        Iterator<T> it2 = l2.iterator();

        T e1 = null, e2 = null;

        if (it1.hasNext()) {
            e1 = it1.next();
        }
        if (it2.hasNext()) {
            e2 = it2.next();
        }

        while (e1 != null && e2 != null) {
            if (e1.compareTo(e2) < 0) {
                result.agregarAlFinal(e1);
                if (it1.hasNext()) {
                    e1 = it1.next();
                } else {
                    e1 = null;
                }
            } else {
                result.agregarAlFinal(e2);
                if (it2.hasNext()) {
                    e2 = it2.next();
                } else {
                    e2 = null;
                }
            }
        }

        while (e1 != null) {
            result.agregarAlFinal(e1);
            if (it1.hasNext()) {
                e1 = it1.next();
            } else {
                e1 = null;
            }
        }

        while (e2 != null) {
            result.agregarAlFinal(e2);
            if (it2.hasNext()) {
                e2 = it2.next();
            } else {
                e2 = null;
            }
        }

        return result;
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

}