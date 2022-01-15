package zork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Game {
  private Scanner in;
  public static HashMap<String, Room> roomMap = new HashMap<String, Room>();
  public static ArrayList<Attacker> attackerList = new ArrayList<Attacker>();
  public static ArrayList<Item> ItemList = new ArrayList<Item>();

  private Parser parser;
  private Room currentRoom;
  private int myHealth = 150;
  private Inventory myInventory = new Inventory(2500);
  private int myKills = 0;


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
      currentRoom = roomMap.get("Spawn"); // use this tp & change spawn
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
      if(startingRoom.equals("random")){
        String[] possibleRooms = {"B-Elbow", "B-Main", "Garage", "B-Lobby", "Fountain", "Sahara", "Hookah", "Mid", "Showers", "HidingSpot", "LivingRoom", "Camps", "A-Main", "A-Elbow", "Haven", "Teleporter-A", "Teleporter-B"};
        int randomRoom = (int)(Math.random()*possibleRooms.length);
        startingRoom = possibleRooms[randomRoom];
      }
      boolean isHealing = (boolean)((JSONObject)itemObj).get("isHealing");
      boolean isWeapon = (boolean)((JSONObject)itemObj).get("isWeapon");
      boolean isSpike = (boolean)((JSONObject)itemObj).get("isSpike");
      String description = (String)((JSONObject)itemObj).get("description");
      if(isWeapon){
        int numBullets = ((Long)((JSONObject)itemObj).get("numBullets")).intValue();
        int damage = ((Long)((JSONObject)itemObj).get("damage")).intValue();
        int accuracy = ((Long)((JSONObject)itemObj).get("accuracy")).intValue();
        ItemList.add(new Weapon(itemWeight, damage, accuracy, numBullets, itemName, startingRoom, isWeapon, isHealing, isSpike, description));
      }
      else{
        ItemList.add(new Item(itemWeight, itemName, startingRoom, isWeapon, isHealing, isSpike, description));
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
      Boolean isTeleportRoom = (Boolean) ((JSONObject) roomObj).get("isTeleport");
      if(isTeleportRoom!=null)                                  
        room.setIsTeleportRoom(isTeleportRoom);
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
      JSONArray jsonTeleportRooms = (JSONArray) ((JSONObject) roomObj).get("teleportRooms");
      if(jsonTeleportRooms!=null){
      ArrayList<Room> teleportRooms = new ArrayList<Room>();
      for (Object teleObj : jsonTeleportRooms) {
        String teleRoomId = (String) teleObj;
        teleportRooms.add(roomMap.get(teleRoomId));
      }

      if(teleportRooms.size()>0){
        room.setTeleportRooms(teleportRooms);

      }
    }

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
    System.out.println("Welcome to TextShoot!");
    System.out.println("TextShoot is a interactive shooting game.");
    System.out.println("Type 'help' if you need help.");
    System.out.println();
    System.out.println(currentRoom.longDescription());
  }

  /**
   * Given a command, process (that is: execute) the command. If this command ends
   * the game, true is returned, otherwise false is returned.
   */
  private boolean processCommand(Command command) {
    String commandWord = command.getCommandWord();

    if (command.isUnknown() || commandWord == null) {
      System.out.println("I don't know what you mean...");
      return false;
    }
    
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
        System.out.println(currentRoom.longDescription());
      else if(commandWord.equals("use")){
        useItem(command);
      }
      else if(commandWord.equals("inventory")){
        myInventory.printItems();
        System.out.println("You can hold " + myInventory.remainingWeight() + " more grams");
      }
      else if(commandWord.equals("drop")){
        Item item = myInventory.dropItem(command.getSecondWord());
        if(!item.isHealing())
          currentRoom.setItem(item);
        else {
          System.out.println("Oh no, you dropped the glass potion, you can no longer use it");
          ItemList.remove(item);
        }
      }
      else if(commandWord.equals("jump")){
        jump(currentRoom);
      }
      else if(commandWord.equals("teleport")){
        teleport(command);
      }
      else if(commandWord.equals("heal")){
        heal();
      }
      else if(commandWord.equals("defuse")){
        defuse();
      }
      else if(commandWord.equals("display")){
        display(command);
      }
     
    return false;
  }

  // implementations of user commands:




  private void display(Command command) {
    String secondWord = command.getSecondWord();
    if(!command.hasSecondWord()){
      System.out.println("Display what?");
      System.out.println("Go where?");
      if(in == null){
        in = new Scanner(System.in);
      }
      System.out.print("> ");
      secondWord = in.nextLine();
    }
    if(secondWord.indexOf("Health") > -1){
      System.out.println("Your health is: " + myHealth + ".");
    }
    else if(secondWord.equalsIgnoreCase("kills")){
      System.out.println("You have " + myKills + " kills.");
    }
    else if(secondWord.equalsIgnoreCase("inventory")){
      myInventory.printItems();
      System.out.println("You can hold " + myInventory.remainingWeight() + " more grams");
    }
  }


  private boolean defuse() {
    if(myKills < 3){
      int remainingKills = 3 - myKills;
      System.out.println("You need to kill " + remainingKills + " more attackers to defuse the spike.");
      return false;
    }
    Item spike = null;
    for (Item item : currentRoom.getRoomItems()) {
      if(item.getName().equalsIgnoreCase("spike")){
        spike = item;
        break;
      }
    }
    if(spike == null){
      System.out.println("Spike is not in this room.");
      return false;
    }
    System.out.println("You successfully defused the spike and saved humanity!");
    return true;
    
  }


  private void teleport(Command command) {
    String nextRoom = command.getSecondWord(); 
    if(!command.hasSecondWord()) {
      System.out.println("Where do you want to teleport?");
      if(in == null){
        in = new Scanner(System.in);
      }
      System.out.print("> ");
      nextRoom = in.nextLine();
    }

    
    if(!currentRoom.isTeleportRoom()){
      System.out.println("This is not a teleport room.");
      return;
    }
    if (nextRoom == null){
      System.out.println("You cannot teleport there.");
      return;
    }
    Room possibleRoom = null; 
    for (Room room : currentRoom.getTeleportRooms()) {
      if(nextRoom.equalsIgnoreCase(room.getRoomName())){
      currentRoom=room; 
      System.out.println(currentRoom.longDescription());
      return; 
      }
    }
      System.out.println("You cannot go there."); 
    }

  


  /**
   * Print out some help information. Here we print some stupid, cryptic message
   * and a list of the command words.
   */
  private void printHelp() {
    System.out.println("Hurry Up! The spike is going to explode");
    System.out.println("You need " + (3 - myKills) + " more kills to defuse the spike.");
    System.out.println("Your goal is to find the spike and defuse it, be careful you may be attacked.");
    System.out.println();
    System.out.println("Your command words are:");
    parser.showCommands();
  }

  /**
   * Try to go to one direction. If there is an exit, enter the new room,
   * otherwise print an error message.
   */
  private void goRoom(Command command) {
    String direction = command.getSecondWord();
    if (!command.hasSecondWord()) {
      // if there is no second word, we don't know where to go...
      System.out.println("Go where?");
      if(in == null){
        in = new Scanner(System.in);
      }
      System.out.print("> ");
      direction = in.nextLine();

    }


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
    String gunName = command.getSecondWord();

    if(!command.hasSecondWord())  {
      //if there is no second word, we don't know what to shoot with...
      System.out.println("Shoot with what?");
      if(in == null){
        in = new Scanner(System.in);
      }
      System.out.print("> ");
      gunName = in.nextLine();
    }

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
            currentRoom.removeAttacker();
            //attackerList.remove(attacker);
            System.out.println("You Killed " + attacker.getName() );
            myKills++;
            System.out.println("You now have " + myKills + " kills");
          }
          else{
            myHealth -= attacker.getAttack();
            System.out.println("You did: " + damageDealt + " damage to " + attacker.getName() + "\n" + "They are now on " + attacker.getHp() + " hp" );
            System.out.println("They did " + attacker.getAttack() + " damage to you." + "\n" + "You are now on " + myHealth + " hp");
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
    String newItem = command.getSecondWord();
    if(!command.hasSecondWord()) {
      System.out.println("What do you want to take?");
      if(in == null){
        in = new Scanner(System.in);
      }
      System.out.print("> ");
      newItem = in.nextLine();
    }

    
    Item item = null; 
    
    for (Item potentialItem : currentRoom.getRoomItems()) {
      if(newItem.equalsIgnoreCase(potentialItem.getName())){
        item = potentialItem;
        myInventory.addItem(item);
        if(myInventory.remainingWeight() > 0){
          currentRoom.removeItem(item);
          System.out.println("You now have " + item.getName());
          System.out.println(item.getDescription());
          System.out.println("You can hold " + myInventory.remainingWeight() + " more grams");
        }
        return;
      }
    }
      System.out.println("You cannot take this item.");
    }


  
  private void useItem(Command command) {
    String itemName = command.getSecondWord();
    //if there is no second word, we don't know what to use
    if(!command.hasSecondWord()) {
      System.out.println("use what?");
      if(in == null){
        in = new Scanner(System.in);
      }
      System.out.print("> ");
      itemName = in.nextLine();
    }
    
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
      else if(potentialItem.isHealing()){
        if(myHealth <= 100){
          myHealth += 60;
          myInventory.dropItem("Health Potion");
          ItemList.remove(potentialItem);
          System.out.println("You have been healed to " + myHealth);
        }
        else{
          System.out.println("You have too much health to heal.");
        }
      }
    }else{
      System.out.println("You do not have this item.");
    }
  }
  
  private void heal() {
    if(myInventory.inInventory("Health Potion")){
      if(myHealth <= 100){
      myHealth += 60;
      System.out.println("You have been healed to " + myHealth);
      Item healingItem;
      for (Item item : myInventory.getInventory()) {
        if(("Health Potion").equals(item.getName())){
          myInventory.dropItem("Health Potion");
          currentRoom.removeItem(item);
          ItemList.remove(item);
          break;
        }
      }

    } else{
      System.out.println("You cannot heal because you have greater than 100 health.");
    }
  }
    else{
      System.out.println("You do not have any health potions");
    }
  }
  private void jump(Room currentRoom) {
    ArrayList<Exit> exits = currentRoom.getExits();
    System.out.println("You jump above the walls and you see the whats in the adjacent rooms");
    for (Exit exit : exits) {
      String direction = exit.getDirection();
      Room nextRoom = currentRoom.nextRoom(direction);
      System.out.println("To the " + direction + ": " + nextRoom.getDescription() + "\n");
    }

  }
//
}

  


