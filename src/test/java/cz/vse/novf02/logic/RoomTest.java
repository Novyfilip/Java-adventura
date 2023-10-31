package cz.vse.novf02.logic;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import  org.junit.jupiter.api.BeforeEach;
import  org.junit.jupiter.api.AfterEach;

public class RoomTest {


    /***************************************************************************
     * Úklid po testu - tato metoda se spustí po vykonání každé testovací metody.
     */
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testExit() {
        Room room1 = new Room("vstupDoKatakomb","vstup");
        Room room2 = new Room("vstupniMistnost","zde si musíš vybrat cesti");
        room1.setExit(room2);
        room2.setExit(room1);
        assertEquals(room1,room2.returnNextRoom("vstupDoKatakomb"));
        assertEquals(room2,room1.returnNextRoom("vstupniMistnost"));
    }
}
