/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.animals;

import java.util.ArrayList;
import typershark.constantes.Constantes;
import typershark.constantes.ConstantesDesplazamientos;
import typershark.constantes.ConstantesPuntos;
import typershark.panels.Mar;
import typershark.people.Jugador;

/**
 *
 * @author Danilo Torres
 */
public class Pulpo extends AnimalMarino {
    private String palabra;
    
    
    public Pulpo(Mar mar, ArrayList<String> palabrasJuego) {
        super(mar, "images/components/pulpo.gif", palabrasJuego,
                ConstantesDesplazamientos.VELOCIDAD_INICIAL*ConstantesDesplazamientos.MULTIPLICADOR_TIBURON);

        int aleatorio = (int) (Math.random()*palabrasJuego.size());
        palabra = palabrasJuego.get(aleatorio);
        super.setTextoEnPantalla(palabra);
    }
    
    
    @Override
    public void aumentarPuntos(Jugador jugador) {
        jugador.setPuntos(jugador.getPuntos() + ConstantesPuntos.PUNTOS_PODER);
    }

    @Override
    public void quitarVidas(Jugador jugador) {
        if (this.getRoot().getLayoutX() <= 0 && !this.isAlive()) {
            if(jugador.getNumVidas() > 0){
            jugador.setNumVidas(jugador.getNumVidas() - 1);
            }
        }
    }
    
    @Override
    public void matarAnimal() {
        super.matarAnimal();
    }

}
