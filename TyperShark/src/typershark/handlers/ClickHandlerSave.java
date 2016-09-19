/*
* @(#)ClickHandlerSave.java 4.0 19/9/2016
*
* Copyright (c) 2016 Galo Castillo & Danilo Torres.
* Escuela Superior Politécnica del Litoral. Guayaquil, Ecuador.
* Todos los Derechos Reservados.
*
*/

package typershark.handlers;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import typershark.panels.Mar;
import typershark.panels.Principal;
import typershark.people.Jugador;

/**
 *
 * @author Galo Castillo, Danilo Torres
 */
public class ClickHandlerSave implements EventHandler<MouseEvent>{
    private Jugador jugador;
    private Mar mar;
    private HashMap<String, HashMap<String, Integer>> partidas;
    
    /**
     * Constructor para el objeto de tipo ClickHandler.
     * Maneja la funcionalidad del botón que guarda partidas.
     * @param mar El parámetro mar corresponde al panel organizador del mar. 
     * @param jugador El parametro jugador corresponde al manejador de
     * la información del jugador.
     * @param partidas es la coleccion que almacena las partidas almacenadas.
     */
    public ClickHandlerSave(Jugador jugador, Mar mar, HashMap<String, HashMap<String, Integer>> partidas) {
        this.jugador = jugador;
        this.mar = mar;
        this.partidas = partidas;
        
    }//Cierre del constructor
    
    
    @Override
    public void handle(MouseEvent event) {
        Principal.playSound("button_sound.mp3", false);
        if(!partidas.containsKey(this.jugador.getNickname())){
            partidas.put(this.jugador.getNickname(), new HashMap<>());
            partidas.get(this.jugador.getNickname()).put("numVidas", this.jugador.getNumVidas());
            partidas.get(this.jugador.getNickname()).put("puntos", this.jugador.getPuntos());
            partidas.get(this.jugador.getNickname()).put("numNivel", this.mar.getNumNivel());
        }
        
        else{
            partidas.get(this.jugador.getNickname()).put("numVidas", this.jugador.getNumVidas());
            partidas.get(this.jugador.getNickname()).put("puntos", this.jugador.getPuntos());
            partidas.get(this.jugador.getNickname()).put("numNivel", this.mar.getNumNivel());
        }
        
        FileWriter writer;
        try {
            File file = new File("src/puntajes/guardado.txt");
            if (file.isFile()) {
                file.delete();
            }   
            writer = new FileWriter(file);
            for (String player: this.partidas.keySet()) {
                
                writer.write(player + "|" + this.partidas.get(player).get("numVidas") + "|" +
                        this.partidas.get(player).get("puntos") + "|" +
                        this.partidas.get(player).get("numNivel") + "\n");
                
                //writer.write(j.getNickname()+"|"+ Integer.toString(j.getPuntos())+ "\n");
                writer.flush();
            }     
            writer.close();
        } catch (IOException ex) {            
        }        
    }//Cierre del metodo        
}//Cierre de la clase
