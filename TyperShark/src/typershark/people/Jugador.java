/*
* @(#)Jugador.java 4.0 28/8/2016
*
* Copyright (c) 2016 Galo Castillo, Jose Luis Masson & Danilo Torres.
* Escuela Superior Politécnica del Litoral. Guayaquil, Ecuador.
* Todos los Derechos Reservados.
*
*/
package typershark.people;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Esta clase define objetos de tipo Buceador.
 * @author: Galo Castillo, Jose Luis Masson, Danilo Torres
 * @version: 4.0 28/8/2016
 */
public class Jugador implements Comparable<Jugador>{
    /** Sobrenombre que recibe el jugador para identificarlo*/
    private String nickname;
    /** Puntaje obtenido por el jugador a lo largo del juego.*/
    private int puntos;
    /** Numero de ataques de tiburones necesarios para perder. En el caso de 
     las piranhas son numVidas x 3*/
    private int numVidas;
    /** Acumulador de ataques de piranhas. Se pierde una vida por cada trees, 
     * .*/
    private int ataquesDePiranhas;
    
    /**
     * Constructor para el objeto de tipo Jugador.
     * Crea un nuevo jugador con puntos y numero de vidas iniciales.
     * @param nickname El parámetro nickname utilizado a lo largo del juego
     * para ser utilizado como identificador de este en el juego.
     */
    public Jugador(String nickname) {
        this.nickname = nickname;
        this.puntos = 0;
        this.numVidas = 3;
        this.ataquesDePiranhas = 0;
    } //Cierre del constructor

    /**
     * Constructor para el objeto de tipo Jugador.
     * Crea un nuevo jugador con puntos y numero de vidas iniciales.
     * @param nickname El parámetro nickname utilizado a lo largo del juego
     * para ser utilizado como identificador de este en el juego.
     * @param puntos Numero de puntos iniciales del jugador.
     */
    public Jugador(String nickname, int puntos) {
        this.nickname = nickname;
        this.puntos = puntos;
    } // Cierre del constructor
    
    /**
     * Método que permite obtener el nickname del usuario.
     * */
    public String getNickname() {
        return nickname;
    } //Cierre del metodo.

    
    /**
     * Método que permite cambir el nickname del usuario.
     * @param nickname El parametro nickname es el nuevo sobrenombre que toma
     * el jugador.
     * */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }//Cierre del metodo.

    /**
     * Método que permite obtener los puntos ganados por del usuario.
     * */
    public int getPuntos() {
        return puntos;
    }//Cierre del metodo.

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }//Cierre del metodo.

        /**
     * Método que permite obtener el numero de vidas del usuario.
     * */    public int getNumVidas() {
        return numVidas;//Cierre del metodo.
    }

    public void setNumVidas(int numVidas) {
        this.numVidas = numVidas;//Cierre del metodo.
    }//Cierre del metodo.

    /**
     * Método que permite obtener el numero de ataques de pirañas..
     * */
    public int getAtaquesDePiranhas() {
        return ataquesDePiranhas;
    }//Cierre del metodo.

    /**
     * Método que permite obtener el nickname del usuario.
     * @param  ataquesDePiranhas El metodo ataqueDePiranhas es utilizado para contar
     * si ya han atacadas las piarañas ncearas para que se uba nizacion ();
     * */
    public void setAtaquesDePiranhas(int ataquesDePiranhas) {
        this.ataquesDePiranhas = ataquesDePiranhas;
    }//Cierre del metodo.

    @Override
    public boolean equals(Object o) {
        Jugador j = (Jugador) o;
        return (this.nickname.equals(j.nickname) && this.puntos == j.puntos);
    }//Cierre del metodo.
    
    
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
    }//Cierre del metodo.
    
    /**
     * Método estático que permite cargar jugadores segun sus pntoso.
     * */
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
    }//Cierre del metodo.
    
    
    
}//Cierre de la clase.
