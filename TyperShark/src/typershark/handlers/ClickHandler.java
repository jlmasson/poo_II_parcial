/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package typershark.handlers;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import typershark.panels.Principal;

/**
 *
 * @author Jose Masson
 */
public class ClickHandler implements EventHandler<MouseEvent>{
    private Stage principal;
    private Stage actual;
    
    public ClickHandler(Stage principal, Stage actual) {
        this.principal = principal;
        this.actual = actual;
    }
    
    @Override
    public void handle(MouseEvent event) {
        Principal.playSound("button_sound.mp3", false);
        this.actual.hide();
        this.principal.show();
    }
    
}
