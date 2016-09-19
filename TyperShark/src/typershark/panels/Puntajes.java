/*
* @(#)Puntajes.java 4.0 24/8/2016
*
* Copyright (c) 2016 Galo Castillo, Jose Luis Masson & Danilo Torres.
* Escuela Superior Politécnica del Litoral. Guayaquil, Ecuador.
* Todos los Derechos Reservados.
*
*/
package typershark.panels;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import typershark.people.Jugador;

/**
 * Esta clase define objetos de tipo Puntajes.
 * @author: Galo Castillo, Jose Luis Masson, Danilo Torres
 * @version: 4.0 24/8/2016
 */
public class Puntajes {
    /** Raiz principal del Panel*/  
    private BorderPane root;
    
    /** Lista utilizada para observar los puntajes en la tabla*/  
    private ObservableList<Jugador> puntajes;
    
    /** Tabla que contiene los puntajes*/  
    private TableView<Jugador> tabla;
    
    /**
     * Constructor para el objeto de tipo Puntajes.
     * Crea un nuevo panel de Puntajes con una tabla en la cual se pueden observar
     * de forma ordenada el hisorial de puntajes.
     */
    public Puntajes() {
        this.root = new BorderPane();
        this.root.getStylesheets().add("styles/styles.css");
        this.root.getStyleClass().add("instrucciones");
        this.setupTitlePane();
        this.setupPuntajes();
        this.setupTable();
        
    }//Cierre del metodo.

    /**
     * Método que permite obtener la raiz principal de Puntajes.
     * @return Retorna una nueva raiz.
     * */
    public BorderPane getRoot() {
        return root;
    }//Cierre del metodo.

    /**
     * Método que permite cambiar la raiz principal de Puntajes.
     * @param root El parametro root es la nueva raiz a utilizar.
     * */
    public void setRoot(BorderPane root) {
        this.root = root;
    }//Cierre del metodo.
    
    /**
     * Método que permite cargar de manera ordenada los puntajes.
     * */
    private void setupPuntajes() {
        this.puntajes = this.cargarPuntajesJugadores();
        this.puntajes.sort(Jugador::compareTo);
    }//Cierre del metodo.
    
    /**
     * Método que modifica y edita la tabla.
     * */
    private void setupTable() {
        TextInputDialog dialog = new TextInputDialog();
                    
        dialog.setTitle("Mejores Puntajes");
        dialog.setHeaderText("Nivel");
        dialog.setContentText("Ingresa el nivel que deseas ver:");

        Optional<String> result = dialog.showAndWait();

        while ((result.isPresent() && result.get().trim().isEmpty()) || Integer.parseInt(result.get()) < 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ingreso no válido");
            alert.setHeaderText("Nivel no válido");
            alert.setContentText("Ingrese un nivel mayor a 0");
            alert.showAndWait();
            result = dialog.showAndWait();
        }
        
        ObservableList<Jugador> l = FXCollections.observableArrayList();

        if (result.isPresent()){
            for (Jugador j : this.puntajes) {
                if (j.getNivelMax() == Integer.parseInt(result.get())) {
                    l.add(j);
                }
            }
        }        
        TableColumn<Jugador, String> nameColumn = new TableColumn<>("Jugador");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        nameColumn.setMinWidth(300);
        nameColumn.setResizable(false);
        TableColumn<Jugador, Integer> pointsColumn = new TableColumn<>("Puntos");
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("puntos"));
        //pointsColumn.setStyle("-fx-alignment: CENTER-RIGHT");
        pointsColumn.setMinWidth(300);
        pointsColumn.setResizable(false);
        
        Pane pane = new Pane();
        
        this.tabla = new TableView<>();
        this.tabla.setItems(l);
        this.tabla.setLayoutX(100);
        this.tabla.getColumns().addAll(nameColumn, pointsColumn);
        pane.getChildren().add(this.tabla);
        
        this.root.setCenter(pane);
        this.tabla.getStyleClass().add("table-view");
        
    }//Cierre del metodo.
    
    /**
     * Método que edita el titulo del panel de Puntajes.
     * */
    private void setupTitlePane() {        
        VBox title;
        title = new VBox();
        title.setAlignment(Pos.CENTER);
        Label titlePane;
        titlePane = new Label("Puntajes Máximos");
        titlePane.getStyleClass().add("labelPuntajesMaximos");
        DropShadow ds;
        ds = this.setupShadow();
        titlePane.setEffect(ds);
        titlePane.setTextAlignment(TextAlignment.CENTER);
        title.getChildren().add(titlePane);
        this.root.setTop(title);
    }//Cierre del metodo.
    
    /**
     * Método que permite modficar el estilo del panel.
     * @return Retorna un DropShadow
     * */
    private DropShadow setupShadow() {
        DropShadow ds = new DropShadow();
        ds.setOffsetY(5.0f);
        ds.setOffsetX(-5.0f);
        ds.setColor(Color.color(0,0,0));
        return ds;
    }//Cierre del metodo.
    
    /**
     * Método que carga los puntajes de los jugadores.
     * @return La lista visible y modificada.
     * */
    private ObservableList<Jugador> cargarPuntajesJugadores() {
        ObservableList<Jugador> puntos = FXCollections.observableArrayList();
        File archivo = new File("src/puntajes/puntajes.txt");
        if (archivo.isFile()) {
            try {
                Scanner sc = new Scanner(archivo);
                sc.useDelimiter("\n");

                while(sc.hasNext()) {
                    String linea = sc.nextLine();
                    String[] campos = linea.split("\\|");
                    puntos.add(new Jugador(campos[0], Integer.parseInt(campos[1]), Integer.parseInt(campos[2])));
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Archivo no encontrado.");
            }
        }
        return puntos;
    }//Cierre del metodo.
    
}//Cierre de clase
