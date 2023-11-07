package cz.vse.novf02.logic;

public class Item {

    private String name;
    private Boolean canCarry;
    private String actionMessage; // Message shown when the item is used
    private String interactionItem; // Another item's name that this item can interact with
    private String interactionMessage; // Message shown when this item interacts with interactionItem
    private Item generatedItem; //loot z nepřátel a truhel
    private boolean isLoot; // flag pro speciální zprávu při sebrání

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
    public String getActionMessage() {
        return this.actionMessage;
    }

    /**
     * Method to return the action message when the item is used.
     * @return Message shown when the item is used.
     */
    public String use(Room currentRoom) {
        // Kontroluje, jestli se v místnosti nachází cíl
        Item target = currentRoom.returnItem(this.interactionItem);
        if (target != null) {
            // Check if the target has loot to generate
            if (target.getGeneratedItem() != null) {
                // Vytvoří loot a vloží ho do místnosti
                currentRoom.insertItem(target.getGeneratedItem());
            }
            // Odstraní cíl po použití
            currentRoom.removeItem(target.getItemName());

            // Return the interaction message or any confirmation message that the action was successful
            return this.interactionMessage;
        }
        // If the target is not in the room or no interaction is possible
        return "Nelze použít " + this.name + " zde.";
    }




    //generování předmětů
    public void setGeneratedItem(Item item) {
        this.generatedItem = item;
    }
    public Item getGeneratedItem() {
        return this.generatedItem;
    }
    public void setAsLoot(boolean isLoot) {
        this.isLoot = isLoot;
    }
    // Metoda na kontrolu, jestli je předmět pokladem
    public boolean isLoot() {
        return isLoot;
    }

    //Metoda pro naplnění kyblíku
    private boolean isFilled = false;

    // Method to fill the bucket
    public void fill() {
        if(this.name.equals("kyblik")) {
            this.isFilled = true;
            this.actionMessage = "Kyblík je nyní naplněný vodou.";
        }
    }

    // Getter pro isFilled
    public boolean isFilled() {
        return isFilled;
    }






}
