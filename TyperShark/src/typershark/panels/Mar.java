/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.panels;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import typershark.animals.AnimalMarino;
import typershark.animals.Piranha;
import typershark.animals.Tiburon;
import typershark.animals.TiburonNegro;
import typershark.constantes.Constantes;
import typershark.constantes.ConstantesDesplazamientos;
import typershark.constantes.ConstantesPuntos;
import typershark.people.Buceador;
import typershark.people.Jugador;

/**
 *
 * @author Jose Masson
 */
public class Mar{
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
    
    public Mar(Jugador jugador) {

        palabrasJuego = new ArrayList<>();
   
        try{
            palabrasJuego = Principal.cargarPalabras();
        }catch(FileNotFoundException ex){}

        this.iniciarMar(jugador);
        
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
        Timeline timeline;
        
        public KeyHandler(Node root) {
            this.root = root;
        }
        @Override
        public void handle(KeyEvent event) {
            if (Mar.this.jugador.getNumVidas() > 0) {
                if (event.getCode() == KeyCode.ENTER && jugador.getPuntos() >= ConstantesPuntos.PUNTOS_PODER && !KeyHandler.this.marcado) {
                    countEnter++;
                    Mar.this.playSound("power.mp3");
                    System.out.println(Mar.this.jugador.getPuntos());
                    Mar.this.finalizarPrograma();
                    Mar.this.removerAnimales();
                    Mar.this.jugador.setPuntos(Mar.this.jugador.getPuntos() - ConstantesPuntos.PUNTOS_PODER);
                    Mar.this.puntos.setText(Integer.toString(Mar.this.jugador.getPuntos()));
                    Mar.this.animales.clear();
                    Mar.this.setupAnimals();
                } else {
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
                        ObservableList<Node> lista = elegido.getFlow().getChildren();
                        if (count < lista.size()) {
                            Mar.this.playSound("correct.mp3");
                            Text c = (Text) lista.get(count);
                            if (event.getText().equals(c.getText())) {
                                c.setFill(Color.WHITE);
                                lista.set(count, c);
                                count++;

                            }
                            else {
                                Mar.this.playSound("wrong.mp3");
                                elegido.aumentarVelocidad(ConstantesDesplazamientos.AUMENTO_VELOCIDAD);
                            }
                        } if (count == lista.size()) {
                            
                            Integer puntosAnteriores = Mar.this.jugador.getPuntos();
                            elegido.aumentarPuntos(jugador);
                            Integer puntosActuales = Mar.this.jugador.getPuntos();
                            Integer diferencia = puntosActuales - puntosAnteriores;
                            Mar.this.jugador.setPuntos(Mar.this.jugador.getPuntos() - diferencia);

                            timeline = new Timeline();
                            timeline.setCycleCount(Timeline.INDEFINITE);
                            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.010),
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
                                Mar.this.playSound("boom.mp3");
                                File file = new File("src/images/components/explosion.gif");
                                Image image = new Image(file.toURI().toString());
                                elegido.getImagen().setImage(image);
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException ex) {
                                   
                                }
                                elegido.setAlive(false);
                                
                                Mar.this.matarAnimal(elegido);
                                
                                /**
                                if (!animales.isEmpty()) {
                                    //Mar.this.root.getChildren().remove(elegido.getRoot());
                                    animales.remove(elegido);
                                }**/
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
    
    private void playSound(String archivo) {
        File file = new File("src/sounds/" + archivo);
        AudioClip clip = new AudioClip(file.toURI().toString());
        
        clip.play();
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
                    animal = new Tiburon(this, palabrasJuego);
                    animal.setLocation(posXInicial, posYInicial);
                    this.animales.add(animal);
                    this.root.getChildren().add(animal.getRoot());
                    new Thread(animal).start();
                    break;
                case 2:
                    animal = new TiburonNegro(this, palabrasJuego);
                    animal.setLocation(posXInicial, posYInicial);
                    this.root.getChildren().add(animal.getRoot());
                    new Thread(animal).start();
                    this.animales.add(animal);
                    break;
                case 3:
                    animal = new Piranha(this, palabrasJuego);
                    animal.setLocation(posXInicial, posYInicial);
                    this.animales.add(animal);
                    this.root.getChildren().add(animal.getRoot());
                    new Thread(animal).start();
                    break;
                
            }
            
            posYInicial += 150;
            
        }
    }
    
    private void iniciarMar(Jugador jugador){
        this.root = new BorderPane();
        this.jugador = jugador;
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
                
        this.imagenPuntos.setLayoutX(-90);
        this.imagenPuntos.setLayoutY(10);
                        
        this.puntos.setLayoutX(-20);
        this.puntos.setLayoutY(50); 
        
        this.nivel.setLayoutX(375);
        this.nivel.setLayoutY(30);
        

        
        //Buceador
        this.buceador = new Buceador(this);
        //this.buceador = new Buceador();
        this.root.getChildren().add(this.buceador.getImagenBuceador());
        buceador.getImagenBuceador().setLayoutX(0);
        buceador.getImagenBuceador().setLayoutY(Constantes.POS_Y_INICIAL_BUCEADOR);
        

        
        //Panel superior
        root.addEventHandler(KeyEvent.KEY_PRESSED, new KeyHandler(root) );
        this.setupAnimals();
        
        
        Thread tBuceador = new Thread(this.buceador);
        tBuceador.start();
        
    }
    
    public void setGameOver() {
        this.eliminarAnimales();
        this.root.getChildren().remove(this.buceador.getImagenBuceador());
        Text gameOver = new Text("GAME OVER");
        Text jugTexto = new Text(this.jugador.getNickname());
        Text puntTexto = new Text("Has conseguido " + Integer.toString(this.jugador.getPuntos()) + " puntos, en " + Integer.toString(this.numNivel) + " nivel(es).");

        gameOver.setTextAlignment(TextAlignment.CENTER);
        jugTexto.setTextAlignment(TextAlignment.CENTER);
        puntTexto.setTextAlignment(TextAlignment.CENTER);
        
        gameOver.setFill(Color.RED);
        jugTexto.setFill(Color.YELLOW);
        puntTexto.setFill(Color.WHITE);
        
        gameOver.getStyleClass().add("gameOver");
        jugTexto.getStyleClass().add("nickname");
        puntTexto.getStyleClass().add("puntosObt");

        VBox panelNickname = new VBox();
        panelNickname.getChildren().addAll(gameOver, jugTexto, puntTexto);
        panelNickname.setAlignment(Pos.CENTER);
        
        this.root.setCenter(panelNickname);
        
        LinkedList<Jugador> jugadores = Jugador.cargarJugadorXPuntos();
        jugadores.add(this.jugador);
        jugadores.sort(Jugador::compareTo);
        if (jugadores.getFirst().equals(this.jugador)) {
            Text maxPuntuacion = new Text("Felicidades!!" + "\nHAS CONSEGUIDO" + "\nUN NUEVO RÉCORD");
            maxPuntuacion.setFill(Color.RED);
            maxPuntuacion.setTextAlignment(TextAlignment.CENTER);
            maxPuntuacion.getStyleClass().add("gameOver");
            panelNickname.getChildren().addAll(maxPuntuacion);
        }
        this.escribirArchivo(jugadores);
        
    }
    
    public void matarAnimal(AnimalMarino animal) {
        this.root.getChildren().remove(animal.getRoot());
        if (!this.animales.isEmpty())
            this.animales.remove(animal);
    }
    
    public void setSiguienteNivel(){
        
        this.jugador.setNumVidas(this.jugador.getNumVidas() + 1);
        this.numVidas.setText(Integer.toString(this.jugador.getNumVidas()));
        this.numNivel += 1;
        this.nivel.setText(Integer.toString(this.numNivel));
        buceador.getImagenBuceador().setLayoutY(Constantes.POS_Y_INICIAL_BUCEADOR);
        this.eliminarAnimales();
        this.setupAnimals();
        
    }
    
    public void eliminarAnimales(){
        animales.stream().map((animal) -> {
            animal.setAlive(false);
            return animal;
        }).forEach((animal) -> {
            this.root.getChildren().remove(animal.getRoot());
        });
        this.animales.clear();
    }
    
    public int getNumNivel(){
        return this.numNivel;
    }
    
    private void escribirArchivo(LinkedList<Jugador> jugadores) {
        FileWriter writer;
        try {
            File file = new File("src/puntajes/puntajes.txt");
            if (file.isFile()) {
                file.delete();
            }   
            writer = new FileWriter(file);
            for (Jugador j: jugadores) {
                writer.write(j.getNickname()+"|"+ Integer.toString(j.getPuntos())+ "\n");
                writer.flush();
            }     
            writer.close();
        } catch (IOException ex) {
            
        } 
    }
    
}
