package cz.vse.novf02.logic;
import java.util.Set;

/**
 *  Class GamePlan - třída představující mapu a stav adventury.
 *
 *  Tato třída inicializuje prvky ze kterých se hra skládá:
 *  vytváří všechny prostory,
 *  propojuje je vzájemně pomocí východů
 *  a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 *
 *@author     Filip Nový
 *@version    pro školní rok 2020/2021
 */

public class GamePlan {


    //Současná místnost
    private Room currentRoom;
    // pro rozšíření scopu, abychom mohli použít pro metodu pro restart
    private Room vstupDoKatakomb;
    //Evidence předchozí místnosti pro návrat zpět
    private Room previousRoom = null;
    public Room getPreviousRoom() {
        return this.previousRoom;
    }

    public void setPreviousRoom(Room previousRoom) {
        this.previousRoom = previousRoom;
    }

    private Inventory inventory = new Inventory();

    private Game game;

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }



    /**
     *  Konstruktor který vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví halu.
     */
    public GamePlan() {
        createGame();
    }
    private void createGame(){
       // vytvářejí se jednotlivé Prostory
        vstupDoKatakomb = new Room("vstupDoKatakomb","Vstup do podzemí");

        Room vstupniMistnost = new Room("vstupniMistnost", "Dveře se otevřely a ty se nacházíš ve vstupní síni. Je tu zima a tma.","stary_klic");
             Room leveRozcesti1 = new Room("leveRozcesti1","Šel jsi levou chodbou, cesta za tebou se ale bohužel zasypala obřími kameny. To bylo těsné! Můžeš jít do místnosti vpravo nebo pokračovat chodbou dál.");
             Room leveRozcesti2 = new Room("leveRozcesti2","Zde chodba končí. Po pravé straně je hrobka prince Philipa, zatímco na konci chodby je hrobka významných rytířů.");
             Room hrobkaPrince = new Room("hrobkaPrince","Vešel jsi do hrobky prince Philipa.\n Oficiálně se dožil 99 let, ale šuškalo se o něm, že je ve skutečnosti 1000 let starý upír.");
             Room hrobkaRytiru = new Room("hrobkaRytiru", "Vstoupil jsi do komnaty, kde jsou pohřbeni nejvýznamnější členové královské stráže. Jeden sarkofág je pootevřený a v něm vidíš ležícího rytíře s pořádným palcátem v ruce. Že by Jan Žižka?");
             Room levyDungeon1 = new Room("levyDungeon1", "Při vstupu do místnosti slyšíš posouvající se kamenné víko. Je to oživlá mumie! Holýma rukama ji rozhodně nezabiješ, budeš si muset obstarat nějaký meč.","palcat");
             Room levyDungeon2 = new Room("levyDungeon2","Před tebou stojí obří oživlý strážce v těžké zbroji. Abys vůbec měl šanci prorazit jeho brnění, budeš potřebovat pořádnou zbraň","excalibur");
             Room stredKatakomb = new Room("stredKatakomb","Nacházíš se v samém srdci katakomb. Na východě vidíš jakési světlo, na jihu dveře, které musí vést do vstupní místnosti a na severu vstup tak honosný, že to může být jen krypta krále Šalamouna.\n" +
                      "Na kamenných dveřích vidíš jakýsi kruh. Po bližším ohledání vidíš, že se tam musí vložit královská pečeť.\n");
             Room prostredniChodba = new Room("prostredniChodba","Našel jsi cestu zpět. Hurá!");
             Room praveRozcesti1 = new Room("praveRozcesti1","Šel jsi pravou chodbou, cesta za tebou se ale bohužel zasypala obřími kameny. To bylo těsné! Můžeš jít do místnosti vlevo nebo pokračovat chodbou dál.\n");
             Room praveRozcesti2 = new Room("praveRozcesti2","Zde chodba končí. Po levé straně je lovecký salon, zatímco na konci chodby slyšíš kapající vodu.");
             Room studanka = new Room("studanka", "Před sebou vidíš, jak pramen vvvěrá do studánky.\n Osvěžíš se a hned je ti lépe. Zároveň si všimneš, že se v záři tvé pochodně něco na dně třpytí...");
             Room loveckySalon = new Room("loveckySalon", "Vstoupil jsi do místnosti plné loveckého vybavení.\n Dle dobových záznamů by tu mohl být luk Robina Hooda.");
             Room pravyDungeon1 = new Room("pravyDungeon1", "Před tebou stojí vetchý oživlý kouzelník.\n Jednej rychle, nebo tě promění v žábu!","kyblik");
             Room pravyDungeon2 = new Room("pravyDungeon2", "Je tady dost horko, stojíš na mostě nad žhavou lávou.\n Zdá se ale, že to ohnivému elementálovi před tebou vyhovuje.","luk");
             Room kralovskaKrypta = new Room("kralovskaKrypta","Konečně jsi u cíle. Před tebou sedí na trůnu král Šalamoun – tedy to, co z něj zbylo. Na čele mu sedí kouzelná koruna, která svou září osvětluje místnost.","kralovska_pecet");


        /**
         * tvorba východů
         */
        // vstup
        vstupDoKatakomb.setExit(vstupniMistnost);
        vstupniMistnost.setExit(leveRozcesti1);
        vstupniMistnost.setExit(praveRozcesti1);
        //levá část
        leveRozcesti1.setExit(leveRozcesti2);
        leveRozcesti1.setExit(levyDungeon1);
        leveRozcesti2.setExit(hrobkaPrince);
        leveRozcesti2.setExit(hrobkaRytiru);
        leveRozcesti2.setExit(leveRozcesti1);
        hrobkaPrince.setExit(leveRozcesti2);
        hrobkaRytiru.setExit(leveRozcesti2);
        levyDungeon1.setExit(leveRozcesti1);
        levyDungeon1.setExit(levyDungeon2);
        levyDungeon2.setExit(levyDungeon1);
        levyDungeon2.setExit(stredKatakomb);
        // pravá část
        praveRozcesti1.setExit(praveRozcesti2);
        praveRozcesti1.setExit(pravyDungeon1);
        praveRozcesti2.setExit(studanka);
        praveRozcesti2.setExit(loveckySalon);
        praveRozcesti2.setExit(praveRozcesti1);
        studanka.setExit(praveRozcesti2);
        loveckySalon.setExit(leveRozcesti2);
        pravyDungeon1.setExit(praveRozcesti1);
        pravyDungeon1.setExit(pravyDungeon2);
        pravyDungeon2.setExit(pravyDungeon1);
        pravyDungeon2.setExit(stredKatakomb);
        //střed
        stredKatakomb.setExit(levyDungeon2);
        stredKatakomb.setExit(pravyDungeon2);
        stredKatakomb.setExit(prostredniChodba);
        stredKatakomb.setExit(kralovskaKrypta);
        kralovskaKrypta.setExit(stredKatakomb);
        prostredniChodba.setExit(vstupniMistnost);
        prostredniChodba.setExit(stredKatakomb);



/**
 * nastavení počáteční mistnosti
 */
        this.currentRoom = vstupDoKatakomb;





        /**
         * předměty, které jdou a nejdou sebrat
         */
        Item kralovska_pecet = new Item("kralovska_pecet",true,null);
        Item klicZpet = new Item("klicZpet", true,null);
        Item levaTruhla = new Item("levaTruhla1",false,null);
        Item pravaTruhla = new Item("pravaTruhla1",false,null);
        Item palcat = new Item("palcat",true,"Bum!");
        Item excalibur = new Item("excalibur",true,"Švihneš mečem.");
        Item luk = new Item("luk",true,"Natáhneš tětivu luku a vystřelíš.");
        Item kyblik = new Item("kyblik",true,"Použil jsi kyblík.");
        Item studna = new Item("studna",false,null);
        Item socha = new Item("socha",false,null);
        Item sloup = new Item("sloup",false,null);
        Item trun = new Item("trun",false,null);
        Item kral = new Item("kral",false,null);
        Item kostlivec = new Item("kostlivec",false,"Rytíř opět usl.");
        Item elemental = new Item("elemental",false,"Vychrstnul jsi kyblík směrem na ohnivé stvoření. Elementál zasyčel a zhasl!");
        Item carodej = new Item("carodej",false,"Čaroději jsi prostřelil lebku");
        Item mumie = new Item("oživlá mumie",false,"Mumii jsi rozsekl vejpůl");
        Item stary_klic = new Item("stary_klic",true,null);
        Item koruna = new Item("koruna",true,"Cítíš, jak tvoje mysl byla naplněna nekonečnou moudrostí.");
        Item stara_mince = new Item("stara_mince",true,"Vypadá cenně. venku se určitě bude dát prodat za spoustu peněz");
        Item zlata_lampa = new Item("zlata_lampa",true,"Vypadá kouzelně. Určitě má nesmírnou cenu.");
        // Nastavení interakcí
        excalibur.setInteraction("mumie","Mumie se rozpadla v prach.");
        palcat.setInteraction("kostlivec","Všimneš si, že něco třímal v ruce.");
        kyblik.setInteraction("elemental","V popelu se něco blýská.");
        kyblik.setInteraction("studna","Kyblík je nyní plný vody.");
        luk.setInteraction("carodej","Jednalo se králova bývalého rádce. Možná měl u sebe něco užitečného.");
        levaTruhla.setInteraction("stary_klic","Víko se pohlo a odhalilo poklad!");
        pravaTruhla.setInteraction("stary_klic","Víko se pohlo a odhalilo poklad!");
        /**
         * Vygenerovaný loot
         */
        carodej.setGeneratedItem(kralovska_pecet);
        kostlivec.setGeneratedItem(kralovska_pecet);
        levaTruhla.setGeneratedItem(stara_mince);
        pravaTruhla.setGeneratedItem(zlata_lampa);
        //později přídám loot z elementála
        /**
         * vkládání věcí do prostoru
         */
        vstupDoKatakomb.insertItem(stary_klic);
        levyDungeon1.insertItem(mumie);
        levyDungeon2.insertItem(kostlivec);
        hrobkaPrince.insertItem(excalibur);
        //hrobkaPrince.insertItem(kralovska_pecet);
        hrobkaRytiru.insertItem(levaTruhla);
        hrobkaRytiru.insertItem(palcat);
        pravyDungeon1.insertItem(carodej);
        pravyDungeon2.insertItem(elemental);
        loveckySalon.insertItem(luk);
        studanka.insertItem(kyblik);
        studanka.insertItem(studna);
        studanka.insertItem(kralovska_pecet);
        studanka.insertItem(pravaTruhla);
        stredKatakomb.insertItem(socha);
        stredKatakomb.insertItem(sloup);
        stredKatakomb.insertItem(klicZpet);
        kralovskaKrypta.insertItem(trun);
        kralovskaKrypta.insertItem(kral);
        kralovskaKrypta.insertItem(koruna);




    }


    /**
     *  Metoda vrací odkaz na aktuální prostor, ve ktetém se hráč právě nachází.
     *
     *@return     aktuální prostor
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }


    /**
     *  Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory
     *
     *@param  room nový aktuální prostor
     */
    public void setCurrentRoom(Room room) {
        currentRoom = room;}

    /**
     * vrati inventar
     * @return
     */
    public Inventory getInventory() {
        return inventory;
    }

    // Restartuje plán
    public void reset() {
        this.currentRoom = vstupDoKatakomb;
        this.inventory = new Inventory();

    }


}
