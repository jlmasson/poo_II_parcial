/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import typershark.panels.Principal;


/**
 *
 * @author Galo Castillo, Danilo Torres & José Massón
 */
public class TyperShark extends Application {

    @Override
    public void start(Stage primaryStage) {
        Principal prog = new Principal(primaryStage);
        Scene scene = new Scene(prog.getRoot(), 790, 640);
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
    
}

