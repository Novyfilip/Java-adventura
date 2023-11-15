package cz.vse.novf02.logic;

/**
 * Class CommandSearch - implementuje interface ICommand
 * @author Filip Nový
 * @version pro školní rok 2020/2021
 */

public class CommandSearch implements ICommand {

    private static final String title = "prohledat";
    private GamePlan plan;


    /**
     *  Konstruktor třídy
     *
     *  @param plan herní plán, který bude prohledán pro zobrazení předmětů hráči
     */
    public CommandSearch(GamePlan plan) {
        this.plan = plan;
    }


    /**
     *  prohledá místnost a vypíše předměty v ní
     */
    @Override
    public String resolveCommand(String... parametry) {
        Sounds.playSound("prohledat.mp3");
        return this.plan.getCurrentRoom().itemDescription();

    }


    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  @return nazev prikazu
     */
    public String getTitle() {
        return title;
    }
}
