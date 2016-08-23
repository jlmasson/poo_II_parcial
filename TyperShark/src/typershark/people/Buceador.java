/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.people;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import typershark.panels.Mar;

/**
 *
 * @author Galo Castillo
 */
public class Buceador implements Runnable{
    private ImageView imagenBuceador;
    private Mar mar;
    
    
    public ImageView getImagenBuceador(){
        return this.imagenBuceador;
    }
    
    public Buceador(Mar mar){
        this.imagenBuceador = new ImageView(new Image("images/components/buceador2.gif"));
        this.mar = mar;
    }
    
    public Buceador(){
        this.imagenBuceador = new ImageView(new Image("images/components/buceador2.gif"));

    }

    @Override
    public void run() {

        try {
            while (this.imagenBuceador.getLayoutY() < 400 && 
                    this.mar.getRoot().getChildren().contains(this.imagenBuceador)) {
                //synchronized(this);
                synchronized(this) {
                    Platform.runLater(() -> {
                        this.imagenBuceador.setLayoutY(this.imagenBuceador.getLayoutY() + 3);
                        if(this.imagenBuceador.getLayoutY() >= 400){
                            Buceador.this.mar.setSiguienteNivel();
                        }
                        if(mar.getJugador().getNumVidas()<= 0){
                            mar.setGameOver();
                        }
                    });
                Thread.sleep(250);
                } 
            }
            
        } catch (InterruptedException ex) {
        }
    
    }
}
