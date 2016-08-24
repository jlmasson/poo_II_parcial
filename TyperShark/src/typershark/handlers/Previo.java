package typershark.handlers;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jose Masson
 */
public class Previo {
    private Stage anterior;
    
    public Previo(Stage anterior) {
        this.anterior = anterior;
        Stage second;
        second = new Stage();
        HBox root = new HBox();

        Button botonPrueba = new Button("Boton Previo");
        botonPrueba.setOnMouseClicked(new ClickHandler(anterior, second));

        root.getChildren().add(botonPrueba);

        Scene scene = new Scene(root, 800, 650);
        second.setScene(scene);
        second.show();
        
        
    }

    public Stage getAnterior() {
        return anterior;
    }

    public void setAnterior(Stage anterior) {
        this.anterior = anterior;
    }
    
    
}
