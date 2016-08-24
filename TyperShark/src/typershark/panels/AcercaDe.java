/*
* @(#)AcercaDe.java 4.0 28/8/2016
*
* Copyright (c) 2016 Galo Castillo, Jose Luis Masson & Danilo Torres.
* Escuela Superior Politécnica del Litoral. Guayaquil, Ecuador.
* Todos los Derechos Reservados.
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
 * Esta clase define objetos de tipo Buceador.
 * @author: Galo Castillo, Jose Luis Masson, Danilo Torres
 * @version: 4.0 28/8/2016
 */
public class AcercaDe {
    /** Panel raiz de AcercaDe*/
    private BorderPane root;
    
    /**
     * Constructor para el objeto de tipo AcercaDe.
     * Crea un nuevo panel en el que hay un boton y un campo de texto con puntos y numero de vidas iniciales.
     */
    public AcercaDe() {
        this.root = new BorderPane();
        this.root.getStylesheets().add("styles/styles.css");
        this.root.getStyleClass().add("acercaDe");
        this.setupTitlePane();
        this.setupText();
    }// Cierre constructor.

    
    /**
     * Método que permite obtener el panel raiz.
     * */
    public BorderPane getRoot() {
        return root;
    } //Cierre del metodo.

    /**
     * Método que permite cambiar el panel raiz.
     * @param root El parametro root es la nueva raiz.
     * */
    public void setRoot(BorderPane root) {
        this.root = root;
    } //Cierre del metodo
    
    /**
     * Método que crea y edita el formato del título de AcercaDe.
     * */
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
    }//Cierre del metodo.
    
    /**
     * Método que muestra la información en el panel AcercaDe.
     * */
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
    }//Cierre del metodo.
    
    /**
     * Método que cambia el formato de la fuente en el panel AcercaDe.
     * */
    private DropShadow setupShadow() {
        DropShadow ds = new DropShadow();
        ds.setOffsetY(5.0f);
        ds.setOffsetX(-5.0f);
        ds.setColor(Color.color(0,0,0));
        return ds;
    }//Cierre del metodo.
    
}//Cierre de la clase.
