/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.animals;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Danilo Torres
 */
public abstract class AnimalMarino implements Runnable {

    private StackPane pane;
    private ImageView imagen;
    private Text palabra;
    private long velocidad;
    private TextFlow text;
    private Text palabraTipeada;
    private boolean vivo;

    public AnimalMarino(Image imagen, String palabra, long velocidad) {

        Pane p = new Pane();
        this.imagen = new ImageView(imagen);
        this.palabra = new Text(palabra);
        this.palabra.setFont(new Font(200));
        this.palabra = new Text(palabra);
        this.palabra.setFill(Color.WHITE);
        this.palabra.setFont(new Font(30));

        vivo = true;

        text = new TextFlow();
        for (Character c : palabra.toLowerCase().toCharArray()) {
            Text e = new Text(c.toString());
            e.setFont(new Font(30));
            e.setFill(Color.BLUE);
            text.getChildren().add(e);

        }

        this.velocidad = velocidad;

        this.pane = new StackPane();
        p.getChildren().addAll(this.text);
        this.palabra.setLayoutX(20);
        pane.getChildren().addAll(this.imagen, p);
    }

    public StackPane getRoot() {
        return this.pane;
    }

    public void run() {
        try {
            while (vivo) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        pane.setLayoutX(pane.getLayoutX() - 10);
                    }
                });
                Thread.sleep(velocidad);
            }
        } catch (InterruptedException ex) {
        }
    }

    public ImageView getImagen() {//codigo  nuevo
        return this.imagen;//codigo  nuevo
    }

    public Text getPalabra() {
        return this.palabra;
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

}
