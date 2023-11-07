package cz.vse.novf02.logic;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Arrays;


/**
 *  Class GamePlan - třída představující mapu a stav adventury.
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
    private Item stary_klic;
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
        vstupDoKatakomb = new Room("vstupDoKatakomb","Vstup do podzemí"+"\nVýchody: vstupniMistnost");

        Room vstupniMistnost = new Room("vstupniMistnost", "Dveře se otevřely a ty se nacházíš ve vstupní síni. Je tu zima a tma.","stary_klic");
             //Room levaChodba = new Room("levaChodba","Šel jsi levou chodbou, cesta za tebou se ale bohužel zasypala obřími kameny. To bylo těsné! Můžeš jít do místnosti vpravo nebo pokračovat chodbou dál.");
             Room leveRozcesti = new Room("leveRozcesti","Šel jsi levou chodbou, cesta za tebou se ale bohužel zasypala obřími kameny. To bylo těsné!\n Zde chodba končí. Po pravé straně je hrobka prince Philipa, zatímco na konci chodby je hrobka významných rytířů.");
             Room hrobkaPrince = new Room("hrobkaPrince","Vešel jsi do hrobky prince Philipa.\n Oficiálně se dožil 99 let, ale šuškalo se o něm, že je ve skutečnosti 1000 let starý upír.");
             Room hrobkaRytiru = new Room("hrobkaRytiru", "Vstoupil jsi do komnaty, kde jsou pohřbeni nejvýznamnější členové královské stráže. Jeden sarkofág je pootevřený a v něm vidíš ležícího rytíře s pořádným palcátem v ruce. Že by Jan Žižka?");
             Room levyDungeon = new Room("levyDungeon", "Při vstupu do místnosti slyšíš posouvající se kamenné víko. Je to oživlá mumie. Bojuj nebo uteč!");
             Room levyMost = new Room("levyMost","Před tebou stojí obří oživlý strážce v těžké zbroji. Abys vůbec měl šanci prorazit jeho brnění, budeš potřebovat pořádnou zbraň","rezavy_klic");
             Room stredKatakomb = new Room("stredKatakomb","Nacházíš se v samém srdci katakomb. Na východě vidíš jakési světlo, na jihu dveře, které musí vést do vstupní místnosti a na severu vstup tak honosný, že to může být jen krypta krále Šalamouna.\n" +
                      "Na kamenných dveřích vidíš jakýsi kruh. Po bližším ohledání vidíš, že se tam musí vložit královská pečeť.\n");
             Room vychod = new Room("vychod","Našel jsi cestu zpět. Hurá!","klic_zpet");
             //Room pravaChodba = new Room("pravaChodba","Šel jsi pravou chodbou, cesta za tebou se ale bohužel zasypala obřími kameny. To bylo těsné! Můžeš jít do místnosti vlevo nebo pokračovat chodbou dál.\n");
             Room praveRozcesti = new Room("praveRozcesti","Šel jsi pravou chodbou, cesta za tebou se ale bohužel zasypala obřími kameny. To bylo těsné!\n Zde chodba končí. Po levé straně je lovecký salon, zatímco na konci chodby slyšíš kapající vodu.");
             Room studanka = new Room("studanka", "Před sebou vidíš, jak pramen vvvěrá do studánky.\n Osvěžíš se a hned je ti lépe.\n Zdá se, že tu byla vytvořena pro případ požáru.");
             Room loveckySalon = new Room("loveckySalon", "Vstoupil jsi do místnosti plné loveckého vybavení.\n Dle dobových záznamů by tu mohl být luk Robina Hooda.");
             Room pravyDungeon = new Room("pravyDungeon", "Před tebou stojí vetchý oživlý kouzelník.\n Jednej rychle, nebo tě promění v žábu!");
             Room pravyMost = new Room("pravyMost", "Je tady dost horko, stojíš na mostě nad žhavou lávou.\n Zdá se ale, že to ohnivému elementálovi před tebou vyhovuje.");
             Room kralovskaKrypta = new Room("kralovskaKrypta","Konečně jsi u cíle. Před tebou leží  zapečetěný sarkofág.","kralovska_pecet");
             Room pokladnice = new Room ("pokladnice","Objevil(a) jsi tajnou pokladnici krále Šalamouna.","kralovska_pecet");


        /**
         * tvorba východů
         */
        // Mapa východů pro každou místnost
        Map<Room, Room[]> exitsMap = new HashMap<>();

        // Naplnění mapy místnostmi a jejich východy
        exitsMap.put(vstupDoKatakomb, new Room[]{vstupniMistnost});
        exitsMap.put(vstupniMistnost, new Room[]{leveRozcesti, praveRozcesti});
        //exitsMap.put(levaChodba, new Room[]{leveRozcesti, levyDungeon});
        exitsMap.put(leveRozcesti, new Room[]{hrobkaPrince, hrobkaRytiru, levyDungeon, vstupniMistnost,pokladnice});
        exitsMap.put(hrobkaPrince, new Room[]{leveRozcesti});
        exitsMap.put(hrobkaRytiru, new Room[]{leveRozcesti});
        exitsMap.put(levyDungeon, new Room[]{leveRozcesti, levyMost});
        exitsMap.put(levyMost, new Room[]{levyDungeon, stredKatakomb});
        //exitsMap.put(pravaChodba, new Room[]{praveRozcesti, pravyDungeon});
        exitsMap.put(praveRozcesti, new Room[]{studanka, loveckySalon, pravyDungeon, vstupniMistnost, pokladnice});
        exitsMap.put(studanka, new Room[]{praveRozcesti});
        exitsMap.put(loveckySalon, new Room[]{praveRozcesti});
        exitsMap.put(pravyDungeon, new Room[]{praveRozcesti, pravyMost});
        exitsMap.put(pravyMost, new Room[]{pravyDungeon, stredKatakomb});
        exitsMap.put(stredKatakomb, new Room[]{levyMost, pravyMost, kralovskaKrypta});
        exitsMap.put(kralovskaKrypta, new Room[]{stredKatakomb, pokladnice});
        exitsMap.put(vychod, new Room[]{vstupDoKatakomb, kralovskaKrypta});
        exitsMap.put(pokladnice, new Room[]{kralovskaKrypta, stredKatakomb});

        // Iterace skrze mapu a nastavení východů pro každou místnost
        exitsMap.forEach((room, exitRooms) -> {
            for (Room exitRoom : exitRooms) {
                room.setExit(exitRoom);
            }
        });



