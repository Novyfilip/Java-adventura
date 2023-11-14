package cz.vse.novf02.main;

import cz.vse.novf02.logic.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.Collection;
import java.util.Objects;

public class InventoryController implements Pozorovatel{


    @FXML
    private GridPane inventoryGrid;
    private TextArea vystup;

    private IGame game;


    /** Nastaví počáteční parametry
     * @param game hra
     * @param vystup výstup v JavaFX TextArea
     */
    public void setGame(IGame game, TextArea vystup) {
        this.game = game;
        this.vystup = vystup;
        game.getGamePlan().getInventory().registruj(ZmenaHry.INVENTORY_CHANGE, this);
        updateInventoryGrid();
    }

    /**
     * Obecná aktualizační metoda
     */
    @Override
    public void aktualizuj() {
        updateInventoryGrid();
    }

    /**
     * Aktualizace inventáře. Do inventáře přidává obrázky předmětů do definovaných míst
     */
    private void updateInventoryGrid() {
        inventoryGrid.getChildren().clear();
        Collection<Item> items = game.getGamePlan().getInventory().getAllItems();

        int row = 0;
        int column = 0;

        for (Item item : items) {
            String imagePath = "/cz/vse/novf02/main/adventuraAssets/predmety/" + item.getItemName() + ".png";
            System.out.println("Cesta pro" + item.getItemName() + ": " + imagePath); // Debugging
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));

            ImageView itemView = new ImageView(image);
            itemView.setFitHeight(64);
            itemView.setFitWidth(64);
            itemView.setOnMouseClicked(e ->{
                System.out.println("Pokouším se použít: " + item.getItemName());//debug
                String vysledek = game.processCommand("pouzij " + item.getItemName());
                game.processCommand("pouzij " + item.getItemName());
                vystup.appendText(vysledek + "\n");
                    });
            inventoryGrid.add(itemView, column, row);
            column++;
            if (column > 2) {
                column = 0;
                row++;
            }
        }
    }
}
