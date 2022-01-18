package zork;
//done
public class Attacker {
    private String id;
    private String name;
    private String description;
    private String startingRoom;
    private int hp;
    private int attack;

    //Creates the instance of an attacker, constructor. 
    public Attacker(String id, String name, String description, String startingRoom, int hp, int attack){
        this.id = id;
        this.name = name;
        this.description = description;
        this.startingRoom = startingRoom;
        this.hp = hp;
        this.attack = attack;
    }

    public Attacker() {
    }

    //returns attackerHp
    public int getHp(){
        return hp;
    }

    //allows you to reduce the hp of an attacker
    public void reduceHp(int loss){
        hp-= loss;
    }
    
    //gets the amount of damage that this attacker does
    public int getAttack(){
        return attack; 
    }

    //gets a string of the startingroom of this attacker
    public String getStartingRoom() {
        return startingRoom;
    }

    //returns the name of the attacker.
    public String getName(){
        return name;
    }

    //returns the description of the attacker.
    public String getDescription(){
        return description;
    }
    


}
