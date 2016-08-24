/*
* @(#)Tiburon.java 4.0 28/8/2016
*
* Copyright (c) 2016 Galo Castillo, Jose Luis Masson & Danilo Torres.
* Escuela Superior Politécnica del Litoral. Guayaquil, Ecuador.
* Todos los Derechos Reservados.
*
*/
package typershark.animals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import typershark.constantes.ConstantesDesplazamientos;
import typershark.panels.Mar;
import typershark.people.Jugador;

/**
 * Esta clase define objetos de tipo TiburonNegro que es subclase de la
 * superclase AnimalMarino.
 * @author: Galo Castillo, Jose Luis Masson, Danilo Torres
 * @version: 4.0 28/8/2016
 */
public class TiburonNegro extends AnimalMarino {
    
    /** Lista de palabras que el animal contiene para ser tipeadas */
    private LinkedList<String> palabras;
    
    
    /**
     * Constructor para el objeto de tipo TiburonNegro que es una subclase de la
     * superclase AnimalMarino.
     * Genera un Tiburon relacionado con un mar al cual pertenece y el jugador
     * capaz de elimianrlo..
     * @param mar El parámetro mar corresponde al panel organizador del mar. 
     * @param palabrasJuego El parametro palabrasJuego corresponde a la lista
     * que contiene cargadas previamente.
     */
    public TiburonNegro(Mar mar, ArrayList<String> palabrasJuego) {
        super(mar, "images/components/tiburonNegroFINAL.png", palabrasJuego,
                ConstantesDesplazamientos.VELOCIDAD_INICIAL*ConstantesDesplazamientos.MULTIPLICADOR_TIBURON);
        
        this.palabras = new LinkedList<>();
        int numPalabras = (int) (Math.random()*2  +2);
        for(int i = 0; i<numPalabras; i++){
            int aleatorio = (int) (Math.random()*palabrasJuego.size());
            palabras.add(palabrasJuego.get(aleatorio));
        }
        super.setTextoEnPantalla(palabras.get(0));
    } // Cierre del constructor.

    @Override
    public void aumentarPuntos(Jugador jugador) {
        
    } //Cierre del metodo
    
    public void matarAnimal() {
        super.matarAnimal();
    } // Cierre del metodo.
    
    
    
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
    
    /**
     * Método que permite obtener la lista de palabras que posee el Tiburon.
     * @return La lista de palabras del Tiburon.
     */
    public LinkedList<String> getPalabras(){
        return this.palabras;
    } //Cierre del metodo

    /**
     *Metodo que disminuye las vidas del jugador a un número mínimo de cero,
     * un vez que el animal haya llegado hasta el buceador.
     * @param jugador
     */
    @Override
    public void quitarVidas(Jugador jugador) {
        if (this.getRoot().getLayoutX() <= 0 && !this.isAlive()) {
            if(jugador.getNumVidas() > 0){
                jugador.setNumVidas(jugador.getNumVidas() - 1);
            }
        }
    } //Cierre del metodo
} // Cierre de la clase
