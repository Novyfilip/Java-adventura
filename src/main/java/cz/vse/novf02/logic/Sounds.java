package cz.vse.novf02.logic;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sounds {
    public static void playSound(String soundFileName) {
        try {
            String musicPath = "src/main/resources/cz/vse/novf02/main/adventuraAssets/zvuky/" + soundFileName;
            Media sound = new Media(new File(musicPath).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);


            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Chyba přehrání zvuku");
            e.printStackTrace();
        }
    }
}
