package cz.vse.novf02.logic;

/**
 * Class CommandHelp - implementuje interface ICommand
 * @author Filip Nový
 * @version pro školní rok 2020/2021
 */

public class CommandHelp implements ICommand {

    private static final String title = "nápověda";
    private CommandList validCommands;


    /**
     *  Konstruktor třídy
     *
     *  @param validCommands seznam příkazů, které je možné ve hře použít, aby je nápověda mohla zobrazit uživateli.
     */
    public CommandHelp(CommandList validCommands) {
        this.validCommands = validCommands;
    }


    /**
     *  Vrací základní nápovědu po zadání příkazu "napoveda". Nyní se vypisuje
     *  vcelku primitivní zpráva a seznam dostupných příkazů.
     *  @return napoveda ke hre
     */
    @Override
    public String resolveCommand(String... parametry) {
        return "Tvým cílem je najít korunu krále Šalamouna, který je zde pohřbený.\n \n"
                + "Můžeš používat tyto příkazy:\n"
                + validCommands.returnCommandNames() + ", jdi zpet";
    }


    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  @return nazev prikazu
     */
    public String getTitle() {
        return title;
    }
}
