
import java.util.LinkedList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hp
 */
public class MarOrganizer {
    private Buceador buceador;
    private ImageView imagenVidas;
    private Label vidas;
    private ImageView imagenPuntos;
    private Label puntos;
    private Label nivel;
    private LinkedList<AnimalMarino> animalesMarinos;
    
   
    private Pane root;
    
    public MarOrganizer(){
        this.root = new Pane();
        Button b = new Button("HOLIWIS");
        Label l = new Label("YAAA");
        root.getChildren().addAll(b, l);
        l.setLayoutX(100);
        l.setLayoutY(100);
        b.setLayoutX(25);
        b.setLayoutY(25);

        BackgroundImage bg = new BackgroundImage(new Image("imagenes/FondoDeMar.gif", 800, 650, false, true), 
                                                 BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                                                    BackgroundSize.DEFAULT);
        this.root.setBackground(new Background(bg));

    }

    
    public void colocarImagenFondo(){

    }
    
    public Pane getRoot(){
        return this.root;
    } 
    
    
    
}
