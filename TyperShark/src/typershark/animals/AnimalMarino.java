/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.animals;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 *
 * @author Danilo Torres
 */
public class AnimalMarino {
    private Pane pane;
    private ImageView imagen;
    private Label palabra;
    
    public AnimalMarino(Image imagen, String palabra) {
        this.imagen = new ImageView(imagen);
        this.palabra = new Label(palabra);
        this.pane = new Pane();
        pane.getChildren().addAll(this.imagen, this.palabra);
    }
    
    public Pane getRoot() {
        return this.pane;
    }
}
