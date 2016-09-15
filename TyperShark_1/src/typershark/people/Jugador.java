/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.people;

/**
 *
 * @author Danilo Torres
 */
public class Jugador implements Comparable<Jugador>{
    private String nickname;
    private int puntos;
    private int numVidas;
    private int ataquesDePiranhas;
    
    public Jugador(String nickname) {
        this.nickname = nickname;
        this.puntos = 3000;
        this.numVidas = 3;
        this.ataquesDePiranhas = 0;
    }

    public Jugador(String nickname, int puntos) {
        this.nickname = nickname;
        this.puntos = puntos;
    }
    
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getNumVidas() {
        return numVidas;
    }

    public void setNumVidas(int numVidas) {
        this.numVidas = numVidas;
    }

    public int getAtaquesDePiranhas() {
        return ataquesDePiranhas;
    }

    public void setAtaquesDePiranhas(int ataquesDePiranhas) {
        this.ataquesDePiranhas = ataquesDePiranhas;
    }

    @Override
    public int compareTo(Jugador o) {
        if (this.puntos > o.puntos) {
            return -1;
        } else if (this.puntos == o.puntos) {
            return 0;
        }
        else {
            return 1;
        }
    }
    
    
    
    
}
