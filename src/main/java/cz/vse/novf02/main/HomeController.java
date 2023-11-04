package cz.vse.novf02.main;

import cz.vse.novf02.logic.Game;
import cz.vse.novf02.logic.IGame;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HomeController {
    @FXML
    private TextField vstup;
    @FXML
    private TextArea vystup;

    private IGame game = new Game();
    @FXML
    private void initialize() {
        vystup.appendText(game.returnStart()+"\n\n");
        Platform.runLater(new Runnable(){
            @Override
            public void run(){
                vstup.requestFocus();
            }
        });

    }
    @FXML
    private void odesliVstup(ActionEvent actionEvent) {
        String prikaz = vstup.getText();
        vystup.appendText("> " + prikaz+"\n");
        String vysledek = game.processCommand(prikaz);
        vystup.appendText(vysledek+"\n\n");
        vstup.clear();
    }
}
