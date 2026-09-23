package twentyone;

import java.util.ArrayList;

/**
 * Implementa una estructura de datos tipo pila.
 * Utiliza el principio LIFO: el último elemento
 * que entra es el primero que sale.
 */
public class Pila<T> {

    private ArrayList<T> elementos;

    /**
     * Crea una pila vacía.
     */
    public Pila() {
        elementos = new ArrayList<>();
    }

    /**
     * Agrega un elemento en la cima de la pila.
     */
    public void push(T elemento) {
        elementos.add(elemento);
    }

    /**
     * Retira y regresa el elemento que está
     * en la cima de la pila.
     */
    public T pop() {
        if (elementos.isEmpty()) {
            return null;
        }

        return elementos.removeLast();
    }

    /**
     * Consulta el elemento que está en la cima
     * sin retirarlo.
     */
    public T peek() {
        if (elementos.isEmpty()) {
            return null;
        }

        return elementos.getLast();
    }

    /**
     * Indica si la pila está vacía.
     */
    public boolean estaVacia() {
        return elementos.isEmpty();
    }

    /**
     * Regresa la cantidad de elementos.
     */
    public int size() {
        return elementos.size();
    }

    /**
     * Elimina todos los elementos.
     */
    public void limpiar() {
        elementos.clear();
    }

    /**
     * Regresa los elementos almacenados.
     */
    public ArrayList<T> getElementos() {
        return elementos;
    }

    @Override
    public String toString() {
        return elementos.toString();
    }
}