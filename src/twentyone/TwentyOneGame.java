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

    // Pilas utilizadas para la función de deshacer
    private Pila<CartaInglesa> cartasDeshacer;
    private Pila<Integer> jugadoresDeshacer;
    private Pila<Integer> turnosAnteriores;

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

        cartasDeshacer = new Pila<>();
        jugadoresDeshacer = new Pila<>();
        turnosAnteriores = new Pila<>();

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
     * Guarda el movimiento para poder deshacerlo.
     */
    public void pedirCarta() {

        Jugador jugador = jugadores.get(jugadorActual);

        if (jugador.estaActivo()) {

            int turnoAnterior = jugadorActual;
            CartaInglesa carta = mazo.obtenerUnaCarta();

            jugador.recibirCarta(carta);

            // Guardar información para poder deshacer
            cartasDeshacer.push(carta);
            jugadoresDeshacer.push(jugadorActual);
            turnosAnteriores.push(turnoAnterior);

            if (!jugador.estaActivo()) {
                siguienteJugador();
            }
        }
    }

    /**
     * Deshace el último movimiento de pedir carta.
     */
    public void deshacer() {

        if (!cartasDeshacer.estaVacia()) {

            CartaInglesa carta = cartasDeshacer.pop();
            int jugador = jugadoresDeshacer.pop();
            int turnoAnterior = turnosAnteriores.pop();

            // Quitar la última carta que recibió el jugador
            jugadores.get(jugador).deshacerUltimaCarta();gi

            // Regresar el turno al jugador que tenía el turno
            jugadorActual = turnoAnterior;
        }
    }

    /**
     * Indica si existe algún movimiento que se pueda deshacer.
     */
    public boolean sePuedeDeshacer() {
        return !cartasDeshacer.estaVacia();
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
     * Getter del dealer.
     *
     * @return dealer de la partida
     */
    public Dealer getDealer() {
        return dealer;
    }

    /**
     * Obtiene la lista de jugadores.
     *
     * @return jugadores de la partida
     */
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    /**
     * Obtiene el jugador cuyo turno está activo.
     *
     * @return jugador actual
     */
    public Jugador getJugadorActual() {

        if (jugadorActual < jugadores.size()) {
            return jugadores.get(jugadorActual);
        }

        return null;
    }

    /**
     * Indica si todos los jugadores terminaron su turno.
     *
     * @return true si ya no quedan jugadores activos
     */
    public boolean terminaronJugadores() {
        return jugadorActual >= jugadores.size();
    }
}