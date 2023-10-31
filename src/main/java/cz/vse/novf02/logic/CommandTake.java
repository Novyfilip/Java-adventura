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
            // pokud chybí druhé slovo, tak ....
            return "Musíš upřesnit, co chceš!";

        }
        else if (parametry.length == 1 && currentRoom.containsItem(parametry[0])) {
            // pokud je druhe slovo takové, které lze vložit
            Item item = currentRoom.returnItem(parametry[0]);
            if(!item.getCanCarry()) {
                return "Tohle rozhodně neuneseš!";
            }
            else if (this.plan.getCurrentRoom().containsItem(parametry[0])) {
                // pokud je druhe slovo takové, které lze vložit
                Inventory inventory = this.plan.getInventory();
                if(inventory.isEmpty() && inventory.containsItem((parametry[0]))) {
                    return "Tento předmět jsi už sebral.";
                }
                else if (inventory.isEmpty()) {
                    this.plan.getInventory().insertItem(currentRoom.removeItem(parametry[0]));
                    return "Sebral jsi předmět " + item.getItemName();
                }
                else {
                    return "Tvůj inventář je plný";
                }
            }
        }return "Nic takového tady není!";
    }
    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  @return nazev prikazu
     */
    public String getTitle() {
        return title;
    }
}
