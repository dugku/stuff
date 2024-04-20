import java.io.*;

public class Trainer
{
    // class member variables
    // set the maximum for the array sizes, and number of items that can be held
    public static final int POKEMON_MAX = 5;
    public static final int ITEM_MAX = 5;
    public static final int INVENTORY_MAX = 3;
    
    // indices of each item in the inventory array
    public static final int POKE_BALL = 0;
    public static final int ULTRA_BALL = 1;
    public static final int REVIVE = 2;

    // private instance variables
    private String name;
    private int numPokemon;
    private String[] pokemon;
    private int inventory[];
    
    /* -------------------------------------------------
    // Name: Trainer
    // Parameters: none
    // Returns: none
    // Purpose: Constructor. Initializes member variables.
    // -------------------------------------------------*/
    public Trainer()
    {
        name = "";
        numPokemon = 0;
        pokemon = new String[POKEMON_MAX];
        inventory = new int[ITEM_MAX];
    }
    
    /* -------------------------------------------------
    // Name: Trainer
    // Parameters: userName: the name of the player
    //             starter: the name of the starter pokemon
    // Returns: none
    // Purpose: Constructor. Initializes member variables to parameters.
    // -------------------------------------------------*/
    public Trainer(String userName, String starter)
    {
        name = userName;
        numPokemon = 1;
        pokemon = new String[POKEMON_MAX];
        pokemon[0] = starter;
        inventory = new int[ITEM_MAX];
    }
    /* -------------------------------------------------
    // Name: Trainer
    // Parameters: other: Trainer, object to be copied from
    // Returns: none
    // Purpose: Copy constructor. Initializes member variables to other Trainer's member variables.
    // -------------------------------------------------*/
    public Trainer(Trainer other){
        this.name = other.name;
        this.numPokemon = other.numPokemon;
        this.pokemon = new String[POKEMON_MAX];
        System.arraycopy(other.pokemon, 0, this.pokemon, 0, other.numPokemon);
        this.inventory = new int[ITEM_MAX];
        System.arraycopy(other.inventory, 0, this.inventory, 0, ITEM_MAX);
    }



    /* -------------------------------------------------
    // Name: getName
    // Parameters: none
    // Returns: string, the trainer's name
    // Purpose: Gets the private name variable.
    // -------------------------------------------------*/    
    public String getName(){
        return name;
    }

    /* -------------------------------------------------
    // Name: getNumPokemon
    // Parameters: none
    // Returns: int, number of pokemon
    // Purpose: Gets the number of Pokemon the user has.
    // -------------------------------------------------*/    
    public int getNumPokemon() {
        int hasPokemon = 0;
        for (int i = 0; i < pokemon.length; i++) {
            if (pokemon[i] != null) {
                hasPokemon++;
            }
        }
        return hasPokemon;
    }

        
    /* -------------------------------------------------
    // Name: getItemAmount
    // Parameters: index: int
    // Returns: int, number of specific item
    // Purpose: Gets the amount of a given item the user has.
    // -------------------------------------------------*/
    public int getItemAmount(int index){
        return inventory[index];
    }



    /* -------------------------------------------------
    // Name: getPokemon
    // Parameters: index, int
    // Returns: string, name of the pokemon at the index
    // Purpose: Gets the name of a pokemon the player has.
    // -------------------------------------------------*/
    public String[] getPokemon(){
        return pokemon;
    }


    
    /* -------------------------------------------------
    // Name: setName
    // Parameters: userName: string
    // Returns: none
    // Purpose: Sets the name of the trainer.
    // -------------------------------------------------*/
    public void setName(String userName) {
        this.name = userName;
    }


    
    /* -------------------------------------------------
    // Name: useItem
    // Parameters: index, int
    // Returns: none
    // Purpose: Uses the item at the specified index.
    // -------------------------------------------------*/
    public void useItem(int index){
        inventory[index]--;
    }



