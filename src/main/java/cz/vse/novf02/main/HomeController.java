package cz.vse.novf02.main;

import cz.vse.novf02.logic.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HomeController{
    @FXML
    private ImageView hrac;
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
    private Map <String, Point2D> souradniceProstoru = new HashMap<>();
    @FXML
    private void initialize() {
        vystup.appendText(game.returnStart()+"\n\n");
        Platform.runLater(() -> vstup.requestFocus());
        panelVychodu.setItems(seznamVychodu);
        game.getGamePlan().registruj(ZmenaHry.ZMENA_MISTNOSTI,()-> {
            aktualizujSeznamVychodu();
            aktualizujPolohuHrace();
        });
        game.registruj(ZmenaHry.KONEC_HRY,() ->aktualizujKonecHry());
        aktualizujSeznamVychodu();
        vlozSouradnice();


    }

    private void vlozSouradnice() {
        souradniceProstoru.put("vstupDoKatakomb", new Point2D(210,225));
        souradniceProstoru.put("vstupniMistnost", new Point2D(216,168));
        souradniceProstoru.put("leveRozcesti", new Point2D(80,150));
        souradniceProstoru.put("loveckySalon", new Point2D(15,150));
        souradniceProstoru.put("hrobkaPrince", new Point2D(15,90));
        souradniceProstoru.put("levyDungeon", new Point2D(111,111));
        souradniceProstoru.put("levyMost", new Point2D(110,67));
        souradniceProstoru.put("pokladnice", new Point2D(100,22));
        souradniceProstoru.put("kralovskaKrypta", new Point2D(210,22));
        souradniceProstoru.put("zlataSin", new Point2D(304,22));
        souradniceProstoru.put("pravyMost", new Point2D(309,69));
        souradniceProstoru.put("stredKatakomb", new Point2D(210,90));
        souradniceProstoru.put("pravyDungeon", new Point2D(280,113));
        souradniceProstoru.put("studanka", new Point2D(390,100));
        souradniceProstoru.put("hrobkaRytiru", new Point2D(400,150));
        souradniceProstoru.put("praveRozcesti", new Point2D(333,133));
        souradniceProstoru.put("vychod", new Point2D(415,225));
    }

    @FXML
    private void aktualizujSeznamVychodu(){
        seznamVychodu.clear();
        seznamVychodu.addAll(game.getGamePlan().getCurrentRoom().getExits());
    }

    private void aktualizujPolohuHrace(){
        String prostor = game.getGamePlan().getCurrentRoom().getRoomName();
        hrac.setLayoutX(souradniceProstoru.get(prostor).getX());
        hrac.setLayoutY(souradniceProstoru.get(prostor).getY());

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
