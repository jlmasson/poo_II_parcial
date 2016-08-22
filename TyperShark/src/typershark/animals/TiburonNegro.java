/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.animals;

import javafx.scene.image.Image;
import typershark.people.Jugador;

/**
 *
 * @author Danilo Torres
 */
public class TiburonNegro extends AnimalMarino {
    public TiburonNegro(String palabra, long velocidad) {
        super("images/components/Tiburon-negro-400x240.png", palabra, velocidad);
        
    }

    @Override
    public void aumentarPuntos(Jugador jugador) {
        
    }
}
