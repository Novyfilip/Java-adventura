package cz.vse.novf02.main;

import cz.vse.novf02.logic.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;

public class HomeController {
    @FXML
    private ImageView hrac;
    @FXML
    private ListView<Room> panelVychodu;
    @FXML
    private ListView<Item> roomItemsListView;
    @FXML
    private Button tlacitkoOdesli;
    @FXML
    private TextField vstup;
    @FXML
    private TextArea vystup;
    @FXML
    private Button tlacitkoProhledat;

    private IGame game = new Game();


    private ObservableList<Room> seznamVychodu = FXCollections.observableArrayList();
    private Map<String, Point2D> souradniceProstoru = new HashMap<>();


    /**
     * Počáteční nastavení zobrazovaného herního plánu
     */
    @FXML
    private void initialize() {
        vystup.appendText(game.returnStart() + "\n\n");
        Platform.runLater(() -> vstup.requestFocus());
        panelVychodu.setItems(seznamVychodu);
        game.getGamePlan().registruj(ZmenaHry.ZMENA_MISTNOSTI, () -> {
            aktualizujSeznamVychodu();
            aktualizujPolohuHrace();
            updateRoomDescription();//Nové
            updateRoomItems(); //První update

        });
        game.getGamePlan().getCurrentRoom().registruj(ZmenaHry.ITEM_CHANGE, this::updateRoomItems);
        updateRoomItems(); // pro panel předmětů
        game.registruj(ZmenaHry.KONEC_HRY, () -> aktualizujKonecHry());
        aktualizujSeznamVychodu();
        vlozSouradnice();
        Room currentRoom = game.getGamePlan().getCurrentRoom();
        updateCurrentRoomImage(currentRoom);
        //implementace obrázků k východům pomocí annonymní třídy
        panelVychodu.setCellFactory(new Callback<ListView<Room>, ListCell<Room>>() {
            @Override
            public ListCell<Room> call(ListView<Room> listView) {
                return new ListCell<Room>() {
                    @Override
                    protected void updateItem(Room room, boolean empty) {
                        super.updateItem(room, empty);
                        if (empty || room == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            String imagePath = "/cz/vse/novf02/main/adventuraAssets/prostory/" + room.getRoomName() + ".png";
                            try {
                                ImageView iv = new ImageView(getClass().getResource(imagePath).toExternalForm());
                                iv.setFitHeight(32);
                                iv.setFitWidth(32);
                                setGraphic(iv);
                                setText(room.getRoomName());
                            } catch (Exception e) {
                                setText("Obrázek nenalezen pro " + room.getRoomName());
                                setGraphic(null);
                            }
                        }
                    }
                };
            }
        });


    }


    /**
     * Definujeme, kam umístit postavičku pro každou místnost
     */
    private void vlozSouradnice() {
        souradniceProstoru.put("vstupDoKatakomb", new Point2D(210, 225));
        souradniceProstoru.put("vstupniMistnost", new Point2D(216, 168));
        souradniceProstoru.put("leveRozcesti", new Point2D(80, 150));
        souradniceProstoru.put("loveckySalon", new Point2D(15, 150));
        souradniceProstoru.put("hrobkaPrince", new Point2D(15, 90));
        souradniceProstoru.put("levyDungeon", new Point2D(111, 111));
        souradniceProstoru.put("levyMost", new Point2D(110, 67));
        souradniceProstoru.put("pokladnice", new Point2D(100, 22));
        souradniceProstoru.put("kralovskaKrypta", new Point2D(210, 22));
        souradniceProstoru.put("zlataSin", new Point2D(304, 22));
        souradniceProstoru.put("pravyMost", new Point2D(309, 69));
        souradniceProstoru.put("stredKatakomb", new Point2D(210, 90));
        souradniceProstoru.put("pravyDungeon", new Point2D(280, 113));
        souradniceProstoru.put("studanka", new Point2D(390, 100));
        souradniceProstoru.put("hrobkaRytiru", new Point2D(400, 150));
        souradniceProstoru.put("praveRozcesti", new Point2D(333, 133));
        souradniceProstoru.put("vychod", new Point2D(415, 225));
    }

    /**
     * Aktualizuje možné východy z místnosti
     */
    @FXML
    private void aktualizujSeznamVychodu() {
        seznamVychodu.clear();
        seznamVychodu.addAll(game.getGamePlan().getCurrentRoom().getExits());
    }

    /**
     * Mění polohu hráče na mapě
     */
    private void aktualizujPolohuHrace() {
        String prostor = game.getGamePlan().getCurrentRoom().getRoomName();
        hrac.setLayoutX(souradniceProstoru.get(prostor).getX());
        hrac.setLayoutY(souradniceProstoru.get(prostor).getY());
        updateCurrentRoomImage(game.getGamePlan().getCurrentRoom());


    }


    /**
     * Umožňuje konec hry
     */
    private void aktualizujKonecHry() {
        if (game.gameEnd()) {
            String epilog = game.returnEpilog();
            vystup.appendText(epilog + "\n\n");
        }

        vstup.setDisable(game.gameEnd());
        tlacitkoOdesli.setDisable(game.gameEnd());
        panelVychodu.setDisable(game.gameEnd());
        // Zavře hru
        closeWindow();
    }

    /**
     * Zavře okno s dostatečným časem pro přečtení Epilogu
     */
    private void closeWindow() {
        PauseTransition delay = new PauseTransition(Duration.seconds(10));
        delay.setOnFinished(event -> Platform.exit());
        delay.play();
    }

