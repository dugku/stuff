import java.util.InputMismatchException;
import java.io.*;
import java.util.Scanner;
import java.util.Random;

public class Homework6
{
    //wild Pokemon (read in from a file) list size)
    public static final int WILD_POKEMON_LIST_SIZE = 25;
    private static Scanner myScanner = new Scanner(System.in);

    
    //error checking for input
    public static char charInputCheck()
    {
        Scanner scanner = new Scanner(System.in);
        char input = scanner.nextLine().charAt(0);
        while(input != 'y' && input != 'n')
        {  
            System.out.print("Please enter either 'y' or 'n'. >> ");
            input = scanner.nextLine().charAt(0);
        }
        return input;
    }
    

    //given - generate a wild Pokemon
    public static String generatePokemonEncounter(String[] pokeList)
    {
        //generate a random number between [0..pokeList.length]
        Random rand = new Random();
        //return encountered pokemon
        return pokeList[rand.nextInt(pokeList.length)];
    }
 
    //given - generate a random item
    public static String generateItemEncounter()
    {
        //generate random number between [0..inventory max]
        Random rand = new Random();
        int randomNumber = rand.nextInt(Trainer.INVENTORY_MAX);
        String pickedUpItem = "";
        switch(randomNumber)
        {
            case 0: 
                pickedUpItem = "Poke Ball";
                break;
            case 1: 
                pickedUpItem = "Ultra Ball";
                break;
            case 2: 
                pickedUpItem = "Revive";
                break;
            default: 
                pickedUpItem = "Poke Ball";
        }
        System.out.println(pickedUpItem);
        return pickedUpItem;
    }
    
