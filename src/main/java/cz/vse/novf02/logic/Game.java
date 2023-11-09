package cz.vse.novf02.logic;

import cz.vse.novf02.main.Pozorovatel;
import cz.vse.novf02.main.PredmetPozorovani;
import cz.vse.novf02.main.ZmenaHry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Game implements IGame {

    private CommandList validCommands;
    private GamePlan gamePlan;
    private boolean gameEnd = false;
    private String epilog = "Konec hry.";
    //private Inventory inventory;
    private Map<ZmenaHry, Set<Pozorovatel>> seznamPozorovatelu = new HashMap<>();

    public Game() {
        gamePlan = new GamePlan();
        validCommands = new CommandList();
        initializeCommands();
    }

    private void initializeCommands() {
        validCommands.insertCommand(new CommandHelp(validCommands));
        validCommands.insertCommand(new CommandGo(gamePlan));
        validCommands.insertCommand(new CommandSearch(gamePlan));
        validCommands.insertCommand(new CommandEnd(this));
        validCommands.insertCommand(new CommandTake(gamePlan));
        validCommands.insertCommand(new CommandInventory(gamePlan));
        validCommands.insertCommand(new CommandRestart(gamePlan));
        validCommands.insertCommand(new CommandUse(gamePlan));
        for (ZmenaHry zmenaHry : ZmenaHry.values()) {
            seznamPozorovatelu.put(zmenaHry,new HashSet<>());
        }
    }
    public String returnStart() {
        return "Vítejte ve hře Tajemná krypta!\n Právě se nacházíš u vstupu do královských katakomb,\n kde se nachází hrobka bájného krále Šalamouna.\n Dveře jsou sice zamčené, ale čirou náhodou leží klíč pohozený přímo před nimi.\n Dnes je tvůj šťastný den!\n" +
                "Napište 'nápověda', pokud si nevíte rady, jak hrát dál.";
    }

    public String returnEpilog() {
        return epilog;
    }

    public boolean gameEnd() {
        return gameEnd;
    }

    public String processCommand(String inputRow) {
        String[] words = inputRow.split("[ \t]+");
        String commandWord = words[0];
        String[] parameters = new String[words.length - 1];
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = words[i + 1];
        }
        String textToPrint = " .... ";
        if (validCommands.isValidCommand(commandWord)) {
            ICommand command = validCommands.returnCommand(commandWord);
            textToPrint = command.resolveCommand(parameters);
        } else {
            textToPrint = "Tomuto příkazu nerozumím.";
        }

        // Kontrola výherní podmínky
        if (gamePlan.getCurrentRoom().containsItem("moudrost")) {
            setEpilog("Gratulace! Objevil(a) jsi moc koruny a vyhrál(a) hru!");
            setGameEnd(true);
        }

        if (gamePlan.getInventory().containsItem("prastara_mince") && gamePlan.getInventory().containsItem("zlata_lampa")) {
            setEpilog("Gratulace! Podařilo se ti najít bohatství, o kterém se ti ani nesnilo a vyhrál(a) jsi hru!");
            setGameEnd(true);}
        if (gamePlan.getCurrentRoom().equals("vychod") && gamePlan.getInventory().containsItem("koruna"))    {
            setEpilog("Gratulace! Podařilo se ti utéct s korunou v batohu. Vyhrál(a) jsi hru!");
            setGameEnd(true);}

        return textToPrint;
    }

    void setGameEnd(boolean gameEnd) {
        this.gameEnd = gameEnd;
    }

    public GamePlan getGamePlan() {
        return gamePlan;
    }

    public void setEpilog(String epilog) {
        this.epilog = epilog;
    }

    /**
     * @param zmenaHry
     * @param pozorovatel
     */
    @Override
    public void registruj(ZmenaHry zmenaHry, Pozorovatel pozorovatel) {
        seznamPozorovatelu.get(zmenaHry).add(pozorovatel);
        //debugging System.out.println("Pozorovatel " + pozorovatel + " byl zaregistrován.");

    }
    public void upozorniPozorovatele(ZmenaHry zmenaHry){
        for(Pozorovatel pozorovatel : seznamPozorovatelu.get(zmenaHry)) {
            pozorovatel.aktualizuj();
        }
    }
}
