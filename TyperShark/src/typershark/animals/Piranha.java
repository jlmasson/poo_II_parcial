/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.animals;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;
import typershark.constantes.ConstantesPuntos;
import typershark.people.Jugador;

/**
 *
 * @author Danilo Torres
 */
public class Piranha extends AnimalMarino {
    private char letra;
    
    public Piranha(ArrayList<String> palabrasJuego, long velocidad) {
        super("images/components/piranha.png",palabrasJuego, velocidad);
        Random r = new Random();
        //int aleatorio = r.nextInt(palabrasJuego.size());
        
        int aleatorio = (int) (Math.random()*palabrasJuego.size());
        letra = palabrasJuego.get(aleatorio).charAt(0);
        //letra = palabrasJuego.get(aleatorio);
        //super.setTextoEnPantalla(Character.toString(letra));
        super.setTextoEnPantalla(Character.toString(letra));
    }

    @Override
    public void aumentarPuntos(Jugador jugador) {
        jugador.setPuntos(jugador.getPuntos() + ConstantesPuntos.PUNTOS_PIRANIA);
    }


    public void agregarTextos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
