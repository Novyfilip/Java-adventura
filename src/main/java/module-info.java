module cz.vse.novf02 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;


    opens cz.vse.novf02.main to javafx.fxml;
    exports cz.vse.novf02.main;
    exports cz.vse.novf02.logic;
    opens cz.vse.novf02.logic to javafx.fxml;
    exports cz.vse.novf02.TextUI;
    opens cz.vse.novf02.TextUI to javafx.fxml;
}