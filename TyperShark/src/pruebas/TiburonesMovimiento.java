/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import typershark.animals.AnimalMarino;
import typershark.animals.Tiburon;

/**
 *
 * @author Jose Masson
 */
public class TiburonesMovimiento extends Application{
    @Override
    public void start(Stage primaryStage) {
        MarOrganizer marOrg = new MarOrganizer();
        Scene scene;
        scene = new Scene(marOrg.getRoot(), 200, 200);
        primaryStage.setTitle("TyperShark!");

        AnimalMarino tiburon = new Tiburon(new Image("Tiburon-negro-400x240.png"), "palabra");

        //Button btn = new Button();
        
        /**
        btn.setText("Say 'Hello World'");
        btn.setOnAction((ActionEvent event) -> {
            System.out.println("Hello World!");
        });**/

        StackPane root = new StackPane();
        root.getChildren().add(tiburon.getRoot());

        scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
