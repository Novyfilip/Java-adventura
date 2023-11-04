package cz.vse.novf02.main;

import cz.vse.novf02.TextUI.TextUI;
import cz.vse.novf02.logic.Game;
import cz.vse.novf02.logic.IGame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

/*******************************************************************************
 * Třída  Start je hlavní třídou projektu,
 * který představuje jednoduchou textovou adventuru.
*/

public class StartTextova extends Application  {


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

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws Exception if something goes wrong
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new Button());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
