package zork;

import java.util.ArrayList;

public class Room {

  private String roomName;
  private String description;
  private ArrayList<Exit> exits;
  private Attacker attacker;
  private boolean isTeleportRoom; // if its a teleport room
  private ArrayList<Item> roomItems;
  private ArrayList<Room> teleportRooms; // these are the rooms i can teleport to

  public ArrayList<Exit> getExits() {
    return exits;
  }

  public void setExits(ArrayList<Exit> exits) {
    this.exits = exits;
  }

  /**
   * Create a room described "description". Initially, it has no exits.
   * "description" is something like "a kitchen" or "an open court yard".
   */
  public Room(String description) {
    this.description = description;
    exits = new ArrayList<Exit>();
  }

  //creates a default room
  public Room() {
    roomName = "DEFAULT ROOM";
    description = "DEFAULT DESCRIPTION";
    exits = new ArrayList<Exit>();
    
    teleportRooms = new ArrayList<Room>(); 
    roomItems = new ArrayList<Item>();
  }
  
  //adds exits 
  public void addExit(Exit exit) throws Exception {
    exits.add(exit);
  }

  /**
   * Return the description of the room (the one that was defined in the
   * constructor).
   */
  public String shortDescription() {
    return "Room: " + roomName + "\n\n" + description;
  }

  /**
   * Return a long description of this room, on the form: You are in the kitchen.
   * Exits: north west
   * There are no items in this room. OR The items in this room are: Spectre.
   * (You see an enemy in this room, they call themselves Raze. 
   * Attacker description ) 
   * (This is a teleport room
   * You can teleport to: Hookah, Fountain)
   */
  public String longDescription() {

    String teleportString = "";
    String itemString = "There are no items in this room.";
    String attackerString = "";

    if(roomItems.size() > 0){
      itemString = "The items in this room are: ";
    for (Item item : roomItems) {
      itemString += item.getName() + ", ";
    }
      itemString = itemString.substring(0, itemString.lastIndexOf(","));
   }

    if(hasAttacker()){
      attackerString += "You see an enemy in this room, they call themselves " + attacker.getName() + "\n" + attacker.getDescription();
    }

    if(isTeleportRoom){
      teleportString += "This is a teleport room. \n You can teleport to: ";
      for (Room teleport : teleportRooms) {
        teleportString += teleport.getRoomName() + ", ";
      } 
      teleportString = teleportString.substring(0, teleportString.lastIndexOf(","));
    }

    //if there are no attackers and if there are no attackers it will not create new lines. 
    return "Room: " + roomName + "\n\n" + description + "\n" + exitString() + "\n" + itemString + (attackerString.equals("")?"":("\n" + attackerString)) + (teleportString.equals("")?"":("\n" + teleportString));

  }

  /**
   * Return a string describing the room's exits, for example "Exits: north west
   * ".
   */
  private String exitString() {
    String returnString = "Exits: ";
    for (Exit exit : exits) {
      returnString += exit.getDirection() + " ";
    }

    return returnString;
  }

  /**
   * Return the room that is reached if we go from this room in direction
   * "direction". If there is no room in that direction, return null.
   */
  public Room nextRoom(String direction) {
    try {
      for (Exit exit : exits) {

        if (exit.getDirection().equalsIgnoreCase(direction)) {
          String adjacentRoom = exit.getAdjacentRoom();

          return Game.roomMap.get(adjacentRoom);
        }

      }
    } catch (IllegalArgumentException ex) {
      System.out.println(direction + " is not a valid direction.");
      return null;
    }

    System.out.println(direction + " is not a valid direction.");
    return null;
  }

  /*
   * private int getDirectionIndex(String direction) { int dirIndex = 0; for
   * (String dir : directions) { if (dir.equals(direction)) return dirIndex; else
   * dirIndex++; }
   * 
   * throw new IllegalArgumentException("Invalid Direction"); }
   */
  public String getRoomName() {
    return roomName;
  }

  //changes room name
  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }

  //returns the description
  public String getDescription() {
    return description;
  }

  //sets the description 
  public void setDescription(String description) {
    this.description = description;
  }

  //returns true if the room has an attacker.
  public boolean hasAttacker(){
    return attacker != null;
  }

  //sets the room's attacker
  public void setAttacker(Attacker attacker){
    this.attacker = attacker;
  }

  //returns the room's attacker
  public Attacker getAttacker() {
    return attacker;
  }

  //returns if an item is in this room.
  public boolean IsInRoom(Item item){
    return roomItems.contains(item);
  }

  //adds an item to the room
  public void setItem(Item item){
    if(item != null)
     roomItems.add(item);
  }

  //adds multiple items to the room
  public void setMultipleItems(ArrayList<Item> items){
    for (Item item : items) {
      if(item != null)
      roomItems.add(item);
    }
  }

  //removes an item from the room
  public void removeItem(Item item){
    roomItems.remove(item);
  }

  public boolean isTeleportRoom(){ // if its telelport room gives option if its not you cant tp
    return isTeleportRoom; 
  }

  public ArrayList <Room> getTeleportRooms(){ 
  //If the currentRoom is a teleport toom then get the teleportRooms 
  //(getTeleportRooms() and then iterate and display the names of each of those rooms. 
  // Have them pick one and then get the id of the room and use the roomMap.get(roomid) 
  //and set the currentRoom.
    return teleportRooms;
  }

  //returns the room's items.
  public ArrayList<Item> getRoomItems(){
    return roomItems;
  }

  //sets if it is a teleport room.
  public void setIsTeleportRoom(boolean isTeleportRoom) {
    this.isTeleportRoom = isTeleportRoom;
  }

  //sets the teleport rooms. 
  public void setTeleportRooms(ArrayList<Room> teleportRooms) { 
    this.teleportRooms=teleportRooms;
  }

  //removes attacker from the room. 
  public void removeAttacker(){
    attacker = null;
  }
}
