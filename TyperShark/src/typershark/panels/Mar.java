/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.panels;


import java.util.LinkedList;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.DataFormat.URL;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import typershark.animals.AnimalMarino;
import typershark.animals.Tiburon;
import typershark.constantes.ConstantesPuntos;
import typershark.people.Buceador;
import typershark.people.Jugador;

/**
 *
 * @author Jose Masson
 */
public class Mar {
    private BorderPane root;
    private Button previo;
    private Label puntos;
    private Buceador buceador;
    
    private AnimalMarino animal1;    //codigo  nuevo
    private AnimalMarino animal2;
    private AnimalMarino animal3;
    
    private Jugador jugador;
    
    private LinkedList<AnimalMarino> animales;
    
    private StackPane panelAn;      //codigo  nuevo
    
    private LinkedList<Thread> hilos;
    
    
    public Mar() {
        this.root = new BorderPane();
        this.root.getStylesheets().add("styles/styles.css");
        this.root.getStyleClass().add("mar");
        this.jugador = new Jugador("Nombre Pruebita");
        this.puntos = new Label(Integer.toString(jugador.getPuntos()));
        

        
        buceador = new Buceador(new Image("images/components/buceador2.gif"), 40, 50);
        this.root.setTop(puntos);
        
        
        Image im = new Image("images/components/tiburoncin2.png");
        
        
        animal1 = new Tiburon(im, "holi", 200);//codigo  nuevo
        animal2 = new Tiburon (im, "chaoo", 300);
        animal3 = new Tiburon (im, "pythonlover", 400);
        this.animales = new LinkedList<>();
        root.addEventHandler(KeyEvent.KEY_PRESSED, new KeyHandler(root) );
        this.setupAnimals();
        /**
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
        
        **/
        
        
        
    }

    public BorderPane getRoot() {
        return root;
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }
    
    private class KeyHandler implements EventHandler<KeyEvent> {
        Node root;
        int count = 0;
        int countPoder = 0;
        int indexActual = -1;
        AnimalMarino elegido = null;
        boolean marcado = false;
        
        public KeyHandler(Node root) {
            this.root = root;
        }
        @Override
        public void handle(KeyEvent event) {
            
            if (event.getCode() == KeyCode.ENTER && jugador.getPuntos() >= ConstantesPuntos.PUNTOS_PODER && !KeyHandler.this.marcado) {
                System.out.println(Mar.this.jugador.getPuntos());
                Mar.this.finalizarPrograma();
                //Mar.this.desaparecerTiburones();
                Mar.this.removerAnimales();
                Mar.this.jugador.setPuntos(Mar.this.jugador.getPuntos() - ConstantesPuntos.PUNTOS_PODER);
                Mar.this.puntos.setText(Integer.toString(Mar.this.jugador.getPuntos()));
                Mar.this.animales.clear();
                Mar.this.setupAnimals();
            } else {
                //int indexActual = -1;
                
                if(!marcado){
                    for(int i = 0 ; i < animales.size() && !marcado; i++){
                        if (animales.get(i).isAlive()) {
                            if(Character.toString(animales.get(i).getPalabra().getText().charAt(0)).equals(event.getText())){
                                indexActual = i;
                                elegido = animales.get(indexActual);
                                marcado = true;
                            }
                        }

                    }
                }

                if(marcado /* && elegido.getRoot().getLayoutX() > 0*/){
                    //Mar.this.reproducirSonido("Correcto");
                    //System.out.println(event.getText());
                    //animales.get(indexActual).getRoot().requestFocus();
                    //count = 0;
                    ObservableList<Node> lista = elegido.getFlow().getChildren();
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
                            elegido.aumentarVelocidad(55);
                        }
                    } if (count == lista.size()) {
                        elegido.aumentarPuntos(jugador);
                        Mar.this.puntos.setText(Integer.toString(jugador.getPuntos()));
                        elegido.setAlive(false);
                        count = 0;
                        //elegido.getRoot().setVisible(false);
                        Mar.this.root.getChildren().remove(elegido.getRoot());
                        if (!animales.isEmpty())
                            animales.remove(elegido);
                        
                        indexActual = -1;
                        marcado = false;
                        if(animales.isEmpty()){
                            Mar.this.setupAnimals();
                        }
                        
                        
                        
                    }



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
    
    private void reproducirSonido(String tipo) {
        
        
        switch (tipo) {
            case "Correcto":
            AudioClip audio = new AudioClip(getClass().getResource("../../../src/keyboard_key.mp3").toString());
                System.out.println(audio.getSource());
                audio.play();
                break;
        }
    }
    
    public void finalizarPrograma() {
        this.animales.stream().forEach((a) -> {
            a.setAlive(false);
        });
    }
    
    /**
    public void desaparecerTiburones() {
        for (int i = 0; i < this.animales.size(); i++) {
            this.animales.get(i).getRoot().setVisible(false);
        }
    } **/
    
    public void removerAnimales() {
        for (AnimalMarino p: animales) {
            this.root.getChildren().remove(p.getRoot());
        }
    }
    
    public void setupAnimals() {
        
         animales.add( new Tiburon(new Image("images/components/tiburoncin2.png"), "holi", 200));
        animales.add(new Tiburon (new Image("images/components/tiburoncin2.png"), "nuevo", 300));
        animales.add( new Tiburon (new Image("images/components/tiburoncin2.png"), "pythonlover", 400));
        
        this.root.getChildren().addAll(animales.get(0).getRoot(), animales.get(1).getRoot(), animales.get(2).getRoot());//codigo  nuevo
        
        buceador.getImagenView().setLayoutX(40);
        buceador.getImagenView().setLayoutY(100);
        
        
        
        animales.get(0).getRoot().setLayoutX(700);//codigo  nuevo
        animales.get(0).getRoot().setLayoutY(50);//codigo  nuevo
        animales.get(1).getRoot().setLayoutX(700);
        animales.get(1).getRoot().setLayoutY(200);
        animales.get(2).getRoot().setLayoutX(700);
        animales.get(2).getRoot().setLayoutY(350);
        
        Thread t = new Thread(animales.get(0));
        t.start();
        Thread t2 = new Thread(animales.get(1));
        t2.start();
        Thread t3 = new Thread(animales.get(2));
        t3.start();
        
        
        
        
    }
    
    
    
}
