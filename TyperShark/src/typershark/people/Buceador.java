/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.people;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Galo Castillo
 */
public class Buceador {
    private Image imagen;
    private ImageView imagenView;
    
    
    public Buceador(double posX, double posY){
        this.imagen = new Image("buceador.gif");
        this.imagenView = new ImageView(this.imagen);
        this.imagenView.setLayoutX(posX);
        this.imagenView.setLayoutY(posY);
        
    }
}
