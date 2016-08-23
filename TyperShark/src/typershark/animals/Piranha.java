/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author Danilo Torres
 */
public class Piranha extends AnimalMarino {
    private char letra;
    
    public Piranha(Mar mar, ArrayList<String> palabrasJuego) {
        super(mar, "images/components/piranha.png",palabrasJuego,
                (long) (ConstantesDesplazamientos.VELOCIDAD_INICIAL*ConstantesDesplazamientos.MULTIPLICADOR_PIRANIA));

        int aleatorio = (int) (Math.random()*palabrasJuego.size());
        letra = palabrasJuego.get(aleatorio).charAt(0);
        super.setTextoEnPantalla(Character.toString(letra));
    }

    @Override
    public void aumentarPuntos(Jugador jugador) {
        jugador.setPuntos(jugador.getPuntos() + ConstantesPuntos.PUNTOS_PIRANIA);
    }


    public void agregarTextos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void quitarVidas(Jugador jugador) {
        if (this.getRoot().getLayoutX() == 0 && !this.isAlive()) {
            if (jugador.getAtaquesDePiranhas() % 3 == 0 && jugador.getAtaquesDePiranhas() > 0) {
                jugador.setAtaquesDePiranhas(0);
                jugador.setNumVidas(jugador.getNumVidas() - 1);
            }
            else {
                jugador.setAtaquesDePiranhas(jugador.getAtaquesDePiranhas() + 1);
            }
        }
    }
    
    @Override
    public void matarAnimal() {
        super.matarAnimal();
    }
}
