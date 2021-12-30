package zork;

public class Attacker {
    private String id;
    private String name;
    private String description;
    private String startingRoom;
    private int hp;
    private int attack;


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

    public int getHp(){
        return hp;
    }

    public void reduceHp(int loss){
        hp-= loss;
    }
    
    public int getAttack(){
        return attack; 
    }

    public void changeHp(Character cha, int reduced){
        hp -= reduced; 
    }

    public String getStartingRoom() {
        return startingRoom;
    }
    


}