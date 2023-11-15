package cz.vse.novf02.logic;

public class CommandUse implements ICommand {

    private GamePlan plan;

    public CommandUse(GamePlan plan) {
        this.plan = plan;
    }

    /**
     * Provede pokus o použití předmětu. Pokud hráč vlastní předmět, dojde k jeho použití.
     * Pokud ne, vrátí zprávu o neúspěchu.
     *
     * @param parametry název předmětu, který chce hráč použít.
     * @return zpráva informující o výsledku akce.
     */
    @Override
    public String resolveCommand(String... parametry) {
        if (parametry.length == 0) {
            return "Co chceš použít? Musíš zadat název předmětu.";
        }

        String itemName = parametry[0];
        Item itemToUse = plan.getInventory().getItem(itemName);
        if (itemToUse == null) {
            return "Tento předmět nemáš v inventáři.";
        }

        // prověření přítomnosti cílového předmětu
        Item targetItem = plan.getCurrentRoom().getItem(itemToUse.getInteractionItem());
        if (targetItem == null) {
            return "V tomto prostoru nemůžeš tento předmět použít.";
        }

        // hláška o úspěchu
        Sounds.playSound("pouzij.mp3");
        return itemToUse.use(plan.getCurrentRoom());
    }

    /**
     * Vrátí název příkazu. V tomto prostoru nemůžeš tento předmět použít."
     *
     * @return název příkazu
     */
    @Override
    public String getTitle() {
        return "pouzij";
    }
}


