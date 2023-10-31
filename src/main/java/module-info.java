module cz.vse.novf02 {
    requires javafx.controls;
    requires javafx.fxml;


    opens cz.vse.novf02 to javafx.fxml;
    exports cz.vse.novf02;
    exports cz.vse.novf02.logic;
    opens cz.vse.novf02.logic to javafx.fxml;
    exports cz.vse.novf02.TextUI;
    opens cz.vse.novf02.TextUI to javafx.fxml;
}