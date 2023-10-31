package cz.vse.novf02.logic;

public class Game implements IGame {

    private CommandList validCommands;
    private GamePlan gamePlan;
    private boolean gameEnd = false;
    private String epilog = "Konec hry.";

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
        if (gamePlan.getInventory().containsItem("koruna")) {
            setEpilog("Gratulace! Vrátil jsi královskou korunu do královské krypty a vyhrál hru!");
            setGameEnd(true);
        }

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
}
