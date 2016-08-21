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
    private ImageView imagenBuceador;
    public Buceador(Image im, double posX, double posY){
        
        this.imagenBuceador = new ImageView(im);
    }
    public ImageView getImagenBuceador(){
        return this.imagenBuceador;
    }
    
    public Buceador(){
        this.imagenBuceador = new ImageView(new Image("images/components/buceador2.gif"));
        
        
    }
}
