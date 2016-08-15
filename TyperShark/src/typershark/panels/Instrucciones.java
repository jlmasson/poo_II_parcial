/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typershark.panels;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Jose Masson
 */
public class Instrucciones {
    private BorderPane root;
    private Button previo;
    
    public Instrucciones() {
        this.root = new BorderPane();
        this.root.getStylesheets().add("styles/styles.css");
        this.root.getStyleClass().add("instrucciones");
        
    }

    public BorderPane getRoot() {
        return root;
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }
    
}
