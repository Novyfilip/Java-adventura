package cz.vse.novf02.main;
import cz.vse.novf02.logic.GamePlan;
import cz.vse.novf02.logic.Room;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/*public class ListCellProstor extends ListCell<Room> {
    /**
     * @param room  The new item for the cell.
     * @param empty whether or not this cell represents data from the list. If it
     *              is empty, then it does not represent any domain data, but is a cell
     *              being used to render an "empty" row.
     */
   /* @Override
    public void updateItem(Room room, boolean empty) {
        super.updateItem(room, empty);
        if(empty||room == null){


            setText("cozyRoom");
            ImageView cozyRoom = new ImageView(Objects.requireNonNull(getClass().getResource("/cz/vse/novf02/main/adventuraAssets/prostory/cozyRoom.png")).toExternalForm());
            cozyRoom.setFitHeight(32);
            cozyRoom.setFitWidth(32);
            setGraphic(cozyRoom);
        } else {

            System.out.println("Room Name: " + room.getRoomName()); // Debug log


                ImageView iv = new ImageView(getClass().getResource("/cz/vse/novf02/main/adventuraAssets/prostory/" + room.getRoomName() + ".png").toExternalForm());
                iv.setFitHeight(32);
                iv.setFitWidth(32);
                setGraphic(iv);
                setText(room.getRoomName());
            }
    }


}
*/
