/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.animals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import javafx.application.Platform;
import javafx.scene.image.Image;
import typershark.people.Jugador;

/**
 *
 * @author Danilo Torres
 */
public class TiburonNegro extends AnimalMarino {
    private LinkedList<String> palabras;
    
    public TiburonNegro(ArrayList<String> palabrasJuego, long velocidad) {
        super("images/components/tiburoncin2.png", palabrasJuego, velocidad);
        this.palabras = new LinkedList<>();
        Random r = new Random();
        //int numPalabras = r.nextInt(2) + 2;
        int numPalabras = (int) (Math.random()*2  +2);
        for(int i = 0; i<numPalabras; i++){
            //int aleatorio = r.nextInt(palabrasJuego.size());
            int aleatorio = (int) (Math.random()*palabrasJuego.size());
            palabras.add(palabrasJuego.get(aleatorio));
        }
        super.setTextoEnPantalla(palabras.get(0));
        
    }

    @Override
    public void aumentarPuntos(Jugador jugador) {
        
    }
    
    
    
    
    /**
    public void run() {
        try {
            while (super.isAlive() && TiburonNegro.super.getImagen().getLayoutX() > 0){ //&&
                                                //!palabras.isEmpty()) {
                //synchronized(this);
                synchronized(this) {
                    Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("SI FUNCAAAAAAAA");
                        
                        //TiburonNegro.super.getImagen().setLayoutX(TiburonNegro.super.getImagen().getLayoutX() - 10);
                    }
                });
                Thread.sleep(200);
                }
                
                
            }
            
        } catch (InterruptedException ex) {
        }
    }
    **/
    
    
    
    public LinkedList<String> getPalabras(){
        return this.palabras;
    }


    public void agregarTextos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
