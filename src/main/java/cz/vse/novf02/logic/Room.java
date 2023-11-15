package cz.vse.novf02.logic;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Trida Room - popisuje jednotlivé prostory (místnosti) hry
 *
 * Room reprezentuje jedno místo ve scénáři hry
 * Room má sousední prostory připojené přes východy
 * Pro každý východ si prostor ukládá odkaz na sousedící prostor
 *
 * @author Filip Nový
 * @version pro školní rok 2020/2021
 */

public class Room {
    private String roomName;
    private String description;
    private Set<Room> exits;
    private Map<String, Item> items = new HashMap<>();
    private String requiredItem;
    private GamePlan game;


    /**
     * @param roomName jméno místnosti
     * @param description popis vzhledu místnosti
     * @param requiredItem předmět potřebný ke vstoupení
     */
    public Room(String roomName, String description, String requiredItem) {
        this.roomName = roomName;
        this.description = description;
        this.requiredItem = requiredItem;
        exits = new HashSet<>();


    }


    public Room(String roomName, String description) {
        this(roomName, description, null);
    }

    /**
     * @param playerInventory kontroluje, jestli inventář obsahuje potřebný předmět
     * @return
     */
    public boolean hasRequiredItem(Inventory playerInventory) {
        if (requiredItem == null) return true;  // žádný předmět není potřeba
        return playerInventory.containsItem(requiredItem);
    }

    /**
     * @return zjistí potřebný předmět ke vstupu
     */
    public String getRequiredItem() {
        return requiredItem;
    }


    /**
     * Definuje východ z prostoru (sousední/vedlejší prostor). Vzhledem k tomu,
     * že je použit Set pro uložení východů, může být sousední prostor uveden
     * pouze jednou (tj. nelze mít dvoje dveře do stejné sousední místnosti).
     * Druhé zadání stejného prostoru tiše přepíše předchozí zadání (neobjeví se
     * žádné chybové hlášení). Lze zadat též cestu ze do sebe sama.
     *
     * @param nextRoom prostor, který sousedi s aktualnim prostorem.
     *
     */
    public void setExit(Room nextRoom) {
        exits.add(nextRoom);
    }


    /**
     * Metoda equals pro porovnání dvou prostorů. Překrývá se metoda equals ze
     * třídy Object. Dva prostory jsou shodné, pokud mají stejný název. Tato
     * metoda je důležitá z hlediska správného fungování seznamu východů (Set).
     *
     * Bližší popis metody equals je u třídy Object.
     *
     * @param o object, který se má porovnávat s aktuálním
     * @return hodnotu true, pokud má zadaný prostor stejný název, jinak false
     */
    @Override
    public boolean equals(Object o) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci
        if (this == o) {
            return true;
        }
        // porovnáváme jakého typu je parametr
        if (!(o instanceof Room)) {
            return false;    // pokud parametr není typu Prostor, vrátíme false
        }
        // přetypujeme parametr na typ Prostor
        Room second = (Room) o;

        //metoda equals třídy java.util.Objects porovná hodnoty obou názvů.
        //Vrátí true pro stejné názvy a i v případě, že jsou oba názvy null,
        //jinak vrátí false.

