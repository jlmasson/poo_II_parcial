/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.animals;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import typershark.constantes.ConstantesPuntos;
import typershark.people.Jugador;

/**
 *
 * @author Danilo Torres
 */
public class Tiburon extends AnimalMarino {
    private String palabra;
    
    public Tiburon(ArrayList<String> palabrasJuego, long velocidad) {
        super("images/components/tiburonBlanco400x400.png", palabrasJuego, velocidad);

        
        //int aleatorio = r.nextInt(palabrasJuego.size());
        int aleatorio = (int) (Math.random()*palabrasJuego.size());
        palabra = palabrasJuego.get(aleatorio);
        super.setTextoEnPantalla(palabra);
    }
    
    

    @Override
    public void aumentarPuntos(Jugador jugador) {
        jugador.setPuntos(jugador.getPuntos() + ConstantesPuntos.PUNTOS_TIBURON);
    }



    
    
    
    
}
