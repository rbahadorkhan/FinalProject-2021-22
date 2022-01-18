package zork;

public class Item {
  private int weight;
  private String name;
  private String startingRoom;
  private boolean isWeapon;
  private boolean isHealing;
  private String description;
  
  //constructor for item
  public Item(int weight, String name, String startingRoom, boolean isWeapon, boolean isHealing, boolean isSpike, String description) {
    this.weight = weight;
    this.name = name;
    this.startingRoom = startingRoom;
    this.isWeapon = isWeapon;
    this.isHealing = isHealing;
    this.description = description;
  }
 
  public Item(){
  }

  //returns the weight of the item
  public int getWeight() {
    return weight;
  }

  //returns the items name
  public String getName() {
    return name;
  }

  //returns the starting room
  public String getStartingRoom() {
    return startingRoom;
  } 

  //returns true if the item is a weapon
  public boolean isWeapon() {
    return isWeapon;

  }
  
  //returns true if the item is a health orb
  public boolean isHealing() {
    return isHealing;
  }

  //returns the description of the item
  public String getDescription(){
    return description;
  }


}
