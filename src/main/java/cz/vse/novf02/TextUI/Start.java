package cz.vse.novf02.TextUI;

import cz.vse.novf02.TextUI.TextUI;
import cz.vse.novf02.logic.Game;
import cz.vse.novf02.logic.IGame;

import java.io.FileNotFoundException;

/*******************************************************************************
 * Třída  Start je hlavní třídou projektu,
 * který představuje jednoduchou textovou adventuru.
*/

public class Start {


     /***************************************************************************
     * Metoda, prostřednictvím níž se spouští celá aplikace.
     *
     * @param args Parametry příkazového řádku
     *             vyhazuje FileNotFoundException kvůli txt souboru, do kterého se vypisuje průběh hry
     */
    public static void main(String[] args) throws FileNotFoundException {

        IGame game = new Game();
        TextUI ui = new TextUI(game);
        ui.play();
    }
}
