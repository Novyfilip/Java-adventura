package cz.vse.novf02.logic;

/**
 * Class CommandTake - implementuje interface ICommand
 * @author Filip Nový
 * @version pro školní rok 2020/2021
 */

public class CommandTake implements ICommand {

    private static final String title = "sebrat";
    private GamePlan plan;

    /**
     *  Konstruktor třídy
     *
     *  @param plan, game herní plán, který bude prohledán pro všechny věci, které můžeme vzít
     */
    public CommandTake(GamePlan plan) {
        this.plan = plan;
    }
    /**
     * sebere predmet a vlozi ho do inventare
     * @param parametry předmět, který chceme vložit do invetáře
     * @return text / přiání věci do inventáře
     */
    @Override
    public String resolveCommand(String... parametry) {
        Room currentRoom = plan.getCurrentRoom();

        if (parametry.length == 0) {
            // If no item name is provided, prompt the user.
            return "Musíš upřesnit, co chceš!";
        }

        String itemName = parametry[0];
        if (currentRoom.containsItem(itemName)) {
            Item item = currentRoom.returnItem(itemName);
            if (!item.getCanCarry()) {
                return "Tohle rozhodně neuneseš!";
            } else {
                Inventory inventory = plan.getInventory();
                if (inventory.containsItem(itemName)) {
                    return "Tento předmět jsi už sebral.";
                } else if (!inventory.isFull()) {
                    item = currentRoom.removeItem(itemName);
                    inventory.insertItem(item);

                    // Check if the item is marked as loot and has an action message
                    if (item.isLoot() && item.getActionMessage() != null) {
                        return "Sebral jsi předmět " + item.getItemName() + ". " + item.getActionMessage();
                    } else {
                        return "Sebral jsi předmět " + item.getItemName();
                    }
                } else {
                    return "Tvůj inventář je plný.";
                }
            }
        } else {
            return "Nic takového tady není!";
        }
    }


    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  @return nazev prikazu
     */
    public String getTitle() {
        return title;
    }
}
