package cz.vse.novf02.logic;

public class Item {

    private String name;
    private Boolean canCarry;
    private String actionMessage; // Message shown when the item is used
    private String interactionItem; // Another item's name that this item can interact with
    private String interactionMessage; // Message shown when this item interacts with interactionItem
    private Item generatedItem; //loot z nepřátel a truhel

    public Item(String name, boolean canCarry, String actionMessage) {
        this.name = name;
        this.canCarry = canCarry;
        this.actionMessage = actionMessage;
        this.interactionItem = null; // Defaulted to null; can be set later
        this.interactionMessage = null; // Defaulted to null; can be set later
    }

    public String getItemName() {
        return this.name;
    }

    public Boolean getCanCarry() {
        return this.canCarry;
    }

    /**
     * Set the item that this item can interact with and the message for the interaction.
     * @param interactionItem The item's name this item interacts with.
     * @param interactionMessage The message shown when interacting.
     */
    public void setInteraction(String interactionItem, String interactionMessage) {
        this.interactionItem = interactionItem;
        this.interactionMessage = interactionMessage;
    }

    /**
     * Returns the name of the interaction item.
     * @return Name of the interaction item.
     */
    public String getInteractionItem() {
        return this.interactionItem;
    }

    /**
     * Checks if this item has an interaction with another item.
     * @param itemName Name of the other item.
     * @return true if they can interact, false otherwise.
     */
    public boolean canInteractWith(String itemName) {
        return itemName.equals(this.interactionItem);
    }

    /**
     * Method to return the interaction message.
     * @return Interaction message.
     */
    public String getInteractionMessage() {
        return this.interactionMessage;
    }

    /**
     * Method to return the action message when the item is used.
     * @return Message shown when the item is used.
     */
    public String use(Room room) {
        if (generatedItem != null) {
            room.insertItem(generatedItem);
            generatedItem = null;  // Aby nebyl předmět vytvořen vícekrát
        }
        return actionMessage;
    }

    public void setGeneratedItem(Item item) {
        this.generatedItem = item;
    }



}
