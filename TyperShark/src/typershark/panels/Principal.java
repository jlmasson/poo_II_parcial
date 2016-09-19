/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.panels;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import typershark.handlers.ClickHandler;
import typershark.handlers.ClickHandlerMar;
import typershark.people.Jugador;

/**
 *
 * @author Jose Masson
 */
public class Principal {
    private Button jugar;
    private Button cargar;
    private Button instrucciones;
    private Button puntajes;
    private Button acercaDe;
    private Button salir;
    private Label title;
    private VBox root;
    private ArrayList<String> palabrasJuego;
    private HashMap<String, HashMap<String, Integer>> partidas;
    
    public Principal(Stage principal) {
        Principal.playSound("game_menu.mp3", true);
        partidas = cargarPartidas();
        this.root = new VBox();
        this.root.setAlignment(Pos.CENTER);
        DropShadow ds = this.setupShadow();
        this.setupTitleGame(ds);
        this.root.getStylesheets().add("styles/styles.css");
        this.root.getStyleClass().add("root");
        this.setupButtons(principal, ds);
    }
    
    public VBox getRoot() {
        return root;
    }

    public void setRoot(VBox root) {
        this.root = root;
    }
    
    private void setupTitleGame(DropShadow dropShadow) {
        this.title = new Label();
        this.title.setText("TyperShark II");
        this.title.getStyleClass().add("labelPrincipal");
        this.title.setEffect(dropShadow);
        this.root.getChildren().add(title);
    }
    
    private void setupButtons(Stage principal, DropShadow dropShadow) {
        this.jugar = new Button("Comenzar Partida");
        this.cargar = new Button("Cargar Partida");
        this.instrucciones = new Button("Leer instrucciones");
        this.puntajes = new Button("Mostrar mejores puntajes");
        this.acercaDe = new Button("Acerca de");
        this.salir = new Button("Salir");
        
        this.jugar.getStyleClass().add("boton");
        this.cargar.getStyleClass().add("boton");
        this.instrucciones.getStyleClass().add("boton");
        this.puntajes.getStyleClass().add("boton");
        this.acercaDe.getStyleClass().add("boton");
        this.salir.getStyleClass().add("boton");
        
        this.jugar.setEffect(dropShadow);
        this.cargar.setEffect(dropShadow);
        this.instrucciones.setEffect(dropShadow);
        this.puntajes.setEffect(dropShadow);
        this.acercaDe.setEffect(dropShadow);
        this.salir.setEffect(dropShadow);
        
        this.jugar.setOnMouseClicked(new ClickMenu(principal, 1));
        this.cargar.setOnMouseClicked(new ClickMenu(principal, 5));
        this.instrucciones.setOnMouseClicked(new ClickMenu(principal, 2));
        this.puntajes.setOnMouseClicked(new ClickMenu(principal, 3));
        this.acercaDe.setOnMouseClicked(new ClickMenu(principal, 4));
        this.salir.setOnMouseClicked(new Exit());
        
        this.root.getChildren().addAll(jugar, cargar, instrucciones, puntajes, acercaDe, salir);
    }
    
    private DropShadow setupShadow() {
        DropShadow ds = new DropShadow();
        ds.setOffsetY(5.0f);
        ds.setOffsetX(-5.0f);
        ds.setColor(Color.color(0,0,0));
        return ds;
    }
    
    private class ClickMenu implements EventHandler<MouseEvent> {

        private Stage stage;
        private int numOpcion;

        public ClickMenu(Stage primary, int numOpcion) {
            this.stage = primary;
            this.numOpcion = numOpcion;
        }

