/*
* @(#)Tiburon.java 4.0 24/8/2016
*
* Copyright (c) 2016 Galo Castillo, Jose Luis Masson & Danilo Torres.
* Escuela Superior Politécnica del Litoral. Guayaquil, Ecuador.
* Todos los Derechos Reservados.
*
*/
package typershark.animals;

import java.util.ArrayList;
import typershark.constantes.ConstantesDesplazamientos;
import typershark.constantes.ConstantesPuntos;
import typershark.panels.Mar;
import typershark.people.Jugador;

/**
 * Esta clase define objetos de tipo Tiburon que es subclase de la
 * superclase AnimalMarino.
 * @author: Galo Castillo, Jose Luis Masson, Danilo Torres
 * @version: 4.0 24/8/2016
 */
public class Tiburon extends AnimalMarino {
    
    /** Palabra que el animal contiene para ser tipeada */
    private String palabra;
    
    /**
     * Constructor para el objeto de tipo Tiburon que es una subclase de la
     * superclase AnimalMarino.
     * Genera un Tiburon relacionado con un mar al cual pertenece y el jugador
     * capaz de elimianrlo..
     * @param mar El parámetro mar corresponde al panel organizador del mar. 
     * que contiene cargadas previamente.
     * @param palabrasJuego
     */
    public Tiburon(Mar mar, ArrayList<String> palabrasJuego) {
        super(mar, "images/components/tiburonBlancoFINAL.png", palabrasJuego,
                ConstantesDesplazamientos.VELOCIDAD_INICIAL*ConstantesDesplazamientos.MULTIPLICADOR_TIBURON);

        int aleatorio = (int) (Math.random()*palabrasJuego.size());
        palabra = palabrasJuego.get(aleatorio);
        super.setTextoEnPantalla(palabra);
    } // Cierre del constructor
    
    /**
     *Metodo que permite incrementar los puntos del jugado cuando sea necesario.
     * @param jugador Es el jugador y actor primario del sistema.
     */
    @Override
    public void aumentarPuntos(Jugador jugador) {
        jugador.setPuntos(jugador.getPuntos() + ConstantesPuntos.PUNTOS_TIBURON);
    } // Cierre del metodo.

    /**
     *Metodo que disminuye las vidas del jugador a un número mínimo de cero,
     * una vez que el animal haya llegado hasta el buceador.
     * @param jugador
     */
    @Override
    public void quitarVidas(Jugador jugador) {
        if (this.getRoot().getLayoutX() <= 0 && !this.isAlive()) {
            if(jugador.getNumVidas() > 0){
                jugador.setNumVidas(jugador.getNumVidas() - 1);
            }
        }
    } // Cierre del metodo.
    
    @Override
    public void matarAnimal() {
        super.matarAnimal();
    } // CIerre del metodo.
} //Cierre de la clase
