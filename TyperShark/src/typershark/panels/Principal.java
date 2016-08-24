/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.panels;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import typershark.handlers.ClickHandler;
import typershark.handlers.ClickHandlerMar;
import typershark.people.Jugador;

/**
 *
 * @author Jose Masson
 */
public class Principal {
    private Button jugar;
    private Button instrucciones;
    private Button puntajes;
    private Button acercaDe;
    private Button salir;
    private Label title;
    private VBox root;
    private ArrayList<String> palabrasJuego;
    
    public Principal(Stage principal) {
        Principal.playSound("game_menu.mp3", true);
        this.root = new VBox();
        this.root.setAlignment(Pos.CENTER);
        DropShadow ds = this.setupShadow();
        this.setupTitleGame(ds);
        this.root.getStylesheets().add("styles/styles.css");
        this.root.getStyleClass().add("root");
        this.setupButtons(principal, ds);
    }
    
    public VBox getRoot() {
        return root;
    }

    public void setRoot(VBox root) {
        this.root = root;
    }
    
    private void setupTitleGame(DropShadow dropShadow) {
        this.title = new Label();
        this.title.setText("TyperShark II");
        this.title.getStyleClass().add("labelPrincipal");
        this.title.setEffect(dropShadow);
        this.root.getChildren().add(title);
    }
    
    private void setupButtons(Stage principal, DropShadow dropShadow) {
        this.jugar = new Button("Comenzar Partida");
        this.instrucciones = new Button("Leer instrucciones");
        this.puntajes = new Button("Mostrar mejores puntajes");
        this.acercaDe = new Button("Acerca de");
        this.salir = new Button("Salir");
        
        this.jugar.getStyleClass().add("boton");
        this.instrucciones.getStyleClass().add("boton");
        this.puntajes.getStyleClass().add("boton");
        this.acercaDe.getStyleClass().add("boton");
        this.salir.getStyleClass().add("boton");
        
        this.jugar.setEffect(dropShadow);
        this.instrucciones.setEffect(dropShadow);
        this.puntajes.setEffect(dropShadow);
        this.acercaDe.setEffect(dropShadow);
        this.salir.setEffect(dropShadow);
        
        this.jugar.setOnMouseClicked(new ClickMenu(principal, 1));
        this.instrucciones.setOnMouseClicked(new ClickMenu(principal, 2));
        this.puntajes.setOnMouseClicked(new ClickMenu(principal, 3));
        this.acercaDe.setOnMouseClicked(new ClickMenu(principal, 4));
        this.salir.setOnMouseClicked(new Exit());
        
        this.root.getChildren().addAll(jugar, instrucciones, puntajes, acercaDe, salir);
    }
    
    private DropShadow setupShadow() {
        DropShadow ds = new DropShadow();
        ds.setOffsetY(5.0f);
        ds.setOffsetX(-5.0f);
        ds.setColor(Color.color(0,0,0));
        return ds;
    }
    
    private class ClickMenu implements EventHandler<MouseEvent> {

        private Stage stage;
        private int numOpcion;

        public ClickMenu(Stage primary, int numOpcion) {
            this.stage = primary;
            this.numOpcion = numOpcion;
        }

