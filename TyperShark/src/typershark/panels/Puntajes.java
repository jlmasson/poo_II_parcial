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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import typershark.people.Jugador;

/**
 *
 * @author Jose Masson
 */
public class Puntajes {
    private BorderPane root;
    private ObservableList<Jugador> puntajes;
    private TableView<Jugador> tabla;
    
    public Puntajes() {
        this.root = new BorderPane();
        this.root.getStylesheets().add("styles/styles.css");
        this.root.getStyleClass().add("instrucciones");
        this.setupTitlePane();
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
        this.tabla.setItems(this.puntajes);
        this.tabla.setLayoutX(100);
        this.tabla.getColumns().addAll(nameColumn, pointsColumn);
        pane.getChildren().add(this.tabla);
        
        this.root.setCenter(pane);
        this.tabla.getStyleClass().add("table-view");
    }
    
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
    }
    
    private DropShadow setupShadow() {
        DropShadow ds = new DropShadow();
        ds.setOffsetY(5.0f);
        ds.setOffsetX(-5.0f);
        ds.setColor(Color.color(0,0,0));
        return ds;
    }
    
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
                    puntos.add(new Jugador(campos[0], Integer.parseInt(campos[1])));
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Archivo no encontrado.");
            }
        }
        return puntos;
    }
    
}
