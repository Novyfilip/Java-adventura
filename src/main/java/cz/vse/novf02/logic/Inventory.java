package cz.vse.novf02.logic;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
/**
 * Class Inventory - inventar, kde se uchovavani sebrane predmety
 * muzememe si ho kdykoliv zobrazit prikazem inventory
 * @author Filip Nový
 * @version pro školní rok 2020/2021
 */

public class Inventory {
    private Map<String, Item> items = new HashMap<>();
    private static final int capacity = 5;


    /**
     * zkontroluje, jestli inventar neni plny a vlozi vec do inventare
     * @param item vec kterou vkladame do inventare
     * @return vraci vec, ktera byla vlozena, null pokud vlozena nebyla
     */
    public Item insertItem(Item item) {
        if(isEmpty()) {
            items.put(item.getItemName(), item); //vloží klíč a hodnotu do mapy
            if (items.containsKey(item.getItemName())) {
                return item;
            }
        }
        return null;
    }


    /**
     * zobrazi obsah inventare
     * @return obsah inventare
     */
    public String showInventory() {

        String returnText = "Tvůj inventář obsahuje: \n";
        if(items.size()>0) {
            for (Map.Entry<String, Item> item : items.entrySet()) {
                returnText += item.getKey() + ", ";
            }
            returnText = returnText.substring(0, returnText.length()-2);
        }
        else {
            returnText += "Tvůj inventář je zatím prázdný.";
        }

        return returnText;
    }


    /**
     * zjisti, jestli se v inventari nachazi dana vec
     * @param name nazev veci, kterou chceme zjistit
     * @return vrati true, pokud vec v inventari je, false pokud neni
     */
    public Boolean containsItem(String name) {
        for (Item item : items.values()) {
            if (item.getItemName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    public boolean containsAll(Set<String> requiredItems) {
        return items.keySet().containsAll(requiredItems);
    }


    /**
     * vrati vec z inventare
     * @param name nazev veci, kterou chceme vratit
     * @return vracena vec
     */
    public Item returnItem(String name) {
        return items.get(name);
    }


    /**
     * odebere vec z inventare
     * @param name nazev veci, kterou chceme odebrat
     * @return vrati vec, kterou jsme odebrali
     */
    public Item removeItem(String name) {
        Item item = items.get(name);
        items.remove(name);
        return item;
    }


    /**
     * zjisti, jestli je inventar plny
     * @return vraci true, pokud neni a false pokud je plny
     */
    public Boolean isEmpty() {
        return (items.size() <= capacity);
    }
    public Boolean isFull(){return(items.size() == capacity);}
    /**
     * Retrieves the item with the given name from the inventory.
     * @param itemName the name of the item
     * @return the item if found, null otherwise
     */
    public Item getItem(String itemName) {
        return items.get(itemName); // This will return null if the item doesn't exist
    }



}
