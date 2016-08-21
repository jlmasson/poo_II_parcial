/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;

import typershark.panels.*;
import java.util.LinkedList;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import typershark.animals.AnimalMarino;
import typershark.animals.Tiburon;
import typershark.people.Buceador;

/**
 *
 * @author Jose Masson
 */
public class Mar_RespaldoPepe {
    private BorderPane root;
    private Button previo;
    
    private Buceador buceador;
    
    private AnimalMarino animal1;    //codigo  nuevo
    private AnimalMarino animal2;
    private AnimalMarino animal3;
    
    
    private LinkedList<AnimalMarino> animales;
    
    private StackPane panelAn;      //codigo  nuevo
    
    private LinkedList<Thread> hilos;
    
    
    public Mar_RespaldoPepe(Stage previo) {
        this.root = new BorderPane();
        this.root.getStylesheets().add("styles/styles.css");
        this.root.getStyleClass().add("mar");
        

        
        buceador = new Buceador(new Image("images/components/buceador2.gif"), 40, 50);
        
        
        
        Image im = new Image("images/components/tiburoncin2.png");
        
        
        animal1 = new Tiburon(im, "holi", 200);//codigo  nuevo
        animal2 = new Tiburon (im, "chaoo", 300);
        animal3 = new Tiburon (im, "pythonlover", 400);
        
        animales = new LinkedList<>();
        animales.add(animal1);
        animales.add(animal2);
        animales.add(animal3);
        
        
        panelAn = animal1.getRoot();
        this.root.getChildren().addAll(panelAn, animal2.getRoot(), animal3.getRoot(), buceador.getImagenView());//codigo  nuevo
        
        buceador.getImagenView().setLayoutX(40);
        buceador.getImagenView().setLayoutY(100);
        root.addEventHandler(KeyEvent.KEY_PRESSED, new KeyHandler(root) );
        
        
        panelAn.setLayoutX(700);//codigo  nuevo
        panelAn.setLayoutY(50);//codigo  nuevo
        animal2.getRoot().setLayoutX(700);
        animal2.getRoot().setLayoutY(200);
        animal3.getRoot().setLayoutX(700);
        animal3.getRoot().setLayoutY(350);
        
        
        hilos = new LinkedList<>();
        
        
        Thread t = new Thread(animal1);
        t.start();
        Thread t2 = new Thread(animal2);
        t2.start();
        Thread t3 = new Thread(animal3);
        t3.start();
        
        
        hilos.add(t);
        hilos.add(t2);
        hilos.add(t3);
        
        
        
        
        
    }

    public BorderPane getRoot() {
        return root;
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }
    
    private class KeyHandler implements EventHandler<KeyEvent> {
        Node root;
        int count=0;
        int indexActual = -1;
        
        public KeyHandler(Node root) {
            this.root = root;
        }
        @Override
        public void handle(KeyEvent event) {
            
            //int indexActual = -1;
            boolean marcado = false;
            if(indexActual == -1){
                for(int i = 0 ; i < animales.size(); i++){
                    if (animales.get(i).isAlive()) {
                    if(animales.get(i).getPalabra().getText().charAt(0) == event.getText().charAt(0) ){
                        indexActual = i;
                        break;
                    }}

                }
        }
                
            if(indexActual > -1){
                //System.out.println(event.getText());
                //animales.get(indexActual).getRoot().requestFocus();
                //count = 0;
                ObservableList<Node> lista = animales.get(indexActual).getFlow().getChildren();
                if (count < lista.size()) {
                    Text c = (Text) lista.get(count);
                    if (event.getText().equals(c.getText())) {
                        //Text c = (Text) lista.get(count);
                        //c.getT
                        c.setFill(Color.WHITE);
                        lista.set(count, c);
                        System.out.println(count);
                        count++;
                        
                    }
                    else {
                        animales.get(indexActual).aumentarVelocidad(55);
                    }
                } if (count == lista.size()) {
                    animales.get(indexActual).setAlive(false);
                    count = 0;
                    animales.get(indexActual).getRoot().setVisible(false);
                    indexActual = -1;
                }
                
          
                
            }
                


            //String tecla = event.getText();
          
            /**
            String tecla = event.getText();
            Label buscado
            prueba.requestFocus();
            prueba.setText(prueba.getText() + tecla);
            root.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
    focusState(newValue);
        });
        
        **/
        }
        
    }
    
    public void finalizarPrograma() {
        this.animales.stream().forEach((a) -> {
            a.setAlive(false);
        });
    }
    
    
    
    
}