/**
 * nastavení počáteční mistnosti
 */
        this.currentRoom = vstupDoKatakomb;





        /**
         * předměty, které jdou a nejdou sebrat
         */
        Item kralovska_pecet = new Item("kralovska_pecet",true,null);
        Item klic_zpet = new Item("klic_zpet", true,null);
        Item truhla = new Item("truhla",false,"Něco jsi našel:\n");
        Item hrouda_zlata = new Item("hrouda_zlata",false,"Něco jsi našel:\n");
        Item palcat = new Item("palcat",true,"Bum!");
        Item excalibur = new Item("excalibur",true,"Švihneš mečem.");
        Item luk = new Item("luk",true,"Natáhneš tětivu luku a vystřelíš.");
        Item kyblik = new Item("kyblik",true,"Kyblík je nyní plný vody.");
        Item studna = new Item("studna",false,null);
        Item socha = new Item("socha",false,null);
        Item sloup = new Item("sloup",false,null);
        Item trun = new Item("trun",false,null);
        Item sarkofag = new Item("sarkofag",false,"\n Uvnitř leží pohřbený král Šalamoun.");
        Item strazce = new Item("strazce",false,"Rytíř opět usl.");
        Item elemental = new Item("elemental",false,"");
        Item carodej = new Item("carodej",false,"Čaroději jsi prostřelil lebku");
        Item mumie = new Item("mumie",false,"Mumii jsi rozsekl vejpůl");
        Item stary_klic = new Item("stary_klic",true,"Zámek se ti podařilo odemknout\n");
        Item koruna = new Item("koruna",true,"Na čele krále sedí kouzelná koruna, která svou září osvětluje místnost.");
        Item stara_mince = new Item("stara_mince",true,"Vypadá cenně. venku se určitě bude dát prodat za spoustu peněz");
        Item zlata_lampa = new Item("zlata_lampa",true,"Vypadá kouzelně. Určitě má nesmírnou cenu.");
        Item magicky_klic = new Item("magicky_klic",true,"Zdá se, že tohle bude užitečné");
        Item rezavy_klic = new Item("rezavy_klic",true,"Zdá se, že tohle je k ničemu");
        Item plny_kyblik = new Item("plny_kyblik",true,"Uhasí jakýkoli požár!" );
        Item moudrost =  new Item("moudrost",false,"V koruně se probudil králův  duch.");
        // Nastavení interakcí
        excalibur.setInteraction("mumie","Mumie se rozpadla v prach.");
        palcat.setInteraction("strazce","Všimneš si, že měl obří prsten na ruce.");
        plny_kyblik.setInteraction("elemental","V popelu se něco blýská.");
        kyblik.setInteraction("studna","kyblík jsi naplnil vodou a položil ho na zem.");
        luk.setInteraction("carodej","Jednalo se králova bývalého rádce. Možná měl u sebe něco užitečného.");
        magicky_klic.setInteraction("truhla","Víko se pohlo a odhalilo poklad!");
        rezavy_klic.setInteraction("hrouda_zlata","Podařilo se to překonat magickou bariéru.");
        koruna.setInteraction("trun","Cítíš, jak tvoje mysl byla naplněna nekonečnou moudrostí.");
        kralovska_pecet.setInteraction("sarkofag","S obtížemi jsi otevřel(a) víko sarkofágu.");
        /**
         * Vygenerovaný loot
         */
        //setAsLoot slouží k přehrání interactionMessage
        elemental.setGeneratedItem(kralovska_pecet);
        strazce.setGeneratedItem(kralovska_pecet);
        mumie.setGeneratedItem(rezavy_klic);
        carodej.setGeneratedItem(magicky_klic);
        trun.setGeneratedItem(moudrost);
        kralovska_pecet.setAsLoot(true);
        truhla.setGeneratedItem(stara_mince);
        stara_mince.setAsLoot(true);
        hrouda_zlata.setGeneratedItem(zlata_lampa);
        zlata_lampa.setAsLoot(true);
        studna.setGeneratedItem(plny_kyblik);
        plny_kyblik.setAsLoot(true);
        sarkofag.setGeneratedItem(koruna);
        koruna.setAsLoot(true);
        moudrost.setAsLoot(true);



        /**
         * vkládání věcí do prostoru
         */
        // Vytvoření mapy pro přiřazení položek do pokojů
        Map<Room, List<Item>> roomItems = new HashMap<>();

        // Přidání položek do mapy
        roomItems.put(vstupDoKatakomb, Arrays.asList(stary_klic));
        roomItems.put(levyDungeon, Arrays.asList(mumie)); // Změněno z levyDungeon1
        roomItems.put(levyMost, Arrays.asList(strazce)); // Změněno z levyDungeon2
        roomItems.put(hrobkaPrince, Arrays.asList(excalibur /*, kralovska_pecet */));
        roomItems.put(hrobkaRytiru, Arrays.asList( palcat));
        roomItems.put(pokladnice, Arrays.asList(truhla, hrouda_zlata));
        roomItems.put(pravyDungeon, Arrays.asList(carodej));
        roomItems.put(pravyMost, Arrays.asList(elemental));
        roomItems.put(loveckySalon, Arrays.asList(luk));
        roomItems.put(studanka, Arrays.asList(kyblik, studna));
        roomItems.put(stredKatakomb, Arrays.asList(socha, sloup, klic_zpet));
        roomItems.put(kralovskaKrypta, Arrays.asList(trun, sarkofag));

        // Procházení mapy a přidávání položek do pokojů
        for (Map.Entry<Room, List<Item>> entry : roomItems.entrySet()) {
            Room room = entry.getKey();
            List<Item> items = entry.getValue();
            for (Item item : items) {
                room.insertItem(item);
            }
        }





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
        Item stary_klic = new Item("stary_klic",true,"Zámek se ti podařilo odemknout\n");
        vstupDoKatakomb.insertItem(stary_klic);



    }


}
