package twentyone;

/**
 * El dealer sigue las reglas para jugar en
 * automáticamente, pide cartas hasta tener 17 puntos
 * o mas
 *
 */
public class Dealer extends Jugador {

    /**
     * Crea un dealer con una mano vacia
     */
    public Dealer() {
        super();
    }

    /**
     * Indica si el dealer debe pedir otra carta
     * Va regresar true si tiene menos de 17 puntos
     */
    public boolean debePedirCarta() {
        return getPuntos() < 17 && !estaBusto();
    }

    /**
     * Indica si el dealer debe plantarse
     * Va regresar true si tiene 17 puntos o más y no está busto
     */
    public boolean debePlantarse() {
        return getPuntos() >= 17 && !estaBusto();
    }
}