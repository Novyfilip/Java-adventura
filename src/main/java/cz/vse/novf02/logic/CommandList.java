package cz.vse.novf02.logic;
import java.util.HashMap;
import java.util.Map;

/**
 *  Class CommandList - Seznam příkazů adventury.
 *  Používá se pro rozpoznávání příkazů
 *  a vrácení odkazu na třídu implementující konkrétní příkaz.
 *  Každý nový příkaz (instance implementující rozhraní ICommand) se
 *  musí do seznamu přidat metodou insertCommand.
 *  @author Filip Nový
 *  @version pro školní rok 2020/21
 */

public class CommandList {


    /**
     * mapa pro uložení přípustných příkazů
     */
    private Map<String,ICommand> commandMap;


    /**
     * Konstruktor
     */
    public CommandList() {
        this.commandMap = new HashMap<>();
    }


    /**
     * Vkládá nový příkaz.
     *@param  command  Instance třídy implementující rozhraní ICommand
     */
    public void insertCommand(ICommand command) {
        commandMap.put(command.getTitle(),command);
    }


    /**
     * Vrací odkaz na instanci třídy implementující rozhraní ICommand,
     * která provádí příkaz uvedený jako parametr.
     *@param  string  klíčové slovo příkazu, který chce hráč zavolat
     *@return          instance třídy, která provede požadovaný příkaz
     */
    public ICommand returnCommand(String string) {
        if (commandMap.containsKey(string)) {
            return commandMap.get(string);
        }
        else {
            return null;
        }
    }


    /**
     * Kontroluje, zda zadaný řetězec je přípustný příkaz.
     *@param  string  Řetězec, který se má otestovat, zda je přípustný příkaz
     *@return Vrací hodnotu true, pokud je zadaný
     * řetězec přípustný příkaz
     */
    public boolean isValidCommand(String string) {
        return commandMap.containsKey(string);
    }


    /**
     *  Vrací seznam přípustných příkazů, jednotlivé příkazy jsou odděleny mezerou.
     *  @return Řetězec, který obsahuje seznam přípustných příkazů
     */
    public String returnCommandNames() {
        String list = "";
        for (String commandWord : commandMap.keySet()){
            list += commandWord + ", ";
        }
        list = list.substring(0, list.length()-2);
        return list;
    }
}
