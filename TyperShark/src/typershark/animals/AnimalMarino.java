/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.animals;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import typershark.panels.Mar;
import typershark.people.Jugador;

/**
 *
 * @author Danilo Torres
 */
public abstract class AnimalMarino implements Runnable {

    private StackPane pane;
    private Pane panelMedio;
    private ImageView imagen;
    private Text palabraEnPantalla;
    private long velocidad;
    private TextFlow text;
    private boolean vivo;
    private Mar mar;
    private boolean marcado;

    public AnimalMarino(Mar mar, String ruta, ArrayList<String> palabrasJuego, long velocidad) {

        this.mar = mar;

        this.imagen = new ImageView(new Image(ruta));
        vivo = true;

        this.velocidad = velocidad;

        this.pane = new StackPane();
        this.marcado = false;

        pane.getChildren().add(this.imagen);
    }

    public StackPane getRoot() {
        return this.pane;
    }

    @Override
    public void run() {
        try {
            while (vivo && pane.getLayoutX() > 0) {
                //synchronized(this);
                synchronized(this) {
                    Platform.runLater(() -> {
                        pane.setLayoutX(pane.getLayoutX() - (10 + 3*mar.getNumNivel()));
                    });
                Thread.sleep(this.velocidad);
                } 
            }
            /**
            if (!vivo && pane.getLayoutX() > 0) {
                Platform.runLater(() -> {
                    this.setImagen(new Image("images/components/explosion.gif"));

                });
            }**/
            if (pane.getLayoutX() <= 0 && vivo) {
                synchronized(this) {
                this.setAlive(false);
                this.quitarVidas(mar.getJugador());
                Platform.runLater(() -> {
                    AnimalMarino.this.mar.matarAnimal(AnimalMarino.this);
                    if (AnimalMarino.this.mar.getAnimales().isEmpty()) {
                        AnimalMarino.this.mar.setupAnimals();
                    }
                    //AnimalMarino.this.mar.getNumVidas().setText(Integer.toString(AnimalMarino.this.mar.getJugador().getNumVidas()));
                });
                Platform.runLater(() -> {
                    AnimalMarino.this.mar.getNumVidas().setText(Integer.toString(AnimalMarino.this.mar.getJugador().getNumVidas()));

                });
                }
            }
            
        } catch (InterruptedException ex) {
        }
    }

    public ImageView getImagen() {//codigo  nuevo
        return this.imagen;//codigo  nuevo
    }
    
    public void setImagen(Image imagen) {
        this.imagen = new ImageView(imagen);
    }

    public Text getpPalabraEnPantalla() {
        return this.palabraEnPantalla;
    }

    public TextFlow getFlow() {
        return this.text;
    }

    public boolean isAlive() {
        return vivo;
    }

    public void setAlive(boolean b) {
        this.vivo = b;
    }

    public void aumentarVelocidad(int v) {
        if (this.velocidad - v > 0) {
            this.velocidad = this.velocidad - v;
        }
    }
    
    public void setTextoEnPantalla(String palabra){
        this.palabraEnPantalla = new Text(palabra);
        this.palabraEnPantalla.setFont(new Font(200));
        this.palabraEnPantalla = new Text(palabra);
        this.palabraEnPantalla.setFill(Color.WHITE);
        this.palabraEnPantalla.setFont(new Font(30));
        text = new TextFlow();
        for (Character c : palabra.toLowerCase().toCharArray()) {
            Text e = new Text(c.toString());
            e.setFont(new Font(30));
            e.setFill(Color.BLUE);
            text.getChildren().add(e);
        }
        panelMedio = new Pane();
        panelMedio.getChildren().add(this.text);
        this.text.setLayoutX(90);
        pane.getChildren().add(panelMedio);
        //pane.getChildren().add(this.text);
        
        
    }
    
    public Pane getPanelMedio (){
        return this.panelMedio;
    }
    
    public void matarAnimal() {
        this.setAlive(false);
        mar.getRoot().getChildren().remove(this.pane);
        if(mar.getAnimales().contains(this)){
            mar.getAnimales().remove(this);
        }
    }
    
    public void setLocation(double x, double y) {
        this.pane.setLayoutX(x);
        this.pane.setLayoutY(y);
    }
    
    public abstract void aumentarPuntos(Jugador jugador);
    
    public abstract void quitarVidas(Jugador jugador);

}
