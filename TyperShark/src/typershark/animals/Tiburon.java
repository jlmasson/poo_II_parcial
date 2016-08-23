/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.animals;

import java.util.ArrayList;
import typershark.constantes.ConstantesPuntos;
import typershark.panels.Mar;
import typershark.people.Jugador;

/**
 *
 * @author Danilo Torres
 */
public class Tiburon extends AnimalMarino {
    private String palabra;
    
    public Tiburon(Mar mar, ArrayList<String> palabrasJuego, long velocidad) {
        super(mar, "images/components/tiburonBlanco400x400.png", palabrasJuego, velocidad);

        
        //int aleatorio = r.nextInt(palabrasJuego.size());
        int aleatorio = (int) (Math.random()*palabrasJuego.size());
        palabra = palabrasJuego.get(aleatorio);
        super.setTextoEnPantalla(palabra);
    }
    
    

    @Override
    public void aumentarPuntos(Jugador jugador) {
        jugador.setPuntos(jugador.getPuntos() + ConstantesPuntos.PUNTOS_TIBURON);
    }

    @Override
    public void quitarVidas(Jugador jugador) {
        if (this.getRoot().getLayoutX() <= 0 && !this.isAlive()) {
            jugador.setNumVidas(jugador.getNumVidas() - 1);
        }
    }
    
    @Override
    public void matarAnimal() {
        super.matarAnimal();
    }

}
