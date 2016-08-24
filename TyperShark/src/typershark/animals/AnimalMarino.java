/*
* @(#)Tiburon.java 4.0 28/8/2016
*
* Copyright (c) 2016 Galo Castillo, Jose Luis Masson & Danilo Torres.
* Escuela Superior Politécnica del Litoral. Guayaquil, Ecuador.
* Todos los Derechos Reservados.
*
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
 * Esta clase es abstracta para permitir el modelamiento
 * de los animales  en el juego.
 * @author: Galo Castillo, Jose Luis Masson, Danilo Torres
 * @version: 4.0 22/6/2016
 */
public abstract class AnimalMarino implements Runnable {

    
    private StackPane pane;
    
    private Pane panelMedio;{
    
    }
    
    /** Nnodo que contiene la imagen del componente */
    private ImageView imagen;
    
    /** Nodo que contiene a la palabra que se mueestra por pantalla */
    private Text palabraEnPantalla;
    
    /** Nodo que contiene a la secuencia de letras de la palabra
     * que se mueestra por pantalla*/
    private TextFlow text;
    
    
    /** Vvariable booleano que india si el animal sigue con cl cuerpo*/
    private boolean vivo;
    
    /** Oorganizdor del*/
    private Mar mar;
    
    /** Nodo que contiene a la palabra que se mueestra por pantalla*/
    private boolean marcado;

    /** Cantidad de milisegundos de espera por cada desplazamiento de la imagen
     del animal en pantalla*/
    private long velocidad;
    
    /**
     * Constructor para el objeto de tipo AnimalMarino.     
     * Genera un animal marino
     * @param mar El parámetro mar es el panel organizador sobre el cual se
     * piensa a posicionar al animal.
     * @param palabrasJuego El parámetro palabrasJuego es la lista que contiene
     * a todas las palabras que los animales serán capaces de  portar.
     * @param velocidad El parametro velocidad es inversamente proporcional a
     * la tasa de movimiento del AnimalMarino.
     * @param ruta El parametro ruta es un string conformado por la ruta utilizada
     * para consegui la imagen del AnimalMarino.
     */
    public AnimalMarino(Mar mar, String ruta, ArrayList<String> palabrasJuego, long velocidad) {

        this.mar = mar;

        this.imagen = new ImageView(new Image(ruta));
        vivo = true;

        this.velocidad = velocidad;

        this.pane = new StackPane();
        this.marcado = false;

        pane.getChildren().add(this.imagen);
    } //Cierre del constructor
    
    /**
     * Método que permite obtener el panel raiz que contiene al animal y su
     * información.
     * @return El panel raiz del AmimalMarino
     */
    public StackPane getRoot() {
        return this.pane;
    } //Cierre del metodo

    
    /**
     * Método que permite el movimiento el animal al utilizarlo con un hilo.
     */
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
    }//Cierre del metodo

    /**
     * Método que permite obtener la imagen del animal.
     * @return La imagen que es utilizada para visualizar el movimiento del
     * animal a traves del escenario.
     */
    public ImageView getImagen() {//codigo  nuevo
        return this.imagen;//codigo  nuevo
    } //Cierre del metodo.
    
    /**
     * Método que cambia la imagen uilizada para vsualizar al animal.
     * @param imagen El parámetro imagen es la imagen utilizada para instanciar 
     * el nodo de la imagen.
     */
    public void setImagen(Image imagen) {
        this.imagen = new ImageView(imagen);
    } //Cierre del metodo

    /**
     * Método que permite obtener la palabra mostrada en pantalla que posee el animal.
     * @return La imagen que es utilizada para visualizar la palabra contenida
     * por el aimal.
     */
    public Text getpPalabraEnPantalla() {
        return this.palabraEnPantalla;
    } //Cierre del método.

    /**
     * Método que permite obtener la secuencia de letras mostrada en pantalla.
     * @return El nodo que contiene a la secuencia de una palabra mostrada por
     * pantalla.
     */
    public TextFlow getFlow() {
        return this.text;
    } // Cierre del metodo.

    /**
     * Método que permite verificar si el animal esta con vida, asi como su hilo.
     * @return True si el animal esta equivocado. Caso contrario mejor.
     */
    public boolean isAlive() {
        return vivo;
    } //Cierre del método

    /**
     * Método que cambia el estado de vivo del animal.
     * @param b El parámetro True o False dependiendo del estado del animal.
     */
    public void setAlive(boolean b) {
        this.vivo = b;
    } //Cierre del metodo.

    /**
     * Método que permite incrementar la velocidad con la cual se desplaza
     * la imagen del animal junto a su palabra a lo largo del escenario del mar.
     * @param v El parametro v es la cantidad de velocidad que piensa incrementarse.
     */
    public void aumentarVelocidad(int v) {
        if (this.velocidad - v > 0) {
            this.velocidad = this.velocidad - v;
        }
    } //Cierre del metodo.
    
    /**
     * Método que permite modificar el texto que el animal muestra por panralla.
     * @param palabra El parametro palabra contiene el nuevo texto que se mostrara
     * por pantalla.
     */
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
    } //Cierre del metodo.
    
    
    public Pane getPanelMedio (){
        return this.panelMedio;
    }
    
    /**
     * Método que permite eliminar a un animal del mar, así como detener al hilo
     * encargado de desplazarlo.
     */
    public void matarAnimal() {
        this.setAlive(false);
        mar.getRoot().getChildren().remove(this.pane);
        if(mar.getAnimales().contains(this)){
            mar.getAnimales().remove(this);
        }
    } // Cierre del metodo.
    
    /**
     * Método que permite reubicar en cualquier posicion del mar al animal.
     * encargado de desplazarlo.
     * @param x El parametro x es la posicion horizontal que tomara el animal.
     * @param y El parametro y es la posicion vertial que tomara el animal.
     */
    public void setLocation(double x, double y) {
        this.pane.setLayoutX(x);
        this.pane.setLayoutY(y);
    } //Cierre del metodo.
    
    /**
     * Método que ha sido declarado abstracto para ser redefinido por las subclases.
     * Este método permite incrementarle puntos al jugador, según el animal que haya
     * sido destruido en el mar.
     */
    public abstract void aumentarPuntos(Jugador jugador);
    
    /**
     * Método que ha sido declarado abstracto para ser redefinido por las subclases.
     * Este método permite quitarle vidas al jugador, según el animal que haya
     * atacado al buceador en el mar.
     */
    public abstract void quitarVidas(Jugador jugador);

}//Cierre de clase.
