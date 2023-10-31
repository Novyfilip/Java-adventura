package cz.vse.novf02.logic;
import cz.vse.novf02.logic.Inventory;
import cz.vse.novf02.logic.Item;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import  org.junit.jupiter.api.BeforeEach;
import  org.junit.jupiter.api.AfterEach;

public class InventoryTest {

    Inventory inventory = new Inventory();


    /**
     * vložení itemu
     */


    @BeforeEach
    public void setUp() {
        inventory.insertItem(new Item("pokemon2",true));
    }

    /***************************************************************************
     * Úklid po testu - tato metoda se spustí po vykonání každé testovací metody.
     */
    @AfterEach
    public void tearDown() {}


    /**
     * test obsahu inventáře
     */
    @Test
    public void testInventory() {
        assertTrue(inventory.containsItem("palcat"));
    }

    /**
     * test nositelnosti věci
     */

}
