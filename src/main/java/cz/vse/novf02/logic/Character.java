package cz.vse.novf02.logic;

/**
 * Class Character - popisuje obecne chovani vsech postav
 *  @author Filip Nový
 *  @version pro školní rok 2020/2021
 */

public class Character {

    /**
     * atributy postav
     */
    private String name;
    private String phrase;



    /**
     * konstruktor
     */
    public Character(String name, String phrase) {
        this.name = name;
        this.phrase = phrase;
    }


    /**
     * vrací jméno postavy
     * @return text jména postavy
     */
    public String getName() {
        return this.name;
    }


    /**
     * vrati text postavy
     * @return co postava řekne
     */
    public String getPhrase() {
        return phrase;
    }
}