        @Override
        public void handle(MouseEvent event) {
            Principal.playSound("button_sound.mp3", false);
            switch (this.numOpcion) {
                case 1: 
                    TextInputDialog dialog = new TextInputDialog();
                    
                    dialog.setTitle("Bienvenido a TyperShark 2.0");
                    dialog.setHeaderText("Nickname");
                    dialog.setContentText("Ingresa tu nickname:");

                    Optional<String> result = dialog.showAndWait();
                    
                    while (result.isPresent() && result.get().trim().isEmpty()) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Ingreso no válido");
                        alert.setHeaderText("Nickname no válido");
                        alert.setContentText("No se permiten nicknames vacíos");
                        alert.showAndWait();
                        result = dialog.showAndWait();
                    }
                    
                    if (result.isPresent()){
                        Jugador jugador;
                        jugador = new Jugador(result.get());
                        Stage game;
                        game = new Stage();
                        Mar mar = new Mar(jugador,partidas);
                        mar.getRoot().setBottom(Principal.this.setupBotonPrevio(this.stage, game, mar));
                        Scene gameScene = new Scene(mar.getRoot(), 800, 600);
                        game.initStyle(StageStyle.UNDECORATED);
                        game.setScene(gameScene);
                        game.setOnCloseRequest((WindowEvent t) -> {
                            Platform.exit();
                            System.exit(0);
                        });
                        game.show();
                        this.stage.close();
                    }

                    
                    
                    break;
                
                case 2:
                    Stage instructions;
                    instructions = new Stage();
                    Instrucciones ins = new Instrucciones();
                    ins.getRoot().setBottom(Principal.this.setupBotonPrevio(this.stage, instructions));
                    Label l = new Label("El buceador acumula puntos a medida que desciende y por cada animal marino que desaparece.\n" +
                    "Inicialmente dispone de 3 vidas, pero puede ganar una vida extra cada vez que llegue al fondo\n" +
                    "del mar. Se puede acumular mas puntaje tipeando la mayor cantidad de palabras posible. Con\n" +
                    "suficiente puntaje acumulado, no solo gana una vida, también puede utilizar 400 PUNTOS\n" +
                    "para eliminar a todos los animales marinos en la cercanía, presionando unicamente la tecla\n" +
                    "ENTER.\n" +
                    "El juego termina cuando el buceador pierde sus tres vidas. A medida que acumula puntaje el jugador\n" +
                    "va avanzando a un siguiente nivel, en el cual los animales marinos aumentan su rapidez."); 
                    l.setFont(new Font("Papyrus", 18));
                    l.setTextFill(Color.WHITE);
                    ins.getRoot().setCenter(l);
                    Scene scen1 = new Scene(ins.getRoot(), 800, 600);
                    instructions.setScene(scen1);
                    instructions.show();
                    this.stage.close();                    
                    break;
                
                case 3:
                    Stage scores = new Stage();
                    Puntajes punt = new Puntajes();
                    punt.getRoot().setBottom(Principal.this.setupBotonPrevio(this.stage, scores));
                    Scene scoreScene = new Scene(punt.getRoot(), 800, 600);
                    scores.setScene(scoreScene);
                    Principal.this.setStageStyles(scores, "Puntajes Máximos");
                    scores.show();
                    this.stage.close();
                    break;
                    
                case 4:
                    Stage about = new Stage();
                    AcercaDe acerca = new AcercaDe();
                    acerca.getRoot().setBottom(Principal.this.setupBotonPrevio(this.stage, about));
                    Scene aboutScene = new Scene(acerca.getRoot(), 800, 650);
                    about.setScene(aboutScene);
                    Principal.this.setStageStyles(about, "Acerca de TyperShark II");
                    about.show();
                    this.stage.close();
                    break;
                case 5:
                    TextInputDialog car = new TextInputDialog();
                    
                    car.setTitle("Bienvenido a TyperShark 2.0");
                    car.setHeaderText("Nickname");
                    car.setContentText("Ingrese el nickname con el que está guardada la partida:");

                    Optional<String> nombre = car.showAndWait();
                    
                    while (nombre.isPresent() && nombre.get().trim().isEmpty()) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Ingreso no válido");
                        alert.setHeaderText("Nickname no válido");
                        alert.setContentText("No se permiten nicknames vacíos");
                        alert.showAndWait();
                        nombre = car.showAndWait();
                    }
                    
         
                    