    public static void readFile(String[] pokemonList,String filePath){
        File file = new File(filePath);
    
        try (Scanner fileScanner = new Scanner(file)) {  // This should now work correctly
            int i = 0;
            while(fileScanner.hasNextLine() && i < pokemonList.length) {
                pokemonList[i++] = fileScanner.nextLine();
            }
            System.out.println("File read successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            e.printStackTrace();
        }
    }

    public static boolean inputHandling(String sentence, String otherInput, String OptionalPass) {
        
        if (OptionalPass != null){
            System.out.printf(sentence, otherInput);
            char input = myScanner.nextLine().trim().toLowerCase().charAt(0);  

            while (input != 'y' && input != 'n') {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
                System.out.printf(sentence, otherInput, OptionalPass); 
                input = myScanner.nextLine().trim().toLowerCase().charAt(0);
                myScanner.nextLine();
            }
            
            return input == 'y';
        }
        
        System.out.printf(sentence, otherInput);
        char input = myScanner.nextLine().trim().toLowerCase().charAt(0);  

        while (input != 'y' && input != 'n') {
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
            System.out.printf(sentence, otherInput); 
            input = myScanner.nextLine().trim().toLowerCase().charAt(0);
            myScanner.nextLine();
        }
        return input == 'y';
    }
    
    public static void generatePoke(Trainer player) {
        String[] playerPoke = player.getPokemon();

        System.out.println("Which pokemon do you want to use?");

        // Displaying available Pokémon
        for (int i = 0; i < playerPoke.length; i++) {
            if (playerPoke[i] != null) {
                System.out.println("\t" + (i + 1) + "). " + playerPoke[i]);
            }
        }
    }
    
    
    public static String generatePokeList(Trainer player) {
        generatePoke(player);

        Scanner localScanner = new Scanner(System.in);  // Local scanner for this method

        String[] playerPoke = player.getPokemon();
        int intInput = 0;
        boolean valid = false;

        System.out.println("Enter the number corresponding to your choice:");
        while (!valid) {
            String input = localScanner.nextLine().trim();

            try {
                intInput = Integer.parseInt(input);
                if (intInput >= 1 && intInput <= playerPoke.length && playerPoke[intInput - 1]!= null) {
                    valid = true;
                } else {
                    System.out.println("Invalid selection. Please enter a number between 1 and " + playerPoke.length);
                }
            } catch (NumberFormatException e) {
                System.out.println("That's not a number. Please enter a valid number.");
            }
        }

        return playerPoke[intInput - 1];
    }
    
    public static int randomFighting(){
        Random rnd = new Random();
        //guess the type casting isn't necessary but oh well.
        return (int) ( Math.random() * 2 + 1); //will either return 1 or 2 thanks other resources since in a real job I can use them.
    }
    
    public static boolean WonLostLogic(String playerPoke, String wildPoke, int outcome, String wonSentence, String lostSentence){
        char input = myScanner.nextLine().trim().toLowerCase().charAt(0);
        
        System.out.printf((outcome == 1) ? wonSentence : lostSentence, playerPoke, wildPoke);
        
        System.out.flush();

        
        input = myScanner.nextLine().trim().toLowerCase().charAt(0);  // Read initial response
        
        while (input != 'y' && input != 'n') {
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
            if (outcome == 1) {
                System.out.printf(wonSentence, playerPoke, wildPoke); // Reprint the message for clarity
            } else {
                System.out.printf(lostSentence, playerPoke, wildPoke);
            }
            input = myScanner.nextLine().trim().toLowerCase().charAt(0);
        }
        
        System.out.flush();

        return input == 'y';
    }
    
    public static void generateItemList(Trainer player){
        int pokeBall = Trainer.POKE_BALL;
        int ultra = Trainer.ULTRA_BALL;
        
        int pokeRemain = player.getItemAmount(pokeBall);
        int ultraRemain = player.getItemAmount(ultra);
        
        System.out.println("What ball do you want to use?");
        System.out.printf("\t1) Poke Ball (%s remaining)\n", pokeRemain);
        System.out.printf("\t2) Ultra Ball (%s remaining)\n", ultraRemain);
    }

    public static boolean catchingLogic(Trainer player){
        
        int input = 0;  // Initial value set to 0
        boolean valid = false;
        while (!valid) {
           input = myScanner.nextInt();
           
            switch(input) {
                case 1:
                    if (player.getItemAmount(Trainer.POKE_BALL) > 0 && player.hasPokemonSpace()){
                        player.useItem(Trainer.POKE_BALL);
                        valid = true;
                    } else {
                        System.out.println("You don't have any Poke Balls to use!");
                    }
                    break; 
                case 2:
                    if(player.getItemAmount(Trainer.ULTRA_BALL) > 0 && player.hasPokemonSpace()){
                        player.useItem(Trainer.ULTRA_BALL);
                        valid = true;
                    } else {
                        System.out.println("You don't have any Ultra Balls to use!");
                    }
                    break; 
                default:
                    System.out.println("Invalid input. Please enter a number.");
                    myScanner.next();
                    break; 
            }
        }
        return valid;
    }
    
    public static void main(String[] args)
    {
        Random rand = new Random();
        
        String wildPokemon = "";
        String item = "";
        boolean stillPlaying = true;
        char input = '\0';
        int choice = 0, itemIndex = 0;
        String currPokemon = "";
        String pokemonList[] = new String[WILD_POKEMON_LIST_SIZE];
        for(int i = 0; i < pokemonList.length; i++)
            pokemonList[i] = "";
        
        //uncomment each of the tests as you finish writing the code in Trainer.java
        System.out.println("Testing readFile()...");
        readFile(pokemonList, "PokemonList.txt");
        
        System.out.println("Testing default constructor...");
        Trainer player = new Trainer();
        player.printStats();

        System.out.println("Testing parameterized constructor...");
        Trainer brock = new Trainer("Brock", "Diglett");
        brock.printStats();

        System.out.println("Testing copy constructor...");
        Trainer brock2 = new Trainer(brock);
        brock2.printStats();

        System.out.println("Testing setName()...");
        player.setName("Ash");
        player.printStats();

        System.out.println("Testing addPokemon()...");
        player.addPokemon("Pikachu");
        player.printStats();

        System.out.println("Testing addItem()...");
        player.addItem(Trainer.POKE_BALL);
        player.addItem(Trainer.ULTRA_BALL);
        player.addItem(Trainer.REVIVE);
        player.printStats();

        System.out.println("Testing generateEncounter()...");
        wildPokemon = generatePokemonEncounter(pokemonList);
        item = generateItemEncounter();
        System.out.println("wildPokemon: " + wildPokemon);
        System.out.println("item: " + item + "\n");

        System.out.println("Game loop starting...");
        
        String ItemSentence = "You found a(n) %s! Do you want to keep it? y/n";
        String encounterSentence = "You encountered a(n) %s\nDo you want to fight it? y/n";
        String lostSentence  = "Oh no! %s fainted and the %s escaped! Use a Revive? y/n";
        String wonSentence = "You and %s won! Do you want to catch the %s?";
        String reviveSentence = "Oh no! %s fainted and the %s escaped! Use a Revive? y/n";
        
        while(stillPlaying)
        {
            System.out.flush();
            //generate an encounter to get the random item and the wild Pokemon
            String genItem = generateItemEncounter();
    
            boolean keep = inputHandling(ItemSentence, genItem, null);

            // Output the results of the encounter
            System.out.println(genItem + " " + keep);  // Debug: Show item and keep result
            System.out.println("You choose to " + (keep ? "keep" : "discard") + " the " + genItem + ".");
            //FULFILL ITEM ENCOUNTER
            if (keep) {
                if(genItem == "Poke Ball") {
                    player.addItem(Trainer.POKE_BALL);
                } 
                if(genItem == "Ultra Ball") {
                    player.addItem(Trainer.ULTRA_BALL);
                }
                if(genItem == "Revive") {
                    player.addItem(Trainer.REVIVE);
                }
            }
            
            
            // PRINT STATS
           player.printStats();
        
           int fightOutcome = randomFighting();

            // generate steps
            int numSteps = rand.nextInt(10) + 1;
            for (int i = 1; i <= numSteps; i++)
            { 
                if(numSteps == 1)
                    System.out.println("You've taken 1 step.\n");
                else
                    System.out.println("You've taken " + i + " steps.");
            }
            
             //CHECK IF THE USER CAN STILL PLAY
            // if the user has no pokemon, they can't keep playing
            if(player.getNumPokemon() == 0) {
                System.out.println("You can't keep playing.");
                break;
            }
        
            //FULFILL POKEMON ENCOUNTER
            String wildestPokemon = generatePokemonEncounter(pokemonList);
            boolean fight = inputHandling(encounterSentence, wildestPokemon, null);
            
            if(fight) {
                String pokemon = generatePokeList(player);
                fightOutcome = randomFighting();
                
                boolean crap = WonLostLogic(pokemon, wildestPokemon, fightOutcome, wonSentence, lostSentence);
                
                if (fightOutcome == 1){
                    generateItemList(player);
                    catchingLogic(player);
                
                    System.out.printf("You have caught %s", wildestPokemon);
                    player.addPokemon(wildestPokemon);
                } else{
                    if (!crap){
                        System.out.printf("%s was transferred to Professor Oak.\n", pokemon);
                        
                        String[] myPoke = player.getPokemon();
                        int indexToRemove = -1;
                        for (int i = 0; i < myPoke.length; i++) {
                            if (myPoke[i] != null && myPoke[i].equals(pokemon)) {
                                indexToRemove = i;
                                break;
                            }
                        }

                        if (indexToRemove != -1) {
                            player.removePokemon(indexToRemove);  // Correctly remove the Pokémon by index
                        }
                    }
                    if(crap){
                        player.useItem(Trainer.REVIVE);
                        System.out.printf("%s was healed. %d revive(s) remaining\n",pokemon, player.getItemAmount(Trainer.REVIVE));
                    }
                }
            }
            else {
                System.out.println("You've ran away.");
            }
            
            
            // else, see if user wants to keep playing

                //note that these 4 lines will likely be inside of an if/else block that is detailed 
                //at the end of 2. Description in the homework
                System.out.print("Do you want to keep playing? >> ");
                input = charInputCheck();

                if (input == 'n')
                    stillPlaying = false;
  
        }
        //create a unique filename with number of seconds passed since Jan 1, 1970 so multiple
        //runs of the program will save to different files
        String outputFilename = "player-" + System.currentTimeMillis() + ".txt";
        
        //save to file
        player.writeFile(outputFilename);
    }
}