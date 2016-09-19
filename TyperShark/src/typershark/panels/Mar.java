/*
* @(#)Mar.java 4.0 24/8/2016
*
* Copyright (c) 2016 Galo Castillo, Jose Luis Masson & Danilo Torres.
* Escuela Superior Politécnica del Litoral. Guayaquil, Ecuador.
* Todos los Derechos Reservados.
*
*/
package typershark.panels;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import typershark.animals.AnimalMarino;
import typershark.animals.Piranha;
import typershark.animals.Pulpo;
import typershark.animals.Tiburon;
import typershark.animals.TiburonNegro;
import typershark.constantes.Constantes;
import typershark.constantes.ConstantesDesplazamientos;
import typershark.constantes.ConstantesPuntos;
import typershark.handlers.ClickHandlerSave;
import typershark.people.Buceador;
import typershark.people.Jugador;

/**
 * Esta clase define objetos de tipo Mar.
 * @author: Galo Castillo, Jose Luis Masson, Danilo Torres
 * @version: 4.0 28/8/2016
 */
public class Mar{
    /** Raiz principal del panel*/    
    private BorderPane root;
    
    /** Boton utilizado para regresar al menu principal*/
    private Button previo;
    
    /** Nodo que muestra en pantalla los puntos ganados por el jugador*/
    private Text puntos;
    
    /** Imagen que representa los puntos*/
    private ImageView imagenPuntos;
    
    /** Nodo que muestra en pantalla el numero de vidas del jugador*/
    private Text numVidas;
    
    /** Imagen que representa la vida del jugador*/
    private ImageView imagenVida;
    
    /** Nodo que muestra el numero del nivel actual*/
    private Text nivel;
    
    /** Numero del nivel actual*/
    private int numNivel;
    
    /** Imagen que representa los niveles*/
    private ImageView imagenNivel;
    
    /** Manejador del buceador mostrado en pantalla*/
    private Buceador buceador;
    
    /** Lista de palabras utilizadas para asignarlas a los animales marinos*/
    private ArrayList<String> palabrasJuego;
    
    /** Jugador encargado de llevar el curso del juego*/
    private Jugador jugador;
    
    /** Lista de los animales mostrados en pantalla*/
    private LinkedList<AnimalMarino> animales;
    
    /** Lista de hilos que manejan los animales*/
    private LinkedList<Thread> hilos;
    
    /** Puntos necesarios para poder utilizar el poder especial*/
    private Integer countDownPoder = ConstantesPuntos.PUNTOS_PODER;
    
    /** Imagen del boton de guardado*/
    private ImageView imagenSave;
    
    /** Diccionario con los datos guardados de los jugadores*/
    private HashMap<String, HashMap<String, Integer>> partidas;
    
    
    /**
     * Constructor para el objeto de tipo Mar.
     * Crea un nuevo Mar con un jugador nuevo, nodos que representan los puntos,
     * nivel y numero de vidas del jugador. Ademas comienza con un buceador y
     * animales marinos vivos.
     * @param jugador El parámetro jugador es utilizado para manejar la informacion
     * del jugador durante la partida
     * @param partidas
     */
    public Mar(Jugador jugador, HashMap<String, HashMap<String, Integer>> partidas) {

        palabrasJuego = new ArrayList<>();
   
        try{
            palabrasJuego = Principal.cargarPalabras();
        }catch(FileNotFoundException ex){}
        
        this.partidas = partidas;

        this.iniciarMar(jugador);
        
    }

    /**
     * Método que permite obtener la raiz principal del mar.
     * @return root Pane donde está ubicado el mar
     * */
    public BorderPane getRoot() {
        return root;
    } //Cierre del metodo

    /**
     * Método que permite cambiar la raiz principal del mar.
     * @param root El parametro root es la nueva raiz a utilizar.
     * */
    public void setRoot(BorderPane root) {
        this.root = root;
    } //Cierre del metodo

    /**
     * Método que permite obtener el jugador del mar.
     * @return jugador Retorna el jugador del juego
     *   */
    public Jugador getJugador() {
        return jugador;
    } //Cierre del metodo

