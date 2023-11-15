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
            // If no item name is provided, ask for clarification
            return "Musíš upřesnit, co chceš sebrat!";
        } else {
            String itemName = parametry[0];
            if (!currentRoom.containsItem(itemName)) {
                // If the item is not present in the current room
                return "Nic takového tady není!";
            }

            Item item = currentRoom.returnItem(itemName);
            if (!item.getCanCarry()) {
                // Pokud se předmět nedá sebrat
                return "Tohle rozhodně neuneseš!";
            }

            Inventory inventory = plan.getInventory();
            if (inventory.containsItem(itemName)) {

                return "Tento předmět jsi už sebral.";
            }  else {
                // Když se dá předmět vzít a je místo v inventáři
                Sounds.playSound("sebrat.mp3");
                item = currentRoom.removeItem(itemName);
                inventory.insertItem(item);
                // Zkontroluje přítomnost interactionMessage
                String pickupMessage = "Sebral jsi předmět " + item.getItemName() + ".";
                 if (item.isLoot() && item.getActionMessage() != null) {
                    pickupMessage += "\n" + item.getActionMessage();
                }
                return pickupMessage;
            }
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
