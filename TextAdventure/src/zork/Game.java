package zork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Game {

  public static HashMap<String, Room> roomMap = new HashMap<String, Room>();
  public static ArrayList<Attacker> attackerList = new ArrayList<Attacker>();
  public static ArrayList<Item> ItemList = new ArrayList<Item>();

  private Parser parser;
  private Room currentRoom;
  private int myHealth = 150;
  private Inventory myInventory;

  /**
   * Create the game and initialise its internal map.
   */
  public Game() {
    try {
      initRooms("src\\zork\\data\\rooms.json");
      initItems("src\\zork\\data\\items.json");
      for(Item item : ItemList){
        String startingRoom = item.getStartingRoom();
        Room room = roomMap.get(startingRoom);
        room.setItem(item);
      }
      initAttacker("src\\zork\\data\\attacker.json");
      for (Attacker attacker : attackerList) {
        String startingRoom = attacker.getStartingRoom();
        Room room = roomMap.get(startingRoom);
        room.setAttacker(attacker);
      }
      currentRoom = roomMap.get("Spawn");
    } catch (Exception e) {
      currentRoom = roomMap.get("Spawn"); //please remove this later we have many issues because we dont know how to code :))
      e.printStackTrace();
    }
    parser = new Parser();
  }


  private void initAttacker(String fileName) throws Exception{
    Path path = Path.of(fileName);
    String jsonString = Files.readString(path);
    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(jsonString);

    JSONArray jsonAttacker = (JSONArray) json.get("attacker");
    
    for (Object attackerObj : jsonAttacker) {
      Attacker attacker = new Attacker();
      String attackerName = (String) ((JSONObject) attackerObj).get("name");
      String attackerId = (String) ((JSONObject) attackerObj).get("id");
      String attackerDescription = (String) ((JSONObject) attackerObj).get("description");
      String startingRoom = ((String)((JSONObject)attackerObj).get("startingRoom"));
      int hp = ((Long)((JSONObject)attackerObj).get("hp")).intValue();
      int attack = ((Long)((JSONObject)attackerObj).get("attack")).intValue();;
      attackerList.add(new Attacker(attackerId, attackerName, attackerDescription, startingRoom, hp, attack));

      }
  }

  private void initItems(String fileName) throws Exception {
    Path path = Path.of(fileName);
    String jsonString = Files.readString(path);
    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(jsonString);

    JSONArray jsonItems = (JSONArray) json.get("items");
    
    for (Object itemObj : jsonItems) {
      Item item = new Item(); 
      String itemName = (String) ((JSONObject) itemObj).get("name");
      String itemId = (String) ((JSONObject) itemObj).get("id");
      String itemDescription = (String) ((JSONObject) itemObj).get("description");
     // Boolean isOpenable = (Boolean) ((JSONObject) itemObj).get("isOpenable");
      int itemWeight = ((Long)((JSONObject)itemObj).get("weight")).intValue();
      String startingRoom = (String)((JSONObject)itemObj).get("startingRoom");
      boolean isWeapon = (boolean)((JSONObject)itemObj).get("isWeapon");
      if(isWeapon){
        int numBullets = ((Long)((JSONObject)itemObj).get("numBullets")).intValue();
        int damage = ((Long)((JSONObject)itemObj).get("damage")).intValue();
        int accuracy = ((Long)((JSONObject)itemObj).get("accuracy")).intValue();
        ItemList.add(new Weapon(itemWeight, damage, accuracy, numBullets, itemName, startingRoom, isWeapon));
      }
      else{
        ItemList.add(new Item(itemWeight, itemName, startingRoom, isWeapon));
      }

      }
     
  }

  private void initRooms(String fileName) throws Exception {
    Path path = Path.of(fileName);
    String jsonString = Files.readString(path);
    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(jsonString);

    JSONArray jsonRooms = (JSONArray) json.get("rooms");

    for (Object roomObj : jsonRooms) {
      Room room = new Room();
      String roomName = (String) ((JSONObject) roomObj).get("name");
      String roomId = (String) ((JSONObject) roomObj).get("id");
      String roomDescription = (String) ((JSONObject) roomObj).get("description");
      room.setDescription(roomDescription);
      room.setRoomName(roomName);

      JSONArray jsonExits = (JSONArray) ((JSONObject) roomObj).get("exits");
      ArrayList<Exit> exits = new ArrayList<Exit>();
      for (Object exitObj : jsonExits) {
        String direction = (String) ((JSONObject) exitObj).get("direction");
        String adjacentRoom = (String) ((JSONObject) exitObj).get("adjacentRoom");
        String keyId = (String) ((JSONObject) exitObj).get("keyId");
        Boolean isLocked = (Boolean) ((JSONObject) exitObj).get("isLocked");
        Boolean isOpen = (Boolean) ((JSONObject) exitObj).get("isOpen");
        Exit exit = new Exit(direction, adjacentRoom, isLocked, keyId, isOpen);
        exits.add(exit);
      }
      room.setExits(exits);
      roomMap.put(roomId, room);
    }
  }

  

  /**
   * Main play routine. Loops until end of play.
   */
  public void play() {
    printWelcome();

    boolean finished = false;
    while (!finished) {
      Command command;
      try {
        command = parser.getCommand();
        finished = processCommand(command);
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
    System.out.println("Thank you for playing. Good bye.");
  }

  /**
   * Print out the opening message for the player.
   */
  private void printWelcome() {
    System.out.println();
    System.out.println("Welcome to Zork!");
    System.out.println("Zork is a interactive shooting game.");
    System.out.println("Type 'help' if you need help.");
    System.out.println();
    System.out.println(currentRoom.longDescription());
  }

  /**
   * Given a command, process (that is: execute) the command. If this command ends
   * the game, true is returned, otherwise false is returned.
   */
  private boolean processCommand(Command command) {
    if (command.isUnknown()) {
      System.out.println("I don't know what you mean...");
      return false;
    }

    String commandWord = command.getCommandWord();
    if (commandWord.equals("help"))
      printHelp();
    else if (commandWord.equals("go"))
      goRoom(command);
    else if (commandWord.equals("quit")) {
      if (command.hasSecondWord())
        System.out.println("Quit what?");
      else
        return true; // signal that we want to quit
    }  else if (commandWord.equals("shoot")) 
        shoot(command); 
      else if(commandWord.equals("take"))
        take(command);
      else if(commandWord.equals("look"))
        currentRoom.longDescription();
      else if(commandWord.equals("use")){
        useItem(command);
      }
      else if(commandWord.equals("inventory")){
        myInventory.printItems();
      }
      else if(commandWord.equals("drop")){
        myInventory.dropItem(command.getSecondWord());
      }
      else if(commandWord.equals("jump")){
        jump(currentRoom);
      }
     
    return false;
  }

  // implementations of user commands:




  /**
   * Print out some help information. Here we print some stupid, cryptic message
   * and a list of the command words.
   */
  private void printHelp() {
    System.out.println("You are lost. You are alone. You wander");
    System.out.println("around at Monash Uni, Peninsula Campus.");
    System.out.println("Your goal is to find the spike and defuse it, be careful you may be attacked");
    System.out.println();
    System.out.println("Your command words are:");
    parser.showCommands();
  }

  /**
   * Try to go to one direction. If there is an exit, enter the new room,
   * otherwise print an error message.
   */
  private void goRoom(Command command) {
    if (!command.hasSecondWord()) {
      // if there is no second word, we don't know where to go...
      System.out.println("Go where?");
      return;
    }

    String direction = command.getSecondWord();

    // Try to leave current room.
    Room nextRoom = currentRoom.nextRoom(direction);

    if (nextRoom == null)
      System.out.println("You cannot go that way");
    else {
      currentRoom = nextRoom;
      System.out.println(currentRoom.longDescription());
    }
  }

  private void shoot(Command command) {
    if(!command.hasSecondWord())  {
      //if there is no second word, we don't know what to shoot with...
      System.out.println("Shoot with what?");
      return;
    }

    String gunName = command.getSecondWord();
    Item gun = null;

    if(myInventory.inInventory(gunName)){
      for (Item item : myInventory.getInventory()) {
        if(gunName.equalsIgnoreCase(item.getName())){
          gun = item;
          break;
        }
      }
      if(gun.isWeapon()){
        int damageDealt = ((Weapon) gun).damageDealt();
        if(currentRoom.hasAttacker()){
          Attacker attacker = currentRoom.getAttacker();
          attacker.reduceHp(damageDealt);
          if(attacker.getHp() < 1){
            attackerList.remove(attacker);
          }
          else{
            myHealth -= attacker.getAttack();
            if(myHealth < 1){
              currentRoom = roomMap.get("Spawn");
              System.out.println("You died you have been respawned in spawn");
              //drop items idk how yet or maybe just die idk
            }
          }
        }
        else{
          System.out.println("There is no enemy in this room");
        }
      }
      else{
        System.out.println("That item is not a weapon");
      }
      
  }
  else{
    System.out.println("You do not have this gun");
  }
}

  private void take(Command command) {
    if(!command.hasSecondWord()) {
      System.out.println("What do you want to take?");
      return;
    }

    String newItem = command.getSecondWord();
    
    Item item = null; 
    
    for (Item potentialItem : currentRoom.getRoomItems()) {
      if(newItem.equalsIgnoreCase(potentialItem.getName())){
        item = potentialItem;
        break;
      }
    }

    myInventory.addItem(item);
    currentRoom.removeItem(item);
  }

  
  private void useItem(Command command) {
    //if there is no second word, we don't know what to use
    if(!command.hasSecondWord()) {
      System.out.println("use what?");
      return;
    }
    String itemName = command.getSecondWord();
    Item potentialItem = null;

    if(myInventory.inInventory(itemName)){
      for (Item item : myInventory.getInventory()) {
        if(itemName.equalsIgnoreCase(item.getName())){
          potentialItem = item;
          break;
        }
      }
      if(potentialItem.isWeapon()){
        shoot(command);
      }
/*    else if(potentialItem.isHealing()){
        myHealth += 60;
        myInventory.dropItem(potentialItem); //need to make this a string or make dropitem a item constructor
        itemList.remove(potentialItem);
      } */
      //need to make health
    }
  }
  
  private void jump(Room currentRoom) {
    ArrayList<Exit> exits = currentRoom.getExits();
    System.out.println("You jump above the walls and you see the whats in the adjacent rooms");
    for (Exit exit : exits) {
      String direction = exit.getDirection();
      Room nextRoom = currentRoom.nextRoom(direction);
      System.out.println("To the " + direction + nextRoom.getDescription());
    }

  }

}

  