        @Override
        public void handle(MouseEvent event) {
            Principal.playSound("button_sound.mp3", false);
            switch (this.numOpcion) {
                case 1: 
                    TextInputDialog dialog = new TextInputDialog();
                    
                    dialog.setTitle("Bienvenido a TyperShark 2.0");
                    dialog.setHeaderText("Nickname");
                    dialog.setContentText("Ingresa tu nickname:");

                    Optional<String> result = dialog.showAndWait();
                    
                    while (result.isPresent() && result.get().trim().isEmpty()) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Ingreso no válido");
                        alert.setHeaderText("Nickname no válido");
                        alert.setContentText("No se permiten nicknames vacíos");
                        alert.showAndWait();
                        result = dialog.showAndWait();
                    }
                    
                    if (result.isPresent()){
                        Jugador jugador;
                        jugador = new Jugador(result.get());
                        Stage game;
                        game = new Stage();
                        Mar mar = new Mar(jugador);
                        mar.getRoot().setBottom(Principal.this.setupBotonPrevio(this.stage, game, mar));
                        Scene gameScene = new Scene(mar.getRoot(), 800, 600);
                        game.initStyle(StageStyle.UNDECORATED);
                        game.setScene(gameScene);
                        game.setOnCloseRequest((WindowEvent t) -> {
                            Platform.exit();
                            System.exit(0);
                        });
                        game.show();
                        this.stage.close();
                    }

                    
                    
                    break;
                
                case 2:
                    //Colocar Instrucciones
                    Stage instructions;
                    instructions = new Stage();
                    Instrucciones ins = new Instrucciones();
                    ins.getRoot().setBottom(Principal.this.setupBotonPrevio(this.stage, instructions));
                    Scene instrucScene = new Scene(ins.getRoot(), 800, 600);
                    instructions.setScene(instrucScene);
                    instructions.show();
                    this.stage.close();
                    break;
                
                case 3:
                    Stage scores = new Stage();
                    Puntajes punt = new Puntajes();
                    punt.getRoot().setBottom(Principal.this.setupBotonPrevio(this.stage, scores));
                    Scene scoreScene = new Scene(punt.getRoot(), 800, 600);
                    scores.setScene(scoreScene);
                    Principal.this.setStageStyles(scores, "Puntajes Máximos");
                    scores.show();
                    this.stage.close();
                    break;
                    
                case 4:
                    Stage about = new Stage();
                    AcercaDe acerca = new AcercaDe();
                    acerca.getRoot().setBottom(Principal.this.setupBotonPrevio(this.stage, about));
                    Scene aboutScene = new Scene(acerca.getRoot(), 800, 650);
                    about.setScene(aboutScene);
                    Principal.this.setStageStyles(about, "Acerca de TyperShark II");
                    about.show();
                    this.stage.close();
                    break;
            }

        }
    }
    
    private class Exit implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            try {
                Principal.playSound("button_sound.mp3", false);
                Thread.sleep(50);
                System.exit(0);
            } catch (InterruptedException ex) {
            }
        }
        
    }
    
    public static ArrayList<String> cargarPalabras() throws FileNotFoundException {
        ArrayList<String> palabras = new ArrayList<>();
        File archivo = new File("src/words/words.txt");
        try (Scanner sc = new Scanner(archivo)) {
            sc.useDelimiter("\n");
            while(sc.hasNext()) {
                String linea = sc.nextLine();
                linea = linea.trim();
                palabras.add(linea);
            }
        }
        return palabras;
    }
    
    private void setStageStyles(Stage primaryStage, String title) {
        primaryStage.setResizable(false);
        Image applicationIcon = new Image(getClass().getClassLoader().getResource("images/title/icono.png").toExternalForm());
        primaryStage.getIcons().add(applicationIcon);
        primaryStage.setTitle(title);
            
    }
    
    public static void playSound(String archivo, boolean indefinido) {
        File file = new File("src/sounds/" + archivo);
        MediaPlayer clip = new MediaPlayer(new Media(file.toURI().toString()));
        if (indefinido) {
            clip.setCycleCount(MediaPlayer.INDEFINITE);
        }
        clip.play();
    }
    
    private Button setupBotonPrevio(Stage principal, Stage opcionElegir) {
        Button previo;
        previo = new Button("< Volver al Menú Principal");
        previo.getStyleClass().add("botonRegresar");
        previo.setOnMouseClicked(new ClickHandler(principal, opcionElegir));
        DropShadow ds = Principal.this.setupShadow();
        previo.setEffect(ds);
        return previo;
    }
    
    private Button setupBotonPrevio(Stage principal, Stage opcionElegir, Mar mar) {
        Button previo;
        previo = new Button("< Volver al Menú Principal");
        previo.getStyleClass().add("botonRegresar");
        previo.setOnMouseClicked(new ClickHandlerMar(principal, opcionElegir, mar));
        DropShadow ds = Principal.this.setupShadow();
        previo.setEffect(ds);
        return previo;
    }
}
