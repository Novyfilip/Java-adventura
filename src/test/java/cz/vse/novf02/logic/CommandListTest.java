package cz.vse.novf02.logic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import  org.junit.jupiter.api.BeforeEach;
import  org.junit.jupiter.api.AfterEach;

import cz.vse.novf02.logic.CommandEnd;
import cz.vse.novf02.logic.CommandGo;
import cz.vse.novf02.logic.CommandList;
import cz.vse.novf02.logic.Game;


public class CommandListTest {
    private Game game;
    private CommandEnd end;
    private CommandGo go;


    /***************************************************************************
     * Metoda se provede před spuštěním každé testovací metody.
     */
    @BeforeEach
    public void setUp() {
        game = new Game();
        go = new CommandGo(game.getGamePlan());
        end = new CommandEnd(game);

    }

    /***************************************************************************
     * Úklid po testu - tato metoda se spustí po vykonání každé testovací metody.
     */
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddCommand() {
        CommandList commandList = new CommandList();
        commandList.insertCommand(go);
        commandList.insertCommand(end);
        assertEquals(end, commandList.returnCommand("konec"));
        assertEquals(go, commandList.returnCommand("jdi"));
        assertEquals(null, commandList.returnCommand("nápověda"));

    }


}
