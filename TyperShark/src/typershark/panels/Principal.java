/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.panels;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import typershark.handlers.ClickHandler;
import typershark.handlers.ClickHandlerMar;

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
        
        this.jugar.setOnAction(new ClickMenu(principal, 2));
        this.instrucciones.setOnAction(new ClickMenu(principal, 3));
        this.puntajes.setOnAction(new ClickMenu(principal, 4));
        this.salir.setOnAction(new Exit());
        
        this.jugar.setFocusTraversable(false);
        this.instrucciones.setFocusTraversable(false);
        this.puntajes.setFocusTraversable(false);
        this.acercaDe.setFocusTraversable(false);
        this.salir.setFocusTraversable(false);
        
        
        this.root.getChildren().addAll(jugar, instrucciones, puntajes, acercaDe, salir);
    }
    
    private DropShadow setupShadow() {
        DropShadow ds = new DropShadow();
        ds.setOffsetY(5.0f);
        ds.setOffsetX(-5.0f);
        ds.setColor(Color.color(0,0,0));
        return ds;
    }
    
    private class ClickMenu implements EventHandler<ActionEvent> {

        private Stage stage;
        private int numOpcion;

        public ClickMenu(Stage primary, int numOpcion) {
            this.stage = primary;
            this.numOpcion = numOpcion;
        }

        @Override
        public void handle(ActionEvent event) {
            Principal.this.playSound("button_sound.mp3", false);
            switch (this.numOpcion) {
                case 2:
                    Stage third;
                    third = new Stage();
                    //HBox root = new HBox();
                    Button botonPrueba = new Button("Volver al Menú Principal");
                    Mar mar = new Mar();
                    botonPrueba.setOnAction(new ClickHandlerMar(this.stage, third, mar));
                    mar.getRoot().setBottom(botonPrueba);
                    Scene scene = new Scene(mar.getRoot(), 800, 600);
                    third.initStyle(StageStyle.UNDECORATED);
                    third.setScene(scene);
                    third.setOnCloseRequest((WindowEvent t) -> {
                        Platform.exit();
                        System.exit(0);
            });
                    third.show();
                    break;
                
                case 3:
                    // Modificar esto a futuro para presentación final
                    
                    
                    Stage second;
                    second = new Stage();
                    //HBox root = new HBox();
                    botonPrueba = new Button("Boton Previo");
                    botonPrueba.setOnAction(new ClickHandler(stage, second));
                    Instrucciones ins = new Instrucciones();
                    ins.getRoot().setTop(botonPrueba);
                    Scene scen1 = new Scene(ins.getRoot(), 800, 600);
                    second.setScene(scen1);
                    second.show();
                    break;
                
                case 4:
                    VBox title = new VBox();
                    title.setAlignment(Pos.CENTER);
                    Label titlePane = new Label("Puntajes Máximos");
                    titlePane.getStyleClass().add("labelPrincipal");
                    DropShadow ds = Principal.this.setupShadow();
                    titlePane.setEffect(ds);
                    title.getChildren().add(titlePane);
                    Stage fourth = new Stage();
                    //HBox root = new HBox();
                    botonPrueba = new Button("< Volver al Menú Principal");
                    botonPrueba.getStyleClass().add("botonRegresar");
                    botonPrueba.setFocusTraversable(false);
                    botonPrueba.setOnAction(new ClickHandler(stage, fourth));
                    Puntajes punt = new Puntajes();
                    punt.getRoot().setBottom(botonPrueba);
                    Scene scene2 = new Scene(punt.getRoot(), 800, 600);
                    fourth.setScene(scene2);
                    Principal.this.setStageStyles(fourth, "Puntajes Máximos");
                    punt.getRoot().setTop(title);
                    fourth.show();
                    break;
                default:
                    break;
            }
            stage.close();

        }
    }
    
    private class Exit implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            try {
                Principal.this.playSound("button_sound.mp3", false);
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
    
    private class MouseOverEvent implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            Principal.this.playSound("mouse_over.wav", false);
        }
        
    }
}
