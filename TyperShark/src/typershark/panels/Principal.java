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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import typershark.handlers.ClickHandler;

/**
 *
 * @author Jose Masson
 */
public class Principal {
    private Button jugar;
    private Button instrucciones;
    private Button puntajes;
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
        this.title.setText("TyperShark");
        this.title.getStyleClass().add("labelPrincipal");
        this.title.setEffect(dropShadow);
        
        this.root.getChildren().add(title);
    }
    
    private void setupButtons(Stage principal, DropShadow dropShadow) {
        this.jugar = new Button("Comenzar Partida");
        this.instrucciones = new Button("Leer instrucciones");
        this.puntajes = new Button("Mostrar mejores puntajes");
        this.salir = new Button("Salir");
        
        jugar.getStyleClass().add("boton");
        instrucciones.getStyleClass().add("boton");
        puntajes.getStyleClass().add("boton");
        salir.getStyleClass().add("boton");
        
        jugar.setEffect(dropShadow);
        instrucciones.setEffect(dropShadow);
        puntajes.setEffect(dropShadow);
        salir.setEffect(dropShadow);
        
        jugar.setOnAction(new ClickMenu(principal, 2));
        salir.setOnAction(new Exit());
        
        this.root.getChildren().addAll(jugar, instrucciones, puntajes, salir);
    }
    
    private DropShadow setupShadow() {
        DropShadow ds = new DropShadow();
        ds.setOffsetY(5.0f);
        ds.setOffsetX(-8.0f);
        ds.setColor(Color.color(0,0,0));
        return ds;
    }
    
    private class ClickMenu implements EventHandler<ActionEvent> {

        Stage stage;
        int numOpcion;

        public ClickMenu(Stage primary, int numOpcion) {
            stage = primary;
            this.numOpcion = numOpcion;
        }

        @Override
        public void handle(ActionEvent event) {
            if (this.numOpcion == 2) {
                Stage second;
                second = new Stage();
                HBox root = new HBox();

                Button botonPrueba = new Button("Boton Previo");
                botonPrueba.setOnAction(new ClickHandler(stage, second));
                //botonPrueba.setOnAction(new ClickHandler());

                root.getChildren().add(botonPrueba);

                Scene scene = new Scene(root, 800, 600);
                second.setScene(scene);
                second.show();
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
