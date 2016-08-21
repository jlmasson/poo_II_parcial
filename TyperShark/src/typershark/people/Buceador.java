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
    private ImageView imagenView;
    
    
    public Buceador(Image im, double posX, double posY){
        
        this.imagenView = new ImageView(im);
        
    }
    
    public ImageView getImagenView(){
        return this.imagenView;
    }
}
