/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.panels;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import typershark.animals.AnimalMarino;
import typershark.animals.Piranha;
import typershark.animals.Tiburon;
import typershark.animals.TiburonNegro;
import typershark.constantes.Constantes;
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
    private Text puntos;
    private ImageView imagenPuntos;
    private Text numVidas;
    private ImageView imagenVida;
    private Text nivel;
    private int numNivel;
    
    private Buceador buceador;
    
    
    private AnimalMarino animal1;   
    private AnimalMarino animal2;
    private AnimalMarino animal3;
    
    private ArrayList<String> palabrasJuego;
    
    private Jugador jugador;
    
    private LinkedList<AnimalMarino> animales;
    
    
    private LinkedList<Thread> hilos;
    
    
    private Integer countDownPoder = ConstantesPuntos.PUNTOS_PODER;
    
    public Mar() {

        palabrasJuego = new ArrayList<>();
        palabrasJuego.add("Hola");
        palabrasJuego.add("Chao");
   
        try{
            palabrasJuego = Principal.cargarPalabras();
        }catch(FileNotFoundException ex){}
        
        this.iniciarMar();

        
        

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

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public LinkedList<AnimalMarino> getAnimales() {
        return animales;
    }

    public void setAnimales(LinkedList<AnimalMarino> animales) {
        this.animales = animales;
    }

    public Text getNumVidas() {
        return numVidas;
    }

    public void setNumVidas(Text numVidas) {
        this.numVidas = numVidas;
    }
    
    private class KeyHandler implements EventHandler<KeyEvent> {
        Node root;
        int countEnter = 0;
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
            if (Mar.this.jugador.getNumVidas() > 0) {
                if (event.getCode() == KeyCode.ENTER && jugador.getPuntos() >= ConstantesPuntos.PUNTOS_PODER && !KeyHandler.this.marcado) {
                    countEnter++;
                    Principal.playSound("power.mp3", false);
                    System.out.println(Mar.this.jugador.getPuntos());
                    Mar.this.finalizarPrograma();
                    Integer puntosDisminuir = ConstantesPuntos.PUNTOS_PODER;
                    //Integer timeSeconds = ConstantesPuntos.PUNTOS_PODER;
                    //Mar.this.desaparecerTiburones();
                    Mar.this.removerAnimales();
                        Timeline timeline = new Timeline();
                        timeline.setCycleCount(Timeline.INDEFINITE);
                        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.001),
                                  new PuntosEvent(timeline, puntosDisminuir, -1)));
                        timeline.playFromStart();
                        //Mar.this.jugador.setPuntos(Mar.this.jugador.getPuntos() - ConstantesPuntos.PUNTOS_PODER);
                        //Mar.this.puntos.setText(Integer.toString(Mar.this.jugador.getPuntos()));
                        Mar.this.animales.clear();
                        Mar.this.setupAnimals();
                } else {
                    //int indexActual = -1;

                    if(!marcado){
                        for(int i = 0 ; i < animales.size() && !marcado; i++){
                            if (animales.get(i).isAlive()) {
                                if(Character.toString(animales.get(i).getpPalabraEnPantalla().getText().charAt(0)).equals(event.getText())){
                                    indexActual = i;
                                    elegido = animales.get(indexActual);
                                    marcado = true;
                                }
                            }

                        }
                    }

                    if(marcado && elegido.isAlive() && elegido.getRoot().getLayoutX() >= 0 /* && elegido.getRoot().getLayoutX() > 0*/){
                        //Mar.this.reproducirSonido("Correcto");
                        ObservableList<Node> lista = elegido.getFlow().getChildren();
                        if (count < lista.size()) {
                            Principal.playSound("correct.mp3", false);
                            Text c = (Text) lista.get(count);
                            if (event.getText().equals(c.getText())) {
                                c.setFill(Color.WHITE);
                                lista.set(count, c);
                                count++;

                            }
                            else {
                                Principal.playSound("wrong.mp3", false);
                                elegido.aumentarVelocidad(55);
                            }
                        } if (count == lista.size()) {
                            
                            Integer puntosAnteriores = Mar.this.jugador.getPuntos();
                            elegido.aumentarPuntos(jugador);
                            Integer puntosActuales = Mar.this.jugador.getPuntos();
                            Integer diferencia = puntosActuales - puntosAnteriores;
                            Mar.this.jugador.setPuntos(Mar.this.jugador.getPuntos() - diferencia);

                            Timeline timeline = new Timeline();
                            timeline.setCycleCount(Timeline.INDEFINITE);
                            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.001),
                                      new AumentarPuntosEvent(timeline, diferencia)));
                            timeline.playFromStart();

                            Mar.this.puntos.setText(Integer.toString(jugador.getPuntos()));
                            count = 0;
                            //elegido.getRoot().setVisible(false);
                            if(elegido instanceof TiburonNegro &&
                                    ((TiburonNegro)elegido).getPalabras().size() > 1){

                                    elegido.getRoot().getChildren().remove(elegido.getPanelMedio());
                                    ((TiburonNegro)elegido).getPalabras().remove(0);
                                    String siguientePalabra = ((TiburonNegro)elegido).getPalabras().get(0);
                                    ((TiburonNegro)elegido).setTextoEnPantalla(siguientePalabra);

                            }
                            else{
                                Principal.playSound("boom.mp3", false);
                                elegido.getImagen().setImage(new Image("images/components/explosion.gif"));
                                elegido.setAlive(false);
                                
                                Mar.this.matarAnimal(elegido);
                                
                                if (!animales.isEmpty()) {
                                    //Mar.this.root.getChildren().remove(elegido.getRoot());
                                    animales.remove(elegido);
                                }
                            }
                            indexActual = -1;
                            marcado = false;
                            if(animales.isEmpty()){
                                Mar.this.setupAnimals();
                            }
                        }
                    }
                    if (marcado && !elegido.isAlive()) {
                        count = 0;
                        marcado = false;
                    }
                    /**
                    else if (marcado && !elegido.isAlive()) {
                        elegido.quitarVidas(Mar.this.jugador);
                        Mar.this.numVidas.setText(Integer.toString(Mar.this.jugador.getNumVidas()));
                        Mar.this.root.getChildren().remove(elegido.getRoot());
                            if (!animales.isEmpty())
                                animales.remove(elegido);
                            marcado = false;
                            if (animales.isEmpty()) {
                                Mar.this.setupAnimals();
                            }
                    }**/
                }
            }
            else {
                Mar.this.setGameOver();
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
    
    private class PuntosEvent implements EventHandler {
        private final Timeline timeline;
        private Integer puntos;
        private final Integer multiplicador;
        private final int auxiliarJugador;
        private final int auxiliar;
        
        public PuntosEvent(Timeline timeline, Integer puntos, Integer multiplicador) {
            this.timeline = timeline;
            this.puntos = puntos;
            this.multiplicador = multiplicador;
            this.auxiliarJugador = Mar.this.jugador.getPuntos();
            this.auxiliar = puntos;
        }

        @Override
        public void handle(Event event) {
            this.puntos--;
            Mar.this.jugador.setPuntos(Mar.this.jugador.getPuntos() + (multiplicador)*1);
            Mar.this.puntos.setText(Integer.toString(Mar.this.jugador.getPuntos()));
            if (this.puntos == 0 && Mar.this.jugador.getPuntos() == this.auxiliarJugador + (multiplicador*this.auxiliar)) {
                this.timeline.stop();
            }
        }
        
    }
    
    
    private class AumentarPuntosEvent implements EventHandler {

            private final Timeline timeline;
            private Integer puntos;

            public AumentarPuntosEvent(Timeline timeline, Integer puntos) {
                this.timeline = timeline;
                this.puntos = puntos;
            }

            @Override
            public void handle(Event event) {
                this.puntos--;
                Mar.this.jugador.setPuntos(Mar.this.jugador.getPuntos() + 1);
                Mar.this.puntos.setText(Integer.toString(Mar.this.jugador.getPuntos()));
                if (this.puntos <= 0) {
                    timeline.stop();
                }
            }
    }
    
    private class DisminuirPuntosEvent implements EventHandler {

            private final Timeline timeline;

            public DisminuirPuntosEvent(Timeline timeline) {
                this.timeline = timeline;
            }

            @Override
            public void handle(Event event) {
                Mar.this.countDownPoder--;
                Mar.this.jugador.setPuntos(Mar.this.jugador.getPuntos() - 1);
                // update timerLabel
                Mar.this.puntos.setText(
                        Integer.toString(Mar.this.jugador.getPuntos()));
                if (Mar.this.countDownPoder <= 0) {
                    timeline.stop();
                    Mar.this.countDownPoder = ConstantesPuntos.PUNTOS_PODER;
                }
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
        animales.stream().forEach((p) -> {
            this.root.getChildren().remove(p.getRoot());
        });
    }
    
    public void setupAnimals() {
        this.animales = new LinkedList<>();
        this.hilos = new LinkedList<>();
        int posXInicial = 600;
        int posYInicial = 150;
        Random r = new Random();
        
        
        for(int i = 0; i < 3; i++){
            int aleatorio = r.nextInt(3) + 1;
            AnimalMarino animal;
            switch(aleatorio){
                case 1:
                    animal = new Tiburon(this, palabrasJuego, 200);
                    animal.setLocation(posXInicial, posYInicial);
                    this.animales.add(animal);
                    this.root.getChildren().add(animal.getRoot());
                    new Thread(animal).start();
                    break;
                case 2:
                    animal = new TiburonNegro(this, palabrasJuego, 200);
                    animal.setLocation(posXInicial, posYInicial);
                    this.root.getChildren().add(animal.getRoot());
                    new Thread(animal).start();
                    this.animales.add(animal);
                    break;
                case 3:
                    animal = new Piranha(this, palabrasJuego, 120);
                    animal.setLocation(posXInicial, posYInicial);
                    this.animales.add(animal);
                    this.root.getChildren().add(animal.getRoot());
                    new Thread(animal).start();
                    break;
                
            }
            
            /**
            this.root.getChildren().add(this.animales.get(i).getRoot());
            
            this.animales.get(i).getRoot().setLayoutX(posXInicial);
            this.animales.get(i).getRoot().setLayoutY(posYInicial);**/
            posYInicial += 150;
            
            //this.hilos.add(new Thread(this.animales.get(i)));
            
        }
        /**
        this.hilos.stream().forEach((t) -> {
            t.start();
        }); /**
        animales.get(0).getRoot().setLayoutX(700);//codigo  nuevo
        animales.get(0).getRoot().setLayoutY(150);//codigo  nuevo
        animales.get(1).getRoot().setLayoutX(700);
        animales.get(1).getRoot().setLayoutY(350);
        animales.get(2).getRoot().setLayoutX(700);
        animales.get(2).getRoot().setLayoutY(500);
        Thread t = new Thread(animales.get(0));
        t.start();
        Thread t2 = new Thread(animales.get(1));
        t2.start();
        Thread t3 = new Thread(animales.get(2));
        t3.start();**/
        
        /**
        
        animales.get(0).getRoot().setLayoutX(700);//codigo  nuevo
        animales.get(0).getRoot().setLayoutY(150);//codigo  nuevo
        animales.get(1).getRoot().setLayoutX(700);
        animales.get(1).getRoot().setLayoutY(350);
        animales.get(2).getRoot().setLayoutX(700);
        animales.get(2).getRoot().setLayoutY(500);
        
        Thread t = new Thread(animales.get(0));
        t.start();
        Thread t2 = new Thread(animales.get(1));
        t2.start();
        Thread t3 = new Thread(animales.get(2));
        t3.start();**/
    }
    
    private void iniciarMar(){
        this.root = new BorderPane();
        this.jugador = new Jugador("Nombre Pruebita");
        this.root.getStylesheets().add("styles/styles.css");
        this.root.getStyleClass().add("mar");
        
        this.numNivel = 1;
        this.puntos = new Text(Integer.toString(jugador.getPuntos()));
        this.puntos.setFill(Color.WHITE);
        this.puntos.getStyleClass().add("jugadorDatos");
        this.imagenVida = new ImageView(new Image("images/components/corazonVidas.png"));
        this.imagenPuntos = new ImageView(new Image("images/components/tesoro.png"));
        this.numVidas = new Text(Integer.toString(this.jugador.getNumVidas()));
        this.numVidas.setFill(Color.WHITE);
        this.numVidas.getStyleClass().add("jugadorDatos");
        this.nivel = new Text("Nivel  " + this.numNivel);
        this.nivel.setFont(new Font("Times New Roman", 20));
        
        
        HBox panelSuperior = new HBox();
        Pane panelDer = new Pane();
        Pane panelIzq = new Pane();
        Pane panelCentral = new Pane();
        panelIzq.getChildren().addAll(this.imagenVida, this.numVidas, this.nivel);
        panelDer.getChildren().addAll(this.imagenPuntos, this.puntos);
        
        
        
        panelSuperior.getChildren().addAll(panelIzq, panelCentral, panelDer);
        this.root.setTop(panelSuperior);
        
        panelSuperior.setAlignment(Pos.BOTTOM_CENTER);
        panelSuperior.setSpacing(200);
        
        
        //this.root.getChildren().addAll(this.imagenVida, this.numVidas, this.imagenPuntos, this.puntos);
        //this.root.getChildren().add(panelSuperior);
        this.imagenVida.setLayoutX(10);
        this.imagenVida.setLayoutY(5);
        
        this.numVidas.setLayoutX(80);
        this.numVidas.setLayoutY(45);
                
        this.imagenPuntos.setLayoutX(-75);
        this.imagenPuntos.setLayoutY(10);
                        
        this.puntos.setLayoutX(0);
        this.puntos.setLayoutY(50); 
        
        this.nivel.setLayoutX(375);
        this.nivel.setLayoutY(30);
        

        
        //Buceador
        this.buceador = new Buceador();
        this.root.getChildren().add(this.buceador.getImagenBuceador());
        buceador.getImagenBuceador().setLayoutX(0);
        buceador.getImagenBuceador().setLayoutY(Constantes.POS_Y_INICIAL_BUCEADOR);
        

        
        //Panel superior
        root.addEventHandler(KeyEvent.KEY_PRESSED, new KeyHandler(root) );
        this.setupAnimals();
        
        
        
        
    }
    
    public void setGameOver() {
        this.root = new BorderPane();
        this.root.addEventFilter(KeyEvent.KEY_PRESSED, new KeyHandler(this.root));
        
    }
    
    public void matarAnimal(AnimalMarino animal) {
        this.root.getChildren().remove(animal.getRoot());
        this.animales.remove(animal);
    }
    
    public void setSiguienteNivel(){
        this.numNivel += 1;
        buceador.getImagenBuceador().setLayoutY(Constantes.POS_Y_INICIAL_BUCEADOR);
    }
}