        return (Objects.equals(this.roomName, second.roomName));
    }


    /**
     * metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object
     */
    @Override
    public int hashCode() {
        int result = 3;
        int hashName = Objects.hashCode(this.roomName);
        result = 37 * result + hashName;
        return result;
    }


    /**
     * Vrací název prostoru (byl zadán při vytváření prostoru jako parametr
     * konstruktoru)
     *
     * @return název prostoru
     */
    public String getRoomName() {
        return roomName;
    }


    /**
     * Vrací "dlouhý" popis prostoru
     *
     * @return Dlouhý popis prostoru
     */
    public String longDescription(Inventory playerInventory) {
        return description + "\n"
                + exitDescription(playerInventory);
    }



    /**
     * Vrací textový řetězec, který popisuje sousední východy.
     *
     * @return Popis východů - názvů sousedních prostorů
     */
    private String exitDescription(Inventory playerInventory) {
        String returnedText = "Východy: ";
        for (Room nextRoom : exits) {
            if (nextRoom.hasRequiredItem(playerInventory)) {
                returnedText += "  " + nextRoom.getRoomName();
            }
        }
        return returnedText;
    }



    /**
     * vrati popis veci v prostoru
     * @return text popisu veci
     */
    public String itemDescription() {
        String returnedText = "\nV místnosti se nachází: ";
        if(items.size()>0) {
            for (Map.Entry<String, Item> item : items.entrySet()) {
                returnedText += item.getValue().getItemName() + ", ";
            }
            return returnedText.substring(0, returnedText.length()-2);
        }
        else {
            return "\nProstor je prázdný.";
        }
    }
    /**
     * Snaží se najít předmět v místnosti
     *
     * @param itemName jméno hledaného předmětu
     * @return vrátí předmět pokud byl nalezen, jinak vrací null
     */
    public Item getItem(String itemName) {
        return items.get(itemName); // This will return the Item if found or null if not found
    }



    /**
     * Vrací prostor, který sousedí s aktuálním prostorem a jehož název je zadán
     * jako parametr. Pokud prostor s udaným jménem nesousedí s aktuálním
     * prostorem, vrací se hodnota null.
     *
     * @param nextRoomName Jméno sousedního prostoru (východu)
     * @return Room, který se nachází za příslušným východem, nebo hodnota
     * null, pokud prostor zadaného jména není sousedem.
     */


    public Room returnNextRoom(String nextRoomName) {
        List<Room> futureRooms =
                exits.stream()
                        .filter(nextRoom -> nextRoom.getRoomName().equalsIgnoreCase(nextRoomName.trim()))
                        .collect(Collectors.toList());
        if (futureRooms.isEmpty()) {
            return null;
        } else {
            return futureRooms.get(0);
        }
    }

    /**
     * @param roomName jméno místnosti
     * @return východ
     */
    //pomocná metoda pro implementaci klíčů
    public Room getExitByName(String roomName) {
        return exits.stream()
                .filter(room -> room.getRoomName().equalsIgnoreCase(roomName.trim()))
                .findFirst().orElse(null);
    }


    /**
     * Vrací kolekci obsahující prostory, se kterými tento prostor sousedí.
     * Takto získaný seznam sousedních prostor nelze upravovat (přidávat,
     * odebírat východy) protože z hlediska správného návrhu je to plně
     * záležitostí třídy Prostor.
     *
     * @return Nemodifikovatelná kolekce prostorů (východů), se kterými tento
     * prostor sousedí.
     */
    public Collection<Room> getExits() {
        return Collections.unmodifiableCollection(exits);
    }

    /**
     * zjisti, jestli se v prostoru nachazi dana vec
     * @param name vec, kterou chceme najit
     * @return true pokud se nachazi, false pokud se nenachayi
     */
    public boolean containsItem(String name) {
        return items.containsKey(name);
    }


    /**
     * vrati vec z prostoru
     * @param name nazev veci, kterou chceme vratit
     * @return vracena vec
     */
    public Item returnItem(String name) {
        return items.get(name);
    }


    /**
     * vlozi vec do prostoru
     * @param item nazev vec, kterou chceme do prostoru vlozit
     * @return vlozena vec
     */
    public Item insertItem(Item item) {
        items.put(item.getItemName(),item);
        if (items.containsKey(item.getItemName())) {
            return item;
        }
        return null;
    }



    /**
     * odebere vec z prostoru
     * @param name nazev veci, kterou chceme odebrat
     * @return odebrana vec
     */
    public Item removeItem(String name) {
        return items.remove(name);
    }

    /**
     * @return vrací název místnosti (ne Hashcode)
     */
    @Override
    public String toString() {
        return this.roomName;
    }


    public Map<String,Item> getItems() {
    return this.items;}
}
