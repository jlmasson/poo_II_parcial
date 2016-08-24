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
import java.util.Random;
import javafx.scene.image.Image;
import typershark.constantes.ConstantesDesplazamientos;
import typershark.constantes.ConstantesPuntos;
import typershark.panels.Mar;
import typershark.people.Jugador;

/**
 * Esta clase define objetos de tipo Piranha que es subclase de la
 * superclase AnimalMarino.
 * @author: Galo Castillo, Jose Luis Masson, Danilo Torres
 * @version: 4.0 28/8/2016
 */
public class Piranha extends AnimalMarino {
    
    /** Caracter que el animal contiene para ser tipeadas */
    private char letra;
    
    /**
     * Constructor para el objeto de tipo Piranha que es una subclase de la
     * superclase AnimalMarino.
     * Genera una Piranha relacionado con un mar al cual pertenece y el jugador
     * capaz de elimianrlo..
     * @param mar El parámetro mar corresponde al panel organizador del mar. 
     * @param palabrasJuego El parametro palabrasJuego corresponde a la lista
     * que contiene cargadas previamente.
     */
    public Piranha(Mar mar, ArrayList<String> palabrasJuego) {
        super(mar, "images/components/piranhaFINAL.png",palabrasJuego,
                ConstantesDesplazamientos.VELOCIDAD_INICIAL*ConstantesDesplazamientos.MULTIPLICADOR_PIRANIA);

        int aleatorio = (int) (Math.random()*palabrasJuego.size());
        letra = palabrasJuego.get(aleatorio).charAt(0);
        super.setTextoEnPantalla(Character.toString(letra));
    }// Cierre del constructor

    /**
     *Metodo que permite incrementar los puntos del jugado cuando sea necesario.
     * @param jugador Es el jugador y actor primario del sistema.
     */
    @Override
    public void aumentarPuntos(Jugador jugador) {
        jugador.setPuntos(jugador.getPuntos() + ConstantesPuntos.PUNTOS_PIRANIA);
    }// Aumenta los puntos del jugador segun sea necesario.


    /**
     *Metodo que disminuye las vidas del jugador a un número mínimo de cero,
     * una vez que el animal haya llegado hasta el buceador.
     * @param jugador
     */
    public void quitarVidas(Jugador jugador) {
        if (this.getRoot().getLayoutX() == 0 && !this.isAlive()) {
            jugador.setAtaquesDePiranhas(jugador.getAtaquesDePiranhas() + 1);
//            if (jugador.getAtaquesDePiranhas() % 3 == 0 && jugador.getAtaquesDePiranhas() > 0) {
//                jugador.setAtaquesDePiranhas(0);
//                jugador.setNumVidas(jugador.getNumVidas() - 1);
//            }
              if(jugador.getAtaquesDePiranhas() == 3 && jugador.getNumVidas() > 0){
                  jugador.setNumVidas(jugador.getNumVidas() - 1);
                  jugador.setAtaquesDePiranhas(0);
              }
        }
    } //Cierre del metodo
    
    @Override
    public void matarAnimal() {
        super.matarAnimal();
    } // Cierre del metodo
} //Cierre de la clase
