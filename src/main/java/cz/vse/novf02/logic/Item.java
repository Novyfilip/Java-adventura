package cz.vse.novf02.logic;

import cz.vse.novf02.main.Pozorovatel;
import cz.vse.novf02.main.ZmenaHry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Item {

    private String name;
    private Boolean canCarry;
    private String actionMessage; // Message shown when the item is used
    private String interactionItem; // Another item's name that this item can interact with
    private String interactionMessage; // Message shown when this item interacts with interactionItem
    private Item generatedItem; //loot z nepřátel a truhel
    private boolean isLoot; // flag pro speciální zprávu při sebrání
    private String imagePath; // Cesta k obrázku
    private Map<ZmenaHry, Set<Pozorovatel>> seznamPozorovatelu = new HashMap<>();

    /**
     * @return zjistí cestu k obrázku
     */
    // Konstruktor
    public String getImagePath() {
        return imagePath;
    }

    /**
     * nastaví parametr podle jména předmětu a stringu adresy
     */
    public void setImagePath() {
        this.imagePath = constructImagePath(name);
    }

    /**
     * @param itemName jméno předmětu
     * @return adresa obrázku k předmětu
     */
    private String constructImagePath(String itemName) {
        return "/cz/vse/novf02/main/adventuraAssets/predmety/" + itemName + ".png";
    }

    public Item(String name, boolean canCarry, String actionMessage) {
        this.name = name;
        this.canCarry = canCarry;
        this.actionMessage = actionMessage;
        this.interactionItem = null;
        this.interactionMessage = null;
        this.imagePath = constructImagePath(name);
    }

    /**
     * @return jméno předmětu
     */
    public String getItemName() {
        return this.name;
    }

    /**
     * @return dá se předmět unést?
     */
    public Boolean getCanCarry() {
        return this.canCarry;
    }

    /**
     * Nastaví speciální interakci s jiným předmětem
     * @param interactionItem S čím předmět má speciální interkaci
     * @param interactionMessage Zpráva při interakci
     */
    public void setInteraction(String interactionItem, String interactionMessage) {
        this.interactionItem = interactionItem;
        this.interactionMessage = interactionMessage;
    }

    /**
     * Vrací jméno předmětu k interakci
     * @return jméno předmětu k interakci
     */
    public String getInteractionItem() {
        return this.interactionItem;
    }

    /**
     * Kontroluje, zda existuje interakce
     * @param itemName Jméno druhého předmětu
     * @return true pokud je interakce definována
     */
    public boolean canInteractWith(String itemName) {
        return itemName.equals(this.interactionItem);
    }

    /**
     * Vrací interakční zprávu.
     * @return Interaction message.
     */
    public String getActionMessage() {
        return this.actionMessage;
    }

    /**
     * Vrací akční zprávu
     * @return Zpráva zobrazená, když se použije předmět
     */
    public String use(Room currentRoom) {
        // Kontroluje, jestli se v místnosti nachází cíl
        Item target = currentRoom.returnItem(this.interactionItem);
        if (target != null) {
            // Má předmět definovaný loot?
            if (target.getGeneratedItem() != null) {
                // Vytvoří loot a vloží ho do místnosti
                currentRoom.insertItem(target.getGeneratedItem());
            }
            // Odstraní cíl po použití
            currentRoom.removeItem(target.getItemName());

            // Vrátí hlášku o úspěchu
            return this.interactionMessage;
        }
        // Pokud cíl není v místnosti
        return "Nelze použít " + this.name + " zde.";
    }



    /**
     * @param item generovaný loot
     */
    //generování předmětů
    public void setGeneratedItem(Item item) {
        this.generatedItem = item;
    }

    /**
     * @return generovaný loot
     */
    public Item getGeneratedItem() {
        return this.generatedItem;
    }

    /**
     * @param isLoot Nastaví, jestli je předmět loot
     */
    public void setAsLoot(boolean isLoot) {
        this.isLoot = isLoot;
    }

    /**
     * @return true - generovaný předmět
     */
    // Metoda na kontrolu, jestli je předmět pokladem
    public boolean isLoot() {
        return isLoot;
    }











}
