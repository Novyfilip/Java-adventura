package cz.vse.novf02.logic;

import cz.vse.novf02.main.PredmetPozorovani;

/**
 *  Rozhraní které musí implementovat hra, je na ně navázáno uživatelské rozhraní
 *
 *@author     Filip Nový
 *@version    pro školní rok 2020/2021
 */

public interface IGame extends PredmetPozorovani {


    /**
     *  Vrátí úvodní zprávu pro hráče.
     *  @return  vrací se řetězec, který se má vypsat na obrazovku
     */
    public String returnStart();


    /**
     *  Vrátí závěrečnou zprávu pro hráče.
     *  @return  vrací se řetězec, který se má vypsat na obrazovku
     */
    public String returnEpilog();


    /**
     * Vrací informaci o tom, zda hra již skončila, je jedno zda výhrou, prohrou nebo příkazem konec.
     * @return   vrací true, pokud hra skončila
     */
    public boolean gameEnd();


    /**
     *  Metoda zpracuje řetězec uvedený jako parametr, rozdělí ho na slovo příkazu a další parametry.
     *  Pak otestuje zda příkaz je klíčovým slovem  např. go.
     *  Pokud ano spustí samotné provádění příkazu.
     *@param  inputRow  text, který zadal uživatel jako příkaz do hry.
     *@return          vrací se řetězec, který se má vypsat na obrazovku
     */
    public String processCommand(String inputRow);


    GamePlan getGamePlan();
}
