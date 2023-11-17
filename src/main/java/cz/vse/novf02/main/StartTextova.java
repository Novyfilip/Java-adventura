package cz.vse.novf02.main;
import cz.vse.novf02.TextUI.TextUI;
import cz.vse.novf02.logic.Game;
import cz.vse.novf02.logic.IGame;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import cz.vse.novf02.logic.Sounds;





/*******************************************************************************
 * Třída  Start je hlavní třídou projektu,
 * který představuje jednoduchou textovou adventuru.
 */

public class StartTextova {


    /***************************************************************************
     * Metoda, prostřednictvím níž se spouští celá aplikace.
     *
     * @param args Parametry příkazového řádku
     *             vyhazuje FileNotFoundException kvůli txt souboru, do kterého se vypisuje průběh hry
     */
    public static void main(String[] args) throws FileNotFoundException {
        if(args.length > 0 && args[0].equals("text")) {

            IGame game = new Game();
            //TextUI ui = new TextUI(game);
            //ui.play();
            //Platform.exit();
        }


    }




}
