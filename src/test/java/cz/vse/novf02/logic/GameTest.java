package cz.vse.novf02.logic;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class GameTest {
    @Test
    void testVyhra() {
        // Step 1: Initialize your game
        Game game = new Game();
        GamePlan plan = new GamePlan();

        // Step 2: Simulate a series of actions leading to a win
        // For example, let's say picking up a 'crown' and reaching 'finalRoom' wins the game
        game.processCommand("sebrat stary_klic");

        game.processCommand("jdi vstupniMistnost");
        System.out.println("úspěšná změna místnosti do " + game.getGamePlan().getCurrentRoom());
        game.processCommand("jdi leveRozcesti");
        System.out.println("úspěšná změna místnosti do " + game.getGamePlan().getCurrentRoom());
        game.processCommand("jdi loveckySalon");
        System.out.println("úspěšná změna místnosti do " + game.getGamePlan().getCurrentRoom());
        game.processCommand("sebrat luk");
        game.processCommand("jdi leveRozcesti");
        System.out.println("úspěšná změna místnosti do " + game.getGamePlan().getCurrentRoom());
        game.processCommand("jdi hrobkaPrince");
        System.out.println("úspěšná změna místnosti do " + game.getGamePlan().getCurrentRoom());
        game.processCommand("sebrat excalibur");
        game.processCommand("jdi leveRozcesti");
        System.out.println("úspěšná změna místnosti do " + game.getGamePlan().getCurrentRoom());
        game.processCommand("jdi levyDungeon");
        game.processCommand("pouzij excalibur");
        game.processCommand("sebrat rezavy_klic");
        System.out.println("Inventář obsahuje " + game.getGamePlan().getInventory().showInventory());
        game.processCommand("jdi levyMost");
        System.out.println("úspěšná změna místnosti do " + game.getGamePlan().getCurrentRoom());
        game.processCommand("pouzij luk");
        game.processCommand("sebrat kralovska_pecet");
        game.processCommand("jdi stredKatakomb");
        System.out.println("úspěšná změna místnosti do " + game.getGamePlan().getCurrentRoom());
        game.processCommand("sebrat klic_zpet");
        game.processCommand("jdi kralovskaKrypta");
        System.out.println("úspěšná změna místnosti do " + game.getGamePlan().getCurrentRoom());
        game.processCommand("pouzij kralovska_pecet");
        game.processCommand("sebrat koruna");
        game.processCommand("jdi pokladnice");
        System.out.println("úspěšná změna místnosti do " + game.getGamePlan().getCurrentRoom());
        game.processCommand("pouzij rezavy_klic");
        game.processCommand("sebrat prastara_mince");
        System.out.println("Inventář obsahuje " + game.getGamePlan().getInventory().showInventory());
        game.processCommand("jdi kralovskaKrypta");
        game.processCommand("jdi vychod");
        System.out.println("úspěšná změna místnosti do " + game.getGamePlan().getCurrentRoom());


        // Výhra?
        assertTrue(game.getGamePlan().getCurrentRoom().toString().equals("vychod"),"Je hráč ve výherní místnosti?");
        System.out.println("Hráč je nyní v " + game.getGamePlan().getCurrentRoom());
        assertTrue(game.getGamePlan().getInventory().containsItem("koruna"),"Má hráč všechny potřebné předměty?");
        assertTrue(game.gameEnd(), "Hra by nyní měla skončit");
    }
}