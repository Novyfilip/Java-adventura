package cz.vse.novf02.TextUI;
import cz.vse.novf02.logic.IGame;

import java.io.*;
import java.util.Scanner;

/**
 *  Class TextUI
 *
 *  Toto je uživatelského rozhraní aplikace Tajemná krypta
 *  Tato třída vytváří instanci třídy Game, která představuje logiku aplikace.
 *  Čte vstup zadaný uživatelem a předává tento řetězec logice a vypisuje odpověď logiky na konzoli.
 *
 *@author     Filip Nový
 *@version    pro školní rok 2020/2021
 */

public class TextUI {
    private IGame game;

    /**
     *  Vytváří hru.
     */
    public TextUI(IGame game) {
        this.game = game;
    }


    /**
     *  Hlavní metoda hry. Vypíše úvodní text a pak opakuje čtení a zpracování
     *  příkazu od hráče do konce hry (dokud metoda gameEnd() z logiky nevrátí
     *  hodnotu true). Nakonec vypíše text epilogu.
     *  Součastí play je vypisování do textového souboru
     */
    public void play() throws FileNotFoundException {
        File file = new File("output.txt");
        PrintWriter out = new PrintWriter(file);

        System.out.println(game.returnStart());
        out.println(game.returnStart());

        // základní cyklus programu - opakovaně se čtou příkazy a poté
        // se provádějí do konce hry.

        while (!game.gameEnd()) {

            String row = readString();
            String result = game.processCommand(row.toLowerCase());
            System.out.println(result);

            out.println(row);
            out.println(result);
        }

        System.out.println(game.returnEpilog());
        out.println(game.returnEpilog());
        out.close();
    }


    /**
     *  Metoda přečte příkaz z příkazového řádku
     *@return    Vrací přečtený příkaz jako instanci třídy String
     */
    private String readString() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        return scanner.nextLine();


    }


}
