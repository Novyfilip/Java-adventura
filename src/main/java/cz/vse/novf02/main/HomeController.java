package cz.vse.novf02.main;

import cz.vse.novf02.logic.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.Optional;

public class HomeController{
    @FXML
    private ListView<Room> panelVychodu;
    @FXML
    private Button tlacitkoOdesli;
    @FXML
    private TextField vstup;
    @FXML
    private TextArea vystup;

    private IGame game = new Game();


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
        game.getGamePlan().registruj(ZmenaHry.ZMENA_MISTNOSTI,()-> aktualizujSeznamVychodu());
        game.registruj(ZmenaHry.KONEC_HRY,() ->aktualizujKonecHry());
        aktualizujSeznamVychodu();
    }
    @FXML
    private void aktualizujSeznamVychodu(){
        seznamVychodu.clear();
        seznamVychodu.addAll(game.getGamePlan().getCurrentRoom().getExits());
    }
    private void aktualizujKonecHry() {
        System.out.println("aktualizuji konec");
        if (game.gameEnd()) {
            String epilog = game.returnEpilog();
            vystup.appendText(epilog + "\n\n");}

        vstup.setDisable(game.gameEnd());
        tlacitkoOdesli.setDisable(game.gameEnd());
        panelVychodu.setDisable(game.gameEnd());
        // Zavře hru
        closeWindow();
        }

    private void closeWindow() {
        PauseTransition delay = new PauseTransition(Duration.seconds(10));
        delay.setOnFinished(event -> Platform.exit());
        delay.play();
    }
    @FXML
    private void odesliVstup(ActionEvent actionEvent) {
        String prikaz = vstup.getText();
        vstup.clear();
        zpracujPrikaz(prikaz);}
    private void zpracujPrikaz(String prikaz){
        vystup.appendText("> " + prikaz+"\n");
        String vysledek = game.processCommand(prikaz);
        vystup.appendText(vysledek+"\n\n");
    }






    public void ukončitHru(ActionEvent actionEvent) {
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION,"Opravdu chcete zavřít hru?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
        Platform.exit();
    }

    @FXML
    private void klikPanelVychodu(MouseEvent mouseEvent) {
        Room cil = panelVychodu.getSelectionModel().getSelectedItem();
        if (cil == null) return;
        String prikaz = CommandGo.title+" "+cil;
        zpracujPrikaz(prikaz);//Jak
    }
}
