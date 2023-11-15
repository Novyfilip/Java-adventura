package cz.vse.novf02.logic;


/**
 * Třída CommandEnd - implementuje interface ICommand
 * @author Filip Nový
 * @version pro školní rok 2020/2021
 */

public class CommandEnd implements ICommand{

    private static final String title = "konec";
    private Game game;


    /**
     *  Konstruktor třídy
     *
     *  @param game odkaz na hru, která má být příkazem konec ukončena
     */
    public CommandEnd(Game game) {
        this.game = game;
    }


    /**
     * V případě, že příkaz má jen jedno slovo "konec" hra končí(volá se metoda setKonecHry(true))
     * jinak pokračuje."
     * @return zpráva, kterou vypíše hra hráči
     */
    @Override
    public String resolveCommand(String... parametry) {
        if (parametry.length > 0) {
            return "Jsi si jistý že chceš hru vypnout? Pro konec napiš 'konec'";
        }
        else {
            game.setGameEnd(true, false);
            return "Zemřel jsi hlady.";
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
