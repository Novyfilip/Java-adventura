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

public class Start extends Application  {


     /***************************************************************************
     * Metoda, prostřednictvím níž se spouští celá aplikace.
     *
     * @param args Parametry příkazového řádku
     *             vyhazuje FileNotFoundException kvůli txt souboru, do kterého se vypisuje průběh hry
     */
    public static void main(String[] args) throws FileNotFoundException {
        if(args.length > 0 && args[0].equals("text")) {

        IGame game = new Game();
        TextUI ui = new TextUI(game);
        ui.play();
        Platform.exit();
        }
        else Start.launch();

    }

    /**
     * @param primaryStage  hlavní okno  aplikace
     *
     * @throws Exception pokud se něco pokazí
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        // Načtení CSS pro změnu vzhledu okna
        String css = getClass().getResource("darkTheme.css").toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tajemná krypta");
        primaryStage.show();
        Sounds.playSound("start.mp3");
    }



}
