package twentyone;

import DeckOfCards.CartaInglesa;

/**
 * Modela a un jugador del juego 21.
 * Un jugador tiene una mano de cartas y puede recibir
 * cartas durante su turno.
 */
public class Jugador {

    private Pila<CartaInglesa> cartas;
    private boolean sePlanto;
    private boolean bust;

    /**
     * Crea un jugador con una mano vacía.
     */
    public Jugador() {
        cartas = new Pila<>();
        sePlanto = false;
        bust = false;
    }

    /**
     * Agrega una carta a la mano del jugador.
     */
    public void recibirCarta(CartaInglesa carta) {
        if (carta != null) {
            carta.makeFaceUp();
            cartas.push(carta);

            if (getPuntos() > 21) {
                bust = true;
            }
        }
    }

    /**
     * Calcula el valor total de la mano.
     * Los Ases se consideran inicialmente como 11.
     * Si el total supera 21, los Ases se cuentan como 1.
     */
    public int getPuntos() {
        int puntos = 0;
        int cantidadAses = 0;

        for (CartaInglesa carta : cartas.getElementos()) {

            if (carta.getValorBajo() == 1) {
                puntos += 11;
                cantidadAses++;

            } else if (carta.getValor() >= 10) {
                puntos += 10;

            } else {
                puntos += carta.getValor();
            }
        }

        while (puntos > 21 && cantidadAses > 0) {
            puntos -= 10;
            cantidadAses--;
        }

        return puntos;
    }

    /**
     * Indica si el jugador se pasó de 21.
     */
    public boolean estaBusto() {
        return bust;
    }

    /**
     * Indica que el jugador decidió no pedir más cartas.
     */
    public void plantarse() {
        sePlanto = true;
    }

    /**
     * Indica si el jugador ya se plantó.
     */
    public boolean sePlanto() {
        return sePlanto;
    }

    /**
     * Indica si el jugador puede continuar jugando.
     */
    public boolean estaActivo() {
        return !sePlanto && !bust && getPuntos() < 21;
    }

    /**
     * Obtiene las cartas que tiene el jugador.
     */
    public Pila<CartaInglesa> getCartas() {
        return cartas;
    }

    /**
     * Elimina todas las cartas y prepara al jugador
     * para una nueva partida.
     */
    public void limpiarMano() {
        cartas.limpiar();
        sePlanto = false;
        bust = false;
    }

    @Override
    public String toString() {
        return cartas + " Puntos: " + getPuntos();
    }

    public void deshacerUltimaCarta() {

        if (!cartas.estaVacia()) {
            cartas.pop();

            bust = false;
            sePlanto = false;
        }
    }
}