    /**
     * Předá hře zadaná text z TextFieldu
     *
     * @param actionEvent příkaz
     */
    @FXML
    private void odesliVstup(ActionEvent actionEvent) {
        String prikaz = vstup.getText();
        vstup.clear();
        zpracujPrikaz(prikaz);
    }

    /**
     * Informuje hráče o výsledku provedené akce v TextAree
     *
     * @param prikaz zadaný text
     */
    private void zpracujPrikaz(String prikaz) {
        vystup.appendText("> " + prikaz + "\n");
        String vysledek = game.processCommand(prikaz);
        vystup.appendText(vysledek + "\n\n");

    }


    /**
     * Zavře hru, když si ji hráč přeje ukončit
     *
     * @param actionEvent potvrzení konce
     */
    public void ukončitHru(ActionEvent actionEvent) {
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION, "Opravdu chcete zavřít hru?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
            Platform.exit();
    }

    /**
     * Definuje co se stane po kliknutí na panel
     *
     * @param mouseEvent klik
     */
    @FXML
    private void klikPanelVychodu(MouseEvent mouseEvent) {
        Room cil = panelVychodu.getSelectionModel().getSelectedItem();
        if (cil == null) return;
        String prikaz = CommandGo.title + " " + cil.getRoomName();
        zpracujPrikaz(prikaz);//Jak
    }

    /**
     * Aktualizuje popis místnosti na současnou místnost
     */
    private void updateRoomDescription() {
        Room currentRoom = game.getGamePlan().getCurrentRoom();
        String roomName = currentRoom.getRoomName();
        vystup.appendText("Nyní jsi v: " + roomName + "\n\n");
    }

    @FXML
    private ImageView currentRoomImageView;

    /**
     * Aktualizuje zobrazovaný obrázek
     *
     * @param currentRoom současná místnost
     */
    public void updateCurrentRoomImage(Room currentRoom) {
        String imageUrl = getClass().getResource("/cz/vse/novf02/main/adventuraAssets/prostory/" + currentRoom.getRoomName() + ".png").toExternalForm();
        if (imageUrl != null) {
            ImageView mistnost = new ImageView(imageUrl);
            mistnost.setFitHeight(142);
            mistnost.setFitWidth(142);
            currentRoomImageView.setImage(mistnost.getImage());
        } else {
            // Když se nenajde obrázek
            currentRoomImageView.setImage(null);
        }
    }


    /**
     * Definuje co se stane po kliknutí na nápovědu v menu
     *
     * @param actionEvent kliknutí na položku menu
     */
    public void napovedaKlik(ActionEvent actionEvent) {
        Stage napovedaStage = new Stage();
        WebView wv = new WebView();
        Scene napovedaScena = new Scene(wv);
        napovedaStage.setScene(napovedaScena);
        napovedaStage.show();
        wv.getEngine().load(getClass().getResource("napoveda.html").toExternalForm());
    }




    /**
     * Vrátí všechno do původního stavu
     *
     * @param event kliknutí na položku nová hra
     */
    @FXML
    private void novaHra(ActionEvent event) {
        this.game = new Game();
        vystup.clear();
        aktualizujSeznamVychodu();
        updateRoomDescription();
        aktualizujPolohuHrace();
        updateCurrentRoomImage(game.getGamePlan().getCurrentRoom());
        vystup.appendText(game.returnEpilog() + "\n\n");
        vystup.appendText(game.returnStart() + "\n\n");
        // možná bude třeba ještě upozornit další observery


    }

    @FXML
    private void otevriInventar(ActionEvent event) {
        try {
            // Vytvoří se a FXMLLoader instance
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cz/vse/novf02/main/inventory.fxml")); // Adjust the path if necessary
            Parent root = loader.load(); // Load the FXML

            // Načte se controller a nastaví se instance hry
            InventoryController inventoryController = loader.getController();
            inventoryController.setGame(game, vystup);

            // Nové okno pro inventář
            Stage inventoryStage = new Stage();
            inventoryStage.setTitle("Inventář");
            inventoryStage.setScene(new Scene(root)); // Scéna z FXML souboru
            inventoryStage.show(); // Nastavení stage

        } catch (IOException e) {
            e.printStackTrace(); // chybová hláška
        }


    }



    private void updateRoomItems() {
        Room currentRoom = game.getGamePlan().getCurrentRoom();
        ObservableList<Item> itemsList = FXCollections.observableArrayList(currentRoom.getItems().values());
        roomItemsListView.setItems(itemsList);

        roomItemsListView.setCellFactory(param -> new ListCell<Item>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    try {
                        Image image = new Image(getClass().getResourceAsStream(item.getImagePath()));
                        ImageView imageView = new ImageView(image);
                        imageView.setFitHeight(72);
                        imageView.setFitWidth(72);
                        setGraphic(imageView);
                        setText(item.getItemName());
                    } catch (Exception e) {
                        setText("Obrázek nenalezen pro " + item.getItemName());
                        setGraphic(null);
                    }
                }
            }
        });
    }




    @FXML
    private void predmetKlik(MouseEvent event) {
        Item selectedItem = roomItemsListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            game.processCommand("sebrat " + selectedItem.getItemName());
        }
    }


    public void klikProhledat(ActionEvent event) {
            game.processCommand("prohledat");
            updateRoomItems();
            vystup.appendText( game.getGamePlan().getCurrentRoom().itemDescription());
        }

}

