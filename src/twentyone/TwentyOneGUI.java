package twentyone;

import DeckOfCards.CartaInglesa;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class TwentyOneGUI extends Application {

    private TwentyOneGame juego;

    // Colores principales
    private final String COLOR_MESA = "#0B3D5C";
    private final String COLOR_PANEL = "#082F46";
    private final String COLOR_BORDE = "#D4AF37";


    @Override
    public void start(Stage stage) {

        mostrarSeleccionJugadores(stage);
    }


    // =========================================================
    // MENU PRINCIPAL
    // =========================================================

    private void mostrarSeleccionJugadores(Stage stage) {

        Label titulo = new Label("BLACKJACK");

        titulo.setStyle(
                "-fx-font-size: 44px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;"
        );

        Label numero = new Label("21");

        numero.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #D4AF37;"
        );

        Label pregunta = new Label(
                "Selecciona la cantidad de jugadores"
        );

        pregunta.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-text-fill: white;"
        );


        Button jugador1 = crearBotonMenu("1 JUGADOR");
        Button jugador2 = crearBotonMenu("2 JUGADORES");
        Button jugador3 = crearBotonMenu("3 JUGADORES");
        Button jugador4 = crearBotonMenu("4 JUGADORES");


        jugador1.setOnAction(e ->
                iniciarJuego(stage, 1)
        );

        jugador2.setOnAction(e ->
                iniciarJuego(stage, 2)
        );

        jugador3.setOnAction(e ->
                iniciarJuego(stage, 3)
        );

        jugador4.setOnAction(e ->
                iniciarJuego(stage, 4)
        );


        Label reglas = new Label(
                "1–4 jugadores   •   HIT   •   STAND"
        );

        reglas.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-text-fill: #D0D0D0;"
        );


        VBox menu = new VBox(14);

        menu.setAlignment(Pos.CENTER);

        menu.getChildren().addAll(
                titulo,
                numero,
                pregunta,
                jugador1,
                jugador2,
                jugador3,
                jugador4,
                reglas
        );


        StackPane fondo = new StackPane();

        fondo.setStyle(
                "-fx-background-color: " + COLOR_MESA + ";"
        );

        fondo.getChildren().add(menu);


        Scene scene = new Scene(
                fondo,
                800,
                700
        );


        stage.setTitle("Blackjack - 21");
        stage.setScene(scene);

        stage.setMinWidth(700);
        stage.setMinHeight(600);
        stage.setResizable(true);

        stage.show();
    }


    // =========================================================
    // INICIAR JUEGO
    // =========================================================

    private void iniciarJuego(
            Stage stage,
            int cantidadJugadores) {

        juego = new TwentyOneGame(
                cantidadJugadores
        );

        juego.repartirCartasIniciales();

        mostrarMesa(stage);
    }


    // =========================================================
    // MESA PRINCIPAL
    // =========================================================

    private void mostrarMesa(Stage stage) {

        BorderPane mesa = new BorderPane();

        mesa.setStyle(
                "-fx-background-color: " + COLOR_MESA + ";"
        );

        mesa.setPadding(
                new Insets(12)
        );


        // -----------------------------------------------------
        // TITULO
        // -----------------------------------------------------

        Label titulo = new Label("BLACKJACK");

        titulo.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;"
        );


        Label subtitulo = new Label("JUEGO 21");

        subtitulo.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-text-fill: #D4AF37;" +
                        "-fx-font-weight: bold;"
        );


        VBox encabezado = new VBox(1);

        encabezado.setAlignment(Pos.CENTER);

        encabezado.getChildren().addAll(
                titulo,
                subtitulo
        );


        // -----------------------------------------------------
        // DEALER
        // -----------------------------------------------------

        VBox panelDealer =
                crearPanelDealer(false);


        VBox superior = new VBox(7);

        superior.setAlignment(Pos.CENTER);

        superior.getChildren().addAll(
                encabezado,
                panelDealer
        );


        mesa.setTop(superior);


        // -----------------------------------------------------
        // JUGADORES
        // -----------------------------------------------------

        GridPane jugadoresGrid =
                new GridPane();

        jugadoresGrid.setAlignment(Pos.CENTER);

        jugadoresGrid.setHgap(12);
        jugadoresGrid.setVgap(10);

        jugadoresGrid.setPadding(
                new Insets(6, 5, 6, 5)
        );


        for (int i = 0;
             i < juego.getJugadores().size();
             i++) {

            Jugador jugador =
                    juego.getJugadores().get(i);


            VBox panelJugador =
                    crearPanelJugador(
                            jugador,
                            i + 1,
                            false
                    );


            int columna = i % 2;
            int fila = i / 2;


            jugadoresGrid.add(
                    panelJugador,
                    columna,
                    fila
            );
        }


        mesa.setCenter(jugadoresGrid);


        // -----------------------------------------------------
        // BOTONES
        // -----------------------------------------------------

        Button hit =
                crearBotonJuego("HIT");

        Button stand =
                crearBotonJuego("STAND");

        Button undo =
                crearBotonJuego("DESHACER");

        Button menuPrincipal =
                crearBotonJuego("MENÚ PRINCIPAL");


        hit.setPrefWidth(110);
        stand.setPrefWidth(110);
        undo.setPrefWidth(110);
        menuPrincipal.setPrefWidth(155);


        // HIT

        hit.setOnAction(e -> {

            juego.pedirCarta();

            actualizarMesa(stage);
        });


        // STAND

        stand.setOnAction(e -> {

            juego.plantarse();

            actualizarMesa(stage);
        });
        // DESHACER

        undo.setOnAction(e -> {

            juego.deshacer();

            actualizarMesa(stage);
        });

        // MENÚ PRINCIPAL

        menuPrincipal.setOnAction(e -> {

            mostrarSeleccionJugadores(stage);
        });


        HBox botones =
                new HBox(12);

        botones.setAlignment(Pos.CENTER);

        botones.setPadding(
                new Insets(8, 0, 0, 0)
        );


        botones.getChildren().addAll(
                hit,
                stand,
                undo,
                menuPrincipal
        );


        // -----------------------------------------------------
        // INDICADOR DE TURNO
        // -----------------------------------------------------

        Jugador jugadorActual =
                juego.getJugadorActual();


        Label indicadorTurno =
                new Label();


        if (jugadorActual != null) {

            int numeroJugador =
                    juego.getJugadores()
                            .indexOf(jugadorActual) + 1;


            indicadorTurno.setText(
                    "TURNO DEL JUGADOR " +
                            numeroJugador
            );

        } else {

            indicadorTurno.setText(
                    "TURNO TERMINADO"
            );
        }


        indicadorTurno.setStyle(
                "-fx-font-size: 17px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #D4AF37;"
        );


        VBox parteInferior =
                new VBox(7);


        parteInferior.setAlignment(
                Pos.CENTER
        );


        parteInferior.getChildren().addAll(
                indicadorTurno,
                botones
        );


        mesa.setBottom(
                parteInferior
        );


        // -----------------------------------------------------
        // ESCENA
        // -----------------------------------------------------

        Scene scene =
                new Scene(
                        mesa,
                        950,
                        680
                );


        stage.setScene(scene);

        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setResizable(true);

        stage.show();
    }


    // =========================================================
    // PANEL DEL DEALER
    // =========================================================

    private VBox crearPanelDealer(
            boolean resultados) {

        VBox panel =
                new VBox(6);


        panel.setAlignment(Pos.CENTER);


        panel.setPadding(
                new Insets(8, 20, 8, 20)
        );


        panel.setStyle(
                "-fx-background-color: " +
                        COLOR_PANEL + ";" +

                        "-fx-background-radius: 12;" +

                        "-fx-border-color: " +
                        COLOR_BORDE + ";" +

                        "-fx-border-width: 2;" +

                        "-fx-border-radius: 12;"
        );


        Label titulo =
                new Label("DEALER");


        titulo.setStyle(
                "-fx-font-size: 19px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;"
        );


        HBox cartas =
                crearMano(
                        juego.getDealer()
                );


        panel.getChildren().addAll(
                titulo,
                cartas
        );


        if (resultados) {

            Label puntos =
                    new Label(
                            "Puntos: " +
                                    juego.getDealer().getPuntos()
                    );


            puntos.setStyle(
                    "-fx-font-size: 15px;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;"
            );


            panel.getChildren().add(
                    puntos
            );
        }


        return panel;
    }


    // =========================================================
    // PANEL DE CADA JUGADOR
    // =========================================================

    private VBox crearPanelJugador(
            Jugador jugador,
            int numeroJugador,
            boolean resultados) {


        VBox panel =
                new VBox(6);


        panel.setAlignment(Pos.CENTER);


        panel.setPadding(
                new Insets(8)
        );


        panel.setMinWidth(400);
        panel.setMinHeight(155);


        panel.setStyle(
                "-fx-background-color: " +
                        COLOR_PANEL + ";" +

                        "-fx-background-radius: 12;" +

                        "-fx-border-color: " +
                        "rgba(255,255,255,0.35);" +

                        "-fx-border-width: 1;" +

                        "-fx-border-radius: 12;"
        );


        Label titulo =
                new Label(
                        "JUGADOR " +
                                numeroJugador
                );


        titulo.setStyle(
                "-fx-font-size: 17px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;"
        );


        HBox cartas =
                crearMano(jugador);


        Label puntos =
                new Label(
                        "Puntos: " +
                                jugador.getPuntos()
                );


        puntos.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-text-fill: white;"
        );


        panel.getChildren().addAll(
                titulo,
                cartas,
                puntos
        );


        if (resultados) {

            Label resultado =
                    new Label(
                            obtenerResultado(jugador)
                    );


            resultado.setStyle(
                    "-fx-font-size: 16px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #D4AF37;"
            );


            panel.getChildren().add(
                    resultado
            );
        }


        return panel;
    }


    // =========================================================
    // ACTUALIZAR MESA
    // =========================================================

    private void actualizarMesa(Stage stage) {

        if (juego.terminaronJugadores()) {

            mostrarDealer(stage);

        } else {

            mostrarMesa(stage);
        }
    }


    // =========================================================
    // TURNO DEL DEALER
    // =========================================================

    private void mostrarDealer(Stage stage) {

        for (CartaInglesa carta :
                juego.getDealer().getCartas().getElementos()) {

            carta.makeFaceUp();
        }

        juego.jugarDealer();

        mostrarResultados(stage);
    }


    // =========================================================
    // RESULTADOS
    // =========================================================

    private void mostrarResultados(Stage stage) {

        BorderPane mesa =
                new BorderPane();


        mesa.setStyle(
                "-fx-background-color: " +
                        COLOR_MESA + ";"
        );


        mesa.setPadding(
                new Insets(12)
        );


        // -----------------------------------------------------
        // TITULO
        // -----------------------------------------------------

        Label titulo =
                new Label("RESULTADOS");


        titulo.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;"
        );


        Label subtitulo =
                new Label(
                        "Resultado final de la partida"
                );


        subtitulo.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-text-fill: #D4AF37;"
        );


        VBox encabezado =
                new VBox(2);


        encabezado.setAlignment(
                Pos.CENTER
        );


        encabezado.getChildren().addAll(
                titulo,
                subtitulo
        );


        // -----------------------------------------------------
        // DEALER
        // -----------------------------------------------------

        VBox panelDealer =
                crearPanelDealer(true);


        VBox superior =
                new VBox(7);


        superior.setAlignment(
                Pos.CENTER
        );


        superior.getChildren().addAll(
                encabezado,
                panelDealer
        );


        mesa.setTop(superior);


        // -----------------------------------------------------
        // JUGADORES
        // -----------------------------------------------------

        GridPane jugadoresGrid =
                new GridPane();


        jugadoresGrid.setAlignment(
                Pos.CENTER
        );


        jugadoresGrid.setHgap(12);
        jugadoresGrid.setVgap(10);


        jugadoresGrid.setPadding(
                new Insets(6, 5, 6, 5)
        );


        for (int i = 0;
             i < juego.getJugadores().size();
             i++) {


            Jugador jugador =
                    juego.getJugadores().get(i);


            VBox panelJugador =
                    crearPanelJugador(
                            jugador,
                            i + 1,
                            true
                    );


            int columna = i % 2;
            int fila = i / 2;


            jugadoresGrid.add(
                    panelJugador,
                    columna,
                    fila
            );
        }


        mesa.setCenter(
                jugadoresGrid
        );


        // -----------------------------------------------------
        // BOTON NUEVA PARTIDA
        // -----------------------------------------------------

        Button nuevaPartida =
                crearBotonJuego(
                        "NUEVA PARTIDA"
                );


        nuevaPartida.setPrefWidth(
                180
        );


        nuevaPartida.setOnAction(e -> {

            mostrarSeleccionJugadores(
                    stage
            );
        });


        HBox botones =
                new HBox();


        botones.setAlignment(
                Pos.CENTER
        );


        botones.setPadding(
                new Insets(8, 0, 0, 0)
        );


        botones.getChildren().add(
                nuevaPartida
        );


        mesa.setBottom(
                botones
        );


        // -----------------------------------------------------
        // ESCENA
        // -----------------------------------------------------

        Scene scene =
                new Scene(
                        mesa,
                        950,
                        680
                );


        stage.setScene(scene);

        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setResizable(true);

        stage.show();
    }


    // =========================================================
    // OBTENER RESULTADO
    // =========================================================

    private String obtenerResultado(
            Jugador jugador) {


        int puntosDealer =
                juego.getDealer().getPuntos();


        if (jugador.estaBusto()) {

            return "PERDEDOR - BUST";

        } else if (juego.getDealer().estaBusto()) {

            return "GANADOR";

        } else if (jugador.getPuntos() > puntosDealer) {

            return "GANADOR";

        } else if (jugador.getPuntos() < puntosDealer) {

            return "PERDEDOR";

        } else {

            return "EMPATE";
        }
    }


    // =========================================================
    // CREAR MANO DE CARTAS
    // =========================================================

    private HBox crearMano(
            Jugador jugador) {


        HBox mano =
                new HBox(7);


        mano.setAlignment(
                Pos.CENTER
        );


        for (CartaInglesa carta :
                jugador.getCartas().getElementos()) {

            StackPane cartaVisual =
                    crearCartaVisual(carta);

            mano.getChildren().add(
                    cartaVisual
            );
        }


        return mano;
    }


    // =========================================================
    // CREAR CARTA VISUAL
    // =========================================================

    private StackPane crearCartaVisual(
            CartaInglesa carta) {


        Rectangle fondo =
                new Rectangle(
                        60,
                        82
                );


        fondo.setArcWidth(10);
        fondo.setArcHeight(10);


        fondo.setFill(
                Color.WHITE
        );


        fondo.setStroke(
                Color.web("#222222")
        );


        fondo.setStrokeWidth(2);


        String textoCarta =
                carta.toString();


        Label texto =
                new Label(
                        textoCarta
                );


        texto.setStyle(
                "-fx-font-size: 23px;" +
                        "-fx-font-weight: bold;"
        );


        if (textoCarta.contains("♥")
                || textoCarta.contains("♦")) {

            texto.setTextFill(
                    Color.RED
            );

        } else {

            texto.setTextFill(
                    Color.BLACK
            );
        }


        StackPane cartaVisual =
                new StackPane();


        cartaVisual.getChildren().addAll(
                fondo,
                texto
        );


        return cartaVisual;
    }


    // =========================================================
    // BOTONES DEL MENU
    // =========================================================

    private Button crearBotonMenu(
            String texto) {


        Button boton =
                new Button(texto);


        boton.setPrefSize(
                230,
                45
        );


        boton.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-color: white;" +
                        "-fx-text-fill: #0B3D5C;" +
                        "-fx-background-radius: 8;"
        );


        return boton;
    }


    // =========================================================
    // BOTONES DEL JUEGO
    // =========================================================

    private Button crearBotonJuego(
            String texto) {


        Button boton =
                new Button(texto);


        boton.setPrefHeight(
                40
        );


        boton.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-color: white;" +
                        "-fx-text-fill: #0B3D5C;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 7 18 7 18;"
        );


        return boton;
    }


    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args) {

        launch(args);
    }
}