package cz.vse.novf02.logic;

/**
 * Class CommandInventory - implementuje interface ICommand
 * @author Filip Nový
 * @version pro školní rok 2020/21
 */

public class CommandInventory implements ICommand{

    private static final String title = "inventory";
    private GamePlan plan;


    /**
     *  Konstruktor třídy
     *
     *  @param plan, game herní plán, který obsahuje inventář
     */
    public CommandInventory(GamePlan plan) {
        this.plan = plan;
    }


    /**
     * zobrazi predmety v inventari
     * @param parametry počet parametrů závisí na konkrétním příkazu.
     * @return
     */
    @Override
    public String resolveCommand(String... parametry) {
        return this.plan.getInventory().showInventory();
    }


    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  @return nazev prikazu
     */
    public String getTitle() {
        return title;
    }
}
