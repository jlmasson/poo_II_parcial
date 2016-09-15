/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
/**import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;*/
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import typershark.handlers.ClickHandler;

/**
 *
 * @author Jose Masson
 */
public class TyperShark_Anterior extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        DropShadow ds = new DropShadow();
        ds.setOffsetY(5.0f);
        ds.setOffsetX(-8.0f);
        ds.setColor(Color.color(0,0,0));
        
        
        root.getStylesheets().add("styles/styles.css");
        Button botonPrueba = new Button("Comenzar Partida");
        Button instrucciones = new Button("Leer instrucciones");
        Button puntajes = new Button("Mostrar mejores puntajes");
        Button salir = new Button("Salir");
        instrucciones.getStyleClass().add("boton");
        puntajes.getStyleClass().add("boton");
        salir.getStyleClass().add("boton");
        //Image imagen = new Image("buceador.gif");
        //ImageView im = new ImageView(imagen);
        //im.setLayoutX(100);
        //im.setLayoutY(200);
        
        /**Image imagenFondo = new Image("pruebita.gif");
        ImageView im2 = new ImageView(imagenFondo);
        im2.setLayoutX(0);+
        im2.setLayoutY(0);
        im2.toFront();**/
        Label principal = new Label();
        principal.setText("TyperShark");
        principal.getStyleClass().add("labelPrincipal");
        principal.setEffect(ds);
        
        /**
        Label texto = new Label();
        texto.setText("Prueba");
        
        texto.setOnMousePressed(new ClickHandler2(primaryStage));
        * **/
        root.setStyle("-fx-background-image: url(images/background/principal.jpg)");
        root.setAlignment(Pos.CENTER);
        botonPrueba.getStyleClass().add("boton");
        botonPrueba.setOnAction(new ClickHandler1(primaryStage, 2));
        botonPrueba.setEffect(ds);
        instrucciones.setEffect(ds);
        puntajes.setEffect(ds);
        salir.setEffect(ds);
        salir.setOnAction(new Exit());
        /**BackgroundImage myBI= new BackgroundImage(new Image("pruebita.gif",800,650,false,true),
        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
          BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBI));
        * ***/
        root.getChildren().addAll(/*im,*/ principal, /*texto,*/ botonPrueba, instrucciones, puntajes, salir);

        Scene scene = new Scene(root, 790, 640);
        primaryStage.setResizable(false);
        Image applicationIcon = new Image(TyperShark_Anterior.class.getClassLoader().getResource("images/title/icono.png").toExternalForm());
        primaryStage.getIcons().add(applicationIcon);
        primaryStage.setTitle("TypeShark 2.0");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class ClickHandler1 implements EventHandler<ActionEvent> {

        Stage stage;
        int numOpcion;

        public ClickHandler1(Stage primary, int numOpcion) {
            stage = primary;
            this.numOpcion = numOpcion;
        }

        @Override
        public void handle(ActionEvent event) {
            if (this.numOpcion == 2) {
                Stage second;
                second = new Stage();
                HBox root = new HBox();

                Button botonPrueba = new Button("Boton Previo");
                botonPrueba.setOnAction(new ClickHandler(stage, second));
                //botonPrueba.setOnAction(new ClickHandler());

                root.getChildren().add(botonPrueba);

                Scene scene = new Scene(root, 800, 600);
                second.setScene(scene);
                second.show();
            }
            stage.close();

        }
    }
    
    private class Exit implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.exit(0);
        }
        
    }
    
    private class ClickHandler2 implements EventHandler<MouseEvent> {
        private final Stage stage;
        
        public ClickHandler2(Stage primary) {
            this.stage = primary;
        }

        @Override
        public void handle(MouseEvent event) {
            Stage second;
            second = new Stage();
            HBox root = new HBox();

            Button botonPrueba = new Button("Boton Previo");
            botonPrueba.setOnAction(new ClickHandler(stage, second));
            //botonPrueba.setOnAction(new ClickHandler());

            root.getChildren().add(botonPrueba);

            Scene scene = new Scene(root, 800, 600);
            second.setScene(scene);
            second.show();
            stage.close();
        }
    /**
    private class ClickHandler2 implements EventHandler<MouseEvent> {

        Stage stage;

        public ClickHandler2(Stage primary) {
            stage = primary;
        }
        
        /**
        @Override
        public void handle(ActionEvent event) {
            Stage second;
            second = new Stage();
            HBox root = new HBox();

            Button botonPrueba = new Button("Boton Previo");
            botonPrueba.setOnAction(new ClickHandler(stage, second));
            //botonPrueba.setOnAction(new ClickHandler());

            root.getChildren().add(botonPrueba);

            Scene scene = new Scene(root, 800, 600);
            second.setScene(scene);
            second.show();
            stage.close();

        }**/
    }
}

