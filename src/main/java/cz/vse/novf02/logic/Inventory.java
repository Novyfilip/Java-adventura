package cz.vse.novf02.logic;
import cz.vse.novf02.main.Pozorovatel;
import cz.vse.novf02.main.PredmetPozorovani;
import cz.vse.novf02.main.ZmenaHry;

import java.util.*;

/**
 * Class Inventory - inventar, kde se uchovavani sebrane predmety
 * muzememe si ho kdykoliv zobrazit prikazem inventory
 * @author Filip Nový
 * @version pro školní rok 2020/2021
 */

public class Inventory implements PredmetPozorovani {
    private Map<String, Item> items = new HashMap<>();
    private static final int capacity = 12;
    private Map<ZmenaHry, List<Pozorovatel>> observers = new HashMap<>();

    /** Zaznamenává změny v herním plánu
     * @param zmenaHry změna v herním plánu
     * @param pozorovatel pozorovatel
     */
    @Override
    public void registruj(ZmenaHry zmenaHry, Pozorovatel pozorovatel) {
        observers.computeIfAbsent(zmenaHry, k -> new ArrayList<>()).add(pozorovatel);
    }

    /** Upozorňuje pozorovatele pro aktualizování zobrazeného obsahu.
     * @param zmenaHry změna hry
     */
    private void notifyObservers(ZmenaHry zmenaHry) {
        if (observers.containsKey(zmenaHry)) {
            for (Pozorovatel pozorovatel : observers.get(zmenaHry)) {
                pozorovatel.aktualizuj();
            }
        }
    }


    /**
     * zkontroluje, jestli inventar neni plny a vlozi vec do inventare
     * @param item vec kterou vkladame do inventare
     * @return vraci vec, ktera byla vlozena, null pokud vlozena nebyla
     */
    public Item insertItem(Item item) {
        if(isEmpty()) {
            items.put(item.getItemName(), item); //vloží klíč a hodnotu do mapy
            if (items.containsKey(item.getItemName())) {
                notifyObservers(ZmenaHry.INVENTORY_CHANGE);
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


    /**
     * odebere vec z inventare
     * @param name nazev veci, kterou chceme odebrat
     * @return vrati vec, kterou jsme odebrali
     */
    public Item removeItem(String name) {
        Item item = items.get(name);
        items.remove(name);
        notifyObservers(ZmenaHry.INVENTORY_CHANGE);
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
     * vrati vec z inventare
     * @param itemName nazev veci, kterou chceme vratit
     * @return vracena vec
     */
    public Item getItem(String itemName) {
        return items.get(itemName);
    }

    /**
     * @return celý obsah inventáře
     */
    public Collection<Item> getAllItems() {
        return items.values();
    }




}