    /* -------------------------------------------------
    // Name: removePokemon
    // Parameters: index, int
    // Returns: none
    // Purpose: Removes Pokemon at the specified index from lineup, and moves up the other pokemon so there's not an empty slot
    //  GIVEN
    // -------------------------------------------------*/ 
    public void removePokemon(int index)
    {
        for(int i = index; i < numPokemon-1; i++)
            pokemon[i] = pokemon[i+1];
        pokemon[--numPokemon] = null;
    }
    
    /* -------------------------------------------------
    // Name: printStats
    // Parameters: none
    // Returns: none
    // Purpose: Prints the trainer's stats.
    // GIVEN
    // -------------------------------------------------*/
    public void printStats()
    {
        System.out.println("Name: " + name);
        System.out.println("numPokemon: " + numPokemon);
        System.out.println("Inventory: ");
        System.out.println("\t(" + inventory[POKE_BALL] + ") Poke Ball(s)");
        System.out.println("\t(" + inventory[ULTRA_BALL] + ") Ultra Ball(s)");
        System.out.println("\t(" + inventory[REVIVE] + ") Revive(s)");
        System.out.print("Pokemon: ");
        for(int i = 0; i < numPokemon; i++)
            System.out.print(pokemon[i] + ", ");
        System.out.println("\n");
    }


    // -------------------------------------------------
    // Name: writeFile
    // Parameters: filename: the name of the file
    // Returns: none
    // Purpose: Prints the trainer's stats to a file
    // -------------------------------------------------
    public void writeFile(String filename){
        File file = new File(filename);
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("Name: " + this.name);
            writer.println("numPokemon: " + this.numPokemon);
            writer.println("Inventory:");
            writer.printf("\t(%d) Poke Ball(s)\n", this.inventory[POKE_BALL]);
            writer.printf("\t(%d) Ultra Ball(s)\n", this.inventory[ULTRA_BALL]);
            writer.printf("\t(%d) Revive(s)\n", this.inventory[REVIVE]);
            writer.print("Pokemon: ");
            for (int i = 0; i < this.numPokemon; i++) {
                writer.print(this.pokemon[i]);
                if (i < this.numPokemon - 1) {
                    writer.print(", ");
                }
            }
            writer.println();
        } catch (FileNotFoundException e) {
            System.out.println("Error writing to file: " + filename);
            e.printStackTrace();
        }
    }    
 
 
    
    /* -------------------------------------------------
    // Name: hasPokemonSpace
    // Parameters: none
    // Returns: bool, whether or not the trainer has space for another pokemon
    // Purpose: Returns true if the trainer has space for another pokemon. Returns false if not.
    // -------------------------------------------------*/
    public boolean hasPokemonSpace(){
        for (int i = 0; i < pokemon.length; i++){
            if (pokemon[i] == null){
                return true;
            }
        }
        return false;
    }



    /* -------------------------------------------------
    // Name: addPokemon
    // Parameters: pokemonName, string, the name of the new pokemon
    // Returns: none
    // Purpose: Adds a pokemon to the trainer's pokemon inventory.
    // -------------------------------------------------*/
    public void addPokemon(String pokemonName){
        if(numPokemon < POKEMON_MAX) {
            pokemon[numPokemon] = pokemonName;
            numPokemon++;
        }
        else {
            System.out.println("No space to add another Pokemon");
        }
    }


    
    /* -------------------------------------------------
    // Name: addItem
    // Parameters: index, the index of the item the trainer picked up
    // Returns: bool, whether or not the item could be added
    // Purpose: If the trainer has space, add an item. Otherwise don't.
    // -------------------------------------------------*/
    public boolean addItem(int index){
        if (index < 0 || index >= ITEM_MAX) { 
            return false; 
        }

        if (inventory[index] < INVENTORY_MAX) {
            inventory[index]++;
            return true;
        } else {
            return false;
        }
    }   

}