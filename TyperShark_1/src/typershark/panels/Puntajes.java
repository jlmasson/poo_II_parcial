/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.panels;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import typershark.people.Jugador;

/**
 *
 * @author Jose Masson
 */
public class Puntajes {
    private BorderPane root;
    private Button previo;
    private ObservableList<Jugador> puntajes;
    private TableView<Jugador> tabla;
    
    public Puntajes() {
        this.root = new BorderPane();
        this.root.getStylesheets().add("styles/styles.css");
        this.root.getStyleClass().add("instrucciones");
        this.setupPuntajes();
        this.setupTable();
        
    }

    public BorderPane getRoot() {
        return root;
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }
    
    private void setupPuntajes() {
        this.puntajes = this.cargarPuntajesJugadores();
        this.puntajes.sort(Jugador::compareTo);
    }
    
    private void setupTable() {
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
        //this.tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //this.tabla.setColumnResizePolicy(false);
        this.tabla.setItems(this.puntajes);
        this.tabla.setLayoutX(100);
        this.tabla.getColumns().addAll(nameColumn, pointsColumn);
        pane.getChildren().add(this.tabla);
        
        this.root.setCenter(pane);
        this.tabla.getStyleClass().add("table-view");
        /**
        Pane pane = new Pane();
        pane.getChildren().add(this.tabla);
        this.root.setCenter(pane);*/
    }
    
    private ObservableList<Jugador> cargarPuntajesJugadores() {
        ObservableList<Jugador> puntajes = FXCollections.observableArrayList();
        File archivo = new File("src/puntajes/puntajes.txt");
        if (archivo.isFile()) {
            try {
                Scanner sc = new Scanner(archivo);
                sc.useDelimiter("\n");

                while(sc.hasNext()) {
                    String linea = sc.nextLine();
                    String[] campos = linea.split("\\|");
                    puntajes.add(new Jugador(campos[0], Integer.parseInt(campos[1])));
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Archivo no encontrado.");
            }
        }
        return puntajes;
    }
}
