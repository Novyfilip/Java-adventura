package cz.vse.novf02.logic;
import cz.vse.novf02.main.Pozorovatel;

import java.util.HashSet;
import java.util.Set;

/**
 * Třída CommandGo - implementuje interface ICommand
 * @author Filip Nový
 * @version pro školní rok 2020/2021
 */

public class CommandGo implements ICommand{

    public static final String title = "jdi";
    private GamePlan plan;


    /**
     *  Konstruktor třídy
     *
     *  @param plan herní plán, ve kterém se bude ve hře "pohybovat"
     */
    public CommandGo(GamePlan plan) {
        this.plan = plan;
    }

    /**
     *  Provádí příkaz "jdi". Zkouší se vyjít do zadaného prostoru. Pokud prostor
     *  existuje, vstoupí se do nového prostoru. Pokud zadaný sousední prostor
     *  (exit) není, vypíše se chybové hlášení.
     *
     * @param parametry - jako parametr obsahuje jméno prostoru (východ),
     *                   do kterého se má jít.
     * @return zpráva, kterou vypíše hra hráči
     */
    public String resolveCommand(String... parametry) {
        if (parametry.length == 0) {
            return  plan.getCurrentRoom().longDescription(plan.getInventory());
        }

        String direction = parametry[0];

        // Získá inventář
        Inventory playerInventory = plan.getInventory();
        // Current Room
        Room currentRoom = plan.getCurrentRoom();

        // Když jde hráč zpět
        if ("zpet".equalsIgnoreCase(direction)) {
            if (plan.getPreviousRoom() != null) {
                plan.setCurrentRoom(plan.getPreviousRoom());
                return plan.getCurrentRoom().longDescription(playerInventory);
            } else {
                return "Nemůžeš jít zpět, protože jsi ještě nenavštívil žádnou místnost.";
            }
        }

        // Přesun do dalši místnosti
        Room nextPotentialRoom = currentRoom.getExitByName(direction);

        if (nextPotentialRoom == null) {
            return "Sem se nedostaneš.";
        }

        if (!nextPotentialRoom.hasRequiredItem(playerInventory)) {
            return "Potřebuješ " + nextPotentialRoom.getRequiredItem() + ".";
        }

        // Uložení místnosti jako předchozí
        plan.setPreviousRoom(plan.getCurrentRoom());
        plan.setCurrentRoom(nextPotentialRoom);
        if (plan.getCurrentRoom().getRoomName().equals("studanka") ||
                plan.getCurrentRoom().getRoomName().equals("levyMost")) {
            Sounds.playSound("voda.mp3");
        }else{ Sounds.playSound("jdi.mp3");}
        return nextPotentialRoom.longDescription(playerInventory);
    }


    private boolean playerHasRequiredItem(String requiredItem) {
        Inventory playerInventory = plan.getInventory();
        return playerInventory.containsItem(requiredItem);
    }


    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  @return nazev prikazu
     */
    public String getTitle() {
        return title;
    }
}
