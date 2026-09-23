package twentyone;

import java.util.Scanner;

public class TwentyOneTextUI {

    private TwentyOneGame juego;
    private Scanner scanner;

    public TwentyOneTextUI() {
        scanner = new Scanner(System.in);
    }

    public void iniciarJuego() {

        System.out.println("=== JUEGO 21 ===");

        int cantidadJugadores = pedirCantidadJugadores();

        juego = new TwentyOneGame(cantidadJugadores);

        juego.repartirCartasIniciales();

        jugarJugadores();

        mostrarResultados();
    }

    private int pedirCantidadJugadores() {

        int cantidad;

        do {
            System.out.print("¿Cuántos jugadores? (1-4): ");
            cantidad = scanner.nextInt();
        } while (cantidad < 1 || cantidad > 4);

        return cantidad;
    }

    private void jugarJugadores() {

        System.out.println("\nDealer:");
        System.out.println(juego.getDealer().getCartas());

        while (!juego.terminaronJugadores()) {

            Jugador jugador = juego.getJugadorActual();

            System.out.println("\nTurno del jugador "
                    + (juego.getJugadores().indexOf(jugador) + 1));

            System.out.println(jugador);

            while (jugador.estaActivo()) {

                System.out.println("\n1. HIT");
                System.out.println("2. STAND");
                System.out.print("Elige una opción: ");

                int opcion = scanner.nextInt();

                if (opcion == 1) {

                    juego.pedirCarta();

                    System.out.println("Nueva mano:");
                    System.out.println(jugador);

                    if (jugador.estaBusto()) {
                        System.out.println("¡BUST! Te pasaste de 21.");
                    }

                } else if (opcion == 2) {

                    juego.plantarse();

                } else {

                    System.out.println("Opción inválida.");
                }
            }
        }
    }

    private void mostrarResultados() {

        System.out.println("\n=== TURNO DEL DEALER ===");

        // Voltear todas las cartas del dealer
        for (var carta : juego.getDealer().getCartas().getElementos()) {
            carta.makeFaceUp();
        }

        System.out.println("Mano inicial del dealer:");
        System.out.println(juego.getDealer());

        // El dealer pide hasta llegar a 17 o más
        juego.jugarDealer();

        System.out.println("Mano final del dealer:");
        System.out.println(juego.getDealer());

        System.out.println("\n=== RESULTADOS ===");

        int puntosDealer = juego.getDealer().getPuntos();

        for (int i = 0; i < juego.getJugadores().size(); i++) {

            Jugador jugador = juego.getJugadores().get(i);

            System.out.print(
                    "Jugador " + (i + 1)
                            + " (" + jugador.getPuntos() + " puntos): "
            );

            if (jugador.estaBusto()) {

                System.out.println("PERDEDOR - BUST");

            } else if (juego.getDealer().estaBusto()) {

                System.out.println("GANADOR");

            } else if (jugador.getPuntos() > puntosDealer) {

                System.out.println("GANADOR");

            } else if (jugador.getPuntos() < puntosDealer) {

                System.out.println("PERDEDOR");

            } else {

                System.out.println("EMPATE");
            }
        }
    }
}