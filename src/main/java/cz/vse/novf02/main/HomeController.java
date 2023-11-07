package cz.vse.novf02.main;

import cz.vse.novf02.logic.Game;
import cz.vse.novf02.logic.IGame;
import cz.vse.novf02.logic.Room;
import cz.vse.novf02.logic.GamePlan;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.util.Optional;

public class HomeController implements Pozorovatel{
    @FXML
    private ListView panelVychodu;
    @FXML
    private Button tlacitkoOdesli;
    @FXML
    private TextField vstup;
    @FXML
    private TextArea vystup;

    private IGame game = new Game();
    private Game hra = new Game();

    private ObservableList<Room> seznamVychodu = FXCollections.observableArrayList();
    /*@FXML
    private void initialize() {
        vystup.appendText(game.returnStart()+"\n\n");
        Platform.runLater(new Runnable(){
            @Override
            public void run(){
                vstup.requestFocus();
                panelVychodu.setItems(seznamVychodu);
                game.getGamePlan().registruj(this);
            }
        });

    }*/
    @FXML
    private void initialize() {
        vystup.appendText(game.returnStart()+"\n\n");
        Platform.runLater(() -> vstup.requestFocus());
        panelVychodu.setItems(seznamVychodu);
        game.getGamePlan().registruj(this);
        aktualizujSeznamVychodu();
    }
    @FXML
    private void aktualizujSeznamVychodu(){
        seznamVychodu.clear();
        seznamVychodu.addAll(game.getGamePlan().getCurrentRoom().getExits());
    }
    private void closeWindow() {
        PauseTransition delay = new PauseTransition(Duration.seconds(10));
        delay.setOnFinished(event -> Platform.exit());
        delay.play();
    }
    @FXML
    private void odesliVstup(ActionEvent actionEvent) {
        String prikaz = vstup.getText();
        vystup.appendText("> " + prikaz+"\n");
        String vysledek = game.processCommand(prikaz);
        vystup.appendText(vysledek+"\n\n");
        if (game.gameEnd()) {
            String epilog = game.returnEpilog();
            vystup.appendText(epilog + "\n\n");
            vstup.setDisable(true);
            tlacitkoOdesli.setDisable(true);
            // Zavře hru
            closeWindow();
        }
        vstup.clear();
    }

    public void ukončitHru(ActionEvent actionEvent) {
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION,"Opravdu chcete zavřít hru?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
        Platform.exit();
    }

    /**
     *
     */
    @Override
    public void aktualizuj() {
        System.out.println("aktualizuji");
        aktualizujSeznamVychodu();

    }
}
