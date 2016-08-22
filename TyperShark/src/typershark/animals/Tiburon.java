/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.animals;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import typershark.constantes.ConstantesPuntos;
import typershark.people.Jugador;

/**
 *
 * @author Danilo Torres
 */
public class Tiburon extends AnimalMarino {
    
    public Tiburon(String palabra, long velocidad) {
        super("images/components/tiburonBlanco400x400.png", palabra, velocidad);
        
    }
    
    

    @Override
    public void aumentarPuntos(Jugador jugador) {
        jugador.setPuntos(jugador.getPuntos() + ConstantesPuntos.PUNTOS_TIBURON);
    }
    
    
}
