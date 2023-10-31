package cz.vse.novf02.logic;

/**
 * Class CommandRestart - implementuje interface ICommand.
 * This class represents the command to restart the game, returning the player to the initial state.
 *
 * @author Filip Nový
 * @version pro školní rok 2020/21
 */
public class CommandRestart implements ICommand {

    private GamePlan plan;

    /**
     * Konstruktor třídy.
     *
     * @param plan herní plán, který může být restartován.
     */
    public CommandRestart(GamePlan plan) {
        this.plan = plan;
    }

    /**
     * Restartuje hru, vrací hráče do počátečního stavu.
     *
     * @param parameters parametry příkazu (v tomto případě nejsou potřeba).
     * @return zpráva, která se zobrazí hráči po restartování hry.
     */
    @Override
    public String resolveCommand(String... parameters) {
        // Restartuje herní plán
        plan.reset();

        // Zpráva hráči
        return "Hra byla restartována. Jsi znovu na začátku.";
    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání).
     *
     * @return název příkazu
     */
    @Override
    public String getTitle() {
        return "restart";
    }
}
