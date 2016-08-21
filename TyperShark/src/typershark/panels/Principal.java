/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.panels;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    
    public Principal(Stage principal) {
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
        this.salir.setOnAction(new Exit());
        
        this.root.getChildren().addAll(jugar, instrucciones, puntajes, acercaDe, salir);
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
                    Button botonPrueba = new Button("Volver al Menú Principal");
                   
                    Mar mar = new Mar(this.stage);
                    botonPrueba.setOnAction(new ClickHandlerMar(this.stage, third, mar));
                    mar.getRoot().setTop(botonPrueba);
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
    
}
