package twentyone;

import DeckOfCards.CartaInglesa;
import DeckOfCards.Mazo;

import java.util.ArrayList;

/**
 * Modela la lógica principal del juego 21.
 *
 * @author
 * @version 2026
 */
public class TwentyOneGame {

    private Mazo mazo;
    private Dealer dealer;
    private ArrayList<Jugador> jugadores;
    private int jugadorActual;

    /**
     * Crea una partida con la cantidad de jugadores indicada.
     *
     * @param cantidadJugadores número de jugadores, entre 1 y 4
     */
    public TwentyOneGame(int cantidadJugadores) {

        mazo = new Mazo();
        dealer = new Dealer();
        jugadores = new ArrayList<>();
        jugadorActual = 0;

        for (int i = 0; i < cantidadJugadores; i++) {
            jugadores.add(new Jugador());
        }
    }

    /**
     * Reparte las dos cartas iniciales a cada jugador
     * y al dealer.
     */
    public void repartirCartasIniciales() {

        for (Jugador jugador : jugadores) {
            jugador.recibirCarta(mazo.obtenerUnaCarta());
            jugador.recibirCarta(mazo.obtenerUnaCarta());
        }

        dealer.recibirCarta(mazo.obtenerUnaCarta());

        CartaInglesa cartaOculta = mazo.obtenerUnaCarta();
        dealer.getCartas().push(cartaOculta);
    }

    /**
     * Hace que el jugador actual reciba una carta.
     */
    public void pedirCarta() {

        Jugador jugador = jugadores.get(jugadorActual);

        if (jugador.estaActivo()) {

            jugador.recibirCarta(mazo.obtenerUnaCarta());

            if (!jugador.estaActivo()) {
                siguienteJugador();
            }
        }
    }

    /**
     * Hace que el jugador actual se plante.
     */
    public void plantarse() {

        jugadores.get(jugadorActual).plantarse();
        siguienteJugador();
    }

    /**
     * Avanza al siguiente jugador activo.
     */
    private void siguienteJugador() {

        jugadorActual++;

        while (jugadorActual < jugadores.size()
                && !jugadores.get(jugadorActual).estaActivo()) {
            jugadorActual++;
        }
    }

    /**
     * Hace que el dealer juegue automáticamente
     * siguiendo las reglas del juego.
     */
    public void jugarDealer() {

        while (dealer.debePedirCarta()) {
            dealer.recibirCarta(mazo.obtenerUnaCarta());
        }
    }

    /**
     * getter del dealer
     * regresará dealer de la partida
     */
    public Dealer getDealer() {
        return dealer;
    }

    /**
     * Obtiene la lista de jugadores.
     * jugadores de la partida
     */
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    /**
     * Obtiene el jugador cuyo turno está activo
     * getter del jugador actual
     */
    public Jugador getJugadorActual() {
        if (jugadorActual < jugadores.size()) {
            return jugadores.get(jugadorActual);
        }

        return null;
    }

    /**
     * Indica si todos los jugadores terminaron su turno.
     * va regresar true si ya no quedan jugadores activos
     */
    public boolean terminaronJugadores() {
        return jugadorActual >= jugadores.size();
    }
}