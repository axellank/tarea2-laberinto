package listapilacola1;

import java.lang.UnsupportedOperationException;
import java.util.NoSuchElementException;

/**
 * <p> Clase abstracta para modelar la estructura de datos pila y cola</p>
 * <p>Esta clase implementa una Cola genérica, es decir que es homogénea pero
 * puede tener elementos de cualquier tipo.
 * @author Alejandro Hernández Mora <alejandrohmora@ciencias.unam.mx>
 * @version 1.0
 * @param <T> Tipo que tienen los objetos que guarda esta cola.
 */
public abstract class LinealAdapter<T> extends Lista<T> {

		@Override
        public void  eliminar(T elemento){
	     		throw new UnsupportedOperationException("Operacion no valida");
        }

        /**
	     * Método para eliminar el elemento del inicio de la estructura.
	    */
        protected T eliminarInicio() throws NoSuchElementException{
        	//aqui va tu codigo

            if(super.getTamanio() == 0){                
                throw new NoSuchElementException("No hay elementos");                
            }

            T elem = getElemento(0);
            super.eliminar(super.getElemento(0));
            return elem;
        }

    protected Nodo ver() throws NoSuchElementException{
        	//Aqui va tu codigo
        if(super.getTamanio() == 0){            
                throw new NoSuchElementException("No hay elementos");            
        }
        return cabeza;                
        }
}