    /**
     * Método que permite cambiar al jugador del mar.
     * @param jugador El parámetro jugador es el nuevo jugador a utilizar */
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    } //Cierre del metodo

    /**
     * Método que permite obtener la lista de animales presentes
     * en el mar.
     * @return  animales La lista de animales en el juego
     */
    public LinkedList<AnimalMarino> getAnimales() {
        return animales;
    } //Cierre del metodo

    /**
     * Método que permite cambiar la lista de animales presentes
     * en el mar.
     * @param animales El parametro animales es la nueva lista de animales
     * que estaran presentes en el mar.
     * */
    public void setAnimales(LinkedList<AnimalMarino> animales) {
        this.animales = animales;
    } //Cierre del metodo

    /**
     * Método que permite obtener el nodo del numero de vidas del jugador.
     * @return numVidas Retorna el texto en pantalla que muestra las vidas
     */
    public Text getNumVidas() {
        return numVidas;
    } //Cierre del metodo

    /**
     * Método que permite cambiar el nodo del numero de vidas del jugador.
     * @param numVidas El parámetro numVidas es el numero de vidas
     * mostrado en pantalla
     *  */
    public void setNumVidas(Text numVidas) {
        this.numVidas = numVidas;
    } //Cierre del metodo


    
    private class KeyHandler implements EventHandler<KeyEvent> {
        /** Raiz principal del panel*/
        private Node root;
        
        /** Número de usos de poder especial*/
        private int countEnter = 0;
        
        /** Índice que permite recorrer la palabra marcada*/
        private int count = 0;
        
        /** Numero para bajar los puntos del jugador*/
        private int countPoder = 0;
        
        /** Numero que permite hacer de bandera para elegir los animales*/
        private int indexActual = -1;
        
        /** Animal elegido cuando se teclea su primera letra*/
        private AnimalMarino elegido = null;
        
        /** Marca que permite identificar cuando un animal está escogido*/
        private boolean marcado = false;
        
        /** Línea de tiempo para animación de puntajes*/
        private Timeline timeline;
        
        public KeyHandler(Node root) {
            this.root = root;
        } 
        
        /**
         * Método que permite manejar la entrada del teclado en el juego
         */
        @Override
        public void handle(KeyEvent event) {
            
            // Condición para lo cual el usuario siga tecleando
            if (Mar.this.jugador.getNumVidas() > 0) {
                if (event.getCode() == KeyCode.ENTER && jugador.getPuntos() >= ConstantesPuntos.PUNTOS_PODER && !KeyHandler.this.isMarcado()) {
                    setCountEnter(getCountEnter() + 1);
                    Mar.this.playSound("power.mp3");
                    System.out.println(Mar.this.jugador.getPuntos());
                    Mar.this.finalizarPrograma();
                    Mar.this.removerAnimales();
                    Mar.this.jugador.setPuntos(Mar.this.jugador.getPuntos() - ConstantesPuntos.PUNTOS_PODER);
                    Mar.this.puntos.setText(Integer.toString(Mar.this.jugador.getPuntos()));
                    Mar.this.animales.clear();
                    Mar.this.setupAnimals();
                } else {
                    
                    // Condición para elegir el animal a marcar
                    if(!isMarcado()){
                        for(int i = 0 ; i < animales.size() && !isMarcado(); i++){
                            if (animales.get(i).isAlive()) {
                                if(Character.toString(animales.get(i).getpPalabraEnPantalla().getText().charAt(0)).equals(event.getText())){
                                    setIndexActual(i);
                                    setElegido(animales.get(getIndexActual()));
                                    setMarcado(true);
                                }
                            }

                        }
                    }
                    
                    // Condición para matar al animal antes de que mate al buceador
                    if(isMarcado() && getElegido().isAlive() && getElegido().getRoot().getLayoutX() >= 0 /* && elegido.getRoot().getLayoutX() > 0*/){
                        ObservableList<Node> lista = getElegido().getFlow().getChildren();
                        if (getCount() < lista.size()) {
                            Mar.this.playSound("correct.mp3");
                            Text c = (Text) lista.get(getCount());
                            if (event.getText().equals(c.getText())) {
                                c.setFill(Color.WHITE);
                                lista.set(getCount(), c);
                                setCount(getCount() + 1);

                            }
                            else {
                                Mar.this.playSound("wrong.mp3");
                                getElegido().aumentarVelocidad(ConstantesDesplazamientos.AUMENTO_VELOCIDAD);
                            }
                            // Condición para matar al tiburon
                        } if (getCount() == lista.size()) {
                            
                            Integer puntosAnteriores = Mar.this.jugador.getPuntos();
                            getElegido().aumentarPuntos(jugador);
                            Integer puntosActuales = Mar.this.jugador.getPuntos();
                            Integer diferencia = puntosActuales - puntosAnteriores;
                            Mar.this.jugador.setPuntos(Mar.this.jugador.getPuntos() - diferencia);

                            setTimeline(new Timeline());
                            getTimeline().setCycleCount(Timeline.INDEFINITE);
                            getTimeline().getKeyFrames().add(new KeyFrame(Duration.seconds(0.010),
                                      new AumentarPuntosEvent(getTimeline(), diferencia)));
                            getTimeline().playFromStart();

                            Mar.this.puntos.setText(Integer.toString(jugador.getPuntos()));
                            setCount(0);
                            if(getElegido() instanceof TiburonNegro &&
                                    ((TiburonNegro)getElegido()).getPalabras().size() > 1){

                                    getElegido().getRoot().getChildren().remove(getElegido().getPanelMedio());
                                    ((TiburonNegro)getElegido()).getPalabras().remove(0);
                                    String siguientePalabra = ((TiburonNegro)getElegido()).getPalabras().get(0);
                                    ((TiburonNegro)getElegido()).setTextoEnPantalla(siguientePalabra);

                            }
                            else{
                                Mar.this.playSound("boom.mp3");
                                File file = new File("src/images/components/explosion.gif");
                                Image image = new Image(file.toURI().toString());
                                getElegido().getImagen().setImage(image);
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException ex) {
                                   
                                }
                                getElegido().setAlive(false);
                                
                                Mar.this.matarAnimal(getElegido());

                            }
                            setIndexActual(-1);
                            setMarcado(false);
                            if(animales.isEmpty()){
                                Mar.this.setupAnimals();
                            }
                        }
                    }
                    if (isMarcado() && !elegido.isAlive()) {
                        setCount(0);
                        setMarcado(false);
                    }
                }
            }
        } //Cierre del metodo

        /**
         * @return the root
         */
        public Node getRoot() {
            return root;
        } //Cierre del metodo

        /**
         * @param root the root to set
         */
        public void setRoot(Node root) {
            this.root = root;
        } //Cierre del metodo

        /**
         * @return the countEnter
         */
        public int getCountEnter() {
            return countEnter;
        } //Cierre del metodo

        /**
         * @param countEnter the countEnter to set
         */
        public void setCountEnter(int countEnter) {
            this.countEnter = countEnter;
        } //Cierre del metodo

        /**
         * @return the count
         */
        public int getCount() {
            return count;
        } //Cierre del metodo

        /**
         * @param count the count to set
         */
        public void setCount(int count) {
            this.count = count;
        } //Cierre del metodo

        /**
         * @return the countPoder
         */
        public int getCountPoder() {
            return countPoder;
        } //Cierre del metodo

        /**
         * @param countPoder the countPoder to set
         */
        public void setCountPoder(int countPoder) {
            this.countPoder = countPoder;
        } //Cierre del metodo

        /**
         * @return the indexActual
         */
        public int getIndexActual() {
            return indexActual;
        } //Cierre del metodo

        /**
         * @param indexActual the indexActual to set
         */
        public void setIndexActual(int indexActual) {
            this.indexActual = indexActual;
        } //Cierre del metodo

        /**
         * @return the elegido
         */
        public AnimalMarino getElegido() {
            return elegido;
        } //Cierre del metodo

        /**
         * @param elegido the elegido to set
         */
        public void setElegido(AnimalMarino elegido) {
            this.elegido = elegido;
        } //Cierre del metodo

        /**
         * @return the marcado
         */
        public boolean isMarcado() {
            return marcado;
        } //Cierre del metodo

        /**
         * @param marcado the marcado to set
         */
        public void setMarcado(boolean marcado) {
            this.marcado = marcado;
        } //Cierre del metodo

        /**
         * @return the timeline
         */
        public Timeline getTimeline() {
            return timeline;
        } //Cierre del metodo

        /**
         * @param timeline the timeline to set
         */
        public void setTimeline(Timeline timeline) {
            this.timeline = timeline;
        } //Cierre del metodo
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
    
    /**
     * Método que permite reproducir audio.
     * @param archivo El parametro archivo es un string con la ruta al archivo 
     * de audio que se desea reproducir.
     * */
    private void playSound(String archivo) {
        File file = new File("src/sounds/" + archivo);
        AudioClip clip = new AudioClip(file.toURI().toString());
        
        clip.play();
    } //Cierre del metodo
    
    public void finalizarPrograma() {
        this.animales.stream().forEach((a) -> {
            a.setAlive(false);
        });
    } //Cierre del metodo

    
    public void removerAnimales() {
        animales.stream().forEach((p) -> {
            this.root.getChildren().remove(p.getRoot());
        });
    } //Cierre del metodo
    
    public void setupAnimals() {
        this.animales = new LinkedList<>();
        this.hilos = new LinkedList<>();
        int posXInicial = 600;
        int posYInicial = 150;
        Random r = new Random();
        for(int i = 0; i < 3; i++){
            int aleatorio = r.nextInt(4) + 1;
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
                case 4:
                    animal = new Pulpo(this, palabrasJuego);
                    animal.setLocation(posXInicial, posYInicial);
                    this.animales.add(animal);
                    this.root.getChildren().add(animal.getRoot());
                    new Thread(animal).start();
                    break;
                
            }
            
            posYInicial += 150;
            
        }
    } //Cierre del metodo
    
    /**
     * Método que inicializa el mar (Inicia el Juego)
     */
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
        this.nivel = new Text(Integer.toString(this.numNivel));
        this.nivel.getStyleClass().add("jugadorDatos");
        this.imagenNivel = new ImageView(new Image("images/components/estrellaNivel.png"));
        StackPane pilaNivel = new StackPane();
        pilaNivel.getChildren().addAll(imagenNivel, nivel);
        
        
        HBox panelSuperior = new HBox();
        Pane panelDer = new Pane();
        Pane panelIzq = new Pane();
        Pane panelCentral = new Pane();
        panelIzq.getChildren().addAll(this.imagenVida, this.numVidas, pilaNivel);
        panelDer.getChildren().addAll(this.imagenPuntos, this.puntos);
        
        
        
        panelSuperior.getChildren().addAll(panelIzq, panelCentral, panelDer);
        this.root.setTop(panelSuperior);
        
        panelSuperior.setAlignment(Pos.BOTTOM_CENTER);
        panelSuperior.setSpacing(200);

        this.imagenVida.setLayoutX(10);
        this.imagenVida.setLayoutY(5);
        
        this.numVidas.setLayoutX(80);
        this.numVidas.setLayoutY(45);
                
        this.imagenPuntos.setLayoutX(-100);
        this.imagenPuntos.setLayoutY(10);
                        
        this.puntos.setLayoutX(-30);
        this.puntos.setLayoutY(50); 
        
        pilaNivel.setLayoutX(350);
        pilaNivel.setLayoutY(5);
        
        
        
        //MEJORAMIENTO
        this.imagenSave = new ImageView(new Image("images/components/disquete.png"));
        Pane paneInf = new Pane();
        paneInf.getChildren().add(this.imagenSave);
        this.imagenSave.setLayoutY(450);
        this.imagenSave.setLayoutX(-20);
        this.root.setRight(paneInf);
        this.imagenSave.setOnMouseClicked(new ClickHandlerSave(this.jugador, this, this.partidas));
        System.out.println(this.numNivel);
        
        
        
        
        //Buceador
        this.buceador = new Buceador(this);
        this.root.getChildren().add(this.buceador.getImagenBuceador());
        buceador.getImagenBuceador().setLayoutX(0);
        buceador.getImagenBuceador().setLayoutY(Constantes.POS_Y_INICIAL_BUCEADOR);

        //Panel superior
        root.addEventHandler(KeyEvent.KEY_PRESSED, new KeyHandler(root) );
        this.setupAnimals();

        Thread tBuceador = new Thread(this.buceador);
        tBuceador.start();
        
    } //Cierre del metodo
    
    /**
     * Método que muestra el finl del juego
     */
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
        this.jugador.setNivelMax(this.numNivel);
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
        
    } //Cierre del metodo
    
    /**
     * Método que muestra el finl del juego
     * @param animal El parámetro animal es el que se va a matar (muere hilo)
     */
    public void matarAnimal(AnimalMarino animal) {
        this.root.getChildren().remove(animal.getRoot());
        if (!this.animales.isEmpty())
            this.animales.remove(animal);
    } //Cierre del metodo
    
    /**
     * Método que cambia el nivel del Juego
     */
    public void setSiguienteNivel(){
        
        this.jugador.setNumVidas(this.jugador.getNumVidas() + 1);
        this.numVidas.setText(Integer.toString(this.jugador.getNumVidas()));
        this.numNivel += 1;
        this.nivel.setText(Integer.toString(this.numNivel));
        buceador.getImagenBuceador().setLayoutY(Constantes.POS_Y_INICIAL_BUCEADOR);
        this.eliminarAnimales();
        this.setupAnimals();
        
        
    } //Cierre del metodo
    
    /**
     * Método que elimina los animales presentes en el par
     */
    
    public void eliminarAnimales(){
        animales.stream().map((animal) -> {
            animal.setAlive(false);
            return animal;
        }).forEach((animal) -> {
            this.root.getChildren().remove(animal.getRoot());
        });
        this.animales.clear();
    } //Cierre del metodo
    
    
    /**
     * Método que obtiene el número del nivel del juego
     * @return numNivel El nivel del juego
     */
    public int getNumNivel(){
        return this.numNivel;
    } //Cierre del metodo
    
    /**
     * Método que escribe el archivo de los puntajes
     */
    private void escribirArchivo(LinkedList<Jugador> jugadores) {
        FileWriter writer;
        try {
            File file = new File("src/puntajes/puntajes.txt");
            if (file.isFile()) {
                file.delete();
            }   
            writer = new FileWriter(file);
            for (Jugador j: jugadores) {
                writer.write(j.getNickname()+"|"+ Integer.toString(j.getPuntos())+"|"+ Integer.toString(j.getNivelMax()) + "\n");
                writer.flush();
            }     
            writer.close();
        } catch (IOException ex) {
            
        } 
    } //Cierre del metodo
    
    /**
     * Método que carga una partida guardada
     * @param vidas
     * @param puntos
     * @param nivel
     */
    public void cargarPartida(int vidas, int puntos, int nivel){
        
        this.jugador.setNumVidas(vidas);
        this.numVidas.setText(Integer.toString(vidas));
        this.numNivel = nivel;
        this.nivel.setText(Integer.toString(nivel));
        this.jugador.setPuntos(puntos);
        this.puntos.setText(Integer.toString(puntos));
        buceador.getImagenBuceador().setLayoutY(Constantes.POS_Y_INICIAL_BUCEADOR);
        this.eliminarAnimales();
        
        this.setupAnimals();
        
    } //Cierre del metodo
    
    /**
     * Método que carga una partida guardada
     */
    public void cargarPartida(){
        
        this.jugador.setNumVidas(partidas.get(this.jugador.getNickname()).get("numVidas"));
        this.numVidas.setText(Integer.toString(partidas.get(this.jugador.getNickname()).get("numVidas")));
        this.numNivel = partidas.get(this.jugador.getNickname()).get("numNivel");
        this.nivel.setText(Integer.toString(this.numNivel));
        this.jugador.setPuntos(partidas.get(this.jugador.getNickname()).get("puntos"));
        this.puntos.setText(Integer.toString(partidas.get(this.jugador.getNickname()).get("puntos")));
        buceador.getImagenBuceador().setLayoutY(Constantes.POS_Y_INICIAL_BUCEADOR);
        this.eliminarAnimales();
        
        this.setupAnimals();
        
    } //Cierre del metodo
    
} //Cierre de la clase