                    if (nombre.isPresent()){
                        if (partidas.containsKey(nombre.get().trim())) {
                            int vidas = partidas.get(nombre.get().trim()).get("numVidas");
                            int puntos = partidas.get(nombre.get().trim()).get("puntos");
                            int nivel = partidas.get(nombre.get().trim()).get("numNivel");
                        }
                        else {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Ingreso no válido");
                            alert.setHeaderText("Nickname no válido");
                            alert.setContentText("El nickname no esta guardado o "
                                                + "datos de guardado corruptos");
                            alert.showAndWait();
                            break;
                        }
                        Jugador jugador;
                        jugador = new Jugador(nombre.get().trim());
                        Stage game;
                        game = new Stage();
                        Mar mar = new Mar(jugador, partidas);
                        mar.cargarPartida();
                        
                        mar.getRoot().setBottom(Principal.this.setupBotonPrevio(this.stage, game, mar));
                        Scene gameScene = new Scene(mar.getRoot(), 800, 600);
                        game.initStyle(StageStyle.UNDECORATED);
                        game.setScene(gameScene);
                        game.setOnCloseRequest((WindowEvent t) -> {
                            Platform.exit();
                            System.exit(0);
                        });
                        game.show();
                        this.stage.close();
                    }
            }

        }
    }
    
    private class Exit implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            try {
                Principal.playSound("button_sound.mp3", false);
                Thread.sleep(50);
                System.exit(0);
            } catch (InterruptedException ex) {
            }
        }
        
    }
    
    public static ArrayList<String> cargarPalabras() throws FileNotFoundException {
        ArrayList<String> palabras = new ArrayList<>();
        File archivo = new File("src/words/words.txt");
        try (Scanner sc = new Scanner(archivo)) {
            sc.useDelimiter("\n");
            while(sc.hasNext()) {
                String linea = sc.nextLine();
                linea = linea.trim();
                palabras.add(linea);
            }
        }
        return palabras;
    }
    
    private void setStageStyles(Stage primaryStage, String title) {
        primaryStage.setResizable(false);
        Image applicationIcon = new Image(getClass().getClassLoader().getResource("images/title/icono.png").toExternalForm());
        primaryStage.getIcons().add(applicationIcon);
        primaryStage.setTitle(title);
            
    }
    
    public static void playSound(String archivo, boolean indefinido) {
        File file = new File("src/sounds/" + archivo);
        MediaPlayer clip = new MediaPlayer(new Media(file.toURI().toString()));
        if (indefinido) {
            clip.setCycleCount(MediaPlayer.INDEFINITE);
        }
        clip.play();
    }
    
    private Button setupBotonPrevio(Stage principal, Stage opcionElegir) {
        Button previo;
        previo = new Button("< Volver al Menú Principal");
        previo.getStyleClass().add("botonRegresar");
        previo.setOnMouseClicked(new ClickHandler(principal, opcionElegir));
        DropShadow ds = Principal.this.setupShadow();
        previo.setEffect(ds);
        return previo;
    }
    
    private Button setupBotonPrevio(Stage principal, Stage opcionElegir, Mar mar) {
        Button previo;
        previo = new Button("< Volver al Menú Principal");
        previo.getStyleClass().add("botonRegresar");
        previo.setOnMouseClicked(new ClickHandlerMar(principal, opcionElegir, mar));
        DropShadow ds = Principal.this.setupShadow();
        previo.setEffect(ds);
        return previo;
    }
    /**
    private LinkedList<String> cargarArchivo(String jugador) {
        File archivo = new File("src/puntajes/guardado.txt");
        LinkedList<String> l = new LinkedList<>();
        if (archivo.isFile()) {
            try {
                Scanner sc = new Scanner(archivo);
                sc.useDelimiter("\n");

                while(sc.hasNext()) {
                    String linea = sc.nextLine();
                    String[] campos = linea.split(",");
                    if (campos[0].equals(jugador)) {
                        for (String i : campos)
                            l.add(i);
                        break;
                    }
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Archivo no encontrado.");
            }
        }  
        return l;
    }
    * **/
    
    /**
     * Método que lee el archivo guardado.txt.
     * @return partidas HashMap con los datos del jugador.
     */
    public static HashMap<String, HashMap<String, Integer>> cargarPartidas(){
        HashMap<String, HashMap<String, Integer>> partidas = new HashMap<>();
        File archivo = new File("src/puntajes/guardado.txt");
        
        if (archivo.isFile()) {
            try {
                Scanner sc = new Scanner(archivo);
                sc.useDelimiter("\n");

                while(sc.hasNext()) {
                    String linea = sc.nextLine();
                    String[] campos = linea.split("\\|");
                    String nickname = campos[0];
                    partidas.put(nickname, new HashMap<>());
                    
                    partidas.get(nickname).put("numVidas", Integer.parseInt(campos[1]));
                    partidas.get(nickname).put("puntos", Integer.parseInt(campos[2]));
                    partidas.get(nickname).put("numNivel", Integer.parseInt(campos[3]));
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Archivo no encontrado.");
            }
        }
        return partidas;
    }//Cierre del metodo
}//Cierre de la clase
