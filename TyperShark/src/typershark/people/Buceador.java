/*
* @(#)Tiburon.java 4.0 28/8/2016
*
* Copyright (c) 2016 Galo Castillo, Jose Luis Masson & Danilo Torres.
* Escuela Superior Politécnica del Litoral. Guayaquil, Ecuador.
* Todos los Derechos Reservados.
*
*/
package typershark.people;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import typershark.panels.Mar;

/**
 * Esta clase define objetos de tipo Buceador.
 * @author: Galo Castillo, Jose Luis Masson, Danilo Torres
 * @version: 4.0 28/8/2016
 */
public class Buceador implements Runnable{
    
    /** Imagen utilizada para mostrar los desplazamientos del buceador en el
     escenario del mar.*/
    private ImageView imagenBuceador;
    
    /** Nodo raiz que contiene los elementos utilizado en el curso del juego.*/
    private Mar mar;
    
    /**
     * Método que permite obtener la imagen utilizada para visualizar los movimientos
     * realizados por el buceador.
     * @return La imagen del buceador no.
     */
    public ImageView getImagenBuceador(){
        return this.imagenBuceador;
    } //Cierre del metodo.
    
    /**
     * Constructor para el objeto de tipo Buceador.
     * Obtiene una imagen que es uilizada para visualizar al buceador en el curso
     * del juego.
     * @param mar El parámetro mar corresponde al panel organizador del mar en el 
     * cual se encontrara el buceador.
     */
    public Buceador(Mar mar){
        this.imagenBuceador = new ImageView(new Image("images/components/buceador2.gif"));
        this.mar = mar;
    } //Cierre del consttructor
    
    /**
     * Constructor por defectp para el objeto de tipo Buceador.
     * Obtiene una imagen que es uilizada para visualizar al buceador en el curso
     * del juego.
     */
    public Buceador(){
        this.imagenBuceador = new ImageView(new Image("images/components/buceador2.gif"));
    } //Cierre del constructor

    /**
     * Método que permite obtener el movimiento independiente del buceador con respecto
     * a las demás componentes.
     * realizados por el buceador.
     * */
    @Override
    public void run() {

        try {
            while (this.imagenBuceador.getLayoutY() < 400 && 
                    this.mar.getRoot().getChildren().contains(this.imagenBuceador)) {
                //synchronized(this);
                synchronized(this) {
                    Platform.runLater(() -> {
                        this.imagenBuceador.setLayoutY(this.imagenBuceador.getLayoutY() + 2);
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
