/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.panels;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Jose Masson
 */
public class AcercaDe {
    private BorderPane root;
    
    public AcercaDe() {
        this.root = new BorderPane();
        this.root.getStylesheets().add("styles/styles.css");
        this.root.getStyleClass().add("acercaDe");
        this.setupTitlePane();
        this.setupText();
    }

    public BorderPane getRoot() {
        return root;
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }
    
    private void setupTitlePane() {
        VBox title;
        title = new VBox();
        title.setAlignment(Pos.CENTER);
        Label titlePane;
        titlePane = new Label("Acerca de\nTyperShark II");
        titlePane.getStyleClass().add("labelAcercaDe");
        DropShadow ds;
        ds = this.setupShadow();
        titlePane.setEffect(ds);
        titlePane.setTextAlignment(TextAlignment.CENTER);
        title.getChildren().add(titlePane);
        this.root.setTop(title);
    }
    
    private void setupText() {
        VBox center;
        center = new VBox();
        center.setAlignment(Pos.CENTER);
        Label espol;
        espol = new Label();
        espol.setText("Escuela Superior Politécnica del Litoral");
        espol.setTextAlignment(TextAlignment.CENTER);
        espol.getStyleClass().add("labelEspol");

        
        Label poo;
        poo = new Label();
        poo.setText("Programación Orientada a Objetos - II Parcial\n2016 - I Término");
        poo.getStyleClass().add("labelPoo");
        poo.setTextAlignment(TextAlignment.CENTER);
        
        Label elaborado;
        elaborado = new Label();
        elaborado.setText("Elaborado por:\nGalo Castillo\nJosé Luis Massón\nDanilo Torres");
        elaborado.getStyleClass().add("labelAutores");
        elaborado.setTextAlignment(TextAlignment.CENTER);
        
        
        center.getChildren().addAll(espol, poo, elaborado);
        this.root.setCenter(center);
    }
    
    private DropShadow setupShadow() {
        DropShadow ds = new DropShadow();
        ds.setOffsetY(5.0f);
        ds.setOffsetX(-5.0f);
        ds.setColor(Color.color(0,0,0));
        return ds;
    }
    
}
