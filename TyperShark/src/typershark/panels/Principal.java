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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
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
        this.root = new VBox();
        this.root.setAlignment(Pos.CENTER);
        DropShadow ds = this.setupShadow();
        this.setupTitleGame(ds);
        this.root.getStylesheets().add("styles/styles.css");
        this.root.getStyleClass().add("root");
        this.setupButtons(principal, ds);
        this.setupWords();
        System.out.println(this.palabrasJuego);
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
        this.salir.setOnAction(new Exit());
        
        this.root.getChildren().addAll(jugar, instrucciones, puntajes, acercaDe, salir);
    }
    
    private void setupWords() {
        try {
            this.palabrasJuego = Principal.cargarPalabras();
        } catch (FileNotFoundException ex) {
            System.out.println("Carga de Palabras no exitosa");
        }
    }
    
    private DropShadow setupShadow() {
        DropShadow ds = new DropShadow();
        ds.setOffsetY(5.0f);
        ds.setOffsetX(-8.0f);
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
            switch (this.numOpcion) {
                case 2:
                    Stage third;
                    third = new Stage();
                    //HBox root = new HBox();
                    Button botonPrueba = new Button("<- Volver al Menú Principal");
                   
                    Mar mar = new Mar();
                    botonPrueba.setOnAction(new ClickHandlerMar(this.stage, third, mar));
                    mar.getRoot().setBottom(botonPrueba);
                    Scene scene = new Scene(mar.getRoot(), 800, 600);
                    third.setScene(scene);
                    third.show();
                    break;
                
                case 3:
                    // Modificar esto a futuro para presentación final
                    
                    Stage second;
                    second = new Stage();
                    //HBox root = new HBox();
                    botonPrueba = new Button("Boton Previo");
                    botonPrueba.setOnAction(new ClickHandler(stage, second));
                    Label instrucciones = new Label("El buceador acumula puntos a medida que desciende y por cada animal marino que desaparece.\n" +
                                            "Inicialmente dispone de 3 vidas, pero puede ganar vidas extra acumulando puntos. Al llegar al fondo\n" +
                                            "del mar se puede acumular mas puntaje tipeando la mayor cantidad de palabras posible. Con\n" +
                                            "suficiente puntaje acumulado el buceador no solo gana una vida, también puede utilizar parte de su\n" +
                                            "puntaje para eliminar a todos los animales marinos en la cercanía, presionando unicamente la tecla\n" +
                                            "ENTER.\n" +
                                            "El juego termina cuando el buceador pierde sus tres vidas. A medida que acumula puntaje el jugador\n" +
                                            "va avanzando a un siguiente nivel, en el cual los animales marinos aumentan su rapidez.");
                    instrucciones.setFont(new Font("Papyrus", 18));
                    instrucciones.setTextFill(Color.WHITE);
                    Instrucciones ins = new Instrucciones();
                    ins.getRoot().setTop(botonPrueba);
                    Scene scen1 = new Scene(ins.getRoot(), 800, 600);
                    second.setScene(scen1);
                    second.show();
                    break;
                
                case 4:
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
            System.exit(0);
        }
        
    }
    
    private static ArrayList<String> cargarPalabras() throws FileNotFoundException {
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
    
}
