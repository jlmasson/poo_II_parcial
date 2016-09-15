package typershark.handlers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import typershark.panels.Mar;
import typershark.panels.Principal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jose Masson
 */
public class ClickHandlerMar implements EventHandler<MouseEvent>{
    private Stage principal;
    private Stage actual;
    private Mar mar;
    
    public ClickHandlerMar(Stage principal, Stage actual, Mar mar) {
        this.principal = principal;
        this.actual = actual;
        this.mar = mar;
    }
    
    @Override
    public void handle(MouseEvent event) {
        Principal.playSound("button_sound.mp3", false);
        this.actual.hide();
        this.mar.finalizarPrograma();
        this.principal.show();
    }
    
}
