/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.people;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

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
        this.puntos = 10000;
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
    public boolean equals(Object o) {
        Jugador j = (Jugador) o;
        return (this.nickname.equals(j.nickname) && this.puntos == j.puntos);
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
    
    public static LinkedList<Jugador> cargarJugadorXPuntos() {
        LinkedList<Jugador> jugadores = new LinkedList<>();
        File archivo = new File("src/puntajes/puntajes.txt");
        if (archivo.isFile()) {
            try {
                Scanner sc = new Scanner(archivo);
                sc.useDelimiter("\n");

                while(sc.hasNext()) {
                    String linea = sc.nextLine();
                    String[] campos = linea.split("\\|");
                    jugadores.add(new Jugador(campos[0], Integer.parseInt(campos[1])));
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Archivo no encontrado.");
            }
        }
        return jugadores;
    }
    
    
    
}
