
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hp
 */
public class Jugador {
    private String nickname;
    private int puntos;
    private int numVidas;
    private int ataquesDePiranhas;
    
    public Jugador(String nickname) {
        this.nickname = nickname;
        this.puntos = 0;
        this.numVidas = 3;
        this.ataquesDePiranhas = 0;
    }
    
    
}
