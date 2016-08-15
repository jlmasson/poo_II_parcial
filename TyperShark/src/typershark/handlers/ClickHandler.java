package typershark.handlers;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
public class ClickHandler implements EventHandler<ActionEvent>{
    private Stage principal;
    private Stage actual;
    
    public ClickHandler(Stage principal, Stage actual) {
        this.principal = principal;
        this.actual = actual;
    }
    
    @Override
    public void handle(ActionEvent event) {
        actual.close();
        principal.show();
    }
    
}